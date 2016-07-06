package com.jzwgj.management.mapper;

import java.util.List;
import java.util.Map;

import com.jzwgj.management.server.web.fancy.domain.FancyMessage;

public interface FancyMessageMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(FancyMessage record);

    int insertSelective(FancyMessage record);

    FancyMessage selectByPrimaryKey(Long id);
    
    int updateByPrimaryKeySelective(FancyMessage record);

    int updateByPrimaryKey(FancyMessage record);

	int getCount(FancyMessage fancyMessage);

	List<FancyMessage> findFancyMessagePageList(Map<String, Object> map);
}