package com.jzwgj.management.server.web.information.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jzwgj.management.exception.GwInformationException;
import com.jzwgj.management.mapper.GwInformationMapper;
import com.jzwgj.management.server.web.information.dao.GwInformationDao;
import com.jzwgj.management.server.web.information.domain.GwInformation;

@Repository
public class GwInformationDaoImpl implements GwInformationDao {
	@Autowired
	private SqlSession sqlSession;

	@Override
	public Long save(GwInformation gwInformation) throws GwInformationException{
		 sqlSession.getMapper(GwInformationMapper.class).insert(gwInformation);
		 return gwInformation.getId();
	}

	@Override
	public GwInformation getByPrimaryKey(Long id) {
		return sqlSession.getMapper(GwInformationMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void update(GwInformation gwInformation) throws GwInformationException{
		sqlSession.getMapper(GwInformationMapper.class).updateByPrimaryKeySelective(gwInformation);
	}
	
	@Override
	public void deleteById(Long id) {
//		sqlSession.getMapper(GwInformationMapper.class).deleteByPrimaryKey(id);
		sqlSession.getMapper(GwInformationMapper.class).delete(id);
	}

	@Override
	public int getCount(GwInformation gwInformation) {
		return sqlSession.getMapper(GwInformationMapper.class).getCount(gwInformation);
	}

	@Override
	public List<GwInformation> findGwInformationPageList(Map<String, Object> map) {
		return sqlSession.getMapper(GwInformationMapper.class).findGwInformationPageList(map);
	}

	@Override
	public Long getMaxSort() {
		return sqlSession.getMapper(GwInformationMapper.class).getMaxSort();
	}

	@Override
	public Long getMinSort() {
		return sqlSession.getMapper(GwInformationMapper.class).getMinSort();
	}
}
