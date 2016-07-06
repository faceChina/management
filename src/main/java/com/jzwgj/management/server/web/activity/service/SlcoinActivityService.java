package com.jzwgj.management.server.web.activity.service;

import java.util.Date;

import com.jzwgj.management.exception.SlcoinActivityException;
import com.jzwgj.management.server.web.activity.domain.SlcoinActivity;
import com.jzwgj.management.server.web.activity.domain.SlcoinActivityVo;
import com.zjlp.face.util.page.Pagination;


public interface SlcoinActivityService {
	
	Pagination<SlcoinActivityVo> findSlcoinActivityPageList(SlcoinActivityVo slcoinActivityVo, Pagination<SlcoinActivityVo> pagination) throws SlcoinActivityException;

	void insert(SlcoinActivity slcoinActivity) throws SlcoinActivityException;
	
	/**
	 * 查询特定的时间点是否有活动存在
	 * @param beginTime		开始时间
	 * @param endTime		结束时间
	 * @param id			剔除id
	 * @return
	 */
	Boolean getPointInTime(Date beginTime,Date endTime,Long id) throws SlcoinActivityException;
	
	SlcoinActivity selectByPrimaryKey(Long id) throws SlcoinActivityException;
	
	void update(SlcoinActivity slcoinActivity) throws SlcoinActivityException;

	/**
	 * 修改活动时间
	 * @param id		活动ID
	 * @param type		修改类型
	 */
	void updateTime(Long id, Integer type) throws SlcoinActivityException;
}
