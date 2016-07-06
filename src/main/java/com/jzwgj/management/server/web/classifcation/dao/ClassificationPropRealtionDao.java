package com.jzwgj.management.server.web.classifcation.dao;

import java.util.List;

import com.jzwgj.management.component.base.BaseDao;
import com.jzwgj.management.server.web.classifcation.domain.ClassificationPropRealtion;
import com.jzwgj.management.server.web.classifcation.domain.vo.ClassificationPropRealtionVo;

public interface ClassificationPropRealtionDao extends BaseDao<Long,ClassificationPropRealtion> {

	Integer selectCount(ClassificationPropRealtionVo classificationPropRealtionVo);

	List<ClassificationPropRealtionVo> selectPageList(ClassificationPropRealtionVo classificationPropRealtionVo,
			int start, int pageSize);
	
	Integer selectSalesCount(Long classificationId);
	
	void deleteByClassificationId(Long classificationId);

	List<ClassificationPropRealtionVo> selectList(ClassificationPropRealtionVo classificationPropRealtionVo);

	/**
	 * 根据商品类目ID和商品属性ID,删除关联关系
	 * @param classificationId		商品类目ID
	 * @param propId				商品属性ID
	 */
	void deleteByClassificationIdPropId(Long classificationId, Long propId);

	/**
	 * 查询关联关系中最大的sort排序值
	 * @param classificationPropRealtionVo
	 * @return
	 */
	Integer selectMaxSort(ClassificationPropRealtionVo classificationPropRealtionVo);
}
