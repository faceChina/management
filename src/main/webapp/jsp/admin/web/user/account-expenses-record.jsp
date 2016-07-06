<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="resourcePath" value="${pageContext.request.contextPath}/resource/admin/" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账户收支记录</title>
	<%@include file="../../common/base.jsp" %>
	<link href="${ctx}/resource/admin/css/fancy/bootstrap.min.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/resource/admin/css/fancy/main.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/resource/admin/css/fancy/main-gw.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/resource/admin/css/user/main-gl.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="${resourcePath}js/daterangepicker/daterangepicker-bs3-zh.css" />
	<script  type="text/javascript" src="${ctx}/resource/company/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${resourcePath}js/daterangepicker/moment.min-zh.js"></script>
	<script type="text/javascript" src="${resourcePath}js/daterangepicker/daterangepicker-zh.js"></script>
</head>
<body>
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-12">
				<div class="row">
					<div class="box box-auto">
						<div class="title">
							<ul class="nav nav-tabs">
								<li class="active"><a href="javascript:;" data-toggle="tab">收支记录</a></li>
							</ul>
						</div>
						<div class="content">
							<div class="form-horizontal">
								<div class="form-group">
									<h4 class="pull-left control-left">${slcoinRecordVo.nickName} </h4> <lable class="pull-left control-left">的颜值收支记录</lable>
								</div>
								<div class="form-group">
									<div class="col-sm-4 col-sm-time">
										<span class="time-icon"></span>
										<input type="text" class="form-control" name="reservation" id="reservation" value="${slcoinRecordVo.beginTime}<c:if test="${not empty slcoinRecordVo.beginTime}">-</c:if>${slcoinRecordVo.endTime}">
										<input type="hidden" class="form-control" name="beginTime" id="beginTime" value="${slcoinRecordVo.beginTime}">
										<input type="hidden" class="form-control" name="endTime" id="endTime" value="${slcoinRecordVo.endTime}">
										<input type="hidden" id="userId" value="${slcoinRecordVo.userId}"/>
									</div>
									<div class="col-sm-1">
										<button id="sumbit" class="btn btn-default" type="sumbit">查询</button>
										<!-- <button id="reset" class="btn btn-default" type="button">清空</button> -->
										<a href="${ctx}/web/user/sl/userSlcoinRecord.htm?userId=${slcoinRecordVo.userId}&pageSize=${pagination.pageSize}" id="reset" class="btn btn-default">清空</a>
										<a href="${ctx}/web/user/sl/userAccountInfo.htm?userId=${slcoinRecordVo.userId}" id="back" class="btn btn-default">返回</a>
									</div>
									<div class="col-sm-2 pull-right">
										<select class="form-control" name="type" id="type">
											<option value="0" <c:if test="${slcoinRecordVo.type == 0}">selected</c:if> >所有</option>
											<option value="1" <c:if test="${slcoinRecordVo.type == 1 || slcoinRecordVo.type == 4 || slcoinRecordVo.type == 5}">selected</c:if> >
												系统赠送
											</option>
											<option value="6" <c:if test="${slcoinRecordVo.type ==6 }">selected</c:if> >admin赠送</option>
											<option value="3" <c:if test="${slcoinRecordVo.type ==3 }">selected</c:if> >退回</option>
											<option value="2" <c:if test="${slcoinRecordVo.type ==2 }">selected</c:if> >消费</option>
										</select>
									</div>
								</div>
								
								<table class="table table-bordered">
									<thead>
										<tr>
											<th width="25%">日期</th>
											<th width="25%">类型</th>
											<th width="25%">颜值</th>
											<th width="25%">备注</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${pagination.datas}" var="item">
										<tr>
											<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${item.createTime }"/></td>
											<td>${item.typeStr}</td>
											<c:choose>
												<c:when test="${item.type == 2}">
													<td>-${item.amount}</td>
												</c:when>
												<c:otherwise>
													<td>+${item.amount}</td>
												</c:otherwise>
											</c:choose>
											<td>${item.remark}</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
								
								<%-- <c:forEach items="${pagination.totalPage}" var="page">
									${page}
								</c:forEach> --%>
											
								<input type="hidden" id="pageSize" value="${pagination.pageSize}"/>
								<input type="hidden" id="curPage" name="curPage" value="${pagination.curPage}"/>
								<c:if test="${not empty pagination.datas}">
									<c:if test="${pagination.totalPage > 1}">
									<ul class="pagination pull-right">
										<li class="disabled"><a href="#">上一页</a></li>
										
											<li class="active"><a href="#">1<span class="sr-only">(current)</span></a></li>
											<li><a href="#">2</a></li>
										
										<li><a href="#">下一页</a></li>
										<!-- <li><a href="javascript:void(0)">跳转到：<input type="text" value=""></a></li> -->
										<!-- <li><a href="#">确认</a></li> -->
									</ul>
									</c:if>
								</c:if>
								<c:if test="${empty pagination.datas}">
									<div class="form-group col-sm-12 text-center">无记录</div>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- body end -->
	<script type="text/javascript">
		 $(document).ready(function() {
	        	// var nowDate = new Date(),
	        	// 	dateStr = nowDate.getFullYear() + "/" + nowDate.getMonth() + "/" + nowDate.getDate();
	            $('#reservation').daterangepicker({
	            	format: 'YYYY/MM/DD',
					minDate: '2015/10/01',
					//maxDate: '2015/12/31'
	            	startDate: '2015/10/01'
	            }, function(start, end, label) {
					$('#beginTime').val(start.format('YYYY/MM/DD'));
					$('#endTime').val(end.format('YYYY/MM/DD'));
	                console.log(start.toISOString(), end.toISOString(), label);
	            });
	        });
		 
		 $("#sumbit").on("click",function(){
			 var userId = $("#userId").val(),
			 	 beginTime = $('#beginTime').val(),
			 	 endTime = $('#endTime').val(),
			 	 type = $('#type').val();
			 var pageSize = $("#pageSize").val();
			 var _url = "${ctx}/web/user/sl/userSlcoinRecord.htm?";
			 var _slcoinRecor = "userId="+userId+"&type="+type+"&beginTime="+beginTime+"&endTime="+endTime;
			 var _pagination = "&pageSize="+pageSize;
			 _url = _url+_slcoinRecor+_pagination;
			 //alert(_url);
			 window.location.href =_url;
			//window.location.reload();
		 });
	</script>
</body>
</html>