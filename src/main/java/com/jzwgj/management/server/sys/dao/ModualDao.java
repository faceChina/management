package com.jzwgj.management.server.sys.dao;

import java.util.List;

import com.jzwgj.management.server.sys.domain.CModual;

public interface ModualDao {
	
	void add(CModual cModual);
	
	void edit(CModual cModual);
	
	CModual getById(Long id);
	
	List<CModual> findList(CModual cModual);
	
	List<CModual> findPageList(CModual cModual,Integer start,Integer pageSize);

	void delete(Long id);

	Integer getChildCount(Long id);
}
