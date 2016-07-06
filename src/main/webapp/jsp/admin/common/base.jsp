<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ext" value=".htm" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="resourcePath" value="${pageContext.request.contextPath}/resource/admin/" />
<c:set var="jspPath" value="${pageContext.request.contextPath}/jsp/admin/" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script  type="text/javascript" src="${resourcePath}js/jquery-1.6.min.js"></script>

<!-- 图标 -->


<link rel="stylesheet" type="text/css" href="${resourcePath}css/exticon/icon.css" />
<link rel="stylesheet" type="text/css" href="${resourcePath}css/exticon/icons-module.css" />
<link rel="stylesheet" type="text/css" href="${resourcePath}css/exticon/icons-menu.css" />
<link rel="stylesheet" type="text/css" href="${resourcePath}css/UploadPanel.css"/>

<!-- ExtJs -->
<link rel="stylesheet" type="text/css" href="${resourcePath}js/extjs/resources/css/ext-all.css"/>
<link rel="stylesheet" type="text/css" href="${resourcePath}js/extjs/resources/css/ext-patch.css"/>
<link rel="stylesheet" type="text/css" href="${resourcePath}js/extjs/custom/tips/tips.css" />
<script type="text/javascript" src="${resourcePath}js/extjs/adapter/ext/ext-base.js"  charset="UTF-8"></script>

<script type="text/javascript" src="${resourcePath}js/extjs/ext-all.js" charset="UTF-8"></script>
<script type="text/javascript" src="${resourcePath}js/extjs/ext-basex.js" charset="UTF-8"></script>
<script type="text/javascript" src="${resourcePath}js/extjs/ext-lang-zh_CN.js" charset="UTF-8"></script>

<!-- ExtJs 自定义库-->
<script type="text/javascript" src="${resourcePath}js/extjs/PageComboResizer.js" charset="UTF-8"></script>
<script type="text/javascript" src="${resourcePath}js/extjs/custom/ExtNames.js" charset="UTF-8"></script>
<script type="text/javascript" src="${resourcePath}js/extjs/custom/BaseGrid.js" charset="UTF-8"></script>
<script type="text/javascript" src="${resourcePath}js/extjs/custom/BaseEdGrid.js" charset="UTF-8"></script>
<script type="text/javascript" src="${resourcePath}js/extjs/custom/BaseComBox.js" charset="UTF-8"></script>
<script type="text/javascript" src="${resourcePath}js/extjs/custom/BaseTreePanel.js" charset="UTF-8"></script>
<script type="text/javascript" src="${resourcePath}js/extjs/custom/BaseApi.js" charset="UTF-8"></script>
<script type="text/javascript" src="${resourcePath}js/extjs/custom/BaseVerify.js" charset="UTF-8"></script>
<script type="text/javascript" src="${resourcePath}js/extjs/custom/EveExt.js" charset="UTF-8"></script>

<!--分页进度条-->
<script type="text/javascript" src="${resourcePath}js/extjs/ux/ProgressBarPager.js"></script>
<script type="text/javascript" src="${resourcePath}js/extjs/custom/AppUtil.js"></script>

<!-- 附件上传对话框 -->
<script type="text/javascript" src="${resourcePath}js/extjs/plugin/upload/swfupload.js"></script>
<script type="text/javascript" src="${resourcePath}js/extjs/plugin/upload/UploadConfig.js"></script>
<script type="text/javascript" src="${resourcePath}js/extjs/plugin/upload/UploadPanel.js"></script>

<!-- 自定义上传组件 -->
<script type="text/javascript" src="${resourcePath}js/extjs/plugin/fileupload/FileUploadPanel.js"></script>
<script type="text/javascript" src="${resourcePath}js/extjs/plugin/fileupload/swfupload.js"></script>
<script type="text/javascript" src="${resourcePath}js/extjs/plugin/fileupload/FilesUploadPanel.js"></script>

<!-- 上传组件 -->
<link rel="stylesheet" type="text/css" href="${resourcePath}js/extjs/plugin/fileuploadfield/css/fileuploadfield.css"/>
<script type="text/javascript" src="${resourcePath}js/extjs/plugin/fileuploadfield/FileUploadField.js"></script>

<script type="text/javascript" src="${resourcePath}js/extjs/custom/App.js"></script>
<%-- <script type="text/javascript" src="${resourcePath}js/common/uuid.js"></script> --%>

<link rel="shortcut icon" href="${resourcePath}resource/admin/img/favicon.ico" type="image/x-icon">

<style type="css/text">
body{
	margin:0 0 0 0;
	font-size: 9pt;
	overflow:auto;
	SCROLLBAR-FACE-COLOR: #E7EFFF;
	SCROLLBAR-HIGHLIGHT-COLOR:#4682B4;
	SCROLLBAR-SHADOW-COLOR:#4682B4;
	SCROLLBAR-3DLIGHT-COLOR: #E7EFFF;
	SCROLLBAR-ARROW-COLOR: #023EEE;
	SCROLLBAR-TRACK-COLOR: #E4E6F8;
	SCROLLBAR-DARKSHADOW-COLOR: #E4E6F8;
	SCROLLBAR-BASE-COLOR: #1763BB
}
.Gray
{
 filter:alpha(style=0,opacity=30,finishopacity=30);
}
</style>
<script type="text/javascript">
    //解决  对象不支持“createContextualFragment”属性或方法
	  if ((typeof Range !== "undefined") && !Range.prototype.createContextualFragment) { 
	Range.prototype.createContextualFragment = function(html) { 
	var frag = document.createDocumentFragment(), div = document.createElement("div"); 
	frag.appendChild(div); 
	div.outerHTML = html; 
	return frag; 
	}
	}
	var ROOT_PATH="${ctx}";
	var EDIT_BUTTON_PATH="${resourcePath}/css/exticon/icon/";
</script>