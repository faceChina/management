package com.jzwgj.management.server.web.activity.domain;

import java.util.Date;

public class SlcoinRecord {
	
	/**
	 * 类型:注册赠送
	 */
	public static final Integer TYPE_REGISTER = 1;
	/**
	 * 类型:消费使用
	 */
	public static final Integer TYPE_CONSUME = 2;
	/**
	 * 类型:取消退回
	 */
	public static final Integer TYPE_CANCEL = 3;
	/**
	 * 类型:邀请好友注册赠送
	 */
	public static final Integer TYPE_INVITE = 4;
	/** 
	 * 类型:5邀请者上级注册赠送
	 * */
	public final static Integer TYPE_SUPER_INVITE = 5;
	/** 类型:6admin赠送*/
	public final static Integer TYPE_ADMIN_GIVE = 6;
	/** 类型:7活动赠送*/
	public final static Integer TYPE_ACTIVITY_GIVE = 7;
	
	
    private Long id;

    private Long userId;

    private Long invitedUserId;

    private String orderNo;

    private Long slcoinShopId;

    private Long activityId;

    private Integer type;

    private Integer amount;

    private Byte status;

    private Date createTime;

    private Date updateTime;
    
    private String remark;
    
    public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getInvitedUserId() {
        return invitedUserId;
    }

    public void setInvitedUserId(Long invitedUserId) {
        this.invitedUserId = invitedUserId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Long getSlcoinShopId() {
        return slcoinShopId;
    }

    public void setSlcoinShopId(Long slcoinShopId) {
        this.slcoinShopId = slcoinShopId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}