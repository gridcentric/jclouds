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
package org.jclouds.rds.features;

import static org.jclouds.aws.reference.FormParameters.ACTION;

import javax.inject.Named;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.jclouds.aws.filters.FormSigner;
import org.jclouds.collect.IterableWithMarker;
import org.jclouds.collect.PagedIterable;
import org.jclouds.rds.domain.SubnetGroup;
import org.jclouds.rds.functions.SubnetGroupsToPagedIterable;
import org.jclouds.rds.options.ListSubnetGroupsOptions;
import org.jclouds.rds.xml.DescribeDBSubnetGroupsResultHandler;
import org.jclouds.rds.xml.SubnetGroupHandler;
import org.jclouds.rest.annotations.ExceptionParser;
import org.jclouds.rest.annotations.FormParams;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.Transform;
import org.jclouds.rest.annotations.VirtualHost;
import org.jclouds.rest.annotations.XMLResponseParser;
import org.jclouds.rest.functions.ReturnNullOnNotFoundOr404;
import org.jclouds.rest.functions.ReturnVoidOnNotFoundOr404;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * Provides access to Amazon RDS via the Query API
 * <p/>
 * 
 * @see <a href="http://docs.amazonwebservices.com/AmazonRDS/latest/APIReference"
 *      >doc</a>
 * @see SubnetGroupApi
 * @author Adrian Cole
 */
@RequestFilters(FormSigner.class)
@VirtualHost
public interface SubnetGroupAsyncApi {
 
   /**
    * @see SubnetGroupApi#get()
    */
   @Named("rds:DescribeDBSubnetGroups")
   @POST
   @Path("/")
   @XMLResponseParser(SubnetGroupHandler.class)
   @FormParams(keys = "Action", values = "DescribeDBSubnetGroups")
   @ExceptionParser(ReturnNullOnNotFoundOr404.class)
   ListenableFuture<SubnetGroup> get(@FormParam("DBSubnetGroupName") String name);

   /**
    * @see SubnetGroupApi#list()
    */
   @Named("rds:DescribeDBSubnetGroups")
   @POST
   @Path("/")
   @XMLResponseParser(DescribeDBSubnetGroupsResultHandler.class)
   @Transform(SubnetGroupsToPagedIterable.class)
   @FormParams(keys = "Action", values = "DescribeDBSubnetGroups")
   ListenableFuture<PagedIterable<SubnetGroup>> list();

   /**
    * @see SubnetGroupApi#list(ListSubnetGroupsOptions)
    */
   @Named("rds:DescribeDBSubnetGroups")
   @POST
   @Path("/")
   @XMLResponseParser(DescribeDBSubnetGroupsResultHandler.class)
   @FormParams(keys = "Action", values = "DescribeDBSubnetGroups")
   ListenableFuture<IterableWithMarker<SubnetGroup>> list(ListSubnetGroupsOptions options);

   /**
    * @see SubnetGroupApi#delete()
    */
   @Named("rds:DeleteDBSubnetGroup")
   @POST
   @Path("/")
   @ExceptionParser(ReturnVoidOnNotFoundOr404.class)
   @FormParams(keys = ACTION, values = "DeleteDBSubnetGroup")
   ListenableFuture<Void> delete(@FormParam("DBSubnetGroupName") String name);
}
