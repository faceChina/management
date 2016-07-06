package com.jzwgj.management.server.web.activity.service;

import java.util.Map;

import com.jzwgj.management.exception.SlcoinShopException;
import com.jzwgj.management.server.web.activity.domain.SlcoinRecordVo;
import com.jzwgj.management.server.web.activity.domain.SlcoinShopVo;
import com.zjlp.face.util.page.Pagination;


public interface SlcoinShopService {
	
	/**
	 * 查询店铺刷脸币使用情况
	 * @param slcoinRecordVo	
	 * @return
	 */
	Pagination<SlcoinShopVo> findSlcoinShopByPageList(SlcoinRecordVo slcoinRecordVo,Pagination<SlcoinShopVo> pagination) throws SlcoinShopException;
	
	/**
	 * 查询累计发放颜值和使用颜值
	 * @return
	 */
	Map<String,Integer> getTotalSlcoin();

	/**
	 * 分页查询当前时间段发放颜值和使用颜值
	 * @param slcoinRecordVo
	 * @return
	 */
	Pagination<SlcoinRecordVo> getslcoinReportByTime(SlcoinRecordVo slcoinRecordVo,Pagination<SlcoinRecordVo> pagination);
	
	/**
	 * 查询当前时间段发放颜值和使用颜值
	 * @param slcoinRecordVo
	 * @return
	 */
	Map<String,Integer> getSlcoinRecordByCurrentTime(SlcoinRecordVo slcoinRecordVo);
}
