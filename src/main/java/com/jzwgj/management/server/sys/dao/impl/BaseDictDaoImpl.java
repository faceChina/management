package com.jzwgj.management.server.sys.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jzwgj.management.mapper.CBaseDictMapper;
import com.jzwgj.management.server.sys.dao.BaseDictDao;
import com.jzwgj.management.server.sys.domain.CBaseDict;

@Repository
public class BaseDictDaoImpl implements BaseDictDao {
	
	@Autowired
	private SqlSession sqlSession;

	public void add(CBaseDict cBaseDict) {
		sqlSession.getMapper(CBaseDictMapper.class).insertSelective(cBaseDict);
	}

	public void edit(CBaseDict cBaseDict) {
		sqlSession.getMapper(CBaseDictMapper.class).updateByPrimaryKeySelective(cBaseDict);
	}

	public CBaseDict getById(Long id) {
		return sqlSession.getMapper(CBaseDictMapper.class).selectByPrimaryKey(id);
	}

	public List<CBaseDict> findList(CBaseDict cBaseDict) {
		return sqlSession.getMapper(CBaseDictMapper.class).selectList(cBaseDict);
	}


	public void delete(Long id) {
		sqlSession.getMapper(CBaseDictMapper.class).deleteByPrimaryKey(id);
	}

	public Integer getCount(CBaseDict cbd) {
		return sqlSession.getMapper(CBaseDictMapper.class).selectCount(cbd);
	}
	
	public List<CBaseDict> findPageList(CBaseDict cBaseDict, Integer start,
			Integer pageSize) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("pojo", cBaseDict);
		map.put("start", start);
		map.put("pageSize", pageSize);
		return sqlSession.getMapper(CBaseDictMapper.class).selectPageList(map);
	}

}
