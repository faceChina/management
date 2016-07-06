package com.jzwgj.management.server.web.activity.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jzwgj.management.exception.SlcoinActivityException;
import com.jzwgj.management.server.web.activity.dao.SlcoinActivityDao;
import com.jzwgj.management.server.web.activity.domain.SlcoinActivity;
import com.jzwgj.management.server.web.activity.domain.SlcoinActivityVo;
import com.jzwgj.management.server.web.activity.service.SlcoinActivityService;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Pagination;

@Service("slcoinActivityService")
public class SlcoinActivityServiceImpl implements SlcoinActivityService {
	
	Logger _logger = Logger.getLogger(getClass());
	
	@Autowired
	private SlcoinActivityDao slcoinActivityDao;

	@Override
	public Pagination<SlcoinActivityVo> findSlcoinActivityPageList(SlcoinActivityVo slcoinActivityVo, Pagination<SlcoinActivityVo> pagination) throws SlcoinActivityException {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("start", pagination.getStart());
			map.put("pageSize", pagination.getPageSize());
			
			Integer count = slcoinActivityDao.getCount();
			List<SlcoinActivityVo> list = slcoinActivityDao.findSlcoinActivityPageList(map);
			
			Date now = new Date();
			
			for(SlcoinActivityVo vo : list){
				Date beginTime = vo.getBeginTime();
				Date endTime = vo.getEndTime();
				
				vo.setActivityTime(DateFormatUtils.format(beginTime, "yyyy.MM.dd HH:mm") + "—" + DateFormatUtils.format(endTime, "yyyy.MM.dd HH:mm"));
				
				if(now.before(beginTime)){
					vo.setNowStatus(SlcoinActivityVo.STATUS_NOT);
				}else if(now.after(beginTime) && now.before(endTime)){
					vo.setNowStatus(SlcoinActivityVo.STATUS_UNDERWAY);
				}else{
					vo.setNowStatus(SlcoinActivityVo.STATUS_END);
				}
			}
			
			pagination.setDatas(list);
			pagination.setTotalRow(count);
			
			return pagination;
		} catch (Exception e) {
			_logger.error("分页查询刷脸活动数据失败:" + e.getMessage(), e);
			throw new SlcoinActivityException("分页查询刷脸活动数据失败:" + e.getMessage(),e);
		}
	}

	@Override
	public void insert(SlcoinActivity slcoinActivity) throws SlcoinActivityException {
		try {
			AssertUtil.notNull(slcoinActivity, "参数slcoinActivityVo为空");
			
			AssertUtil.hasLength(slcoinActivity.getName(), "活动名称不能为空");
			
			Date beginTime = slcoinActivity.getBeginTime();
			Date endTime = slcoinActivity.getEndTime();
			
			AssertUtil.notNull(beginTime, "活动开始时间不能为空");
			AssertUtil.notNull(endTime, "活动结束时间不能为空");
			AssertUtil.isTrue(beginTime.before(endTime), "活动开始时间不可大于结束时间");
			
			AssertUtil.notNull(slcoinActivity.getInviteAmount(), "邀请者赠送点数不能为空");
			AssertUtil.isTrue(StringUtils.isNumeric(slcoinActivity.getInviteAmount().toString()),"邀请者赠送点数只可输入数字");
			AssertUtil.isTrue(slcoinActivity.getInviteAmount()>0, "邀请者赠送点数不可小于0");
			AssertUtil.isTrue(slcoinActivity.getInviteAmount()<=100, "邀请者赠送点数不可大于100");
			
			AssertUtil.notNull(slcoinActivity.getRegisterAmount(), "被邀请者赠送点数不能为空");
			AssertUtil.isTrue(StringUtils.isNumeric(slcoinActivity.getRegisterAmount().toString()),"被邀请者赠送点数只可输入数字");
			AssertUtil.isTrue(slcoinActivity.getRegisterAmount()>0, "被邀请者赠送点数不可小于0");
			AssertUtil.isTrue(slcoinActivity.getRegisterAmount()<=100, "被邀请者赠送点数不可大于100");
			
			AssertUtil.notNull(slcoinActivity.getSuperAmount(), "邀请者上级赠送点数不能为空");
			AssertUtil.isTrue(StringUtils.isNumeric(slcoinActivity.getSuperAmount().toString()),"邀请者上级赠送点数只可输入数字");
			AssertUtil.isTrue(slcoinActivity.getSuperAmount()>0, "邀请者上级赠送点数不可小于0");
			AssertUtil.isTrue(slcoinActivity.getSuperAmount()<=100, "邀请者上级赠送点数不可大于100");
			
			AssertUtil.notTrue(this.getPointInTime(beginTime, endTime,null),"该时间段已有活动进行");
			
			Date now = new Date();
			slcoinActivity.setCreateTime(now);
			slcoinActivity.setUpdateTime(now);
			slcoinActivityDao.insert(slcoinActivity);
		} catch (Exception e) {
			_logger.error("新增活动失败:" + e.getMessage(), e);
			throw new SlcoinActivityException("新增活动失败:" + e.getMessage(),e);
		}
	}

	@Override
	public Boolean getPointInTime(Date beginTime, Date endTime,Long id) throws SlcoinActivityException {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("id", id);
		
		Integer count = slcoinActivityDao.getPointInTime(map);
		if(count > 0){
			return true;
		}
		return false;
	}

	@Override
	public SlcoinActivity selectByPrimaryKey(Long id) throws SlcoinActivityException {
		try {
			AssertUtil.notNull(id, "参数ID不可为空");
			SlcoinActivity slcoinActivity = slcoinActivityDao.selectByPrimaryKey(id);
			AssertUtil.notNull(slcoinActivity, "该活动已不存在");
			return slcoinActivity;
		} catch (Exception e) {
			_logger.error("查询活动失败:" + e.getMessage(), e);
			throw new SlcoinActivityException("查询活动失败:" + e.getMessage(),e);
		}
	}

	@Override
	public void update(SlcoinActivity slcoinActivity) throws SlcoinActivityException {
		try {
			AssertUtil.notNull(slcoinActivity, "参数slcoinActivityVo为空");
			AssertUtil.notNull(slcoinActivity.getId(), "活动Id不可为空");
			
			AssertUtil.hasLength(slcoinActivity.getName(), "活动名称不能为空");
			
			Date beginTime = slcoinActivity.getBeginTime();
			Date endTime = slcoinActivity.getEndTime();
			
			AssertUtil.notNull(beginTime, "活动开始时间不能为空");
			AssertUtil.notNull(endTime, "活动结束时间不能为空");
			AssertUtil.isTrue(beginTime.before(endTime), "活动开始时间不可大于结束时间");
			
			AssertUtil.notNull(slcoinActivity.getInviteAmount(), "邀请者赠送点数不能为空");
			AssertUtil.isTrue(StringUtils.isNumeric(slcoinActivity.getInviteAmount().toString()),"邀请者赠送点数只可输入数字");
			AssertUtil.isTrue(slcoinActivity.getInviteAmount()>0, "邀请者赠送点数不可小于0");
			AssertUtil.isTrue(slcoinActivity.getInviteAmount()<=100, "邀请者赠送点数不可大于100");
			
			AssertUtil.notNull(slcoinActivity.getRegisterAmount(), "被邀请者赠送点数不能为空");
			AssertUtil.isTrue(StringUtils.isNumeric(slcoinActivity.getRegisterAmount().toString()),"被邀请者赠送点数只可输入数字");
			AssertUtil.isTrue(slcoinActivity.getRegisterAmount()>0, "被邀请者赠送点数不可小于0");
			AssertUtil.isTrue(slcoinActivity.getRegisterAmount()<=100, "被邀请者赠送点数不可大于100");
			
			AssertUtil.notNull(slcoinActivity.getSuperAmount(), "邀请者上级赠送点数不能为空");
			AssertUtil.isTrue(StringUtils.isNumeric(slcoinActivity.getSuperAmount().toString()),"邀请者上级赠送点数只可输入数字");
			AssertUtil.isTrue(slcoinActivity.getSuperAmount()>0, "邀请者上级赠送点数不可小于0");
			AssertUtil.isTrue(slcoinActivity.getSuperAmount()<=100, "邀请者上级赠送点数不可大于100");
			
			AssertUtil.notTrue(this.getPointInTime(beginTime, endTime,slcoinActivity.getId()),"该时间段已有活动进行");
			
			Date now = new Date();
			slcoinActivity.setUpdateTime(now);
			
			slcoinActivityDao.update(slcoinActivity);
		} catch (Exception e) {
			_logger.error("修改活动失败:" + e.getMessage(), e);
			throw new SlcoinActivityException("修改活动失败:" + e.getMessage(),e);
		}
	}

	@Override
	public void updateTime(Long id, Integer type) throws SlcoinActivityException {
		try {
			SlcoinActivity slcoinActivity = slcoinActivityDao.selectByPrimaryKey(id);
			AssertUtil.notNull(slcoinActivity, "该活动已不存在");
			
			Date now = new Date();
			slcoinActivity.setUpdateTime(now);
			
			if(SlcoinActivityVo.UPDATE_TYPE_END.equals(type)){
				if(now.before(slcoinActivity.getBeginTime())){
					slcoinActivity.setBeginTime(now);
				}
				slcoinActivity.setEndTime(now);
			}else if(SlcoinActivityVo.UPDATE_TYPE_STATUS.equals(type)){
				AssertUtil.notTrue(this.getPointInTime(now, slcoinActivity.getEndTime(),slcoinActivity.getId()),"该时间段已有活动进行");
				slcoinActivity.setBeginTime(now);
			}else{
				AssertUtil.isTrue(false, "修改类型错误");
			}
			
			slcoinActivityDao.update(slcoinActivity);
		} catch (Exception e) {
			_logger.error("修改活动时间失败:" + e.getMessage(), e);
			throw new SlcoinActivityException("修改活动时间失败:" + e.getMessage(),e);
		}
	}
}