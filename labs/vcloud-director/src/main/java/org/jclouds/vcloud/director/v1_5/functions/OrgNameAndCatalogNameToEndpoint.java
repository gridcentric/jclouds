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
package org.jclouds.vcloud.director.v1_5.functions;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.jclouds.vcloud.director.v1_5.predicates.ReferencePredicates.nameEquals;

import java.net.URI;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jclouds.vcloud.director.v1_5.domain.Reference;
import org.jclouds.vcloud.director.v1_5.domain.org.AdminOrg;
import org.jclouds.vcloud.director.v1_5.endpoints.Catalog;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.collect.Iterables;

/**
 * 
 * @author danikov
 */
@Singleton
public class OrgNameAndCatalogNameToEndpoint implements Function<Object, URI> {
   private final Supplier<Map<String, AdminOrg>> orgMap;
   private final Supplier<Reference> defaultOrg;
   private final Supplier<Reference> defaultCatalog;

   @Inject
   public OrgNameAndCatalogNameToEndpoint(Supplier<Map<String, AdminOrg>> orgMap,
         @org.jclouds.vcloud.director.v1_5.endpoints.Org Supplier<Reference> defaultOrg,
         @Catalog Supplier<Reference> defaultCatalog) {
      this.orgMap = orgMap;
      this.defaultOrg = defaultOrg;
      this.defaultCatalog = defaultCatalog;
   }

   @SuppressWarnings("unchecked")
   public URI apply(Object from) {
      Iterable<Object> orgCatalog = (Iterable<Object>) checkNotNull(from, "args");
      Object org = Iterables.get(orgCatalog, 0);
      Object catalog = Iterables.get(orgCatalog, 1);
      if (org == null && catalog == null)
         return defaultCatalog.get().getHref();
      else if (org == null)
         org = defaultOrg.get().getName();

      try {
         Set<Reference> catalogs = checkNotNull(orgMap.get().get(org)).getCatalogs();
         return catalog == null ? Iterables.getLast(catalogs).getHref() : 
            Iterables.find(catalogs, nameEquals((String)catalog)).getHref();
      } catch (NullPointerException e) {
         throw new NoSuchElementException(org + "/" + catalog + " not found in " + orgMap.get());
      }
   }

}
