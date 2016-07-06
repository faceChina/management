package com.jzwgj.management.server.web.activity.dao;

import java.util.List;
import java.util.Map;

import com.jzwgj.management.server.web.activity.domain.SlcoinShopVo;

public interface SlcoinShopDao {
	
	Integer getCount(Map<String,Object> map);
	
	List<SlcoinShopVo> findSlcoinShopByPageList(Map<String,Object> map);
}
