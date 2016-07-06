package com.jzwgj.management.server.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jzwgj.management.server.sys.dao.ModualDao;
import com.jzwgj.management.server.sys.domain.CModual;
import com.jzwgj.management.server.sys.service.ModualService;
import com.jzwgj.management.vo.ModuleTreeNodeVo;
@Service
public class ModualServiceImpl implements ModualService {
	
	@Autowired
	private ModualDao modualDao;

	public void add(CModual cModual) {
		modualDao.add(cModual);
	}

	public void edit(CModual cModual) {
		modualDao.edit(cModual);
	}

	public void delete(Long id) {
		modualDao.delete(id);
	}

	public CModual getById(Long id) {
		return modualDao.getById(id);
	}

	public List<CModual> findList(CModual cModual) {
		return modualDao.findList(cModual);
	}

	public List<ModuleTreeNodeVo> findTreeList(String node) {
		List<ModuleTreeNodeVo> treeNodeList = new ArrayList<ModuleTreeNodeVo>();
		try {
			CModual cModual = new CModual();
			cModual.setPid(Long.valueOf(node));
			List<CModual> list = modualDao.findList(cModual);
			ModuleTreeNodeVo treeNodeVo = null;
			for (CModual modual : list) {
				treeNodeVo = new ModuleTreeNodeVo();
				treeNodeVo.setId(modual.getId().toString());// 菜单ID
				treeNodeVo.setText(modual.getName());// 菜单名称
				treeNodeVo.setIconCls(modual.getIcon());//菜单图标
				Integer isHaveChild = modualDao.getChildCount(modual.getId());
				if (isHaveChild > 0) {
					treeNodeVo.setLeaf(false);
				} else {
					treeNodeVo.setLeaf(true);
				}
				treeNodeList.add(treeNodeVo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return treeNodeList;
	}

	public List<ModuleTreeNodeVo> findListByPid(Long moduleId) {
		List<ModuleTreeNodeVo> treeNodelist = new ArrayList<ModuleTreeNodeVo>();
		List<ModuleTreeNodeVo> treeNodeChildList = null;
		CModual modual = new CModual(); 
		modual.setPid(moduleId);
		List<CModual> list = modualDao.findList(modual);
		ModuleTreeNodeVo treeNodeVo = null;
		for (CModual cModual : list) {
			treeNodeVo = new ModuleTreeNodeVo();
			treeNodeVo.setId(cModual.getId().toString()); // 菜单ID
			treeNodeVo.setText(cModual.getName());// 菜单名称
			treeNodeVo.setIconCls(cModual.getIcon());// 菜单图标
			treeNodeVo.setPageUrl(cModual.getUrl());//模块地址
			treeNodeVo.setParentId(cModual.getPid().toString());//模块pid
			Integer isHaveChild = modualDao.getChildCount(cModual.getId());
			if (isHaveChild > 0) {
				treeNodeVo.setLeaf(false);
				CModual m = new CModual(); 
				m.setPid(cModual.getId());
				List<CModual> childList = modualDao.findList(m);
				treeNodeChildList = new ArrayList<ModuleTreeNodeVo>();
				for (CModual cm : childList) {
					ModuleTreeNodeVo vo = new ModuleTreeNodeVo();
					vo.setId(cm.getId().toString()); // 菜单ID
					vo.setText(cm.getName());// 菜单名称
					vo.setIconCls(cm.getIcon());// 菜单图标
					vo.setPageUrl(cm.getUrl());//模块地址
					vo.setParentId(cm.getPid().toString());//模块pid
					vo.setLeaf(false);
					treeNodeChildList.add(vo);
				}
			} else {
				treeNodeVo.setLeaf(true);
			}
			treeNodeVo.setChildList(treeNodeChildList);
			treeNodelist.add(treeNodeVo);
		}
		return treeNodelist;
	}

}
