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
import java.util.concurrent.TimeUnit;

import org.jclouds.concurrent.Timeout;
import org.jclouds.joyent.cloudapi.v6_5.domain.Key;

/**
 * Keys are the means by which you operate on your SSH/signing keys. Currently
 * CloudAPI supports uploads of public keys in the OpenSSH format.
 * 
 * @author Adrian Cole
 * @see KeyAsyncApi
 * @see <a href="http://apidocs.joyent.com/sdcapidoc/cloudapi/index.html#keys">api doc</a>
 */
@Timeout(duration = 30, timeUnit = TimeUnit.SECONDS)
public interface KeyApi {

   /**
    * Lists all public keys we have on record for the specified account.
    */
   Set<Key> list();

   /**
    * Retrieves an individual key record.
    */
   Key get(String name);

   /**
    * Uploads a new OpenSSH key to SmartDataCenter for use in SSH and HTTP
    * signing.
    */
   Key create(Key key);

   /**
    * Deletes an SSH key by name.
    */
   void delete(String name);
}
