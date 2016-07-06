package com.jzwgj.management.mapper;

import java.util.Map;

import com.jzwgj.management.server.sys.domain.CUser;

public interface CUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CUser record);

    int insertSelective(CUser record);

    CUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CUser record);

    int updateByPrimaryKey(CUser record);

	CUser getUserByNameAndPwd(Map<String, Object> map);
}