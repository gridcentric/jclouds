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
package org.jclouds.route53.config;

import java.util.Date;
import java.util.Map;

import javax.inject.Singleton;

import org.jclouds.aws.config.AWSRestClientModule;
import org.jclouds.date.DateService;
import org.jclouds.date.TimeStamp;
import org.jclouds.http.HttpErrorHandler;
import org.jclouds.http.annotation.ClientError;
import org.jclouds.http.annotation.Redirection;
import org.jclouds.http.annotation.ServerError;
import org.jclouds.rest.ConfiguresRestClient;
import org.jclouds.rest.RequestSigner;
import org.jclouds.route53.Route53Api;
import org.jclouds.route53.Route53AsyncApi;
import org.jclouds.route53.features.ResourceRecordSetApi;
import org.jclouds.route53.features.ResourceRecordSetAsyncApi;
import org.jclouds.route53.features.HostedZoneApi;
import org.jclouds.route53.features.HostedZoneAsyncApi;
import org.jclouds.route53.filters.RestAuthentication;
import org.jclouds.route53.handlers.Route53ErrorHandler;

import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.TypeToken;
import com.google.inject.Provides;

/**
 * Configures the Route53 connection.
 * 
 * @author Adrian Cole
 */
@ConfiguresRestClient
public class Route53RestClientModule extends AWSRestClientModule<Route53Api, Route53AsyncApi> {
   public static final Map<Class<?>, Class<?>> DELEGATE_MAP = ImmutableMap.<Class<?>, Class<?>> builder()//
         .put(HostedZoneApi.class, HostedZoneAsyncApi.class)
         .put(ResourceRecordSetApi.class, ResourceRecordSetAsyncApi.class).build();

   public Route53RestClientModule() {
      super(TypeToken.of(Route53Api.class), TypeToken.of(Route53AsyncApi.class), DELEGATE_MAP);
   }
   
   @Provides
   @TimeStamp
   protected String provideTimeStamp(DateService dateService) {
      return dateService.rfc1123DateFormat(new Date(System.currentTimeMillis()));
   }

   @Provides
   @Singleton
   RequestSigner provideRequestSigner(RestAuthentication in) {
      return in;
   }

   @Override
   protected void bindErrorHandlers() {
      bind(HttpErrorHandler.class).annotatedWith(Redirection.class).to(Route53ErrorHandler.class);
      bind(HttpErrorHandler.class).annotatedWith(ClientError.class).to(Route53ErrorHandler.class);
      bind(HttpErrorHandler.class).annotatedWith(ServerError.class).to(Route53ErrorHandler.class);
   }
}
