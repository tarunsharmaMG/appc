/*-
 * ============LICENSE_START=======================================================
 * ONAP : APPC
 * ================================================================================
 * Copyright (C) 2018 AT&T Intellectual Property. All rights reserved.
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

package org.onap.appc.requesthandler.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class ActionIdentifierModelTest {
    private ActionIdentifierModel model;

    @Before
    public void setUp() {
        model = new ActionIdentifierModel();
    }

    @Test
    public void testVnfId() {
        assertNull(model.getVnfId());
        model.setVnfId("VnfId");
        assertNotNull(model.getVnfId());
        assertEquals(model.getVnfId(), "VnfId");
    }

    @Test
    public void testVnfcName() {
        assertNull(model.getVnfcName());
        model.setVnfcName("VnfcName");
        assertNotNull(model.getVnfcName());
        assertEquals(model.getVnfcName(), "VnfcName");
    }

    @Test
    public void testVfModuleId() {
        assertNull(model.getVfModuleId());
        model.setVfModuleId("VfModuleId");
        assertNotNull(model.getVfModuleId());
        assertEquals(model.getVfModuleId(), "VfModuleId");
    }

    @Test
    public void testVserverId() {
        assertNull(model.getVserverId());
        model.setVserverId("VserverId");
        assertNotNull(model.getVserverId());
        assertEquals(model.getVserverId(), "VserverId");
    }

    @Test
    public void testServiceInstanceId() {
        assertNull(model.getServiceInstanceId());
        model.setServiceInstanceId("ServiceInstanceId");
        assertNotNull(model.getServiceInstanceId());
        assertEquals(model.getServiceInstanceId(), "ServiceInstanceId");
    }
}
