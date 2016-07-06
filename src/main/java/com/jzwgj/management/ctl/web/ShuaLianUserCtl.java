package com.jzwgj.management.ctl.web;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jzwgj.management.ctl.BaseCtl;
import com.jzwgj.management.exception.ShuaLianUserException;
import com.jzwgj.management.server.web.activity.domain.SlcoinRecord;
import com.jzwgj.management.server.web.activity.domain.SlcoinRecordVo;
import com.jzwgj.management.server.web.activity.service.SlcoinRecordService;
import com.jzwgj.management.server.web.user.domain.ShuaLianUser;
import com.jzwgj.management.server.web.user.service.ShuaLianUserService;
import com.jzwgj.management.vo.JsonPageVo;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Pagination;

@Controller
@RequestMapping("/web/user/sl/")
public class ShuaLianUserCtl extends BaseCtl {
	
	private Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private ShuaLianUserService shuaLianUserService;
	
	@Autowired
	private SlcoinRecordService slcoinRecordService;
	

	/**
	 * 分页查询刷脸用户列表
	 * @param gwInformation
	 * @param pagination
	 * @return
	 */
	@RequestMapping(value="userlist")
	@ResponseBody
	public String userlist(ShuaLianUser shuaLianUser,Pagination<ShuaLianUser> pagination){
		try {
			pagination = shuaLianUserService.findUserPageList(shuaLianUser, pagination);
			JsonPageVo<ShuaLianUser> jsonPageVo = new JsonPageVo<ShuaLianUser>(pagination.getDatas(),pagination.getTotalRow());
			return setExtSelfObjectResult(jsonPageVo,true, new String[]{}, null);
		} catch (ShuaLianUserException e) {
			log.error("分页查询刷脸用户列表出错:" + e.getMessage(),e);
			return setExtSelfObjectResult(null,true, new String[]{}, null);
		}
	}
	
	@RequestMapping(value="getUserNumMap")
	@ResponseBody
	public String getUserNumMap() {
		try {
			Map<String,Integer> map = shuaLianUserService.getUserNumMap();
			return setExtSelfObjectResult(map,true, new String[]{}, null);
		} catch (ShuaLianUserException e) {
			log.error("查询总注册用户数,今日新增用户数失败", e);
			return setExtOptResult(false, "操作失败");
		}
	}
	
	@RequestMapping(value="userDetail",method = RequestMethod.GET)
	public String userDetail(Long userId,Model model) {
		try {
			AssertUtil.notNull(userId, "id不存在");
			ShuaLianUser shuaLianUser = shuaLianUserService.getUserByUserId(userId);
			AssertUtil.notNull(shuaLianUser, "用户不存在");
			model.addAttribute("shuaLianUser", shuaLianUser);
			
			return "/admin/web/user/shuaLianUserInfo";
		} catch (Exception e) {
			return setExtOptResult(false, e.getMessage());
		}
	}
	
	@RequestMapping(value="userEdit",method = RequestMethod.GET)
	public String userEdit(Long userId,Model model) {
		try {
			AssertUtil.notNull(userId, "id不存在");
			ShuaLianUser shuaLianUser = shuaLianUserService.getUserByUserId(userId);
			AssertUtil.notNull(shuaLianUser, "用户不存在");
			model.addAttribute("shuaLianUser", shuaLianUser);
			
			if(null == shuaLianUser.getMemberCard()){
				model.addAttribute("isMemberCard", true);
			}
			if(null == shuaLianUser.getBusinessCard()){
				model.addAttribute("isBusinessCard", true);
			}
			
			return "/admin/web/user/shuaLianUserEdit";
		} catch (Exception e) {
			return setExtOptResult(false, e.getMessage());
		}
	}
	
	@RequestMapping(value="checkLpNo")
	@ResponseBody
	public String checkLpNo(String lpNo,Long userId) {
		try {
			AssertUtil.hasLength(lpNo, "请输入刷脸号");
			if(!StringUtils.isNumeric(lpNo)){
				AssertUtil.isTrue(false, "刷脸号必须为数字");
			}
			Boolean isExist = shuaLianUserService.checkLpNo(lpNo,userId);
			if(isExist){
				AssertUtil.isTrue(false, "刷脸号已存在");
			}
			return setExtOptResult(true, "");
		} catch (ShuaLianUserException e) {
			log.error("查询总注册用户数,今日新增用户数失败", e);
			return setExtOptResult(false, e.getMessage());
		} catch (Exception e) {
			log.error("查询总注册用户数,今日新增用户数失败", e);
			return setExtOptResult(false, e.getMessage());
		}
	}
	
	@RequestMapping(value="updateUser",method = RequestMethod.POST)
	public String updateUser(ShuaLianUser shuaLianUser,Model model) {
		try {
			AssertUtil.notNull(shuaLianUser, "修改对象不能为空");
			shuaLianUserService.update(shuaLianUser);
			model.addAttribute("succeed", true);
		} catch (ShuaLianUserException e) {
			log.error("修改用户信息出错", e);
			model.addAttribute("succeed", false);
			model.addAttribute("error", e.getMessage());
		}

		ShuaLianUser newShuaLianUser = shuaLianUserService.getUserByUserId(shuaLianUser.getUserId());
		model.addAttribute("shuaLianUser", newShuaLianUser);
		return "/admin/web/user/shuaLianUserEdit";
	}
	
	@RequestMapping(value="userAccountInfo",method = RequestMethod.GET)
	public String userAccountInfo (Long userId,Model model) {
		try {
			AssertUtil.notNull(userId, "id不存在");
			ShuaLianUser shuaLianUser = shuaLianUserService.userAccountInfo(userId);
			model.addAttribute("shuaLianUser", shuaLianUser);
			return "/admin/web/user/account-info";
		} catch (Exception e) {
			return setExtOptResult(false, e.getMessage());
		}
	}
	
	@RequestMapping(value="userSlcoinRecord",method = RequestMethod.GET)
	public String userSlcoinRecord (SlcoinRecordVo slcoinRecordVo,Pagination<SlcoinRecordVo> pagination,Model model) {
		try {
			slcoinRecordService.findSlcoinRecordPageList(slcoinRecordVo, pagination);
			model.addAttribute("slcoinRecordVo", slcoinRecordVo);
			model.addAttribute("pagination", pagination);
			return "/admin/web/user/account-expenses-record";
		} catch (Exception e) {
			return setExtOptResult(false, e.getMessage());
		}
	}
	
	@RequestMapping(value="giveUserSlcoin",method = RequestMethod.POST)
	@ResponseBody
	public String giveUserSlcoin (SlcoinRecord slcoinRecord) {
		try {
			AssertUtil.notNull(slcoinRecord, "slcoinRecord不存在");
			slcoinRecordService.giveUserSlcoin(slcoinRecord);
			return getReqJson(true, "赠送成功");
		}catch (Exception e) {
			return setExtOptResult(false, e.getMessage());
		}
		
	}
}
