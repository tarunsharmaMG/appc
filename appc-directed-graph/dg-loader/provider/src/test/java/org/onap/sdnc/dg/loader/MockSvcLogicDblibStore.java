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

package org.onap.sdnc.dg.loader;

import java.util.Properties;
import org.onap.ccsdk.sli.core.sli.SvcLogicException;
import org.onap.ccsdk.sli.core.sli.SvcLogicGraph;
import org.onap.ccsdk.sli.core.sli.SvcLogicStore;

public class MockSvcLogicDblibStore implements SvcLogicStore {

    @Override
    public boolean hasGraph(String module, String rpc, String version, String mode) {
        return true;
    }

    @Override
    public SvcLogicGraph fetch(String module, String rpc, String version, String mode)
            throws SvcLogicException {
        SvcLogicGraph retVal = new SvcLogicGraph();
        retVal.setMode("sync");
        retVal.setModule("Appc");
        retVal.setRpc("unitTestDG");
        retVal.setVersion("4.0.0");
        return retVal;

    }

    @Override
    public void store(SvcLogicGraph graph) throws SvcLogicException {

    }

    @Override
    public void init(Properties props) throws SvcLogicException {
        // TODO Auto-generated method stub

    }


    public void registerNodeType(String nodeType) throws SvcLogicException {
        // TODO Auto-generated method stub

    }

    public void unregisterNodeType(String nodeType) throws SvcLogicException {
        // TODO Auto-generated method stub

    }


    public boolean isValidNodeType(String nodeType) throws SvcLogicException {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void delete(String module, String rpc, String version, String mode)
            throws SvcLogicException {
        // TODO Auto-generated method stub
    }

    @Override
    public void activate(SvcLogicGraph graph) throws SvcLogicException {
        // TODO Auto-generated method stub
    }
    public void activate(String module, String rpc, String version, String mode)
            throws SvcLogicException {
        // TODO Auto-generated method stub
    }
}
