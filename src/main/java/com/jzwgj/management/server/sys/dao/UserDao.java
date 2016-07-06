package com.jzwgj.management.server.sys.dao;

import java.util.List;

import com.jzwgj.management.server.sys.domain.CUser;

public interface UserDao {

	void add(CUser CUser);
	
	void edit(CUser CUser);
	
	CUser getById(Long id);
	
	List<CUser> findList(CUser CUser);

	void delete(Long id);

	CUser getUserByNameAndPwd(String loginName, String passWrod);
}
