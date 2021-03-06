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

package org.onap.appc.provider.lcm.util;

import org.opendaylight.yang.gen.v1.org.onap.appc.lcm.rev160108.Action;
import org.opendaylight.yang.gen.v1.org.onap.appc.lcm.rev160108.common.header.CommonHeader;
import org.opendaylight.yang.gen.v1.org.onap.appc.lcm.rev160108.status.Status;
import org.opendaylight.yang.gen.v1.org.onap.appc.lcm.rev160108.status.StatusBuilder;
import org.onap.appc.Constants;
import org.onap.appc.configuration.Configuration;
import org.onap.appc.configuration.ConfigurationFactory;
import org.onap.appc.executor.objects.LCMCommandStatus;
import org.onap.appc.executor.objects.Params;
import org.onap.appc.i18n.Msg;
import com.att.eelf.configuration.EELFLogger;
import com.att.eelf.configuration.EELFManager;
import com.att.eelf.i18n.EELFResourceManager;

public class ValidationService {

    private static class ValidationServiceHolder {
        private static final ValidationService INSTANCE = new ValidationService();
    }

    private final EELFLogger logger = EELFManager.getInstance().getLogger(ValidationService.class);
    private Configuration configuration = ConfigurationFactory.getConfiguration();

    public static ValidationService getInstance(){
        return ValidationServiceHolder.INSTANCE;
    }

    public Status validateInput(CommonHeader commonHeader, Action action , String rpcName) {
        if (!isEmpty(commonHeader)
            && !isEmpty(commonHeader.getApiVer())
            && !isEmpty(commonHeader.getTimestamp())
            && !isEmpty(commonHeader.getRequestId())
            && !isEmpty(commonHeader.getOriginatorId())
            && !isEmpty(action)) {
            return null;
        }

        StringBuilder paramName = new StringBuilder();
        if(isEmpty(commonHeader)){
            paramName.append("common-header");
        } else {
            if (isEmpty(commonHeader.getApiVer())) {
                paramName.append("api-ver");
            }
            if (isEmpty(commonHeader.getTimestamp())) {
                paramName.append(isEmpty(paramName) ? "timestamp" : " , timestamp");
            }
            if (isEmpty(commonHeader.getRequestId())) {
                paramName.append(isEmpty(paramName)  ? "request-id" : " , request-id");
            }
            if (isEmpty(commonHeader.getOriginatorId())) {
                paramName.append(isEmpty(paramName)  ? "originator-id" : " , originator-id");
            }
        }
        if (isEmpty(action)) {
            paramName.append(isEmpty(paramName)  ? "action" : " , action");
        }

        logger.info("Mandatory parameter/s" + paramName.toString() + " is/are missing");
        String reason = EELFResourceManager.format(
            Msg.NULL_OR_INVALID_ARGUMENT,
            configuration.getProperty(Constants.PROPERTY_APPLICATION_NAME),
            rpcName,
            paramName.toString(),
            "");
        logger.error(reason);

        LCMCommandStatus lcmCommandStatus = LCMCommandStatus.MISSING_MANDATORY_PARAMETER;
        Params params = new Params().addParam("paramName", paramName.toString());

        StatusBuilder status = new StatusBuilder();
        status.setCode(lcmCommandStatus.getResponseCode());
        status.setMessage(lcmCommandStatus.getFormattedMessage(params));
        return status.build();
    }

    private boolean isEmpty(Object object) {
        return null == object || "".equalsIgnoreCase(object.toString());
    }
}
