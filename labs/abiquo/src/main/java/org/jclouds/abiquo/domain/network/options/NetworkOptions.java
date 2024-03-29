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
package org.jclouds.abiquo.domain.network.options;

import org.jclouds.http.options.BaseHttpRequestOptions;

import com.abiquo.model.enumerator.NetworkType;

/**
 * Available options to query networks.
 * 
 * @author Francesc Montserrat
 */
public class NetworkOptions extends BaseHttpRequestOptions
{
    public static Builder builder()
    {
        return new Builder();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        NetworkOptions options = new NetworkOptions();
        options.queryParameters.putAll(queryParameters);
        return options;
    }

    public static class Builder
    {
        private NetworkType type;

        private Boolean all;

        public Builder type(final NetworkType type)
        {
            this.type = type;
            return this;
        }

        public Builder all(final boolean all)
        {
            this.all = all;
            return this;
        }

        public NetworkOptions build()
        {
            NetworkOptions options = new NetworkOptions();

            if (type != null)
            {
                options.queryParameters.put("type", String.valueOf(type));
            }

            if (all != null)
            {
                options.queryParameters.put("all", String.valueOf(all));
            }

            return options;
        }
    }
}
