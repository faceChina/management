package com.jzwgj.management.server.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jzwgj.management.server.sys.dao.UserDao;
import com.jzwgj.management.server.sys.domain.CUser;
import com.jzwgj.management.server.sys.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	public void add(CUser cUser) {
		userDao.add(cUser);
	}

	public void edit(CUser cUser) {
		userDao.edit(cUser);
	}

	public void delete(Long id) {
		userDao.delete( id);
	}

	public CUser getById(Long id) {
		return userDao.getById( id);
	}

	public List<CUser> findList(CUser cUser) {
		return userDao.findList(cUser);
	}

	@Override
	public CUser login(String loginName, String passWrod) {
		return userDao.getUserByNameAndPwd(loginName,passWrod);
	}

}
