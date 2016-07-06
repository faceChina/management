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
import org.springframework.util.Assert;

import com.jzwgj.management.constant.Constant;
import com.jzwgj.management.exception.PropException;
import com.jzwgj.management.server.web.classifcation.dao.PropDao;
import com.jzwgj.management.server.web.classifcation.dao.PropValueDao;
import com.jzwgj.management.server.web.classifcation.domain.Prop;
import com.jzwgj.management.server.web.classifcation.domain.PropValue;
import com.jzwgj.management.server.web.classifcation.service.PropService;
import com.jzwgj.management.util.Pagination;
import com.jzwgj.management.vo.ModuleTreeNodeVo;
import com.zjlp.face.util.exception.AssertUtil;
@Service
public class PropServiceImpl implements PropService {
	
	@Autowired
	private PropDao propDao;
	@Autowired
	private PropValueDao propValueDao;
	
	@Override
	public List<ModuleTreeNodeVo> list() {
		List<Prop> propList = propDao.findAll();
		if (null == propList || propList.isEmpty()) {
			return null;
		}
		List<ModuleTreeNodeVo> voList = new ArrayList<ModuleTreeNodeVo>();
		for (Prop prop : propList) {
			ModuleTreeNodeVo vo = new ModuleTreeNodeVo();
			vo.setId(String.valueOf(prop.getId()));
			vo.setText(prop.getName());
			vo.setLeaf(true);
			vo.setCustomJson(String.valueOf(prop.getIsColorProp()));
			voList.add(vo);
		}
		return voList;
	}

	@Override
	public boolean add(Prop prop) {
		try {
			if (null == prop.getCreateTime()) {
				Date date = new Date();
				prop.setCreateTime(date);
				prop.setUpdateTime(date);
			}
			Long sort = propDao.getMaxSort();
			prop.setSort(sort+1);
			prop.setStatus(Constant.STATUS_NOT_PUBLISH);
			//标准属性默认必须为true
//			prop.setIsStandard(true);
//			prop.setIsAllowAlias(true);
//			prop.setIsEnumProp(true);
//			prop.setIsInputProp(false);
//			prop.setIsKeyProp(true);
//			prop.setIsMulti(true);
//			prop.setIsRequired(true);
//			prop.setIsSalesProp(true);
//			prop.setIsStandard(true);
			
			propDao.insertSelective(prop);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<ModuleTreeNodeVo> findTreeList(String node) {
		Assert.hasLength(node, "节点ID不能为空");
		List<Prop> resultList = propDao.selectTreeList(node);
		List<ModuleTreeNodeVo> treeList = new ArrayList<ModuleTreeNodeVo>();
		if (null == resultList || resultList.isEmpty()) {
			return treeList;
		}
		ModuleTreeNodeVo vo = null;
		for (Prop prop : resultList) {
			vo = new ModuleTreeNodeVo();
			vo.setId(prop.getId().toString());
			vo.setText(prop.getName());
			vo.setPId(prop.getPid().toString());
			
			Map<String,Object> jsonMap = new HashMap<String,Object>();
			jsonMap.put("status", prop.getStatus());
			jsonMap.put("isColor", prop.getIsColorProp());
			JSONObject json = JSONObject.fromObject(jsonMap);
			vo.setCustomJson(json.toString());
//			vo.setCustomJson(String.valueOf(prop.getIsColorProp()));
			
			Integer isHaveChild = propDao.selectChildCount(prop.getId());
			if (isHaveChild > 0) {
				vo.setLeaf(false);
			} else {
				vo.setLeaf(true);
				
				if(Constant.STATUS_PUBLISH == prop.getStatus()){
					vo.setText("<font color='#041690'>" + prop.getName() + "(已发布)</font>");
				}else if(Constant.STATUS_DELETE == prop.getStatus()){
					vo.setText("<font color='#ff0000'>" + prop.getName() + "(已隐藏)</font>");
				}else{
					vo.setText("<font color='#666666'>" + prop.getName() + "</font>");
				}
			}
			treeList.add(vo);
		}
		return treeList;
	}

	@Override
	public Pagination<Prop> selectPageList(Prop prop,Pagination<Prop> pagination) {
		Integer totalRow = propDao.selectCount(prop);
		List<Prop> detailList = propDao.selectPageList(prop, pagination.getStart(), pagination.getPageSize());
		pagination.setTotalRow(totalRow);
		pagination.setDatas(detailList);
		return pagination;
	}


	/**
	 * 查询品类属性,过滤已添加属性
	 * @param request
	 * @param prop					查询条件
	 * @param pagination			分页对象
	 * @param classificationId		需过滤的品类已添加属性
	 * @return
	 */
	@Override
	public Pagination<Prop> selectPageListFilterClass(Prop prop,Pagination<Prop> pagination, Long classificationId) {
		Integer totalRow = propDao.selectCountFilterClass(prop,classificationId);
		List<Prop> detailList = propDao.selectPageListFilterClass(prop, pagination.getStart(), pagination.getPageSize(),classificationId);
		pagination.setTotalRow(totalRow);
		pagination.setDatas(detailList);
		return pagination;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean editStatus(Long propId,Integer status) throws PropException {
		try {
			AssertUtil.notNull(propId, "类目属性ID不能为空");
			Prop prop = propDao.selectByPrimaryKey(propId);
			AssertUtil.notNull(prop, "类目属性不存在");
			
			//查询商品属性下是否有商品属性值关联,无则不允许发布,非叶子节点除外
			PropValue propValue = new PropValue();
			propValue.setPropertyId(propId);
			Integer count = propValueDao.selectCount(propValue);
			if(count <= 0){
				throw new PropException("商品属性:<font color='red'>" + prop.getName() + "</font>未添加任何商品属性值,不允许发布");
			}
			
			//修改状态为已发布
			prop.setStatus(status);
			propDao.updateByPrimaryKeySelective(prop);
			
			//判断是否为叶子节点,若非叶子节点,修改子节点状态
			boolean isHaveChildFlag = true;
			Integer isHaveChild = propDao.selectChildCount(prop.getId());
			if (isHaveChild > 0) {
				isHaveChildFlag = false;
			}
			
			if(!isHaveChildFlag){
				List<Prop> childList = propDao.selectTreeList(prop.getId().toString());
				if(null==childList || 0==childList.size()){
					return true;
				}else{
					for(Prop child : childList){
						this.editStatus(child.getId(),status);
					}
				}
			}else{
				//若是叶子节点,则修改父节点状态为已发布
				if(!prop.getPid().equals(0L)){
					this.editParentStatus(prop.getPid(),status);
				}
			}
		} catch (Exception e) {
			throw new PropException("修改状态失败_" + e.getMessage(),e);
		}
		
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean editParentStatus(Long propId,Integer status)throws PropException {
		try {
			AssertUtil.notNull(propId, "类目属性ID不能为空");
			Prop prop = propDao.selectByPrimaryKey(propId);
			AssertUtil.notNull(prop, "类目属性不存在");
			
			//修改状态为已发布
			prop.setStatus(status);
			propDao.updateByPrimaryKeySelective(prop);
			
			if(!prop.getPid().equals(0L)){
				this.editParentStatus(prop.getPid(),status);
			}
		} catch (Exception e) {
			throw new PropException("修改状态失败_" + e.getMessage(),e);
		}
		
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean delete(Long propId)throws PropException {
		try {
			AssertUtil.notNull(propId, "类目属性ID不能为空");
			Prop prop = propDao.selectByPrimaryKey(propId);
			AssertUtil.notNull(prop, "类目属性不存在");
			
			//判断是否为叶子节点,若非叶子节点,修改子节点状态
			boolean isHaveChildFlag = true;
			Integer isHaveChild = propDao.selectChildCount(prop.getId());
			if (isHaveChild > 0) {
				isHaveChildFlag = false;
			}
			
			if(!isHaveChildFlag){
				List<Prop> childList = propDao.selectTreeList(prop.getId().toString());
				if(null==childList || 0==childList.size()){
					return true;
				}else{
					for(Prop child : childList){
						this.delete(child.getId());
					}
				}
			}else{
				//若是叶子节点,则删除类目属性值
				propValueDao.deleteByPropId(prop.getId());
			}

			//删除类目属性
			propDao.deleteByPrimaryKey(prop.getId());
		} catch (Exception e) {
			throw new PropException("删除失败_" + e.getMessage(),e);
		}
		
		return true;
	}

	@Override
	public Prop selectByPrimaryKey(Long id)throws PropException {
		try {
			AssertUtil.notNull(id, "类目属性ID不能为空");
			Prop prop = propDao.selectByPrimaryKey(id);
			return prop;
		}  catch (Exception e) {
			throw new PropException("查询失败_" + e.getMessage(),e);
		}
	}

	@Override
	public boolean update(Prop prop)throws PropException {
		try {
			AssertUtil.notNull(prop, "商品属性对象为空");
			prop.setUpdateTime(new Date());
			propDao.updateByPrimaryKeySelective(prop);
			
			return true;
		} catch (Exception e) {
			throw new PropException("修改失败_" + e.getMessage(),e);
		}
	}
}
