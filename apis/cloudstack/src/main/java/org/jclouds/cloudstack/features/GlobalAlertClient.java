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
package org.jclouds.cloudstack.features;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.jclouds.cloudstack.domain.Alert;
import org.jclouds.cloudstack.options.ListAlertsOptions;
import org.jclouds.concurrent.Timeout;

/**
 * Provides synchronous access to CloudStack Alerts features available to Global
 * Admin users.
 * 
 * @author Richard Downer
 * @see <a href=
 *      "http://download.cloud.com/releases/2.2.0/api_2.2.12/TOC_Global_Admin.html"
 *      />
 */
@Timeout(duration = 60, timeUnit = TimeUnit.SECONDS)
public interface GlobalAlertClient {

   /**
    * List Alerts
    *
    * @return alert list or null if not found
    */
   Set<Alert> listAlerts(ListAlertsOptions... options);

}
