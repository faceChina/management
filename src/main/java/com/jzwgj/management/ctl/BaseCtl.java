package com.jzwgj.management.ctl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;

import com.jzwgj.management.util.JsonUtil;
import com.jzwgj.management.vo.JsonPageVo;
import com.jzwgj.management.vo.JsonResultData;

@Controller
public class BaseCtl {
	

	public <T> String setJsonString(int totalCount, boolean resultFlag, List<T> list, String[] exclude,String datePattern) {
		JsonPageVo<T> jsonPageVo = new JsonPageVo<T>(list, totalCount);
		return setExtSelfObjectResult(jsonPageVo,resultFlag,exclude,datePattern);
	}
	
	public <T> String setJsonString(int totalCount, boolean resultFlag,List<T> list, String[] exclude) {
		JsonPageVo<T> jsonPageVo = new JsonPageVo<T>(list, totalCount);
		return setExtSelfObjectResult(jsonPageVo,resultFlag,exclude,null);
	}
	
	
	protected static String getReqJson(boolean isTure,Object info){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", isTure);   
		resultMap.put("info", info);
		return JSONObject.fromObject(resultMap).toString();
	}
	
	/**
	 * 页面ext 返回数据
	 */
	protected String setExtObjectResult(Object object) {
		return setExtObjectResult(object,false,null);
	}
	
	/**
	 * 页面ext 返回数据
	 */
	protected String setExtObjectResult(Object object, boolean resultFlag,String[] propertys) {
		JSON json =jsonConfigPropertys(object,resultFlag,propertys,null);
		return json.toString();
	}

	/**
	 * 页面ext 只返回操作结果
	 */
	protected String setExtOptResult(boolean optResult, String optDesc) {
		JsonResultData<Object> jsonResultData = new JsonResultData<Object>(
				optResult, null, optDesc);
		JSON json = JsonUtil.toJson(jsonResultData, true,
				new String[] { "data" }, null);
		return json.toString();
	}
	
	
	public String setExtSelfObjectResult(Object object, boolean resultFlag,String[] propertys,
		String datePattern) {
		JSON json = jsonConfigPropertys(object,resultFlag,propertys,datePattern);
		return json.toString();
	}
	
	/**
	 * 页面ext 返回数据
	 */
	public String setExtJsonDataResult(Object object, boolean resultFlag,
		String[] propertys, String datePattern, String resultDesc) {
		JsonResultData<Object> jsonResultData = new JsonResultData<Object>(
				true, object, resultDesc);
		JSON json = jsonConfigPropertys(jsonResultData,resultFlag,propertys,datePattern);
		return json.toString();
	}
	
	/**
	 * 页面ext 返回数据
	 */
	public String setExtJsonDataResult(Object object, String[] propertys) {
		return setExtJsonDataResult(object, true, propertys, null, null);
	}
	
	
	/**
	 * 页面ext 返回数据
	 */
	public String setExtJsonDataResult(Object object) {
		return setExtJsonDataResult(object, true, null, null, null);
	}
	
	
	private JSON jsonConfigPropertys(Object object,boolean resultFlag,String[] propertys, String datePattern){
		JSON json = null;
		
		if (propertys != null) {
			if(resultFlag){
				json = JsonUtil.toJson(object, true, propertys,
						datePattern);
			}else{
				List<String> propertyList = new ArrayList<String>(Arrays.asList(propertys));
				propertyList.add("success");
				propertyList.add("data");
				propertys = propertyList.toArray(new String[propertyList.size()]); 
				json = JsonUtil.toJson(object, false,propertys,
						datePattern);
			}
			
		} else {
			json = JsonUtil
					.toJson(object, false, null, datePattern);
		}
		return json;
	}
	
}
