<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>活动管理</title>
		<%@include file="../../common/base.jsp" %>
		<script type="text/javascript" src="${jspPath}web/activity/CenterGridActivityManage.js" charset="UTF-8"></script>
		
		<style type="text/css">
			div.x-grid-record-gray { background: #C0C0C0; }
		</style>
		
		<script type="text/javascript">
			//单击行获取的数据
			var rowClick = null;
			
			//刷新活动列表
			function reloadGrid(){
				var gridPanel = Ext.getCmp('grid-activityManage-view');
				gridPanel.queryData(1,'');
			}
			//创建活动
			function addActivity(){
				var url = ROOT_PATH + "/jsp/admin/web/activity/activity-insert.jsp";
				
				if(window.parent.isExitsTab('activityManage_tab')){
					window.parent.activateTab('activityManage_tab');
					var tab = window.parent.getTab('activityManage_tab');
					window.parent.frames["Frm-" + tab.id].closeActivityManageTab('创建活动详情',url,'sysmenu_mag');
				}else{
					window.parent.addTabUrl('activityManage_tab','创建活动详情',url,'sysmenu_mag');
				}
			}
			//根据Id修改活动详情
			function updateActivityById(){
				var url = ROOT_PATH + "/web/activity/detail.htm?id=" + rowClick.id
						
				if(window.parent.isExitsTab('activityManage_tab')){
					window.parent.activateTab('activityManage_tab');
					var tab = window.parent.getTab('activityManage_tab');
					window.parent.frames["Frm-" + tab.id].closeActivityManageTab('编辑活动详情',url,'sysmenu_mag');
				}else{
					window.parent.addTabUrl('activityManage_tab','编辑活动详情',url,'sysmenu_mag');
				}
			}
			//立即开始活动
			function startActivityById(){
				updateTime(2);
			}
			//结束活动
			function closeActivityById(){
				updateTime(1);
			}
			function updateTime(type){
				var param = {'id':rowClick.id,'type':type};
				
				var msg = '确认要';
				
				if(type==1){
					msg += '结束该活动吗?';
				}else{
					msg += '立即开始该活动吗?';
				}
				
				Ext.Msg.confirm("温馨提示", msg, function(msg){
					if(msg=='yes'){
						$.ajax({
			                type: "POST",
			                url: '${ctx }/web/activity/updateTime.htm',
			                data: param,
			                dataType: "json",
			                async:true,
			                beforeSend: function () {
			                },
			                success: function(data){
			                	if(data.success){
			                		window.parent.g_showTip("操作成功");
									reloadGrid();
			                	}else{
			                		window.parent.g_showTip(data.resultDesc);
			                	}
			                },
			                error:function(){
			        			window.parent.g_showTip('系统异常');
							}
			            });
					}
				});
			}
			//查询活动详情
			function detailActivityById(){
				var url = ROOT_PATH + "/web/activity/detail.htm?id=" + rowClick.id + "&type=1"
				
				if(window.parent.isExitsTab('activityManage_tab')){
					window.parent.activateTab('activityManage_tab');
					var tab = window.parent.getTab('activityManage_tab');
					window.parent.frames["Frm-" + tab.id].closeActivityManageTab('查看活动详情',url,'sysmenu_mag');
				}else{
					window.parent.addTabUrl('activityManage_tab','查看活动详情',url,'sysmenu_mag');
				}
			}
			
			Ext.onReady(function() {
				Ext.QuickTips.init();
				var centerGridActivityManage = new CenterGridActivityManage({id:'f_panel_activityManage_view'});
				new Ext.Viewport( {
					layout : 'fit',
					border : false,
					items : [centerGridActivityManage]
				});
				
				var gridPanel = Ext.getCmp('grid-activityManage-view')
				gridPanel.addListener('cellclick',cellclick);
				
				reloadGrid();
			});
			
			//判断是否为空
			function isNull(value){
				if(value!=null && value!='' && value!='undefined'){
					return false;
				}
				return true;
			}
			//单击行获取数据
		 	function cellclick(grid,rowIndex,columnIndex, e){
			 	var record = grid.getStore().getAt(rowIndex);  // 返回Record对象 Get the Record
			 	rowClick = record.data;
        	}
		</script>
	</head>
	<body>
	</body>
</html>