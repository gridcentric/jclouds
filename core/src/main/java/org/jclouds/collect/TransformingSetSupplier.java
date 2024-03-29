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
package org.jclouds.collect;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Predicates.notNull;

import java.io.Serializable;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Supplier;
import com.google.common.collect.FluentIterable;

/**
 * 
 * @author Adrian Cole
 */
public class TransformingSetSupplier<F, T> implements Supplier<Set<? extends T>>, Serializable {
   /** The serialVersionUID */
   private static final long serialVersionUID = -8747953419394840218L;
   
   private final Supplier<Iterable<F>> backingSupplier;
   private final Function<F, T> converter;

   public TransformingSetSupplier(Supplier<Iterable<F>> backingSupplier, Function<F, T> converter) {
      this.backingSupplier = checkNotNull(backingSupplier, "backingSupplier");
      this.converter = checkNotNull(converter, "converter");
   }

   @Override
   public Set<? extends T> get() {
      Iterable<F> original = backingSupplier.get();
      return FluentIterable.from(original)
                           .filter(notNull())
                           .transform(converter)
                           .filter(notNull())
                           .toImmutableSet();
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(backingSupplier, converter);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      TransformingSetSupplier<?, ?> that = TransformingSetSupplier.class.cast(obj);
      return Objects.equal(backingSupplier, that.backingSupplier) && Objects.equal(converter, that.converter);
   }

   @Override
   public String toString() {
      return Objects.toStringHelper(this).add("backingSupplier", backingSupplier).add("converter", converter).toString();
   }
}
