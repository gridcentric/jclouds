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
package org.jclouds.azure.management.domain.role.conf;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ConfigurationSetAdapter extends
         XmlAdapter<ConfigurationSetAdapter.AdaptedConfigurationSet, ConfigurationSet> {

   @Override
   public ConfigurationSet unmarshal(AdaptedConfigurationSet adapted) throws Exception {

      final String type = adapted.configurationSetType;
      if (NetworkConfiguration.ID.equals(type)) {
         NetworkConfiguration network = new NetworkConfiguration();
         network.setConfigurationSetType(NetworkConfiguration.ID);
         network.setInputEndpoints(adapted.inputEndpoints);
         network.setSubnetNames(adapted.subnetNames);
         return network;
      } else if (LinuxProvisioningConfiguration.ID.equals(type)) {
         LinuxProvisioningConfiguration linux = new LinuxProvisioningConfiguration();
         linux.setConfigurationSetType(LinuxProvisioningConfiguration.ID);
         linux.setDisableSshPasswordAuthentication(adapted.disableSshPasswordAuthentication);
         linux.setHostName(adapted.hostName);
         linux.setSsh(adapted.ssh);
         linux.setUserName(adapted.userName);
         linux.setUserPassword(adapted.userPassword);
         return linux;
      } else if (WindowsProvisioningConfiguration.ID.equals(type)) {
         WindowsProvisioningConfiguration windows = new WindowsProvisioningConfiguration();
         windows.setConfigurationSetType(WindowsProvisioningConfiguration.ID);
         windows.setAdminPassword(adapted.adminPassword);
         windows.setComputerName(adapted.computerName);
         windows.setDomainJoin(adapted.domainJoin);
         windows.setEnableAutomaticUpdates(adapted.enableAutomaticUpdates);
         windows.setResetPasswordOnFirstLogon(adapted.resetPasswordOnFirstLogon);
         windows.setStoredCertificateSettings(adapted.storedCertificateSettings);
         windows.setTimeZone(adapted.timeZone);
         return windows;
      }

      return null;
   }

   @Override
   public AdaptedConfigurationSet marshal(ConfigurationSet configSet) throws Exception {
      if (configSet == null) {
         return null;
      }

      AdaptedConfigurationSet adapted = new AdaptedConfigurationSet();
      adapted.configurationSetType = configSet.getConfigurationSetType();

      if (configSet instanceof NetworkConfiguration) {
         NetworkConfiguration network = (NetworkConfiguration) configSet;
         adapted.inputEndpoints = network.getInputEndpoints();
         adapted.subnetNames = network.getSubnetNames();
      } else if (configSet instanceof LinuxProvisioningConfiguration) {
         LinuxProvisioningConfiguration linux = (LinuxProvisioningConfiguration) configSet;
         adapted.disableSshPasswordAuthentication = linux.getDisableSshPasswordAuthentication();
         adapted.hostName = linux.getHostName();
         adapted.ssh = linux.getSsh();
         adapted.userName = linux.getUserName();
         adapted.userPassword = linux.getUserPassword();
      } else if (configSet instanceof WindowsProvisioningConfiguration) {
         WindowsProvisioningConfiguration windows = (WindowsProvisioningConfiguration) configSet;
         adapted.adminPassword = windows.getAdminPassword();
         adapted.computerName = windows.getComputerName();
         adapted.domainJoin = windows.getDomainJoin();
         adapted.enableAutomaticUpdates = windows.getEnableAutomaticUpdates();
         adapted.resetPasswordOnFirstLogon = windows.getResetPasswordOnFirstLogon();
         adapted.storedCertificateSettings = windows.getStoredCertificateSettings();
         adapted.timeZone = windows.getTimeZone();
      }

      return adapted;
   }

   public static class AdaptedConfigurationSet {
      @XmlElement(required = true, name = "ConfigurationSetType")
      public String configurationSetType;

      @XmlElement(required = true, name = "HostName")
      public String hostName;

      @XmlElement(required = true, name = "UserName")
      public String userName;

      @XmlElement(required = true, name = "UserPassword")
      public String userPassword;

      @XmlElement(name = "DisableSshPasswordAuthentication")
      public Boolean disableSshPasswordAuthentication;

      @XmlElement(name = "SSH")
      public SSH ssh;

      @XmlElement(name = "ComputerName")
      public String computerName;

      @XmlElement(required = true, name = "AdminPassword")
      public String adminPassword;

      @XmlElement(required = true, name = "ResetPasswordOnFirstLogon")
      public Boolean resetPasswordOnFirstLogon;

      @XmlElement(name = "EnableAutomaticUpdates")
      public Boolean enableAutomaticUpdates;

      @XmlElement(name = "TimeZone")
      public TimeZone timeZone;

      @XmlElement(name = "DomainJoin")
      public DomainJoin domainJoin;

      @XmlElementWrapper(required = true, name = "StoredCertificateSettings")
      @XmlElement(name = "CertificateSetting")
      public List<CertificateSetting> storedCertificateSettings = new ArrayList<CertificateSetting>();

      @XmlElementWrapper(name = "InputEndpoints")
      @XmlElement(name = "InputEndpoint")
      public List<InputEndpoint> inputEndpoints = new ArrayList<InputEndpoint>(0);

      @XmlElementWrapper(name = "SubnetNames")
      @XmlElement(name = "SubnetName")
      public List<String> subnetNames = new ArrayList<String>(0);
   }

}
