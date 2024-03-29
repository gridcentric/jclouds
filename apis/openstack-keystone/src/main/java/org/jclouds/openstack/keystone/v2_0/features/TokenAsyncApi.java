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
package org.jclouds.openstack.keystone.v2_0.features;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.jclouds.openstack.keystone.v2_0.domain.Endpoint;
import org.jclouds.openstack.keystone.v2_0.domain.Token;
import org.jclouds.openstack.keystone.v2_0.domain.User;
import org.jclouds.openstack.keystone.v2_0.filters.AuthenticateRequest;
import org.jclouds.openstack.v2_0.services.Identity;
import org.jclouds.rest.annotations.ExceptionParser;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SelectJson;
import org.jclouds.rest.functions.ReturnEmptySetOnNotFoundOr404;
import org.jclouds.rest.functions.ReturnFalseOnNotFoundOr404;
import org.jclouds.rest.functions.ReturnNullOnNotFoundOr404;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * Provides asynchronous access to Token via their REST API.
 * <p/>
 *
 * @see TokenApi
 * @see <a href=
 *       "http://docs.openstack.org/api/openstack-identity-service/2.0/content/Token_Operations.html"
 *      />
 * @author Adam Lowe
 */
@org.jclouds.rest.annotations.Endpoint(Identity.class)
public interface TokenAsyncApi {

   
   /** @see TokenApi#get(String) */
   @GET
   @SelectJson("token")
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/tokens/{token}")
   @RequestFilters(AuthenticateRequest.class)
   @ExceptionParser(ReturnNullOnNotFoundOr404.class)
   ListenableFuture<? extends Token> get(@PathParam("token") String token);

   /** @see TokenApi#getUserOfToken(String) */
   @GET
   @SelectJson("user")
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/tokens/{token}")
   @RequestFilters(AuthenticateRequest.class)
   @ExceptionParser(ReturnNullOnNotFoundOr404.class)
   ListenableFuture<? extends User> getUserOfToken(@PathParam("token") String token);

   /** @see TokenApi#isValid(String) */
   @HEAD
   @Path("/tokens/{token}")
   @RequestFilters(AuthenticateRequest.class)
   @ExceptionParser(ReturnFalseOnNotFoundOr404.class)
   ListenableFuture<Boolean> isValid(@PathParam("token") String token);

   /** @see TokenApi#listEndpointsForToken(String) */
   @GET
   @SelectJson("endpoints")
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/tokens/{token}/endpoints")
   @RequestFilters(AuthenticateRequest.class)
   @ExceptionParser(ReturnEmptySetOnNotFoundOr404.class)
   ListenableFuture<? extends Set<? extends Endpoint>> listEndpointsForToken(@PathParam("token") String token);

}
