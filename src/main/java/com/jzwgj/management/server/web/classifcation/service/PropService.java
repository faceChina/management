package com.jzwgj.management.server.web.classifcation.service;

import java.util.List;

import com.jzwgj.management.exception.PropException;
import com.jzwgj.management.server.web.classifcation.domain.Prop;
import com.jzwgj.management.util.Pagination;
import com.jzwgj.management.vo.ModuleTreeNodeVo;

public interface PropService {

	List<ModuleTreeNodeVo> list();

	boolean add(Prop prop);
	
	List<ModuleTreeNodeVo> findTreeList(String node);

	Pagination<Prop> selectPageList(Prop prop, Pagination<Prop> pagination);
	
	Pagination<Prop> selectPageListFilterClass(Prop prop, Pagination<Prop> pagination, Long classificationId);

	/**
	 * 发布类目属性,若此属性非叶子节点,则其下的所有子节点都为发布状态
	 * @param propId
	 * @param status
	 * @return
	 */
	boolean editStatus(Long propId,Integer status)throws PropException;

	/**
	 * 修改类目属性父节点为发布状态
	 * @param propId		类目属性ID
	 * @param status
	 * @return
	 * @throws PropException
	 */
	boolean editParentStatus(Long propId,Integer status)throws PropException;

	/**
	 * 根据ID删除类目属性
	 * @param propId		类目属性ID
	 * @return
	 * @throws PropException
	 */
	boolean delete(Long propId)throws PropException;

	/**
	 * 根据ID查询商品属性
	 * @param id	商品属性ID
	 * @return
	 */
	Prop selectByPrimaryKey(Long id);

	/**
	 * 修改商品属性
	 * @param prop		商品属性对象
	 * @return
	 */
	boolean update(Prop prop);
}
