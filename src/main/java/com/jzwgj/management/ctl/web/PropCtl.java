package com.jzwgj.management.ctl.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jzwgj.management.ctl.BaseCtl;
import com.jzwgj.management.exception.PropException;
import com.jzwgj.management.server.web.classifcation.domain.Prop;
import com.jzwgj.management.server.web.classifcation.domain.PropValue;
import com.jzwgj.management.server.web.classifcation.service.ClassificationPropRealtionService;
import com.jzwgj.management.server.web.classifcation.service.PropService;
import com.jzwgj.management.server.web.classifcation.service.PropValueService;
import com.jzwgj.management.util.Pagination;
import com.jzwgj.management.vo.JsonPageVo;
import com.jzwgj.management.vo.ModuleTreeNodeVo;

@Controller
@RequestMapping("/web/prop/")
public class PropCtl extends BaseCtl {
	
	@Autowired
	private PropService propService;
	@Autowired
	private PropValueService propValueService;
	@Autowired
	private ClassificationPropRealtionService classificationPropRealtionService;

	//类目属性列表
	@RequestMapping(value="list")
	@ResponseBody
	public String list(){
		List<ModuleTreeNodeVo> list = propService.findTreeList("0");
		return setExtObjectResult(list);
	}
	
	//添加类目属性
	@RequestMapping(value="add")
	@ResponseBody
	public String add(Prop prop) {
		boolean flag =propService.add(prop);
		if (flag) {
			return setExtOptResult(true, null);
		}
		return setExtOptResult(false, "操作失败");
	}

	
	@RequestMapping(value="listvalue")
	@ResponseBody
	public String propList(Prop prop,Pagination<Prop> pagination){
		pagination = propService.selectPageList(prop, pagination);
		JsonPageVo<Prop> jsonPageVo = 
				new JsonPageVo<Prop>(pagination.getDatas(),pagination.getTotalRow());
		return setExtSelfObjectResult(jsonPageVo,true, new String[]{}, null);
	}
	
	@RequestMapping("propValueList")
	@ResponseBody
	public String propValueList(PropValue propValue,Pagination<PropValue> pagination){
		pagination = propValueService.selectPageList(propValue, pagination);
		JsonPageVo<PropValue> jsonPageVo = 
				new JsonPageVo<PropValue>(pagination.getDatas(),pagination.getTotalRow());
		return setExtSelfObjectResult(jsonPageVo,true, new String[]{}, null);
	}
	
	@RequestMapping("savePropValue")
	@ResponseBody
	public String saveProp(PropValue propValue){
		boolean flag =propValueService.add(propValue);
		if (flag) {
			return setExtOptResult(true, null);
		}
		return setExtOptResult(false, "操作失败");
	}
	
	/**
	 * 查询品类属性,过滤已添加属性
	 * @param request
	 * @param prop			查询条件
	 * @param pagination	分页对象
	 * @return
	 */
	@RequestMapping("propListFilterClass")
	@ResponseBody
	public String propListFilterClass(HttpServletRequest request,Prop prop,Pagination<Prop> pagination){
		//需过滤的品类已添加属性
		String classificationId = request.getParameter("classificationId");
		if(!org.apache.commons.lang3.StringUtils.isNumeric(classificationId)){
			return null;
		}
		pagination = propService.selectPageListFilterClass(prop, pagination,Long.parseLong(classificationId));
		JsonPageVo<Prop> jsonPageVo = new JsonPageVo<Prop>(pagination.getDatas(),pagination.getTotalRow());
		return setExtSelfObjectResult(jsonPageVo,true, new String[]{}, null);
	}
	
	@RequestMapping("editStatus")
	@ResponseBody
	public String editStatus(HttpServletRequest request){
		try {
			String propId = request.getParameter("propId");
			String status = request.getParameter("status");
			if(StringUtils.isBlank(propId)){
				throw new Exception("类目属性ID不能为空");
			}
			if(!NumberUtils.isNumber(propId)){
				throw new Exception("类目属性ID出错");
			}

			if(StringUtils.isBlank(status)){
				throw new Exception("商品属性修改状态不能为空");
			}
			if(!NumberUtils.isNumber(status)){
				throw new Exception("商品属性修改状态出错");
			}
			
			boolean flag = propService.editStatus(Long.parseLong(propId),Integer.parseInt(status));
			if (flag) {
				return setExtOptResult(true, null);
			}
			return setExtOptResult(false, "操作失败");
		} catch (Exception e) {
			return setExtOptResult(false, e.getMessage());
		}
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public String delete(HttpServletRequest request){
		try {
			String propId = request.getParameter("propId");
			if(StringUtils.isBlank(propId)){
				throw new Exception("类目属性ID不能为空");
			}
			if(!NumberUtils.isNumber(propId)){
				throw new Exception("类目属性ID出错");
			}
			
			boolean flag = propService.delete(Long.parseLong(propId));
			if (flag) {
				return setExtOptResult(true, null);
			}
			return setExtOptResult(false, "操作失败");
		} catch (Exception e) {
			return setExtOptResult(false, e.getMessage());
		}
	}
	
	/**
	 * 查询商品属性
	 * @param request
	 * @return
	 */
	@RequestMapping("selectById")
	@ResponseBody
	public String selectById(HttpServletRequest request){
		try {
			String propId = request.getParameter("id");
			if(StringUtils.isBlank(propId)){
				throw new Exception("商品属性ID不能为空");
			}
			if(!NumberUtils.isNumber(propId)){
				throw new Exception("商品属性ID出错");
			}
			
			Prop prop = propService.selectByPrimaryKey(Long.parseLong(propId));
			
			return setExtJsonDataResult(prop, true, new String[]{""}, null, null);
		} catch (Exception e) {
			return setExtOptResult(false, e.getMessage());
		}
	}
	
	/**
	 * 修改商品属性
	 * @param prop	商品属性对象
	 * @return
	 */
	@RequestMapping("update")
	@ResponseBody
	public String update(Prop prop){
		try {
			boolean flag = propService.update(prop);
			if (flag) {
				return setExtOptResult(true, null);
			}
		} catch (PropException e) {
			return setExtOptResult(false, e.getMessage());
		}
		return setExtOptResult(false, "操作失败");
	}
	
	@RequestMapping("deletePropValueList")
	@ResponseBody
	public String deletePropValueList(Long propertyId,Long[] ids){
		if(null == propertyId){
			return setExtOptResult(false,"商品属性ID不可为空");
		}
		
		if(null==ids || 0==ids.length){
			return setExtOptResult(false,"商品属性值ID不可为空");
		}
		
		try {
			boolean flag = propValueService.deleteByPropIdValueList(propertyId,ids);
			
			if(flag){
				return setExtOptResult(true, null);
			}
		} catch (Exception e) {
			return setExtOptResult(false,e.getMessage());
		}
		
		return setExtOptResult(false, "操作失败");
	}
	
	@RequestMapping("updatePropValueSort")
	@ResponseBody
	public String updatePropValueSort(Integer sort,String ids){
		if(sort == null){
			return setExtOptResult(false,"排序类型错误");
		}
		
		if(ids==null || "".equals(ids.trim())){
			return setExtOptResult(false,"关联ID不可为空");
		}
		
		try {
			boolean flag = propValueService.updateSort(sort,ids);
			
			if(flag){
				return setExtOptResult(true, null);
			}
		} catch (Exception e) {
			return setExtOptResult(false,e.getMessage());
		}
		
		return setExtOptResult(false, "操作失败");
	}
}
