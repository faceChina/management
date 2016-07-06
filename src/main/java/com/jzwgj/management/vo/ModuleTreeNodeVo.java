package com.jzwgj.management.vo;

import java.util.List;

public class ModuleTreeNodeVo {
	
	/** 名称  */
	private String text;
	/** 主键 */
	private String id;
	/** 图标样式 */
	private String iconCls;
	/** 是否是叶节点*/
	private boolean leaf;
	/** 菜单URL */
	private String pageUrl;
	/** 菜单父级ID */
	private String parentId;
	private String pId;
	private String customJson; //自定义属性
	
	private List<ModuleTreeNodeVo> childList;
	

	public String getPId() {
		return pId;
	}

	public void setPId(String pId) {
		this.pId = pId;
	}

	public List<ModuleTreeNodeVo> getChildList() {
		return childList;
	}

	public void setChildList(List<ModuleTreeNodeVo> childList) {
		this.childList = childList;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCustomJson() {
		return customJson;
	}

	public void setCustomJson(String customJson) {
		this.customJson = customJson;
	}


}
