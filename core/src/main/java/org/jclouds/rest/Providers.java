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
package org.jclouds.rest;

import org.jclouds.Context;
import org.jclouds.View;
import org.jclouds.apis.Apis;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.common.reflect.TypeToken;

/**
 * @see org.jclouds.providers.Providers
 * @see org.jclouds.apis.Apis
 * @author Adrian Cole
 */
@Deprecated
public class Providers {

   /**
    * Gets a set of supported providers. Idea stolen from pallets (supported-clouds).
    */
   public static Iterable<String> getSupportedProviders() {
      return getSupportedProvidersOfType(TypeToken.of(View.class));
   }

   /**
    * Gets a set of supported providers. Idea stolen from pallets
    * (supported-clouds).
    * 
    */
   public static <C extends Context> Iterable<String> getSupportedProvidersOfType(TypeToken<? extends View> type) {
      Builder<String> builder = ImmutableSet.<String> builder();
      builder.addAll(Iterables.transform(Apis.viewableAs(type), Apis.idFunction()));
      builder.addAll(Iterables.transform(org.jclouds.providers.Providers.viewableAs(type),
            org.jclouds.providers.Providers.idFunction()));
      return builder.build();
   }


}
