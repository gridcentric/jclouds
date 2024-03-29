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
package org.jclouds.rest.functions;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicates;
import com.google.common.base.Throwables;
import com.google.common.primitives.Ints;
import javax.inject.Singleton;
import static org.jclouds.http.HttpUtils.returnValueOnCodeOrNull;

/**
 * 
 * @author Leander Beernaert
 */
@Singleton
public class ReturnAbsentOn403Or404Or500 implements Function<Exception, Object> {


   public Object apply(Exception from) {
      Boolean returnVal = returnValueOnCodeOrNull(from, true, Predicates.in(Ints.asList(403, 404, 500)));
      if (returnVal != null)
         return Optional.absent();
       
      throw Throwables.propagate(from);
   }

}
