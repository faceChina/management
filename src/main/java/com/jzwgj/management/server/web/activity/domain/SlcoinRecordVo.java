package com.jzwgj.management.server.web.activity.domain;

import java.util.Date;

public class SlcoinRecordVo extends SlcoinRecord {

	private String beginTime;
	
	private String endTime;
	
	private Date beginDate;
	
	private Date endDate;
	
	private Integer giveTotal;
	
	private Integer useTotal;
	
	private String nickName;
	
	private String typeStr;
	
	
	public String getTypeStr() {
		Integer types = super.getType();
		if (types.equals(SlcoinRecord.TYPE_REGISTER) || types.equals(SlcoinRecord.TYPE_INVITE) ||types.equals(SlcoinRecord.TYPE_SUPER_INVITE)) {
			typeStr = "系统赠送";
		}
		if (types.equals(SlcoinRecord.TYPE_CONSUME)) {
			typeStr = "消费";
		}
		if (types.equals(SlcoinRecord.TYPE_CANCEL)) {
			typeStr = "退回";
		}
		if (types.equals(SlcoinRecord.TYPE_ADMIN_GIVE)) {
			typeStr = "admin赠送";
		}
		if (types.equals(SlcoinRecord.TYPE_ACTIVITY_GIVE)) {
			typeStr = "活动赠送";
		}
		return typeStr;
	}
	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getGiveTotal() {
		return giveTotal;
	}

	public void setGiveTotal(Integer giveTotal) {
		this.giveTotal = giveTotal;
	}

	public Integer getUseTotal() {
		return useTotal;
	}

	public void setUseTotal(Integer useTotal) {
		this.useTotal = useTotal;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
