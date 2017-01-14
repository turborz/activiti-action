package com.panku.turbor.study.activiti.test;

import static org.junit.Assert.*;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.test.TestHelper;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.ActivitiTestCase;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.mock.ActivitiMockSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class IdentityServiceWithJobTest extends AbstractTest {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		identityService.deleteGroup("leader");
		identityService.deleteUser("turborz");
		identityService.deleteMembership("turborz", "leader");

		Group group = identityService.newGroup("leader");
		group.setName("领导");
		identityService.saveGroup(group);

		User user = identityService.newUser("turborz");
		user.setEmail("seezss@163.com");
		identityService.saveUser(user);

		identityService.createMembership(user.getId(), group.getId());

	}

	@After
	public void afterInvokeMethod() {
		identityService.deleteGroup("leader");
		identityService.deleteUser("turborz");
		identityService.deleteMembership("turborz", "leader");
	}

	@Test
	@Deployment(resources = "bpmn/userAndGroupInUserTaskTest.bpmn20.xml")
	public void testUserAndGroupInUserTask() {
		processEngine.getProcessEngineConfiguration().setHistory("full");

		// repositoryService.getBpmnModel("userAndGroupInUserTask");
		/** id格式为：userAndGroupInUserTask:8:70004，key:userAndGroupInUserTask **/
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("userAndGroupInUserTask");

		System.out.println("**************************************" + processInstance.getProcessDefinitionId() + ","
				+ processInstance.getProcessDefinitionKey());
		assertNotNull(processInstance);
		Task task = taskService.createTaskQuery().taskCandidateUser("turborz").singleResult();
		taskService.claim(task.getId(), "turborz");
		taskService.complete(task.getId());
	}

	

	

}
