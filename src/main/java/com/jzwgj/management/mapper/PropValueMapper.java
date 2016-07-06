package com.jzwgj.management.mapper;

import java.util.List;
import java.util.Map;

import com.jzwgj.management.server.web.classifcation.domain.PropValue;

public interface PropValueMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PropValue record);

    int insertSelective(PropValue record);

    PropValue selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PropValue record);

    int updateByPrimaryKey(PropValue record);

	List<PropValue> selectPropValuesByPropId(Long propId);

	Integer selectCount(Map<String, Object> map);

	List<PropValue> selectPageList(Map<String, Object> map);

	void deleteByPropId(Long propId);

	Integer selectMaxSort(Map<String, Object> map);
}