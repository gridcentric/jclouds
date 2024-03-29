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
package org.jclouds.trmk.vcloud_0_8.functions;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.jclouds.logging.Logger;
import org.jclouds.trmk.vcloud_0_8.domain.Catalog;
import org.jclouds.trmk.vcloud_0_8.domain.CatalogItem;
import org.jclouds.trmk.vcloud_0_8.domain.Org;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;

/**
 * @author Adrian Cole
 */
@Singleton
public class AllCatalogItemsInOrg implements Function<Org, Iterable<? extends CatalogItem>> {

   @Resource
   public Logger logger = Logger.NULL;

   private final Function<Org, Iterable<? extends Catalog>> allCatalogsInOrg;

   private final Function<Catalog, Iterable<? extends CatalogItem>> allCatalogItemsInCatalog;

   @Inject
   AllCatalogItemsInOrg(Function<Org, Iterable<? extends Catalog>> allCatalogsInOrg,
            Function<Catalog, Iterable<? extends CatalogItem>> allCatalogItemsInCatalog) {
      this.allCatalogsInOrg = allCatalogsInOrg;
      this.allCatalogItemsInCatalog = allCatalogItemsInCatalog;
   }

   @Override
   public Iterable<? extends CatalogItem> apply(Org from) {
      return Iterables.concat(Iterables.transform(allCatalogsInOrg.apply(from),
               new Function<Catalog, Iterable<? extends CatalogItem>>() {
                  @Override
                  public Iterable<? extends CatalogItem> apply(Catalog from) {
                     return allCatalogItemsInCatalog.apply(from);
                  }

               }));
   }
}