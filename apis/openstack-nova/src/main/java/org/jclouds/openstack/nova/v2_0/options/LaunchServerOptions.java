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
package org.jclouds.openstack.nova.v2_0.options;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.jclouds.encryption.internal.Base64;
import org.jclouds.http.HttpRequest;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.rest.MapBinder;
import org.jclouds.rest.binders.BindToJsonPayload;
import org.jclouds.util.Preconditions2;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.base.Optional;
import com.google.common.collect.ForwardingObject;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 
 * @author Adrian Cole
 * @author Rui Lin
 * 
 */
public class LaunchServerOptions implements MapBinder {
   @Inject
   private BindToJsonPayload jsonBinder;

   static class File {
      private final String path;
      private final String contents;

      public File(String path, byte[] contents) {
         this.path = checkNotNull(path, "path");
         this.contents = Base64.encodeBytes(checkNotNull(contents, "contents"));
         checkArgument(
               path.getBytes().length < 255,
               String.format("maximum length of path is 255 bytes.  Path specified %s is %d bytes", path,
                     path.getBytes().length));
         checkArgument(contents.length < 10 * 1024,
               String.format("maximum size of the file is 10KB.  Contents specified is %d bytes", contents.length));
      }

      public String getContents() {
         return contents;
      }

      public String getPath() {
         return path;
      }
      
      @Override
      public boolean equals(Object object) {
         if (this == object) {
            return true;
         }
         if (object instanceof File) {
            final File other = File.class.cast(object);
            return equal(path, other.path);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return Objects.hashCode(path);
      }

      @Override
      public String toString() {
         return toStringHelper("file").add("path", path).toString();
      }

   }

   private String keyName;
   private Set<String> securityGroupNames = ImmutableSet.of();
   private Map<String, String> guestParams = ImmutableMap.of();
   private Map<String, String> schedulerHints = ImmutableMap.of();
   // private List<File> personality = Lists.newArrayList(); // huh, useful RUITODO
   private byte[] userData;
   private int numInstances;

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      }
      if (object instanceof LaunchServerOptions) {
         final LaunchServerOptions other = LaunchServerOptions.class.cast(object);
         return equal(keyName, other.keyName) && equal(securityGroupNames, other.securityGroupNames)
                  && equal(guestParams, other.guestParams) && equal(schedulerHints, other.schedulerHints)
                  && (numInstances == other.numInstances);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(keyName, securityGroupNames, guestParams, schedulerHints, numInstances);
   }

   protected ToStringHelper string() {
      ToStringHelper toString = Objects.toStringHelper("").omitNullValues();
      toString.add("keyName", keyName);
      if (securityGroupNames.size() > 0)
         toString.add("securityGroupNames", securityGroupNames);
      if (guestParams.size() > 0)
         toString.add("guestParams", guestParams);
      if (schedulerHints.size() > 0)
         toString.add("schedulerHints", schedulerHints);
      toString.add("numInstances", numInstances);
      toString.add("userData", userData == null ? null : new String(userData));
      return toString;
   }

   @Override
   public String toString() {
      return string().toString();
   }

   static class ServerRequest {
      final String name;
      Map<String, String> guest;
      Map<String, String> scheduler_hints;
      String key_name;
      int num_instances;
      @Named("security_groups")
      Set<NamedThingy> securityGroupNames;
      String user_data;

      private ServerRequest(String name) {
         this.name = name;
      }

   }

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Map<String, Object> postParams) {
      ServerRequest server = new ServerRequest(checkNotNull(postParams.get("name"), "name parameter not present").toString());
      if (numInstances < 1)
          numInstances=1;
      server.num_instances = numInstances;
      if (guestParams.size() > 0)
         server.guest = guestParams;
      if (schedulerHints.size() > 0)
         server.scheduler_hints = schedulerHints;
      if (keyName != null)
         server.key_name = keyName;
      if (userData != null)
          server.user_data = Base64.encodeBytes(userData);
      if (securityGroupNames.size() > 0) {
         server.securityGroupNames = Sets.newLinkedHashSet();
         for (String groupName : securityGroupNames) {
            server.securityGroupNames.add(new NamedThingy(groupName));
         }
      }

      return bindToRequest(request, ImmutableMap.of("gc_launch", server));
   }

   private static class NamedThingy extends ForwardingObject {
      private String name;

      private NamedThingy(String name) {
         this.name = name;
      }

      @Override
      protected Object delegate() {
         return name;
      }
   }

   /**
    * Guest params to send to vms-agent. RUITODO not sure if following still
    * relevant. The maximum size of the metadata key and value
    * is each 255 bytes and the maximum number of key-value pairs that can be
    * supplied per server is 5.
    */
   public LaunchServerOptions guestParams(Map<String, String> guestParams) {
      checkNotNull(guestParams, "guestParams");
      checkArgument(guestParams.size() <= 5,
            "you cannot have more then 5 guestParams values.  You specified: " + guestParams.size());
      for (Entry<String, String> entry : guestParams.entrySet()) {
         checkArgument(
               entry.getKey().getBytes().length < 255,
               String.format("maximum length of guestParams key is 255 bytes.  Key specified %s is %d bytes",
                     entry.getKey(), entry.getKey().getBytes().length));
         checkArgument(entry.getKey().getBytes().length < 255, String.format(
               "maximum length of guestParams value is 255 bytes.  Value specified for %s (%s) is %d bytes",
               entry.getKey(), entry.getValue(), entry.getValue().getBytes().length));
      }
      this.guestParams = ImmutableMap.copyOf(guestParams);
      return this;
   }

   /**
    * Arbitary key-value pairs to scheduler for custom use.
    * RUITODO not sure if still relevant. The maximum size of the metadata key and value
    * is each 255 bytes and the maximum number of key-value pairs that can be
    * supplied per server is 5.
    */
   public LaunchServerOptions schedulerHints(Map<String, String> schedulerHints) {
      checkNotNull(schedulerHints, "schedulerHints");
      checkArgument(schedulerHints.size() <= 5,
            "you cannot have more then 5 schedulerHints values.  You specified: " + schedulerHints.size());
      for (Entry<String, String> entry : schedulerHints.entrySet()) {
         checkArgument(
               entry.getKey().getBytes().length < 255,
               String.format("maximum length of schedulerHints key is 255 bytes.  Key specified %s is %d bytes",
                     entry.getKey(), entry.getKey().getBytes().length));
         checkArgument(entry.getKey().getBytes().length < 255, String.format(
               "maximum length of schedulerHints value is 255 bytes.  Value specified for %s (%s) is %d bytes",
               entry.getKey(), entry.getValue(), entry.getValue().getBytes().length));
      }
      this.schedulerHints = ImmutableMap.copyOf(schedulerHints);
      return this;
   }

   /**
    * Custom user-data can be also be supplied at launch time.
    * It is retrievable by the instance and is often used for launch-time configuration
    * by instance scripts.
    */
   public LaunchServerOptions userData(byte[] userData) {
       this.userData = userData;
       return this;
   }

   /**
    * A keypair name can be defined when creating a server. This key will be
    * linked to the server and used to SSH connect to the machine
    */
   public String getKeyPairName() {
      return keyName;
   }

   /**
    * Number of instances to launch.
    */
   public int getNumInstances() {
      return numInstances;
   }
   
   /**
    * @see #getKeyPairName()
    */
   public LaunchServerOptions keyPairName(String keyName) {
      this.keyName = keyName;
      return this;
   }

   /**
    * @see #getNumInstances()
    */
   public LaunchServerOptions numInstances(int numInstances) {
      this.numInstances = numInstances;
      return this;
   }
   
   /**
    * 
    * Security groups the user specified to run servers with.
    * 
    * <h3>Note</h3>
    * 
    * This requires that {@link NovaApi#getSecurityGroupExtensionForZone(String)} to return
    * {@link Optional#isPresent present}
    */
   public Set<String> getSecurityGroupNames() {
      return securityGroupNames;
   }
   
   /**
    * 
    * @see #getSecurityGroupNames
    */
   public LaunchServerOptions securityGroupNames(String... securityGroupNames) {
      return securityGroupNames(ImmutableSet.copyOf(checkNotNull(securityGroupNames, "securityGroupNames")));
   }

   /**
    * @see #getSecurityGroupNames
    */
   public LaunchServerOptions securityGroupNames(Iterable<String> securityGroupNames) {
      for (String groupName : checkNotNull(securityGroupNames, "securityGroupNames"))
         Preconditions2.checkNotEmpty(groupName, "all security groups must be non-empty");
      this.securityGroupNames = ImmutableSet.copyOf(securityGroupNames);
      return this;
   }
   
   public static class Builder {

      /**
       * @see LaunchServerOptions#schedulerHints(Map<String, String>)
       */
      public static LaunchServerOptions schedulerHints(Map<String, String> schedulerHints) {
         LaunchServerOptions options = new LaunchServerOptions();
         return options.schedulerHints(schedulerHints);
      }

      /**
       * @see LaunchServerOptions#guestParams(Map<String, String>)
       */
      public static LaunchServerOptions guestParams(Map<String, String> guestParams) {
         LaunchServerOptions options = new LaunchServerOptions();
         return options.guestParams(guestParams);
      }

      /**
       * @see #getNumInstances()
       */
      public static LaunchServerOptions numInstances(int numInstances) {
         LaunchServerOptions options = new LaunchServerOptions();
         return options.numInstances(numInstances);
      }
      
      /**
       * @see #getKeyPairName()
       */
      public static LaunchServerOptions keyPairName(String keyName) {
         LaunchServerOptions options = new LaunchServerOptions();
         return options.keyPairName(keyName);
      }
      
      /**
       * @see LaunchServerOptions#getSecurityGroupNames
       */
      public static LaunchServerOptions securityGroupNames(String... groupNames) {
         LaunchServerOptions options = new LaunchServerOptions();
         return LaunchServerOptions.class.cast(options.securityGroupNames(groupNames));
      }

      /**
       * @see LaunchServerOptions#getSecurityGroupNames
       */
      public static LaunchServerOptions securityGroupNames(Iterable<String> groupNames) {
         LaunchServerOptions options = new LaunchServerOptions();
         return LaunchServerOptions.class.cast(options.securityGroupNames(groupNames));
      }
   }

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Object input) {
      return jsonBinder.bindToRequest(request, input);
   }
}
