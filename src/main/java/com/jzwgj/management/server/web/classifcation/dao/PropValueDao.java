package com.jzwgj.management.server.web.classifcation.dao;

import java.util.List;

import com.jzwgj.management.component.base.BaseDao;
import com.jzwgj.management.server.web.classifcation.domain.PropValue;


public interface PropValueDao extends BaseDao<Long,PropValue> {

	Integer selectCount(PropValue propValue);

	List<PropValue> selectPageList(PropValue propValue, int start, int pageSize);
	
	List<PropValue> selectPageListFilterClass(PropValue propValue, int start, int pageSize,Long classificationId);

	/**
	 * 根据类目属性ID,删除类目属性值
	 * @param propId	类目属性ID
	 */
	void deleteByPropId(Long propId);

	Integer selectMaxSort(PropValue query);
}
