<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ext" value=".htm" />

<!DOCTYPE html>
<html lang="zh-cn" style="font-size: 16.875px;">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
		<meta content="yes" name="apple-mobile-web-app-capable" />
		<meta content="black" name="apple-mobile-web-app-status-bar-style" />
		<meta content="telephone=no" name="format-detection" />
		<title>刷脸精选二维码预览</title>
		<link href="${ctx}/resource/admin/css/fancy/main_preview.css" rel="stylesheet" type="text/css" />
		<%@include file="../../common/base.jsp" %>
	</head>
	<body navbar="true">
		<img alt="" src="${path }">
	</body>
</html>
