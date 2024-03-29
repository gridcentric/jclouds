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
package org.jclouds.dynect.v3.domain.rdata;

import static com.google.common.base.Preconditions.checkNotNull;

import java.beans.ConstructorProperties;
import java.util.Map;

import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.UnsignedInteger;

/**
 * Corresponds to the binary representation of the {@code SRV} (Service) RData
 * 
 * <h4>Example</h4>
 * 
 * <pre>
 * SRVData rdata = SRVData.builder()
 *                        .priority(0)
 *                        .weight(1)
 *                        .port(80)
 *                        .target(&quot;www.foo.com.&quot;).build()
 * </pre>
 * 
 * @see <a href="http://www.ietf.org/rfc/rfc2782.txt">RFC 2782</a>
 */
public class SRVData extends ForwardingMap<String, Object> {

   @ConstructorProperties({ "priority", "weight", "port", "target" })
   private SRVData(UnsignedInteger priority, UnsignedInteger weight, UnsignedInteger port, String target) {
      this.delegate = ImmutableMap.<String, Object> builder()
            .put("priority", checkNotNull(priority, "priority of %s", target))
            .put("weight", checkNotNull(weight, "weight of %s", target))
            .put("port", checkNotNull(port, "port of %s", target))
            .put("target", checkNotNull(target, "target"))
            .build();
   }

   private final ImmutableMap<String, Object> delegate;

   protected Map<String, Object> delegate() {
      return delegate;
   }

   /**
    * The priority of this target host. A client MUST attempt to contact the
    * target host with the lowest-numbered priority it can reach; target hosts
    * with the same priority SHOULD be tried in an order defined by the weight
    * field.
    */
   public UnsignedInteger getPriority() {
      return UnsignedInteger.class.cast(get("priority"));
   }

   /**
    * The weight field specifies a relative weight for entries with the same
    * priority. Larger weights SHOULD be given a proportionately higher
    * probability of being selected.
    */
   public UnsignedInteger getWeight() {
      return UnsignedInteger.class.cast(get("weight"));
   }

   /**
    * The port on this target host of this service.
    */
   public UnsignedInteger getPort() {
      return UnsignedInteger.class.cast(get("port"));
   }

   /**
    * The domain name of the target host. There MUST be one or more address
    * records for this name, the name MUST NOT be an alias.
    */
   public String getTarget() {
      return String.class.cast(get("target"));
   }

   public static SRVData.Builder builder() {
      return new Builder();
   }

   public SRVData.Builder toBuilder() {
      return builder().from(this);
   }

   public final static class Builder {
      private UnsignedInteger priority;
      private UnsignedInteger weight;
      private UnsignedInteger port;
      private String target;

      /**
       * @see SRVData#getPriority()
       */
      public SRVData.Builder priority(UnsignedInteger priority) {
         this.priority = priority;
         return this;
      }

      /**
       * @see SRVData#getPriority()
       */
      public SRVData.Builder priority(int priority) {
         return priority(UnsignedInteger.valueOf(priority));
      }

      /**
       * @see SRVData#getWeight()
       */
      public SRVData.Builder weight(UnsignedInteger weight) {
         this.weight = weight;
         return this;
      }

      /**
       * @see SRVData#getWeight()
       */
      public SRVData.Builder weight(int weight) {
         return weight(UnsignedInteger.valueOf(weight));
      }

      /**
       * @see SRVData#getPort()
       */
      public SRVData.Builder port(UnsignedInteger port) {
         this.port = port;
         return this;
      }

      /**
       * @see SRVData#getPort()
       */
      public SRVData.Builder port(int port) {
         return port(UnsignedInteger.valueOf(port));
      }

      /**
       * @see SRVData#getTarget()
       */
      public SRVData.Builder target(String target) {
         this.target = target;
         return this;
      }

      public SRVData build() {
         return new SRVData(priority, weight, port, target);
      }

      public SRVData.Builder from(SRVData in) {
         return this.priority(in.getPriority()).weight(in.getWeight()).port(in.getPort()).target(in.getTarget());
      }
   }
}
