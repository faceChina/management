package com.jzwgj.management.server.web.fancy.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jzwgj.management.mapper.FancyMessageMapper;
import com.jzwgj.management.server.web.fancy.dao.FancyMessageDao;
import com.jzwgj.management.server.web.fancy.domain.FancyMessage;
@Repository
public class FancyMessageDaoImpl implements FancyMessageDao {
	@Autowired
	private SqlSession sqlSession;

	@Override
	public Long save(FancyMessage fancyMessage) {
		 sqlSession.getMapper(FancyMessageMapper.class).insert(fancyMessage);
		 return fancyMessage.getId();
	}

	@Override
	public FancyMessage selectByPrimaryKey(Long id) {
		return sqlSession.getMapper(FancyMessageMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void update(FancyMessage fancyMessage) {
		sqlSession.getMapper(FancyMessageMapper.class).updateByPrimaryKey(fancyMessage);
	}
	@Override
	public void deleteById(Long id) {
		sqlSession.getMapper(FancyMessageMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public int getCount(FancyMessage fancyMessage) {
		return sqlSession.getMapper(FancyMessageMapper.class).getCount(fancyMessage);
	}

	@Override
	public List<FancyMessage> findFancyMessagePageList(Map<String, Object> map) {
		return sqlSession.getMapper(FancyMessageMapper.class).findFancyMessagePageList(map);
	}
}
