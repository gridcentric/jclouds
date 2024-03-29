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
package org.jclouds.virtualbox.functions;

import org.jclouds.virtualbox.domain.DeviceDetails;
import org.virtualbox_4_1.IMachine;
import org.virtualbox_4_1.IMedium;
import org.virtualbox_4_1.VBoxException;

import com.google.common.base.Function;

/**
 * @author Mattias Holmqvist
 */
public class AttachMediumToMachineIfNotAlreadyAttached implements Function<IMachine, Void> {

   private final DeviceDetails device;
   private final IMedium medium;
   private final String controllerName;

   public AttachMediumToMachineIfNotAlreadyAttached(DeviceDetails device, IMedium medium, String controllerName) {
      this.device = device;
      this.medium = medium;
      this.controllerName = controllerName;
   }

   @Override
   public Void apply(IMachine machine) {
      try {
         machine.attachDevice(controllerName, device.getPort(), device.getDeviceSlot(), device.getDeviceType(), medium);
         machine.saveSettings();
      } catch (VBoxException e) {
         if (!alreadyAttached(e))
            throw e;
      }
      return null;
   }

   private boolean alreadyAttached(VBoxException e) {
      return e.getMessage().contains("is already attached to port");
   }

}
