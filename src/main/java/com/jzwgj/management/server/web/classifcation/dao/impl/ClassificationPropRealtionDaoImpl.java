package com.jzwgj.management.server.web.classifcation.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jzwgj.management.component.base.BaseDaoImpl;
import com.jzwgj.management.mapper.ClassificationPropRealtionMapper;
import com.jzwgj.management.server.web.classifcation.dao.ClassificationPropRealtionDao;
import com.jzwgj.management.server.web.classifcation.domain.ClassificationPropRealtion;
import com.jzwgj.management.server.web.classifcation.domain.vo.ClassificationPropRealtionVo;
@Repository
public class ClassificationPropRealtionDaoImpl extends BaseDaoImpl<Long, ClassificationPropRealtion>  
	implements ClassificationPropRealtionDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public SqlSession getSqlSession() {
		return sqlSession;
	}

	@Override
	public Class<?> getMapperClass() {
		return ClassificationPropRealtionMapper.class;
	}

	@Override
	public Integer selectCount(ClassificationPropRealtionVo classificationPropRealtionVo) {
		Map<String,Object> map = new HashMap<String,Object>(1);
		map.put("vo", classificationPropRealtionVo);
		return sqlSession.getMapper(ClassificationPropRealtionMapper.class).selectCount(map);
	}
	
	@Override
	public List<ClassificationPropRealtionVo> selectPageList(
			ClassificationPropRealtionVo classificationPropRealtionVo,
			int start, int pageSize) {
		Map<String,Object> map = new HashMap<String,Object>(3);
		map.put("vo", classificationPropRealtionVo);
		map.put("start", start);
		map.put("pageSize", pageSize);
		return sqlSession.getMapper(ClassificationPropRealtionMapper.class).selectPageList(map);
	}

	@Override
	public Integer selectSalesCount(Long classificationId) {
		return sqlSession.getMapper(ClassificationPropRealtionMapper.class).selectSalesCount(classificationId);
	}

	@Override
	public void deleteByClassificationId(Long classificationId) {
		sqlSession.getMapper(ClassificationPropRealtionMapper.class).deleteByClassificationId(classificationId);
	}
	
	@Override
	public List<ClassificationPropRealtionVo> selectList(ClassificationPropRealtionVo classificationPropRealtionVo) {
		Map<String,Object> map = new HashMap<String,Object>(1);
		map.put("vo", classificationPropRealtionVo);
		return sqlSession.getMapper(ClassificationPropRealtionMapper.class).selectList(map);
	}

	@Override
	public void deleteByClassificationIdPropId(Long classificationId,Long propId) {
		Map<String,Object> map = new HashMap<String,Object>(2);
		map.put("classificationId", classificationId);
		map.put("propId", propId);
		sqlSession.getMapper(ClassificationPropRealtionMapper.class).deleteByClassificationIdPropId(map);
	}
	
	@Override
	public Integer selectMaxSort(ClassificationPropRealtionVo classificationPropRealtionVo) {
		Map<String,Object> map = new HashMap<String,Object>(1);
		map.put("vo", classificationPropRealtionVo);
		return sqlSession.getMapper(ClassificationPropRealtionMapper.class).selectMaxSort(map);
	}
}
