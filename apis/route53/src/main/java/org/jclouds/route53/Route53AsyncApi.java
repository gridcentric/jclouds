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
package org.jclouds.route53;

import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.jclouds.rest.annotations.Delegate;
import org.jclouds.rest.annotations.ExceptionParser;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.VirtualHost;
import org.jclouds.rest.annotations.XMLResponseParser;
import org.jclouds.rest.functions.ReturnNullOnNotFoundOr404;
import org.jclouds.route53.domain.Change;
import org.jclouds.route53.features.ResourceRecordSetAsyncApi;
import org.jclouds.route53.features.HostedZoneAsyncApi;
import org.jclouds.route53.filters.RestAuthentication;
import org.jclouds.route53.xml.ChangeHandler;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * Provides access to Amazon Route53 via the Query API
 * <p/>
 * 
 * @see <a href="http://docs.amazonwebservices.com/Route53/latest/APIReference"
 *      />
 * @author Adrian Cole
 */
@RequestFilters(RestAuthentication.class)
@VirtualHost
@Path("/{jclouds.api-version}")
public interface Route53AsyncApi {

   /**
    * @see Route53Api#getChange()
    */
   @Named("GetChange")
   @GET
   @Path("/change/{changeId}")
   @XMLResponseParser(ChangeHandler.class)
   @ExceptionParser(ReturnNullOnNotFoundOr404.class)
   ListenableFuture<Change> getChange(@PathParam("changeId") String changeID);

   /**
    * Provides asynchronous access to Zone features.
    */
   @Delegate
   HostedZoneAsyncApi getHostedZoneApi();

   /**
    * Provides asynchronous access to record set features.
    */
   @Delegate
   ResourceRecordSetAsyncApi getResourceRecordSetApiForHostedZone(@PathParam("zoneId") String zoneId);
}
