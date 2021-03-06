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

package org.onap.appc.statemachine.impl.readers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.onap.appc.statemachine.objects.Event;
import org.onap.appc.statemachine.objects.State;
import org.onap.appc.statemachine.objects.StateMachineMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AppcOamMetaDataReaderTest {
    private List<String> expectedStateNames = new ArrayList<>();
    private List<String> expectedEventNames = new ArrayList<>();

    private StateMachineMetadata stateMachineMetadata = new AppcOamMetaDataReader().readMetadata();

    @Before
    public void setUp() throws Exception {
        for (AppcOamStates appcOamStates : AppcOamStates.values()) {
            expectedStateNames.add(appcOamStates.toString());
        }
        for (AppcOamMetaDataReader.AppcOperation appcOperation : AppcOamMetaDataReader.AppcOperation.values()) {
            expectedEventNames.add(appcOperation.toString());
        }
    }

    @Test
    public void testReadMetadataForState() throws Exception {
        Set<State> stateSet = stateMachineMetadata.getStates();
        for (State state : stateSet) {
            String eventName = state.getStateName();
            Assert.assertTrue(String.format("Event(%s) should exist in expectedEventNames", eventName),
                    expectedStateNames.contains(eventName));
        }
    }

    @Test
    public void testReadMetadataForEvent() throws Exception {
        Set<Event> eventSet = stateMachineMetadata.getEvents();
        for (Event event : eventSet) {
            String eventName = event.getEventName();
            Assert.assertTrue(String.format("Event(%s) should exist in expectedEventNames", eventName),
                    expectedEventNames.contains(eventName));
        }
    }

}
