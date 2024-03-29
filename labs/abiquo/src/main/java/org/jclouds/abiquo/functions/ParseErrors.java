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

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jclouds.http.functions.ParseXMLWithJAXB;
import org.jclouds.xml.XMLParser;

import com.abiquo.model.transport.error.ErrorsDto;
import com.google.inject.TypeLiteral;

/**
 * Parses a errors object.
 * 
 * @author Ignasi Barrera
 */
@Singleton
public class ParseErrors extends ParseXMLWithJAXB<ErrorsDto>
{
    @Inject
    public ParseErrors(final XMLParser xml, final TypeLiteral<ErrorsDto> type)
    {
        super(xml, type);
    }

}
