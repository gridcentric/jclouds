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
package org.jclouds.joyent.cloudapi.v6_5.features;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jclouds.http.filters.BasicAuthentication;
import org.jclouds.joyent.cloudapi.v6_5.domain.Machine;
import org.jclouds.joyent.cloudapi.v6_5.options.CreateMachineOptions;
import org.jclouds.rest.annotations.ExceptionParser;
import org.jclouds.rest.annotations.Headers;
import org.jclouds.rest.annotations.Payload;
import org.jclouds.rest.annotations.PayloadParam;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.functions.ReturnEmptySetOnNotFoundOr404;
import org.jclouds.rest.functions.ReturnNullOnNotFoundOr404;
import org.jclouds.rest.functions.ReturnVoidOnNotFoundOr404;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * Provides asynchronous access to Machine via their REST API.
 * <p/>
 * 
 * @author Gerald Pereira
 * @see MachineApi
 * @see <a href="http://apidocs.joyent.com/sdcapidoc/cloudapi/index.html#machines">api doc</a>
 */
@Headers(keys = "X-Api-Version", values = "{jclouds.api-version}")
@RequestFilters(BasicAuthentication.class)
public interface MachineAsyncApi {

   /**
    * @see MachineApi#list
    */
   @GET
   @Path("/my/machines")
   @Consumes(MediaType.APPLICATION_JSON)
   @ExceptionParser(ReturnEmptySetOnNotFoundOr404.class)
   ListenableFuture<Set<Machine>> list();

   /**
    * @see MachineApi#get
    */
   @GET
   @Path("/my/machines/{id}")
   @Consumes(MediaType.APPLICATION_JSON)
   @ExceptionParser(ReturnNullOnNotFoundOr404.class)
   ListenableFuture<Machine> get(@PathParam("id") String id);

   /**
    * @see MachineApi#createWithDataset(String)
    */
   @POST
   @Path("/my/machines")
   @Consumes(MediaType.APPLICATION_JSON)
   ListenableFuture<Machine> createWithDataset(@QueryParam("dataset") String datasetURN);

   /**
    * @see MachineApi#createWithDataset(String, CreateMachineOptions)
    */
   @POST
   @Path("/my/machines")
   @Consumes(MediaType.APPLICATION_JSON)
   ListenableFuture<Machine> createWithDataset(@QueryParam("dataset") String datasetURN, CreateMachineOptions options);

   /**
    * @see MachineApi#stop
    */
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_FORM_URLENCODED)
   @Path("/my/machines/{id}")
   @Payload("action=stop")
   ListenableFuture<Void> stop(@PathParam("id") String id);

   /**
    * @see MachineApi#start
    */
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_FORM_URLENCODED)
   @Path("/my/machines/{id}")
   @Payload("action=start")
   ListenableFuture<Void> start(@PathParam("id") String id);

   /**
    * @see MachineApi#reboot
    */
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_FORM_URLENCODED)
   @Path("/my/machines/{id}")
   @Payload("action=reboot")
   ListenableFuture<Void> reboot(@PathParam("id") String id);

   /**
    * @see MachineApi#resize
    */
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_FORM_URLENCODED)
   @Path("/my/machines/{id}")
   @Payload("action=resize&package={package}")
   ListenableFuture<Void> resize(@PathParam("id") String id, @PayloadParam("package") String packageJoyentCloud);

   /**
    * @see MachineApi#delete
    */
   @DELETE
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/my/machines/{id}")
   @ExceptionParser(ReturnVoidOnNotFoundOr404.class)
   ListenableFuture<Void> delete(@PathParam("id") String id);

}
