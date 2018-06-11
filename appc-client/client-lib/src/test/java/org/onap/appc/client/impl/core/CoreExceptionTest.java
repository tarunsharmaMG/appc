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

package org.onap.appc.client.impl.core;

import static org.junit.Assert.*;
import org.junit.Test;

public class CoreExceptionTest {
    @Test
    public void testCoreException() {
        CoreException ce = new CoreException();
        assertNotNull(ce);
    }

    @Test
    public void testCoreExceptionString() {
        CoreException ce = new CoreException(new String("testCoreExceptionString"));
        assertNotNull(ce.getMessage());
    }

    @Test
    public void testCoreExceptionStringThrowable() {
        Throwable tr = new Throwable();
        CoreException ce = new CoreException("abc", tr);
        assertNotNull(ce.getMessage());
    }

    @Test
    public void testCoreExceptionThrowable() {
        CoreException ce = new CoreException(new Throwable());
        assertNotNull(ce.getMessage());
    }
}