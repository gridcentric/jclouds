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

import java.lang.reflect.Method;

import org.jclouds.cloudstack.domain.Account;
import org.jclouds.cloudstack.internal.BaseCloudStackAsyncClientTest;
import org.jclouds.cloudstack.options.CreateAccountOptions;
import org.jclouds.cloudstack.options.UpdateAccountOptions;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.functions.ParseFirstJsonValueNamed;
import org.jclouds.http.functions.ReleasePayloadAndReturn;
import org.jclouds.rest.functions.ReturnNullOnNotFoundOr404;
import org.jclouds.rest.internal.RestAnnotationProcessor;
import org.testng.annotations.Test;

import com.google.inject.TypeLiteral;

/**
 * Tests behavior of {@code GlobalAccountAsyncClient}
 *
 * @author Adrian
 */
@Test(groups = "unit", testName = "GlobalAccountAsyncClientTest")
public class GlobalAccountAsyncClientTest extends BaseCloudStackAsyncClientTest<GlobalAccountAsyncClient> {

   HttpRequest create = HttpRequest.builder().method("GET")
                                   .endpoint("http://localhost:8080/client/api")
                                   .addQueryParam("response", "json")
                                   .addQueryParam("command", "createAccount")
                                   .addQueryParam("username", "user")
                                   .addQueryParam("accounttype", "0")
                                   .addQueryParam("email", "email@example.com")
                                   .addQueryParam("firstname", "FirstName")
                                   .addQueryParam("lastname", "LastName")
                                   .addQueryParam("password", "hashed-password")
                                   .build();

   public void testCreateAccount() throws Exception {
      Method method = GlobalAccountAsyncClient.class.getMethod("createAccount", String.class, Account.Type.class,
         String.class, String.class, String.class, String.class, CreateAccountOptions[].class);
      HttpRequest httpRequest = processor.createRequest(method, "user", Account.Type.USER, "email@example.com",
         "FirstName", "LastName", "hashed-password");

      assertRequestLineEquals(httpRequest, create.getRequestLine());
      assertNonPayloadHeadersEqual(httpRequest, "Accept: application/json\n");
      assertPayloadEquals(httpRequest, null, null, false);

      assertResponseParserClassEquals(method, httpRequest, ParseFirstJsonValueNamed.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, ReturnNullOnNotFoundOr404.class);

      checkFilters(httpRequest);
   }

   HttpRequest update = HttpRequest.builder().method("GET")
                                   .endpoint("http://localhost:8080/client/api")
                                   .addQueryParam("response", "json")
                                   .addQueryParam("command", "updateAccount")
                                   .addQueryParam("account", "account")
                                   .addQueryParam("domainid", "42")
                                   .addQueryParam("newname", "new-account-name")
                                   .build();

   public void testUpdateAccount() throws Exception {
      Method method = GlobalAccountAsyncClient.class.getMethod("updateAccount", String.class, String.class,
         String.class, UpdateAccountOptions[].class);
      HttpRequest httpRequest = processor.createRequest(method, "account", 42L, "new-account-name");

      assertRequestLineEquals(httpRequest, update.getRequestLine());
      assertNonPayloadHeadersEqual(httpRequest, "Accept: application/json\n");
      assertPayloadEquals(httpRequest, null, null, false);

      assertResponseParserClassEquals(method, httpRequest, ParseFirstJsonValueNamed.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, ReturnNullOnNotFoundOr404.class);

      checkFilters(httpRequest);
   }

   public void testDeleteAccount() throws Exception {
      Method method = GlobalAccountAsyncClient.class.getMethod("deleteAccount", String.class);
      HttpRequest httpRequest = processor.createRequest(method, 42L);

      assertRequestLineEquals(httpRequest,
         "GET http://localhost:8080/client/api?response=json&command=deleteAccount&id=42 HTTP/1.1");
      assertNonPayloadHeadersEqual(httpRequest, "Accept: application/json\n");
      assertPayloadEquals(httpRequest, null, null, false);

      assertResponseParserClassEquals(method, httpRequest, ReleasePayloadAndReturn.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, ReturnNullOnNotFoundOr404.class);

      checkFilters(httpRequest);
   }

   @Override
   protected TypeLiteral<RestAnnotationProcessor<GlobalAccountAsyncClient>> createTypeLiteral() {
      return new TypeLiteral<RestAnnotationProcessor<GlobalAccountAsyncClient>>() {
      };
   }
}
