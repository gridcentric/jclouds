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
package org.jclouds.io;

import java.io.InputStream;
import java.util.Map;

import javax.inject.Inject;

import org.jclouds.collect.InputSupplierMap;

import com.google.common.annotations.Beta;
import com.google.common.io.InputSupplier;

/**
 * 
 * @author Adrian Cole
 */
@Beta
public class CopyInputStreamInputSupplierMap extends InputSupplierMap<String, InputStream> {
   @Inject
   public CopyInputStreamInputSupplierMap(Map<String, InputSupplier<InputStream>> toMap,
         CopyInputStreamIntoSupplier putFunction) {
      super(toMap, putFunction);
   }

   public CopyInputStreamInputSupplierMap(Map<String, InputSupplier<InputStream>> toMap) {
      super(toMap, new CopyInputStreamIntoSupplier());
   }

}