package com.jzwgj.management.ctl.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jzwgj.management.ctl.BaseCtl;
import com.jzwgj.management.exception.GwInformationException;
import com.jzwgj.management.server.web.information.domain.GwInformation;
import com.jzwgj.management.server.web.information.service.GwInformationService;
import com.jzwgj.management.vo.JsonPageVo;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.json.JsonUtil;
import com.zjlp.face.util.page.Pagination;

@Controller
@RequestMapping("/web/information/")
public class GwInformationCtl extends BaseCtl {
	
	private Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private GwInformationService gwInformationService;

	/**
	 * 分页查询资讯列表
	 * @param gwInformation
	 * @param pagination
	 * @return
	 */
	@RequestMapping(value="list")
	@ResponseBody
	public String list(GwInformation gwInformation,Pagination<GwInformation> pagination){
		pagination = gwInformationService.findGwInformationPageList(gwInformation, pagination);
		JsonPageVo<GwInformation> jsonPageVo = new JsonPageVo<GwInformation>(pagination.getDatas(),pagination.getTotalRow());
		return setExtSelfObjectResult(jsonPageVo,true, new String[]{}, null);
	}
	
	/**
	 * 批量删除资讯
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	@ResponseBody
	public String delete(Long[] ids){
		try {
			gwInformationService.deleteById(ids);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return setExtOptResult(false,e.getMessage());
		}
		
		return setExtOptResult(true, null);
	}
	
	/**
	 * 新增资讯
	 * @param gwInformation
	 * @return
	 */
	@RequestMapping(value="add")
	@ResponseBody
	public String add(String jsonData) {
		GwInformation gwInformation = JsonUtil.toBean(jsonData, GwInformation.class);
		try {
			gwInformationService.add(gwInformation);
		} catch (GwInformationException e) {
			log.error(e.getMessage(),e);
			return setExtOptResult(false,e.getMessage());
		}
		
//		return setExtOptResult(true, gwInformation.getStatus().toString());
		return setExtOptResult(true, "");
	}
	
	/**
	 * 修改资讯
	 * @param gwInformation
	 * @return
	 */
	@RequestMapping(value="edit")
	@ResponseBody
	public String edit(String jsonData) {
		GwInformation gwInformation = JsonUtil.toBean(jsonData, GwInformation.class);
		try {
			gwInformationService.edit(gwInformation);
		} catch (GwInformationException e) {
			log.error(e.getMessage(),e);
			return setExtOptResult(false,e.getMessage());
		}
		
//		return setExtOptResult(true, gwInformation.getStatus()==null ? "1" : gwInformation.getStatus().toString());
		return setExtOptResult(true, "");
	}
	
	@RequestMapping(value="initEdit",method = RequestMethod.GET)
	public String initEdit(Long id,Model model) {
		try {
			AssertUtil.notNull(id, "id不存在");
			GwInformation gwInformation = gwInformationService.getGwInformation(id);
			AssertUtil.notNull(gwInformation, "资讯不存在");
			model.addAttribute("gwInformation", gwInformation);
			
			return "/admin/web/information/article-cont";
		} catch (Exception e) {
			return setExtOptResult(false, e.getMessage());
		}
	}
	
	/**
	 * 排序
	 * @param sort
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="updateSort")
	@ResponseBody
	public String updateSort(Integer sort,String ids) {
		try {
			gwInformationService.updateSort(sort,ids);
			return setExtOptResult(true, null);
		} catch (Exception e) {
			return setExtOptResult(false,e.getMessage());
		}
	}
}
