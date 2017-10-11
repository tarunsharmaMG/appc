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

package org.openecomp.appc.adapter.openstack.heat.model;

import javax.annotation.Generated;
import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"heat_template_version", "resources"})
public class Template {

    @JsonProperty("heat_template_version")
    private String heatTemplateVersion;
    @JsonProperty("resources")
    @Valid
    private Resources_ resources;

    /**
     * 
     * @return The heatTemplateVersion
     */
    @JsonProperty("heat_template_version")
    public String getHeatTemplateVersion() {
        return heatTemplateVersion;
    }

    /**
     * 
     * @param heatTemplateVersion The heat_template_version
     */
    @JsonProperty("heat_template_version")
    public void setHeatTemplateVersion(String heatTemplateVersion) {
        this.heatTemplateVersion = heatTemplateVersion;
    }

    /**
     * 
     * @return The resources
     */
    @JsonProperty("resources")
    public Resources_ getResources() {
        return resources;
    }

    /**
     * 
     * @param resources The resources
     */
    @JsonProperty("resources")
    public void setResources(Resources_ resources) {
        this.resources = resources;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
