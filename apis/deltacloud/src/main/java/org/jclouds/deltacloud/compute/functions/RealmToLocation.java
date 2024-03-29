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
package org.jclouds.deltacloud.compute.functions;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jclouds.deltacloud.domain.Realm;
import org.jclouds.domain.Location;
import org.jclouds.domain.LocationBuilder;
import org.jclouds.domain.LocationScope;
import org.jclouds.location.Iso3166;
import org.jclouds.location.Provider;

import com.google.common.base.Function;
import com.google.common.base.Supplier;

/**
 * @author Adrian Cole
 */
@Singleton
public class RealmToLocation implements Function<Realm, Location> {

   private final String providerName;
   private final Supplier<URI> endpoint;
   private final Set<String> isoCodes;

   @Inject
   public RealmToLocation(@Iso3166 Set<String> isoCodes, @Provider String providerName, @Provider Supplier<URI> endpoint) {
      this.providerName = checkNotNull(providerName, "providerName");
      this.endpoint = checkNotNull(endpoint, "endpoint");
      this.isoCodes = checkNotNull(isoCodes, "isoCodes");
   }

   @Override
   public Location apply(Realm from) {
      return new LocationBuilder().scope(LocationScope.ZONE).id(from.getHref().toASCIIString()).description(from.getName()).parent(
               new LocationBuilder().scope(LocationScope.PROVIDER).iso3166Codes(isoCodes).id(providerName).description(
                        endpoint.get().toASCIIString()).parent(null).build()).build();
   }
}
