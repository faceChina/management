package com.jzwgj.management.server.web.classifcation.domain.vo;

import com.jzwgj.management.server.web.classifcation.domain.ClassificationPropRealtion;

public class ClassificationPropRealtionVo extends ClassificationPropRealtion {

    //属性名称（用于页面显示）
    private String propName;
    //是否颜色属性
    private Boolean isColorProp= false;
    //是否枚举属性
    private Boolean isEnumProp= false;
    //是否输入属性
    private Boolean isInputProp= false;
    //是否关键属性
    private Boolean isKeyProp= false;
    //是否销售属性
    private Boolean isSalesProp= false;
    //是否允许别名
    private Boolean isAllowAlias= false;
    //是否标准属性
    private Boolean isStandard= false;
    //发布商品时是否为必选属性
    private Boolean isRequired= false;
    //发布商品时是否可以多选属性
    private Boolean isMulti= false;
    //商品属性ID
    private Long propId;

	public Long getPropId() {
		return propId;
	}

	public void setPropId(Long propId) {
		this.propId = propId;
	}

	public Boolean getIsColorProp() {
		return isColorProp;
	}

	public void setIsColorProp(Boolean isColorProp) {
		this.isColorProp = isColorProp;
	}

	public Boolean getIsEnumProp() {
		return isEnumProp;
	}

	public void setIsEnumProp(Boolean isEnumProp) {
		this.isEnumProp = isEnumProp;
	}

	public Boolean getIsInputProp() {
		return isInputProp;
	}

	public void setIsInputProp(Boolean isInputProp) {
		this.isInputProp = isInputProp;
	}

	public Boolean getIsKeyProp() {
		return isKeyProp;
	}

	public void setIsKeyProp(Boolean isKeyProp) {
		this.isKeyProp = isKeyProp;
	}

	public Boolean getIsAllowAlias() {
		return isAllowAlias;
	}

	public void setIsAllowAlias(Boolean isAllowAlias) {
		this.isAllowAlias = isAllowAlias;
	}

	public Boolean getIsStandard() {
		return isStandard;
	}

	public void setIsStandard(Boolean isStandard) {
		this.isStandard = isStandard;
	}

	public Boolean getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}

	public Boolean getIsMulti() {
		return isMulti;
	}

	public void setIsMulti(Boolean isMulti) {
		this.isMulti = isMulti;
	}

	public Boolean getIsSalesProp() {
		return isSalesProp;
	}

	public void setIsSalesProp(Boolean isSalesProp) {
		this.isSalesProp = isSalesProp;
	}

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}
}
