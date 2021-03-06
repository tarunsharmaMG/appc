/*-
 * ============LICENSE_START=======================================================
 * ONAP : APPC
 * ================================================================================
 * Copyright (C) 2017-2019 AT&T Intellectual Property. All rights reserved.
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

package org.onap.appc.flow.controller.ResponseHandlerImpl;

import org.apache.commons.lang3.StringUtils;
import com.att.eelf.configuration.EELFLogger;
import com.att.eelf.configuration.EELFManager;
import org.onap.appc.flow.controller.data.Response;
import org.onap.appc.flow.controller.data.ResponseAction;
import org.onap.appc.flow.controller.data.Transaction;
import org.onap.ccsdk.sli.core.sli.SvcLogicContext;

public class DefaultResponseHandler {

    private static final EELFLogger log = EELFManager.getInstance().getLogger(DefaultResponseHandler.class);

    public ResponseAction handlerResponse(Transaction transaction, SvcLogicContext ctx) {
        log.info("Transaction Input params " + transaction.toString());
        ResponseAction responseAction = new ResponseAction();
        if (transaction.getResponses() != null && !transaction.getResponses().isEmpty()) {
            for (Response response : transaction.getResponses()) {
                if ((StringUtils.isNotBlank(ctx.getAttribute("error-code"))
                        && !StringUtils.equals(ctx.getAttribute("error-code"), "400"))
                        || ((StringUtils.isNotBlank(transaction.getStatusCode()))
                                && !StringUtils.equals(transaction.getStatusCode(), "400"))) {

                    responseAction = response.getResponseAction();
                    break;
                }
            }
        }
        return responseAction;
    }
}
