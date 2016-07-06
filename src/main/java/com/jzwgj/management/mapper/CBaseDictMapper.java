package com.jzwgj.management.mapper;

import java.util.List;
import java.util.Map;

import com.jzwgj.management.server.sys.domain.CBaseDict;

public interface CBaseDictMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CBaseDict record);

    int insertSelective(CBaseDict record);

    CBaseDict selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CBaseDict record);

    int updateByPrimaryKey(CBaseDict record);

	List<CBaseDict> selectList(CBaseDict cBaseDict);

	List<CBaseDict> selectPageList(Map<String, Object> map);

	Integer selectCount(CBaseDict cbd);
}