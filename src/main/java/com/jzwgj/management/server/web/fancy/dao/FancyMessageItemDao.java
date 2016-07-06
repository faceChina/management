package com.jzwgj.management.server.web.fancy.dao;

import java.util.List;

import com.jzwgj.management.server.web.fancy.domain.FancyMessageItem;

public interface FancyMessageItemDao {
	
	Long save(FancyMessageItem fancyMessageItem);
	
	List<FancyMessageItem> selectByFancyMessageId(Long fancyMessageId);
	
	void update(FancyMessageItem fancyMessageItem);
	
	/** 逻辑删除*/
	int deleteByFancyMessageId(Long fancyMessageId);
	
	/** 物理删除*/
	void removeByFancyMessageId(Long fancyMessageId);

	FancyMessageItem selectByPrimaryKey(Long id);
}
