package com.jzwgj.management.server.web.activity.service;

import com.jzwgj.management.server.web.activity.domain.SlcoinRecord;
import com.jzwgj.management.server.web.activity.domain.SlcoinRecordVo;
import com.zjlp.face.util.page.Pagination;

public interface SlcoinRecordService {

	
	/**
	 * 赠送颜值
	 * @param slcoinRecord
	 * @author talo
	 */
	void giveUserSlcoin (SlcoinRecord slcoinRecord);
	
	/**
	 * 获得个人历史颜值总数
	 * @param  userId	
	 * @author talo
	 */
	
	Integer getHistoryTotalSlcoin (Long userId);
	
	/**
	 * 分页查询用户颜值记录
	 * @param SlcoinRecord
	 * @param pagination
	 * @return
	 * @author talo 
	 */
	 Pagination<SlcoinRecordVo> findSlcoinRecordPageList (SlcoinRecordVo slcoinRecordVo,Pagination<SlcoinRecordVo> pagination);
	 
}
