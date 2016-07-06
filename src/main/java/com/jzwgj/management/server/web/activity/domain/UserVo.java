package com.jzwgj.management.server.web.activity.domain;

import java.util.Date;

public class UserVo {

	private Long id;
	private String nickName;
	private String loginAccount;
	private Date createTime;
	private Long invitedUserId;
	private Integer type;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getLoginAccount() {
		return loginAccount;
	}
	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getInvitedUserId() {
		return invitedUserId;
	}
	public void setInvitedUserId(Long invitedUserId) {
		this.invitedUserId = invitedUserId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}
