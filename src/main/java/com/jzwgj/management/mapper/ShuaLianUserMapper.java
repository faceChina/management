package com.jzwgj.management.mapper;

import java.util.List;
import java.util.Map;

import com.jzwgj.management.server.web.user.domain.ShuaLianUser;

public interface ShuaLianUserMapper {

	ShuaLianUser getByPrimaryKey(Long id);

	int getCount(ShuaLianUser shuaLianUser);

	List<ShuaLianUser> findShuaLianUserPageList(Map<String, Object> map);

	Integer getUserShopNum(Long userId);

	Integer getUserInvitationNum(Long userId);

	Integer getUserSum();

	Integer getTodayAddUserNum();

	ShuaLianUser getUserMemberCard(Long userId);

	Integer getUserBylpNo(Map<String, Object> map);

	ShuaLianUser getUserBusinessCard(Long userId);

	Integer getUserBisInvitationNum(Long userId);

	void updateUser(ShuaLianUser shuaLianUser);

	void updateUserMemberCard(ShuaLianUser shuaLianUser);

	void updateUserBusinessCard(ShuaLianUser shuaLianUser);

	void addUserBusinessCard(ShuaLianUser shuaLianUser);

}
