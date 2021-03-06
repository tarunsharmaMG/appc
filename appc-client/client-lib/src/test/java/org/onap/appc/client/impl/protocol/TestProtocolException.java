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
package org.onap.appc.client.impl.protocol;

import org.junit.Assert;
import org.junit.Test;

public class TestProtocolException {

    @Test
    public void TestProException() {
        try {
            throw new ProtocolException();
        } catch (ProtocolException protocolException) {
            Assert.assertEquals("org.onap.appc.client.impl.protocol.ProtocolException",
                    protocolException.getClass().getName());
        }
    }

    @Test
    public void TestProtocolException1() {
        try {
            throw new ProtocolException("ProtocolException");
        } catch (ProtocolException protocolException) {
            Assert.assertEquals("ProtocolException", protocolException.getMessage());
        }
    }

    @Test
    public void TestProtocolException2() {
        try {
            throw new ProtocolException("ProtocolException", new Throwable("Test Message"));
        } catch (ProtocolException protocolException) {
            Assert.assertEquals("ProtocolException", protocolException.getMessage());
            Assert.assertEquals("Test Message", protocolException.getCause().getMessage());
        }

    }

    @Test
    public void TestProtocolException3() {
        try {
            throw new ProtocolException(new Throwable());
        } catch (ProtocolException protocolException) {
            Assert.assertEquals("org.onap.appc.client.impl.protocol.ProtocolException",
                    protocolException.getClass().getName());
        }

    }

}
