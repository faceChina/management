package com.jzwgj.management.vo;

import java.util.ArrayList;
import java.util.List;

public class JsonPageVo<T> {
	
	private List<T> data = new ArrayList<T>();
	
	private int total;
    
	public JsonPageVo(){
	}
	public JsonPageVo(List<T> data,int total){
		this.data = data;
		this.total = total;
	}
	

	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	
	

}
