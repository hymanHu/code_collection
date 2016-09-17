package com.cpkf.notpad.server.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.cpkf.notpad.dao.IResourceDao;
import com.cpkf.notpad.dao.impl.ResourceDaoImpl;
import com.cpkf.notpad.entity.Resource;
import com.cpkf.notpad.entity.Role;
import com.cpkf.notpad.server.IResourceService;

public class ResourceServiceImplTest {
	private IResourceService resourceService;
	private IResourceDao resourceDao;
	@Before
	public void setup(){
		resourceService = new ResourceServiceImpl();
		resourceDao = EasyMock.createMock(ResourceDaoImpl.class);
		ReflectionTestUtils.setField(resourceService, "resourceDao", resourceDao);
	}
	@After
	public void tearDown(){
		resourceDao =  null;
		resourceService = null;
	}
	@Test
	public void testGetAllResources(){
		Role role = new Role();
		role.setRoleName("role_admin");
		Resource resource = new Resource();
		resource.setUrl("/admin/**");
		resource.getRoles().add(role);
		List<Resource> list = new ArrayList<Resource>();
		list.add(resource);
		EasyMock.expect(resourceDao.getAllResources()).andReturn(list).anyTimes();
		EasyMock.replay(resourceDao);
		List<Resource> resources = resourceService.getAllResources();
		Iterator<Role> roleIterator = resources.get(0).getRoles().iterator();
		Role role2 = new Role();
		while(roleIterator.hasNext()){
			role2 = roleIterator.next();
		}
		Assert.assertEquals(1, resources.size());
		Assert.assertEquals(1, resources.get(0).getRoles().size());
		Assert.assertEquals("role_admin", role2.getRoleName());
		EasyMock.verify(resourceDao);
	}
}
