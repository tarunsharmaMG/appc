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

package org.onap.appc.design.validator;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.onap.appc.design.data.ArtifactInfo;
import org.onap.appc.design.data.DesignInfo;
import org.onap.appc.design.data.DesignRequest;
import org.onap.appc.design.data.DesignResponse;
import org.onap.appc.design.data.StatusInfo;

public class TestDesigndata {

    DesignResponse dr = new DesignResponse();

    @Test
    public void testSetUserID() {
        dr.setUserId("00000");
        dr.getUserId();
    }

    @Test
    public void testSetDesignInfoList() {
        DesignInfo di = new DesignInfo();
        List<DesignInfo> li = new ArrayList<DesignInfo>();
        di.setAction("TestAction");
        di.setArtifact_name("TestName");
        di.setArtifact_type("TestType");
        di.setInCart("TestCart");
        di.setProtocol("TestProtocol");
        di.setVnf_type("TestVNF");
        di.setVnfc_type("TestVNFC");
        li.add(di);
        dr.setDesignInfoList(li);
    }

    @Test
    public void testSetArtifactInfo() {
        ArtifactInfo ai = new ArtifactInfo();
        List<ArtifactInfo> li = new ArrayList<ArtifactInfo>();
        ai.setArtifact_content("TestContent");
        li.add(ai);
        dr.setArtifactInfo(li);
    }

    @Test
    public void testStatusInfo() {
        StatusInfo si = new StatusInfo();
        List<StatusInfo> li = new ArrayList<StatusInfo>();
        si.setAction("TestAction");
        si.setAction_status("TestActionStatus");
        si.setArtifact_status("TestArtifactStatus");
        si.setVnf_type("TestVNF");
        si.setVnfc_type("TestVNFC");
        li.add(si);
        dr.setStatusInfoList(li);
    }

    @Test
    public void testDesignRequest() {
        DesignRequest dreq = new DesignRequest();
        dreq.setAction("TestAction");
        dreq.setArtifact_contents("TestContent");
        dreq.setArtifact_name("TestName");
        dreq.setProtocol("TestProtocol");
        dreq.setUserId("0000");
        dreq.setVnf_type("testvnf");
        dreq.setVnfc_type("testvnfc");
        dreq.getAction();
        dreq.getArtifact_contents();
        dreq.getArtifact_name();
        dreq.getProtocol();
        dreq.getUserId();
        dreq.getVnf_type();
        dreq.getVnfc_type();
        dreq.toString();
    }
}
