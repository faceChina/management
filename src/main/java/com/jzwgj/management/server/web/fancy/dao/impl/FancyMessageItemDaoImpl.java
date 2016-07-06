package com.jzwgj.management.server.web.fancy.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jzwgj.management.mapper.FancyMessageItemMapper;
import com.jzwgj.management.server.web.fancy.dao.FancyMessageItemDao;
import com.jzwgj.management.server.web.fancy.domain.FancyMessageItem;
@Repository
public class FancyMessageItemDaoImpl implements FancyMessageItemDao {
	@Autowired
	private SqlSession sqlSession;

	@Override
	public Long save(FancyMessageItem fancyMessageItem) {
		 sqlSession.getMapper(FancyMessageItemMapper.class).insert(fancyMessageItem);
		 return fancyMessageItem.getId();
	}

	@Override
	public List<FancyMessageItem> selectByFancyMessageId(Long fancyMessageId) {
		return  sqlSession.getMapper(FancyMessageItemMapper.class).selectByFancyMessageId(fancyMessageId);
	}

	@Override
	public void update(FancyMessageItem fancyMessageItem) {
		sqlSession.getMapper(FancyMessageItemMapper.class).updateByPrimaryKeySelective(fancyMessageItem);
	}

	@Override
	public int deleteByFancyMessageId(Long fancyMessageId) {
		return sqlSession.getMapper(FancyMessageItemMapper.class).deleteByFancyMessageId(fancyMessageId);
	}

	@Override
	public void removeByFancyMessageId(Long fancyMessageId) {
		sqlSession.getMapper(FancyMessageItemMapper.class).removeByFancyMessageId(fancyMessageId);
	}

	@Override
	public FancyMessageItem selectByPrimaryKey(Long id) {
		return sqlSession.getMapper(FancyMessageItemMapper.class).selectByPrimaryKey(id);
	}
}
