package com.jzwgj.management.mapper;

import java.util.List;

import com.jzwgj.management.server.web.fancy.domain.FancyMessageItem;

public interface FancyMessageItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FancyMessageItem record);

    int insertSelective(FancyMessageItem record);

    FancyMessageItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FancyMessageItem record);

    int updateByPrimaryKeyWithBLOBs(FancyMessageItem record);

    int updateByPrimaryKey(FancyMessageItem record);
    
    List<FancyMessageItem> selectByFancyMessageId(Long id);
    
    int deleteByFancyMessageId(Long fancyMessageId);

	void removeByFancyMessageId(Long fancyMessageId);
}