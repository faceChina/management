package com.jzwgj.management.ctl.admin;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.jzwgj.management.ctl.BaseCtl;
import com.jzwgj.management.server.sys.domain.CUser;
import com.jzwgj.management.server.sys.service.ModualService;
import com.jzwgj.management.server.sys.service.UserService;
import com.jzwgj.management.vo.ModuleTreeNodeVo;

@Controller
@RequestMapping("/admin/index/")
@SessionAttributes("sessionUser")
public class LoginCtl extends BaseCtl {
	
	@Autowired
	private ModualService modualService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("left")
	@ResponseBody
	public String leftTree(Long moduleId,HttpSession httpSession){
		List<ModuleTreeNodeVo> treeNodeList = modualService.findListByPid(moduleId);
		
		CUser cUser = (CUser)httpSession.getAttribute("sessionUser");
		if(!"superadmin".equals(cUser.getLoginName()) && !"admin".equals(cUser.getLoginName())){
			for(int i=0;i<treeNodeList.size();i++){
				ModuleTreeNodeVo vo = treeNodeList.get(i);
				if("系统管理".equals(vo.getText())){
					treeNodeList.remove(i);
				}
			}
		}
		return setExtObjectResult(treeNodeList);
	}
	
	@RequestMapping("index")
	public String index(){
		return "/admin/sys/main/login";
	}
	
	@RequestMapping("login")
	@ResponseBody
	public String login(@RequestParam(required=true) String loginName,
			@RequestParam(required=true) String passWrod,HttpSession httpSession){
		CUser cUser = userService.login(loginName,passWrod);
		if (null != cUser) {
			httpSession.setAttribute("sessionUser", cUser);
			return getReqJson(true, null);
		}else{
			return getReqJson(false, "用户不存在");
		}
	}
	

}
