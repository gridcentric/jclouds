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

import static com.google.common.collect.Iterables.filter;
import static org.jclouds.concurrent.FutureIterables.transformParallel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.jclouds.Constants;
import org.jclouds.compute.reference.ComputeServiceConstants;
import org.jclouds.logging.Logger;
import org.jclouds.trmk.vcloud_0_8.TerremarkVCloudAsyncClient;
import org.jclouds.trmk.vcloud_0_8.TerremarkVCloudMediaType;
import org.jclouds.trmk.vcloud_0_8.domain.CatalogItem;
import org.jclouds.trmk.vcloud_0_8.domain.VAppTemplate;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

/**
 * @author Adrian Cole
 */
@Singleton
public class VAppTemplatesForCatalogItems implements
      Function<Iterable<? extends CatalogItem>, Iterable<? extends VAppTemplate>> {
   @Resource
   @Named(ComputeServiceConstants.COMPUTE_LOGGER)
   public Logger logger = Logger.NULL;
   private final TerremarkVCloudAsyncClient aclient;
   private final ExecutorService executor;

   @Inject
   VAppTemplatesForCatalogItems(TerremarkVCloudAsyncClient aclient,
         @Named(Constants.PROPERTY_USER_THREADS) ExecutorService executor) {
      this.aclient = aclient;
      this.executor = executor;
   }

   @Override
   public Iterable<? extends VAppTemplate> apply(Iterable<? extends CatalogItem> from) {
      return transformParallel(filter(from, new Predicate<CatalogItem>() {

         @Override
         public boolean apply(CatalogItem input) {
            return input.getEntity().getType().equals(TerremarkVCloudMediaType.VAPPTEMPLATE_XML);
         }

      }), new Function<CatalogItem, Future<? extends VAppTemplate>>() {
         @Override
         public Future<? extends VAppTemplate> apply(CatalogItem from) {
            return aclient.getVAppTemplate(from.getEntity().getHref());
         }

      }, executor, null, logger, "vappTemplates in");
   }

}