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
package org.jclouds.abiquo.strategy.enterprise.internal;

import static com.google.common.collect.Iterables.filter;
import static org.jclouds.abiquo.domain.DomainWrapper.wrap;

import javax.inject.Singleton;

import org.jclouds.abiquo.AbiquoAsyncApi;
import org.jclouds.abiquo.AbiquoApi;
import org.jclouds.abiquo.domain.enterprise.Enterprise;
import org.jclouds.abiquo.strategy.enterprise.ListEnterprises;
import org.jclouds.rest.RestContext;

import com.abiquo.server.core.enterprise.EnterprisesDto;
import com.google.common.base.Predicate;
import com.google.inject.Inject;

/**
 * List enterprises.
 * 
 * @author Ignasi Barrera
 * @author Francesc Montserrat
 */
@Singleton
public class ListEnterprisesImpl implements ListEnterprises
{
    // This strategy does not have still an Executor instance because the current methods call
    // single api methods

    protected final RestContext<AbiquoApi, AbiquoAsyncApi> context;

    @Inject
    ListEnterprisesImpl(final RestContext<AbiquoApi, AbiquoAsyncApi> context)
    {
        this.context = context;
    }

    @Override
    public Iterable<Enterprise> execute()
    {
        EnterprisesDto result = context.getApi().getEnterpriseApi().listEnterprises();
        return wrap(context, Enterprise.class, result.getCollection());
    }

    @Override
    public Iterable<Enterprise> execute(final Predicate<Enterprise> selector)
    {
        return filter(execute(), selector);
    }

}
