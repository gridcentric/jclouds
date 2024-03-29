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
package org.jclouds.jenkins.v1.features;

import java.util.concurrent.TimeUnit;

import org.jclouds.concurrent.Timeout;
import org.jclouds.jenkins.v1.domain.Computer;
import org.jclouds.jenkins.v1.domain.ComputerView;

/**
 * Computer Services
 * 
 * @see ComputerAsyncApi
 * @author Adrian Cole
 * @see <a href= "http://ci.jruby.org/computer/api/" >api doc</a>
 */
@Timeout(duration = 180, timeUnit = TimeUnit.SECONDS)
public interface ComputerApi {

   /**
    * @return overview of all configured computers
    */
   ComputerView getView();
   
   /**
    * 
    * @param displayName display name of the computer
    * @return computer or null if not found
    */
   Computer get(String displayName);
   
}
