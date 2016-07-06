package com.jzwgj.management.server.web.user.dao;

import java.util.List;
import java.util.Map;

import com.jzwgj.management.exception.ShuaLianUserException;
import com.jzwgj.management.server.web.user.domain.ShuaLianUser;

public interface ShuaLianUserDao {
	
	ShuaLianUser getByPrimaryKey(Long id) throws ShuaLianUserException;
	
	int getCount(ShuaLianUser shuaLianUser) throws ShuaLianUserException;

	List<ShuaLianUser> findShuaLianUserPageList(Map<String, Object> map) throws ShuaLianUserException;
	
	/**
	 * 查询用户店铺数
	 * @param userId	用户Id
	 * @return
	 */
	Integer getUserShopNum(Long userId);
	
	/**
	 * 查询用户邀请用户数
	 * @param userId	用户Id
	 * @return
	 */
	Integer getUserInvitationNum(Long userId);
	
	/**
	 * 查询总用户数
	 * @return
	 */
	Integer getUserSum();
	
	/**
	 * 查询今日新增用户数
	 * @return
	 */
	Integer getTodayAddUserNum();
	
	/**
	 * 查询用户会员卡信息
	 * @param userId
	 * @return
	 */
	ShuaLianUser getUserMemberCard(Long userId) throws ShuaLianUserException;
	
	/**
	 * 根据刷脸号查询用户
	 * @param map
	 * @return
	 */
	Integer getUserBylpNo(Map<String,Object> map);
	
	/**
	 * 查询用户名片信息
	 * @param userId
	 * @return
	 */
	ShuaLianUser getUserBusinessCard(Long userId) throws ShuaLianUserException;
	
	/**
	 * 查询用用户二度邀请用户数
	 * @param userId
	 * @return
	 */
	Integer getUserBisInvitationNum(Long userId) throws ShuaLianUserException;
	
	/**
	 * 修改用户信息数据
	 * @param shuaLianUser
	 */
	void updateUser(ShuaLianUser shuaLianUser) throws ShuaLianUserException;
	
	/**
	 * 修改用户会员卡数据
	 * @param shuaLianUser
	 */
	void updateUserMemberCard(ShuaLianUser shuaLianUser) throws ShuaLianUserException;
	
	/**
	 * 修改用户名片数据
	 * @param shuaLianUser
	 */
	void updateUserBusinessCard(ShuaLianUser shuaLianUser) throws ShuaLianUserException;

	/**
	 * 新增用户名片
	 * @param shuaLianUser
	 */
	void addUserBusinessCard(ShuaLianUser shuaLianUser);
}
