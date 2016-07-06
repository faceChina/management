<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="resourcePath" value="${pageContext.request.contextPath}/resource/admin/" />

<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="UTF-8" />
		<title>创建活动</title>	
		<!-- top -->
		<script  type="text/javascript" src="${resourcePath}js/jquery-1.6.min.js"></script>
		
		<link href="${ctx}/resource/admin/css/fancy/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/resource/admin/css/fancy/main.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/resource/admin/css/fancy/main-gw.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/resource/admin/css/user/main-gl.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="${ctx}/resource/admin/js/My97DatePicker/calendar.js"></script>
		<script type="text/javascript" src="${ctx}/resource/admin/js/My97DatePicker/WdatePicker.js"></script>
		<link rel="stylesheet" href="${ctx}/resource/admin/js/My97DatePicker/skin/default/datepicker.css"/>
		
		<script type="text/javascript" src="${ctx}/resource/admin/js/artDialog/jquery.artDialog.js"></script>
		<script type="text/javascript" src="${ctx}/resource/admin/js/artDialog/iframeTools.js"></script>
		
		<!--top end -->
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
									<li class="active"><a href="#" data-toggle="tab">活动详情</a></li>
								</ul>
							</div>
	
							<div class="content">
								<form action="${ctx }/web/activity/insert.htm" id="jform" class="form-horizontal" method="post">
									<div class="form-group">
										<label class="col-sm-4 control-label">
											<b>活动名称：</b>
										</label>
										<div class="col-sm-4">
											<input class="form-control" type="text"  value="" id="name" name="name" placeholder="" maxlength="64">
										</div>
									</div>
	
									<div class="form-group">
										<label class="col-sm-4 control-label">
											<b>开始时间：</b>
										</label>
										<div class="col-md-4">
											<input type="text" id="beginTime" class="form-control dateFirst" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="readonly" name="beginTime" placeholder="">
										</div>
									</div>
	
									<div class="form-group">
										<label class="col-sm-4 control-label">
											<b>结束时间：</b>
										</label>
										<div class="col-md-4">
											<input type="text" id="endTime" class="form-control dateFirst" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="readonly" name="endTime" placeholder="">
										</div>
									</div>
									<hr>
									<div class="form-group">
										<label class="col-sm-4 control-label">
											<b>赠送设置　</b>
										</label>
									</div>
	
									<div class="form-group">
										<label class="col-sm-4 control-label">
											<b>邀请者赠送：</b>
										</label>
										<div class="col-sm-4">
											<input class="form-control" type="text" value="" id="inviteAmount" name="inviteAmount" placeholder="" maxlength="3">
										</div>
										<label class="col-sm-4"><span>个</span></label>
									</div>
	
									<div class="form-group">
										<label class="col-sm-4 control-label">
											<b>被邀请者赠送：</b>
										</label>
										<div class="col-sm-4">
											<input class="form-control" type="text" value="" id="registerAmount" name="registerAmount" placeholder="" maxlength="3">
										</div>
										<label class="col-sm-4"><span>个</span></label>
									</div>
	
									<div class="form-group">
										<label class="col-sm-4 control-label">
											<b>邀请者上级赠送：</b>
										</label>
										<div class="col-sm-4">
											<input class="form-control" type="text" value="" id="superAmount" name="registerAmount" placeholder="" maxlength="3">
										</div>
										<label class="col-sm-4"><span>个</span></label>
									</div>
	
									<div class="form-group">
										<div class="row text-center" style="margin-top:10px;">
											<a href="javaScript:save()" class="btn btn-default" data-step="1">保存</a>
											<button class="btn btn-default" type="button" onclick="cancel()">取消</button>
										</div>
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
	<script type="text/javascript">
		//取消创建
		function cancel(){
			art.dialog.confirm('确定要取消此活动的设置吗？', function() {
				close();
			}, function() {
				return true;
			});
		}
		//保存
		function save(){
			var name = $('#name').val();
			var beginTime = $('#beginTime').val();
			var endTime = $('#endTime').val();
			var inviteAmount = $('#inviteAmount').val();
			var registerAmount = $('#registerAmount').val();
			var superAmount = $('#superAmount').val();
			
			if(!isNum(inviteAmount)){
				art.dialog.tips('邀请者赠送点数只可输入正整数数字');
				return;
			}
			if(!isNum(registerAmount)){
				art.dialog.tips('被邀请者赠送点数只可输入正整数数字');
				return;
			}
			if(!isNum(superAmount)){
				art.dialog.tips('邀请者上级赠送点数只可输入正整数数字');
				return;
			}
			
			var param = {'name':name,'beginTimeVo':beginTime,'endTimeVo':endTime,'inviteAmount':inviteAmount,'registerAmount':registerAmount,'superAmount':superAmount};
			
			$.ajax({
                type: "POST",
                url: '${ctx }/web/activity/insert.htm',
                data: param,
                dataType: "json",
                async:true,
                beforeSend: function () {
        			$('.btn-default').attr('disabled',true);
                },
                success: function(data){
        			$('.btn-default').attr('disabled',false);
                	if(data.success){
                		window.parent.g_showTip("保存成功");
						window.parent.frames["Frm-ext-comp-" + window.parent.opentabId].reloadGrid();
                		close();
                	}else{
                		art.dialog.tips(data.resultDesc);
                	}
                },
                error:function(){
        			$('.btn-default').attr('disabled',false);
        			art.dialog.tips('系统异常');
				}
            });
		}
		//取消修改
		function close(){
			window.parent.closeTab("activityManage_tab");
		}
		//关闭标签窗口
		function closeActivityManageTab(name,url,icon,type){
			art.dialog.confirm('确认要关闭此窗口?', function() {
				window.parent.closeTabToAddTabUrl('activityManage_tab',name,url,icon);
			}, function() {
				return true;
			});
        }
		//判断是否为数字
		function isNum(nubmer){
	     	var re = /^[1-9]+[0-9]*]*$/;   //判断字符串是否为数字     //判断正整数 /^[1-9]+[0-9]*]*$/  
			    
	     	if(!re.test(nubmer)){
	        	return false;
	     	}
	     	return true;
		}
	</script>
</html>

