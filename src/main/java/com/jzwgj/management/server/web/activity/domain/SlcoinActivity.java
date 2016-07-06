package com.jzwgj.management.server.web.activity.domain;

import java.util.Date;

public class SlcoinActivity {
	
    private Long id;

    private String name;

    private Integer status;

    private Integer inviteAmount;

    private Integer registerAmount;

    private Integer superAmount;
    
	private Date beginTime;

    private Date endTime;

    private Date createTime;

    private Date updateTime;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getInviteAmount() {
        return inviteAmount;
    }

    public void setInviteAmount(Integer inviteAmount) {
        this.inviteAmount = inviteAmount;
    }

    public Integer getRegisterAmount() {
        return registerAmount;
    }

    public void setRegisterAmount(Integer registerAmount) {
        this.registerAmount = registerAmount;
    }

    public Integer getSuperAmount() {
		return superAmount;
	}

	public void setSuperAmount(Integer superAmount) {
		this.superAmount = superAmount;
	}

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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