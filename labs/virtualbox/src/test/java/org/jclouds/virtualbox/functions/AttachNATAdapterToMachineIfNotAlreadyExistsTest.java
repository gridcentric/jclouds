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

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.virtualbox_4_1.NATProtocol.TCP;
import static org.virtualbox_4_1.NetworkAttachmentType.NAT;

import org.jclouds.virtualbox.domain.NetworkAdapter;
import org.jclouds.virtualbox.domain.NetworkInterfaceCard;
import org.testng.annotations.Test;
import org.virtualbox_4_1.IMachine;
import org.virtualbox_4_1.INATEngine;
import org.virtualbox_4_1.INetworkAdapter;
import org.virtualbox_4_1.NetworkAttachmentType;
import org.virtualbox_4_1.VBoxException;

/**
 * @author Mattias Holmqvist, Andrea Turli
 */
@Test(groups = "unit", testName = "AttachNATAdapterToMachineIfNotAlreadyExistsTest")
public class AttachNATAdapterToMachineIfNotAlreadyExistsTest {

	@Test
	public void testApplyNetworkingToNonExistingAdapter() throws Exception {
		Long slotId = 0l;
		IMachine machine = createMock(IMachine.class);
		INetworkAdapter iNetworkAdapter = createMock(INetworkAdapter.class);
		INATEngine natEngine = createMock(INATEngine.class);

		expect(machine.getNetworkAdapter(slotId)).andReturn(iNetworkAdapter);
		iNetworkAdapter.setAttachmentType(NAT);
		expect(iNetworkAdapter.getNatDriver()).andReturn(natEngine);

		natEngine.addRedirect("TCP@127.0.0.1:2222->:22", TCP, "127.0.0.1",
				2222, "", 22);
		iNetworkAdapter.setEnabled(true);
		machine.saveSettings();

		replay(machine, iNetworkAdapter, natEngine);
		NetworkAdapter networkAdapter = NetworkAdapter.builder()
				.networkAttachmentType(NetworkAttachmentType.NAT)
				.tcpRedirectRule("127.0.0.1", 2222, "", 22).build();
		NetworkInterfaceCard networkInterfaceCard = NetworkInterfaceCard
				.builder().addNetworkAdapter(networkAdapter).build();

		new AttachNATAdapterToMachineIfNotAlreadyExists(networkInterfaceCard)
				.apply(machine);

		verify(machine, iNetworkAdapter, natEngine);
	}

	@Test
	public void testApplySkipsWhenAlreadyExists() throws Exception {
		Long slotId = 0l;
		IMachine machine = createMock(IMachine.class);
		INetworkAdapter iNetworkAdapter = createMock(INetworkAdapter.class);
		INATEngine natEngine = createMock(INATEngine.class);

		expect(machine.getNetworkAdapter(slotId)).andReturn(iNetworkAdapter);
		iNetworkAdapter.setAttachmentType(NAT);
		expect(iNetworkAdapter.getNatDriver()).andReturn(natEngine);

		natEngine.addRedirect("TCP@127.0.0.1:2222->:22", TCP, "127.0.0.1",
				2222, "", 22);
		expectLastCall()
				.andThrow(
						new VBoxException(null,
								"VirtualBox error: A NAT rule of this name already exists (0x80070057)"));

		iNetworkAdapter.setEnabled(true);
		machine.saveSettings();

		replay(machine, iNetworkAdapter, natEngine);
		NetworkAdapter networkAdapter = NetworkAdapter.builder()
				.networkAttachmentType(NetworkAttachmentType.NAT)
				.tcpRedirectRule("127.0.0.1", 2222, "", 22).build();
		NetworkInterfaceCard networkInterfaceCard = NetworkInterfaceCard
				.builder().addNetworkAdapter(networkAdapter).build();
		new AttachNATAdapterToMachineIfNotAlreadyExists(networkInterfaceCard)
				.apply(machine);

		verify(machine, iNetworkAdapter, natEngine);
	}

	@Test(enabled=false, expectedExceptions = VBoxException.class)
	public void testRethrowInvalidAdapterSlotException() throws Exception {
		Long slotId = 30l;
		IMachine machine = createMock(IMachine.class);
		INetworkAdapter iNetworkAdapter = createMock(INetworkAdapter.class);
		INATEngine natEngine = createMock(INATEngine.class);

		String error = "VirtualBox error: Argument slot is invalid "
				+ "(must be slot < RT_ELEMENTS(mNetworkAdapters)) (0x80070057)";

		VBoxException invalidSlotException = new VBoxException(
				createNiceMock(Throwable.class), error);
		expect(machine.getNetworkAdapter(slotId))
				.andThrow(invalidSlotException);

		replay(machine, iNetworkAdapter, natEngine);
		NetworkAdapter networkAdapter = NetworkAdapter.builder()
				.networkAttachmentType(NetworkAttachmentType.NAT)
				.tcpRedirectRule("127.0.0.1", 2222, "", 22).build();
		NetworkInterfaceCard networkInterfaceCard = NetworkInterfaceCard
				.builder().addNetworkAdapter(networkAdapter).build();
		new AttachNATAdapterToMachineIfNotAlreadyExists(networkInterfaceCard)
				.apply(machine);

		verify(machine, iNetworkAdapter, natEngine);
	}

}
