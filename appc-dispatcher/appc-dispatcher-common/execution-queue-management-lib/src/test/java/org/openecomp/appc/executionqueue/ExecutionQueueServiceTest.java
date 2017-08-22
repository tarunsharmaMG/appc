/*-
 * ============LICENSE_START=======================================================
 * ONAP : APPC
 * ================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property. All rights reserved.
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
 * ECOMP is a trademark and service mark of AT&T Intellectual Property.
 * ============LICENSE_END=========================================================
 */

package org.openecomp.appc.executionqueue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.openecomp.appc.exceptions.APPCException;
import org.openecomp.appc.executionqueue.helper.Util;
import org.openecomp.appc.executionqueue.impl.ExecutionQueueServiceImpl;
import org.openecomp.appc.executionqueue.impl.QueueManager;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;

@RunWith(PowerMockRunner.class)
public class ExecutionQueueServiceTest {

    @InjectMocks
    private ExecutionQueueServiceImpl service;
    @Spy
    private QueueManager queueManager = new QueueManager();
    @Spy
    private Util executionQueueUtil = new Util();

    @Before
    public void setup() {
        Mockito.doReturn(true).when(queueManager).enqueueTask(any());
    }

    @Test
    public void testPositiveFlow() {
        Message message = new Message();
        try {
            service.putMessage(message);
            Mockito.verify(queueManager, times(1)).enqueueTask(any());
        } catch (APPCException e) {
            Assert.fail(e.toString());
        }
    }
}