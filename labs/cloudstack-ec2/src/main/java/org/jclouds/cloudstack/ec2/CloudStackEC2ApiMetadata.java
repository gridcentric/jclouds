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
package org.jclouds.cloudstack.ec2;

import java.net.URI;
import java.util.Properties;

import org.jclouds.apis.ApiMetadata;
import org.jclouds.ec2.EC2ApiMetadata;
import org.jclouds.ec2.EC2AsyncClient;
import org.jclouds.ec2.EC2Client;


/**
 * Implementation of {@link ApiMetadata} for the CloudStackEC2 (EC2 clone) api.
 * 
 * @author Adrian Cole
 */
public class CloudStackEC2ApiMetadata extends EC2ApiMetadata {
   /** The serialVersionUID */
   private static final long serialVersionUID = 3060225665040763827L;

   private static Builder builder() {
      return new Builder();
   }

   @Override
   public Builder toBuilder() {
      return builder().fromApiMetadata(this);
   }

   public CloudStackEC2ApiMetadata() {
      this(builder());
   }

   protected CloudStackEC2ApiMetadata(Builder builder) {
      super(builder);
   }

   public static Properties defaultProperties() {
      Properties properties = EC2ApiMetadata.defaultProperties();
      return properties;
   }

   public static class Builder extends EC2ApiMetadata.Builder {
      protected Builder() {
         super(EC2Client.class, EC2AsyncClient.class);
         id("cloudstack-ec2")
         .name("CloudStackEC2 (EC2 clone) API")
         .version("2010-11-15")
         .defaultEndpoint("http://localhost:8090/bridge/rest/AmazonEC2")
         .documentation(URI.create("http://docs.cloudstack.org/CloudBridge_Documentation"))
         .defaultProperties(CloudStackEC2ApiMetadata.defaultProperties());
      }

      @Override
      public CloudStackEC2ApiMetadata build() {
         return new CloudStackEC2ApiMetadata(this);
      }

      @Override
      public Builder fromApiMetadata(ApiMetadata in) {
         super.fromApiMetadata(in);
         return this;
      }
   }

}
