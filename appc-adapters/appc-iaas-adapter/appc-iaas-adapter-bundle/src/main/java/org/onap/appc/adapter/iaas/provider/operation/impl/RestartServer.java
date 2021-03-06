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

package org.onap.appc.adapter.iaas.provider.operation.impl;

import com.att.cdp.exceptions.ResourceNotFoundException;
import com.att.cdp.exceptions.ZoneException;
import com.att.cdp.zones.Context;
import com.att.cdp.zones.model.ModelObject;
import com.att.cdp.zones.model.Server;
import com.att.eelf.configuration.EELFLogger;
import com.att.eelf.configuration.EELFManager;
import com.att.eelf.i18n.EELFResourceManager;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.onap.appc.Constants;
import org.onap.appc.adapter.iaas.ProviderAdapter;
import org.onap.appc.adapter.iaas.impl.IdentityURL;
import org.onap.appc.adapter.iaas.impl.RequestContext;
import org.onap.appc.adapter.iaas.impl.RequestFailedException;
import org.onap.appc.adapter.iaas.impl.VMURL;
import org.onap.appc.adapter.iaas.provider.operation.common.constants.Property;
import org.onap.appc.adapter.iaas.provider.operation.common.enums.Outcome;
import org.onap.appc.adapter.iaas.provider.operation.impl.base.ProviderServerOperation;
import org.onap.appc.exceptions.UnknownProviderException;
import org.onap.appc.i18n.Msg;
import org.onap.ccsdk.sli.core.sli.SvcLogicContext;
import org.slf4j.MDC;
import org.onap.appc.logging.LoggingConstants;
import org.onap.appc.logging.LoggingUtils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import static org.onap.appc.adapter.iaas.provider.operation.common.enums.Operation.RESTART_SERVICE;
import static org.onap.appc.adapter.utils.Constants.ADAPTER_NAME;

public class RestartServer extends ProviderServerOperation {

    private static final EELFLogger logger = EELFManager.getInstance().getLogger(RestartServer.class);
    private static EELFLogger metricsLogger = EELFManager.getInstance().getMetricsLogger();

    /**
     * This method handles the case of restarting a server once we have found the
     * server and have obtained the abstract representation of the server via the
     * context (i.e., the "Server" object from the CDP-Zones abstraction).
     *
     * @param rc
     *            The request context that manages the state and recovery of the
     *            request for the life of its processing.
     * @param server
     *            The server object representing the server we want to operate on
     * @throws ZoneException
     *             when error occurs.
     * @throws RequestFailedException
     *             when server status is error.
     */
    @SuppressWarnings("nls")
    private void restartServer(RequestContext rc, Server server, SvcLogicContext ctx)
            throws ZoneException, RequestFailedException {
        /*
         * Pending is a bit of a special case. If we find the server is in a pending
         * state, then the provider is in the process of changing state of the server.
         * So, lets try to wait a little bit and see if the state settles down to one we
         * can deal with. If not, then we have to fail the request.
         */
        String msg;
        if (server.getStatus().equals(Server.Status.PENDING)) {
            waitForStateChange(rc, server, Server.Status.READY, Server.Status.RUNNING, Server.Status.ERROR,
                    Server.Status.SUSPENDED, Server.Status.PAUSED);
        }
        setTimeForMetricsLogger("restart server");
        String skipHypervisorCheck = configuration.getProperty(Property.SKIP_HYPERVISOR_CHECK);
        if (skipHypervisorCheck == null && ctx != null) {
            skipHypervisorCheck = ctx.getAttribute(ProviderAdapter.SKIP_HYPERVISOR_CHECK);
        }
        // Always perform Virtual Machine/Hypervisor Status/Network checks
        // unless the skip is set to true
        if (skipHypervisorCheck == null || (!skipHypervisorCheck.equalsIgnoreCase("true"))) {
            // Check of the Hypervisor for the VM Server is UP and reachable
            checkHypervisor(server);
        }
        /*
         * We determine what to do based on the current state of the server
         */
        switch (server.getStatus()) {
        case DELETED:
            // Nothing to do, the server is gone
            msg = EELFResourceManager.format(Msg.SERVER_DELETED, server.getName(), server.getId(), server.getTenantId(),
                    "restarted");
            generateEvent(rc, false, msg);
            logger.error(msg);
            metricsLogger.error(msg);
            break;
        case RUNNING:
            // Attempt to stop and start the server
            stopServer(rc, server);
            startServer(rc, server);
            generateEvent(rc, true, Outcome.SUCCESS.toString());
            metricsLogger.info("Server status: RUNNING");
            break;
        case ERROR:
            msg = EELFResourceManager.format(Msg.SERVER_ERROR_STATE, server.getName(), server.getId(),
                    server.getTenantId(), "restart");
            generateEvent(rc, false, msg);
            logger.error(msg);
            metricsLogger.error(msg);
            throw new RequestFailedException("Restart Server", msg, HttpStatus.METHOD_NOT_ALLOWED_405, server);
        case READY:
            // Attempt to start the server
            startServer(rc, server);
            generateEvent(rc, true, Outcome.SUCCESS.toString());
            metricsLogger.info("Server status: READY");
            break;
        case PAUSED:
            // if paused, un-pause it
            unpauseServer(rc, server);
            generateEvent(rc, true, Outcome.SUCCESS.toString());
            metricsLogger.info("Server status: PAUSED");
            break;
        case SUSPENDED:
            // Attempt to resume the suspended server
            resumeServer(rc, server);
            generateEvent(rc, true, Outcome.SUCCESS.toString());
            metricsLogger.info("Server status: SUSPENDED");
            break;
        default:
            // Hmmm, unknown status, should never occur
            msg = EELFResourceManager.format(Msg.UNKNOWN_SERVER_STATE, server.getName(), server.getId(),
                    server.getTenantId(), server.getStatus().name());
            generateEvent(rc, false, msg);
            logger.error(msg);
            metricsLogger.error(msg);
            break;
        }
    }

    /**
     * This method is used to restart an existing virtual machine given the fully
     * qualified URL of the machine.
     * <p>
     * The fully qualified URL contains enough information to locate the appropriate
     * server. The URL is of the form
     * 
     * <pre>
     *  [scheme]://[host[:port]] / [path] / [tenant_id] / servers / [vm_id]
     * </pre>
     * 
     * Where the various parts of the URL can be parsed and extracted and used to
     * locate the appropriate service in the provider service catalog. This then
     * allows us to open a context using the CDP abstraction, obtain the server by
     * its UUID, and then perform the restart.
     * </p>
     *
     * @throws UnknownProviderException
     *             If the provider cannot be found
     * @throws IllegalArgumentException
     *             if the expected argument(s) are not defined or are invalid
     * @see org.onap.appc.adapter.iaas.ProviderAdapter#restartServer(java.util.Map,
     *      org.onap.ccsdk.sli.core.sli.SvcLogicContext)
     */
    @SuppressWarnings("nls")
    private Server restartServer(Map<String, String> params, SvcLogicContext ctx)
            throws UnknownProviderException, IllegalArgumentException {
        Server server = null;
        RequestContext rc = new RequestContext(ctx);
        rc.isAlive();
        String appName = configuration.getProperty(Constants.PROPERTY_APPLICATION_NAME);
        /*
         * Set Time for Metrics Logger
         */
        setTimeForMetricsLogger("GET server status");
        ctx.setAttribute("RESTART_STATUS", "ERROR");
        try {
            validateParametersExist(params, ProviderAdapter.PROPERTY_INSTANCE_URL,
                    ProviderAdapter.PROPERTY_PROVIDER_NAME);
            String vm_url = params.get(ProviderAdapter.PROPERTY_INSTANCE_URL);
            VMURL vm = VMURL.parseURL(vm_url);
            if (validateVM(rc, appName, vm_url, vm))
                return null;
            IdentityURL ident = IdentityURL.parseURL(params.get(ProviderAdapter.PROPERTY_IDENTITY_URL));
            String identStr = (ident == null) ? null : ident.toString();
            Context context = null;
            String tenantName = "Unknown";// to be used also in case of exception
            try {
                context = getContext(rc, vm_url, identStr);
                if (context != null) {
                    tenantName = context.getTenantName();// this varaible also is used in case of exception
                    rc.reset();
                    server = lookupServer(rc, context, vm.getServerId());
                    logger.debug(Msg.SERVER_FOUND, vm_url, tenantName, server.getStatus().toString());
                    rc.reset();
                    restartServer(rc, server, ctx);
                    context.close();
                    doSuccess(rc);
                    ctx.setAttribute("RESTART_STATUS", "SUCCESS");
                    String msg = EELFResourceManager.format(Msg.SUCCESS_EVENT_MESSAGE, "RestartServer", vm_url);
                    ctx.setAttribute(org.onap.appc.Constants.ATTRIBUTE_SUCCESS_MESSAGE, msg);
                }
            } catch (RequestFailedException e) {
                doFailure(rc, e.getStatus(), e.getMessage());
            } catch (ResourceNotFoundException e) {
                String msg = EELFResourceManager.format(Msg.SERVER_NOT_FOUND, e, vm_url);
                logger.error(msg);
                metricsLogger.error(msg);
                doFailure(rc, HttpStatus.NOT_FOUND_404, msg);
            } catch (Exception e1) {
                String msg = EELFResourceManager.format(Msg.SERVER_OPERATION_EXCEPTION, e1,
                        e1.getClass().getSimpleName(), RESTART_SERVICE.toString(), vm_url, tenantName);
                logger.error(msg, e1);
                metricsLogger.error(msg, e1);
                doFailure(rc, HttpStatus.INTERNAL_SERVER_ERROR_500, msg);
            }
        } catch (RequestFailedException e) {
            doFailure(rc, e.getStatus(), e.getMessage());
        }
        return server;
    }

    @Override
    protected ModelObject executeProviderOperation(Map<String, String> params, SvcLogicContext context)
            throws UnknownProviderException {
        setMDC(RESTART_SERVICE.toString(), "App-C IaaS Adapter:Restart", ADAPTER_NAME);
        logOperation(Msg.RESTARTING_SERVER, params, context);
        setTimeForMetricsLogger("execute restart");
        metricsLogger.info("Executing Provider Operation: Restart");
        return restartServer(params, context);
    }

    private void setTimeForMetricsLogger(String targetServiceName) {
        String timestamp = LoggingUtils.generateTimestampStr(((Date) new Date()).toInstant());
        MDC.put(LoggingConstants.MDCKeys.BEGIN_TIMESTAMP, timestamp);
        MDC.put(LoggingConstants.MDCKeys.END_TIMESTAMP, timestamp);
        MDC.put(LoggingConstants.MDCKeys.ELAPSED_TIME, "0");
        MDC.put(LoggingConstants.MDCKeys.STATUS_CODE, LoggingConstants.StatusCodes.COMPLETE);
        MDC.put(LoggingConstants.MDCKeys.TARGET_ENTITY, "cdp");
        MDC.put(LoggingConstants.MDCKeys.TARGET_SERVICE_NAME, targetServiceName);
        MDC.put(LoggingConstants.MDCKeys.CLASS_NAME,
                "org.onap.appc.adapter.iaas.provider.operation.impl.RestartServer");
    }
}
