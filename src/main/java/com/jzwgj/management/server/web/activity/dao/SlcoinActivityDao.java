package com.jzwgj.management.server.web.activity.dao;

import java.util.List;
import java.util.Map;

import com.jzwgj.management.server.web.activity.domain.SlcoinActivity;
import com.jzwgj.management.server.web.activity.domain.SlcoinActivityVo;

public interface SlcoinActivityDao {
	
	Integer getCount();
	
	List<SlcoinActivityVo> findSlcoinActivityPageList(Map<String,Object> map);
	
	void insert(SlcoinActivity slcoinActivity);
	
	/**
	 * 查询特定的时间点是否有活动存在
	 * @param map
	 * @return
	 */
	Integer getPointInTime(Map<String,Object> map);
	
	void update(SlcoinActivity slcoinActivity);
	
	SlcoinActivity selectByPrimaryKey(Long id);
}
