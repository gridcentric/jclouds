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

import org.jclouds.cloudstack.domain.Domain;
import org.jclouds.cloudstack.filters.AuthenticationFilter;
import org.jclouds.cloudstack.options.CreateDomainOptions;
import org.jclouds.cloudstack.options.UpdateDomainOptions;
import org.jclouds.rest.annotations.ExceptionParser;
import org.jclouds.rest.annotations.QueryParams;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SelectJson;
import org.jclouds.rest.functions.ReturnNullOnNotFoundOr404;
import org.jclouds.rest.functions.ReturnVoidOnNotFoundOr404;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * Provides asynchronous access to CloudStack Domain features available to Global
 * Admin users.
 *
 * @author Andrei Savu
 * @see <a href=
 *      "http://download.cloud.com/releases/2.2.0/api_2.2.12/TOC_Global_Admin.html"
 *      />
 */
@RequestFilters(AuthenticationFilter.class)
@QueryParams(keys = "response", values = "json")
public interface GlobalDomainAsyncClient extends DomainDomainAsyncClient {

   /**
    * @see GlobalDomainClient#createDomain
    */
   @GET
   @QueryParams(keys = "command", values = "createDomain")
   @SelectJson("domain")
   @Consumes(MediaType.APPLICATION_JSON)
   @ExceptionParser(ReturnNullOnNotFoundOr404.class)
   ListenableFuture<Domain> createDomain(@QueryParam("name") String name, CreateDomainOptions... options);

   /**
    * @see GlobalDomainClient#updateDomain
    */
   @GET
   @QueryParams(keys = "command", values = "updateDomain")
   @SelectJson("domain")
   @Consumes(MediaType.APPLICATION_JSON)
   @ExceptionParser(ReturnNullOnNotFoundOr404.class)
   ListenableFuture<Domain> updateDomain(@QueryParam("id") String domainId, UpdateDomainOptions... options);

   /**
    * @see GlobalDomainClient#deleteOnlyDomain
    */
   @GET
   @QueryParams(keys = {"command", "cleanup"}, values = {"deleteDomain", "false"})
   @ExceptionParser(ReturnVoidOnNotFoundOr404.class)
   ListenableFuture<Void> deleteOnlyDomain(@QueryParam("id") String id);

   /**
    * @see GlobalDomainClient#deleteDomainAndAttachedResources
    */
   @GET
   @QueryParams(keys = {"command", "cleanup"}, values = {"deleteDomain", "true"})
   @ExceptionParser(ReturnVoidOnNotFoundOr404.class)
   ListenableFuture<Void> deleteDomainAndAttachedResources(@QueryParam("id") String id);
}

