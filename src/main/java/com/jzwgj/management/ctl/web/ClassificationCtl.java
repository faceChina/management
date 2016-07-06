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
import com.jzwgj.management.exception.ClassificationException;
import com.jzwgj.management.exception.ClassificationPropRealtionException;
import com.jzwgj.management.server.web.classifcation.domain.Classification;
import com.jzwgj.management.server.web.classifcation.domain.ClassificationPropRealtion;
import com.jzwgj.management.server.web.classifcation.domain.vo.ClassificationPropRealtionVo;
import com.jzwgj.management.server.web.classifcation.service.ClassificationPropRealtionService;
import com.jzwgj.management.server.web.classifcation.service.ClassificationService;
import com.jzwgj.management.util.Pagination;
import com.jzwgj.management.vo.JsonPageVo;
import com.jzwgj.management.vo.ModuleTreeNodeVo;

@Controller
@RequestMapping("/web/classification/")
public class ClassificationCtl extends BaseCtl {
	
	@Autowired
	private ClassificationService classificationService;
	
	@Autowired
	private ClassificationPropRealtionService classificationPropRealtionService;

	/**
	 * 商品类目树结构
	 * @param node		父节点
	 * @param type		商品类目种类
	 * @return
	 */
	@RequestMapping("tree")
	@ResponseBody
	public String tree(String node,String type){
		//商品种类为普通商品
		type = "1";
		List<ModuleTreeNodeVo>  treeNodeList = classificationService.findTreeList(node,type);
		return setExtObjectResult(treeNodeList);
	}
	
	/**
	 * 保存商品类目
	 * @param classification	商品类目对象
	 * @return
	 */
	@RequestMapping("save")
	@ResponseBody
	public String saveParent(Classification classification){
		if (null == classification.getParentId()) {
			classification.setParentId(0L);
		}
		boolean flag = classificationService.add(classification);
		if (flag) {
			return setExtOptResult(true, null);
		}
		return setExtOptResult(false, "操作失败");
	}
	
	/**
	 * 保存商品类目和商品属性关联关系
	 * @param classificationPropRealtion		关联关系对象
	 * @return
	 */
	@RequestMapping("saveRelation")
	@ResponseBody
	public String saveRelation(ClassificationPropRealtion classificationPropRealtion){
		try {
			boolean flag = classificationPropRealtionService.add(classificationPropRealtion);
			if(flag){
				return setExtOptResult(true, null);
			}
		} catch (ClassificationPropRealtionException e) {
			return setExtOptResult(false, e.getMessage());
		}

		return setExtOptResult(false, "操作失败");
	}
	
	/**
	 * 分页显示商品类目和商品属性关联数据
	 * @param classificationPropRealtionVo
	 * @param pagination		分页对象
	 * @return
	 */
	@RequestMapping("relationList")
	@ResponseBody
	public String relationList(ClassificationPropRealtionVo classificationPropRealtionVo,
			Pagination<ClassificationPropRealtionVo> pagination){
		pagination = classificationPropRealtionService.selectPageList(classificationPropRealtionVo, pagination);
		JsonPageVo<ClassificationPropRealtionVo> jsonPageVo = 
				new JsonPageVo<ClassificationPropRealtionVo>(pagination.getDatas(),pagination.getTotalRow());
		return setExtSelfObjectResult(jsonPageVo,true, new String[]{}, null);
	}
	
	/**
	 * 批量保存商品类目和商品属性关联关系
	 * @param classificationId		商品类目ID
	 * @param ids
	 * @return
	 */
	@RequestMapping("saveRelationList")
	@ResponseBody
	public String saveRelationList(Long classificationId,Long[] ids){
		if(classificationId == null){
			return setExtOptResult(false,"商品类目ID不可为空");
		}
		
		if(ids==null || ids.length==0){
			return setExtOptResult(false,"商品属性ID不可为空");
		}
		
		try {
			boolean flag = classificationPropRealtionService.addList(classificationId,ids);
			
			if(flag){
				return setExtOptResult(true, null);
			}
		} catch (Exception e) {
			return setExtOptResult(false,e.getMessage());
		}
		
		return setExtOptResult(false, "操作失败");
	}
	
	/**
	 * 修改商品类目状态
	 * @param request
	 * @return
	 */
	@RequestMapping("editStatus")
	@ResponseBody
	public String editStatus(HttpServletRequest request){
		try {
			String classificationId = request.getParameter("classifcationId");
			String status = request.getParameter("status");
			
			if(StringUtils.isBlank(classificationId)){
				throw new Exception("商品类目ID不能为空");
			}
			if(!NumberUtils.isNumber(classificationId)){
				throw new Exception("商品类目ID出错");
			}
			
			if(StringUtils.isBlank(status)){
				throw new Exception("商品类目修改状态不能为空");
			}
			if(!NumberUtils.isNumber(status)){
				throw new Exception("商品类目修改状态出错");
			}
			
			boolean flag = classificationService.editStatus(Long.parseLong(classificationId),Integer.parseInt(status));
			if (flag) {
				return setExtOptResult(true, null);
			}
			return setExtOptResult(false, "操作失败");
		} catch (Exception e) {
			return setExtOptResult(false, e.getMessage());
		}
	}
	
	/**
	 * 删除商品类目
	 * @param request
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public String delete(HttpServletRequest request){
		try {
			String classificationId = request.getParameter("classifcationId");
			if(StringUtils.isBlank(classificationId)){
				throw new Exception("商品类目ID不能为空");
			}
			if(!NumberUtils.isNumber(classificationId)){
				throw new Exception("商品类目ID出错");
			}
			
			boolean flag = classificationService.delete(Long.parseLong(classificationId));
			if (flag) {
				return setExtOptResult(true, null);
			}
			return setExtOptResult(false, "操作失败");
		} catch (Exception e) {
			return setExtOptResult(false, e.getMessage());
		}
	}
	
	/**
	 * 查询商品类目
	 * @param request
	 * @return
	 */
	@RequestMapping("selectById")
	@ResponseBody
	public String selectById(HttpServletRequest request){
		try {
			String classificationId = request.getParameter("id");
			if(StringUtils.isBlank(classificationId)){
				throw new Exception("商品类目ID不能为空");
			}
			if(!NumberUtils.isNumber(classificationId)){
				throw new Exception("商品类目ID出错");
			}
			
			Classification classification = classificationService.selectById(Long.parseLong(classificationId));
			
			return setExtJsonDataResult(classification, true, new String[]{""}, null, null);
		} catch (Exception e) {
			return setExtOptResult(false, e.getMessage());
		}
	}
	
	/**
	 * 修改商品类目
	 * @param classification	商品类目对象
	 * @return
	 */
	@RequestMapping("update")
	@ResponseBody
	public String update(Classification classification){
		try {
			boolean flag = classificationService.update(classification);
			if (flag) {
				return setExtOptResult(true, null);
			}
		} catch (ClassificationException e) {
			return setExtOptResult(false, e.getMessage());
		}
		return setExtOptResult(false, "操作失败");
	}
	
	/**
	 * 批量删除商品类目和商品属性关联关系
	 * @param classificationId		商品类目ID
	 * @param ids					需删除的商品属性ID集合
	 * @return
	 */
	@RequestMapping("deleteRealtionList")
	@ResponseBody
	public String deleteRealtionList(Long classificationId,Long[] ids){
		if(null == classificationId){
			return setExtOptResult(false,"商品类目ID不可为空");
		}
		
		if(null==ids || 0==ids.length){
			return setExtOptResult(false,"商品属性ID不可为空");
		}
		
		try {
			boolean flag = classificationPropRealtionService.deleteRealtionList(classificationId,ids);
			
			if(flag){
				return setExtOptResult(true, null);
			}
		} catch (Exception e) {
			return setExtOptResult(false,e.getMessage());
		}
		
		return setExtOptResult(false, "操作失败");
	}
	
//	@RequestMapping("updateRealtionSort")
//	@ResponseBody
//	public String updateRealtionSort(Integer sort,Long[] ids){
//		if(sort == null){
//			return setExtOptResult(false,"排序类型错误");
//		}
//		
//		if(ids==null || ids.length==0){
//			return setExtOptResult(false,"关联ID不可为空");
//		}
//		
//		try {
//			boolean flag = classificationPropRealtionService.updateRealtionSort(sort,ids);
//			
//			if(flag){
//				return setExtOptResult(true, null);
//			}
//		} catch (Exception e) {
//			return setExtOptResult(false,e.getMessage());
//		}
//		
//		return setExtOptResult(false, "操作失败");
//	}
	
	@RequestMapping("updateRealtionSort")
	@ResponseBody
	public String updateRealtionSort(Integer sort,String ids){
		if(sort == null){
			return setExtOptResult(false,"排序类型错误");
		}
		
		if(ids==null || "".equals(ids.trim())){
			return setExtOptResult(false,"关联ID不可为空");
		}
		
		try {
			boolean flag = classificationPropRealtionService.updateRealtionSort(sort,ids);
			
			if(flag){
				return setExtOptResult(true, null);
			}
		} catch (Exception e) {
			return setExtOptResult(false,e.getMessage());
		}
		
		return setExtOptResult(false, "操作失败");
	}
}
