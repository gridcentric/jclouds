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

import org.jclouds.cloudstack.domain.ApiKeyPair;
import org.jclouds.cloudstack.domain.User;
import org.jclouds.cloudstack.filters.AuthenticationFilter;
import org.jclouds.cloudstack.options.CreateUserOptions;
import org.jclouds.cloudstack.options.UpdateUserOptions;
import org.jclouds.rest.annotations.ExceptionParser;
import org.jclouds.rest.annotations.QueryParams;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SelectJson;
import org.jclouds.rest.functions.ReturnNullOnNotFoundOr404;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * Provides asynchronous access to CloudStack User features available to Global
 * Admin users.
 *
 * @author Andrei Savu
 * @see <a href=
 *      "http://download.cloud.com/releases/2.2.0/api_2.2.12/TOC_Global_Admin.html"
 *      />
 */
@RequestFilters(AuthenticationFilter.class)
@QueryParams(keys = "response", values = "json")
public interface GlobalUserAsyncClient extends DomainUserAsyncClient {

   /**
    * @see GlobalUserClient#createUser
    */
   @GET
   @QueryParams(keys = "command", values = "createUser")
   @SelectJson("user")
   @Consumes(MediaType.APPLICATION_JSON)
   @ExceptionParser(ReturnNullOnNotFoundOr404.class)
   ListenableFuture<User>createUser(@QueryParam("username") String userName, @QueryParam("account") String accountName,
      @QueryParam("email") String email, @QueryParam("password") String hashedPassword,
      @QueryParam("firstname") String firstName, @QueryParam("lastname") String lastName, CreateUserOptions... options);

   /**
    * @see GlobalUserClient#registerUserKeys
    */
   @GET
   @QueryParams(keys = "command", values = "registerUserKeys")
   @SelectJson("userkeys")
   @Consumes(MediaType.APPLICATION_JSON)
   @ExceptionParser(ReturnNullOnNotFoundOr404.class)
   ListenableFuture<ApiKeyPair> registerUserKeys(@QueryParam("id") String userId);

   /**
    * @see GlobalUserClient#updateUser
    */
   @GET
   @QueryParams(keys = "command", values = "updateUser")
   @SelectJson("user")
   @Consumes(MediaType.APPLICATION_JSON)
   @ExceptionParser(ReturnNullOnNotFoundOr404.class)
   ListenableFuture<User> updateUser(@QueryParam("id") String id, UpdateUserOptions... options);

   /**
    * @see GlobalUserClient#deleteUser
    */
   @GET
   @QueryParams(keys = "command", values = "deleteUser")
   @Consumes(MediaType.APPLICATION_JSON)
   @ExceptionParser(ReturnNullOnNotFoundOr404.class)
   ListenableFuture<Void> deleteUser(@QueryParam("id") String id);
}
