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

package org.onap.sdnc.config.audit;

import java.util.LinkedList;
import java.util.List;

import org.onap.sdnc.config.audit.node.CompareNode;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.att.eelf.configuration.EELFLogger;
import com.att.eelf.configuration.EELFManager;

public class SliAuditActivator implements BundleActivator{

    private List<ServiceRegistration> registrations = new LinkedList<ServiceRegistration>();


    private static final EELFLogger log = EELFManager.getInstance().getLogger(SliAuditActivator.class);

    @Override
    public void start(BundleContext ctx) throws Exception
    {

        CompareNode compareNodePlugin = new CompareNode();
        log.info("Registering service "+ compareNodePlugin.getClass().getName());
        registrations.add(ctx.registerService(compareNodePlugin.getClass().getName(), compareNodePlugin, null));

    }
    @Override
    public void stop(BundleContext arg0) throws Exception
    {
        for (ServiceRegistration registration: registrations)
        {
            registration.unregister();
            registration = null;
        }

    }

}
