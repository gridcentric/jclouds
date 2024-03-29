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
package org.jclouds.openstack.cinder.v1.features;

import java.util.concurrent.TimeUnit;

import org.jclouds.concurrent.Timeout;
import org.jclouds.openstack.cinder.v1.domain.VolumeType;

import com.google.common.collect.FluentIterable;

/**
 * Provides synchronous access to Volumes via their REST API.
 * 
 * @see VolumeAsyncApi
 * @see <a href="http://api.openstack.org/">API Doc</a>
 * @author Everett Toews
 */
@Timeout(duration = 180, timeUnit = TimeUnit.SECONDS)
public interface VolumeTypeApi {
   /**
    * Returns a summary list of VolumeTypes.
    *
    * @return The list of VolumeTypes
    */
   FluentIterable<? extends VolumeType> list();

   /**
    * Return data about the given VolumeType.
    *
    * @param volumeTypeId Id of the VolumeType
    * @return Details of a specific VolumeType
    */
   VolumeType get(String volumeTypeId);
}
