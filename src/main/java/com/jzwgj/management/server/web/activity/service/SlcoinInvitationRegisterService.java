package com.jzwgj.management.server.web.activity.service;

import java.util.List;

import com.jzwgj.management.exception.SlcoinActivityException;
import com.jzwgj.management.server.web.activity.domain.SlcoinInvitationRegister;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.user.user.domain.vo.UserVoObj;

public interface SlcoinInvitationRegisterService {

	Pagination<SlcoinInvitationRegister> findSlcoinInvitationRegisterPageList(UserVoObj userVoObj, Pagination<SlcoinInvitationRegister> pagination,Integer loginAccountType) throws SlcoinActivityException;

	List<SlcoinInvitationRegister> findSlcoinInvitationRegisterList(UserVoObj userVoObj, Integer loginAccountType) throws SlcoinActivityException;
}
