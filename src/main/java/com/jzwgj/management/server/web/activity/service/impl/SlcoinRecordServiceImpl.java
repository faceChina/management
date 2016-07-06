package com.jzwgj.management.server.web.activity.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jzwgj.management.server.web.activity.dao.SlcoinRecordDao;
import com.jzwgj.management.server.web.activity.domain.SlcoinRecord;
import com.jzwgj.management.server.web.activity.domain.SlcoinRecordVo;
import com.jzwgj.management.server.web.activity.service.SlcoinRecordService;
import com.zjlp.face.account.service.AccountService;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.user.user.business.UserBusiness;

@Service
public class SlcoinRecordServiceImpl implements SlcoinRecordService {

	@Autowired
	private SlcoinRecordDao slcoinRecordDao;
	@Autowired
	private UserBusiness userBusiness;
	@Autowired(required=false)
	private AccountService accountService;
	
	
	@Override
	public Integer getHistoryTotalSlcoin(Long userId) {
		return slcoinRecordDao.getHistoryTotalSlcoin(userId);
	}

	public Pagination<SlcoinRecordVo> findSlcoinRecordPageList (SlcoinRecordVo slcoinRecordVo,Pagination<SlcoinRecordVo> pagination) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		map.put("slcoinRecordVo", slcoinRecordVo);
		slcoinRecordVo.setNickName(userBusiness.getUserById(slcoinRecordVo.getUserId()).getNickname());
		List<SlcoinRecordVo> list = slcoinRecordDao.findSlcoinRecordPageList(map);
		Integer count = slcoinRecordDao.getPageCount(slcoinRecordVo);
		pagination.setTotalRow(count);
//		pagination.setCurPage(0);
//		pagination.setToPage(0);
		pagination.setDatas(list);
		return pagination;
	}

	@Override
	public void giveUserSlcoin(SlcoinRecord slcoinRecord) {
		slcoinRecord.setType(SlcoinRecord.TYPE_ADMIN_GIVE);
		slcoinRecord.setStatus(new Byte("1"));
		Date date = new Date();
		slcoinRecord.setCreateTime(date);
		slcoinRecord.setUpdateTime(date);
		slcoinRecordDao.insert(slcoinRecord);
		
		Long userId = slcoinRecord.getUserId();
		Integer slcoinAmount = slcoinRecord.getAmount();
		accountService.increaseSlcoin(userId, slcoinAmount);
	}
}
