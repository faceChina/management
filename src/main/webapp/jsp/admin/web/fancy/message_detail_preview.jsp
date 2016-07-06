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
		<title>刷脸精选 预览</title>
		<link href="${ctx}/resource/admin/css/fancy/main_preview.css" rel="stylesheet" type="text/css" />
		<%@include file="../../common/base.jsp" %>
		
		<script type="text/javascript">
			$(function(){
				<c:if test="${type == 'app'}">
					window.location.href = '${path}' ;
				</c:if>
			});
		</script>
	</head>
	<body navbar="true">
		<div class="outbox">
		    <div class="wxhead">
		    	<p class="wxhead-title">刷脸精选</p>
		    </div>
		    <div class="inbox">
		    	<iframe src="${wgjUrl }/message/fancy/details/${fancyMessageItem.id }${ext}" frameborder="0"></iframe>
				<%--
				<div id="box">
					<div class="details">
						<div class="details-title">
							<h3>${fancyMessageItem.name }</h3>
							<h4>
								<span class="time">
									<fmt:formatDate value="${fancyMessageItem.createTime }" pattern="yyyy-MM-dd"/>
								</span>
								<samp>${fancyMessageItem.author}</samp>
							</h4>
						</div>
						<div class="details-center">
							${fancyMessageItem.content}
						</div>
					</div>
				</div>
				--%>
		    </div>
		</div>
	</body>
</html>
