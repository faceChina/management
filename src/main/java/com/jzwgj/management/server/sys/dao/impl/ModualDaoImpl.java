package com.jzwgj.management.server.sys.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jzwgj.management.mapper.CModualMapper;
import com.jzwgj.management.server.sys.dao.ModualDao;
import com.jzwgj.management.server.sys.domain.CModual;

@Repository
public class ModualDaoImpl implements ModualDao {
	
	@Autowired
	private SqlSession sqlSession;

	public void add(CModual cModual) {
		sqlSession.getMapper(CModualMapper.class).insertSelective(cModual);
	}

	public void edit(CModual cModual) {
		sqlSession.getMapper(CModualMapper.class).updateByPrimaryKeySelective(cModual);
	}

	public CModual getById(Long id) {
		return sqlSession.getMapper(CModualMapper.class).selectByPrimaryKey(id);
	}

	public List<CModual> findList(CModual cModual) {
		return sqlSession.getMapper(CModualMapper.class).selectList(cModual);
	}

	public List<CModual> findPageList(CModual cModual, Integer start,
			Integer pageSize) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("pojo", cModual);
		map.put("start", start);
		map.put("pageSize", pageSize);
		return sqlSession.getMapper(CModualMapper.class).selectPageList(map);
	}

	public void delete(Long id) {
		sqlSession.getMapper(CModualMapper.class).deleteByPrimaryKey(id);
	}

	public Integer getChildCount(Long id) {
		return sqlSession.getMapper(CModualMapper.class).selectChildCount(id);
	}

}
