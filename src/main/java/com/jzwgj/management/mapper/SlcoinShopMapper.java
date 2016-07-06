package com.jzwgj.management.mapper;

import java.util.List;
import java.util.Map;

import com.jzwgj.management.server.web.activity.domain.SlcoinShop;
import com.jzwgj.management.server.web.activity.domain.SlcoinShopVo;

public interface SlcoinShopMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SlcoinShop record);

    int insertSelective(SlcoinShop record);

    SlcoinShop selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SlcoinShop record);

    int updateByPrimaryKey(SlcoinShop record);

	Integer getCount(Map<String, Object> map);

	List<SlcoinShopVo> findSlcoinShopByPageList(Map<String, Object> map);
}