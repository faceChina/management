<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>邀请注册列表</title>
		<%@include file="../../common/base.jsp" %>
		<script type="text/javascript" src="${jspPath}web/activity/CenterGridInvitationRegister.js" charset="UTF-8"></script>
		
		<script type="text/javascript">
			//刷新活动列表
			function reloadGrid(){
				var gridPanel = Ext.getCmp('grid-invitationRegister-view');
				gridPanel.queryData(1,'');
			}
			
			//判断是否为空
			function isNull(value){
				if(value!=null && value!='' && value!='undefined'){
					return false;
				}
				return true;
			}
			
			//导出数据
			function exportData(params){
				var nickname = params['nickname'];
				var loginAccountType = params['loginAccountType'];
				var loginAccount = params['loginAccount'];
				var createTimeStart = params['createTimeStart'];
				var createTimeEnd = params['createTimeEnd'];
				
				window.location.href = '${ctx }/web/activity/exportData.htm?nickname=' + nickname + '&loginAccountType=' + loginAccountType + '&loginAccount=' + loginAccount
				 																		+ '&createTimeStart=' + createTimeStart + '&createTimeEnd=' + createTimeEnd;
				/*
				$.ajax({
	                type: "GET",
	                url: '${ctx }/web/activity/exportData.htm',
	                data: params,
	                dataType: "json",
	                async:true,
	                beforeSend: function () {
	                },
	                success: function(data){
	                	if(data.success){
	                		//window.parent.g_showTip("操作成功");
							//reloadGrid();
	                	}else{
	                		window.parent.g_showTip(data.resultDesc);
	                	}
	                },
	                error:function(){
	        			window.parent.g_showTip('系统异常');
					}
	            });
				*/
			}
			
			Ext.onReady(function() {
				Ext.QuickTips.init();
				var centerGridInvitationRegister = new CenterGridInvitationRegister({id:'f_panel_invitationRegister_view'});
				new Ext.Viewport( {
					layout : 'fit',
					border : false,
					items : [centerGridInvitationRegister]
				});
				
				reloadGrid();
			});
		</script>
	</head>
	<body>
	</body>
</html>