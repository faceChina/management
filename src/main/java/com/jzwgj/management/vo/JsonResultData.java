package com.jzwgj.management.vo;

public class JsonResultData<T> implements java.io.Serializable{

	private static final long serialVersionUID = 7359075158641919678L;

	private boolean success;
	
	private T data;
	
	private String resultDesc;
	
	public JsonResultData() {
	}

	public JsonResultData(boolean success, T data,String resultDesc) {
		this.success = success;
		this.data = data;
		this.resultDesc = resultDesc;
	}
	
	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
