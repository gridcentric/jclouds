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
package org.jclouds.deltacloud.xml;

import java.util.Set;

import javax.inject.Inject;

import org.jclouds.deltacloud.domain.Image;
import org.jclouds.http.functions.ParseSax;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.google.common.collect.Sets;

/**
 * @author Adrian Cole
 */
public class ImagesHandler extends ParseSax.HandlerWithResult<Set<? extends Image>> {
   private StringBuilder currentText = new StringBuilder();

   private Set<Image> images = Sets.newLinkedHashSet();
   private final ImageHandler imageHandler;

   @Inject
   public ImagesHandler(ImageHandler locationHandler) {
      this.imageHandler = locationHandler;
   }

   public Set<? extends Image> getResult() {
      return images;
   }

   @Override
   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      imageHandler.startElement(uri, localName, qName, attributes);
   }

   @Override
   public void endElement(String uri, String localName, String qName) throws SAXException {
      imageHandler.endElement(uri, localName, qName);
      if (qName.equals("image") && currentText.toString().trim().equals("")) {
         this.images.add(imageHandler.getResult());
      }
      currentText = new StringBuilder();
   }

   public void characters(char ch[], int start, int length) {
      imageHandler.characters(ch, start, length);
      currentText.append(ch, start, length);
   }
}
