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
package org.jclouds.rimuhosting.miro.functions;

import java.util.Map;
import java.util.SortedSet;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jclouds.http.HttpResponse;
import org.jclouds.http.functions.ParseJson;
import org.jclouds.rimuhosting.miro.domain.Server;
import org.jclouds.rimuhosting.miro.domain.internal.RimuHostingResponse;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;

/**
 * @author Ivan Meredith
 * @author Adrian Cole
 */
@Singleton
public class ParseServersFromJsonResponse implements
      Function<HttpResponse, SortedSet<Server>> {

   private final ParseJson<Map<String, OrderResponse>> json;

   @Inject
   ParseServersFromJsonResponse(ParseJson<Map<String, OrderResponse>> json) {
      this.json = json;
   }

   @Override
   public SortedSet<Server> apply(HttpResponse arg0) {
      return Iterables.get(json.apply(arg0).values(), 0).about_orders;
   }

   private static class OrderResponse extends RimuHostingResponse {
      private SortedSet<Server> about_orders;

   }

}
