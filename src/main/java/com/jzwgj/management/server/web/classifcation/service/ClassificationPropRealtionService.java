package com.jzwgj.management.server.web.classifcation.service;

import com.jzwgj.management.exception.ClassificationPropRealtionException;
import com.jzwgj.management.server.web.classifcation.domain.ClassificationPropRealtion;
import com.jzwgj.management.server.web.classifcation.domain.vo.ClassificationPropRealtionVo;
import com.jzwgj.management.util.Pagination;

public interface ClassificationPropRealtionService {
	
	boolean add(ClassificationPropRealtion classificationPropRealtion) throws ClassificationPropRealtionException;
	
	Pagination<ClassificationPropRealtionVo> selectPageList(ClassificationPropRealtionVo classificationPropRealtionVo,
			Pagination<ClassificationPropRealtionVo> pagination);
	
	boolean addList(Long classificationId,Long[] ids);

	/**
	 * 批量删除商品类目和商品属性关联关系
	 * @param classificationId		商品类目ID
	 * @param ids					需删除的商品属性ID集合
	 * @return
	 */
	boolean deleteRealtionList(Long classificationId, Long[] ids)throws ClassificationPropRealtionException;

	/**
	 * 修改商品类目和商品属性关联关系排序
	 * @param sort		排序类型
	 * @param ids		商品关联ID集合
	 * @return
	 */
	boolean updateRealtionSort(Integer sort, String ids);
}
