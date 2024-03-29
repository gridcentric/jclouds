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
package org.jclouds.abiquo.predicates.infrastructure;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Arrays;

import org.jclouds.abiquo.domain.infrastructure.StorageDevice;

import com.google.common.base.Predicate;

/**
 * Container for {@link StorageDevice} filters.
 * 
 * @author Ignasi Barrera
 * @author Francesc Montserrat
 */
public class StorageDevicePredicates
{
    public static Predicate<StorageDevice> name(final String... names)
    {
        checkNotNull(names, "names must be defined");

        return new Predicate<StorageDevice>()
        {
            @Override
            public boolean apply(final StorageDevice storageDevice)
            {
                return Arrays.asList(names).contains(storageDevice.getName());
            }
        };
    }

    public static Predicate<StorageDevice> managementIp(final String... ips)
    {
        checkNotNull(ips, "managementIps must be defined");

        return new Predicate<StorageDevice>()
        {
            @Override
            public boolean apply(final StorageDevice storageDevice)
            {
                return Arrays.asList(ips).contains(storageDevice.getManagementIp());
            }
        };
    }

    public static Predicate<StorageDevice> type(final String... types)
    {
        checkNotNull(types, "types must be defined");

        return new Predicate<StorageDevice>()
        {
            @Override
            public boolean apply(final StorageDevice storageDevice)
            {
                return Arrays.asList(types).contains(storageDevice.getType());
            }
        };
    }
}
