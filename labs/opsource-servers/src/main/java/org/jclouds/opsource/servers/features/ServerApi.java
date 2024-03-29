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
package org.jclouds.opsource.servers.features;

import java.util.concurrent.TimeUnit;

import org.jclouds.concurrent.Timeout;
import org.jclouds.opsource.servers.domain.DeployedServersList;
import org.jclouds.opsource.servers.domain.PendingDeployServersList;

/**
 * Provides synchronous access to server api's.
 * <p/>
 * 
 * @see ServerAsyncApi
 * @author Kedar Dave
 */
@Timeout(duration = 180, timeUnit = TimeUnit.SECONDS)
public interface ServerApi {
	
	/**
	 * @see ServerAsyncApi#getDeployedServers() 
	 */
	DeployedServersList getDeployedServers(String orgId);
	
	/**
	 * @see ServerAsyncApi#getPendingDeployServers() 
	 */
	PendingDeployServersList getPendingDeployServers(String orgId);
	
}
