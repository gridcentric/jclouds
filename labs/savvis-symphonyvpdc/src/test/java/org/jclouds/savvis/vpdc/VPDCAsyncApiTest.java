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
package org.jclouds.savvis.vpdc;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.jclouds.http.HttpRequest;
import org.jclouds.rest.internal.RestAnnotationProcessor;
import org.jclouds.savvis.vpdc.features.BaseVPDCAsyncApiTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.inject.TypeLiteral;

/**
 * Tests behavior of {@code VPDCAsyncApi}
 * 
 * @author Adrian Cole
 */
// NOTE:without testName, this will not call @Before* and fail w/NPE during surefire
@Test(groups = "unit", testName = "VPDCAsyncApiTest")
public class VPDCAsyncApiTest extends BaseVPDCAsyncApiTest<VPDCAsyncApi> {

   private VPDCAsyncApi asyncApi;
   private VPDCApi syncApi;

   public void testSync() {
      assert syncApi.getBrowsingApi() != null;
      assert syncApi.getVMApi() != null;
      assertEquals(syncApi.listOrgs().size(), 1);
      assertEquals(syncApi.listPredefinedOperatingSystems().size(), 3);

   }

   public void testAsync() {
      assert asyncApi.getBrowsingApi() != null;
      assert asyncApi.getVMApi() != null;
      assertEquals(asyncApi.listOrgs().size(), 1);
      assertEquals(asyncApi.listPredefinedOperatingSystems().size(), 3);

   }

   @Override
   protected TypeLiteral<RestAnnotationProcessor<VPDCAsyncApi>> createTypeLiteral() {
      return new TypeLiteral<RestAnnotationProcessor<VPDCAsyncApi>>() {
      };
   }

   @BeforeClass
   @Override
   protected void setupFactory() throws IOException {
      super.setupFactory();
      asyncApi = injector.getInstance(VPDCAsyncApi.class);
      syncApi = injector.getInstance(VPDCApi.class);
   }

   @Override
   protected void checkFilters(HttpRequest request) {

   }
}
