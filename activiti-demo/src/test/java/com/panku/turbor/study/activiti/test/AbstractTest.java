package com.panku.turbor.study.activiti.test;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.test.TestHelper;
import org.activiti.engine.test.ActivitiTestCase;
import org.activiti.engine.test.mock.ActivitiMockSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;

public abstract class AbstractTest extends TestCase {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected ProcessEngine processEngine = null;

	protected IdentityService identityService = null;

	protected RuntimeService runtimeService = null;

	protected RepositoryService repositoryService = null;

	protected TaskService taskService = null;

	private ActivitiMockSupport mockSupport;

	public AbstractTest() {
		super();
		if (processEngine == null) {
			/*
			 * 使用默认的配置文件名称（activiti.cfg.xml）创建引擎对象
			 */
			processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault()
					.buildProcessEngine();
			identityService = processEngine.getIdentityService();
			runtimeService = processEngine.getRuntimeService();
			taskService = processEngine.getTaskService();
			repositoryService = processEngine.getRepositoryService();
			initializeMockSupport();
		}
	}

	protected void initializeMockSupport() {
		if (ActivitiMockSupport.isMockSupportPossible(processEngine)) {
			this.mockSupport = new ActivitiMockSupport(processEngine);
		}
	}

	@Override
	protected void runTest() throws Throwable {

		// Support for mockup annotations on test method
		TestHelper.annotationMockSupportSetup(getClass(), getName(), mockSupport);

		// The deployment of processes denoted by @Deployment should
		// be done after the setup(). After all, the mockups must be
		// configured in the engine before the actual deployment happens
		TestHelper.annotationDeploymentSetUp(processEngine, getClass(), getName());

		super.runTest();

		// Remove deployment
		// TestHelper.annotationDeploymentTearDown(processEngine, deploymentId,
		// getClass(), getName());

		// Reset mocks
		// TestHelper.annotationMockSupportTeardown(mockSupport);
	}
}
