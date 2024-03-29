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
package org.jclouds.openstack.nova.ec2.services;

import static org.jclouds.aws.reference.FormParameters.ACTION;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.jclouds.aws.filters.FormSigner;
import org.jclouds.ec2.domain.KeyPair;
import org.jclouds.ec2.functions.EncodedRSAPublicKeyToBase64;
import org.jclouds.ec2.services.KeyPairAsyncClient;
import org.jclouds.ec2.xml.KeyPairResponseHandler;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.location.functions.RegionToEndpointOrProviderIfNull;
import org.jclouds.rest.annotations.EndpointParam;
import org.jclouds.rest.annotations.FormParams;
import org.jclouds.rest.annotations.ParamParser;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.VirtualHost;
import org.jclouds.rest.annotations.XMLResponseParser;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * @author Adrian Cole
 */
@RequestFilters(FormSigner.class)
@VirtualHost
public interface NovaEC2KeyPairAsyncClient extends KeyPairAsyncClient {

   /**
    * @see NovaEC2KeyPairClient#importKeyPairInRegion(String, String, String)
    */
   @POST
   @Path("/")
   @FormParams(keys = ACTION, values = "ImportKeyPair")
   @XMLResponseParser(KeyPairResponseHandler.class)
   ListenableFuture<KeyPair> importKeyPairInRegion(
         @EndpointParam(parser = RegionToEndpointOrProviderIfNull.class) @Nullable String region,
         @FormParam("KeyName") String keyName,
         @FormParam("PublicKeyMaterial") @ParamParser(EncodedRSAPublicKeyToBase64.class) String publicKeyMaterial);
}
