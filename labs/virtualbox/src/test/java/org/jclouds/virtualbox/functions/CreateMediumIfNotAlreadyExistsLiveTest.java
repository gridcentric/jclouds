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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.File;

import org.jclouds.virtualbox.BaseVirtualBoxClientLiveTest;
import org.jclouds.virtualbox.domain.ErrorCode;
import org.jclouds.virtualbox.domain.HardDisk;
import org.testng.annotations.Test;
import org.virtualbox_4_1.DeviceType;
import org.virtualbox_4_1.IMedium;
import org.virtualbox_4_1.IProgress;
import org.virtualbox_4_1.VBoxException;

/**
 * @author Mattias Holmqvist
 */
public class CreateMediumIfNotAlreadyExistsLiveTest extends BaseVirtualBoxClientLiveTest {

   @Test
   public void testCreateMedium() throws Exception {
      String path = System.getProperty("user.home") + "/jclouds-virtualbox-test/test-medium-1.vdi";
      HardDisk hardDisk = HardDisk.builder().diskpath(path).controllerPort(0).deviceSlot(0).build();
      IMedium iMedium = new CreateMediumIfNotAlreadyExists(manager, machineUtils, true).apply(hardDisk);
      manager.get().getVBox().findMedium(path, DeviceType.HardDisk);
      try {
         assertFileCanBeDeleted(path);
      } finally {
         deleteMediumAndBlockUntilComplete(iMedium);
      }
   }

   @Test
   public void testCreateMediumFailWhenUsingNonFullyQualifiedPath() throws Exception {
      String path = "test-medium-2.vdi";
      HardDisk hardDisk = HardDisk.builder().diskpath(path).controllerPort(0).deviceSlot(0).build();
      try {
         new CreateMediumIfNotAlreadyExists(manager, machineUtils, true).apply(hardDisk);
         fail();
      } catch (VBoxException e) {
         ErrorCode errorCode = ErrorCode.valueOf(e);
         assertEquals(errorCode, ErrorCode.VBOX_E_FILE_ERROR);
      }
   }

   @Test
   public void testCreateSameMediumTwiceWhenUsingOverwrite() throws Exception {
      String path = System.getProperty("user.home") + "/jclouds-virtualbox-test/test-medium-3.vdi";
      HardDisk hardDisk = HardDisk.builder().diskpath(path).controllerPort(0).deviceSlot(0).build();
      new CreateMediumIfNotAlreadyExists(manager, machineUtils, true).apply(hardDisk);
      IMedium iMedium = new CreateMediumIfNotAlreadyExists(manager, machineUtils, true).apply(hardDisk);
      manager.get().getVBox().findMedium(path, DeviceType.HardDisk);
      try {
         assertFileCanBeDeleted(path);
      } finally {
         deleteMediumAndBlockUntilComplete(iMedium);
      }
   }

   private void assertFileCanBeDeleted(String path) {
      File file = new File(path);
      boolean mediumDeleted = file.delete();
      assertTrue(mediumDeleted);
   }

   void deleteMediumAndBlockUntilComplete(IMedium medium) {
      final IProgress progress = medium.deleteStorage();
      progress.waitForCompletion(-1);
   }

}
