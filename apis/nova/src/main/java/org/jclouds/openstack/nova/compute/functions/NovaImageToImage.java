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
package org.jclouds.openstack.nova.compute.functions;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.Image.Status;
import org.jclouds.compute.domain.ImageBuilder;
import org.jclouds.compute.domain.OperatingSystem;
import org.jclouds.openstack.nova.domain.ImageStatus;

import com.google.common.base.Function;

/**
 * 
 * @author Adrian Cole
 */
@Singleton
public class NovaImageToImage implements Function<org.jclouds.openstack.nova.domain.Image, Image> {
   private final Map<ImageStatus, Status> toPortableImageStatus;
   private final Function<org.jclouds.openstack.nova.domain.Image, OperatingSystem> imageToOs;

   @Inject
   NovaImageToImage(Map<ImageStatus, Image.Status> toPortableImageStatus,
            Function<org.jclouds.openstack.nova.domain.Image, OperatingSystem> imageToOs) {
      this.toPortableImageStatus = toPortableImageStatus;
      this.imageToOs = imageToOs;
   }

   public Image apply(org.jclouds.openstack.nova.domain.Image from) {
      ImageBuilder builder = new ImageBuilder();
      builder.ids(from.getId() + "");
      builder.name(from.getName() != null ? from.getName() : "unspecified");
      builder.description(from.getName() != null ? from.getName() : "unspecified");
      builder.version(from.getUpdated() != null ? from.getUpdated().getTime() + "" : "-1");
      builder.operatingSystem(imageToOs.apply(from)); //image name may not represent the OS type
      builder.status(toPortableImageStatus.get(from.getStatus()));
      builder.uri(from.getURI());
      Image image = builder.build();
      return image;
   }
}
