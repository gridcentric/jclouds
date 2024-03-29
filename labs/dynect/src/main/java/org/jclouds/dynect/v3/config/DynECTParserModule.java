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
package org.jclouds.dynect.v3.config;

import java.lang.reflect.Type;
import java.util.Map;

import javax.inject.Singleton;

import org.jclouds.dynect.v3.domain.SessionCredentials;

import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.UnsignedInteger;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * @author Adrian Cole
 */
public class DynECTParserModule extends AbstractModule {

   @Override
   protected void configure() {
   }

   @Provides
   @Singleton
   public Map<Type, Object> provideCustomAdapterBindings() {
      return new ImmutableMap.Builder<Type, Object>()
              .put(SessionCredentials.class, new SessionCredentialsTypeAdapter())
              .put(UnsignedInteger.class, new UnsignedIntegerAdapter())
              .build();
   }

   private static class SessionCredentialsTypeAdapter implements JsonSerializer<SessionCredentials> {
      public JsonElement serialize(SessionCredentials src, Type typeOfSrc, JsonSerializationContext context) {
         JsonObject metadataObject = new JsonObject();
         metadataObject.addProperty("customer_name", src.getCustomerName());
         metadataObject.addProperty("user_name", src.getUserName());
         metadataObject.addProperty("password", src.getPassword());
         return metadataObject;
      }
   }

   private static class UnsignedIntegerAdapter implements JsonDeserializer<UnsignedInteger> {
      public UnsignedInteger deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
            throws JsonParseException {
         return UnsignedInteger.valueOf(jsonElement.getAsBigInteger().intValue());
      }
   }
}
