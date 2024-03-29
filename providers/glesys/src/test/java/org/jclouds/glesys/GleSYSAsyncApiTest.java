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
package org.jclouds.glesys;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.jclouds.http.HttpRequest;
import org.jclouds.providers.ProviderMetadata;
import org.jclouds.rest.internal.BaseAsyncApiTest;
import org.jclouds.rest.internal.RestAnnotationProcessor;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.inject.TypeLiteral;

/**
 * Tests behavior of {@code GleSYSAsyncApi}
 * 
 * @author Adrian Cole
 */
// NOTE:without testName, this will not call @Before* and fail w/NPE during
// surefire
@Test(groups = "unit", testName = "GleSYSAsyncApiTest")
public class GleSYSAsyncApiTest extends BaseAsyncApiTest<GleSYSAsyncApi> {
   private GleSYSAsyncApi asyncApi;
   private GleSYSApi syncApi;

   @Override
   public ProviderMetadata createProviderMetadata() {
      return new GleSYSProviderMetadata();
   }
   
   public void testSync() throws SecurityException, NoSuchMethodException, InterruptedException, ExecutionException {
      assert syncApi.getServerApi() != null;
      assert syncApi.getIpApi() != null;
      assert syncApi.getDomainApi() != null;
      assert syncApi.getArchiveApi() != null;
   }

   public void testAsync() throws SecurityException, NoSuchMethodException, InterruptedException, ExecutionException {
      assert asyncApi.getServerApi() != null;
      assert asyncApi.getIpApi() != null;
      assert asyncApi.getDomainApi() != null;
      assert asyncApi.getArchiveApi() != null;
   }

   @Override
   protected TypeLiteral<RestAnnotationProcessor<GleSYSAsyncApi>> createTypeLiteral() {
      return new TypeLiteral<RestAnnotationProcessor<GleSYSAsyncApi>>() {
      };
   }

   @BeforeClass
   @Override
   protected void setupFactory() throws IOException {
      super.setupFactory();
      asyncApi = injector.getInstance(GleSYSAsyncApi.class);
      syncApi = injector.getInstance(GleSYSApi.class);
   }

   @Override
   protected void checkFilters(HttpRequest request) {

   }
}
