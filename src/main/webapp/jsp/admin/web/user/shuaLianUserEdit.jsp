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
		<title>刷脸用户基本资料-编辑</title>	
		<!-- top -->
		<link href="${ctx}/resource/admin/css/fancy/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/resource/admin/css/fancy/main.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/resource/admin/css/fancy/main-gw.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/resource/admin/css/user/main-gl.css" rel="stylesheet" type="text/css" />
		<!--top end -->
		
		<script type="text/javascript">
			$(function(){
				<c:choose>
					<c:when test="${succeed == 'true'}">
						window.parent.g_showTip("保存成功");
						window.parent.frames["Frm-ext-comp-" + window.parent.opentabId].reloadGrid();
						cancel();
					</c:when>
					<c:when test="${succeed == 'false'}">
						window.parent.g_showTip("${error}");
					</c:when>
				</c:choose>
			});
		
			//判断是否为空
			function isNull(value){
				if(value!=null &&  $.trim(value)!='' && value!='undefined'){
					return false;
				}
				return true;
			}
			//检测刷脸号是否存在
			function checkLpNo(obj,type){
				var lpNo = $('#myInvitationCode').val();
				if($.trim(lpNo)==''){
					window.parent.g_showTip('请输入刷脸号');
					return false;
				}
				if(isNull(lpNo)){
					lpNo = $('#myInvitationCode').attr("placeholder");
					if(isNull(lpNo)){
						window.parent.g_showTip('请输入刷脸号');
						return false;
					}
				}
				
				var flag = false;
				
				$.ajax({
                    type: "POST",
                    url: '${ctx}/web/user/sl/checkLpNo.htm',
                    data: {lpNo:lpNo,userId:$('#userId').val()},
                    dataType: "json",
                    async:false,
                    beforeSend: function () {
                    	obj.attr('disabled',true);
                    },
                    success: function(data){
                    	obj.attr('disabled',false);
                    	if(data.success){
                    		if(type==0){
                    			window.parent.g_showTip("刷脸号可以使用");
                    		}
							flag = true;
                    	}else{
                    		window.parent.g_showTip(data.resultDesc);
	                  		flag = false;
                    	}
                    	/*
                    	if(data.success){
							window.parent.g_showTip("刷脸号已存在");
							flag = false;
	                  	}else if(type==0&&!data.success){
							window.parent.g_showTip("刷脸号可以使用");
							flag = true;
	                  	}else{
	                  		window.parent.g_showTip(data.resultDesc);
	                  		flag = true;
	                  	}
                    	*/
                    },
                    error:function(){
                    	obj.attr('disabled',false);
                    	window.parent.g_showTip("系统异常");
                    	flag = false;
					}
                });
				return flag;
			}
			//确认修改
			function confirm(){
				<c:if test="${shuaLianUser.user.isUpdateCode == 0 }">
					if(!checkLpNo($('#lpNoCheckButton'))){
						return;
					}
				</c:if>
				
				$('#form1').submit();
			}
			//取消修改
			function cancel(){
				window.parent.closeTab("shuaLianUser_tab");
			}
			//关闭标签窗口
			function closeShuaLianUserTab(name,url,icon,type){
        		window.parent.closeTabToAddTabUrl('shuaLianUser_tab',name,url,icon);
	        }
			
			function changeCode(){//placeholder
				var value = $('#myInvitationCode').val();
				if(value==null || (value)=='' || value=='undefined'){
					$('#myInvitationCode').val($('#myInvitationCode').attr('placeholder'));
				}
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
									<li class="active"><a href="#" data-toggle="tab">基本资料</a></li>
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
								<form class="form-horizontal" id="form1" action="${ctx}/web/user/sl/updateUser.htm" method="post">
									<input type="hidden" id="userId" name="userId" value="${shuaLianUser.userId }"/>
									<table class="table table-bordered table-striped">
										<tbody>
											<tr>
												<td>
													<div class="pull-left col-md-12">姓名：${shuaLianUser.user.contacts }</div>
												</td>
												<td>
													<div class="pull-left col-md-12">联系电话：
														<c:choose>
															<c:when test="${shuaLianUser.user.cell != null && shuaLianUser.user.cell != ''}">
																${shuaLianUser.user.cell }
															</c:when>
															<c:otherwise>
																${shuaLianUser.user.loginAccount }
															</c:otherwise>
														</c:choose>
													</div>
												</td>
											</tr>
											
											<tr>
												<td class="radio-control">
													<div class="pull-left" style="padding-left: 4px;">性别：</div>
													<c:choose>
														<c:when test="${shuaLianUser.memberCard.sex == 0 }">
															女
														</c:when>
														<c:when test="${shuaLianUser.memberCard.sex == 1 }">
															男
														</c:when>
													</c:choose>
												</td>
												<td>
													<div class="pull-left control-left">年龄：</div>
													<div class="col-md-10">
													</div>
												</td>
											</tr>
											<tr>
												<td>
													<div class="pull-left control-left">邮箱：</div>
													<div class="col-md-10">
														<input type="text" name="user.email" id="user.email" value="${shuaLianUser.user.email }" class="form-control" placeholder="">
													</div>
												</td>
												<td>
													<div class="pull-left col-md-12">生日：${shuaLianUser.memberCard.birthday }</div>
												</td>
											</tr>
											<tr>
												<td>
													<div class="pull-left control-left">刷脸号：</div>
													
													<div class="col-md-8">
														<input type="text" name="user.myInvitationCode" id="myInvitationCode" maxlength="32" onblur="changeCode(this)"
															value="${shuaLianUser.user.myInvitationCode }" class="form-control" placeholder="${shuaLianUser.user.myInvitationCode }"
															<c:if test="${shuaLianUser.user.isUpdateCode == 1 }">
																disabled
															</c:if>
														>
													</div>
													<c:if test="${shuaLianUser.user.isUpdateCode == 0 }">
														<div class="col-md-2">
															<button type="button" class="btn btn-default" onclick="checkLpNo($('#lpNoCheckButton'),0)" id="lpNoCheckButton">检测</button>
														</div>
													</c:if>
												</td>
												<td>
													<div class="pull-left col-md-12">邀请用户数：${shuaLianUser.invitationNum }，二度邀请用户数：${shuaLianUser.bisInvitationNum }</div>
												</td>
											</tr>
											<tr>
												<td>
													<div class="pull-left control-left">公司：</div>
													<div class="col-md-10">
														<input type="text" name="businessCard.companyName" id="businessCard.companyName"  value="${shuaLianUser.businessCard.companyName }" class="form-control" placeholder="" maxLength="10">
													</div>
												</td>
												<td>
													<div class="pull-left col-md-12">行业：${shuaLianUser.businessCard.industryName }</div>
												</td>
											</tr>
											<tr>
												<td>
													<div class="pull-left control-left">职位：</div>
													<div class="col-md-10">
														<input type="text" name="businessCard.position" id="businessCard.position"  value="${shuaLianUser.businessCard.position }" class="form-control" placeholder="" maxLength="10">
													</div>
												</td>
												<td>
													<div class="pull-left col-md-12">地区：${shuaLianUser.businessCard.vAreaName }</div>
												</td>
											</tr>
											<tr>
												<td colspan="2">
													<div class="pull-left control-left">提供：</div>
													<div class="col-md-11">
														<input type="text" name="businessCard.industryProvide" id="businessCard.industryProvide"  value="${shuaLianUser.businessCard.industryProvide }" class="form-control" placeholder="" maxLength="45">
													</div>
												</td>
											</tr>
											<tr>
												<td colspan="2">
													<div class="pull-left control-left">需要：</div>
													<div class="col-md-11">
														<input type="text" name="businessCard.industryRequirement" id="businessCard.industryRequirement"  value="${shuaLianUser.businessCard.industryRequirement }" class="form-control" placeholder="" maxLength="45">
													</div>
												</td>
											</tr>
											<tr>
												<td colspan="2">
													<div class="pull-left control-left">身份证号码：${shuaLianUser.user.identity }</div>
												</td>
											</tr>
										</tbody>
									</table>
	
									<div class="col-md-12 text-center">
										<a href="javaScript:confirm()" type="buttom" class="btn btn-default">保存</a>
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

