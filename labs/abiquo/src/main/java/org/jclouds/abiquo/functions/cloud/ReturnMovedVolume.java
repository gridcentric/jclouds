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
package org.jclouds.abiquo.functions.cloud;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jclouds.abiquo.functions.ReturnMovedResource;
import org.jclouds.http.HttpResponse;

import com.abiquo.server.core.infrastructure.storage.VolumeManagementDto;

/**
 * Return false on service error exceptions.
 * 
 * @author Ignasi Barrera
 */
@Singleton
public class ReturnMovedVolume extends ReturnMovedResource<VolumeManagementDto>
{
    private ReturnMoveVolumeReference parser;

    @Inject
    public ReturnMovedVolume(final ReturnMoveVolumeReference parser)
    {
        super();
        this.parser = parser;
    }

    @Override
    protected VolumeManagementDto getMovedEntity(final HttpResponse response)
    {
        return parser.apply(response).getVolume();
    }
}
