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
package org.jclouds.softlayer.features;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.jclouds.concurrent.Timeout;
import org.jclouds.softlayer.domain.ProductPackage;

/**
 * Provides synchronous access to Account.
 * <p/>
 * 
 * @see AccountAsyncClient
 * @see <a href="http://sldn.softlayer.com/article/REST" />
 * @author Jason King
 */
@Timeout(duration = 90, timeUnit = TimeUnit.SECONDS)
public interface AccountClient {

   /**
    * 
    * @return Gets all the active packages.
    * This will give you a basic description of the packages that are currently
    * active and from which you can order a server or additional services.
    *
    * Calling ProductPackage.getItems() will return an empty set.
    * Use ProductPackageClient.getProductPackage(long id) to obtain items data.
    * @see ProductPackageClient#getProductPackage
    */
   Set<ProductPackage> getActivePackages();

}
