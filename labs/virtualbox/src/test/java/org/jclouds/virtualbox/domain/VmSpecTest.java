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
package org.jclouds.virtualbox.domain;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import org.testng.annotations.Test;
import org.virtualbox_4_1.CleanupMode;
import org.virtualbox_4_1.StorageBus;

public class VmSpecTest {

   @Test
   public void testEqualsSuccessful() throws Exception {
      VmSpec vmSpec = defaultVm().build();
      VmSpec sameVmSpec = defaultVm().build();
      assertEquals(vmSpec, sameVmSpec);
   }

   @Test
   public void testEqualsWrongId() throws Exception {
      VmSpec vmSpec = defaultVm().build();
      VmSpec other = defaultVm().id("OtherVMId").build();
      assertFalse(vmSpec.equals(other));
   }

   @Test
   public void testEqualsWrongName() throws Exception {
      VmSpec vmSpec = defaultVm().build();
      VmSpec other = defaultVm().name("OtherName").build();
      assertFalse(vmSpec.equals(other));
   }

   @Test
   public void testEqualsWrongOsType() throws Exception {
      VmSpec vmSpec = defaultVm().build();
      VmSpec other = defaultVm().osTypeId("OtherOS").build();
      assertFalse(vmSpec.equals(other));
   }

   @Test
   public void testEqualsWrongForceOverwriteRule() throws Exception {
      VmSpec vmSpec = defaultVm().build();
      VmSpec other = defaultVm().forceOverwrite(false).build();
      assertFalse(vmSpec.equals(other));
   }

   private VmSpec.Builder defaultVm() {
		return VmSpec.builder()
              .id("MyVmId")
              .name("My VM")
              .osTypeId("Ubuntu")
              .memoryMB(1024)
              .cleanUpMode(CleanupMode.Full)
              .forceOverwrite(true)
              .controller(
                      StorageController.builder().name("Controller")
                              .bus(StorageBus.IDE)
                              .attachHardDisk(HardDisk.builder().diskpath("/tmp/tempdisk.vdi")
                                    .controllerPort(0).deviceSlot(0).build())
                              .build());
   }
}
