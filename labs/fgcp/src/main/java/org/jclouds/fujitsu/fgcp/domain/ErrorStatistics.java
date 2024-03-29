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
package org.jclouds.fujitsu.fgcp.domain;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.ImmutableSet;

/**
 * Holds statistics of errors reported by a load balancer (SLB).
 * 
 * @author Dies Koper
 */
@XmlRootElement(name = "errorstatistics")
public class ErrorStatistics {
   private Period period;
   private Set<Group> groups = new LinkedHashSet<Group>();

   /**
    * @return the period
    */
   public Period getPeriod() {
      return period;
   }

   /**
    * @return the groups
    */
   public Set<Group> getGroups() {
      return groups == null ? ImmutableSet.<Group> of() : ImmutableSet
            .copyOf(groups);
   }

}
