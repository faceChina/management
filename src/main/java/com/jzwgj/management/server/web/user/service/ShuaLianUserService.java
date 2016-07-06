package com.jzwgj.management.server.web.user.service;

import java.util.Map;

import com.jzwgj.management.exception.ShuaLianUserException;
import com.jzwgj.management.server.web.user.domain.ShuaLianUser;
import com.zjlp.face.util.page.Pagination;


public interface ShuaLianUserService {
	
	void update(ShuaLianUser shuaLianUser) throws ShuaLianUserException;
	
	Map<String,Integer> getUserNumMap();
	
	Boolean checkLpNo(String lpNo,Long userId);
	
	Pagination<ShuaLianUser> findUserPageList(ShuaLianUser shuaLianUser, Pagination<ShuaLianUser> pagination) throws ShuaLianUserException;
	
	ShuaLianUser getUserByUserId(Long userId) throws ShuaLianUserException;
	
	ShuaLianUser userAccountInfo (Long userId); 
}
