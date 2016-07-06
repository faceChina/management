package com.jzwgj.management.server.web.classifcation.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jzwgj.management.component.base.BaseDaoImpl;
import com.jzwgj.management.mapper.PropMapper;
import com.jzwgj.management.server.web.classifcation.dao.PropDao;
import com.jzwgj.management.server.web.classifcation.domain.Prop;

@Repository
public class PropDaoImpl extends BaseDaoImpl<Long, Prop>  implements PropDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public SqlSession getSqlSession(){
		return sqlSession;
	}
	
	@Override
	public Class<?> getMapperClass() {
		return PropMapper.class;
	}

	@Override
	public List<Prop> selectTreeList(String node) {
		return sqlSession.getMapper(PropMapper.class).selectTreeList(Long.valueOf(node));
	}

	@Override
	public Integer selectChildCount(Long id) {
		return sqlSession.getMapper(PropMapper.class).selectChildCount(id);
	}

	@Override
	public List<Prop> findAll() {
		return sqlSession.getMapper(PropMapper.class).selectAll();
	}
	@Override
	public List<Prop> selectPageList(Prop prop, int start, int pageSize) {
		Map<String,Object> map = new HashMap<String,Object> (3);
		map.put("pojo", prop);
		map.put("start", start);
		map.put("pageSize", pageSize);
		return sqlSession.getMapper(PropMapper.class).selectPageList(map);
	}

	@Override
	public Long getMaxSort() {
		return sqlSession.getMapper(PropMapper.class).getMaxSort();
	}
	@Override
	public Integer selectCount(Prop prop) {
		Map<String,Object> map = new HashMap<String,Object> (1);
		map.put("pojo", prop);
		return sqlSession.getMapper(PropMapper.class).selectCount(map);
	}

	@Override
	public List<Prop> selectPageListFilterClass(Prop prop, int start, int pageSize, Long classificationId) {
		Map<String,Object> map = new HashMap<String,Object> (4);
		map.put("pojo", prop);
		map.put("start", start);
		map.put("pageSize", pageSize);
		map.put("classificationId", classificationId);
		return sqlSession.getMapper(PropMapper.class).selectPageList(map);
	}

	@Override
	public Integer selectCountFilterClass(Prop prop, Long classificationId) {
		Map<String,Object> map = new HashMap<String,Object> (2);
		map.put("pojo", prop);
		map.put("classificationId", classificationId);
		return sqlSession.getMapper(PropMapper.class).selectCount(map);
	}
}
