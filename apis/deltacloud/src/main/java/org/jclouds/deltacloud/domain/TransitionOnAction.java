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
package org.jclouds.deltacloud.domain;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 
 * @author Adrian Cole
 */
public class TransitionOnAction implements Transition {
   private final Instance.Action action;
   private final Instance.State to;

   public TransitionOnAction(Instance.Action action, Instance.State to) {
      this.to = checkNotNull(to, "to");
      this.action = checkNotNull(action, "action");
   }

   public Instance.State getTo() {
      return to;
   }

   public Instance.Action getAction() {
      return action;
   }

   @Override
   public String toString() {
      return "[action=" + action + ", to=" + to + "]";
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((action == null) ? 0 : action.hashCode());
      result = prime * result + ((to == null) ? 0 : to.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      TransitionOnAction other = (TransitionOnAction) obj;
      if (action == null) {
         if (other.action != null)
            return false;
      } else if (!action.equals(other.action))
         return false;
      if (to != other.to)
         return false;
      return true;
   }
}