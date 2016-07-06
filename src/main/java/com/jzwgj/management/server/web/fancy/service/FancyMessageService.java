package com.jzwgj.management.server.web.fancy.service;

import com.jzwgj.management.exception.FancyMessageException;
import com.jzwgj.management.server.web.fancy.domain.FancyMessage;
import com.jzwgj.management.server.web.fancy.domain.FancyMessageItem;
import com.jzwgj.management.server.web.fancy.domain.dto.FancyMessageDto;
import com.zjlp.face.util.page.Pagination;


public interface FancyMessageService {
	
	/**
	 * 新增刷脸精选
	* @Title: add
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param fancyMessageDto
	* @return
	* @return Long    返回类型
	* @throws
	* @author wxn  
	* @date 2015年7月27日 下午5:28:23
	 */
	Long add(FancyMessageDto fancyMessageDto) throws FancyMessageException;
	/**
	 * 获取刷脸精选
	* @Title: getFancyMessage
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param id
	* @return
	* @return FancyMessageDto    返回类型
	* @throws
	* @author wxn  
	* @date 2015年7月27日 下午5:28:37
	 */
	FancyMessageDto getFancyMessage(Long id) throws FancyMessageException;
	
	/**
	 * 编辑刷脸精选 
	* @Title: edit
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param fancyMessageDto
	* @return
	* @return boolean    返回类型
	* @throws
	* @author wxn  
	* @date 2015年7月27日 下午5:28:50
	 */
	boolean edit(FancyMessageDto fancyMessageDto) throws FancyMessageException;
	
	void deleteById(Long id);

	Pagination<FancyMessage> findFancyMessagePageList(FancyMessage fancyMessage, Pagination<FancyMessage> pagination);
	
	FancyMessageItem getFancyMessageItem(Long id) throws FancyMessageException;

}
