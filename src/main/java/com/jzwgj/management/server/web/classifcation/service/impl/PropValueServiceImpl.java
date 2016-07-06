package com.jzwgj.management.server.web.classifcation.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.jzwgj.management.constant.Constant;
import com.jzwgj.management.exception.ClassificationPropRealtionException;
import com.jzwgj.management.exception.PropException;
import com.jzwgj.management.server.web.classifcation.dao.PropValueDao;
import com.jzwgj.management.server.web.classifcation.domain.PropValue;
import com.jzwgj.management.server.web.classifcation.service.PropValueService;
import com.jzwgj.management.util.Pagination;
@Service
public class PropValueServiceImpl implements PropValueService {
	
	@Autowired
	private PropValueDao propValueDao;

	@Override
	public boolean add(PropValue propValue) {
		Assert.notNull(propValue);
		Assert.notNull(propValue.getPropertyId(),"属性不能为空");
		try {
			if (null == propValue.getCreateTime()) {
				Date date = new Date();
				propValue.setCreateTime(date);
				propValue.setUpdateTime(date);
			}
			
			PropValue query = new PropValue();
			query.setPropertyId(propValue.getPropertyId());
			Integer sort = propValueDao.selectMaxSort(query);
			if(sort == null){
				propValue.setSort(0L);
			}else{
				propValue.setSort(sort + 1L);
			}
			propValue.setStatus(1);
			propValueDao.insertSelective(propValue);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Pagination<PropValue> selectPageList(PropValue propValue,
			Pagination<PropValue> pagination) {
		Integer totalRow = propValueDao.selectCount(propValue);
		List<PropValue> detailList = propValueDao.selectPageList(propValue, pagination.getStart(), pagination.getPageSize());
		pagination.setTotalRow(totalRow);
		pagination.setDatas(detailList);
		return pagination;
	}

	@Override
	public Pagination<PropValue> selectPageListFilterClass(PropValue propValue,Pagination<PropValue> pagination,Long classificationId) {
		Integer totalRow = propValueDao.selectCount(propValue);
		List<PropValue> detailList = propValueDao.selectPageListFilterClass(propValue, pagination.getStart(), pagination.getPageSize(),classificationId);
		pagination.setTotalRow(totalRow);
		pagination.setDatas(detailList);
		return pagination;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean deleteByPropIdValueList(Long propId, Long[] ids) {
		try {
			for(Long id : ids){
				propValueDao.deleteByPrimaryKey(id);
			}
		} catch (Exception e) {
			throw new ClassificationPropRealtionException("删除失败_" + e.getMessage(),e);
		}
		return true;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean updateSort(Integer sort, String ids) throws PropException{
		try {
			if(Constant.SORT_TYPE_DOWN==sort || Constant.SORT_TYPE_UP==sort){
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
					throw new PropException("数据出错",e);
				}
				
				if(upIdColl.length == 1){
					Long upId = upIdColl[0];
					PropValue upobj = propValueDao.selectByPrimaryKey(upId);
					PropValue downobj = propValueDao.selectByPrimaryKey(downId);
					if(upobj == null){
						throw new PropException("ID:" + upId + ",没有此关联数据");
					}else if(downobj == null){
						throw new PropException("ID:" + downId + ",没有此关联数据");
					}
					
					Long upSort = downobj.getSort();
					Long downSort = upobj.getSort();
					
					upobj.setSort(upSort);
					downobj.setSort(downSort);
					
					propValueDao.updateByPrimaryKey(upobj);
					propValueDao.updateByPrimaryKey(downobj);
				}else{
//					ClassificationPropRealtion downobj = classificationPropRealtionDao.selectByPrimaryKey(downId);
//					if(downobj == null){
//						throw new ClassificationPropRealtionException("ID:" + downId + ",没有此关联数据");
//					}
//					
//					Prop downProp = propDao.selectByPrimaryKey(downobj.getPropId());
//					if(downProp == null){
//						throw new ClassificationPropRealtionException("ID:" + downId + ",没有此关联的商品属性数据");
//					}
//					
//					if(downProp.getIsColorProp()){
//						throw new ClassificationPropRealtionException("颜色属性排序必须为第一位");
//					}
//					
////					List<ClassificationPropRealtion> updateSorts = new ArrayList<ClassificationPropRealtion>();
//					for(int i=0;i<upIdColl.length;i++){
//						Long upId = upIdColl[i];
//						ClassificationPropRealtion upobj = classificationPropRealtionDao.selectByPrimaryKey(upId);
//						if(upobj == null){
//							throw new ClassificationPropRealtionException("ID:" + upId + ",没有此关联数据");
//						}
//						
//						Prop upProp = propDao.selectByPrimaryKey(upobj.getPropId());
//						
//						if(upProp == null){
//							throw new ClassificationPropRealtionException("ID:" + upId + ",没有此关联的商品属性数据");
//						}
//						
//						if(upProp.getIsColorProp()){
//							throw new ClassificationPropRealtionException("颜色属性排序必须为第一位");
//						}
//						
//						//Integer upSort = downobj.getSort();
//						if(i == 0){
//							Integer downSort = upobj.getSort();
//							downobj.setSort(downSort);
//							classificationPropRealtionDao.updateByPrimaryKey(downobj);
//						}
//						
//						upobj.setSort(upobj.getSort() + sort);
//						classificationPropRealtionDao.updateByPrimaryKey(upobj);
//					}
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
