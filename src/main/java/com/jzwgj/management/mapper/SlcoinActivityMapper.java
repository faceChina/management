package com.jzwgj.management.mapper;

import java.util.List;
import java.util.Map;

import com.jzwgj.management.server.web.activity.domain.SlcoinActivity;
import com.jzwgj.management.server.web.activity.domain.SlcoinActivityVo;

public interface SlcoinActivityMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(SlcoinActivity record);

    int insertSelective(SlcoinActivity record);

    SlcoinActivity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SlcoinActivity record);

    int updateByPrimaryKey(SlcoinActivity record);

	Integer getCount();

	List<SlcoinActivityVo> findSlcoinActivityPageList(Map<String, Object> map);

	Integer getPointInTime(Map<String, Object> map);
}