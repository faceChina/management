package com.jzwgj.management.mapper;

import java.util.List;
import java.util.Map;

import com.jzwgj.management.server.web.classifcation.domain.Prop;

public interface PropMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Prop record);

    int insertSelective(Prop record);

    Prop selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Prop record);

    int updateByPrimaryKey(Prop record);

	Integer selectChildCount(Long id);

	List<Prop> selectTreeList(Long node);

	List<Prop> selectAll();

	List<Prop> selectPageList(Map<String, Object> map);

	Integer selectCount(Map<String, Object> map);

	Long getMaxSort();
}