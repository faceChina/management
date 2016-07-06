package com.jzwgj.management.mapper;

import java.util.List;
import java.util.Map;

import com.jzwgj.management.server.web.classifcation.domain.ClassificationPropRealtion;
import com.jzwgj.management.server.web.classifcation.domain.vo.ClassificationPropRealtionVo;

public interface ClassificationPropRealtionMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(ClassificationPropRealtion record);

    int insertSelective(ClassificationPropRealtion record);

    ClassificationPropRealtion selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ClassificationPropRealtion record);

    int updateByPrimaryKey(ClassificationPropRealtion record);

	Integer selectCount(Map<String, Object> map);

	List<ClassificationPropRealtionVo> selectPageList(Map<String, Object> map);
	
	Integer selectSalesCount(Long classificationId);

	void deleteByClassificationId(Long classificationId);

	List<ClassificationPropRealtionVo> selectList(Map<String, Object> map);

	void deleteByClassificationIdPropId(Map<String, Object> map);

	Integer selectMaxSort(Map<String, Object> map);
}