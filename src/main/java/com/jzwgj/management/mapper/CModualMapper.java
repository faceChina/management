	package com.jzwgj.management.mapper;

import java.util.List;
import java.util.Map;

import com.jzwgj.management.server.sys.domain.CModual;

public interface CModualMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(CModual record);

    int insertSelective(CModual record);

    CModual selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CModual record);

    int updateByPrimaryKey(CModual record);
    
    List<CModual> selectList(CModual record);

	List<CModual> selectPageList(Map<String, Object> map);

	Integer selectChildCount(Long id);
    
}