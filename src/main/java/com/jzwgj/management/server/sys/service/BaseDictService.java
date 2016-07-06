package com.jzwgj.management.server.sys.service;

import java.util.List;

import com.jzwgj.management.server.sys.domain.CBaseDict;
import com.jzwgj.management.util.Pagination;
import com.jzwgj.management.vo.ModuleTreeNodeVo;

public interface BaseDictService {
	
	void add(CBaseDict cBaseDict);
	
	void edit(CBaseDict cBaseDict);
	
	void delete(Long id);
	
	CBaseDict getById(Long id);
	
	List<CBaseDict> findList(CBaseDict cBaseDict);

	List<ModuleTreeNodeVo> findTreeList(String node);

	Pagination<CBaseDict> findChildPageList(String dictId,Pagination<CBaseDict> pagination );

	void deleteAll(String[] ids);

}
