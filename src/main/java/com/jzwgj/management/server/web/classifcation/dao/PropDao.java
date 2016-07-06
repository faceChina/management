package com.jzwgj.management.server.web.classifcation.dao;

import java.util.List;

import com.jzwgj.management.component.base.BaseDao;
import com.jzwgj.management.server.web.classifcation.domain.Prop;
/**
 * 商品规格属性持久层
 * @ClassName: PropertyDefinitionDao 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author Administrator
 * @date 2015年3月11日 下午2:02:24
 */
public interface PropDao extends BaseDao<Long,Prop> {

	List<Prop> selectTreeList(String node);

	Integer selectChildCount(Long id);
	
	Long getMaxSort();

	List<Prop> findAll();
	List<Prop> selectPageList(Prop prop, int start, int pageSize);

	Integer selectCount(Prop prop);

	List<Prop> selectPageListFilterClass(Prop prop, int start, int pageSize,Long classificationId);

	Integer selectCountFilterClass(Prop prop,Long classificationId);
	
}
