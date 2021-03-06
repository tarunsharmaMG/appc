/*-
 * ============LICENSE_START=======================================================
 * ONAP : APPC
 * ================================================================================
 * Copyright (C) 2017-2018 AT&T Intellectual Property. All rights reserved.
 * ================================================================================
 * Copyright (C) 2017 Amdocs
 * ================================================================================
 * Modifications Copyright (C) 2018 Ericsson
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

package org.onap.sdnc.config.params.transformer.tosca;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.onap.sdnc.config.params.transformer.tosca.exceptions.ArtifactProcessorException;

public class TestArtifactProcessor {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testArtifactProcessor() throws IOException, ArtifactProcessorException {
        ArtifactProcessor arp = ArtifactProcessorFactory.getArtifactProcessor();
        String pdString = FileUtils.readFileToString(
                new File("src/test/resources/tosca/ExamplePropertyDefinition.yml"),
                Charset.defaultCharset());
        OutputStream outstream = null;

        File tempFile = temporaryFolder.newFile("TestTosca.yml");
        outstream = new FileOutputStream(tempFile);
        arp.generateArtifact(pdString, outstream);
        outstream.flush();
        outstream.close();

        String expectedTosca = FileUtils.readFileToString(
                new File("src/test/resources/tosca/ExpectedTosca.yml"),
                StandardCharsets.UTF_8);
        String toscaString = FileUtils.readFileToString(tempFile, StandardCharsets.UTF_8);
        Assert.assertEquals(expectedTosca.replaceAll("\\r\\n", "\n"), toscaString.replaceAll("\\r\\n", "\n"));
    }

    @Test
    public void testArtifactProcessorWithStringOutput()
            throws IOException, ArtifactProcessorException {

        ArtifactProcessor arp = ArtifactProcessorFactory.getArtifactProcessor();

        String pdString = FileUtils.readFileToString(
                new File("src/test/resources/tosca/ExamplePropertyDefinition.yml"),
                Charset.defaultCharset());
        OutputStream outstream = null;

        outstream = new ByteArrayOutputStream();
        arp.generateArtifact(pdString, outstream);
        outstream.flush();
        outstream.close();

        String expectedTosca = FileUtils.readFileToString(
                new File("src/test/resources/tosca/ExpectedTosca.yml"),
                Charset.defaultCharset());
        String toscaString = outstream.toString();
        Assert.assertEquals(expectedTosca.replaceAll("\\r\\n", "\n"), toscaString.replaceAll("\\r\\n", "\n"));
    }
}
