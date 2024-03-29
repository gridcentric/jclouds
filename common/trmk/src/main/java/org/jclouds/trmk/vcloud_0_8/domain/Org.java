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
package org.jclouds.trmk.vcloud_0_8.domain;

import java.util.Map;

import org.jclouds.javax.annotation.Nullable;
import org.jclouds.trmk.vcloud_0_8.domain.internal.OrgImpl;
import org.jclouds.trmk.vcloud_0_8.endpoints.Keys;

import com.google.inject.ImplementedBy;

/**
 * A vCloud organization is a high-level abstraction that provides a unit of
 * administration for objects and resources. As viewed by a user, an
 * organization (represented by an Org element) can contain Catalog, Network,
 * and vDC elements. If there are any queued, running, or recently completed
 * tasks owned by a member of the organization, it also contains a TasksList
 * element. As viewed by an administrator, an organization also contains users,
 * groups, and other information
 * 
 * @author Adrian Cole
 */
@ImplementedBy(OrgImpl.class)
public interface Org extends ReferenceType {
   /**
    * optional description
    * 
    * @since vcloud api 0.8
    */
   @Nullable
   String getDescription();

   /**
    * @since vcloud api 0.8
    */
   Map<String, ReferenceType> getCatalogs();

   /**
    * @since vcloud api 0.8
    */
   Map<String, ReferenceType> getVDCs();

   /**
    * If there are any queued, running, or recently completed tasks owned by a
    * member of the organization, it also contains a TasksList.
    * 
    * there are multiple tasks lists in a terremark org
    * 
    */
   Map<String, ReferenceType> getTasksLists();

   @Keys
   ReferenceType getKeys();

}