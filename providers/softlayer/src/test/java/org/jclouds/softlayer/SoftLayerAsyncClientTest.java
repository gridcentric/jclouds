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
package org.jclouds.softlayer;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.jclouds.http.HttpRequest;
import org.jclouds.rest.internal.RestAnnotationProcessor;
import org.jclouds.softlayer.features.BaseSoftLayerAsyncClientTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.inject.TypeLiteral;

/**
 * Tests behavior of {@code SoftLayerAsyncClient}
 * 
 * @author Adrian Cole
 */
// NOTE:without testName, this will not call @Before* and fail w/NPE during surefire
@Test(groups = "unit", testName = "SoftLayerAsyncClientTest")
public class SoftLayerAsyncClientTest extends BaseSoftLayerAsyncClientTest<SoftLayerAsyncClient> {

   private SoftLayerAsyncClient asyncClient;
   private SoftLayerClient syncClient;

   public void testSync() throws SecurityException, NoSuchMethodException, InterruptedException, ExecutionException {
      assert syncClient.getVirtualGuestClient() != null;
      assert syncClient.getDatacenterClient() != null;
      assert syncClient.getProductPackageClient() != null;
   }

   public void testAsync() throws SecurityException, NoSuchMethodException, InterruptedException, ExecutionException {
      assert asyncClient.getVirtualGuestClient() != null;
      assert asyncClient.getDatacenterClient() != null;
      assert asyncClient.getProductPackageClient() != null;
   }

   @Override
   protected TypeLiteral<RestAnnotationProcessor<SoftLayerAsyncClient>> createTypeLiteral() {
      return new TypeLiteral<RestAnnotationProcessor<SoftLayerAsyncClient>>() {
      };
   }

   @BeforeClass
   @Override
   protected void setupFactory() throws IOException {
      super.setupFactory();
      asyncClient = injector.getInstance(SoftLayerAsyncClient.class);
      syncClient = injector.getInstance(SoftLayerClient.class);
   }

   @Override
   protected void checkFilters(HttpRequest request) {

   }
}
