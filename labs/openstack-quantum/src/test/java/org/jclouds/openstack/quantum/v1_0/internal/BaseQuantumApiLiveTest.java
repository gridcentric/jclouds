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
package org.jclouds.openstack.quantum.v1_0.internal;

import java.util.Properties;

import org.jclouds.apis.BaseContextLiveTest;
import org.jclouds.openstack.keystone.v2_0.config.KeystoneProperties;
import org.jclouds.openstack.quantum.v1_0.QuantumApi;
import org.jclouds.openstack.quantum.v1_0.QuantumApiMetadata;
import org.jclouds.openstack.quantum.v1_0.QuantumAsyncApi;
import org.jclouds.rest.RestContext;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import com.google.common.reflect.TypeToken;

/**
 * Tests behavior of {@code QuantumApi}
 * 
 * @author Adam Lowe
 */
@Test(groups = "live")
public class BaseQuantumApiLiveTest extends BaseContextLiveTest<RestContext<QuantumApi, QuantumAsyncApi>> {

   public BaseQuantumApiLiveTest() {
      provider = "openstack-quantum";
   }

   protected RestContext<QuantumApi, QuantumAsyncApi> quantumContext;

   @BeforeGroups(groups = { "integration", "live" })
   @Override
   public void setupContext() {
      super.setupContext();
      quantumContext = context;
   }

   @Override
   protected Properties setupProperties() {
      Properties props = super.setupProperties();
      setIfTestSystemPropertyPresent(props, KeystoneProperties.CREDENTIAL_TYPE);
      return props;
   }

   @Override
   protected TypeToken<RestContext<QuantumApi, QuantumAsyncApi>> contextType() {
      return QuantumApiMetadata.CONTEXT_TOKEN;
   }

}
