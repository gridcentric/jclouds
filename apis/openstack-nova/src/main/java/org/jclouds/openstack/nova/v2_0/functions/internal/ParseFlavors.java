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
package org.jclouds.openstack.nova.v2_0.functions.internal;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.jclouds.openstack.v2_0.options.PaginationOptions.Builder.marker;

import java.beans.ConstructorProperties;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jclouds.collect.IterableWithMarker;
import org.jclouds.collect.internal.CallerArg0ToPagedIterable;
import org.jclouds.http.functions.ParseJson;
import org.jclouds.json.Json;
import org.jclouds.openstack.keystone.v2_0.domain.PaginatedCollection;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.features.FlavorApi;
import org.jclouds.openstack.nova.v2_0.functions.internal.ParseFlavors.Flavors;
import org.jclouds.openstack.v2_0.domain.Link;
import org.jclouds.openstack.v2_0.domain.Resource;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.inject.TypeLiteral;

/**
 * boiler plate until we determine a better way
 * 
 * @author Adrian Cole
 */
@Beta
@Singleton
public class ParseFlavors extends ParseJson<Flavors<? extends Resource>> {
   static class Flavors<T extends Resource> extends PaginatedCollection<T> {

      @ConstructorProperties({ "flavors", "flavors_links" })
      protected Flavors(Iterable<T> flavors, Iterable<Link> flavors_links) {
         super(flavors, flavors_links);
      }

   }

   @Inject
   public ParseFlavors(Json json) {
      super(json, new TypeLiteral<Flavors<? extends Resource>>() {
      });
   }

   public static class ToPagedIterable extends CallerArg0ToPagedIterable<Resource, ToPagedIterable> {

      private final NovaApi api;

      @Inject
      protected ToPagedIterable(NovaApi api) {
         this.api = checkNotNull(api, "api");
      }

      @Override
      protected Function<Object, IterableWithMarker<Resource>> markerToNextForCallingArg0(final String zone) {
         final FlavorApi flavorApi = api.getFlavorApiForZone(zone);
         return new Function<Object, IterableWithMarker<Resource>>() {

            @SuppressWarnings("unchecked")
            @Override
            public IterableWithMarker<Resource> apply(Object input) {
               return IterableWithMarker.class.cast(flavorApi.list(marker(input.toString())));
            }

            @Override
            public String toString() {
               return "listFlavors()";
            }
         };
      }

   }

}
