package com.jzwgj.management.constant;

public class Constant {

	/**
	 * 跳转模板类型
	 */
	public static final Integer DETAIL_PAGE_WORD  = 1;
	
	public static final Integer DETAIL_PAGE_IMGS  = 2;
	
	/**
	 * 商品类目状态:删除
	 */
	public static final Integer CLASSIFIACTION_STATUS_DELETE = -1;
	/**
	 * 商品类目状态:未发布
	 */
	public static final Integer CLASSIFIACTION_STATUS_NOT_PUBLISH = 0;
	/**
	 * 商品类目状态:已发布
	 */
	public static final Integer CLASSIFIACTION_STATUS_PUBLISH = 1;
	
	/**
	 * 是否为叶子节点:是
	 */
	public static final Integer IS_LEAF = 1;
	/**
	 * 是否为叶子节点:否
	 */
	public static final Integer IS_NOT_LEAF = 0;
	
	/**
	 * 状态:删除
	 */
	public static final Integer STATUS_DELETE = -1;
	/**
	 * 状态:未发布
	 */
	public static final Integer STATUS_NOT_PUBLISH = 0;
	/**
	 * 状态:已发布
	 */
	public static final Integer STATUS_PUBLISH = 1;
	
	/**
	 * 排序类型:下移
	 */
	public static final Integer SORT_TYPE_DOWN = 1;
	/**
	 * 排序类型:上移
	 */
	public static final Integer SORT_TYPE_UP = -1;
	/**
	 * 排序类型:置地
	 */
	public static final Integer SORT_TYPE_DOWN_LAST = 2;
	/**
	 * 排序类型:置顶
	 */
	public static final Integer SORT_TYPE_UP_FIRST = -2;
}
