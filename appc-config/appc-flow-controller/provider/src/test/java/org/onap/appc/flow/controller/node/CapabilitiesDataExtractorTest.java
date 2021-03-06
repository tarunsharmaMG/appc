/*
 * ============LICENSE_START=======================================================
 * ONAP : APPC
 * ================================================================================
 * Copyright (C) 2018 Nokia. All rights reserved.
 * Copyright (C) 2019 AT&T Intellectual Property. All rights reserved.
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
 * ============LICENSE_END=========================================================
 */
package org.onap.appc.flow.controller.node;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.onap.appc.flow.controller.dbervices.FlowControlDBService;
import org.onap.appc.flow.controller.interfaceData.Capabilities;
import org.onap.ccsdk.sli.core.sli.SvcLogicContext;

public class CapabilitiesDataExtractorTest {

    private CapabilitiesDataExtractor capabilitiesDataExtractor;
    private FlowControlDBService dbService;
    private SvcLogicContext ctx;

    @Before
    public void setUp() {
        dbService = mock(FlowControlDBService.class);
        ctx = mock(SvcLogicContext.class);
        capabilitiesDataExtractor = new CapabilitiesDataExtractor(dbService);
    }

    @Test
    public void should_handle_capabilities_full_config() throws Exception {

        String jsonPayload = "{'capabilities':{'vnfc':[],'vm':[{'AttachVolume':[]},{'DetachVolume':[]},{'Evacuate':['pld','ssc']},{'Migrate':['pld','ssc']},{'Reboot':['pld','ssc']},{'Rebuild':['pld','ssc']},{'Restart':['pld','ssc']},{'Snapshot':['pld','ssc']},{'Start':['pld','ssc']},{'Stop':['pld','ssc']}],'vf-module':[],'vnf':['Configure','AllAction','ConfigModify','OpenStack Actions']}}";
        when(dbService.getCapabilitiesData(ctx)).thenReturn(jsonPayload.replaceAll("'", "\""));

        Capabilities capabilitiesData = capabilitiesDataExtractor.getCapabilitiesData(ctx);
        Assert.assertEquals(
                "Capabilities [vnf=[Configure, AllAction, ConfigModify, OpenStack Actions], vfModule=[], vm={Evacuate=[pld, ssc], DetachVolume=[], Snapshot=[pld, ssc], AttachVolume=[], Start=[pld, ssc], Stop=[pld, ssc], Migrate=[pld, ssc], Restart=[pld, ssc], Reboot=[pld, ssc], Rebuild=[pld, ssc]}, vnfc=[]]",
                capabilitiesData.toString());
    }

    @Test
    public void should_handle_capabilities_config_with_missing_params1() throws Exception {

        // CASE: vm is empty, vnfc is absent
        String jsonPayload = "{'capabilities':{'vnf':['vnf-1', 'vnf-2'],'vf-module':['vf-module-1'],'vm':[]}}";
        when(dbService.getCapabilitiesData(ctx)).thenReturn(jsonPayload.replaceAll("'", "\""));

        Capabilities capabilitiesData = capabilitiesDataExtractor.getCapabilitiesData(ctx);

        Assert.assertEquals("Capabilities [vnf=[vnf-1, vnf-2], vfModule=[vf-module-1], vm={}, vnfc=[]]",
                capabilitiesData.toString());
    }

    @Test
    public void should_handle_capabilities_config_with_missing_params2() throws Exception {

        // CASE: vm has action+vnfc format, vf-module is empty, vnfc is absent
        String jsonPayload = "{'capabilities':{'vnf':['vnf-1', 'vnf-2'],'vf-module':[],'vm':[{'AttachVolume':['vnfc-1']}]}}";
        when(dbService.getCapabilitiesData(ctx)).thenReturn(jsonPayload.replaceAll("'", "\""));

        Capabilities capabilitiesData = capabilitiesDataExtractor.getCapabilitiesData(ctx);

        Assert.assertEquals("Capabilities [vnf=[vnf-1, vnf-2], vfModule=[], vm={AttachVolume=[vnfc-1]}, vnfc=[]]",
                capabilitiesData.toString());
    }
}
