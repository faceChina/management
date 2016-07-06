package com.jzwgj.management.server.web.activity.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jzwgj.management.exception.SlcoinActivityException;
import com.jzwgj.management.server.web.activity.dao.SlcoinRecordDao;
import com.jzwgj.management.server.web.activity.domain.SlcoinInvitationRegister;
import com.jzwgj.management.server.web.activity.domain.SlcoinRecord;
import com.jzwgj.management.server.web.activity.domain.SlcoinRecordVo;
import com.jzwgj.management.server.web.activity.domain.UserVo;
import com.jzwgj.management.server.web.activity.service.SlcoinInvitationRegisterService;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.domain.vo.UserVoObj;

@Service("slcoinInvitationRegisterService")
public class SlcoinInvitationRegisterServiceImpl implements SlcoinInvitationRegisterService{
	Logger _logger = Logger.getLogger(super.getClass());

	@Autowired
	private SlcoinRecordDao slcoinRecordDao;

	@Autowired
	private UserBusiness userBusiness;

	public Pagination<SlcoinInvitationRegister> findSlcoinInvitationRegisterPageList(UserVoObj userVoObj, Pagination<SlcoinInvitationRegister> pagination, Integer loginAccountType) throws SlcoinActivityException{
		try{
			Map<String,Object> map = new HashMap<String,Object>();

			map.put("user", userVoObj);
			map.put("loginAccountType", loginAccountType);

			Integer count = this.slcoinRecordDao.getUserCount(map);

			map.put("start", Integer.valueOf(pagination.getStart()));
			map.put("pageSize", Integer.valueOf(pagination.getPageSize()));

			List<SlcoinInvitationRegister> list = new ArrayList<SlcoinInvitationRegister>();

			List<UserVo> userList = slcoinRecordDao.findUserPageList(map);
			for (UserVo user : userList){ 
				SlcoinInvitationRegister slcoinInvitationRegister = null;

				if (SlcoinInvitationRegister.LOGIN_ACCOUNT_TYPE_ALL.equals(loginAccountType)) {
					Integer type = user.getType();

					if (SlcoinRecord.TYPE_REGISTER.equals(type)){
						slcoinInvitationRegister = getSlcoinRecordRegister(user);
					}else if (SlcoinRecord.TYPE_INVITE.equals(type)){
						slcoinInvitationRegister = getSlcoinRecordInvite(user);
					}else if (SlcoinRecord.TYPE_SUPER_INVITE.equals(type)){
						slcoinInvitationRegister = getSlcoinRecordSuper(user);
					}else{
						AssertUtil.isTrue(false, "出现脏数据");
					}
				}else if (SlcoinInvitationRegister.LOGIN_ACCOUNT_TYPE_REGISTER.equals(loginAccountType)) {
					slcoinInvitationRegister = getSlcoinRecordRegister(user);
				} else if (SlcoinInvitationRegister.LOGIN_ACCOUNT_TYPE_INVITE.equals(loginAccountType)) {
					slcoinInvitationRegister = getSlcoinRecordInvite(user);
				} else if (SlcoinInvitationRegister.LOGIN_ACCOUNT_TYPE_SUPER.equals(loginAccountType)) {
					slcoinInvitationRegister = getSlcoinRecordSuper(user);
				} else {
					slcoinInvitationRegister = getSlcoinRecordRegister(user);
				}

				if (slcoinInvitationRegister != null){
					list.add(slcoinInvitationRegister);
				}
			}

			//List<SlcoinInvitationRegister> list2 = new ArrayList<SlcoinInvitationRegister>(new HashSet<SlcoinInvitationRegister>(list));

			//Collections.sort(list2);
			pagination.setTotalRow(count.intValue());
			pagination.setDatas(list);

			return pagination;
		} catch (Exception e) {
			_logger.error("分页查询邀请注册列表出错", e);
			throw new SlcoinActivityException(e.getMessage(), e);
		}
	}

	@Override
	public List<SlcoinInvitationRegister> findSlcoinInvitationRegisterList(UserVoObj userVoObj, Integer loginAccountType) throws SlcoinActivityException {
		try {
			Map<String,Object> map = new HashMap<String,Object>();

			map.put("user", userVoObj);
			map.put("loginAccountType", loginAccountType);

			List<SlcoinInvitationRegister> list = new ArrayList<SlcoinInvitationRegister>();

			List<UserVo> userList = slcoinRecordDao.findUserPageList(map);
			for (UserVo user : userList){ 
				SlcoinInvitationRegister slcoinInvitationRegister = null;

				if (SlcoinInvitationRegister.LOGIN_ACCOUNT_TYPE_ALL.equals(loginAccountType)) {
					Integer type = user.getType();

					if (SlcoinRecord.TYPE_REGISTER.equals(type)){
						slcoinInvitationRegister = getSlcoinRecordRegister(user);
					}else if (SlcoinRecord.TYPE_INVITE.equals(type)){
						slcoinInvitationRegister = getSlcoinRecordInvite(user);
					}else if (SlcoinRecord.TYPE_SUPER_INVITE.equals(type)){
						slcoinInvitationRegister = getSlcoinRecordSuper(user);
					}else{
						AssertUtil.isTrue(false, "出现脏数据");
					}
				}else if (SlcoinInvitationRegister.LOGIN_ACCOUNT_TYPE_REGISTER.equals(loginAccountType)) {
					slcoinInvitationRegister = getSlcoinRecordRegister(user);
				} else if (SlcoinInvitationRegister.LOGIN_ACCOUNT_TYPE_INVITE.equals(loginAccountType)) {
					slcoinInvitationRegister = getSlcoinRecordInvite(user);
				} else if (SlcoinInvitationRegister.LOGIN_ACCOUNT_TYPE_SUPER.equals(loginAccountType)) {
					slcoinInvitationRegister = getSlcoinRecordSuper(user);
				} else {
					slcoinInvitationRegister = getSlcoinRecordRegister(user);
				}

				if (slcoinInvitationRegister != null){
					list.add(slcoinInvitationRegister);
				}
			}

			//List<SlcoinInvitationRegister> list2 = new ArrayList<SlcoinInvitationRegister>(new HashSet<SlcoinInvitationRegister>(list));

			//Collections.sort(list2);

			return list;
		} catch (Exception e) {
			_logger.error("查询邀请注册列表出错", e);
			throw new SlcoinActivityException(e.getMessage(), e);
		}
	}

	private SlcoinInvitationRegister getSlcoinRecordRegister(UserVo user){
		SlcoinInvitationRegister slcoinInvitationRegister = null;
		Long userId = user.getId();

		Map<String,Object> map = new HashMap<String,Object>();
	    SlcoinRecordVo slcoinRecord = new SlcoinRecordVo();
	    slcoinRecord.setUserId(userId);
	    slcoinRecord.setType(SlcoinRecord.TYPE_REGISTER);
	    map.put("slcoinRecord", slcoinRecord);
	    SlcoinRecord slcoinRecordRegister = this.slcoinRecordDao.getSlcoinRecord(map);
	    if (slcoinRecordRegister != null) {
	      slcoinInvitationRegister = new SlcoinInvitationRegister();
	      slcoinInvitationRegister.setAmount(slcoinRecordRegister.getAmount());
	      slcoinInvitationRegister.setNickName(user.getNickName());
	      slcoinInvitationRegister.setLoginAccount(user.getLoginAccount());
	      slcoinInvitationRegister.setCreateTime(user.getCreateTime());
	
	      slcoinRecord.setUserId(null);
	      slcoinRecord.setInvitedUserId(userId);
	      slcoinRecord.setType(SlcoinRecord.TYPE_INVITE);
	      SlcoinRecord slcoinRecordInvite = this.slcoinRecordDao.getSlcoinRecord(map);
	      if (slcoinRecordInvite != null) {
	    	  User userInvite = this.userBusiness.getUserById(slcoinRecordInvite.getUserId());
	    	  if (userInvite != null) {
	    		  slcoinInvitationRegister.setInvitationAmount(slcoinRecordInvite.getAmount());
		          slcoinInvitationRegister.setInvitationLoginAccount(userInvite.getLoginAccount());
		          slcoinInvitationRegister.setInvitationNickName(userInvite.getNickname());
	    	  }
	
	    	  slcoinRecord.setInvitedUserId(userId);
	    	  slcoinRecord.setType(SlcoinRecord.TYPE_SUPER_INVITE);
	    	  SlcoinRecord slcoinRecordSuperInvite = this.slcoinRecordDao.getSlcoinRecord(map);
	    	  if (slcoinRecordSuperInvite != null) {
	    		  User userSuperInvite = this.userBusiness.getUserById(slcoinRecordSuperInvite.getUserId());
	    		  if (userSuperInvite != null) {
	    			  slcoinInvitationRegister.setSuperAmount(slcoinRecordSuperInvite.getAmount());
	    			  slcoinInvitationRegister.setSuperLoginAccount(userSuperInvite.getLoginAccount());
	    			  slcoinInvitationRegister.setSuperNickName(userSuperInvite.getNickname());
	    		  }
	    	  }
	      	}
    	}

	    return slcoinInvitationRegister;
	}

	private SlcoinInvitationRegister getSlcoinRecordInvite(UserVo user) {
		SlcoinInvitationRegister slcoinInvitationRegister = null;
		Long userId = user.getId();

		Map<String,Object> map = new HashMap<String,Object>();
	    SlcoinRecordVo slcoinRecord = new SlcoinRecordVo();
	    slcoinRecord.setUserId(userId);
	    slcoinRecord.setInvitedUserId(user.getInvitedUserId());
	    slcoinRecord.setType(SlcoinRecord.TYPE_INVITE);
	    map.put("slcoinRecord", slcoinRecord);
	    SlcoinRecord slcoinRecordInvite = this.slcoinRecordDao.getSlcoinRecord(map);
	    if (slcoinRecordInvite != null) {
	    	slcoinInvitationRegister = new SlcoinInvitationRegister();
	    	slcoinInvitationRegister.setInvitationAmount(slcoinRecordInvite.getAmount());
	    	slcoinInvitationRegister.setInvitationNickName(user.getNickName());
	    	slcoinInvitationRegister.setInvitationLoginAccount(user.getLoginAccount());

	    	slcoinRecord.setUserId(slcoinRecordInvite.getInvitedUserId());
	    	slcoinRecord.setInvitedUserId(null);
	    	slcoinRecord.setType(SlcoinRecord.TYPE_REGISTER);
	    	SlcoinRecord slcoinRecordRegister = this.slcoinRecordDao.getSlcoinRecord(map);
	    	if (slcoinRecordRegister != null) {
	    		User userRegister = this.userBusiness.getUserById(slcoinRecordRegister.getUserId());
	    		if (userRegister != null) {
	    			slcoinInvitationRegister.setAmount(slcoinRecordRegister.getAmount());
	    			slcoinInvitationRegister.setLoginAccount(userRegister.getLoginAccount());
	    			slcoinInvitationRegister.setNickName(userRegister.getNickname());
	    			slcoinInvitationRegister.setCreateTime(userRegister.getCreateTime());
	    		}

	    		slcoinRecord.setUserId(null);
	    		slcoinRecord.setInvitedUserId(slcoinRecordInvite.getInvitedUserId());
	    		slcoinRecord.setType(SlcoinRecord.TYPE_SUPER_INVITE);
	    		SlcoinRecord slcoinRecordSuperInvite = this.slcoinRecordDao.getSlcoinRecord(map);
	    		if (slcoinRecordSuperInvite != null) {
	    			User userSuperInvite = this.userBusiness.getUserById(slcoinRecordSuperInvite.getUserId());
	    			if (userSuperInvite != null) {
	    				slcoinInvitationRegister.setSuperAmount(slcoinRecordSuperInvite.getAmount());
	    				slcoinInvitationRegister.setSuperLoginAccount(userSuperInvite.getLoginAccount());
	    				slcoinInvitationRegister.setSuperNickName(userSuperInvite.getNickname());
	    			}
	    		}
	    	}
	    }

	    return slcoinInvitationRegister;
	}

	private SlcoinInvitationRegister getSlcoinRecordSuper(UserVo user) {
		SlcoinInvitationRegister slcoinInvitationRegister = null;
		Long userId = user.getId();

		Map<String,Object> map = new HashMap<String,Object>();
		SlcoinRecordVo slcoinRecord = new SlcoinRecordVo();
		slcoinRecord.setUserId(userId);
		slcoinRecord.setInvitedUserId(user.getInvitedUserId());
		slcoinRecord.setType(SlcoinRecord.TYPE_SUPER_INVITE);
		map.put("slcoinRecord", slcoinRecord);
		SlcoinRecord slcoinRecordSuper = this.slcoinRecordDao.getSlcoinRecord(map);
		if (slcoinRecordSuper != null) {
			slcoinInvitationRegister = new SlcoinInvitationRegister();
			slcoinInvitationRegister.setSuperAmount(slcoinRecordSuper.getAmount());
			slcoinInvitationRegister.setSuperNickName(user.getNickName());
			slcoinInvitationRegister.setSuperLoginAccount(user.getLoginAccount());

			slcoinRecord.setUserId(slcoinRecordSuper.getInvitedUserId());
			slcoinRecord.setInvitedUserId(null);
			slcoinRecord.setType(SlcoinRecord.TYPE_REGISTER);
			SlcoinRecord slcoinRecordRegister = this.slcoinRecordDao.getSlcoinRecord(map);
			if (slcoinRecordRegister != null) {
				User userRegister = this.userBusiness.getUserById(slcoinRecordRegister.getUserId());
				if (userRegister != null) {
					slcoinInvitationRegister.setAmount(slcoinRecordRegister.getAmount());
					slcoinInvitationRegister.setLoginAccount(userRegister.getLoginAccount());
					slcoinInvitationRegister.setNickName(userRegister.getNickname());
					slcoinInvitationRegister.setCreateTime(userRegister.getCreateTime());
				}

				slcoinRecord.setUserId(null);
				slcoinRecord.setInvitedUserId(slcoinRecordSuper.getInvitedUserId());
				slcoinRecord.setType(SlcoinRecord.TYPE_INVITE);
				SlcoinRecord slcoinRecordInvite = this.slcoinRecordDao.getSlcoinRecord(map);
				if (slcoinRecordInvite != null) {
					User userInvite = this.userBusiness.getUserById(slcoinRecordInvite.getUserId());
					if (userInvite != null) {
						slcoinInvitationRegister.setInvitationAmount(slcoinRecordInvite.getAmount());
						slcoinInvitationRegister.setInvitationLoginAccount(userInvite.getLoginAccount());
						slcoinInvitationRegister.setInvitationNickName(userInvite.getNickname());
					}
				}
			}
		}

		return slcoinInvitationRegister;
	}
}