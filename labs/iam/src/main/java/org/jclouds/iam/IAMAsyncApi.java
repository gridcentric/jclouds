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
package org.jclouds.iam;

import javax.inject.Named;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.jclouds.aws.filters.FormSigner;
import org.jclouds.iam.domain.User;
import org.jclouds.iam.features.InstanceProfileAsyncApi;
import org.jclouds.iam.features.RoleAsyncApi;
import org.jclouds.iam.features.RolePolicyAsyncApi;
import org.jclouds.iam.features.UserAsyncApi;
import org.jclouds.iam.xml.UserHandler;
import org.jclouds.rest.annotations.Delegate;
import org.jclouds.rest.annotations.FormParams;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.VirtualHost;
import org.jclouds.rest.annotations.XMLResponseParser;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * Provides access to Amazon IAM via the Query API
 * <p/>
 * 
 * @see <a href="http://docs.amazonwebservices.com/IAM/latest/APIReference" />
 * @author Adrian Cole
 */
@RequestFilters(FormSigner.class)
@VirtualHost
public interface IAMAsyncApi {

   /**
    * @see IAMApi#getCurrentUser()
    */
   @Named("iam:GetUser")
   @POST
   @Path("/")
   @XMLResponseParser(UserHandler.class)
   @FormParams(keys = "Action", values = "GetUser")
   ListenableFuture<User> getCurrentUser();

   /**
    * Provides asynchronous access to User features.
    */
   @Delegate
   UserAsyncApi getUserApi();

   /**
    * Provides asynchronous access to Role features.
    */
   @Delegate
   RoleAsyncApi getRoleApi();

   /**
    * Provides asynchronous access to Role Policy features.
    */
   @Delegate
   RolePolicyAsyncApi getPolicyApiForRole(@FormParam("RoleName") String roleName);

   /**
    * Provides asynchronous access to Instance Profile features.
    */
   @Delegate
   InstanceProfileAsyncApi getInstanceProfileApi();
}
