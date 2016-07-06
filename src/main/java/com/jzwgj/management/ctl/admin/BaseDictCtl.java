package com.jzwgj.management.ctl.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jzwgj.management.ctl.BaseCtl;
import com.jzwgj.management.server.sys.domain.CBaseDict;
import com.jzwgj.management.server.sys.service.BaseDictService;
import com.jzwgj.management.util.Pagination;
import com.jzwgj.management.vo.JsonPageVo;
import com.jzwgj.management.vo.ModuleTreeNodeVo;

@Controller
@RequestMapping("/admin/dict/")
public class BaseDictCtl extends BaseCtl {
	
	@Autowired
	private BaseDictService baseDictService;
	
	@RequestMapping("tree")
	@ResponseBody
	public String tree(String node){
		List<ModuleTreeNodeVo>  treeNodeList = baseDictService.findTreeList(node);
		return setExtObjectResult(treeNodeList);
	}

	@RequestMapping("add")
	@ResponseBody
	public String add(CBaseDict cBaseDict){
		cBaseDict.setType("0");
		baseDictService.add(cBaseDict);
		return setExtOptResult(true, cBaseDict.getId().toString());
	}
	
	
	@RequestMapping("edit")
	@ResponseBody
	public String edit(CBaseDict cBaseDict){
		baseDictService.edit(cBaseDict);
		return setExtOptResult(true, cBaseDict.getId().toString());
	}
	
	@RequestMapping("del")
	@ResponseBody
	public String del(String dictId){
		baseDictService.delete(Long.valueOf(dictId));
		return setExtOptResult(true,null);
	}

	@RequestMapping("delAll")
	@ResponseBody
	public String delAll(String[] ids){
		baseDictService.deleteAll(ids);
		return setExtOptResult(true,null);
	}
	
	@RequestMapping("get")
	@ResponseBody
	public String get(String dictId){
		CBaseDict cBaseDict = baseDictService.getById(Long.valueOf(dictId));
		return setExtJsonDataResult(cBaseDict, true, new String[]{""}, null, null);
	}
	
	@RequestMapping("getByCode")
	@ResponseBody
	public String getByCode(String sbdCode){
		CBaseDict cBaseDict = new CBaseDict();
		cBaseDict.setCode(sbdCode);
		List<CBaseDict> cBaseDictList = baseDictService.findList(cBaseDict);
		return setExtObjectResult(cBaseDictList,false, new String[] { "id","name" });
	}
	
	@RequestMapping("listChild")
	@ResponseBody
	public String findListChild(String dictId,Pagination<CBaseDict> pagination){
		pagination =  baseDictService.findChildPageList(dictId,pagination);
		JsonPageVo<CBaseDict> jsonPageVo = new JsonPageVo<CBaseDict>(pagination.getDatas(),pagination.getTotalRow());//将查询结果转化为json格式的数据
		return setExtSelfObjectResult(jsonPageVo,true, new String[]{"pid"}, null);//String[]{"pid"}放置的是不需要转化json的字段;
	}
}
