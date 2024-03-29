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
package org.jclouds.abiquo.functions;

import javax.inject.Singleton;

import org.jclouds.abiquo.domain.exception.AbiquoException;
import org.jclouds.rest.ResourceNotFoundException;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Throwables;
import com.google.common.collect.Iterables;

/**
 * Return an Abiquo Exception on not found errors.
 * 
 * @author Ignasi Barrera
 */
@Singleton
public class ReturnAbiquoExceptionOnNotFoundOr4xx implements Function<Exception, Object>
{
    @Override
    public Object apply(final Exception from)
    {
        Throwable exception =
            Iterables.find(Throwables.getCausalChain(from), isNotFoundAndHasAbiquoException(from),
                null);

        throw Throwables.propagate(exception == null ? from : (AbiquoException) exception
            .getCause());
    }

    private static Predicate<Throwable> isNotFoundAndHasAbiquoException(final Throwable exception)
    {
        return new Predicate<Throwable>()
        {
            @Override
            public boolean apply(final Throwable input)
            {
                return input instanceof ResourceNotFoundException
                    && input.getCause() instanceof AbiquoException;
            }
        };
    }
}
