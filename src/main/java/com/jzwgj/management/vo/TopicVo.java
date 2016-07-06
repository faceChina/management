package com.jzwgj.management.vo;

public class TopicVo {
	
	private String code;
	
	private String seCode;
	
	private Long typeId;
	
	private Long parentId;
	
	private Long pid;

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getCode() {
		return code;
	}
	

	public String getSeCode() {
		return seCode;
	}

	public void setSeCode(String seCode) {
		this.seCode = seCode;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

}
