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
package org.jclouds.vcloud.director.v1_5.features;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.jclouds.concurrent.Timeout;
import org.jclouds.rest.annotations.Delegate;
import org.jclouds.rest.annotations.EndpointParam;
import org.jclouds.vcloud.director.v1_5.domain.Metadata;
import org.jclouds.vcloud.director.v1_5.domain.org.Org;
import org.jclouds.vcloud.director.v1_5.domain.org.OrgList;
import org.jclouds.vcloud.director.v1_5.functions.href.OrgURNToHref;

/**
 * Provides synchronous access to {@link Org}.
 * 
 * @see OrgAsyncApi
 * @author Adrian Cole
 */
@Timeout(duration = 180, timeUnit = TimeUnit.SECONDS)
public interface OrgApi {

   /**
    * Retrieves a list of organizations.
    * 
    * <pre>
    * GET / org
    * </pre>
    * 
    * @return a list of organizations
    */
   OrgList list();

   /**
    * Retrieves an organization.
    * 
    * <pre>
    * GET /org/{id}
    * </pre>
    * 
    * @return the org or null if not found
    */
   Org get(String orgUrn);

   Org get(URI orgHref);

   /**
    * @return synchronous access to {@link Metadata.Readable} features
    */
   @Delegate
   MetadataApi.Readable getMetadataApi(@EndpointParam(parser = OrgURNToHref.class) String orgUrn);
   
   @Delegate
   MetadataApi.Readable getMetadataApi(@EndpointParam URI orgHref);

}
