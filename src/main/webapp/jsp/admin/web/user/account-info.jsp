<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
    
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="resourcePath" value="${pageContext.request.contextPath}/resource/admin/" />
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账户信息</title>
	<%@include file="../../common/base.jsp" %>
	<link href="${ctx}/resource/admin/css/fancy/bootstrap.min.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/resource/admin/css/fancy/main.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/resource/admin/css/fancy/main-gw.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/resource/admin/css/user/main-gl.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${resourcePath}js/artDialog/jquery.artDialog.js"></script>
	<script type="text/javascript" src="${resourcePath}js/artDialog/iframeTools.js"></script>
	<script type="text/javascript" src="${resourcePath}js/validate/jquery.validate.js"></script>
	<script type="text/javascript" src="${resourcePath}js/validate/messages_zh.js"></script>
</head>
<body>
<!-- body  -->
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-12">
				<div class="row">
					<div class="box box-auto">
						<div class="title">
							<ul class="nav nav-tabs">
								<li><a href="${ctx}/web/user/sl/userDetail.htm?userId=${shuaLianUser.account.remoteId}" data-toggle="tab">基本资料</a></li>
								<li class="active"><a href="javascript:;" data-toggle="tab">账户信息</a></li>
							</ul>
						</div>

						<!-- <a href="${ctx}/web/user/sl/userAccountInfo.htm?userId=600" type="buttom" class="btn btn-default">账户信息</a>
						<a href="${ctx}/jsp/admin/web/user/account-info.jsp" type="buttom" class="btn btn-default">账户信息</a> -->
						
						<div class="content">
							<div class="form-horizontal account-info">
								<div class="form-group">
									<ul class="list-unstyled list-unstyled-line basics">
										<div class="col-md-12">
											<label class="col-md-2"><b>账户信息</b></label>
										</div>
										<div class="col-md-12">
											<label class="col-md-2">账户状态：${shuaLianUser.status}</label>
										</div>
										<div class="col-md-12">
											<label class="pull-left">钱包余额：<b>¥<fmt:formatNumber pattern="0.00" value="${shuaLianUser.account.withdrawAmount/100 }"/></b></label>
											<label class="col-md-1"> </label>
											<label class="pull-left">冻结金额：<b>¥<fmt:formatNumber pattern="0.00" value="${shuaLianUser.account.freezeAmount/100 }"/></b></label>
										</div>
										<div class="col-md-12">
											<label class="col-md-2">可用颜值：<b id="slcoinAmount">${shuaLianUser.account.slcoinAmount}</b><b>点</b></label>
											<div class="pull-left">
												<button type="button" class="btn btn-default" onclick="giveyz()">赠送</button>
												<a href="${ctx}/web/user/sl/userSlcoinRecord.htm?userId=${shuaLianUser.userId}&pageSize=15" type="buttom" class="btn btn-default">收支记录</a>
												<input type="hidden" id="userId" value="${shuaLianUser.userId}"/>
											</div>
										</div>
										<div class="col-md-12">
											<label class="col-md-3">累计获得颜值：<b id="totalSlcoin">${shuaLianUser.totalSlcoin}</b><b>点</b></label>
										</div>
									</ul>
								</div>
							</div>
							<br>
							<div class="form-horizontal">
								<div class="form-group">
									<label class="col-md-3"><b>支付信息</b></label>
								</div>
								<!-- <div class="form-group">
									<label class="col-md-3">支付宝账户：未绑定</label>
									<label class="pull-left">支付宝账号：</label>
								</div> -->
								<c:if test="${not empty shuaLianUser.user.wechat}">
								<div class="form-group">
									<label class="col-md-3">微信账户：已绑定</label>
									<label class="pull-left">微信账号：${shuaLianUser.user.wechat}</label>
								</div>
								</c:if>
								<div class="form-group">
									<label class="col-md-3">银行卡：  已绑定<b>${shuaLianUser.bankcardNumber}</b>张</label>
								</div>
							</div>

							<div class="account-bank-card">
								<c:forEach items="${shuaLianUser.bankCardList}" var="bankCard">
								<div class="pull-left bank-card-left">
									<div class="bank-card-left-head">
										<strong>${bankCard.bankName}</strong>
										<small class="pull-right">尾号：${bankCard.bankCard}</small>
									</div>
									<div class="bank-card-left-center">
										<div class="account-info-bank-state text-left">
											<c:if test="${bankCard.bankType == 2}">
												<span>储蓄卡</span>
												<span>(可提现)</span>
												<!-- <c:if test="${bankCard.type == 1}">
													<span>(不可提现)</span>
												</c:if>
												<c:if test="${bankCard.type == 2}">
													<span>(可提现)</span>
												</c:if> -->
											</c:if>
											<c:if test="${bankCard.bankType == 3}">
												<span>信用卡</span>
												<span>(不可提现)</span>
												<!-- <c:if test="${bankCard.type == 1}">
													<span>(不可提现)</span>
												</c:if>
												<c:if test="${bankCard.type == 2}">
													<span>(可提现)</span>
												</c:if> -->
											</c:if>
										</div>
									</div>
								</div>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 赠送颜值 -->
	<div id="j-give-yz" style="display: none;">
		<form action="${ctx}/web/user/sl/giveUserSlcoin.htm" class="form-horizontal" id="j-activity">
			<div class="form-group">
				<label class="col-md-2 control-label">赠送：</label>
				<div class="col-md-8">
					<input type="text" value="" id="amount" name="amount" class="form-control" maxlength="5">
				</div>
				<div class="col-md-1 control-left">
					<label class="col-md-6 text-center">点</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-2"> </label>
				<small class="col-md-8">（范围 1- 10000整数）</small>
			</div>
			<div class="form-group">
				<label class="col-md-2 control-label">备注：</label>
				<div class="col-md-8">
					<textarea value="" id="remark" name="remark" class="form-control" rows="8" maxlength="128"></textarea>
				</div>
			</div>
		</form>
	</div>
	<!-- body end -->
	<script type="text/javascript">
		function giveyz(){
			art.dialog({
				lock : true,
				width : '600px',
				title : "赠送颜值",
				background : '#000', // 背景色
				opacity : 0.1, // 透明度
				content : document.getElementById("j-give-yz"),
				button : [{
					name : '保存',
					callback : function() {
						//赠送颜值用户
						//校验颜值
						var activity = $("#j-activity").validate({
							rules:{
								amount:{
									required:true,
									digits:true,
									range:[1,10000]
								},
								remark:{
									required:true,
									maxlength:128
								}
							},
							messages:{
								amount:{
									required:'赠送颜值不能为空',
									digits:'必须是整数',
									range:'范围 1- 10000'
								},
								remark:{
									required:'备注不能为空',
									maxlength:'备注不能超过128字'
								}
							}
						});
						if(activity.form()){
							var _amount = $("#amount").val(),
								_userId = $("#userId").val(),
								_url = $("form").attr("action");
							$.ajax({
			                    type: "POST",
			                    url: _url,
			                    data: {userId:_userId,amount:_amount,remark:$("#remark").val()},
			                    dataType: "json",
			                    beforeSend: function () {
			                   		$(".aui_buttons button:first-child").attr("disabled",true);
			                    },
			                    success: function(data){
			                    	if (data.success) {
										$("#totalSlcoin").text(parseInt(_amount)+parseInt($("#totalSlcoin").text()));
										$("#slcoinAmount").text(parseInt(_amount)+parseInt($("#slcoinAmount").text()));
										art.dialog.tips(data.info);
										$(".aui_buttons button:first-child").removeAttr("disabled");
										$("#amount").val("");
										$("#remark").val("");
				                  	}
			                    },
			                    error:function(){
			                    	$(".aui_buttons button:first-child").removeAttr("disabled"); 
				     				art.dialog.tips("系统异常");
								}
			                });
							return true;
						}else{
							return false;
						}
					},
					focus : true
				}, {
					name : '取消',
					callback : function() {
						$("#amount").val("");
						$("#remark").val("");
					},
					focus : true
				} ]
			});
		}
	</script>
</body>
</html>