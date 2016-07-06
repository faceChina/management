package com.jzwgj.management.server.web.user.domain;

import java.util.List;

import com.zjlp.face.account.domain.Account;
import com.zjlp.face.web.server.operation.member.domain.MemberCard;
import com.zjlp.face.web.server.user.bankcard.domain.BankCard;
import com.zjlp.face.web.server.user.businesscard.domain.BusinessCard;
import com.zjlp.face.web.server.user.user.domain.User;


public class ShuaLianUser {
	
	private User user = new User();
	
	private MemberCard memberCard = new MemberCard();
	
	private BusinessCard businessCard = new BusinessCard();
	
	private Account account = new Account();
	
	private List<BankCard> bankCardList;
	
	
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 拥有店铺数量
	 */
	private Integer shopNum;
	
	/**
	 * 邀请用户数量
	 */
	private Integer invitationNum;
	
	/**
	 * 二度邀请用户数量
	 */
	private Integer bisInvitationNum;
	
	/**
	 * 查询注册时间,开始
	 */
	private String createTimeStart;
	/**
	 * 查询注册时间,结束
	 */
	private String createTimeEnd;
	
	/**
	 * 账号状态
	 */
	private String status;
	
	/**
	 * 历史总颜值
	 */
	private Integer totalSlcoin;
	
	/**
	 * 银行卡总数
	 */
	private Integer bankcardNumber;


	public List<BankCard> getBankCardList() {
		return bankCardList;
	}
	public void setBankCardList(List<BankCard> bankCardList) {
		this.bankCardList = bankCardList;
	}
	
	public Integer getBankcardNumber() {
		return bankcardNumber;
	}
	public void setBankcardNumber(Integer bankcardNumber) {
		this.bankcardNumber = bankcardNumber;
	}
	public Integer getTotalSlcoin() {
		return totalSlcoin;
	}
	public void setTotalSlcoin(Integer totalSlcoin) {
		this.totalSlcoin = totalSlcoin;
	}

	public String getStatus() {
		if (user.getStatus().equals(-1)) {
			status = "删除";
		}
		if (user.getStatus().equals(0)) {
			status = "冻结";
		}
		if (user.getStatus().equals(1)) {
			status = "正常";
		}
		return status;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public MemberCard getMemberCard() {
		return memberCard;
	}
	public void setMemberCard(MemberCard memberCard) {
		this.memberCard = memberCard;
	}
	public Integer getShopNum() {
		return shopNum;
	}
	public void setShopNum(Integer shopNum) {
		this.shopNum = shopNum;
	}
	public Integer getInvitationNum() {
		return invitationNum;
	}
	public void setInvitationNum(Integer invitationNum) {
		this.invitationNum = invitationNum;
	}
	public String getCreateTimeStart() {
		return createTimeStart;
	}
	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}
	public String getCreateTimeEnd() {
		return createTimeEnd;
	}
	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
	public BusinessCard getBusinessCard() {
		return businessCard;
	}
	public void setBusinessCard(BusinessCard businessCard) {
		this.businessCard = businessCard;
	}
	public Integer getBisInvitationNum() {
		return bisInvitationNum;
	}
	public void setBisInvitationNum(Integer bisInvitationNum) {
		this.bisInvitationNum = bisInvitationNum;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
}
