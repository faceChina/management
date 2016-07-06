package com.jzwgj.management.ctl.web;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jzwgj.management.ctl.BaseCtl;
import com.jzwgj.management.exception.SlcoinActivityException;
import com.jzwgj.management.exception.SlcoinShopException;
import com.jzwgj.management.server.web.activity.domain.SlcoinActivity;
import com.jzwgj.management.server.web.activity.domain.SlcoinActivityVo;
import com.jzwgj.management.server.web.activity.domain.SlcoinInvitationRegister;
import com.jzwgj.management.server.web.activity.domain.SlcoinRecordVo;
import com.jzwgj.management.server.web.activity.domain.SlcoinShopVo;
import com.jzwgj.management.server.web.activity.service.SlcoinActivityService;
import com.jzwgj.management.server.web.activity.service.SlcoinInvitationRegisterService;
import com.jzwgj.management.server.web.activity.service.SlcoinShopService;
import com.jzwgj.management.util.DateUtils;
import com.jzwgj.management.util.SimpleExcelWrite;
import com.jzwgj.management.vo.JsonPageVo;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.user.user.domain.vo.UserVoObj;

@Controller
@RequestMapping("/web/activity/")
public class SlcoinActivityCtl extends BaseCtl {
	
	private Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private SlcoinActivityService slcoinActivityService;
	
	@Autowired
	private SlcoinShopService slcoinShopService;
	
	@Autowired
	private SlcoinInvitationRegisterService slcoinInvitationRegisterService;
	
	private static String DETAIL_TYPE = "1";

	/**
	 * 分页查询刷脸活动列表
	 * @param gwInformation
	 * @param pagination
	 * @return
	 */
	@RequestMapping(value="list")
	@ResponseBody
	public String list(SlcoinActivityVo slcoinActivityVo,Pagination<SlcoinActivityVo> pagination){
		try {
			pagination = slcoinActivityService.findSlcoinActivityPageList(slcoinActivityVo, pagination);
			JsonPageVo<SlcoinActivityVo> jsonPageVo = new JsonPageVo<SlcoinActivityVo>(pagination.getDatas(),pagination.getTotalRow());
			return setExtSelfObjectResult(jsonPageVo,true, new String[]{}, null);
		} catch (SlcoinActivityException e) {
			log.error(e.getMessage(),e);
			return setExtSelfObjectResult(null,true, new String[]{}, null);
		}
	}
	
	@RequestMapping(value="insert")
	@ResponseBody
	public String insert(SlcoinActivityVo slcoinActivityVo) {
		try {
			slcoinActivityVo.setBeginTime(DateUtils.getDate(slcoinActivityVo.getBeginTimeVo()));
			slcoinActivityVo.setEndTime(DateUtils.getDate(slcoinActivityVo.getEndTimeVo()));
			
			slcoinActivityService.insert(slcoinActivityVo);
			return setExtOptResult(true, "");
		} catch (SlcoinActivityException e) {
			log.error(e.getMessage(), e);
			return setExtOptResult(false, e.getMessage());
		}
	}
	
	@RequestMapping(value="detail")
	public String detail(Long id,String type,Model model) {
		try {
			SlcoinActivity slcoinActivity = slcoinActivityService.selectByPrimaryKey(id);
			model.addAttribute("slcoinActivity", slcoinActivity);
			
			if(DETAIL_TYPE.equals(type)){
				return "/admin/web/activity/activity-detail";
			}else{
				return "/admin/web/activity/activity-update";
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return setExtOptResult(false, e.getMessage());
		}
	}
	
	@RequestMapping(value="update")
	@ResponseBody
	public String update(SlcoinActivityVo slcoinActivityVo) {
		try {
			slcoinActivityVo.setBeginTime(DateUtils.getDate(slcoinActivityVo.getBeginTimeVo()));
			slcoinActivityVo.setEndTime(DateUtils.getDate(slcoinActivityVo.getEndTimeVo()));
			
			slcoinActivityService.update(slcoinActivityVo);
			return setExtOptResult(true, "");
		} catch (SlcoinActivityException e) {
			log.error(e.getMessage(), e);
			return setExtOptResult(false, e.getMessage());
		}
	}
	
	@RequestMapping(value="updateTime")
	@ResponseBody
	public String updateTime(Long id,Integer type) {
		try {
			slcoinActivityService.updateTime(id,type);
			return setExtOptResult(true, "");
		} catch (SlcoinActivityException e) {
			log.error(e.getMessage(), e);
			return setExtOptResult(false, e.getMessage());
		}
	}
	
	@RequestMapping(value="slcoinTotal")
	@ResponseBody
	public String slcoinTotal() {
		try {
			Map<String,Integer> totalMap = slcoinShopService.getTotalSlcoin();
			return getReqJson(true,totalMap);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return getReqJson(false,e.getMessage());
		}
	}
	
	@RequestMapping(value="slcoinDateReport")
	@ResponseBody
	public String slcoinDateReport(SlcoinRecordVo slcoinRecordVo,Pagination<SlcoinRecordVo> pagination) {
		try {
			pagination = slcoinShopService.getslcoinReportByTime(slcoinRecordVo,pagination);
			return getReqJson(true,pagination);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return getReqJson(false,e.getMessage());
		}
	}
	
	@RequestMapping(value="shopList")
	@ResponseBody
	public String shopList(SlcoinRecordVo slcoinRecordVo,Pagination<SlcoinShopVo> pagination) {
		try {
			pagination = slcoinShopService.findSlcoinShopByPageList(slcoinRecordVo, pagination);
			return getReqJson(true,pagination);
		} catch (SlcoinShopException e) {
			log.error(e.getMessage(),e);
			return getReqJson(false,e.getMessage());
		}
	}
	
	@RequestMapping(value="slcoinCurrentTime")
	@ResponseBody
	public String slcoinCurrentTime(SlcoinRecordVo slcoinRecordVo) {
		try {
			Map<String,Integer> map = slcoinShopService.getSlcoinRecordByCurrentTime(slcoinRecordVo);
			return getReqJson(true,map);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return getReqJson(false,e.getMessage());
		}
	}
	
	@RequestMapping(value="invitationRegisterList")
	@ResponseBody
	public String invitationRegisterList(UserVoObj userVoObj,String loginAccountType,Pagination<SlcoinInvitationRegister> pagination){
		try {
			if(StringUtils.isBlank(loginAccountType) || !StringUtils.isNumeric(loginAccountType)){
				loginAccountType = "1";
			}
			pagination = slcoinInvitationRegisterService.findSlcoinInvitationRegisterPageList(userVoObj, pagination,Integer.parseInt(loginAccountType));
			JsonPageVo<SlcoinInvitationRegister> jsonPageVo = new JsonPageVo<SlcoinInvitationRegister>(pagination.getDatas(),pagination.getTotalRow());
			return setExtSelfObjectResult(jsonPageVo,true, new String[]{}, null);
		} catch (SlcoinActivityException e) {
			log.error(e.getMessage(),e);
			return setExtSelfObjectResult(null,true, new String[]{}, null);
		}
	}
	
	@RequestMapping(value="invitationUserList")
	@ResponseBody
	public String invitationUserList(UserVoObj userVoObj,String loginAccountType,Pagination<SlcoinInvitationRegister> pagination){
		try {
			if(StringUtils.isBlank(loginAccountType) || !StringUtils.isNumeric(loginAccountType)){
				loginAccountType = "1";
			}
			pagination = slcoinInvitationRegisterService.findSlcoinInvitationRegisterPageList(userVoObj, pagination,Integer.parseInt(loginAccountType));
			JsonPageVo<SlcoinInvitationRegister> jsonPageVo = new JsonPageVo<SlcoinInvitationRegister>(pagination.getDatas(),pagination.getTotalRow());
			return setExtSelfObjectResult(jsonPageVo,true, new String[]{}, null);
		} catch (SlcoinActivityException e) {
			log.error(e.getMessage(),e);
			return setExtSelfObjectResult(null,true, new String[]{}, null);
		}
	}
	
	@RequestMapping(value="exportData")
	public void exportData(UserVoObj userVoObj,String loginAccountType,HttpServletResponse response){
		try {
			if(StringUtils.isBlank(loginAccountType) || !StringUtils.isNumeric(loginAccountType)){
				loginAccountType = "1";
			}
			
			String fname = "导出数据";
		    OutputStream os = response.getOutputStream();//取得输出流
		    response.reset();//清空输出流
		    
		    //下面是对中文文件名的处理
//		    response.setCharacterEncoding("UTF-8");//设置相应内容的编码格式
		    fname = java.net.URLEncoder.encode(fname,"UTF-8");
		    response.setHeader("Content-Disposition","attachment;filename="+new String(fname.getBytes("UTF-8"),"GBK")+".xls");
		    response.setContentType("application/msexcel");//定义输出类型

			List<SlcoinInvitationRegister> list = slcoinInvitationRegisterService.findSlcoinInvitationRegisterList(userVoObj,Integer.parseInt(loginAccountType));
			
		    SimpleExcelWrite sw = new SimpleExcelWrite();
		    sw.createExcel(os,list);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
