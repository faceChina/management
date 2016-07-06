package com.jzwgj.management.server.web.fancy.domain.dto;

import com.jzwgj.management.server.web.fancy.domain.FancyMessageItem;

public class FancyMessageItemDto extends FancyMessageItem {

	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 4488792190183967927L;

	private String sessionId;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
