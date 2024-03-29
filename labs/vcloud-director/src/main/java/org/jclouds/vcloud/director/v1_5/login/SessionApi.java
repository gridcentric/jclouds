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
package org.jclouds.vcloud.director.v1_5.login;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.jclouds.concurrent.Timeout;
import org.jclouds.vcloud.director.v1_5.domain.Session;
import org.jclouds.vcloud.director.v1_5.domain.SessionWithToken;

/**
 * Provides synchronous access to Session.
 * <p/>
 * 
 * @see SessionAsyncApi
 * @author Adrian Cole
 */
@Timeout(duration = 180, timeUnit = TimeUnit.SECONDS)
public interface SessionApi {

   /**
    * TODO
    */
   SessionWithToken loginUserInOrgWithPassword(URI loginUrl, String user, String org, String password);

   /**
    * TODO
    */
   Session getSessionWithToken(URI session, String authenticationToken);

   /**
    * TODO
    */
   void logoutSessionWithToken(URI session, String authenticationToken);
}
