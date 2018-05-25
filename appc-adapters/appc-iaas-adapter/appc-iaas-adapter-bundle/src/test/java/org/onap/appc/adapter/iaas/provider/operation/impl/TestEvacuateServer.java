/*-
 * ============LICENSE_START=======================================================
 * ONAP : APPC
 * ================================================================================
 * Copyright (C) 2017-2018 AT&T Intellectual Property. All rights reserved.
 * ================================================================================
 * Copyright (C) 2017 Amdocs
 * =============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ============LICENSE_END=========================================================
 */
package org.onap.appc.adapter.iaas.provider.operation.impl;

import org.junit.Assert;
import org.junit.Test;
import org.onap.appc.adapter.iaas.ProviderAdapter;
import org.onap.appc.exceptions.APPCException;
import com.att.cdp.exceptions.ZoneException;
import com.att.cdp.zones.model.Server;
import com.att.cdp.zones.model.Server.Status;
import static org.mockito.Mockito.verify;

public class TestEvacuateServer {

    @Test
    public void evacuateServerRunning() throws ZoneException {
        MockGenerator mg = new MockGenerator(Status.RUNNING);
        Server server = mg.getServer();
        EvacuateServer rbs = new EvacuateServer();
        rbs.setProviderCache(mg.getProviderCacheMap());
        mg.getParams().put(ProviderAdapter.PROPERTY_TARGETHOST_ID, "newServer1");
        try {
            rbs.executeProviderOperation(mg.getParams(), mg.getSvcLogicContext());
        } catch (APPCException e) {
            Assert.fail("Exception during EvacuateServer.executeProviderOperation");
        }
        verify(mg.getComputeService()).moveServer(server.getId(), "newServer1");
    }

}