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
package org.jclouds.azure.management.features;

import java.util.concurrent.TimeUnit;

import org.jclouds.azure.management.domain.Operation;
import org.jclouds.concurrent.Timeout;

/**
 * The Service Management API includes one operation for tracking the progress of asynchronous requests.
 * 
 * @see <a href="http://msdn.microsoft.com/en-us/library/ee460796">docs</a>
 * @see OperationAsyncApi
 * @author Gerald Pereira
 */
@Timeout(duration = 30, timeUnit = TimeUnit.SECONDS)
public interface OperationApi {

   Operation get(String requestId);

}
