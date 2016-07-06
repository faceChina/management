package com.jzwgj.management.server.web.activity.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jzwgj.management.mapper.SlcoinActivityMapper;
import com.jzwgj.management.server.web.activity.dao.SlcoinActivityDao;
import com.jzwgj.management.server.web.activity.domain.SlcoinActivity;
import com.jzwgj.management.server.web.activity.domain.SlcoinActivityVo;

@Repository
public class SlcoinActivityDaoImpl implements SlcoinActivityDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public Integer getCount() {
		return sqlSession.getMapper(SlcoinActivityMapper.class).getCount();
	}

	@Override
	public List<SlcoinActivityVo> findSlcoinActivityPageList(Map<String, Object> map) {
		return sqlSession.getMapper(SlcoinActivityMapper.class).findSlcoinActivityPageList(map);
	}

	@Override
	public void insert(SlcoinActivity slcoinActivity) {
		sqlSession.getMapper(SlcoinActivityMapper.class).insertSelective(slcoinActivity);
	}

	@Override
	public Integer getPointInTime(Map<String, Object> map) {
		return sqlSession.getMapper(SlcoinActivityMapper.class).getPointInTime(map);
	}

	@Override
	public void update(SlcoinActivity slcoinActivity) {
		sqlSession.getMapper(SlcoinActivityMapper.class).updateByPrimaryKeySelective(slcoinActivity);
	}

	@Override
	public SlcoinActivity selectByPrimaryKey(Long id) {
		return sqlSession.getMapper(SlcoinActivityMapper.class).selectByPrimaryKey(id);
	}

}
