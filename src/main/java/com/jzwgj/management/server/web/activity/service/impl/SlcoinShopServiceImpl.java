package com.jzwgj.management.server.web.activity.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jzwgj.management.exception.SlcoinActivityException;
import com.jzwgj.management.exception.SlcoinShopException;
import com.jzwgj.management.server.web.activity.dao.SlcoinRecordDao;
import com.jzwgj.management.server.web.activity.dao.SlcoinShopDao;
import com.jzwgj.management.server.web.activity.domain.SlcoinRecord;
import com.jzwgj.management.server.web.activity.domain.SlcoinRecordVo;
import com.jzwgj.management.server.web.activity.domain.SlcoinShopVo;
import com.jzwgj.management.server.web.activity.service.SlcoinShopService;
import com.jzwgj.management.util.DateUtils;
import com.zjlp.face.util.page.Pagination;

@Service("slcoinShopService")
public class SlcoinShopServiceImpl implements SlcoinShopService {
	
	Logger _logger = Logger.getLogger(getClass());
	
	@Autowired
	private SlcoinShopDao slcoinShopDao;
	
	@Autowired
	private SlcoinRecordDao slcoinRecordDao;

	@Override
	public Pagination<SlcoinShopVo> findSlcoinShopByPageList(SlcoinRecordVo slcoinRecordVo, Pagination<SlcoinShopVo> pagination) throws SlcoinShopException{
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("start", pagination.getStart());
			map.put("pageSize", pagination.getPageSize());
			map.put("slcoinRecord", slcoinRecordVo);
			
			Integer count = slcoinShopDao.getCount(map);
			List<SlcoinShopVo> list = slcoinShopDao.findSlcoinShopByPageList(map);
			
			pagination.setDatas(list);
			pagination.setTotalRow(count);
			
			return pagination;
		} catch (Exception e) {
			_logger.error("分页查询店铺使用情况数据失败:" + e.getMessage(), e);
			throw new SlcoinActivityException("分页查询店铺使用情况数据失败:" + e.getMessage(),e);
		}
	}

	@Override
	public Map<String, Integer> getTotalSlcoin() throws SlcoinShopException{
		try {
			Integer registerTotal = slcoinRecordDao.getTotalSlcoin(SlcoinRecord.TYPE_REGISTER);
			Integer inviteTotal = slcoinRecordDao.getTotalSlcoin(SlcoinRecord.TYPE_INVITE);
			
			Integer consumeTotal = slcoinRecordDao.getTotalSlcoin(SlcoinRecord.TYPE_CONSUME);
			Integer cancelTotal = slcoinRecordDao.getTotalSlcoin(SlcoinRecord.TYPE_CANCEL);
			
			Map<String,Integer> map = new HashMap<String,Integer>();
			map.put("giveTotal", registerTotal+inviteTotal);
			map.put("useTotal", consumeTotal-cancelTotal);
			
			return map;
		} catch (Exception e) {
			_logger.error("查询累计发放颜值和使用颜值失败:" + e.getMessage(), e);
			throw new SlcoinActivityException("查询累计发放颜值和使用颜值失败:" + e.getMessage(),e);
		}
	}

	@Override
	public Pagination<SlcoinRecordVo> getslcoinReportByTime(SlcoinRecordVo slcoinRecordVo,Pagination<SlcoinRecordVo> pagination) throws SlcoinShopException{
		try {
			String beginTime = slcoinRecordVo.getBeginTime();
			String endTime = slcoinRecordVo.getEndTime();
			
			Date beginDate = null;
			Date endDate = null;
			
			SlcoinRecordVo time = slcoinRecordDao.getSlcoinRecordTime();
			if(StringUtils.isBlank(beginTime)){
				beginDate = time.getBeginDate();
			}else{
				beginDate = DateUtils.getDate(beginTime,new SimpleDateFormat("yyyy-MM-dd"));
			}
			if(StringUtils.isBlank(endTime)){
				endDate = time.getEndDate();
			}else{
				endDate = DateUtils.getDate(endTime,new SimpleDateFormat("yyyy-MM-dd"));
			}
			
			Integer totalRow = DateUtils.getDateList(DateFormatUtils.format(beginDate, "yyyy-MM-dd"),DateFormatUtils.format(endDate, "yyyy-MM-dd")).size();
			
			Integer start = pagination.getStart();
			Integer pageSize = pagination.getPageSize();
			
			Date beginDate2 = getTime(beginDate,start);
			Date endDate2 = getTime(beginDate2,pageSize-1);
			
			if(beginDate2.before(endDate)){
				beginDate = beginDate2;
			}
			
			if(endDate2.before(endDate)){
				endDate = endDate2;
			}
			
			List<String> dataList = DateUtils.getDateList(DateFormatUtils.format(beginDate, "yyyy-MM-dd"),DateFormatUtils.format(endDate, "yyyy-MM-dd"));
			
			List<SlcoinRecordVo> list = new ArrayList<SlcoinRecordVo>();
			for(int i=dataList.size()-1;i>=0;i--){
				String s = dataList.get(i);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("time", s);
				
				map.put("type", SlcoinRecordVo.TYPE_REGISTER);
				Integer registerTotal = slcoinRecordDao.getSlcoinRecordByTime(map);

				map.put("type", SlcoinRecordVo.TYPE_INVITE);
				Integer inviteTotal = slcoinRecordDao.getSlcoinRecordByTime(map);

				map.put("type", SlcoinRecordVo.TYPE_CONSUME);
				Integer consumeTotal = slcoinRecordDao.getSlcoinRecordByTime(map);
				
				map.put("type", SlcoinRecordVo.TYPE_CANCEL);
				Integer cancelTotal = slcoinRecordDao.getSlcoinRecordByTime(map);
				
				SlcoinRecordVo vo = new SlcoinRecordVo();
				vo.setGiveTotal(registerTotal + inviteTotal);
				vo.setUseTotal(consumeTotal - cancelTotal);
				vo.setBeginTime(s);
				list.add(vo);
			}
			
			pagination.setTotalRow(totalRow);
			pagination.setDatas(list);
			pagination.setStart(start);
			
			return pagination;
		} catch (Exception e) {
			_logger.error("分页查询当前时间段发放颜值和使用颜值:" + e.getMessage(), e);
			throw new SlcoinActivityException("分页查询当前时间段发放颜值和使用颜值:" + e.getMessage(),e);
		}
	}
	
	private Date getTime(Date time,Integer total) {
		Calendar calendar = Calendar.getInstance();   
		calendar.setTime(time); 
		calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+total);//让日期加1  
		
		return calendar.getTime();
	}

	@Override
	public Map<String, Integer> getSlcoinRecordByCurrentTime(SlcoinRecordVo slcoinRecordVo) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("slcoinRecord", slcoinRecordVo);
		
		map.put("type", SlcoinRecordVo.TYPE_REGISTER);
		Integer registerTotal = slcoinRecordDao.getSlcoinRecordByCurrentTime(map);
		
		map.put("type", SlcoinRecordVo.TYPE_INVITE);
		Integer inviteTotal = slcoinRecordDao.getSlcoinRecordByCurrentTime(map);

		map.put("type", SlcoinRecordVo.TYPE_CONSUME);
		Integer consumeTotal = slcoinRecordDao.getSlcoinRecordByCurrentTime(map);
		
		map.put("type", SlcoinRecordVo.TYPE_CANCEL);
		Integer cancelTotal = slcoinRecordDao.getSlcoinRecordByCurrentTime(map);
		
		Map<String,Integer> totalMap = new HashMap<String,Integer>();
		totalMap.put("giveTotal", registerTotal+inviteTotal);
		totalMap.put("useTotal", consumeTotal-cancelTotal);
		
		return totalMap;
	}
}