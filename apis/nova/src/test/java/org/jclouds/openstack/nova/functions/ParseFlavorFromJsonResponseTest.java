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
package org.jclouds.openstack.nova.functions;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;

import org.jclouds.http.HttpResponse;
import org.jclouds.http.functions.UnwrapOnlyJsonValue;
import org.jclouds.json.config.GsonModule;
import org.jclouds.openstack.nova.domain.Flavor;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

/**
 * Tests behavior of {@code ParseFlavorFromJsonResponse}
 * 
 * @author Adrian Cole
 */
@Test(groups = "unit")
public class ParseFlavorFromJsonResponseTest {

   @Test
   public void testParseFlavorFromJsonResponseTest() throws IOException {
      Flavor response = parseFlavor();

      String json = new Gson().toJson(response);
      assertNotNull(json);

      assertEquals(response.getId(), 1);
      assertEquals(response.getName(), "256 MB Server");
      assertEquals(response.getDisk().intValue(), 10);
      assertEquals(response.getRam().intValue(), 256);
      assertEquals(response.getVcpus().intValue(), 2);
   }

   public static Flavor parseFlavor() {
      Injector i = Guice.createInjector(new GsonModule());

      InputStream is = ParseFlavorFromJsonResponseTest.class.getResourceAsStream("/test_get_flavor_details.json");

      UnwrapOnlyJsonValue<Flavor> parser = i.getInstance(Key.get(new TypeLiteral<UnwrapOnlyJsonValue<Flavor>>() {
      }));
      return parser.apply(HttpResponse.builder().statusCode(200).message("ok").payload(is).build());
   }

}
