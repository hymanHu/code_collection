package com.cpkf.notpad.server.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpkf.notpad.dao.IResourceDao;
import com.cpkf.notpad.server.IResourceService;
@Service
public class ResourceServiceImpl implements IResourceService {
	@Autowired
	private IResourceDao resourceDao;
	public List getAllResources() {
		return resourceDao.getAllResources();
	}

}
