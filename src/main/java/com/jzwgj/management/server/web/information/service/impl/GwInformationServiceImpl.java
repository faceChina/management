package com.jzwgj.management.server.web.information.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jzwgj.management.constant.Constant;
import com.jzwgj.management.exception.GwInformationException;
import com.jzwgj.management.server.web.information.dao.GwInformationDao;
import com.jzwgj.management.server.web.information.domain.GwInformation;
import com.jzwgj.management.server.web.information.service.GwInformationService;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Pagination;

@Service("gwInformationService")
public class GwInformationServiceImpl implements GwInformationService {
	
	Logger _logger = Logger.getLogger(getClass());
	@Autowired
	private GwInformationDao gwInformationDao;
	
	@Override
	@Transactional
	public void deleteById(Long[] ids) throws GwInformationException {
		try {
			for(Long id : ids){
				GwInformation gwInformation = gwInformationDao.getByPrimaryKey(id);
				AssertUtil.notNull(gwInformation, "资讯不存在");
				gwInformationDao.deleteById(id);
			}
		} catch (RuntimeException e) {
			_logger.error("删除出错:" + e.getMessage(), e);
			throw new GwInformationException(e.getMessage(),e);
		}
	}
	
	@Override
	public Long add(GwInformation gwInformation) throws GwInformationException {
		try {
			Date date = new Date();
			gwInformation.setCreateTime(date);
			gwInformation.setPublishTime(date);
			gwInformation.setUpdateTime(date);
			//gwInformation.setStatus(Constant.STATUS_PUBLISH);
			
			//gwInformation.setContent(new String(gwInformation.getContent().getBytes("GBK"),"UTF-8"));
			
//			if(gwInformation.getContent().length()*3 > 65535){
//				AssertUtil.isTrue(false, "文章内容字数过长");
//			}

			detection(gwInformation);
			
			Long sort = gwInformationDao.getMinSort();
			if(null==sort){
				gwInformation.setSort(0L);
			}else{
				gwInformation.setSort(sort-1);
			}
			
			Long id = gwInformationDao.save(gwInformation);
			
			AssertUtil.notNull(id, "保存数据失败");
			
			return id;
		} catch(GwInformationException e) {
			_logger.error("修改资讯失败:" + e.getMessage(), e);
			throw new GwInformationException("修改资讯失败",e);
		} catch (RuntimeException e) {
			_logger.error("新增资讯失败:" + e.getMessage(), e);
			throw new GwInformationException("新增资讯失败:" + e.getMessage(),e);
		} catch (Exception e) {
			_logger.error("新增资讯失败:" + e.getMessage(), e);
			throw new GwInformationException("新增资讯失败",e);
		}
	}

	@Override
	public GwInformation getGwInformation(Long id)throws GwInformationException {
		try {
			AssertUtil.notNull(id, "资讯ID不能为空");
			GwInformation gwInformation = gwInformationDao.getByPrimaryKey(id);
			return gwInformation;
		} catch (Exception e) {
			_logger.error("查询资讯失败:" + e.getMessage(), e);
			throw new GwInformationException(e.getMessage(),e);
		}
	}

	@Override
	public boolean edit(GwInformation gwInformation) throws GwInformationException {
		try {
			AssertUtil.notNull(gwInformation.getId(), "资讯ID不能为空");
			GwInformation model = gwInformationDao.getByPrimaryKey(gwInformation.getId());
			AssertUtil.notNull(model, "资讯不存在");

//			if(gwInformation.getContent().length()*3 > 65535){
//				AssertUtil.isTrue(false, "文章内容字数过长");
//			}
			
			detection(gwInformation);
			
//			Integer status = model.getStatus();
			
			Date date = new Date();
			gwInformation.setUpdateTime(date);
			if (null!=gwInformation.getStatus() && Constant.STATUS_PUBLISH.equals(gwInformation.getStatus())/* && !Constant.STATUS_PUBLISH.equals(status)*/) {
				gwInformation.setPublishTime(date);
			}
			
//			if(Constant.STATUS_PUBLISH.equals(status)){
//				_logger.info("资讯已发布过,不再发布");
//			}
			
			gwInformationDao.update(gwInformation);
		} catch(GwInformationException e) {
			_logger.error("修改资讯失败:" + e.getMessage(), e);
			throw new GwInformationException("修改资讯失败",e);
		} catch(RuntimeException e) {
			_logger.error("修改资讯失败:" + e.getMessage(), e);
			throw new GwInformationException("修改资讯失败:" + e.getMessage(),e);
		} catch (Exception e) {
			_logger.error("修改资讯失败:" + e.getMessage(), e);
			throw new GwInformationException("修改资讯失败",e);
		}
		
		return true;
	}
	
	@Override
	public Pagination<GwInformation> findGwInformationPageList(GwInformation gwInformation, Pagination<GwInformation> pagination) {
		int count = gwInformationDao.getCount(gwInformation);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("gwInformation", gwInformation);
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		List<GwInformation> list = gwInformationDao.findGwInformationPageList(map);
		pagination.setDatas(list);
		pagination.setTotalRow(count);
		return pagination;
	}

	@Override
	@Transactional
	public void updateSort(Integer sort, String ids) throws GwInformationException {
		try {
			if(sort == null){
				AssertUtil.isTrue(false, "排序类型错误");
			}
			
			if(StringUtils.isBlank(ids)){
				AssertUtil.isTrue(false, "ID集合不可为空");
			}
			
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
					_logger.error("数据出错:" + e.getMessage(),e);
					throw new GwInformationException("数据出错",e);
				}
				
				if(upIdColl.length == 1){
					Long upId = upIdColl[0];
					GwInformation upobj = gwInformationDao.getByPrimaryKey(upId);
					GwInformation downobj = gwInformationDao.getByPrimaryKey(downId);
					if(upobj == null){
						throw new GwInformationException("ID:" + upId + ",没有此资讯数据");
					}else if(downobj == null){
						throw new GwInformationException("ID:" + downId + ",没有此资讯数据");
					}
					
					Long upSort = downobj.getSort();
					Long downSort = upobj.getSort();
					
					upobj.setSort(upSort);
					downobj.setSort(downSort);
					
					gwInformationDao.update(upobj);
					gwInformationDao.update(downobj);
				}
			}else if(Constant.SORT_TYPE_DOWN_LAST==sort || Constant.SORT_TYPE_UP_FIRST==sort){
				Long id = null;
				try {
					id = Long.parseLong(ids);
				} catch (Exception e) {
					_logger.error("数据出错:" + e.getMessage(),e);
					throw new GwInformationException("数据出错",e);
				}
				
				boolean isDown = false;
				if(sort > 0){
					isDown = true;
				}
				
				GwInformation gwInformation = gwInformationDao.getByPrimaryKey(id);
				AssertUtil.notNull(gwInformation, "ID:" + id + ",没有此资讯数据");
				
				if(isDown){
					Long maxSort = gwInformationDao.getMaxSort();
					gwInformation.setSort(maxSort+1);
				}else{
					Long minSort = gwInformationDao.getMinSort();
					gwInformation.setSort(minSort-1);
				}
				gwInformationDao.update(gwInformation);
			}else{
				throw new GwInformationException("排序类型未知");
			}
		} catch (Exception e) {
			throw new GwInformationException("排序失败_" + e.getMessage(),e);
		}
	}
	
	/**
	 * 检测字数
	 * @param gwInformation
	 */
	private void detection(GwInformation gwInformation){
		if(gwInformation.getContent().length() > 16777215){
			AssertUtil.isTrue(false, "文章内容字数过长");
		}
		
		if(gwInformation.getTitle().length() > 64){
			AssertUtil.isTrue(false, "资讯标题字数过长");
		}
		
		if(gwInformation.getAnnouncer().length() > 30){
			AssertUtil.isTrue(false, "作者字数过长");
		}
		
		if(gwInformation.getSummary().length() > 65535){
			AssertUtil.isTrue(false, "内容摘要字数过长");
		}
		
		if(gwInformation.getSource().length() > 64){
			AssertUtil.isTrue(false, "来源字数过长");
		}
		
		if(gwInformation.getSeoTitle().length() > 64){
			AssertUtil.isTrue(false, "SEO标题字数过长");
		}
		
		if(gwInformation.getSeoKeywords().length() > 256){
			AssertUtil.isTrue(false, "SEO关键字字数过长");
		}
		
		if(gwInformation.getSeoDescription().length() > 512){
			AssertUtil.isTrue(false, "SEO描述字数过长");
		}
	}
}
