package com.jzwgj.management.server.web.fancy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jzwgj.management.component.metaq.producer.ChoicenessMetaOperateProducer;
import com.jzwgj.management.exception.FancyMessageException;
import com.jzwgj.management.server.web.fancy.dao.FancyMessageDao;
import com.jzwgj.management.server.web.fancy.dao.FancyMessageItemDao;
import com.jzwgj.management.server.web.fancy.domain.FancyMessage;
import com.jzwgj.management.server.web.fancy.domain.FancyMessageItem;
import com.jzwgj.management.server.web.fancy.domain.dto.FancyMessageDto;
import com.jzwgj.management.server.web.fancy.service.FancyMessageService;
import com.jzwgj.management.util.PropertiesUtil;
import com.zjlp.face.file.dto.FileBizParamDto;
import com.zjlp.face.file.service.ImageService;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Pagination;

@Service("fancyMessageService")
public class FancyMessageServiceImpl implements FancyMessageService {
	
	Logger _logger = Logger.getLogger(getClass());
	@Autowired
	private FancyMessageDao fancyMessageDao;
	@Autowired
	private FancyMessageItemDao fancyMessageItemDao;
	@Autowired
	private ImageService imageService;
	@Autowired
	private ChoicenessMetaOperateProducer choicenessMetaOperateProducer;
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteById(Long id) {
		fancyMessageDao.deleteById(id);
		fancyMessageItemDao.deleteByFancyMessageId(id);
	}
	@SuppressWarnings({ "unchecked", "static-access", "deprecation" })
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Long add(FancyMessageDto fancyMessageDto)throws FancyMessageException {
		Date date = new Date();
		fancyMessageDto.setCreateTime(date);
		fancyMessageDto.setUpdateTime(date);
		if (null != fancyMessageDto.getStatus() && 1 == fancyMessageDto.getStatus()) {
			fancyMessageDto.setPublishTime(date);
		}else {
			fancyMessageDto.setStatus(0);
		}
		Long id = fancyMessageDao.save(fancyMessageDto);
		List<FancyMessageItem> itemList = fancyMessageDto.getItemList();
		
		List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
		for (FancyMessageItem item : itemList) {
			AssertUtil.notNull(item.getPicturePath(), "图片不能为空");
			item.setFancyMessageId(id);
			item.setCreateTime(date);
			item.setUpdateTime(date);
			item.setStatus(1);
			fancyMessageItemDao.save(item);
			if(Integer.valueOf(1).equals(item.getType())){
				FancyMessageItem edit = new FancyMessageItem();
				edit.setId(item.getId());
				edit.setLink(PropertiesUtil.getContexrtParam("wgjUrl")+"/message/fancy/details/"+item.getId()+".htm");
				fancyMessageItemDao.update(edit);
			}
			if(StringUtils.isNotBlank(item.getPicturePath())){
				FileBizParamDto dto = new FileBizParamDto(null,0L,null,"FANCY_MESSAGE_ITEM",item.getId().toString(),"fancyMessageItem",1);
				dto.setImgData(item.getPicturePath());
				list.add(dto);
			}
			if(StringUtils.isNotBlank(item.getContent())){
				FileBizParamDto dto = new FileBizParamDto(null,0L,null,"FANCY_MESSAGE_ITEM",item.getId().toString(),"ubbFile",1);
				dto.setImgData(item.getContent());
				list.add(dto);
			}
		}

		String flag = imageService.addOrEdit(list);
        JSONObject jsonObject = JSONObject.fromObject(flag);
        AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "上传图片失败:"+flag);
        String dataJson = jsonObject.getString("data");
        JSONArray jsonArray = JSONArray.fromObject(dataJson);
        List<FileBizParamDto> fbpDto = jsonArray.toList(jsonArray, FileBizParamDto.class);
		for (FileBizParamDto fd : fbpDto) {
			if(fd.getCode().equals("fancyMessageItem")){
				fd.getTableId();
				FancyMessageItem edit = new FancyMessageItem();
				edit.setId(Long.valueOf(fd.getTableId()));
				edit.setPicturePath(fd.getImgData());
				fancyMessageItemDao.update(edit);
			}
			if(fd.getCode().equals("ubbFile")){
				fd.getTableId();
				FancyMessageItem edit = new FancyMessageItem();
				edit.setId(Long.valueOf(fd.getTableId()));
				edit.setContent(fd.getImgData());
				fancyMessageItemDao.update(edit);
			}
		}
        
		try{
			if (1 == fancyMessageDto.getStatus()) {
				choicenessMetaOperateProducer.senderAnsy(id);
			}
		}catch(Exception e ){
			_logger.error("刷脸精选消息推送失败!", e);
		}

		return id;
	}

	@Override
	public FancyMessageDto getFancyMessage(Long id)throws FancyMessageException {
		AssertUtil.notNull(id, "刷脸精选ID不能为空");
		FancyMessage fancyMessage = fancyMessageDao.selectByPrimaryKey(id);
		FancyMessageDto dto = new FancyMessageDto(fancyMessage);
		List<FancyMessageItem> itemList = fancyMessageItemDao.selectByFancyMessageId(id);
		dto.setItemList(itemList);
		return dto;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean edit(FancyMessageDto fancyMessageDto)throws FancyMessageException {
		AssertUtil.notNull(fancyMessageDto.getId(), "刷脸精选ID不能为空");
		FancyMessage fancydto = fancyMessageDao.selectByPrimaryKey(fancyMessageDto.getId());
		Integer status = fancydto.getStatus();
//		AssertUtil.isTrue(!(fancydto.getStatus() == 1), "刷脸精选已发布，不能编辑");
		AssertUtil.notNull(fancydto, "刷脸精选不存在");
		AssertUtil.notNull(fancyMessageDto.getItemList(), "刷脸精选内容不能为空");
		AssertUtil.isTrue(fancyMessageDto.getItemList().size() > 0, "刷脸精选内容不能为空");
		
		fancydto.setName(fancyMessageDto.getName());
		fancydto.setType(fancyMessageDto.getType());
		Date date = new Date();
		fancyMessageDto.setUpdateTime(date);
		if (null != fancyMessageDto.getStatus() && 1 == fancyMessageDto.getStatus()) {
			fancyMessageDto.setPublishTime(date);
		}else {
			fancyMessageDto.setStatus(0);
		}
		fancydto.setStatus(fancyMessageDto.getStatus());
		if (1 == fancyMessageDto.getStatus()) {
			fancydto.setPublishTime(date);
		}
//		Long id = fancyMessageDao.save(fancyMessageDto);
		fancyMessageDao.update(fancydto);
		/** 物理删除*/
		/*fancyMessageItemDao.removeByFancyMessageId(fancydto.getId());*/
		List<FancyMessageItem> itemList = fancyMessageDto.getItemList();
		List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
		for (FancyMessageItem item : itemList) {
			AssertUtil.notNull(item.getPicturePath(), "图片不能为空");
			if (null == item.getId()) {
				item.setFancyMessageId(fancydto.getId());
				item.setStatus(1); //有效的
				item.setCreateTime(date);
				item.setUpdateTime(date);
				fancyMessageItemDao.save(item);
			}
			item.setFancyMessageId(fancydto.getId());
			item.setUpdateTime(date);
			if(Integer.valueOf(1).equals(item.getType())){
				item.setLink(PropertiesUtil.getContexrtParam("wgjUrl")+"/message/fancy/details/"+item.getId()+".htm");
			}
			fancyMessageItemDao.update(item);
			
			if(StringUtils.isNotBlank(item.getPicturePath())){
				FileBizParamDto dto = new FileBizParamDto(null,0L,null,"FANCY_MESSAGE_ITEM",item.getId().toString(),"fancyMessageItem",1);
				dto.setImgData(item.getPicturePath());
				list.add(dto);
			}
			if(StringUtils.isNotBlank(item.getContent())){
				FileBizParamDto dto = new FileBizParamDto(null,0L,null,"FANCY_MESSAGE_ITEM",item.getId().toString(),"ubbFile",1);
				dto.setImgData(item.getContent());
				list.add(dto);
			}
		}

		String flag = imageService.addOrEdit(list);
        JSONObject jsonObject = JSONObject.fromObject(flag);
        AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "上传图片失败:"+flag);
        String dataJson = jsonObject.getString("data");
        JSONArray jsonArray = JSONArray.fromObject(dataJson);
        List<FileBizParamDto> fbpDto = jsonArray.toList(jsonArray, FileBizParamDto.class);
		for (FileBizParamDto fd : fbpDto) {
			if(fd.getCode().equals("fancyMessageItem")){
				fd.getTableId();
				FancyMessageItem edit = new FancyMessageItem();
				edit.setId(Long.valueOf(fd.getTableId()));
				edit.setPicturePath(fd.getImgData());
				fancyMessageItemDao.update(edit);
			}
			if(fd.getCode().equals("ubbFile")){
				fd.getTableId();
				FancyMessageItem edit = new FancyMessageItem();
				edit.setId(Long.valueOf(fd.getTableId()));
				edit.setContent(fd.getImgData());
				fancyMessageItemDao.update(edit);
			}
		}
        
		try{
			if (1==fancyMessageDto.getStatus() && !status.equals(1)) {
				choicenessMetaOperateProducer.senderAnsy(fancydto.getId());
			}else if(status.equals(1)){
				_logger.info("精选已推送过,不再推送");
			}
		}catch(Exception e ){
			_logger.error("刷脸精选消息推送失败!", e);
		}
		return true;
		
//		AssertUtil.notNull(fancyMessageDto.getId(), "刷脸精选ID不能为空");
//		FancyMessage dto = fancyMessageDao.selectByPrimaryKey(fancyMessageDto.getId());
//		AssertUtil.notNull(dto, "刷脸精选不存在");
//		AssertUtil.notNull(fancyMessageDto.getItemList(), "刷脸精选内容不能为空");
//		AssertUtil.isTrue(fancyMessageDto.getItemList().size() > 0, "刷脸精选内容不能为空");
//		
//		dto.setName(fancyMessageDto.getName());
//		dto.setType(fancyMessageDto.getType());
//		Date date = new Date();
//		fancyMessageDto.setUpdateTime(date);
//		dto.setStatus(fancyMessageDto.getStatus());
//		if (1 == fancyMessageDto.getStatus()) {
//			dto.setPublishTime(date);
//		}
//		fancyMessageDao.update(dto);
//		/** 物理删除*/
//		fancyMessageItemDao.removeByFancyMessageId(dto.getId());
//		List<FancyMessageItem> itemList = fancyMessageDto.getItemList();
//		for (FancyMessageItem item : itemList) {
//			Long id = item.getId();
//			item.setId(null);
//			item.setFancyMessageId(dto.getId());
//			item.setCreateTime(date);
//			item.setUpdateTime(date);
//			item.setStatus(1);
//			AssertUtil.notNull(item.getPicturePath(), "图片不能为空");
//			fancyMessageItemDao.save(item);
//			String path = _savePicture(item.getPicturePath(),id);
//			FancyMessageItem fmi=new FancyMessageItem();
//			fmi.setId(item.getId());
//			fmi.setPicturePath(path);
//			fmi.setUpdateTime(date);
//			fancyMessageItemDao.update(fmi);
//		}
//		return true;
	}
	@Override
	public Pagination<FancyMessage> findFancyMessagePageList(FancyMessage fancyMessage, Pagination<FancyMessage> pagination) {
		int count=fancyMessageDao.getCount(fancyMessage);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("fancyMessage", fancyMessage);
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		List<FancyMessage> list=fancyMessageDao.findFancyMessagePageList(map);
		
		pagination.setDatas(list);
		pagination.setTotalRow(count);
		return pagination;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation", "static-access" })
	private String _savePicture(String img,Long fancyMessageId) {
		List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
		if (null == img || "".equals(img)){
			return null;
		}
		FileBizParamDto dto = new FileBizParamDto();
		dto.setImgData(img);
		dto.setZoomSizes(null);
		dto.setUserId(0l);
		dto.setTableName("FANCY_MESSAGE_ITEM");
		dto.setTableId(String.valueOf(fancyMessageId));
		dto.setCode("fancyMessageItem");
		dto.setFileLabel(1);
		list.add(dto);
        String flag = imageService.addOrEdit(list);
        JSONObject jsonObject = JSONObject.fromObject(flag);
        AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "上传图片失败:"+flag);
        String dataJson = jsonObject.getString("data");
        JSONArray jsonArray = JSONArray.fromObject(dataJson);
        List<FileBizParamDto> fbpDto = jsonArray.toList(jsonArray, FileBizParamDto.class);
        FileBizParamDto fileBizParamDto = fbpDto.get(0);
        String imgPath = fileBizParamDto.getImgData();
        return imgPath;
	}

	@Override
	public FancyMessageItem getFancyMessageItem(Long id)throws FancyMessageException {
		AssertUtil.notNull(id, "刷脸精选细项ID不能为空");
		FancyMessageItem fancyMessageItem = fancyMessageItemDao.selectByPrimaryKey(id);
		return fancyMessageItem;
	}
}
