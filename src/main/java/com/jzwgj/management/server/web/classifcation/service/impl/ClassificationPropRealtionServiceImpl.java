package com.jzwgj.management.server.web.classifcation.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jzwgj.management.constant.Constant;
import com.jzwgj.management.exception.ClassificationPropRealtionException;
import com.jzwgj.management.server.web.classifcation.dao.ClassificationPropRealtionDao;
import com.jzwgj.management.server.web.classifcation.dao.PropDao;
import com.jzwgj.management.server.web.classifcation.domain.ClassificationPropRealtion;
import com.jzwgj.management.server.web.classifcation.domain.Prop;
import com.jzwgj.management.server.web.classifcation.domain.vo.ClassificationPropRealtionVo;
import com.jzwgj.management.server.web.classifcation.service.ClassificationPropRealtionService;
import com.jzwgj.management.util.Pagination;

@Service
public class ClassificationPropRealtionServiceImpl implements ClassificationPropRealtionService{

	@Autowired
	private ClassificationPropRealtionDao classificationPropRealtionDao;
	@Autowired
	private PropDao propDao;
	
	@Override
	public boolean add(ClassificationPropRealtion classificationPropRealtion) throws ClassificationPropRealtionException{
		try {
			if (null == classificationPropRealtion.getCreateTime()) {
				classificationPropRealtion.setCreateTime(new Date());
			}
			
			Long classificationId = classificationPropRealtion.getClassificationId();
			Long propId = classificationPropRealtion.getPropId();
			
			if(null==classificationId || null==propId){
				throw new ClassificationPropRealtionException("商品类目ID或商品属性ID为空");
			}else{
				Prop prop = propDao.selectByPrimaryKey(propId);
				if(null == prop){
					throw new ClassificationPropRealtionException("商品属性不存在");
				}else{
					if(prop.getIsColorProp()){
						ClassificationPropRealtionVo classificationPropRealtionVo = new ClassificationPropRealtionVo();
						classificationPropRealtionVo.setClassificationId(classificationId);
						List<ClassificationPropRealtionVo> list = classificationPropRealtionDao.selectList(classificationPropRealtionVo);
						if(null!=list && !list.isEmpty()){
							for(ClassificationPropRealtionVo vo : list){
								if(vo.getIsColorProp()){
									throw new ClassificationPropRealtionException("商品属性中只能有一个颜色属性");
								}
							}
						}
						
						//颜色属性必须为第一位
						classificationPropRealtion.setSort(0);
						for(ClassificationPropRealtionVo vo : list){
							ClassificationPropRealtion updateModel = new ClassificationPropRealtion();
							updateModel.setId(vo.getId());
							updateModel.setSort(vo.getSort()+1);
							classificationPropRealtionDao.updateByPrimaryKey(updateModel);
						}
					}else{
						//查询此商品类型下有多少商品属性关联,排序赋值
						ClassificationPropRealtionVo querySortVo = new ClassificationPropRealtionVo();
						querySortVo.setClassificationId(classificationId);
						//非颜色属性,排序+1
						Integer sort = classificationPropRealtionDao.selectMaxSort(querySortVo);
						if(sort == null){
							sort = 0;
						}
						classificationPropRealtion.setSort(sort+1);
					}
				}
			}
			
			classificationPropRealtionDao.insert(classificationPropRealtion);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ClassificationPropRealtionException(e.getMessage(),e);
		}
	}

	@Override
	public Pagination<ClassificationPropRealtionVo> selectPageList(
			ClassificationPropRealtionVo classificationPropRealtionVo,
			Pagination<ClassificationPropRealtionVo> pagination) {
		Integer totalRow = classificationPropRealtionDao.selectCount(classificationPropRealtionVo);
		List<ClassificationPropRealtionVo> detailList = classificationPropRealtionDao.selectPageList(classificationPropRealtionVo, 
				pagination.getStart(), pagination.getPageSize());
		pagination.setTotalRow(totalRow);
		pagination.setDatas(detailList);
		return pagination;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean addList(Long classificationId,Long[] ids) throws ClassificationPropRealtionException{
		//销售属性数量
		Integer count = null;
		
		ClassificationPropRealtion classificationPropRealtion = new ClassificationPropRealtion();
		classificationPropRealtion.setClassificationId(classificationId);
		
		for(Long propId : ids){
			try {
				Prop prop = propDao.selectByPrimaryKey(propId);
				if(prop.getIsSalesProp()){
					if(count == null){
						//查询销售属性数量
						count = classificationPropRealtionDao.selectSalesCount(classificationId);
					}
					count++;
					//销售属性数量不可多于3个
					if(count > 3){
						throw new ClassificationPropRealtionException("销售属性数量不可超过3个");
					}
				}

				classificationPropRealtion.setPropId(propId);
				add(classificationPropRealtion);
			} catch (Exception e) {
				throw new ClassificationPropRealtionException(e.getMessage(),e);
			}
		}
		return true;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean deleteRealtionList(Long classificationId, Long[] ids) throws ClassificationPropRealtionException{
		try {
			for(Long id : ids){
				classificationPropRealtionDao.deleteByClassificationIdPropId(classificationId,id);
				
				
			}
		} catch (Exception e) {
			throw new ClassificationPropRealtionException("删除失败_" + e.getMessage(),e);
		}
		return true;
	}

//	@Override
//	@Transactional(propagation=Propagation.REQUIRED)
//	public boolean updateRealtionSort(Integer sort, Long[] ids) throws ClassificationPropRealtionException{
//		try {
//			if(Constant.CLASSIFIACTION_PROP_REALTION_SORT_DOWN==sort || Constant.CLASSIFIACTION_PROP_REALTION_SORT_UP==sort){
//				for(Long id : ids){
//					ClassificationPropRealtion obj = classificationPropRealtionDao.selectByPrimaryKey(id);
//					if(obj == null){
//						throw new ClassificationPropRealtionException("ID:" + id + ",没有此关联数据");
//					}
//					
//					Prop prop = propDao.selectByPrimaryKey(obj.getPropId());
//					if(prop == null){
//						throw new ClassificationPropRealtionException("ID:" + id + ",没有此关联的商品属性数据");
//					}
//					
//					if(prop.getIsColorProp()){
//						throw new ClassificationPropRealtionException("颜色属性排序必须为第一位");
//					}
//					
//					obj.setSort(sort + obj.getSort());
//					classificationPropRealtionDao.updateByPrimaryKey(obj);
//					
//
//					ClassificationPropRealtionVo querySortVo = new ClassificationPropRealtionVo();
//					querySortVo.setClassificationId(obj.getClassificationId());
//					querySortVo.setId(obj.getId());
//					Integer maxSort = classificationPropRealtionDao.selectMaxSort(querySortVo);
//				}
//			}else{
//				throw new ClassificationPropRealtionException("排序类型未知");
//			}
//		} catch (Exception e) {
//			throw new ClassificationPropRealtionException("排序失败_" + e.getMessage(),e);
//		}
//		return true;
//	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean updateRealtionSort(Integer sort, String ids) throws ClassificationPropRealtionException{
		try {
			if(Constant.SORT_TYPE_DOWN.equals(sort) || Constant.SORT_TYPE_UP.equals(sort)){
				Long downId = null;
				Long[] upIdColl = null;
				
				try {
					String upIds = ids.split("-")[0];
					String[] upIdList = upIds.split(",");
					upIdColl = new Long[upIdList.length];
					for(int i=0;i<upIdList.length;i++){
						Long upId = Long.parseLong(upIdList[i]);
						upIdColl[i] = upId;
					}
					
					downId = Long.parseLong(ids.split("-")[1]);
				} catch (Exception e) {
					throw new ClassificationPropRealtionException("数据出错",e);
				}
				
				if(upIdColl.length == 1){
					Long upId = upIdColl[0];
					ClassificationPropRealtion upobj = classificationPropRealtionDao.selectByPrimaryKey(upId);
					ClassificationPropRealtion downobj = classificationPropRealtionDao.selectByPrimaryKey(downId);
					if(upobj == null){
						throw new ClassificationPropRealtionException("ID:" + upId + ",没有此关联数据");
					}else if(downobj == null){
						throw new ClassificationPropRealtionException("ID:" + downId + ",没有此关联数据");
					}
					
					Prop upProp = propDao.selectByPrimaryKey(upobj.getPropId());
					Prop downProp = propDao.selectByPrimaryKey(downobj.getPropId());
					if(upProp == null){
						throw new ClassificationPropRealtionException("ID:" + upId + ",没有此关联的商品属性数据");
					}else if(downProp == null){
						throw new ClassificationPropRealtionException("ID:" + downId + ",没有此关联的商品属性数据");
					}
					
					if(upProp.getIsColorProp() || downProp.getIsColorProp()){
						throw new ClassificationPropRealtionException("颜色属性排序必须为第一位");
					}
					
					Integer upSort = downobj.getSort();
					Integer downSort = upobj.getSort();
					
					upobj.setSort(upSort);
					downobj.setSort(downSort);
					
					classificationPropRealtionDao.updateByPrimaryKey(upobj);
					classificationPropRealtionDao.updateByPrimaryKey(downobj);
				}else{
					ClassificationPropRealtion downobj = classificationPropRealtionDao.selectByPrimaryKey(downId);
					if(downobj == null){
						throw new ClassificationPropRealtionException("ID:" + downId + ",没有此关联数据");
					}
					
					Prop downProp = propDao.selectByPrimaryKey(downobj.getPropId());
					if(downProp == null){
						throw new ClassificationPropRealtionException("ID:" + downId + ",没有此关联的商品属性数据");
					}
					
					if(downProp.getIsColorProp()){
						throw new ClassificationPropRealtionException("颜色属性排序必须为第一位");
					}
					
//					List<ClassificationPropRealtion> updateSorts = new ArrayList<ClassificationPropRealtion>();
					for(int i=0;i<upIdColl.length;i++){
						Long upId = upIdColl[i];
						ClassificationPropRealtion upobj = classificationPropRealtionDao.selectByPrimaryKey(upId);
						if(upobj == null){
							throw new ClassificationPropRealtionException("ID:" + upId + ",没有此关联数据");
						}
						
						Prop upProp = propDao.selectByPrimaryKey(upobj.getPropId());
						
						if(upProp == null){
							throw new ClassificationPropRealtionException("ID:" + upId + ",没有此关联的商品属性数据");
						}
						
						if(upProp.getIsColorProp()){
							throw new ClassificationPropRealtionException("颜色属性排序必须为第一位");
						}
						
						//Integer upSort = downobj.getSort();
						if(i == 0){
							Integer downSort = upobj.getSort();
							downobj.setSort(downSort);
							classificationPropRealtionDao.updateByPrimaryKey(downobj);
						}
						
						upobj.setSort(upobj.getSort() + sort);
						classificationPropRealtionDao.updateByPrimaryKey(upobj);
					}
				}
			}else{
				throw new ClassificationPropRealtionException("排序类型未知");
			}
		} catch (Exception e) {
			throw new ClassificationPropRealtionException("排序失败_" + e.getMessage(),e);
		}
		return true;
	}
}
