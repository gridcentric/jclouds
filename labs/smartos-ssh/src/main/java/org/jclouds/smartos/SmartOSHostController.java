/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.smartos;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.jclouds.domain.Credentials;
import org.jclouds.domain.LoginCredentials;
import org.jclouds.json.Json;
import org.jclouds.location.Provider;
import org.jclouds.smartos.compute.domain.DataSet;
import org.jclouds.smartos.compute.domain.VM;
import org.jclouds.smartos.compute.domain.VmSpecification;
import org.jclouds.ssh.SshClient;

import com.google.common.base.Splitter;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.net.HostAndPort;
import com.google.common.util.concurrent.RateLimiter;

/**
 * A host machine that runs smartOS
 */
public class SmartOSHostController {
   protected final String hostname;
   protected final Supplier<Credentials> creds;
   protected final SshClient.Factory sshClientFactory;
   protected final Json json;

   protected transient SshClient _connection;

   public static class HostException extends RuntimeException {
      private static final long serialVersionUID = -2247796213703641847L;

      public HostException(String s, Throwable throwable) {
         super(s, throwable);
      }

      public HostException(String s) {
         super(s);
      }
   }

   public static class NoResponseException extends Exception {
      private static final long serialVersionUID = -2247796213703641847L;

      public NoResponseException() {
      }
   }

   @Inject
   protected SmartOSHostController(@Provider Supplier<URI> provider,
         @org.jclouds.location.Provider final Supplier<Credentials> creds, SshClient.Factory sshFactory, Json json) {
      this.hostname = provider.get().getHost();
      this.creds = creds;
      this.sshClientFactory = sshFactory;
      this.json = json;
   }

   public String getDescription() {
      return "SmartOS@" + hostname;
   }

   public String getHostname() {
      return hostname;
   }

   public SshClient.Factory getSshClientFactory() {
      return sshClientFactory;
   }

   protected SshClient getConnection() {
      if (_connection == null) {
         Credentials currentCreds = checkNotNull(creds.get(), "credential supplier returned null");

         LoginCredentials credentials = new LoginCredentials.Builder().user(currentCreds.identity)
               .password(currentCreds.credential).build();

         _connection = getSshClientFactory().create(HostAndPort.fromParts(hostname, 22), credentials);

         _connection.connect();

      }
      return _connection;
   }

   public String exec(String cmd) {
      return getConnection().exec(cmd).getOutput();
   }

   public String vmList() {
      return exec("vmadm list -p");
   }

   public Map<String, String> getVMIpAddresses(UUID vmId) {
      ImmutableMap.Builder<String, String> netMapBuilder = ImmutableMap.builder();

      String response = getConnection().exec("zlogin " + vmId.toString() + " ifconfig -a4").getOutput();

      if (response.length() == 0)
         return ImmutableMap.of();

      Iterable<String> strings = Splitter.on("\n").split(response);
      Pattern inetMatcher = Pattern.compile("inet [0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");

      String iface = "";
      for (String line : strings) {
         if (line.length() > 0 && Character.isLetterOrDigit(line.charAt(0))) {
            iface = line.substring(0, line.indexOf(":"));
         } else {
            Matcher matcher = inetMatcher.matcher(line);
            if (matcher.find())
               netMapBuilder.put(iface, matcher.group().substring(5));
         }
      }

      return netMapBuilder.build();

   }

   /**
    * What remotely available images are there in the cloud?
    * 
    * @return Collection of datasets
    */
   public Iterable<DataSet> getAvailableImages() {
      return toSpecList(exec("dsadm avail"));
   }

   public Iterable<DataSet> getLocalDatasets() {
      return toSpecList(exec("dsadm list"));
   }

   public Iterable<VM> getVMs() {
      return toVMList(exec("vmadm list -p"));
   }

   public VM createVM(VmSpecification specification) {

      String specAsJson = json.toJson(specification);
      String response = getConnection().exec("(cat <<END\n" + specAsJson + "\nEND\n) | vmadm create").getOutput();

      Pattern uuidPattern = Pattern.compile("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}");
      Matcher matcher = uuidPattern.matcher(response);
      if (matcher.find()) {
         String uuid = matcher.group();
         return getVM(UUID.fromString(uuid));
      } else {
         throw new HostException("Error creating Host: response = " + response + "\n source = " + specAsJson);
      }

   }

   private Iterable<DataSet> toSpecList(String string) {

      try {
         BufferedReader r = new BufferedReader(new StringReader(string));
         r.readLine(); // Skip
         String line;
         ImmutableList.Builder<DataSet> resultBuilder = ImmutableList.builder();
         while ((line = r.readLine()) != null) {
            DataSet dataset = DataSet.builder().fromDsadmString(line).build();

            resultBuilder.add(dataset);
         }
         return resultBuilder.build();
      } catch (IOException e) {
         throw new HostException("Error parsing response when building spec list", e);
      }
   }

   private Iterable<VM> toVMList(String string) {
      try {
         BufferedReader r = new BufferedReader(new StringReader(string));
         String line;
         ImmutableList.Builder<VM> resultBuilder = ImmutableList.builder();
         while ((line = r.readLine()) != null) {
            VM vm = VM.builder().fromVmadmString(line).build();

            Map<String, String> ipAddresses;
            RateLimiter limiter = RateLimiter.create(1.0);
            for (int i = 0; i < 30; i++) {
               ipAddresses = getVMIpAddresses(vm.getUuid());
               if (!ipAddresses.isEmpty()) {
                  // Got some
                  String ip = ipAddresses.get("net0");
                  if (ip != null && !ip.equals("0.0.0.0")) {
                     vm = vm.toBuilder().publicAddress(ip).build();
                     break;
                  }
               }

               limiter.acquire();
            }

            resultBuilder.add(vm);
         }
         return resultBuilder.build();
      } catch (IOException e) {
         throw new HostException("Error parsing response when building VM list", e);
      }
   }

   public VM getVM(UUID serverId) {
      for (VM vm : getVMs())
         if (vm.getUuid().equals(serverId))
            return vm;
      return null;
   }

   public DataSet getDataSet(UUID imageId) {
      for (DataSet ds : getLocalDatasets()) {
         if (ds.getUuid().equals(imageId))
            return ds;
      }
      return null;
   }

   public void destroyHost(UUID uuid) {
      exec("vmadm delete " + uuid.toString());
   }

   public void rebootHost(UUID uuid) {
      exec("vmadm reboot " + uuid.toString());
   }

   public void stopHost(UUID uuid) {
      exec("vmadm stop -p");
   }

   public void startHost(UUID uuid) {
      exec("vmadm start " + uuid.toString());
   }
}
