package com.jzwgj.management.server.web.classifcation.dao;

import java.util.List;

import com.jzwgj.management.component.base.BaseDao;
import com.jzwgj.management.server.web.classifcation.domain.Classification;

/**
 * 商品类目持久层接口
 * @ClassName: ClassificationDao
 * @Description: (这里用一句话描述这个类的作用)
 * @author Administrator
 * @date 2015年3月11日 下午1:50:49
 */
public interface ClassificationDao extends BaseDao<Long,Classification> {

	List<Classification> findClassificationByPid(Long pid,Integer level);

	Integer selectChildCount(Long id);

	List<Classification> selectTreeList(String parentId, String code);
	
	Classification selectById(Long id);
	
	/**
	 * 发布商品类目
	 * @param classification	商品类目ID
	 */
	void publish(Classification classification);
}
