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

/**
 *
 */
package org.onap.appc.executor;


import org.onap.appc.domainmodel.lcm.RuntimeContext;
import org.onap.appc.exceptions.APPCException;



public interface CommandExecutor {
    /**
     * Execute given command
     * Create command request and enqueue it for execution.
     * @param commandHeaderInput Contains CommandHeader,  command , target Id , payload and conf ID (optional)
     * @throws APPCException in case of error.
     */
    void executeCommand(RuntimeContext commandHeaderInput) throws APPCException;
}