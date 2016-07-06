package com.jzwgj.management.ctl.admin;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jzwgj.management.ctl.BaseCtl;
import com.jzwgj.management.server.sys.domain.CAttachment;
import com.jzwgj.management.server.sys.service.AttachmentService;
import com.jzwgj.management.util.Pagination;

@Controller
@RequestMapping("/common/")
public class AttachmentCtl extends BaseCtl {
	
	@Autowired
	private AttachmentService attachmentService;
	
	@RequestMapping("listUploadAttachment")
	@ResponseBody
	public String list(String Q_uaTableid_S_EQ,String attachFileIds,Pagination<CAttachment> pagination){
		CAttachment cAttachment = new CAttachment();
		if(StringUtils.isNotEmpty(Q_uaTableid_S_EQ)){
			cAttachment.setTableId(Long.valueOf(Q_uaTableid_S_EQ));
		}else if(StringUtils.isNotEmpty(attachFileIds)){
			cAttachment.setAttachFileIds(attachFileIds);
		}
		pagination = attachmentService.findPageList(cAttachment,pagination);
		return setJsonString(pagination.getTotalRow(), true, pagination.getDatas(), null);
	}
	
	@RequestMapping("updateUploadAttachment")
	@ResponseBody
	public String update(String[] uaIds,String tableName,Long tableId){
		attachmentService.editAll(uaIds, tableName, tableId);
		return setExtOptResult(true, null);
	}
	
	
	@RequestMapping("addUploadFile")
	@ResponseBody
	public String add(CAttachment cAttachment){
		attachmentService.add(cAttachment);
		return setExtOptResult(true, null);
	}
	
	@RequestMapping("updateTempTableId")
	@ResponseBody
	public String editTableId(String tempTableId,String tableId){
		attachmentService.editTableId(tempTableId,tableId);
		return setExtOptResult(true, null);
	}
	
	@RequestMapping("deleteUploadFile")
	@ResponseBody
	public String del(Long id){
		attachmentService.delete(id);
		return setExtOptResult(true, null);
	}
	
	@RequestMapping("queryUploadFileByTable")
	@ResponseBody
	public String getByTable(CAttachment cAttachment){
		attachmentService.getByTable(cAttachment);
		return null;
	}
	
	@RequestMapping("previewImg")
	@ResponseBody
	public String previewImg(String uaId){
		CAttachment fileAttach =attachmentService.previewImg(uaId);
		return setExtJsonDataResult(fileAttach);
	}
	
	@RequestMapping("delAll")
	@ResponseBody
	public String delAll(String[] ids){
		attachmentService.deleteAll(ids);
		return setExtOptResult(true, null);
	}
	
	@RequestMapping("queryTitleImgInfo")
	@ResponseBody
	public String queryTitleImgInfo(String tableId,String type){
		CAttachment fileAttach = attachmentService.getTitleImg(tableId,type);
		if (null != fileAttach) {
			return setExtOptResult(true, fileAttach.getName());
		}
		return setExtOptResult(true, null);
	}

}
