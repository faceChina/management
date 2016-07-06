package com.jzwgj.management.server.web.activity.domain;

import java.util.Date;

public class SlcoinInvitationRegister/* implements Comparable<SlcoinInvitationRegister>*/{
	
	/**
	 * 账户类型:全部
	 */
	public static final Integer LOGIN_ACCOUNT_TYPE_ALL = 0;
	/**
	 * 账户类型:注册账号
	 */
	public static final Integer LOGIN_ACCOUNT_TYPE_REGISTER = 1;
	/**
	 * 账户类型:邀请账号
	 */
	public static final Integer LOGIN_ACCOUNT_TYPE_INVITE = 2;
	/**
	 * 账户类型:上级邀请账号
	 */
	public static final Integer LOGIN_ACCOUNT_TYPE_SUPER = 3;
	
	
    private Date createTime;

    private String nickName;
    
    private String loginAccount;
    
    private Integer amount;
    

    private String invitationNickName;
    
    private String invitationLoginAccount;
    
    private Integer invitationAmount;
    

    private String superNickName;
    
    private String superLoginAccount;
    
    private Integer superAmount;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getInvitationNickName() {
		return invitationNickName;
	}

	public void setInvitationNickName(String invitationNickName) {
		this.invitationNickName = invitationNickName;
	}

	public String getInvitationLoginAccount() {
		return invitationLoginAccount;
	}

	public void setInvitationLoginAccount(String invitationLoginAccount) {
		this.invitationLoginAccount = invitationLoginAccount;
	}

	public Integer getInvitationAmount() {
		return invitationAmount;
	}

	public void setInvitationAmount(Integer invitationAmount) {
		this.invitationAmount = invitationAmount;
	}

	public String getSuperNickName() {
		return superNickName;
	}

	public void setSuperNickName(String superNickName) {
		this.superNickName = superNickName;
	}

	public String getSuperLoginAccount() {
		return superLoginAccount;
	}

	public void setSuperLoginAccount(String superLoginAccount) {
		this.superLoginAccount = superLoginAccount;
	}

	public Integer getSuperAmount() {
		return superAmount;
	}

	public void setSuperAmount(Integer superAmount) {
		this.superAmount = superAmount;
	}
	


//	public int hashCode() {
//		return (amount==null?0:amount.hashCode()) + (nickName==null?0:nickName.hashCode()) + (loginAccount==null?0:loginAccount.hashCode()) + 
//			(invitationLoginAccount==null?0:invitationLoginAccount.hashCode()) + (invitationNickName==null?0:invitationNickName.hashCode()) + (invitationAmount==null?0:invitationAmount.hashCode()) + 
//			(superLoginAccount==null?0:superLoginAccount.hashCode()) + (superNickName==null?0:superNickName.hashCode()) + (superAmount==null?0:superAmount.hashCode());
//	}
//
//	public boolean equals(Object obj) {
//		if(obj == null) return false;
//		SlcoinInvitationRegister slcoinInvitationRegister = (SlcoinInvitationRegister)obj;
//		
//		if(amount==null?true:amount.equals(slcoinInvitationRegister.getAmount()) && nickName==null?true:nickName.equals(slcoinInvitationRegister.getNickName()) && loginAccount==null?true:loginAccount.equals(slcoinInvitationRegister.getLoginAccount())
//			&& invitationAmount==null?true:invitationAmount.equals(slcoinInvitationRegister.getInvitationAmount()) && invitationLoginAccount==null?true:invitationLoginAccount.equals(slcoinInvitationRegister.getInvitationLoginAccount()) && invitationNickName==null?true:invitationNickName.equals(slcoinInvitationRegister.getInvitationNickName())
//			&& superAmount==null?true:superAmount.equals(slcoinInvitationRegister.getSuperAmount()) && superLoginAccount==null?true:superLoginAccount.equals(slcoinInvitationRegister.getSuperLoginAccount()) && superNickName==null?true:superNickName.equals(slcoinInvitationRegister.getSuperNickName())){
//			return true;
//		}
//		return false;
//	}
//
//	public int compareTo(SlcoinInvitationRegister slcoinInvitationRegister) {
//		if(slcoinInvitationRegister!=null && this.createTime!=null && slcoinInvitationRegister.getCreateTime().after(this.createTime)){
//			return 1;
//		}
//		return -1;
//	}
}