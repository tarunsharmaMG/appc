/*-
 * ============LICENSE_START=======================================================
 * openECOMP : APP-C
 * ================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property. All rights
 * 						reserved.
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

package org.openecomp.appc.domainmodel.lcm;


public class Flags {

    private final boolean force;
    private final int ttl;
    private final Mode mode;

    public Flags(Mode mode, boolean force, int ttl) {
        super();
        this.force = force;
        this.ttl = ttl;
        this.mode = mode;
    }

    public boolean isForce() {
        return force;
    }

    public int getTtl() {
        return ttl;
    }

    public Mode getMode() {
        return mode;
    }

    @Override
    public String toString() {
        return "Flags{" +
                "force=" + force +
                ", ttl=" + ttl +
                ", mode=" + mode +
                '}';
    }

    public enum Mode {
        EXCLUSIVE,
        NORMAL
    }
}
