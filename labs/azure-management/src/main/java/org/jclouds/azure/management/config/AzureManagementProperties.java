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
package org.jclouds.azure.management.config;

/**
 * Configuration properties and constants used in Azure Service Management
 * connections.
 * 
 * @author Gerald Pereira
 */
public class AzureManagementProperties {
	/**
	 * Every call to the Service Management API must include the subscription ID
	 * for your subscription. The subscription ID is appended to the base URI,
	 * as follows:
	 * 
	 * <pre>
	 * https://management.core.windows.net/${subscriptionId}
	 * </pre>
	 * 
	 * @see <a href="http://msdn.microsoft.com/en-us/library/ee460786">docs</a>
	 */
	public final static String SUBSCRIPTION_ID = "jclouds.azure.management.subscription-id";
}
