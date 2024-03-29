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
package org.jclouds.walrus.config;

import javax.inject.Singleton;

import org.jclouds.rest.ConfiguresRestClient;
import org.jclouds.s3.S3AsyncClient;
import org.jclouds.s3.S3Client;
import org.jclouds.s3.config.S3RestClientModule;
import org.jclouds.walrus.WalrusAsyncClient;

import com.google.common.reflect.TypeToken;
import com.google.inject.Provides;

/**
 * 
 * @author Adrian Cole
 */
@ConfiguresRestClient
public class WalrusRestClientModule extends S3RestClientModule<S3Client, WalrusAsyncClient> {
   public WalrusRestClientModule() {
      super(TypeToken.of(S3Client.class), TypeToken.of(WalrusAsyncClient.class));
   }

   @Provides
   @Singleton
   S3AsyncClient provideS3AsyncClient(WalrusAsyncClient in) {
      return in;
   }

}
