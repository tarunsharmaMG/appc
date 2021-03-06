/*
* ============LICENSE_START=======================================================
* ONAP : APPC
* ================================================================================
* Copyright 2018 TechMahindra
*=================================================================================
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
* ============LICENSE_END=========================================================
*/
package org.onap.appc.adapter.ssh;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestSshConnectionDetails {

    private SshConnectionDetails sshConnectionDetails;

    @Before
    public void setUp() {
        sshConnectionDetails = new SshConnectionDetails();
    }

    @Test
    public void testGetHost() {
        sshConnectionDetails.setHost("localhost");
        assertNotNull(sshConnectionDetails.getHost());
        assertEquals(sshConnectionDetails.getHost(), "localhost");
    }

    @Test
    public void testGetPort() {
        sshConnectionDetails.setPort(8080);
        assertNotNull(sshConnectionDetails.getPort());
        assertEquals(sshConnectionDetails.getPort(), 8080);
    }

    @Test
    public void testGetUsername() {
        sshConnectionDetails.setUsername("username");
        assertNotNull(sshConnectionDetails.getUsername());
        assertEquals(sshConnectionDetails.getUsername(), "username");
    }

    @Test
    public void testGetPassword() {
        sshConnectionDetails.setPassword("password");
        assertNotNull(sshConnectionDetails.getPassword());
        assertEquals(sshConnectionDetails.getPassword(), "password");
    }
}
