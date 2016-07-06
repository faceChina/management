package com.jzwgj.management.server.web.classifcation.service;

import com.jzwgj.management.server.web.classifcation.domain.PropValue;
import com.jzwgj.management.util.Pagination;

public interface PropValueService {

	boolean add(PropValue propValue);
	
	Pagination<PropValue> selectPageList(PropValue propValue,Pagination<PropValue> pagination);
	
	Pagination<PropValue> selectPageListFilterClass(PropValue propValue,Pagination<PropValue> pagination,Long classificationId);

	/**
	 * 根据商品属性Id和商品属性值Id删除商品属性值
	 * @param propId	商品属性Id
	 * @param ids		商品属性值Id集合
	 * @return
	 */
	boolean deleteByPropIdValueList(Long propId, Long[] ids);

	boolean updateSort(Integer sort, String ids);
}
