package com.jzwgj.management.server.web.activity.domain;


public class SlcoinActivityVo extends SlcoinActivity{
	
	/**
	 * 状态:未开始
	 */
	public static final Integer STATUS_NOT = 0;
	/**
	 * 状态:进行中
	 */
	public static final Integer STATUS_UNDERWAY = 1;
	/**
	 * 状态:已结束
	 */
	public static final Integer STATUS_END = 2;
	/**
	 * 修改类型:结束
	 */
	public static final Integer UPDATE_TYPE_END = 1;
	/**
	 * 修改类型:开始
	 */
	public static final Integer UPDATE_TYPE_STATUS = 2;
	
	/**
	 * 活动时间(开始时间-结束时间)
	 */
    private String activityTime;
    /**
     * 状态(0:未开始;1:进行中;2:已结束)
     */
    private Integer nowStatus;
    
    private String beginTimeVo;
    private String endTimeVo;
    
	public String getBeginTimeVo() {
		return beginTimeVo;
	}
	public void setBeginTimeVo(String beginTimeVo) {
		this.beginTimeVo = beginTimeVo;
	}
	public String getEndTimeVo() {
		return endTimeVo;
	}
	public void setEndTimeVo(String endTimeVo) {
		this.endTimeVo = endTimeVo;
	}
	public String getActivityTime() {
		return activityTime;
	}
	public void setActivityTime(String activityTime) {
		this.activityTime = activityTime;
	}
	public Integer getNowStatus() {
		return nowStatus;
	}
	public void setNowStatus(Integer nowStatus) {
		this.nowStatus = nowStatus;
	}
}