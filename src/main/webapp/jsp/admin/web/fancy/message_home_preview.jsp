<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	</head>
	<body navbar="true">
		<c:if test="${type == 'web' }">
			<div class="outbox">
			    <div class="wxhead">
			    	<p class="wxhead-title">刷脸精选</p>
			    </div>
			    <div class="inbox">
	    </c:if>
				<div id="box">
					<div class="m-graphic">
						<c:forEach items="${itemList}" var="item" varStatus="status">
							<c:choose>
								<c:when test="${ status.index == 0}">
									<a class="graphic-big" href="${ctx}/web/fancy/previewDetail.htm?id=${item.id}&type=${type}">
										<p class="pic">
											<img src="${picUrl }${item.picturePath }" alt="">
										</p>
										<p class="title txt-rowspan2">
											${item.name }
										</p>
									</a>
								</c:when>
								<c:otherwise>
									<a class="graphic-item" href="${ctx}/web/fancy/previewDetail.htm?id=${item.id}&type=${type}">
										<p class="pic">
											<img src="${picUrl }${item.picturePath }" alt="">
										</p>
										<p class="title txt-rowspan2">
											${item.name }
										</p>
									</a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</div>
				</div>
		<c:if test="${type == 'web' }">
			    </div>
			</div>	
		</c:if>
	</body>
</html>
