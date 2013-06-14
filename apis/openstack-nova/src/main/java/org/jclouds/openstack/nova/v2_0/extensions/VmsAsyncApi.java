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
package org.jclouds.openstack.nova.v2_0.extensions;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jclouds.openstack.keystone.v2_0.filters.AuthenticateRequest;
import org.jclouds.openstack.nova.v2_0.domain.VolumeAttachment;
import org.jclouds.openstack.nova.v2_0.domain.ServerCreated;
import org.jclouds.openstack.nova.v2_0.options.LaunchServerOptions;
import org.jclouds.openstack.v2_0.ServiceType;
import org.jclouds.openstack.v2_0.services.Extension;
import org.jclouds.rest.annotations.ExceptionParser;
import org.jclouds.rest.annotations.PayloadParam;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SelectJson;
import org.jclouds.rest.annotations.WrapWith;
import org.jclouds.rest.annotations.Unwrap;
import org.jclouds.rest.annotations.MapBinder;
import org.jclouds.rest.annotations.Payload;
import org.jclouds.rest.functions.ReturnEmptyFluentIterableOnNotFoundOr404;
import org.jclouds.rest.functions.ReturnFalseOnNotFoundOr404;
import org.jclouds.rest.functions.ReturnNullOnNotFoundOr404;

import com.google.common.annotations.Beta;
import com.google.common.collect.FluentIterable;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * Provides asynchronous access to Volume Attachments .
 * 
 * @see VolumeAttachmentApi
 * @author Everett Toews
 */
@Beta
@Extension(of = ServiceType.COMPUTE, namespace = ExtensionNamespaces.VMS)
@RequestFilters(AuthenticateRequest.class)
@Path("/servers/{server_id}/action")
public interface VmsAsyncApi {
   /**
    * @see Vms#liveImageCreate(String, String)
    */
   // @ExceptionParser(ReturnNullOnNotFoundOr404.class)
   @POST
   @WrapWith("gc_bless") 
   @Produces(MediaType.APPLICATION_JSON) // request
   @Unwrap // unwraps single entry json, eg {"foo":"bar"} becomes "bar", could use SelectJson instead
   @Consumes(MediaType.APPLICATION_JSON) // this is response, [ {} ] array of json tho
   ListenableFuture<ServerCreated> liveImageCreate(
         @PathParam("server_id") String serverId,
         @PayloadParam("name") String liveImageName);

   /**
    * @see Vms#liveImageStart(String, String, LaunchServerOptions...)
    */
   @POST
   // @Produces(MediaType.APPLICATION_JSON) // request
   @Consumes(MediaType.APPLICATION_JSON) // this is response, [ {} ] array of json tho
   // @ExceptionParser(ReturnNullOnNotFoundOr404.class)
   // @WrapWith("gc_launch") 
   @MapBinder(LaunchServerOptions.class)
   ListenableFuture<? extends FluentIterable<? extends ServerCreated>> liveImageStart(
         @PathParam("server_id") String serverId,
         @PayloadParam("name") String launchedName,
         LaunchServerOptions... options);

   /**
    * @see Vms#liveImageDelete(String)
    */
   @POST
   @Consumes
   @Produces(MediaType.APPLICATION_JSON)
   @Payload("{\"gc_discard\":null}")
   @ExceptionParser(ReturnFalseOnNotFoundOr404.class)
   ListenableFuture<Boolean> liveImageDelete(@PathParam("server_id") String serverId);
}
