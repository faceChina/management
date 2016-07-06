package com.jzwgj.management.server.sys.service;

import java.util.List;

import com.jzwgj.management.server.sys.domain.CModual;
import com.jzwgj.management.vo.ModuleTreeNodeVo;

public interface ModualService {
	
	void add(CModual cModual);
	
	void edit(CModual cModual);
	
	void delete(Long id);
	
	CModual getById(Long id);
	
	List<CModual> findList(CModual cModual);

	List<ModuleTreeNodeVo> findTreeList(String node);
	
	List<ModuleTreeNodeVo> findListByPid(Long moduleId);
}
