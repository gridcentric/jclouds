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
package org.jclouds.cloudstack.features;

import java.io.IOException;
import java.lang.reflect.Method;

import org.jclouds.cloudstack.internal.BaseCloudStackAsyncClientTest;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.functions.ParseFirstJsonValueNamed;
import org.jclouds.rest.functions.MapHttp4xxCodesToExceptions;
import org.jclouds.rest.internal.RestAnnotationProcessor;
import org.testng.annotations.Test;

import com.google.inject.TypeLiteral;

/**
 * Tests behavior of {@code ConfigurationAsyncClient}
 * 
 * @author Adrian Cole
 */
// NOTE:without testName, this will not call @Before* and fail w/NPE during
// surefire
@Test(groups = "unit", testName = "ConfigurationAsyncClientTest")
public class ConfigurationAsyncClientTest extends BaseCloudStackAsyncClientTest<ConfigurationAsyncClient> {

   public void testListCapabilities() throws SecurityException, NoSuchMethodException, IOException {
      Method method = ConfigurationAsyncClient.class.getMethod("listCapabilities");
      HttpRequest httpRequest = processor.createRequest(method);

      assertRequestLineEquals(httpRequest,
            "GET http://localhost:8080/client/api?response=json&listAll=true&command=listCapabilities HTTP/1.1");
      assertNonPayloadHeadersEqual(httpRequest, "Accept: application/json\n");
      assertPayloadEquals(httpRequest, null, null, false);

      assertResponseParserClassEquals(method, httpRequest, ParseFirstJsonValueNamed.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, MapHttp4xxCodesToExceptions.class);

      checkFilters(httpRequest);

   }

   @Override
   protected TypeLiteral<RestAnnotationProcessor<ConfigurationAsyncClient>> createTypeLiteral() {
      return new TypeLiteral<RestAnnotationProcessor<ConfigurationAsyncClient>>() {
      };
   }
}
