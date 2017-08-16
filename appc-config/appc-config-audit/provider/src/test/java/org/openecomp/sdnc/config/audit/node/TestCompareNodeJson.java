/*-
 * ============LICENSE_START=======================================================
 * ONAP : APP-C
 * ================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property.  All rights reserved.
 * ================================================================================
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

package org.openecomp.sdnc.config.audit.node;

import java.util.HashMap;

import org.junit.Test;
import org.openecomp.sdnc.config.audit.node.CompareNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.openecomp.sdnc.sli.SvcLogicContext;
import org.openecomp.sdnc.sli.SvcLogicException;

public class TestCompareNodeJson
{
private static final Logger log = LoggerFactory.getLogger(TestCompareNodeJson.class);

    //@Test
    public void TestCompareJsonForSamePayload()
    {
        SvcLogicContext ctx  = new SvcLogicContext();
        HashMap<String, String> testMap = new HashMap<String, String>();
        CompareNode cmp  = new CompareNode();
        try
        {
            String controlJson = "{\n\"input\": {\n   \"appc-request-header\": {\n       \"svc-request-id\": \"000000000\", \n \"svc-action\": \"prepare\"   \n }, \n\"request-information\": {\n \"request-id\": \"000000000\", \n\"request-action\": \"VsbgServiceActivateRequest\", \n\"request-sub-action\": \"PREPARE\",  \n \"source\": \"Version2\" \n} \n} \n}";
            String testJson = "{\n\"input\": {\n  \"appc-request-header\": {\n \"svc-request-id\": \"000000000\", \n \"svc-action\": \"prepare\"   \n }, \n\"request-information\": {\n \"request-id\": \"000000000\", \n\"request-action\": \"VsbgServiceActivateRequest\", \n\"request-sub-action\": \"PREPARE\",  \n \"source\": \"Version2\" \n} \n} \n}";
            testMap.put("compareDataType", "RestConf");
            testMap.put("sourceData", controlJson);
            testMap.put("targetData", testJson);
            cmp.compare(testMap, ctx);
            assert(ctx.getAttribute("STATUS").equals("SUCCESS"));
        }
        catch (SvcLogicException e)
        {
            e.printStackTrace();
        }
    }

    //@Test
    public void TestCompareJsonFordifferentPayload()
    {
        SvcLogicContext ctx  = new SvcLogicContext();
        HashMap<String, String> testMap = new HashMap<String, String>();
        CompareNode cmp  = new CompareNode();
        try
        {
            String controlJson = "{\n\"input\": {\n   \"appc-request-header\": {\n       \"svc-request-id\": \"000000000\", \n \"svc-action\": \"prepare\"   \n }, \n\"request-information\": {\n \"request-id\": \"000000000\", \n\"request-action\": \"VsbgServiceActivateRequest\", \n\"request-sub-action\": \"PREPARE\",  \n \"source\": \"Version2\" \n} \n} \n}";
            String testJson = "{\n\"input\": {\n  \"appc-request-header\": { \n \"svc-action\": \"prepare\"   \n }, \n\"request-information\": {\n \"request-id\": \"0000000000\", \n\"request-action\": \"VsbgServiceActivateRequest\", \n\"request-sub-action\": \"PREPARE\",  \n \"source\": \"Version2\" \n} \n} \n}";
            testMap.put("compareDataType", "RestConf");
            testMap.put("sourceData", controlJson);
            testMap.put("targetData", testJson);
            cmp.compare(testMap, ctx);
            assert(ctx.getAttribute("STATUS").equals("FAILURE"));
        }
        catch (SvcLogicException e)
        {
            e.printStackTrace();
        }
    }
}