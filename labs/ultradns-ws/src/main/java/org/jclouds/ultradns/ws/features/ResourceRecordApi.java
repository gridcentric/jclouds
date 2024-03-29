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
package org.jclouds.ultradns.ws.features;

import java.util.concurrent.TimeUnit;

import org.jclouds.concurrent.Timeout;
import org.jclouds.rest.ResourceNotFoundException;
import org.jclouds.ultradns.ws.ResourceTypeToValue;
import org.jclouds.ultradns.ws.UltraDNSWSExceptions.ResourceAlreadyExistsException;
import org.jclouds.ultradns.ws.domain.ResourceRecord;
import org.jclouds.ultradns.ws.domain.ResourceRecordMetadata;

import com.google.common.collect.FluentIterable;
import com.google.common.primitives.UnsignedInteger;

/**
 * @see ResourceRecordAsyncApi
 * @author Adrian Cole
 */
@Timeout(duration = 30, timeUnit = TimeUnit.SECONDS)
public interface ResourceRecordApi {

   /**
    * creates a resource record in the zone.
    * 
    * @param toCreate
    *           the new record to create.
    * @return the {@code guid} of the new record
    * @throws ResourceAlreadyExistsException
    *            if a record already exists with the same attrs
    */
   String create(ResourceRecord toCreate) throws ResourceAlreadyExistsException;

   /**
    * updates an existing resource record in the zone.
    * 
    * @param guid
    *           the global unique identifier for the resource record {@see
    *           ResourceRecordMetadata#getGuid()}
    * @param updated
    *           the record to update.
    * @throws ResourceNotFoundException
    *            if the guid doesn't exist
    */
   void update(String guid, ResourceRecord updated) throws ResourceNotFoundException;

   /**
    * Returns all the specified record types in the zone.
    * 
    * @throws ResourceNotFoundException
    *            if the zone doesn't exist
    */
   FluentIterable<ResourceRecordMetadata> list() throws ResourceNotFoundException;

   /**
    * Returns all the specified record types in the zone with the fully
    * qualified {@link hostName}
    * 
    * @param hostName
    *           fully qualified hostname including the trailing dot.
    * @throws ResourceNotFoundException
    *            if the zone doesn't exist
    */
   FluentIterable<ResourceRecordMetadata> listByName(String hostName) throws ResourceNotFoundException;

   /**
    * Returns all the specified record types in the zone with the fully
    * qualified {@link hostName} and {@link rrType}
    * 
    * @param hostName
    *           fully qualified hostname including the trailing dot.
    * @param rrType
    *           type value (ex. for {@code A}, this is {@code 1}
    * 
    * @throws ResourceNotFoundException
    *            if the zone doesn't exist
    */
   FluentIterable<ResourceRecordMetadata> listByNameAndType(String hostName, UnsignedInteger rrType)
         throws ResourceNotFoundException;

   /**
    * @see #listByNameAndType(String, UnsignedInteger)
    */
   FluentIterable<ResourceRecordMetadata> listByNameAndType(String hostName, int rrType)
         throws ResourceNotFoundException;

   /**
    * @param type
    *           the literal type defined in {@link ResourceTypeToValue}. ex
    *           {@code AAAA}
    * @see #listByNameAndType(String, UnsignedInteger)
    */
   FluentIterable<ResourceRecordMetadata> listByNameAndType(String hostName, String type)
         throws ResourceNotFoundException;

   /**
    * deletes a specific resource record/
    * 
    * @param guid
    *           the global unique identifier for the resource record {@see
    *           ResourceRecordMetadata#getGuid()}
    */
   void delete(String guid);
}
