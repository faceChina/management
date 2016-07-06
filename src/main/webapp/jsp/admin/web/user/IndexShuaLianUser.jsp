<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>刷脸用户列表</title>
		<%@include file="../../common/base.jsp" %>
		<script type="text/javascript" src="${jspPath}web/user/CenterGridShuaLianUser.js" charset="UTF-8"></script>
		
		<script type="text/javascript">
			//单击行获取的数据
			var rowClick = null;
			//获取总注册用户数,今日新增用户数失败地址
			var getUserNumMapUrl = ROOT_PATH+"/web/user/sl/getUserNumMap.htm";
			//资讯排序
			var updateInformationSortUrl = ROOT_PATH+"/web/information/updateSort.htm"; 
			
			function reloadGrid(){
				var gridPanel = Ext.getCmp('grid-shualian-view');
				gridPanel.queryData(1,'');
			}
			
			//根据Id查询刷脸用户
			function queryShuaLianUserById(){
				var url = ROOT_PATH + "/web/user/sl/userDetail.htm?userId=" + rowClick.userId
				
				if(window.parent.isExitsTab('shuaLianUser_tab')){
					window.parent.activateTab('shuaLianUser_tab');
					var tab = window.parent.getTab('shuaLianUser_tab');
					window.parent.frames["Frm-" + tab.id].closeShuaLianUserTab('查看刷脸用户',url,'sysmenu_mag');
				}else{
					window.parent.addTabUrl('shuaLianUser_tab','查看刷脸用户',url,'sysmenu_mag');
				}
			}
			//根据Id编辑刷脸用户
			function editShuaLianUserById(userId){
				var id = rowClick.userId;
				if(!isNull(userId)){
					id = userId;
				}
				
				var url = ROOT_PATH + "/web/user/sl/userEdit.htm?userId=" + id
						
				if(window.parent.isExitsTab('shuaLianUser_tab')){
					window.parent.activateTab('shuaLianUser_tab');
					var tab = window.parent.getTab('shuaLianUser_tab');
					window.parent.frames["Frm-" + tab.id].closeShuaLianUserTab('编辑刷脸用户',url,'sysmenu_mag');
				}else{
					window.parent.addTabUrl('shuaLianUser_tab','编辑刷脸用户',url,'sysmenu_mag');
				}
			}
			
			Ext.onReady(function() {
				Ext.QuickTips.init();
				var centerGridShuaLianUser = new CenterGridShuaLianUser({id:'f_panel_shualian_view'});
				new Ext.Viewport( {
					layout : 'fit',
					border : false,
					items : [centerGridShuaLianUser]
				});
				
				getUserNumMap();
				
				var gridPanel = Ext.getCmp('grid-shualian-view')
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
			
			function getUserNumMap(){
				Ext.Ajax.request({
					url:getUserNumMapUrl,
					method:'post',
					success : function(form, action) {
						var result = eval('(' + form.responseText + ')');
						var sum = result.sum;
						var today = result.today;
						
						if(!isNull(sum)){
							Ext.getCmp('UserSumNum').setText(sum);
						}
						
						if(!isNull(today)){
							Ext.getCmp('todayNewUserNum').setText(today);
						}
					},
					failure : function() {
						g_showTip("查询总注册用户数,今日新增用户数失败!");
					}
				});
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