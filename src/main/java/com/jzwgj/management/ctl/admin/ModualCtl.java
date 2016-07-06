package com.jzwgj.management.ctl.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jzwgj.management.ctl.BaseCtl;
import com.jzwgj.management.server.sys.domain.CModual;
import com.jzwgj.management.server.sys.service.ModualService;
import com.jzwgj.management.vo.ModuleTreeNodeVo;

@Controller
@RequestMapping("/admin/modual/")
public class ModualCtl extends BaseCtl {

	@Autowired
	private ModualService modualService;

	@RequestMapping("tree")
	@ResponseBody
	public String tree(@RequestParam String node) {
		List<ModuleTreeNodeVo> treeNodeList = modualService.findTreeList(node);
		return setExtObjectResult(treeNodeList);
	}

	@RequestMapping("get")
	@ResponseBody
	public String get(String moduleId) {
		CModual cModual = modualService.getById(Long.valueOf(moduleId));
		return this.setExtJsonDataResult(cModual, true, new String[]{}, null, null);
	}

	@RequestMapping("add")
	@ResponseBody
	public String add(CModual cModual) {
		modualService.add(cModual);
		return setExtOptResult(true, cModual.getId().toString());
	}

	@RequestMapping("edit")
	@ResponseBody
	public String edit(CModual cModual) {
		modualService.edit(cModual);
		return setExtOptResult(true, cModual.getId().toString());
	}

	@RequestMapping("del")
	@ResponseBody
	public String delete(String moduleId) {
		modualService.delete(Long.valueOf(moduleId));
		return setExtOptResult(true, null);
	}

}
