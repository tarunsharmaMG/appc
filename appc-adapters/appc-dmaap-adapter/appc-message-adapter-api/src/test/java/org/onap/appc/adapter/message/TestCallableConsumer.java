/*
 * ============LICENSE_START==========================================
 *  org.onap.music
 * ===================================================================
 *  Copyright (c) 2019 IBM.
 * ===================================================================
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * 
 * ============LICENSE_END=============================================
 * ====================================================================
 */
package org.onap.appc.adapter.message;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestCallableConsumer {

    CallableConsumer callableConsumer;

    @Mock
    private Consumer consumer;

    private List<String> list;
    private int waitMs = 15000;
    private int limit = 1000;
    private int maxLife = 25000;

    @Before
    public void setUp() {
        list = new ArrayList<String>();
        callableConsumer = new CallableConsumer(consumer, waitMs, limit);
    }

    @Test
    public void testCallableConsumer() {
        CallableConsumer callableConsumer = new CallableConsumer(consumer);
        Mockito.when(consumer.fetch()).thenReturn(list);
        assertEquals(list, callableConsumer.call());
    }

    @Test
    public void testCall() {
        Mockito.when(consumer.fetch()).thenReturn(list);
        assertEquals(list, callableConsumer.call());
    }

    @Test
    public void testGetMaxLife() {
        assertEquals(maxLife, callableConsumer.getMaxLife());
    }

}
