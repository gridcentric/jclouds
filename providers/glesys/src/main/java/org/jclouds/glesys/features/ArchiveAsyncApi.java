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
package org.jclouds.glesys.features;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.jclouds.glesys.domain.Archive;
import org.jclouds.glesys.domain.ArchiveAllowedArguments;
import org.jclouds.http.filters.BasicAuthentication;
import org.jclouds.rest.annotations.ExceptionParser;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SelectJson;
import org.jclouds.rest.functions.ReturnEmptyFluentIterableOnNotFoundOr404;
import org.jclouds.rest.functions.ReturnNullOnNotFoundOr404;

import com.google.common.collect.FluentIterable;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * Provides asynchronous access to Archive data via the Glesys REST API.
 * <p/>
 *
 * @author Adam Lowe
 * @see ArchiveApi
 * @see <a href="https://customer.glesys.com/api.php" />
 */
@RequestFilters(BasicAuthentication.class)
public interface ArchiveAsyncApi {

   /**
    * @see ArchiveApi#list
    */
   @POST
   @Path("/archive/list/format/json")
   @SelectJson("archives")
   @Consumes(MediaType.APPLICATION_JSON)
   @ExceptionParser(ReturnEmptyFluentIterableOnNotFoundOr404.class)
   ListenableFuture<FluentIterable<Archive>> list();

   /**
    * @see ArchiveApi#get
    */
   @POST
   @Path("/archive/details/format/json")
   @SelectJson("details")
   @Consumes(MediaType.APPLICATION_JSON)
   @ExceptionParser(ReturnNullOnNotFoundOr404.class)
   ListenableFuture<Archive> get(@FormParam("username") String username);

   /**
    * @see ArchiveApi#createWithCredentialsAndSize
    */
   @POST
   @Path("/archive/create/format/json")
   @SelectJson("details")
   @Consumes(MediaType.APPLICATION_JSON)
   ListenableFuture<Archive> createWithCredentialsAndSize(@FormParam("username") String username, @FormParam("password") String password,
                                        @FormParam("size")int size);

   /**
    * @see ArchiveApi#delete
    */
   @POST
   @Path("/archive/delete/format/json")
   ListenableFuture<Void> delete(@FormParam("username") String username);

   /**
    * @see ArchiveApi#resize
    */
   @POST
   @Path("/archive/resize/format/json")
   @SelectJson("details")
   @Consumes(MediaType.APPLICATION_JSON)
   ListenableFuture<Archive> resize(@FormParam("username") String username, @FormParam("size") int size);
   /**
    * @see ArchiveApi#changePassword
    */
   @POST
   @Path("/archive/changepassword/format/json")
   @SelectJson("details")
   @Consumes(MediaType.APPLICATION_JSON)
   ListenableFuture<Archive> changePassword(@FormParam("username") String username, @FormParam("password") String password);

   /**
    * @see org.jclouds.glesys.features.ArchiveApi#getAllowedArguments
    */
   @GET
   @Path("/archive/allowedarguments/format/json")
   @SelectJson("argumentslist")
   @Consumes(MediaType.APPLICATION_JSON)
   @ExceptionParser(ReturnNullOnNotFoundOr404.class)
   ListenableFuture<ArchiveAllowedArguments> getAllowedArguments();

}
