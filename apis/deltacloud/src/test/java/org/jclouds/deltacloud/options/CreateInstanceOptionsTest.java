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
package org.jclouds.deltacloud.options;

import static org.jclouds.deltacloud.options.CreateInstanceOptions.Builder.named;
import static org.testng.Assert.assertEquals;

import java.util.Collections;

import org.jclouds.http.options.HttpRequestOptions;
import org.testng.annotations.Test;

/**
 * Tests possible uses of CreateInstanceOptions and CreateInstanceOptions.Builder.*
 * 
 * @author Adrian Cole
 */
public class CreateInstanceOptionsTest {

   @Test
   public void testAssignability() {
      assert HttpRequestOptions.class.isAssignableFrom(CreateInstanceOptions.class);
      assert !String.class.isAssignableFrom(CreateInstanceOptions.class);
   }

   @Test
   public void testWithNamed() {
      CreateInstanceOptions options = new CreateInstanceOptions();
      options.named("test");
      assertEquals(options.buildFormParameters().get("name"), Collections.singletonList("test"));
   }

   @Test
   public void testNullWithNamed() {
      CreateInstanceOptions options = new CreateInstanceOptions();
      assertEquals(options.buildFormParameters().get("name"), Collections.EMPTY_LIST);
   }

   @Test
   public void testWithNamedStatic() {
      CreateInstanceOptions options = named("test");
      assertEquals(options.buildFormParameters().get("name"), Collections.singletonList("test"));
   }

   @Test(expectedExceptions = NullPointerException.class)
   public void testWithNamedNPE() {
      named(null);
   }

}
