package com.jzwgj.management.server.web.activity.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jzwgj.management.mapper.SlcoinShopMapper;
import com.jzwgj.management.server.web.activity.dao.SlcoinShopDao;
import com.jzwgj.management.server.web.activity.domain.SlcoinShopVo;

@Repository
public class SlcoinShopDaoImpl implements SlcoinShopDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public Integer getCount(Map<String, Object> map) {
		return sqlSession.getMapper(SlcoinShopMapper.class).getCount(map);
	}

	@Override
	public List<SlcoinShopVo> findSlcoinShopByPageList(Map<String, Object> map) {
		return sqlSession.getMapper(SlcoinShopMapper.class).findSlcoinShopByPageList(map);
	}

}
