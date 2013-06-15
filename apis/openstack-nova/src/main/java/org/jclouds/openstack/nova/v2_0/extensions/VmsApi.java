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
package org.jclouds.openstack.nova.v2_0.extensions;

import java.util.concurrent.TimeUnit;
import java.util.Set;

import org.jclouds.collect.PagedIterable;
import org.jclouds.concurrent.Timeout;
import org.jclouds.openstack.v2_0.ServiceType;
import org.jclouds.openstack.v2_0.services.Extension;
import org.jclouds.openstack.nova.v2_0.domain.ServerCreated;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.nova.v2_0.options.LaunchServerOptions; 

import com.google.common.annotations.Beta;
import com.google.common.collect.FluentIterable;

/**
 * Provide vms actions for servers:
 * 'live-image-create', 'live-image-start', 'live-image-delete'
 *
 * @author Rui Lin
 * @see org.jclouds.openstack.nova.v2_0.extensions.VmsAsyncApi
 */
@Beta
@Extension(of = ServiceType.COMPUTE, namespace = ExtensionNamespaces.VMS)
@Timeout(duration = 180, timeUnit = TimeUnit.SECONDS)
public interface VmsApi {
    
    /**
     * Create a live image
     *
     * @param serverId id of the server
     * @param liveImageName name of the live image
     *
     * @return the newly created live image
     */
    FluentIterable<? extends ServerCreated> liveImageCreate(String serverId, String liveImageName);

    /**
     * Start a live image
     *
     * @param serverId id of the server
     * @param launchedName name of the new instance
     * @param options optional params to be passed to live image start
     *
     * @return array of newly created instances
     */
    FluentIterable<? extends ServerCreated> liveImageStart(String serverId,
            String launchedName, LaunchServerOptions... options);

    /**
     * Delete a live image
     *
     * @param serverId id of the server
     */
    boolean liveImageDelete(String serverId);

    /**
     * Lists the live images of this instance
     *
     * @param serverId id of the server
     */
    FluentIterable<? extends Server> liveImageList(String serverId);


    /**
     * Lists the instance started from this live image
     *
     * @param serverId id of the server
     */
    FluentIterable<? extends Server> liveImageServers(String serverId);
}
