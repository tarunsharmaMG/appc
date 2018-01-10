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
package org.onap.appc.adapter.iaas.provider.operation.impl;
import static org.onap.appc.adapter.utils.Constants.ADAPTER_NAME;
import java.util.Map;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.onap.appc.Constants;
import org.onap.appc.adapter.iaas.ProviderAdapter;
import org.onap.appc.adapter.iaas.impl.IdentityURL;
import org.onap.appc.adapter.iaas.impl.RequestContext;
import org.onap.appc.adapter.iaas.impl.RequestFailedException;
import org.onap.appc.adapter.iaas.impl.VMURL;
import org.onap.appc.adapter.iaas.provider.operation.common.enums.Operation;
import org.onap.appc.adapter.iaas.provider.operation.impl.base.ProviderServerOperation;
import org.onap.appc.exceptions.APPCException;
import org.onap.appc.i18n.Msg;
import org.onap.ccsdk.sli.core.sli.SvcLogicContext;
import com.att.cdp.exceptions.ZoneException;
import com.att.cdp.zones.Context;
import com.att.cdp.zones.VolumeService;
import com.att.cdp.zones.model.ModelObject;
import com.att.cdp.zones.model.Server;
import com.att.cdp.zones.model.Volume;
import com.att.eelf.configuration.EELFLogger;
import com.att.eelf.configuration.EELFManager;
import com.att.eelf.i18n.EELFResourceManager;
import static org.onap.appc.adapter.iaas.provider.operation.common.enums.Operation.ATTACHVOLUME_SERVICE;;

public class AttachVolumeServer extends ProviderServerOperation {
    private final EELFLogger logger = EELFManager.getInstance().getLogger(AttachVolumeServer.class);
    private Server attachVolume(Map<String, String> params, SvcLogicContext ctx) throws APPCException {
        Server server = null;
        RequestContext rc = new RequestContext(ctx);
        rc.isAlive();
        String appName = configuration.getProperty(Constants.PROPERTY_APPLICATION_NAME);
        String vm_url = params.get(ProviderAdapter.PROPERTY_INSTANCE_URL);
        String volumeid = params.get(ProviderAdapter.VOLUME_ID);
        String device = params.get(ProviderAdapter.DEVICE);
        VMURL vm = VMURL.parseURL(vm_url);
        Context context = null;
        try {
            if (validateVM(rc, appName, vm_url, vm))
                return null;
            IdentityURL ident = IdentityURL.parseURL(params.get(ProviderAdapter.PROPERTY_IDENTITY_URL));
            String identStr = (ident == null) ? null : ident.toString();
            String vol_id = (volumeid == null) ? null : volumeid.toString();
            String msg;
            context = getContext(rc, vm_url, identStr);
            if (context != null) {
                rc.reset();
                server = lookupServer(rc, context, vm.getServerId());
                logger.debug(Msg.SERVER_FOUND, vm_url, context.getTenantName(), server.getStatus().toString());
                    VolumeService vs = context.getVolumeService();
                    vs.getVolumes(server);;
                    Volume vol = new Volume();
                    vol.setId(vol_id);
                    logger.info("Server status: "+server.getStatus());
                    Map volms = server.getVolumes();
                    logger.info("list of attachments");
                    logger.info(volms.size()+"initial volumes");
                    logger.info(vol.getId());
                    if(server.getVolumes().containsValue(vol_id))
                    {
                        logger.info("Alreday volumes exists:");
                         logger.info( volms.size()+"volumes size if exists");
                    }
                    else
                    {
                    server.attachVolume(vol, device);
                    logger.info( volms.size()+"volumes size after attaching volume");
                    }
                context.close();
                doSuccess(rc);
                ctx.setAttribute("VOLUME_STATUS", "SUCCESS");
            } else {
                ctx.setAttribute("VOLUME_STATUS", "CONTEXT_NOT_FOUND");
            }
        } catch (ZoneException e) {
            String msg = EELFResourceManager.format(Msg.SERVER_NOT_FOUND, e, vm_url);
            logger.error(msg);
            doFailure(rc, HttpStatus.NOT_FOUND_404, msg);
        } catch (RequestFailedException e) {
            doFailure(rc, e.getStatus(), e.getMessage());
        } catch (Exception ex) {
            String msg = EELFResourceManager.format(Msg.SERVER_OPERATION_EXCEPTION, ex, ex.getClass().getSimpleName(),
                    ATTACHVOLUME_SERVICE.toString(), vm_url, context == null ? "Unknown" : context.getTenantName());
            logger.error(msg, ex);
            doFailure(rc, HttpStatus.INTERNAL_SERVER_ERROR_500, msg);
        }
        return server;
    }
    @Override
    protected ModelObject executeProviderOperation(Map<String, String> params, SvcLogicContext context)
            throws APPCException {
        setMDC(Operation.ATTACHVOLUME_SERVICE.toString(), "App-C IaaS Adapter:attachVolume", ADAPTER_NAME);
        logOperation(Msg.ATTACHINGVOLUME_SERVER, params, context);
        return attachVolume(params, context);
    }
}
