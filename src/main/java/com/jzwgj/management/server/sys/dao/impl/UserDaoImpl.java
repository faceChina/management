package com.jzwgj.management.server.sys.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jzwgj.management.mapper.CUserMapper;
import com.jzwgj.management.server.sys.dao.UserDao;
import com.jzwgj.management.server.sys.domain.CUser;
@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private SqlSession sqlSession;

	public void add(CUser cUser) {
		sqlSession.getMapper(CUserMapper.class).insertSelective(cUser);

	}

	public void edit(CUser cUser) {
		sqlSession.getMapper(CUserMapper.class).updateByPrimaryKeySelective(cUser);
	}

	public CUser getById(Long id) {
		return sqlSession.getMapper(CUserMapper.class).selectByPrimaryKey(id);
	}

	public List<CUser> findList(CUser cUser) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(Long id) {
		sqlSession.getMapper(CUserMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public CUser getUserByNameAndPwd(String loginName, String passWrod) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("loginName", loginName);
		map.put("passWrod", passWrod);
		return sqlSession.getMapper(CUserMapper.class).getUserByNameAndPwd(map);
	}

}
