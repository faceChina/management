package com.jzwgj.management.server.web.classifcation.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jzwgj.management.component.base.BaseDaoImpl;
import com.jzwgj.management.mapper.PropValueMapper;
import com.jzwgj.management.server.web.classifcation.dao.PropValueDao;
import com.jzwgj.management.server.web.classifcation.domain.PropValue;
@Repository
public class PropValueDaoImpl extends BaseDaoImpl<Long, PropValue> implements PropValueDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public SqlSession getSqlSession(){
		return sqlSession;
	}
	
	@Override
	public Class<?> getMapperClass() {
		return PropValueMapper.class;
	}

	@Override
	public Integer selectCount(PropValue propValue) {
		Map<String,Object> map = new HashMap<String,Object> (1);
		map.put("propValue", propValue);
		return sqlSession.getMapper(PropValueMapper.class).selectCount(map);
	}

	@Override
	public List<PropValue> selectPageList(PropValue propValue, int start,
			int pageSize) {
		Map<String,Object> map = new HashMap<String,Object> (3);
		map.put("propValue", propValue);
		map.put("start", start);
		map.put("pageSize", pageSize);
		return sqlSession.getMapper(PropValueMapper.class).selectPageList(map);
	}

	@Override
	public List<PropValue> selectPageListFilterClass(PropValue propValue,int start, int pageSize, Long classificationId) {
		Map<String,Object> map = new HashMap<String,Object> (3);
		map.put("propValue", propValue);
		map.put("start", start);
		map.put("pageSize", pageSize);
		map.put("classificationId", classificationId);
		return sqlSession.getMapper(PropValueMapper.class).selectPageList(map);
	}

	@Override
	public void deleteByPropId(Long propId) {
		sqlSession.getMapper(PropValueMapper.class).deleteByPropId(propId);
	}

	@Override
	public Integer selectMaxSort(PropValue query) {
		Map<String,Object> map = new HashMap<String,Object> (1);
		map.put("propValue", query);;
		return sqlSession.getMapper(PropValueMapper.class).selectMaxSort(map);
	}
}
