/**
 *
 * Copyright (C) 2010 Cloud Conscious, LLC. <info@cloudconscious.com>
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */

package org.jclouds.savvis.vpdc.xml;

import static org.jclouds.savvis.vpdc.util.Utils.newResource;
import static org.jclouds.util.SaxUtils.equalsOrSuffix;

import java.util.Map;

import javax.inject.Inject;

import org.jclouds.cim.xml.ResourceAllocationSettingDataHandler;
import org.jclouds.http.functions.ParseSax;
import org.jclouds.ovf.xml.NetworkSectionHandler;
import org.jclouds.savvis.vpdc.domain.Resource;
import org.jclouds.savvis.vpdc.domain.VM;
import org.jclouds.savvis.vpdc.util.Utils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.google.common.collect.ImmutableMap;

/**
 * @author Kedar Dave
 */
public class VMHandler extends ParseSax.HandlerWithResult<VM> {
   protected StringBuilder currentText = new StringBuilder();
   private final NetworkSectionHandler networkSectionHandler;
   private final ResourceAllocationSettingDataHandler allocationHandler;

   @Inject
   public VMHandler(NetworkSectionHandler networkSectionHandler, ResourceAllocationSettingDataHandler allocationHandler) {
      this.networkSectionHandler = networkSectionHandler;
      this.allocationHandler = allocationHandler;
   }

   private VM.Builder builder = VM.builder();
   protected boolean inOs;

   public VM getResult() {
      try {
         return builder.build();
      } finally {
         builder = VM.builder();
      }
   }

   public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXException {
      Map<String, String> attributes = Utils.cleanseAttributes(attrs);
      if (equalsOrSuffix(qName, "VApp")) {
         // savvis doesn't add href in the header for some reason
         if (!attributes.containsKey("href") && getRequest() != null)
            attributes = ImmutableMap.<String, String> builder().putAll(attributes)
                  .put("href", getRequest().getEndpoint().toASCIIString()).build();
         Resource vApp = newResource(attributes);
         builder.name(vApp.getName()).type(vApp.getType()).id(vApp.getId()).href(vApp.getHref());
         builder.status(VM.Status.fromValue(attributes.get("status")));
      } else if (equalsOrSuffix(qName, "OperatingSystemSection")) {
         inOs = true;
         if (attributes.containsKey("id"))
            builder.osType(Integer.parseInt(attributes.get("id")));
      } else {
         networkSectionHandler.startElement(uri, localName, qName, attrs);
         allocationHandler.startElement(uri, localName, qName, attrs);
      }

   }

   @Override
   public void endElement(String uri, String localName, String qName) throws SAXException {
      if (equalsOrSuffix(qName, "OperatingSystemSection")) {
         inOs = false;
      } else if (inOs && equalsOrSuffix(qName, "Description")) {
         builder.osDescripton(Utils.currentOrNull(currentText));
      } else if (equalsOrSuffix(qName, "IpAddress")) {
         builder.ipAddress(Utils.currentOrNull(currentText));
      } else if (equalsOrSuffix(qName, "NetworkSection")) {
         networkSectionHandler.endElement(uri, localName, qName);
         builder.networkSection(networkSectionHandler.getResult());
      } else if (equalsOrSuffix(qName, "Item")) {
         allocationHandler.endElement(uri, localName, qName);
         builder.resourceAllocation(allocationHandler.getResult());
      } else {
         networkSectionHandler.endElement(uri, localName, qName);
         allocationHandler.endElement(uri, localName, qName);
      }
      currentText = new StringBuilder();
   }

   @Override
   public void characters(char ch[], int start, int length) {
      currentText.append(ch, start, length);
      networkSectionHandler.characters(ch, start, length);
      allocationHandler.characters(ch, start, length);
   }

}