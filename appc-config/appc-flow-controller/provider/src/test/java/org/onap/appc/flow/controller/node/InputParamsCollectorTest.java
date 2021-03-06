/*
 * ============LICENSE_START=======================================================
 * ONAP : APPC
 * ================================================================================
 * Copyright (C) 2018 Nokia. All rights reserved.
 * Copyright (C) 2018-2019 AT&T Intellectual Property. All rights reserved.
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
import static org.onap.appc.flow.controller.node.InputParamsCollector.SDNC_CONFIG_DIR_VAR;
import static org.onap.appc.flow.controller.utils.FlowControllerConstants.ACTION_LEVEL;
import static org.onap.appc.flow.controller.utils.FlowControllerConstants.PAYLOAD;
import static org.onap.appc.flow.controller.utils.FlowControllerConstants.REQUEST_ACTION;
import static org.onap.appc.flow.controller.utils.FlowControllerConstants.VNFC_NAME;
import static org.onap.appc.flow.controller.utils.FlowControllerConstants.VNF_ID;
import static org.onap.appc.flow.controller.utils.FlowControllerConstants.VSERVER_ID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.onap.appc.flow.controller.data.Transaction;
import org.onap.appc.flow.controller.dbervices.FlowControlDBService;
import org.onap.appc.flow.controller.interfaceData.DependencyInfo;
import org.onap.appc.flow.controller.interfaceData.Vnfcs;
import org.onap.ccsdk.sli.core.sli.SvcLogicContext;

public class InputParamsCollectorTest {

    private SvcLogicContext ctx;
    private FlowControlDBService dbService;
    private EnvVariables envVariables;
    private InputParamsCollector inputParamsCollector;

    @Before
    public void setUp() {

        ctx = mock(SvcLogicContext.class);
        dbService = mock(FlowControlDBService.class);
        envVariables = mock(EnvVariables.class);

        when(envVariables.getenv(SDNC_CONFIG_DIR_VAR)).thenReturn("./src/test/resources");

        inputParamsCollector = new InputParamsCollector(envVariables, dbService);
    }

    @Test
    public void should_collect_input_params() throws Exception {

        when(ctx.getAttribute(VNF_ID)).thenReturn("some-vnf-id");
        when(ctx.getAttribute(REQUEST_ACTION)).thenReturn("some-request-action");
        when(ctx.getAttribute(ACTION_LEVEL)).thenReturn("some-action-level");
        when(ctx.getAttribute(PAYLOAD)).thenReturn("some-payload");
        when(ctx.getAttribute(VSERVER_ID)).thenReturn("some-vserver-id");
        when(ctx.getAttribute(VNFC_NAME)).thenReturn("some-vnfc-name");
        when(ctx.getAttribute("identity-url")).thenReturn("test_url");

        when(dbService.getCapabilitiesData(ctx)).thenReturn(
                "{'capabilities': { 'vnf':['vnf-1', 'vnf-2'],'vf-module':['vf-module-1', 'vf-module-2'],'vnfc':['vnfc-1', 'vnfc-2'],'vm':[{'Start':['vnfc-1','vnfc-2']},{'Stop':[]}]}}"
                        .replaceAll("'", "\""));
        when(dbService.getDependencyInfo(ctx)).thenReturn(dependencyInfoPayload());

        Transaction transaction = inputParamsCollector.collectInputParams(ctx);

        Assert.assertEquals(
                "{\"input\":{\"request-info\":{\"action\":\"some-request-action\",\"payload\":\"some-payload\",\"action-level\":\"some-action-level\",\"action-identifier\":{\"vnf-id\":\"some-vnf-id\",\"vserver-id\":\"some-vserver-id\",\"vnfc-name\":\"some-vnfc-name\"}},\"inventory-info\":{\"vnf-info\":{\"vnf-id\":\"some-vnf-id\",\"identity-url\":\"test_url\",\"vm\":[]}},\"capabilities\":{\"vnf\":[\"vnf-1\",\"vnf-2\"],\"vm\":{\"Start\":[\"vnfc-1\",\"vnfc-2\"],\"Stop\":[]},\"vnfc\":[\"vnfc-1\",\"vnfc-2\"],\"vf-module\":[\"vf-module-1\",\"vf-module-2\"]}}}",
                transaction.getPayload());
        Assert.assertEquals("POST", transaction.getExecutionRPC());
        Assert.assertEquals("seq-generator-uid", transaction.getuId());
        Assert.assertEquals("some-pswd", transaction.getPswd());
        Assert.assertEquals("exec-endpoint", transaction.getExecutionEndPoint());
    }

    @Test
    public void should_handle_dependency_config() throws Exception {

        Vnfcs vnfcs = new Vnfcs();
        vnfcs.setVnfcType("some-type");
        vnfcs.setResilience("some-resilience");
        vnfcs.setMandatory("some-mandatory");
        Map<String, List<Vnfcs>> input = new HashMap<>();
        List<Vnfcs> list = new ArrayList<>();
        list.add(vnfcs);
        list.add(vnfcs);
        input.put("vnfcs", list);

        String jsonPayload = new ObjectMapper().writeValueAsString(input);

        when(dbService.getDependencyInfo(ctx)).thenReturn(jsonPayload);

        DependencyInfo dependencyInfo = inputParamsCollector.getDependencyInfo(ctx);

        Assert.assertEquals(
                "DependencyInfo [vnfcs=[Vnfcs [vnfcType=some-type, mandatory=some-mandatory, resilience=some-resilience, parents=[]], Vnfcs [vnfcType=some-type, mandatory=some-mandatory, resilience=some-resilience, parents=[]]]]",
                dependencyInfo.toString());
    }

    @Test
    public void should_collect_input_params_null_caps() throws Exception {

        when(ctx.getAttribute(VNF_ID)).thenReturn("some-vnf-id");
        when(ctx.getAttribute(REQUEST_ACTION)).thenReturn("some-request-action");
        when(ctx.getAttribute(ACTION_LEVEL)).thenReturn("some-action-level");
        when(ctx.getAttribute(PAYLOAD)).thenReturn("some-payload");
        when(ctx.getAttribute(VSERVER_ID)).thenReturn("some-vserver-id");
        when(ctx.getAttribute(VNFC_NAME)).thenReturn("some-vnfc-name");
        when(ctx.getAttribute("identity-url")).thenReturn("test_url");

        when(dbService.getCapabilitiesData(ctx)).thenReturn(null);

        when(dbService.getDependencyInfo(ctx)).thenReturn(dependencyInfoPayload());

        Transaction transaction = inputParamsCollector.collectInputParams(ctx);

        Assert.assertEquals(
                "{\"input\":{\"request-info\":{\"action\":\"some-request-action\",\"payload\":\"some-payload\",\"action-level\":\"some-action-level\",\"action-identifier\":{\"vnf-id\":\"some-vnf-id\",\"vserver-id\":\"some-vserver-id\",\"vnfc-name\":\"some-vnfc-name\"}},\"inventory-info\":{\"vnf-info\":{\"vnf-id\":\"some-vnf-id\",\"identity-url\":\"test_url\",\"vm\":[]}},\"capabilities\":{\"vnf\":[],\"vm\":{},\"vnfc\":[],\"vf-module\":[]}}}",
                transaction.getPayload());
        Assert.assertEquals("POST", transaction.getExecutionRPC());
        Assert.assertEquals("seq-generator-uid", transaction.getuId());
        Assert.assertEquals("some-pswd", transaction.getPswd());
        Assert.assertEquals("exec-endpoint", transaction.getExecutionEndPoint());
    }

    private String dependencyInfoPayload() throws JsonProcessingException {
        Vnfcs vnfcs = new Vnfcs();
        vnfcs.setVnfcType("some-type");
        vnfcs.setResilience("some-resilience");
        vnfcs.setMandatory("some-mandatory");
        Map<String, List<Vnfcs>> input = new HashMap<>();
        List<Vnfcs> list = new ArrayList<>();
        list.add(vnfcs);
        list.add(vnfcs);
        input.put("vnfcs", list);

        return new ObjectMapper().writeValueAsString(input);
    }

}
