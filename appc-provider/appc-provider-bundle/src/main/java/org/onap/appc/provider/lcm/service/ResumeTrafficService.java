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

package org.onap.appc.provider.lcm.service;

import org.opendaylight.yang.gen.v1.org.onap.appc.lcm.rev160108.Action;
import org.opendaylight.yang.gen.v1.org.onap.appc.lcm.rev160108.ResumeTrafficInput;
import org.opendaylight.yang.gen.v1.org.onap.appc.lcm.rev160108.ResumeTrafficOutput;
import org.opendaylight.yang.gen.v1.org.onap.appc.lcm.rev160108.ResumeTrafficOutputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.appc.lcm.rev160108.action.identifiers.ActionIdentifiers;
import org.opendaylight.yang.gen.v1.org.onap.appc.lcm.rev160108.common.header.CommonHeader;
import org.onap.appc.requesthandler.objects.RequestHandlerInput;
import org.onap.appc.executor.objects.LCMCommandStatus;
import org.onap.appc.util.JsonUtil;

import java.io.IOException;
import java.util.Map;
/**
 * Provide LCM command service for Resume VNF traffic
 */
public class ResumeTrafficService extends AbstractBaseService {

    /**
     * Constructor
     */
    public ResumeTrafficService() {
        super(Action.ResumeTraffic);
        logger.debug("ResumeTrafficService starts");
    }

    /**
     * Process the Resume request
     * @param input of ResumeTrafficInput from the REST API input
     * @return ResumeTrafficOutputBuilder which has the process results
     */
    public ResumeTrafficOutputBuilder process(ResumeTrafficInput input) {
    	CommonHeader commonHeader = input.getCommonHeader();
        ActionIdentifiers actionIdentifiers = input.getActionIdentifiers();

        validate(commonHeader, input.getAction(), actionIdentifiers);
        if (status == null) {
            proceedAction(input);
        }

        ResumeTrafficOutputBuilder outputBuilder = new ResumeTrafficOutputBuilder();
        outputBuilder.setStatus(status);
        outputBuilder.setCommonHeader(input.getCommonHeader());
        return outputBuilder;
    }

    /**
     * Validate the input.
     * Set Status if any error occurs.
     *
     * @param input of ResumeTrafficInput from the REST API input
     */
    void validate(CommonHeader commonHeader,
            Action action,
            ActionIdentifiers actionIdentifiers) {
		status = validateVnfId(commonHeader, action, actionIdentifiers);
		if (status != null) {
			return;
		}
	}

    /**
     * Proceed to action for the Resume VNF traffic.
     *
     * @param input of ResumeTrafficInput from the REST API input
     */
    void proceedAction(ResumeTrafficInput input) {
        RequestHandlerInput requestHandlerInput = getRequestHandlerInput(
            input.getCommonHeader(), input.getActionIdentifiers(), null, this.getClass().getName());
        if (requestHandlerInput != null) {
            executeAction(requestHandlerInput);
        }
    }
}