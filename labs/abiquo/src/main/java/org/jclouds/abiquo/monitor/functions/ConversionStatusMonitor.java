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
package org.jclouds.abiquo.monitor.functions;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Resource;
import javax.inject.Singleton;

import org.jclouds.abiquo.domain.cloud.Conversion;
import org.jclouds.abiquo.monitor.MonitorStatus;
import org.jclouds.logging.Logger;

import com.google.common.base.Function;

/**
 * This class takes care of monitoring {@link Conversion} jobs.
 * 
 * @author Sergi Castro
 */
@Singleton
public class ConversionStatusMonitor implements Function<Conversion, MonitorStatus>
{
    @Resource
    protected Logger logger = Logger.NULL;

    @Override
    public MonitorStatus apply(final Conversion conversion)
    {
        checkNotNull(conversion, "conversion");

        try
        {
            conversion.refresh();

            switch (conversion.getState())
            {
                case ENQUEUED:
                    return MonitorStatus.CONTINUE;
                case FAILED:
                    return MonitorStatus.FAILED;
                case FINISHED:
                    return MonitorStatus.DONE;
                default:
                    throw new IllegalStateException("Unsupported conversion status");
            }
        }
        catch (Exception ex)
        {
            logger.warn(ex, "exception thrown while monitoring %s on %s, returning CONTINUE",
                conversion, getClass().getName());

            return MonitorStatus.CONTINUE;
        }
    }
}
