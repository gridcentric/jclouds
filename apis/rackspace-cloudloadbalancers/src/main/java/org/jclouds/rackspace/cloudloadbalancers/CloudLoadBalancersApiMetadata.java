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
package org.jclouds.rackspace.cloudloadbalancers;

import static org.jclouds.openstack.keystone.v2_0.config.KeystoneProperties.CREDENTIAL_TYPE;
import static org.jclouds.openstack.keystone.v2_0.config.KeystoneProperties.SERVICE_TYPE;

import java.net.URI;
import java.util.Properties;

import org.jclouds.apis.ApiMetadata;
import org.jclouds.loadbalancer.LoadBalancerServiceContext;
import org.jclouds.openstack.keystone.v2_0.config.KeystoneAuthenticationModule.ZoneModule;
import org.jclouds.rackspace.cloudidentity.v2_0.ServiceType;
import org.jclouds.rackspace.cloudidentity.v2_0.config.CloudIdentityAuthenticationModule;
import org.jclouds.rackspace.cloudidentity.v2_0.config.CloudIdentityCredentialTypes;
import org.jclouds.rackspace.cloudloadbalancers.config.CloudLoadBalancersRestClientModule;
import org.jclouds.rackspace.cloudloadbalancers.loadbalancer.config.CloudLoadBalancersLoadBalancerContextModule;
import org.jclouds.rest.RestContext;
import org.jclouds.rest.internal.BaseRestApiMetadata;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.TypeToken;
import com.google.inject.Module;

/**
 * Implementation of {@link ApiMetadata} for CloudLoadBalancers 1.0 API
 * 
 * @author Adrian Cole
 */
public class CloudLoadBalancersApiMetadata extends BaseRestApiMetadata {

   @SuppressWarnings("serial")
   public static final TypeToken<RestContext<CloudLoadBalancersApi, CloudLoadBalancersAsyncApi>> CONTEXT_TOKEN = 
         new TypeToken<RestContext<CloudLoadBalancersApi, CloudLoadBalancersAsyncApi>>() {};


   @Override
   public Builder toBuilder() {
      return new Builder().fromApiMetadata(this);
   }

   public CloudLoadBalancersApiMetadata() {
      this(new Builder());
   }

   protected CloudLoadBalancersApiMetadata(Builder builder) {
      super(builder);
   }

   public static Properties defaultProperties() {
      Properties properties = BaseRestApiMetadata.defaultProperties();

      properties.setProperty(SERVICE_TYPE, ServiceType.LOAD_BALANCERS);
      properties.setProperty(CREDENTIAL_TYPE, CloudIdentityCredentialTypes.API_KEY_CREDENTIALS);

      return properties;
   }

   public static class Builder extends BaseRestApiMetadata.Builder {

      protected Builder() {
         super(CloudLoadBalancersApi.class, CloudLoadBalancersAsyncApi.class);
         id("cloudloadbalancers")
               .name("Rackspace Cloud Load Balancers API")
               .identityName("Username")
               .credentialName("API Key")
               .documentation(
                     URI.create("http://docs.rackspace.com/loadbalancers/api/clb-devguide-latest/index.html"))
               .version("1.0")
               .defaultEndpoint("https://identity.api.rackspacecloud.com/v2.0/")
               .defaultProperties(CloudLoadBalancersApiMetadata.defaultProperties())
               .view(TypeToken.of(LoadBalancerServiceContext.class))
               .defaultModules(
                     ImmutableSet.<Class<? extends Module>> of(
                           CloudIdentityAuthenticationModule.class,
                           ZoneModule.class,
                           CloudLoadBalancersRestClientModule.class,
                           CloudLoadBalancersLoadBalancerContextModule.class));
      }

      @Override
      public CloudLoadBalancersApiMetadata build() {
         return new CloudLoadBalancersApiMetadata(this);
      }

      @Override
      public Builder fromApiMetadata(ApiMetadata in) {
         super.fromApiMetadata(in);
         return this;
      }

   }

}
