package com.jzwgj.management.util;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.PropertyFilter;

import org.apache.log4j.Logger;

/**
 * 本类不可改。 把传入的对象转为JSON对象 用法看JsonTest方法
 * 
 * @author String-->"" integer-->null list-->[]
 */
public class JsonUtil {

	private static final Logger log = Logger.getLogger(JsonUtil.class);

	/**
	 * 直接输出JSON到前台
	 * 
	 * @param writer
	 *            输出流
	 * @param source
	 *            要转换的java对象
	 * @param isfilter
	 *            是否为过滤状态。true:过滤状态.fale:不过滤状态(保留)
	 * @param propertys
	 *            [] 属性数组 new String[]{1,2.3}
	 * @param datePattern
	 *            yyyy-MM-dd HH:mm:ss日期转换模式
	 * @throws Exception
	 *             写入数据可能出现异常
	 */
	public static void outJson(Writer writer, Object source, boolean isfilter,
			String[] propertys, String datePattern) throws Exception {
		JSON json = toJson(source, isfilter, propertys, datePattern);
		json.write(writer);
	}

	/**
	 * 配置json-lib需要的excludes和datePattern.
	 * 
	 * @param excludes
	 *            不需要转换的属性数组
	 * @param datePattern
	 *            yyyy-MM-dd HH:mm:ss 日期转换模式
	 * @return JsonConfig 根据excludes和dataPattern生成的jsonConfig，用于write
	 */
	public static JsonConfig getConfigJson(final String datePattern) {
		final String defaultDatePattern = "yyyy-MM-dd HH:mm:ss";
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "hibernateLazyInitializer",
				"handler" });
		jsonConfig.setIgnoreDefaultExcludes(false);// 默认为false，即过滤默认的key
													// 转换Map对象时，要设为true
													// 要不key会被过滤掉
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 循环检测策略
																				// 这里指明如果含有自包含的时候怎么外理，如树形
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonValueProcessor() {
					public Object processArrayValue(Object value,
							JsonConfig arg1) {
						if (value != null) {
							return new SimpleDateFormat(
									datePattern == null ? defaultDatePattern
											: datePattern).format(value);
						}
						return null;
					}

					public Object processObjectValue(String key, Object value,
							JsonConfig arg2) {
						if (value != null) {
							return new SimpleDateFormat(
									datePattern == null ? defaultDatePattern
											: datePattern).format(value);
						}
						return null;
					}

				});
		jsonConfig.registerDefaultValueProcessor(Long.class,
				new MyDefaultValueProcessor());// 无值时设为null 否则会默认会被设为0
		jsonConfig.registerDefaultValueProcessor(Integer.class,
				new MyDefaultValueProcessor());// 无值时设为null 否则会默认会被设为0
		jsonConfig.registerDefaultValueProcessor(Double.class,
				new MyDefaultValueProcessor());// 无值时设为null 否则会默认会被设为0

		return jsonConfig;
	}

	/**
	 * * 默认值为null时，返回null 否则默认会被设为0
	 */
	public static class MyDefaultValueProcessor implements
			DefaultValueProcessor {
		public Object getDefaultValue(@SuppressWarnings("rawtypes") Class type) {
			return null;
		}
	}

	/**
	 * java对像转成json
	 * 
	 * @param source
	 *            要转换的java对象
	 * @param isfilter
	 *            是否为过滤状态。true:滤掉.fale:不滤掉
	 * @param propertys
	 *            [] 要转换的java对象中的字符串数组
	 * @param datePattern
	 *            java对象中日期字段的格式化格式
	 * @return
	 */
	public static JSON toJson(Object source, final boolean isfilter,
			final String[] propertys, String datePattern) {
		JsonConfig jsonConfig = getConfigJson(datePattern);
		if (propertys != null && propertys.length > 0) {
			final List<String> propertyList = Arrays.asList(propertys);
			// a b c d e
			jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
				public boolean apply(Object source, String name, Object value) {
					// 返回 true, 表示这个属性将被过滤掉
					if (isfilter) {// 1 过滤a isfilter=true propertyList=[a]
						return propertyList.contains(name);
					} else {// 2 不过滤a isfilter=false propertyList=[a]
						return !propertyList.contains(name);
					}
				}

			});
		}
		JSON json = JSONSerializer.toJSON(source, jsonConfig);
		log.debug(json.toString());
		return json;
	}

	public static JSON toSimpleJson(Object source) {
		return toJson(source, false, null, null);
	}

	/**
	 * 把传入的对象转为JSON对象,用于分页
	 * 
	 * @param recordTotal
	 *            :总记录数
	 * @param beanList
	 *            :传入的数据列表
	 * @param vStart
	 *            :记录开始值
	 * @param vLimit
	 *            :一页几条记录
	 * @return:{"result":2,"items":[{"id":1,"name":"张三" 
	 *                                                  ,{"id":2,"name":"李四"}],"start"
	 *                                                  :0,"limit":10}
	 */
	public static String getJsonFromObject(Object obj) {
		JSONObject jsonObj = JSONObject.fromObject(obj);
		return jsonObj.toString();
	}

	/**
	 * 把传入的对象转为JSON对象,无分页
	 * 
	 * @param beanList
	 *            :传入的数据列表
	 * @return
	 */
	public static String getJsonFromList(List<?> beanList) {
		JSONObject jsonArray = JSONObject.fromObject(beanList);
		return jsonArray.toString();
	}

	public static Object[] getDTOArray(String jsonString, Class<?> clazz) {
		JSONArray array = JSONArray.fromObject(jsonString);
		Object[] obj = new Object[array.size()];
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			obj[i] = JSONObject.toBean(jsonObject, clazz);
		}
		return obj;
	}

}