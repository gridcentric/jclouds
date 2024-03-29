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
package org.jclouds.savvis.vpdc.binders;

import static com.google.common.base.Preconditions.checkArgument;

import javax.inject.Singleton;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.jclouds.rest.internal.GeneratedHttpRequest;
import org.jclouds.savvis.vpdc.domain.VMSpec;

import com.google.common.collect.Iterables;
import com.jamesmurty.utils.XMLBuilder;

/**
 * 
 * @author Adrian Cole
 * 
 */
@Singleton
public class BindVMSpecsToXmlPayload extends BaseBindVMSpecToXmlPayload<Iterable<VMSpec>> {

   @SuppressWarnings("unchecked")
   protected Iterable<VMSpec> findSpecInArgsOrNull(GeneratedHttpRequest gRequest) {
      for (Object arg : gRequest.getArgs()) {
         if (arg instanceof Iterable<?>) {
            Iterable<VMSpec> specs = (Iterable<VMSpec>) arg;
            checkArgument(Iterables.size(specs) > 0,
                     "At least one VMSpec must be included in the argument list");
            return specs;
         }
      }
      throw new IllegalArgumentException("Iterable<VMSpec> must be included in the argument list");
   }

   @Override
   protected void bindSpec(Iterable<VMSpec> specs, XMLBuilder rootBuilder) throws ParserConfigurationException, FactoryConfigurationError {
      rootBuilder.a("name", "");
      XMLBuilder specBuilder = buildChildren(rootBuilder);
      for (VMSpec spec : specs) {
         checkSpec(spec);
         XMLBuilder vAppBuilder = buildRootForName(specBuilder, spec.getName());
         addOperatingSystemAndVirtualHardware(spec, vAppBuilder);
      }
   }
}
