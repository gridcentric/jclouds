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

import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.List;

import org.jclouds.http.HttpResponse;
import org.jclouds.http.functions.UnwrapOnlyJsonValue;
import org.jclouds.json.config.GsonModule;
import org.jclouds.openstack.nova.domain.FloatingIP;
import org.testng.annotations.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

/**
 * @author chamerling
 * 
 */
@Test(groups = "unit", testName = "ParseFloatingIPListFromJsonResponse")
public class ParseFloatingIPListFromJsonResponse {
   Injector i = Guice.createInjector(new GsonModule());

   @Test
   public void testParseFloatingIPListFromJsonResponseTest() throws UnknownHostException {
      InputStream is = getClass().getResourceAsStream("/test_list_floatingips.json");

      UnwrapOnlyJsonValue<List<FloatingIP>> parser = i.getInstance(Key
               .get(new TypeLiteral<UnwrapOnlyJsonValue<List<FloatingIP>>>() {
               }));
      List<FloatingIP> response = parser
               .apply(HttpResponse.builder().statusCode(200).message("ok").payload(is).build());

      assertEquals(response.size(), 1);
      FloatingIP floatingIP = FloatingIP.builder().id(1).ip("10.0.0.3").fixedIP("11.0.0.1").instanceID(12).build();
      assertEquals(response.get(0), floatingIP);
   }
}
