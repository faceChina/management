package com.jzwgj.management.server.web.classifcation.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jzwgj.management.component.base.BaseDaoImpl;
import com.jzwgj.management.mapper.ClassificationMapper;
import com.jzwgj.management.server.web.classifcation.dao.ClassificationDao;
import com.jzwgj.management.server.web.classifcation.domain.Classification;
@Repository
public class ClassificationDaoImpl  extends BaseDaoImpl<Long, Classification>  implements ClassificationDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public SqlSession getSqlSession() {
		return sqlSession;
	}

	@Override
	public Class<?> getMapperClass() {
		return ClassificationMapper.class;
	}
	

	@Override
	public List<Classification> findClassificationByPid(Long pid,Integer level) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pid", pid);
		map.put("level", level);
		return sqlSession.getMapper(ClassificationMapper.class).selectByPid(pid);
	}

	@Override
	public Integer selectChildCount(Long id) {
		return sqlSession.getMapper(ClassificationMapper.class).selectChildCount(id);
	}

	@Override
	public List<Classification> selectTreeList(String parentId, String category) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentId", parentId);
		map.put("category", category);
		return sqlSession.getMapper(ClassificationMapper.class).selectTreeList(map);
	}

	@Override
	public Classification selectById(Long id) {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(ClassificationMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void publish(Classification classification) {
		sqlSession.getMapper(ClassificationMapper.class).updateByPrimaryKeySelective(classification);
	}
}
