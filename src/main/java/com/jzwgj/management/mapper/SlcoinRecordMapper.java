package com.jzwgj.management.mapper;

import java.util.List;
import java.util.Map;

import com.jzwgj.management.server.web.activity.domain.SlcoinRecord;
import com.jzwgj.management.server.web.activity.domain.SlcoinRecordVo;
import com.jzwgj.management.server.web.activity.domain.UserVo;

public interface SlcoinRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SlcoinRecord record);

    int insertSelective(SlcoinRecord record);

    SlcoinRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SlcoinRecord record);

    int updateByPrimaryKey(SlcoinRecord record);

    List<SlcoinRecord> getSlcoinByShop(Map<String, Object> map);

	Integer getTotalGiveSlcoin(Integer type);

	SlcoinRecordVo getSlcoinRecordTime();

	Integer getSlcoinRecordByTime(Map<String, Object> map);

	Integer getSlcoinRecordByCurrentTime(Map<String, Object> map);

	SlcoinRecord getSlcoinRecord(Map<String, Object> map);

	List<UserVo> findUserPageList(Map<String, Object> map);

	Integer getUserCount(Map<String, Object> map);
	
	Integer getHistoryTotalSlcoin (Long userId);
	
	List<SlcoinRecordVo> findSlcoinRecordPageList (Map<String,Object> map);
	
	Integer getPageCount (SlcoinRecordVo slcoinRecordVo);
}