package com.jzwgj.management.ctl.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jzwgj.management.component.metaq.producer.ChoicenessMetaOperateProducer;
import com.jzwgj.management.ctl.BaseCtl;
import com.jzwgj.management.exception.FancyMessageException;
import com.jzwgj.management.server.web.fancy.domain.FancyMessage;
import com.jzwgj.management.server.web.fancy.domain.FancyMessageItem;
import com.jzwgj.management.server.web.fancy.domain.dto.FancyMessageDto;
import com.jzwgj.management.server.web.fancy.service.FancyMessageService;
import com.jzwgj.management.util.PropertiesUtil;
import com.jzwgj.management.util.QRCodeUtil;
import com.jzwgj.management.vo.JsonPageVo;
import com.zjlp.face.file.service.ImageService;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.json.JsonUtil;
import com.zjlp.face.util.page.Pagination;

@Controller
@RequestMapping("/web/fancy/")
public class FancyMessageCtl extends BaseCtl {
	
	private Log log=LogFactory.getLog(getClass());
	
	@Autowired
	private FancyMessageService fancyMessageService;
	@Autowired
	private ChoicenessMetaOperateProducer choicenessMetaOperateProducer;
	@Autowired
	private ImageService imageService;
	
	/**
	 * @Description: 查询全部:status:-1或null,type:-1或null
	 * @date: 2015年7月28日 下午2:04:56
	 * @author: zyl
	 */
	@RequestMapping(value="list")
	@ResponseBody
	public String list(FancyMessage fancyMessage,Pagination<FancyMessage> pagination){
		pagination = fancyMessageService.findFancyMessagePageList(fancyMessage,pagination);
		JsonPageVo<FancyMessage> jsonPageVo = new JsonPageVo<FancyMessage>(pagination.getDatas(),pagination.getTotalRow());
		return setExtSelfObjectResult(jsonPageVo,true, new String[]{}, null);
	}
	
	/**
	 * @Description: 批量删除脸谱精选,ids格式:['1','2']
	 * @date: 2015年7月28日 上午10:19:06
	 * @author: zyl
	 */
	@RequestMapping(value="batchDelete",method=RequestMethod.POST)
	@ResponseBody
	public String batchDelete(Long[] ids){
		
		List<Long> pushId = new ArrayList<Long>();
		for(Long id : ids){
			try{
				FancyMessage fancy = fancyMessageService.getFancyMessage(id);
				if (null != fancy.getStatus() && fancy.getStatus() == 1) {
					pushId.add(id);
				}
				fancyMessageService.deleteById(id);
			}catch(Exception e){
				log.error(e.getMessage()+","+id,e);
				return setExtOptResult(false,"删除ID：" + id + "失败_" + e.getMessage());
			}
		}
		/*try{
			choicenessMetaOperateProducer.ansyAll(pushId);
		}catch(Exception e ){
			log.error("刷脸精选消息推送失败!", e);
		}*/

		return setExtOptResult(true, null);
	}
	@RequestMapping(value="add")
	@ResponseBody
	public String add(String name,Integer type,String jsonData,Integer status) {
//		FancyMessageDto fancyMessageDto = JsonUtil.toBean(jsonData, FancyMessageDto.class);
		FancyMessageDto fancyMessageDto = new FancyMessageDto();
		fancyMessageDto.setName(name);
		fancyMessageDto.setType(type);
		fancyMessageDto.setStatus(status);
		Date date = new Date();
		fancyMessageDto.setCreateTime(date);
		fancyMessageDto.setUpdateTime(date);
		try {
			List<FancyMessageItem> itemList = JsonUtil.toArrayBean(jsonData, FancyMessageItem.class);
			fancyMessageDto.setItemList(itemList);
			fancyMessageService.add(fancyMessageDto);
			return setExtSelfObjectResult(fancyMessageDto,true, new String[]{"publishTime", "createTime", "updateTime"}, null);
//			return setExtOptResult(true, fancyMessageDto.toString());
		} catch (FancyMessageException e) {
			log.error("新增刷脸精选信息失败", e);
			return setExtOptResult(false, "操作失败");
		} catch (Exception e) {
			log.error("新增刷脸精选信息失败", e);
			return setExtOptResult(false, "操作失败");
		}
	}
	
	@RequestMapping(value="edit")
	@ResponseBody
	public String edit(FancyMessageDto fancyMessageDto,String jsonData) {
		try {
			List<FancyMessageItem> itemList = JsonUtil.toArrayBean(jsonData, FancyMessageItem.class);
			FancyMessageDto fancy  = fancyMessageService.getFancyMessage(fancyMessageDto.getId());
			if(!fancy.getStatus().equals(1)){
				fancy.setStatus(fancyMessageDto.getStatus());
			}
			fancy.setName(fancyMessageDto.getName());
			fancy.setItemList(itemList);
			fancyMessageService.edit(fancy);
			return setExtSelfObjectResult(fancyMessageDto,true, new String[]{}, null);
//			return setExtOptResult(true, null);
		} catch (FancyMessageException e) {
			log.error("编辑刷脸精选信息失败", e);
			return setExtOptResult(false, "操作失败");
		}
	}
	
	@RequestMapping(value="initEdit",method = RequestMethod.GET)
	public String initEdit(Long id,Model model) {
		FancyMessageDto dto = null;
		try {
			AssertUtil.notNull(id, "id不存在，操作失败！");
			dto = fancyMessageService.getFancyMessage(id);
			//AssertUtil.isTrue(!(dto.getStatus() == 1), "刷脸精选已发布，不能编辑");
			model.addAttribute("dto", dto);
			if (dto.getType().equals(1)) {
				return "/admin/web/fancy/message-set1";
			} else {
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.setExcludes(new String[] { "publishTime", "createTime", "updateTime" });  
				model.addAttribute("jsonData",JSONArray.fromObject(dto.getItemList(),jsonConfig).toString());
				return "/admin/web/fancy/message-set2";
			}
		} catch (Exception e) {
			return setExtOptResult(false, "操作失败");
		}
	}
	
	@RequestMapping(value="previewHome")
	public String previewHome(HttpServletRequest request,Model model) {
		Long id = Long.parseLong(request.getParameter("id"));
		AssertUtil.notNull(id, "id不存在，操作失败！");
		String type = request.getParameter("type");
		
		FancyMessageDto fancyMessageDto = fancyMessageService.getFancyMessage(id);
		AssertUtil.notNull(fancyMessageDto, "没有该精选，操作失败！");
		
		List<FancyMessageItem> itemList = fancyMessageDto.getItemList();
		
		String picUrl = PropertiesUtil.getContexrtParam("ROOT_PICURL");
		
		model.addAttribute("itemList", itemList);
		model.addAttribute("picUrl", picUrl);
		
		if(type == null){
			type = "web";
		}
		model.addAttribute("type", type);
		
		return "/admin/web/fancy/message_home_preview";
	}
	
	@RequestMapping(value="previewDetail")
	public String previewHomeGet(Model model,HttpServletRequest request) {
		Long id = Long.parseLong(request.getParameter("id"));
		FancyMessageItem fancyMessageItem = fancyMessageService.getFancyMessageItem(id);
		String wgjUrl = PropertiesUtil.getContexrtParam("wgjUrl");
		
		String type = request.getParameter("type");
		
		model.addAttribute("fancyMessageItem", fancyMessageItem);
		model.addAttribute("wgjUrl", wgjUrl);
		model.addAttribute("type", type);
		model.addAttribute("path",wgjUrl + "/message/fancy/details/"+id+".htm");
		return "/admin/web/fancy/message_detail_preview";
	}
	
	@RequestMapping(value="QRCode")
	public String QRCode(Model model,HttpServletRequest request) {
		Long id = Long.parseLong(request.getParameter("id"));
		AssertUtil.notNull(id, "id不存在，操作失败！");
		
		String basePath = request.getScheme() + "://" + request.getServerName()  +":"  +request.getServerPort() + request.getContextPath();
		String codeUrl = basePath + "/web/fancy/previewHome.htm?id=" + id + "&type=app";
		
		String path = generatedCode(codeUrl);
		
		if(path!=null && path.contains("resources/")){
			String picUrl = PropertiesUtil.getContexrtParam("ROOT_PICURL");
			path = picUrl + path;
		}else{
			String wgjUrl = PropertiesUtil.getContexrtParam("wgjUrl");
			path = wgjUrl + path;
			
		}
		
		model.addAttribute("path", path);

		return "/admin/web/fancy/message_QRCode_preview";
	}
	
	private String generatedCode(String codeUrl){
		try {
			byte[] b = null;
			
			byte[] qrbyte = QRCodeUtil.encode(codeUrl, b, true);
			
			if(null == qrbyte){
				return null;
			}
			
			StopWatch uploadClock = new StopWatch();
			uploadClock.start(); // 计时开始
			
			//上传二维码
			String flag = imageService.upload(qrbyte);
			
			uploadClock.stop();
			
			JSONObject jsonObject = JSONObject.fromObject(flag);
			if(!"SUCCESS".equals(jsonObject.get("flag").toString())){
				return null;
			}
			if(StringUtils.isBlank(jsonObject.getString("path"))){
				return null;
			}
			
			return jsonObject.getString("path");
		} catch (Exception e) {
			return null;
		}
	}
}
