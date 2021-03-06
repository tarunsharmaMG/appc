﻿.. ============LICENSE_START==========================================
.. ===================================================================
.. Copyright © 2017-2018 AT&T Intellectual Property. All rights reserved.
.. ===================================================================
.. Licensed under the Creative Commons License, Attribution 4.0 Intl.  (the "License");
.. you may not use this documentation except in compliance with the License.
.. You may obtain a copy of the License at
.. 
..  https://creativecommons.org/licenses/by/4.0/
.. 
.. Unless required by applicable law or agreed to in writing, software
.. distributed under the License is distributed on an "AS IS" BASIS,
.. WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
.. See the License for the specific language governing permissions and
.. limitations under the License.
.. ============LICENSE_END============================================

Release Notes
=============

.. note
..	* This Release Notes must be updated each time the team decides to Release new artifacts.
..	* The scope of this Release Notes is for this particular component. In other words, each ONAP component has its Release Notes.
..	* This Release Notes is cumulative, the most recently Released artifact is made visible in the top of this Release Notes.
..	* Except the date and the version number, all the other sections are optional but there must be at least one section describing the purpose of this new release.
..	* This note must be removed after content has been added.


Version: 1.4.4
--------------

:Release Date: 2019-1-31


**Bug Fixes**

The Casablanca maintenance release fixed the following bugs:

	- `APPC-1247 <https://jira.onap.org/browse/APPC-1247>`_ - java.lang.NoClassDefFoundError when publishing DMAAP message

	- `CCSDK-741 <https://jira.onap.org/browse/CCSDK-741>`_ - Removed Work-around required for vCPE use case to correct the error described in CCSDK ticket.
 
Special Note for `APPC-1367 <https://jira.onap.org/browse/APPC-1367>`_ - APPC fails healthcheck with 404 error:

       During testing, we found there is a timing issue. When using OOM to deploy to k8s environment the clustered MariaDB database is not accessible at the time when the APPC pod is trying to insert the DG into MariaDb. This would cause the healthcheck issue. The workaround to solve this issue is redeploying the APPC pod.

Version: 1.4.3
--------------

:Release Date: 2018-11-30


**New Features**

The Casablanca release added the following functionality:

	 - Upgraded OpenDaylight (ODL) version to Oxygen 

	 - Upgraded to Karaf 4.1.5

	 - Migrated DB from mysql to maria db with galeira, tested on k8s clustering platform

	 - Added an ansible docker container, tested for DistributeTraffic LCM action

	 - Added support for the following LCM actions (a desciption of all of the above LCM actions can be found in the APPC LCM API Guide on readthedoc): 
	 
		- To support in-place software upgrade:
		
		  - DistributeTraffic
		  
		- To support storage management in OpenStack
		
		  - Reboot with hard and soft option
		  
	 - Additional contributions as part of Casablanca include: 
	 
		- Support for Platform Maturity requirements, including:
		
		   - Increased security
		   
			  - Enabled bath feature from AAF, CDT GUI and APIDOC can be used when AAF enbled  (see `APPC-1237 <https://jira.onap.org/browse/APPC-1237>`_ for additional details)
			  - Addressed critical alerts reported via Nexus IQ to the extent possible (see `APPC-770 <https://jira.onap.org/browse/APPC-770>`_ and wiki: https://wiki.onap.org/pages/viewpage.action?pageId=40927352 )
			  
		   - Stability
		   
			  - Executed 72 hour stability test on both Heat and OOM deployed environments using JMeter to drive a steady set of transactions over the 72 hour period (see the following wiki page for more details: https://wiki.onap.org/display/DW/APPC+72+Hour+Stability+Testing+Casablanca )
			  
		   - Resiliency
		   
			  - Support for OOM deployment, which enables resiliency via use of Kubernetes (see https://wiki.onap.org/display/DW/APPC+Resiliency for additional details) 
			  

**Bug Fixes**

	- `APPC-1009 <https://jira.onap.org/browse/APPC-1009>`_ - An incorrect regex in appc-provider-model was causing intermittent unit test failures. This is now fixed.

	- `APPC-1021 <https://jira.onap.org/browse/APPC-1021>`_ - An unnecessary pseudoterminal allocation for SSH connection was causing problems when trying to connect to a ConfD NETCONF Server.
  
	- `APPC-1107 <https://jira.onap.org/browse/APPC-1107>`_ - Database problems were causing artifacts created in CDT to not save to APPC. These have been fixed.

	- `APPC-1111 <https://jira.onap.org/browse/APPC-1111>`_ - TestDmaapConsumerImpl.testFetch method was taking 130+ seconds to run test. Build time is shorter now.

	- `APPC-1112 <https://jira.onap.org/browse/APPC-1112>`_ - Several unit tests in TimeTest.java had intermittent failures.

	- `APPC-1157 <https://jira.onap.org/browse/APPC-1157>`_ - Mockito package was removed from the APPC client jar. It was causing conflicts with other applications using APPC client library.

	- `APPC-1184 <https://jira.onap.org/browse/APPC-1184>`_ - The APPC LCM API documentation was outdated and did not reflect the correct endpoints.

	- `APPC-1186 <https://jira.onap.org/browse/APPC-1186>`_ - VNF-Level OpenStack actions such as Restart were failing if the optional identity-url was omitted from the payload of the request.

	- `APPC-1188 <https://jira.onap.org/browse/APPC-1188>`_ - Exception was occurring if force flag was set to false in a request from policy.

	- `APPC-1192 <https://jira.onap.org/browse/APPC-1192>`_ - CDT was not updating the DEVICE_INTERFACE_PROTOCOL table, so APPC was unable to get the protocol during lcm actions.

	- `APPC-1205 <https://jira.onap.org/browse/APPC-1205>`_ - Artifacts manually entered into CDT were not saving correctly, while artifacts created by uploading a template were.

	- `APPC-1207 <https://jira.onap.org/browse/APPC-1207>`_ - Logging constants were missing in several features, causing incorrect logging messages.

	- `APPC-1218 <https://jira.onap.org/browse/APPC-1218>`_ - Aai connection had certificate errors and path build exceptions.

	- `APPC-1224 <https://jira.onap.org/browse/APPC-1224>`_ - SubRequestID was not being relayed back to Policy in DMaaP Response messages.

	- `APPC-1226 <https://jira.onap.org/browse/APPC-1226>`_ - Mock code to mimic backend execution for Reboot was causing problems and has been removed.

	- `APPC-1227 <https://jira.onap.org/browse/APPC-1227>`_ - APPC was not able to read VNF templates created with CDT.

	- `APPC-1230 <https://jira.onap.org/browse/APPC-1230>`_ - APPC was using the GenericRestart DG instead of DGOrchestrator.

	- `APPC-1231 <https://jira.onap.org/browse/APPC-1231>`_ - APPC was not updating the TRANSACTIONS table correctly when an operation completed.

	- `APPC-1233 <https://jira.onap.org/browse/APPC-1233>`_ - DGOrchestrator was incorrectly being given an output.payload parameter instead of output-payload.

	- `APPC-1234 <https://jira.onap.org/browse/APPC-1234>`_ - AppC Open Day Light login was responding 401 unauthorized when AAF was enabled.

	- `APPC-1237 <https://jira.onap.org/browse/APPC-1237>`_ - APPC was not properly url-encoding AAF credentials.

	- `APPC-1243 <https://jira.onap.org/browse/APPC-1243>`_ - Container was not preserving mysql data after kubectl edit statefulset.

	- `APPC-1244 <https://jira.onap.org/browse/APPC-1244>`_ - Ansible Server would never start in oom.

**Known Issues**

	- `APPC-1247 <https://jira.onap.org/browse/APPC-1247>`_ - java.lang.NoClassDefFoundError when publishing DMAAP message
	    - This issue is relevant during the vCPE use case.
	    - Due to this defect, the VM will perform four start/stop sequences, instead of the normal one.
	    - After the four start/stop sequences, the VM will be left in the correct state that it should be in.
	
	- Work-around required for vCPE use case to correct the error described in: `CCSDK-741 <https://jira.onap.org/browse/CCSDK-741>`_
	    - CCSDK aai adapter doesn't recognize generic-vnf attribute in the response, as it is not defined by aai_schema XSD
	    - To work around this, several steps must be performed as described here:
	        
	        1. Add a restapi template file into the appc docker containers
	            a. Enter the appc docker container (docker exec... or kubectl exec...)
	            b. Create a directory: /opt/onap/appc/templates
	            c. Download this file `aai-named-query.json <https://gerrit.onap.org/r/gitweb?p=appc/deployment.git;a=blob_plain;f=vcpe-workaround-files/aai-named-query.json;hb=refs/heads/casablanca>`_ and place it in that directory
	        2. Replace the generic restart DG with a new one
	            a. Download the `APPC_Generic_Restart.xml <https://gerrit.onap.org/r/gitweb?p=appc/deployment.git;a=blob_plain;f=vcpe-workaround-files/APPC_method_Generic_Restart_3.0.0.xml;hb=refs/heads/casablanca>`_
	            b. Edit the file. Find the parameter definition lines for restapiUrl, restapiUser, restapiPassword (lines 52-54) and replace these with the correct values for your aai server.
	            c. Copy this file into the appc docker containers to the /opt/onap/appc/svclogic/graphs directory (you will be replacing the old version of the file with this copy)
	        3. Load the new DG file
	            a. In the appc docker containers, enter the "/opt/appc/svclogic/bin directory
	            b. Run install-converted-dgs.sh

Quick Links:

 	- `APPC project page <https://wiki.onap.org/display/DW/Application+Controller+Project>`_
 	
 	- `Passing Badge information for APPC <https://bestpractices.coreinfrastructure.org/en/projects/1579>`_
 	
 	- `Project Vulnerability Review Table for APPC <https://wiki.onap.org/pages/viewpage.action?pageId=40927352>`_

**Other**

- Limitations, Constraints and other worthy notes:

	- OpenStack Restriction:

		- Currently APPC only supports OpenStack.

		- Admin level access for Tenant level operations.

		- OpenStack Hypervisorcheck is turned off by default.


	- Integration with MultiCloud is supported for Standalone Restart (i.e., not via DGOrchestrator). For any other action, such as Stop, Start, etc.. via MultiCloud requires the MultiCloud identity URL to be either passed in the payload or defined in appc.properties.
	


Version: 1.3.0
--------------


:Release Date: 2018-06-07


**New Features**

The Beijing release added the following functionality:
 
	 - Added support for the following LCM actions (a desciption of all of the above LCM actions can be found in the APPC LCM API Guide on readthedoc): 
	 
		- To support in-place software upgrade:
		
		  - QuiesceTraffic
		  - ResumeTraffic
		  - UpgradeSoftware
		  - UpgradePreCheck
		  - UpgradePostCheck
		  - UpgradeBackup
		  - UpgradeBackout
		  
		- To support storage management in OpenStack
		
		  - AttachVolume
		  - DetachVolume
		  
		- To support Manual Scale Out use case
		
		  - ConfigScaleOut (more details can be found in teh APPC Epic: `APPC-431 <https://jira.onap.org/browse/APPC-431>`_ )
		  
		- To support general operations
		
		  - ActionStatus
		  

	 - Contributed the APPC Controller Design Tool (CDT), which enables self-serve capabilities by allowing users to model their VNF/VNFC for consumption by APPC to use in the execution of requests to perform life cycle management activities.
	 
		- More details on the APPC CDT can be found in the APPC CDT User Guide in readthedocs.
		- Additional information on how the APPC CDT tool was used to model the vLB and build teh artifacts needed by APPC to execute teh ConfigScaleOut action can be found at the following wiki pages: https://wiki.onap.org/pages/viewpage.action?pageId=33065185 
		
	 - Additional contributions as part of Beijing include: 
	 
		- Support for Platform Maturity requirements, including:
		
		   - Increased security
		   
			  - Added security to ODL web-based API access via AAF (see `APPC-404 <https://jira.onap.org/browse/APPC-404>`_ for additional details)
			  - Addressed critical alerts reported via Nexus IQ to the extent possible (see `APPC-656 <https://jira.onap.org/browse/APPC-656>`_ )
			  
		   - Stability
		   
			  - Executed 72 hour stability test on both Heat and OOM deployed environments using JMeter to drive a steady set of transactions over the 72 hour period (see the following wiki page for more details: https://wiki.onap.org/display/DW/ONAP+APPC+72+Hour+Stability+Test+Results )
			  
		   - Resiliency
		   
			  - Support for OOM deployment, which enables resiliency via use of Kubernetes (see `APPC-414 <https://jira.onap.org/browse/APPC-414>`_ for additional details) 
			  
		- Upgraded OpenDaylight (ODL) version to Nitrogen
      
      


**Bug Fixes**

The following defects that were documented as known issues in Amsterdam have been fixed in Beijing release:
	
	- `APPC-316 <https://jira.onap.org/browse/APPC-316>`_ - Null payload issue for Stop Application

	- `APPC-315 <https://jira.onap.org/browse/APPC-315>`_ - appc-request-handler is giving error java.lang.NoClassDefFoundError 

	- `APPC-312 <https://jira.onap.org/browse/APPC-312>`_ - APPC request is going to wrong request handler and rejecting request

	- `APPC-311 <https://jira.onap.org/browse/APPC-311>`_ - The APPC LCM Provider Healthcheck

	- `APPC-309 <https://jira.onap.org/browse/APPC-309>`_ - APPC LCM Provider URL missing in appc.properties. 

	- `APPC-307 <https://jira.onap.org/browse/APPC-307>`_ - Embed jackson-annotations dependency in appc-dg-common during run-time 

	- `APPC-276 <https://jira.onap.org/browse/APPC-276>`_ - Some Junit are breaking convention causing excessively long build
  
	- `APPC-248 <https://jira.onap.org/browse/APPC-248>`_ - There is an compatibility issue between PowerMock and Jacoco which causes Sonar coverage not to be captured. Fix is to move to Mockito.
	
	
**Known Issues**

The following issues remain open at the end of Beijing release. Please refer to Jira for further details and workaround, if available.

        - `APPC-987 <https://jira.onap.org/browse/APPC-987>`_ - APPC Investigate TRANSACTION Table Aging. See **Other** section for further information
	
	- `APPC-977 <https://jira.onap.org/browse/APPC-977>`_ - Procedures needed for enabling AAF support in OOM. See **Other** section for further information
	
        - `APPC-973 <https://jira.onap.org/browse/APPC-973>`_ - Fix delimiter string for xml-download CDT action
	
	- `APPC-940 <https://jira.onap.org/browse/APPC-940>`_ - APPC CDT Tool is not updating appc_southbound.properties with the URL supplied for REST

        - `APPC-929 <https://jira.onap.org/browse/APPC-929>`_ - LCM API - ConfigScaleOut- Payload parameter to be manadatory set to "true"
 
	- `APPC-912 <https://jira.onap.org/browse/APPC-912>`_ - MalformedChunkCodingException in MDSALStoreImpl.getNodeName
	
	- `APPC-892 <https://jira.onap.org/browse/APPC-892>`_ - Cntl+4 to highlight and replace feature-Textbox is accepting space  and able to submit without giving any value

	- `APPC-869 <https://jira.onap.org/browse/APPC-869>`_ - VM Snapshot error occurs during image validation.
	
	- `APPC-814 <https://jira.onap.org/browse/APPC-814>`_ - Update openecomp-tosca-datatype namespace  
	
	- `APPC-340 <https://jira.onap.org/browse/APPC-340>`_ - APPC rejecting request even for decimal of millisecond timestamp difference
	 
	- `APPC-154 <https://jira.onap.org/browse/APPC-154>`_ - Logging issue - Request REST API of APPC has RequestID (MDC) in Body or Payload section instead of Header.
	
	
**Security Notes**

APPC code has been formally scanned during build time using NexusIQ and all Critical vulnerabilities have been addressed, items that remain open have been assessed for risk and determined to be false positive. The APPC open Critical security vulnerabilities and their risk assessment have been documented as part of the `project <https://wiki.onap.org/pages/viewpage.action?pageId=25438971>`_.

Additionally, communication over DMaaP currently does not use secure topics in this release. This has dependency on DMaaP to enable. 	


Quick Links:
 	- `APPC project page <https://wiki.onap.org/display/DW/Application+Controller+Project>`_
 	
 	- `Passing Badge information for APPC <https://bestpractices.coreinfrastructure.org/en/projects/1579>`_
 	
 	- `Project Vulnerability Review Table for APPC <https://wiki.onap.org/pages/viewpage.action?pageId=25438971>`_
 	
**Other**

- Limitations, Constraints and other worthy notes

	- An issue was discovered with usage of AAF in an OOM deployed environment after the Beijing release was created. The issue was twofold (tracked under `APPC-977 <https://jira.onap.org/browse/APPC-977>`_):
	  
	     - Needed APPC configuration files were missing in Beijing OOM , and 
	     - AAF updated their certificates to require 2way certs, which requires APPC updates 
		 
          Additionally, in a Heat deployed environment, a manual workaround will be required to authorize with AAF if they are using 2way certificates.  For instruction on workaround steps needed depending on type of deployment, please refer to the following wiki: https://wiki.onap.org/display/DW/AAF+Integration+with+APPC.  

        - During the testing of the vCPE/vMUX closed loop scenarios in an OOM deployed environment, an issue was encountered where transactions were not being deleted from the TRANSACTION table and was blocking other Restart request from completing successfully (tracked under `APPC-987 <https://jira.onap.org/browse/APPC-987>`_). A workaround is available and documented in the Jira ticket.

        - It is impossible for us to test all aspect of the application. Scope of testing done in Beijing is captured on the following wiki:   https://wiki.onap.org/display/DW/APPC+Beijing+Testing+Scope+and+Status
	  
	- Currently APPC only supports OpenStack
	  
	- OpenStack Hypervisorcheck is turned off by default. If you want to invoke this functionality via the appc.properties, you need to enable it and ensure you have Admin level access to OpenStack.
	  
	- Integration with MultiCloud is supported for Standalone Restart (i.e., not via DGOrchestrator). For any other action, such as Stop, Start, etc.. via MultiCloud requires the MultiCloud identity URL to be either passed in the payload or defined in appc.properties.
	  
	- APPC needs Admin level access for Tenant level operations. 
	  
	- Currently, the "ModifyConfig" API and the implementation in the Master Directed Graph is only designed to work with the vFW Closed-Loop Demo.
  

Version: 1.2.0
--------------

:Release Date: 2017-11-16


**New Features**

The Amsterdam release continued evolving the design driven architecture of and functionality for APPC. 
APPC aims to be completely agnostic and make no assumption about the network. 

The main goal of the Amsterdam release was to:
 - Support the vCPE use case as part of the closed loop action to perform a Restart on the vGMUX
 - Demonstrate integration with MultiCloud as a proxy to OpenStack 
 - Continue supporting the vFW closed loop use case as part of regression from the seed contribution. 

Other key features added in this release include:
 - Support for Ansible 
   - The Ansible Extension for APP-C allows management of VNFs that support Ansible. Ansible is a an open-source VNF management framework that provides an almost cli like set of tools in a structured form. APPC supports Ansible through the following three additions: An Ansible server interface, Ansible Adapter, and Ansible Directed Graph. 
 - Support for Chef 
   - The Chef Extension for APPC allows management of VNFs that support Chef through the following two additions: a Chef Adapter and Chef Directed Graph.
 - LifeCycle Management (LCM) APIs via standalone DGs or via the DGOrchestrator architecture to trigger actions on VMs, VNFs, or VNFCs
 - OAM APIs to manage the APPC application itself
 - Upgrade of OpenDaylight to Carbon version



**Bug Fixes**

	- This is technically the first release of APPC, previous release was the seed code contribution. As such, the defects fixed in this release were raised during the course of the release. Anything not closed is captured below under Known Issues. If you want to review the defects fixed in the Amsterdam release, refer to `Jira <https://jira.onap.org/issues/?filter=10570&jql=project%20%3D%20APPC%20AND%20issuetype%20%3D%20Bug%20AND%20status%20%3D%20Closed%20AND%20fixVersion%20%3D%20%22Amsterdam%20Release%22>`_. 
	
	- Please also refer to the notes below. Given the timeframe and resource limitations, not all functions of the release could be validated. Items that were validated are documented on the wiki at the link provide below. If you find issues in the course of your work with APPC, please open a defect in the Application Controller project of Jira (jira.onpa.org)
	
**Known Issues**

	- `APPC-312 <https://jira.onap.org/browse/APPC-312>`_ - APPC request is going to wrong request handler and rejecting request. Configure request failing with following error: ``REJECTED Action Configure is not supported on VM level``.
	
	- `APPC-311 <https://jira.onap.org/browse/APPC-311>`_ - The APPC LCM Provider Healthcheck, which does a healthceck on a VNF, is failing. No known workaround at this time. 
	
	- `APPC-309 <https://jira.onap.org/browse/APPC-309>`_ - The property: ``appc.LCM.provider.url=http://127.0.0.1:8181/restconf/operations/appc-provider-lcm`` is missing from appc.properties in the appc deployment.  The property can be manually added as a workaround, then bounce the appc container. 
	
	- `APPC-307 <https://jira.onap.org/browse/APPC-307>`_ - Missing jackson-annotations dependency in appc-dg-common - This issue results in Rebuild operation via the APPC Provider not to work. Use instead Rebuild via the APPC LCM Provider using DGOrchestrator.
	
	- `APPC-276 <https://jira.onap.org/browse/APPC-276>`_ - A number of junit testcases need to be reworked because they are causing APPC builds to take much  longer to complete. This issue does not cause the build to fail, just take longer. You can comment out these junit in your local build if this is a problem. 
	  
	- `APPC-248 <https://jira.onap.org/browse/APPC-248>`_ - There is an compatibility issue between PowerMock and Jacoco which causes Sonar coverage not to be captured. There is no functional impact on APPC.
	 
	- `APPC-154 <https://jira.onap.org/browse/APPC-154>`_ - Logging issue - Request REST API of APPC has RequestID (MDC) in Body or Payload section instead of Header.
	
		
**Security Issues**
	- Communication over DMaaP currently does not use secure topics in this release.
	- AAF is deactivated by default in this release and was not validated or committed as part of the Amsterdam Release.


**Other**

- Limitations, Constraints and other worthy notes

  - LCM Healthcheck and Configure actions do not work.
  - The APPC actions validated in this release are captured here: https://wiki.onap.org/display/DW/APPC+Testing+Scope+and+Status
  - Currently APPC only supports OpenStack
  - OpenStack Hypervisorcheck is turned off by default. If you want to invoke this functionality via the appc.properties, you need to enable it and ensure you have Admin level access to OpenStack.
  - Integration with MultiCloud is supported for Standalone Restart (i.e., not via DGOrchestrator). For any other action, such as Stop, Start, etc.. via MultiCloud requires the MultiCloud identity URL to be either passed in the payload or defined in appc.properties.
  - APPC needs Admin level access for Tenant level operations. 
  - Currently, if DGs are modified in appc.git repo, they must be manually moved to the appc/deployment repo. 
  - Currently, the "ModifyConfig" API and the implementation in the Master Directed Graph is only designed to work with the vFW Closed-Loop Demo.
  

===========

End of Release Notes


