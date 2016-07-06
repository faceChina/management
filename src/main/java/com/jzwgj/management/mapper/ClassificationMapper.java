package com.jzwgj.management.mapper;

import java.util.List;
import java.util.Map;

import com.jzwgj.management.server.web.classifcation.domain.Classification;

public interface ClassificationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Classification record);

    int insertSelective(Classification record);

    Classification selectByPrimaryKey(Long id);
    
    int updateByPrimaryKeySelective(Classification record);

    int updateByPrimaryKey(Classification record);

	List<Classification> selectByPid(Long pid);

	Integer selectChildCount(Long id);

	List<Classification> selectTreeList(Map<String, Object> map);
}