package com.jzwgj.management.server.sys.service;

import java.util.List;

import com.jzwgj.management.server.sys.domain.CUser;

public interface UserService {
	
	void add(CUser cUser);
	
	void edit(CUser cUser);
	
	void delete(Long id);
	
	CUser getById(Long id);
	
	List<CUser> findList(CUser cUser);

	CUser login(String loginName, String passWrod);
}
