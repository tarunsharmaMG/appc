/*-
 * ============LICENSE_START=======================================================
 * ONAP : APPC
 * ================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property. All rights reserved.
 * ================================================================================
 * Copyright (C) 2018 Nokia Solutions and Networks
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
 * ECOMP is a trademark and service mark of AT&T Intellectual Property.
 * ============LICENSE_END=========================================================
 */
package org.onap.appc.listener.LCM.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import org.onap.appc.exceptions.APPCException;
import org.onap.appc.listener.EventHandler;
import org.onap.appc.listener.LCM.model.DmaapMessage;
import org.onap.appc.listener.LCM.operation.ProviderOperations;
import org.onap.appc.listener.util.Mapper;

public class WorkerImplTest {

    private static final String jsonInputBodyStr =
        "{\"input\":{ \"common-header\": { \"timestamp\": \"2016-08-03T08:50:18.97Z\", "
            + "\"api-ver\": \"1\", \"originator-id\": \"1\", \"request-id\": \"123\", \"sub-request-id\": \"1\", "
            + "\"flags\": { \"force\":\"TRUE\", \"ttl\":\"9900\" } }, \"action\": \"Stop\", "
            + "\"action-identifiers\": { \"vnf-id\": \"TEST\" } }}";

    private static final String jsonOutputBodyStr = "{\"output\":{\"common-header\":{\"timestamp\":\"2016-08-03T08:50:18.97Z\","
        + "\"api-ver\":\"1\",\"flags\":{\"force\":\"TRUE\",\"ttl\":\"9900\"},\"sub-request-id\":\"1\","
        + "\"request-id\":\"123\",\"originator-id\":\"1\"},\"status\":{\"value\":\"TestException\",\"code\":200}}}";

    private EventHandler mockEventHandler = mock(EventHandler.class);
    private ProviderOperations mockProviderOperations = mock(ProviderOperations.class);


    @Test(expected = IllegalStateException.class)
    public void should_throw_when_one_of_worker_fields_is_null() {

        WorkerImpl worker = new WorkerImpl(null, mockEventHandler, mockProviderOperations);
        worker.run();
    }

    @Test
    public void should_post_error_message_to_dmaap_on_exception() throws APPCException {

        when(mockProviderOperations.topologyDG(anyString(), any(JsonNode.class)))
            .thenThrow(new RuntimeException("test exception"));

        WorkerImpl worker = new WorkerImpl(buildDmaapMessage(), mockEventHandler, mockProviderOperations);
        worker.run();

        verify(mockEventHandler).postStatus(anyString(), anyString());
    }


    @Test
    public void should_post_message_to_dmaap_on_successful_run() throws APPCException {

        JsonNode testOutputJsonNode = Mapper.toJsonNodeFromJsonString(jsonOutputBodyStr);
        when(mockProviderOperations.topologyDG(anyString(), any(JsonNode.class)))
            .thenReturn(testOutputJsonNode);

        WorkerImpl worker = new WorkerImpl(buildDmaapMessage(), mockEventHandler, mockProviderOperations);
        worker.run();

        verify(mockEventHandler).postStatus(anyString(), anyString());
    }


    private DmaapMessage buildDmaapMessage() {

        DmaapMessage dmaapMessage = new DmaapMessage();
        dmaapMessage.setRpcName("test");
        JsonNode jsonNode = Mapper.toJsonNodeFromJsonString(jsonInputBodyStr);
        dmaapMessage.setBody(jsonNode);
        return dmaapMessage;
    }
}