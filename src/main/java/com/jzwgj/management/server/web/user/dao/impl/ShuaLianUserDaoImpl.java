package com.jzwgj.management.server.web.user.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jzwgj.management.exception.ShuaLianUserException;
import com.jzwgj.management.mapper.ShuaLianUserMapper;
import com.jzwgj.management.server.web.user.dao.ShuaLianUserDao;
import com.jzwgj.management.server.web.user.domain.ShuaLianUser;

@Repository
public class ShuaLianUserDaoImpl implements ShuaLianUserDao {
	
	private Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public ShuaLianUser getByPrimaryKey(Long id) throws ShuaLianUserException {
		return sqlSession.getMapper(ShuaLianUserMapper.class).getByPrimaryKey(id);
	}

	@Override
	public int getCount(ShuaLianUser shuaLianUser) throws ShuaLianUserException {
		return sqlSession.getMapper(ShuaLianUserMapper.class).getCount(shuaLianUser);
	}

	@Override
	public List<ShuaLianUser> findShuaLianUserPageList(Map<String, Object> map) throws ShuaLianUserException {
		return sqlSession.getMapper(ShuaLianUserMapper.class).findShuaLianUserPageList(map);
	}

	@Override
	public Integer getUserShopNum(Long userId) {
		return sqlSession.getMapper(ShuaLianUserMapper.class).getUserShopNum(userId);
	}

	@Override
	public Integer getUserInvitationNum(Long userId) {
		return sqlSession.getMapper(ShuaLianUserMapper.class).getUserInvitationNum(userId);
	}

	@Override
	public Integer getUserSum() {
		return sqlSession.getMapper(ShuaLianUserMapper.class).getUserSum();
	}

	@Override
	public Integer getTodayAddUserNum() {
		return sqlSession.getMapper(ShuaLianUserMapper.class).getTodayAddUserNum();
	}

	@Override
	public ShuaLianUser getUserMemberCard(Long userId) throws ShuaLianUserException {
		return sqlSession.getMapper(ShuaLianUserMapper.class).getUserMemberCard(userId);
	}

	@Override
	public Integer getUserBylpNo(Map<String,Object> map) {
		return sqlSession.getMapper(ShuaLianUserMapper.class).getUserBylpNo(map);
	}

	@Override
	public ShuaLianUser getUserBusinessCard(Long userId) throws ShuaLianUserException {
		return sqlSession.getMapper(ShuaLianUserMapper.class).getUserBusinessCard(userId);
	}

	@Override
	public Integer getUserBisInvitationNum(Long userId) throws ShuaLianUserException {
		return sqlSession.getMapper(ShuaLianUserMapper.class).getUserBisInvitationNum(userId);
	}

	@Override
	public void updateUser(ShuaLianUser shuaLianUser) throws ShuaLianUserException {
//		try {
//			Long userId = shuaLianUser.getUserId();
//			ShuaLianUser oldUser = this.getByPrimaryKey(userId);
//			if(User.IS_UPDATE.equals(oldUser.getUser().getIsUpdateCode())){
//				shuaLianUser.getUser().setMyInvitationCode(null);
//			}else if(oldUser.getUser().getMyInvitationCode().equals(shuaLianUser.getUser().getMyInvitationCode())){
//				shuaLianUser.getUser().setMyInvitationCode(null);
//			}else{
//				shuaLianUser.getUser().setIsUpdateCode(User.IS_UPDATE);
//			}
//			
//			sqlSession.getMapper(ShuaLianUserMapper.class).updateUser(shuaLianUser);
//		} catch (Exception e) {
//			log.error("修改用户信息数据出错", e);
//			throw new ShuaLianUserException("修改用户信息数据出错:" + e.getMessage(),e);
//		}
	}

	@Override
	public void updateUserMemberCard(ShuaLianUser shuaLianUser) throws ShuaLianUserException {
		try {
			sqlSession.getMapper(ShuaLianUserMapper.class).updateUserMemberCard(shuaLianUser);
		} catch (Exception e) {
			log.error("修改用户会员卡数据出错", e);
			throw new ShuaLianUserException("修改用户会员卡数据出错:" + e.getMessage(),e);
		}
	}

	@Override
	public void updateUserBusinessCard(ShuaLianUser shuaLianUser) throws ShuaLianUserException {
		try {
			sqlSession.getMapper(ShuaLianUserMapper.class).updateUserBusinessCard(shuaLianUser);
		} catch (Exception e) {
			log.error("修改用户名片数据出错", e);
			throw new ShuaLianUserException("修改用户名片数据出错:" + e.getMessage(),e);
		}
	}

	@Override
	public void addUserBusinessCard(ShuaLianUser shuaLianUser) {
		sqlSession.getMapper(ShuaLianUserMapper.class).addUserBusinessCard(shuaLianUser);
	}

}
