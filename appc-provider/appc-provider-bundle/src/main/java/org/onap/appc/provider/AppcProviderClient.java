/*-
 * ============LICENSE_START=======================================================
 * ONAP : APPC
 * ================================================================================
 * Copyright (C) 2017-2018 AT&T Intellectual Property. All rights reserved.
 * ================================================================================
 * Copyright (C) 2017 Amdocs
 * ================================================================================
 * Modifications (C) 2019 Ericsson
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

package org.onap.appc.provider;

import com.att.eelf.configuration.EELFLogger;
import com.att.eelf.configuration.EELFManager;
import org.onap.appc.logging.LoggingConstants;
import org.onap.appc.logging.LoggingUtils;
import org.onap.appc.util.StringHelper;
import org.onap.ccsdk.sli.core.sli.SvcLogicException;
import org.onap.ccsdk.sli.core.sli.provider.SvcLogicService;
import org.slf4j.MDC;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Properties;

public class AppcProviderClient {

    private final EELFLogger LOG = EELFManager.getInstance().getApplicationLogger();
    private final EELFLogger metricsLogger = EELFManager.getInstance().getMetricsLogger();

    private final SvcLogicService svcLogic;

    public AppcProviderClient(final SvcLogicService svcLogicService) {
    	LOG.info("provider alert: appcprovider client init");
    	this.svcLogic = svcLogicService;
    }

    public boolean hasGraph(String module, String rpc, String version, String mode) throws SvcLogicException {
        LOG.debug(String.format("Checking for graph. %s %s %s %s", module, rpc, version, mode));
        return (svcLogic.hasGraph(module, rpc, version, mode));
    }

    public Properties execute(String module, String rpc, String version, String mode, Properties parms)
        throws SvcLogicException {

        /*
         * Set properties for Metrics Logger
         */
        Date startTimestamp = new Date();
        Instant startTimestampInstant = startTimestamp.toInstant();
        String startTimeStr = LoggingUtils.generateTimestampStr(startTimestampInstant);
        MDC.put(LoggingConstants.MDCKeys.TARGET_ENTITY, "sli");
        MDC.put(LoggingConstants.MDCKeys.TARGET_SERVICE_NAME, "execute");
        MDC.put(LoggingConstants.MDCKeys.CLASS_NAME, "org.onap.appc.provider.AppcProviderClient");

        LOG.debug("Parameters passed to SLI: " + StringHelper.propertiesToString(parms));
        metricsLogger.info("Parameters passed to SLI: " + StringHelper.propertiesToString(parms));

        Properties respProps = svcLogic.execute(module, rpc, version, mode, parms);

        /*
         * Set End time for Metrics Logger
         */
        Date endTimestamp = new Date();
        Instant endTimestampInstant = endTimestamp.toInstant();
        String endTimeStr = LoggingUtils.generateTimestampStr(endTimestampInstant);
        long duration = ChronoUnit.MILLIS.between(startTimestampInstant, endTimestampInstant);
        String durationStr = String.valueOf(duration);
        MDC.put(LoggingConstants.MDCKeys.BEGIN_TIMESTAMP, startTimeStr);
        MDC.put(LoggingConstants.MDCKeys.END_TIMESTAMP, endTimeStr);
        MDC.put(LoggingConstants.MDCKeys.ELAPSED_TIME, durationStr);

        LOG.debug("Parameters returned by SLI: " + StringHelper.propertiesToString(respProps));
        metricsLogger.info("Parameters returned by SLI: " + StringHelper.propertiesToString(respProps));

        return respProps;
    }
}
