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
package org.jclouds.blobstore.functions;

import org.jclouds.blobstore.ContainerNotFoundException;
import org.jclouds.http.HttpResponse;
import org.jclouds.http.HttpResponseException;
import org.testng.annotations.Test;

/**
 * 
 * @author Adrian Cole
 */
@Test(groups = "unit")
public class ThrowContainerNotFoundOn404Test {
   ThrowContainerNotFoundOn404 fn = new ThrowContainerNotFoundOn404();

   @Test(expectedExceptions = { ContainerNotFoundException.class })
   public void testFound404ThrowsContainerNotFound() throws SecurityException, NoSuchMethodException {
      HttpResponse response = HttpResponse.builder().statusCode(404).build();
      HttpResponseException exception = new HttpResponseException(null, null, response);
      fn.apply(exception);
   }

   @Test(expectedExceptions = { HttpResponseException.class })
   public void testNotFound404PropagatesHttpResponseException() throws SecurityException, NoSuchMethodException {
      HttpResponse response = HttpResponse.builder().statusCode(409).build();
      HttpResponseException exception = new HttpResponseException(null, null, response);
      fn.apply(exception);
   }

   @Test(expectedExceptions = { RuntimeException.class })
   public void testNotFoundPropagates() throws SecurityException, NoSuchMethodException {
      fn.apply(new RuntimeException());
   }

   @Test(expectedExceptions = { NullPointerException.class, IllegalStateException.class })
   public void testNullIsBad() {
      fn.apply(null);
   }
}
