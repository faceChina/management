package com.jzwgj.management.server.sys.dao;

import java.util.List;

import com.jzwgj.management.server.sys.domain.CBaseDict;

public interface BaseDictDao {
	
	void add(CBaseDict cBaseDict);
	
	void edit(CBaseDict cBaseDict);
	
	CBaseDict getById(Long id);
	
	List<CBaseDict> findList(CBaseDict cBaseDict);
	
	void delete(Long id);

	Integer getCount(CBaseDict cbd);
	
	List<CBaseDict> findPageList(CBaseDict cBaseDict,Integer start,Integer pageSize);

}
