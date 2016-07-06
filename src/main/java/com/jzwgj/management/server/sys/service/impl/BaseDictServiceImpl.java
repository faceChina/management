package com.jzwgj.management.server.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jzwgj.management.server.sys.dao.BaseDictDao;
import com.jzwgj.management.server.sys.domain.CBaseDict;
import com.jzwgj.management.server.sys.service.BaseDictService;
import com.jzwgj.management.util.Pagination;
import com.jzwgj.management.vo.ModuleTreeNodeVo;
@Service
public class BaseDictServiceImpl implements BaseDictService{
	
	@Autowired
	private BaseDictDao baseDictDao;

	public void add(CBaseDict cBaseDict) {
		baseDictDao.add(cBaseDict);
	}

	public void edit(CBaseDict cBaseDict) {
		baseDictDao.edit(cBaseDict);
		
	}

	public CBaseDict getById(Long id) {
		return baseDictDao.getById(id);
	}

	public List<CBaseDict> findList(CBaseDict cBaseDict) {
		return baseDictDao.findList(cBaseDict);
	}

	public void delete(Long id) {
		baseDictDao.delete(id);
	}

	public List<ModuleTreeNodeVo> findTreeList(String node) {
		List<ModuleTreeNodeVo> treeNodeList = new ArrayList<ModuleTreeNodeVo>();
		CBaseDict cbd = new CBaseDict();
		cbd.setPid(Long.valueOf(node));
		List<CBaseDict> list = baseDictDao.findList(cbd);
		ModuleTreeNodeVo treeNodeVo = null;
		for (CBaseDict cBaseDict : list) {
			treeNodeVo = new ModuleTreeNodeVo();
			treeNodeVo.setId(cBaseDict.getId().toString()); // 数据字典树的ID
			treeNodeVo.setText(cBaseDict.getName());// 数据字典树的名称
			treeNodeVo.setLeaf(true);
			treeNodeList.add(treeNodeVo);
		}
		return treeNodeList;
	}

	public Pagination<CBaseDict> findChildPageList(String dictId,Pagination<CBaseDict> pagination ) {
		CBaseDict cbd = new CBaseDict();
		cbd.setPid(Long.valueOf(dictId));
		Integer totalRow = baseDictDao.getCount(cbd);
		List<CBaseDict> resultList = baseDictDao.findPageList(cbd, pagination.getStart(), pagination.getPageSize());
		pagination.setTotalRow(totalRow);
		pagination.setDatas(resultList);
		return pagination;
	}

	public void deleteAll(String[] ids) {
		for (String id : ids) {
			baseDictDao.delete(Long.valueOf(id));
		}
	}


}
