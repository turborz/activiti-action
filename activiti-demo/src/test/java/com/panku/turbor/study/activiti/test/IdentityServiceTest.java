package com.panku.turbor.study.activiti.test;

import static org.junit.Assert.*;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

public class IdentityServiceTest {

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule("activiti.cfg.xml");

	@Test
	public void testUser() throws Exception {
		/** 单元测试用户 **/
		
//		IdentityService identityService = activitiRule.getIdentityService();
//
//		User user = identityService.newUser("turborz");
//		user.setFirstName("zhang");
//		user.setLastName("shengshan");
//		user.setEmail("seezss@163.com");
//		identityService.saveUser(user);
//		User userInDb = identityService.createUserQuery().userId("turborz").singleResult();
//		assertNotNull(userInDb);
//		identityService.deleteUser("turborz");
//		userInDb = identityService.createUserQuery().userId("turborz").singleResult();
//		assertNotNull(userInDb);
		
		/** 单元测试组 **/
		
//		IdentityService identityService=activitiRule.getIdentityService();
//		Group group=identityService.newGroup("turborz_group");
//		group.setName("turborz_group");
//		group.setType("测试组");
//		identityService.saveGroup(group);
//		assertNotNull(group);
//		
//		identityService.deleteGroup("turborz_group");
//		Group groupInDb=identityService.createGroupQuery().groupId("turborz_group").singleResult();
//		assertNotNull(groupInDb);
		
		/** 单元测试组与用户的关联 **/
		
		IdentityService identityService=activitiRule.getIdentityService();
		Group group=identityService.newGroup("turborz_group_id");
		group.setName("turborz_group_name");
		identityService.saveGroup(group);
		
		User user=identityService.newUser("turborz_user_id");
		user.setFirstName("turborz_user_first_name");
		user.setLastName("turborz_user_last_name");
	
		identityService.saveUser(user);
		identityService.createMembership(user.getId(),group.getId());
		
		User userInDb=identityService.createUserQuery().memberOfGroup(group.getId()).singleResult();
		
		assertNotNull(userInDb);
		assertEquals("turborz_user_first_name",userInDb.getFirstName());
		
		Group groupInDb=identityService.createGroupQuery().groupMember(user.getId()).singleResult();
		
		assertNotNull(groupInDb);
		assertEquals("turborz_group_name", group.getName());
	
		
	}
	
	

}
