package com.jzwgj.management.server.web.classifcation.service;

import java.util.List;

import com.jzwgj.management.exception.ClassificationException;
import com.jzwgj.management.server.web.classifcation.domain.Classification;
import com.jzwgj.management.vo.ModuleTreeNodeVo;

public interface ClassificationService {

	boolean add(Classification classification);
	
	List<ModuleTreeNodeVo> findTreeList(String node, String code);

	/**
	 * 修改类目状态,若此类目非叶子节点,则修改其下的所有子节点
	 * @param classificationId		类目ID
	 * @param status				需修改为什么状态
	 * @return
	 * @throws Exception 
	 */
	boolean editStatus(Long classificationId,Integer status) throws ClassificationException;

	/**
	 * 修改商品类目父节点状态
	 * @param classificationId		商品类目ID
	 * @param status				需修改为什么状态
	 * @return
	 * @throws ClassificationException
	 */
	boolean editParentStatus(Long classificationId,Integer status)throws ClassificationException;

	/**
	 * 根据ID删除商品类目
	 * @param classificationId		商品类目ID
	 * @return
	 * @throws ClassificationException
	 */
	boolean delete(Long classificationId)throws ClassificationException;

	/**
	 * 根据ID查询商品类目
	 * @param classificationId		商品类目ID
	 * @return
	 * @throws ClassificationException
	 */
	Classification selectById(Long classificationId)throws ClassificationException;

	/**
	 * 修改商品类目
	 * @param classification		商品类目对象
	 * @return
	 * @throws ClassificationException
	 */
	boolean update(Classification classification)throws ClassificationException;
}
