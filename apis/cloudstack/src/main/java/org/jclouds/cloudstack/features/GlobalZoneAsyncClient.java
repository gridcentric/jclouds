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
package org.jclouds.cloudstack.features;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jclouds.cloudstack.domain.NetworkType;
import org.jclouds.cloudstack.domain.Zone;
import org.jclouds.cloudstack.filters.AuthenticationFilter;
import org.jclouds.cloudstack.options.CreateZoneOptions;
import org.jclouds.cloudstack.options.UpdateZoneOptions;
import org.jclouds.rest.annotations.ExceptionParser;
import org.jclouds.rest.annotations.QueryParams;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SelectJson;
import org.jclouds.rest.functions.ReturnNullOnNotFoundOr404;
import org.jclouds.rest.functions.ReturnVoidOnNotFoundOr404;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * Provides asynchronous access to CloudStack Account features available to Global
 * Admin users.
 *
 * @author Adrian Cole, Andrei Savu
 * @see <a href=
 *      "http://download.cloud.com/releases/2.2.0/api_2.2.12/TOC_Global_Admin.html"
 *      />
 */
@RequestFilters(AuthenticationFilter.class)
@QueryParams(keys = "response", values = "json")
public interface GlobalZoneAsyncClient extends ZoneAsyncClient {

   /**
    * @see GlobalZoneClient#createZone
    */
   @GET
   @QueryParams(keys = "command", values = "createZone")
   @SelectJson("zone")
   @Consumes(MediaType.APPLICATION_JSON)
   @ExceptionParser(ReturnNullOnNotFoundOr404.class)
   ListenableFuture<Zone> createZone(@QueryParam("name") String name, @QueryParam("networktype") NetworkType networkType,
      @QueryParam("dns1") String externalDns1, @QueryParam("internaldns1") String internalDns1, CreateZoneOptions... options);

   /**
    * @see GlobalZoneClient#updateZone
    */
   @GET
   @QueryParams(keys = "command", values = "updateZone")
   @SelectJson("zone")
   @Consumes(MediaType.APPLICATION_JSON)
   @ExceptionParser(ReturnNullOnNotFoundOr404.class)
   ListenableFuture<Zone> updateZone(@QueryParam("id") String id, UpdateZoneOptions... options);

   /**
    * @see GlobalZoneClient#deleteZone
    */
   @GET
   @QueryParams(keys = "command", values = "deleteZone")
   @Consumes(MediaType.APPLICATION_JSON)
   @ExceptionParser(ReturnVoidOnNotFoundOr404.class)
   ListenableFuture<Void> deleteZone(@QueryParam("id") String id);
}
