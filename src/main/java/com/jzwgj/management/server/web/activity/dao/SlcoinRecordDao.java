package com.jzwgj.management.server.web.activity.dao;

import java.util.List;
import java.util.Map;

import com.jzwgj.management.server.web.activity.domain.SlcoinRecord;
import com.jzwgj.management.server.web.activity.domain.SlcoinRecordVo;
import com.jzwgj.management.server.web.activity.domain.UserVo;

public interface SlcoinRecordDao {
	
	/**
	 * 赠送颜值
	 * @param slcoinRecord
	 * @author talo
	 */
	void insert (SlcoinRecord slcoinRecord);
	
	/**
	 * 统计分页查询用户颜值记录总数
	 * @param map
	 * @return
	 * @author talo 
	 */
	Integer getPageCount (SlcoinRecordVo slcoinRecordVo);
	
	/**
	 * 分页查询用户颜值记录
	 * @param map
	 * @return
	 * @author talo 
	 */
	 List<SlcoinRecordVo> findSlcoinRecordPageList (Map<String,Object> map);
	
	
	/**
	 * 获得个人历史颜值总数
	 * @param  userId	
	 * @author talo
	 */
	
	Integer getHistoryTotalSlcoin (Long userId); 
	
	
	List<SlcoinRecord> getSlcoinByShop(Map<String,Object> map);
	
	/**
	 * 查询不同类型的刷脸币总量
	 * @param type	类型:1注册赠送 2消费使用 3取消退回 4邀请好友注册赠送
	 * @return
	 */
	Integer getTotalSlcoin(Integer type);
	
	/**
	 * 查询最早/最晚 使用刷脸记录的时间
	 * @return
	 */
	SlcoinRecordVo getSlcoinRecordTime();
	
	/**
	 * 查询某天刷脸币使用情况
	 * @param map
	 * @return
	 */
	Integer getSlcoinRecordByTime(Map<String,Object> map);
	
	/**
	 * 查询某个时间段刷脸币使用情况
	 * @param map
	 * @return
	 */
	Integer getSlcoinRecordByCurrentTime(Map<String,Object> map);
	
	/**
	 * 查询刷脸币记录
	 * @param map
	 * @return
	 */
	SlcoinRecord getSlcoinRecord(Map<String,Object> map);
	
	List<UserVo> findUserPageList (Map<String,Object> map);
	
	Integer getUserCount(Map<String,Object> map);
}
