package com.jzwgj.management.server.web.classifcation.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jzwgj.management.constant.Constant;
import com.jzwgj.management.exception.ClassificationException;
import com.jzwgj.management.server.web.classifcation.dao.ClassificationDao;
import com.jzwgj.management.server.web.classifcation.dao.ClassificationPropRealtionDao;
import com.jzwgj.management.server.web.classifcation.domain.Classification;
import com.jzwgj.management.server.web.classifcation.domain.vo.ClassificationPropRealtionVo;
import com.jzwgj.management.server.web.classifcation.service.ClassificationService;
import com.jzwgj.management.vo.ModuleTreeNodeVo;
import com.zjlp.face.util.exception.AssertUtil;
@Service
public class ClassificationServiceImpl implements ClassificationService {

	@Autowired
	private ClassificationDao classificationDao;
	@Autowired
	private ClassificationPropRealtionDao classificationPropRealtionDao;
	
	@Override
	public boolean add(Classification classification) {
		try {
			if (null == classification.getCreateTime()) {
				Date date = new Date();
				classification.setCreateTime(date);
				classification.setUpdateTime(date);
			}
			if(classification.getParentId().equals(0L)){
				classification.setLevel(0);
			}else{
				Classification parentC = classificationDao.selectById(classification.getParentId());
				classification.setLevel(parentC.getLevel()+1);
			}
			classification.setStatus(Constant.CLASSIFIACTION_STATUS_NOT_PUBLISH);
			classificationDao.insertSelective(classification);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<ModuleTreeNodeVo> findTreeList(String node, String category) {
		List<Classification> resultList = classificationDao.selectTreeList(node,category);
		List<ModuleTreeNodeVo> treeList = new ArrayList<ModuleTreeNodeVo>();
		if (null == resultList || resultList.isEmpty()) {
			return treeList;
		}
		ModuleTreeNodeVo vo = null;
		for (Classification classification : resultList) {
			vo = new ModuleTreeNodeVo();
			vo.setId(classification.getId().toString());
			vo.setText(classification.getName());
			vo.setPId(classification.getParentId().toString());
//			Integer isHaveChild = classificationDao.selectChildCount(classification.getId());
			
			Map<String,Object> jsonMap = new HashMap<String,Object>();
			jsonMap.put("status", classification.getStatus());
			jsonMap.put("level", classification.getLevel());
			JSONObject json = JSONObject.fromObject(jsonMap);
			vo.setCustomJson(json.toString());
			
			if (classification.getLeaf() == Constant.IS_NOT_LEAF) {
				vo.setLeaf(false);
			} else {
				vo.setLeaf(true);
				if(Constant.CLASSIFIACTION_STATUS_PUBLISH == classification.getStatus()){
					vo.setText("<font color='#041690'>" + classification.getName() + "(已发布)</font>");
				}else if(Constant.CLASSIFIACTION_STATUS_DELETE == classification.getStatus()){
					vo.setText("<font color='#ff0000'>" + classification.getName() + "(已隐藏)</font>");
				}else{
					vo.setText("<font color='#666666'>" + classification.getName() + "</font>");
				}
			}
			treeList.add(vo);
		}
		return treeList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean editStatus(Long classificationId,Integer status) throws ClassificationException {
		try {
			AssertUtil.notNull(classificationId, "商品类目ID不能为空");
			AssertUtil.notNull(status, "商品类目修改状态不能为空");
			Classification classification = classificationDao.selectById(classificationId);
			AssertUtil.notNull(classification, "商品类目不存在");
			
			//查询商品类目下是否有商品属性关联,无则不允许发布,非叶子节点除外
			if(Constant.IS_LEAF == classification.getLeaf()){
				ClassificationPropRealtionVo vo = new ClassificationPropRealtionVo();
				vo.setClassificationId(classification.getId());
				Integer count = classificationPropRealtionDao.selectCount(vo);
				if(count <= 0){
					throw new ClassificationException("商品类目:<font color='red'>" + classification.getName() + "</font>未添加任何商品属性,不允许发布");
				}
			}
			
			//修改状态
			if(Constant.CLASSIFIACTION_STATUS_PUBLISH == status){
				classification.setStatus(Constant.CLASSIFIACTION_STATUS_PUBLISH);
			}else if(Constant.CLASSIFIACTION_STATUS_DELETE == status){
				classification.setStatus(Constant.CLASSIFIACTION_STATUS_DELETE);
			}else{
				throw new ClassificationException("商品类目:<font color='red'>" + classification.getName() + "</font>修改状态未知,不允许修改");
			}
			classificationDao.updateByPrimaryKeySelective(classification);
			
			//判断是否为叶子节点,若非叶子节点,修改子节点状态
			if(Constant.IS_NOT_LEAF == classification.getLeaf()){
				List<Classification> childList = classificationDao.findClassificationByPid(classification.getId(), 0);
				if(null==childList || 0==childList.size()){
					return true;
				}else{
					for(Classification child : childList){
						this.editStatus(child.getId(),status);
					}
				}
			}else{
				//若是叶子节点,则修改父节点状态为已发布
				if(Constant.CLASSIFIACTION_STATUS_DELETE == status){
					
				}else{
					this.editParentStatus(classification.getParentId(),status);
				}
			}
		} catch (Exception e) {
			throw new ClassificationException("修改状态失败_" + e.getMessage(),e);
		}
		
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean editParentStatus(Long classificationId,Integer status)throws ClassificationException {
		try {
			AssertUtil.notNull(classificationId, "商品类目ID不能为空");
			Classification classification = classificationDao.selectById(classificationId);
			AssertUtil.notNull(classification, "商品类目不存在");
			
			//修改状态
			if(Constant.CLASSIFIACTION_STATUS_PUBLISH == status){
				classification.setStatus(Constant.CLASSIFIACTION_STATUS_PUBLISH);
			}else if(Constant.CLASSIFIACTION_STATUS_DELETE == status){
				classification.setStatus(Constant.CLASSIFIACTION_STATUS_DELETE);
			}else{
				throw new ClassificationException("商品类目:<font color='red'>" + classification.getName() + "</font>修改状态未知,不允许修改");
			}
			
			classificationDao.updateByPrimaryKeySelective(classification);
			
			if(!classification.getParentId().equals(0L)){
				this.editParentStatus(classification.getParentId(),status);
			}
		} catch (Exception e) {
			throw new ClassificationException("修改状态失败_" + e.getMessage(),e);
		}
		
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean delete(Long classificationId)throws ClassificationException {
		try {
			AssertUtil.notNull(classificationId, "商品类目ID不能为空");
			Classification classification = classificationDao.selectById(classificationId);
			AssertUtil.notNull(classification, "商品类目不存在");
			
			//判断是否为叶子节点,若非叶子节点,删除子节点
			if(Constant.IS_NOT_LEAF == classification.getLeaf()){
				List<Classification> childList = classificationDao.findClassificationByPid(classification.getId(), 0);
				if(null==childList || 0==childList.size()){
					//return true;
				}else{
					for(Classification child : childList){
						this.delete(child.getId());
					}
				}
			}else{
				//若是叶子节点,则删除商品类目关联关系
				classificationPropRealtionDao.deleteByClassificationId(classificationId);
			}

			//删除商品类目
			classificationDao.deleteByPrimaryKey(classification.getId());
		} catch (Exception e) {
			throw new ClassificationException("删除失败_" + e.getMessage(),e);
		}
		
		return true;
	}
	
	@Override
	public Classification selectById(Long classificationId)throws ClassificationException {
		try {
			AssertUtil.notNull(classificationId, "商品类目ID不能为空");
			Classification classification = classificationDao.selectById(classificationId);
			AssertUtil.notNull(classification, "商品类目不存在");
			
			return classification;
		} catch (Exception e) {
			throw new ClassificationException(e.getMessage(),e);
		}
	}

	@Override
	public boolean update(Classification classification)throws ClassificationException {
		try {
			AssertUtil.notNull(classification, "商品类目对象为空");
			classification.setUpdateTime(new Date());
			classificationDao.updateByPrimaryKeySelective(classification);
			
			return true;
		} catch (Exception e) {
			throw new ClassificationException(e.getMessage(),e);
		}
	}
}
