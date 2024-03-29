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
package org.jclouds.snia.cdmi.v1.features;

import static org.testng.Assert.assertEquals;

import org.jclouds.crypto.CryptoStreams;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpResponse;
import org.jclouds.snia.cdmi.v1.CDMIApi;
import org.jclouds.snia.cdmi.v1.internal.BaseCDMIApiExpectTest;
import org.jclouds.snia.cdmi.v1.parse.ParseContainerTest;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMultimap;

/**
 * 
 * @author Adrian Cole
 */
@Test(groups = "unit", testName = "ContainerAsyncApiTest")
public class ContainerApiExpectTest extends BaseCDMIApiExpectTest {

   public void testGetContainerWhenResponseIs2xx() throws Exception {

      HttpRequest get = HttpRequest
               .builder()
               .method("GET")
               .endpoint("http://localhost:8080/MyContainer/")
               .headers(ImmutableMultimap.<String, String> builder().put("X-CDMI-Specification-Version", "1.0.1")
                        .put("TID", "tenantId")
                        .put("Authorization", "Basic " + CryptoStreams.base64("username:password".getBytes()))
                        .put("Accept", "application/cdmi-container").build()).build();

      HttpResponse getResponse = HttpResponse.builder().statusCode(200).payload(payloadFromResource("/container.json"))
               .build();

      CDMIApi apiWhenContainersExist = requestSendsResponse(get, getResponse);

      assertEquals(apiWhenContainersExist.getApi().get("MyContainer/"), new ParseContainerTest().expected());
   }

}
