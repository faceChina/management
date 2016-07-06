<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="resourcePath" value="${pageContext.request.contextPath}/resource/admin/" />

<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="UTF-8" />
		<%@include file="../../common/base.jsp" %>
		<title>刷脸用户基本资料-查看</title>	
		<!-- top -->
		<link href="${ctx}/resource/admin/css/fancy/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/resource/admin/css/fancy/main.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/resource/admin/css/fancy/main-gw.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/resource/admin/css/user/main-gl.css" rel="stylesheet" type="text/css" />
		<!--top end -->
		
		<script type="text/javascript">
			function closeShuaLianUserTab(name,url,icon,type){
        		window.parent.closeTabToAddTabUrl('shuaLianUser_tab',name,url,icon);
	        }
			//取消修改
			function cancel(){
				window.parent.closeTab("shuaLianUser_tab");
			}
			//修改
			function update(){
				window.parent.frames["Frm-ext-comp-" + window.parent.opentabId].editShuaLianUserById($('#userId').val());
			}
		</script>
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
									<li class="active"><a href="javascript:;" data-toggle="tab">基本资料</a></li>
									<li class=""><a href="${ctx}/web/user/sl/userAccountInfo.htm?userId=${shuaLianUser.userId}" data-toggle="tab">账户信息</a></li>
								</ul>
							</div>
							<div class="content">
								<ul class="media-info" style="width:45%;">
									<li class="poto">
										<img src="
											<c:choose>
												<c:when test="${shuaLianUser.user.headimgurl==null || shuaLianUser.user.headimgurl==''}">
													${resourcePath}/img/user/adm-head.png
												</c:when>
												<c:otherwise>
													${picUrl}${shuaLianUser.user.headimgurl}
												</c:otherwise>
											</c:choose>
										" alt="">
									</li>
									<li> </li>
									<li>账号：${shuaLianUser.user.loginAccount }</li>
									<li>注册时间：<fmt:formatDate value="${shuaLianUser.user.createTime }" pattern="yyyy年MM月dd日 HH:mm:ss"/></li>
								</ul>
	
								<ul class="media-info" style="width:35%;">
									<li> </li>
									<li>昵称：${shuaLianUser.user.nickname }</li>
									<li>最近一次登录时间：</li>
								</ul>
	
								<ul class="media-info" style="width:20%;">
									<li> </li>
									<li>&nbsp;</li>
									<li>登录次数：</li>
								</ul>
								<div class="clearfix"></div>
								<br />
								<form class="form-horizontal">
									<input type="hidden" id="userId" name="userId" value="${shuaLianUser.userId }"/>
									<table class="table table-bordered table-striped">
										<tbody>
											<tr>
												<td>姓名：${shuaLianUser.user.contacts }</td>
												<td>联系电话：
													<c:choose>
														<c:when test="${shuaLianUser.user.cell != null && shuaLianUser.user.cell != ''}">
															${shuaLianUser.user.cell }
														</c:when>
														<c:otherwise>
															${shuaLianUser.user.loginAccount }
														</c:otherwise>
													</c:choose>
												</td>
											</tr>
											
											<tr>
												<td>性别：
													<c:choose>
														<c:when test="${shuaLianUser.memberCard.sex == 0 }">
															女
														</c:when>
														<c:when test="${shuaLianUser.memberCard.sex == 1 }">
															男
														</c:when>
													</c:choose>
												</td>
												<td>年龄：</td>
											</tr>
											<tr>
												<td>邮箱：${shuaLianUser.user.email }</td>
												<td>生日：${shuaLianUser.memberCard.birthday }</td>
											</tr>
											<tr>
												<td>刷脸号：${shuaLianUser.user.myInvitationCode }</td>
												<td>邀请用户数：${shuaLianUser.invitationNum }，二度邀请用户数：${shuaLianUser.bisInvitationNum }</td>
											</tr>
											<tr>
												<td>公司：${shuaLianUser.businessCard.companyName }</td>
												<td>行业：${shuaLianUser.businessCard.industryName }</td>
											</tr>
											<tr>
												<td>职位：${shuaLianUser.businessCard.position }</td>
												<td>地区：${shuaLianUser.businessCard.vAreaName }</td>
											</tr>
											<tr>
												<td colspan="2">提供：${shuaLianUser.businessCard.industryProvide }</td>
											</tr>
											<tr>
												<td colspan="2">需要：${shuaLianUser.businessCard.industryRequirement }</td>
											</tr>
											<tr>
												<td colspan="2">身份证号码：${shuaLianUser.user.identity }</td>
											</tr>
										</tbody>
									</table>
	
									<div class="col-md-12 text-center">
										<a href="javaScript:update()" type="buttom" class="btn btn-default">修改</a>
										<a href="javaScript:cancel()" type="buttom" class="btn btn-default">取消</a>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- body end -->
	</body>
</html>

