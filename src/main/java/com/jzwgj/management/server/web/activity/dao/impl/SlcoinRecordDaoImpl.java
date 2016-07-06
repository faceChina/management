package com.jzwgj.management.server.web.activity.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jzwgj.management.mapper.SlcoinRecordMapper;
import com.jzwgj.management.server.web.activity.dao.SlcoinRecordDao;
import com.jzwgj.management.server.web.activity.domain.SlcoinRecord;
import com.jzwgj.management.server.web.activity.domain.SlcoinRecordVo;
import com.jzwgj.management.server.web.activity.domain.UserVo;

@Repository
public class SlcoinRecordDaoImpl implements SlcoinRecordDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<SlcoinRecord> getSlcoinByShop(Map<String, Object> map) {
		return sqlSession.getMapper(SlcoinRecordMapper.class).getSlcoinByShop(map);
	}

	@Override
	public Integer getTotalSlcoin(Integer type) {
		Integer total = sqlSession.getMapper(SlcoinRecordMapper.class).getTotalGiveSlcoin(type);
		
		if(total == null){
			total = 0;
		}
		
		return total;
	}

	@Override
	public SlcoinRecordVo getSlcoinRecordTime() {
		return sqlSession.getMapper(SlcoinRecordMapper.class).getSlcoinRecordTime();
	}

	@Override
	public Integer getSlcoinRecordByTime(Map<String, Object> map) {
		Integer total = sqlSession.getMapper(SlcoinRecordMapper.class).getSlcoinRecordByTime(map);
		
		if(total == null){
			total = 0;
		}
		
		return total;
	}

	@Override
	public Integer getSlcoinRecordByCurrentTime(Map<String, Object> map) {
		Integer total = sqlSession.getMapper(SlcoinRecordMapper.class).getSlcoinRecordByCurrentTime(map);
		
		if(total == null){
			total = 0;
		}
		
		return total;
	}

	@Override
	public SlcoinRecord getSlcoinRecord(Map<String, Object> map) {
		return sqlSession.getMapper(SlcoinRecordMapper.class).getSlcoinRecord(map);
	}

	@Override
	public List<UserVo> findUserPageList(Map<String, Object> map) {
		return sqlSession.getMapper(SlcoinRecordMapper.class).findUserPageList(map);
	}

	@Override
	public Integer getUserCount(Map<String, Object> map) {
		return sqlSession.getMapper(SlcoinRecordMapper.class).getUserCount(map);
	}

	@Override
	public Integer getHistoryTotalSlcoin(Long userId) {
		return sqlSession.getMapper(SlcoinRecordMapper.class).getHistoryTotalSlcoin(userId);
	}

	@Override
	public List<SlcoinRecordVo> findSlcoinRecordPageList(Map<String, Object> map) {
		return sqlSession.getMapper(SlcoinRecordMapper.class).findSlcoinRecordPageList(map);
	}

	@Override
	public void insert(SlcoinRecord slcoinRecord) {
		sqlSession.getMapper(SlcoinRecordMapper.class).insert(slcoinRecord);
	}

	@Override
	public Integer getPageCount(SlcoinRecordVo slcoinRecordVo) {
		return sqlSession.getMapper(SlcoinRecordMapper.class).getPageCount(slcoinRecordVo);
	}
}
