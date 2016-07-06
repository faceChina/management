<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>精选管理</title>
		<%@include file="../../common/base.jsp" %>
		<script type="text/javascript" src="${jspPath}web/fancy/CenterGridFancyMessage.js" charset="UTF-8"></script>
		
		<script type="text/javascript">
			//单击行获取的数据
			var rowClick = null;
			//删除刷脸精选地址
			var deleteFancyMessageUrl = ROOT_PATH+"/web/fancy/batchDelete.htm";
			//删除刷脸精选
			function deleteFancyMessage(){
				var nameExt = Ext.getCmp('name').getValue();
				var statusExt = Ext.getCmp('status').getValue();
				var typeExt = Ext.getCmp('type').getValue();
				
				if(statusExt == ''){
					statusExt = null;
				}
				if(typeExt == ''){
					typeExt = null;
				}
				
				delGridData('grid-fancyMessage-view','id',deleteFancyMessageUrl,1,{'name':nameExt,'type':typeExt,'status':statusExt});
			}
			function reloadGrid(){
				/*
				var nameExt = Ext.getCmp('name').getValue();
				var statusExt = Ext.getCmp('status').getValue();
				var typeExt = Ext.getCmp('type').getValue();
				
				if(statusExt == ''){
					statusExt = null;
				}
				if(typeExt == ''){
					typeExt = null;
				}
				*/
				
				var toolbar = Ext.getCmp('fancyMessageToolBar');
				for(var j = 0; j < toolbar.items.length; j++) {
					if(toolbar.items.items[j].ref) {
						toolbar.items.items[j].setValue(toolbar.items.items[j].defaultValue);
					}
				}
				
				//console.log('-----');
				//console.log({'name':nameExt,'type':typeExt,'status':statusExt});
				var gridPanel = Ext.getCmp('grid-fancyMessage-view');
				gridPanel.queryData(1,{'type':null,'status':null});
			}
			
			//根据Id删除刷脸精选
			function deleteFancyMessageById(){
				var msgValue = "确定要删除<font color='red'>" + rowClick.name + "</font>吗?";
				Ext.Msg.confirm("温馨提示", msgValue, function(msg){
					if(msg=='yes'){
						var nameExt = Ext.getCmp('name').getValue();
						var statusExt = Ext.getCmp('status').getValue();
						var typeExt = Ext.getCmp('type').getValue();
						
						if(statusExt == ''){
							statusExt = null;
						}
						if(typeExt == ''){
							typeExt = null;
						}
						
						var resultValues = rowClick.id;
						
						delDataByValue(deleteFancyMessageUrl,resultValues,'grid-fancyMessage-view',1,{'name':nameExt,'type':typeExt,'status':statusExt});
					}
				});
			}
			//编辑刷脸精选
			function editFancyMessage(){
				var url = ROOT_PATH + "/web/fancy/initEdit.htm?id=" + rowClick.id
						
				if(window.parent.isExitsTab('fancyMessage_tab')){
					window.parent.activateTab('fancyMessage_tab');
					var tab = window.parent.getTab('fancyMessage_tab');
					window.parent.frames["Frm-" + tab.id].closeFancyMessageTab('修改精选',url,'sysmenu_mag');
				}else{
					window.parent.addTabUrl('fancyMessage_tab','修改精选',url,'sysmenu_mag');
				}
				//g_showTip("编辑刷脸精选ID:" + rowClick.id);
			}
			//生成二维码预览
			function QRCode(){
				window.open("${ctx}/web/fancy/QRCode.htm?id="+rowClick.id,"刷脸精选二维码预览","width=420,height=500");
			}
			
			Ext.onReady(function() {
				Ext.QuickTips.init();
				var centerGridFancyMessage = new CenterGridFancyMessage({id:'f_panel_fancyMessage_view'});
				new Ext.Viewport( {
					layout : 'fit',
					border : false,
					items : [centerGridFancyMessage]
				});
				
				var gridPanel = Ext.getCmp('grid-fancyMessage-view')
				gridPanel.addListener('cellclick',cellclick);
				gridPanel.queryData(1,{'type':null,'status':null});
			});
			//单击行获取数据
		 	function cellclick(grid,rowIndex,columnIndex, e){
			 	var record = grid.getStore().getAt(rowIndex);  // 返回Record对象 Get the Record
			 	rowClick = record.data;
        	}
			//重写回调函数
		 	function delDataByValue(delUrl,resultValues,reloadGridId,submitType,params,windowWidth,methodaudit){
				//console.log(resultValues);
				var value = resultValues.toString().split(',');
				var idArray = new Array();
				var names=new Array();
				
				for(var i=0;i<value.length;i++){
					var temp = value[i].split('-');
					idArray[i] = temp[0];
					names[i] = temp[1];
				}
				params.ids= idArray;
				Ext.Ajax.request({
					url:delUrl,
					method:'post',
					params : params,
					success : function(form, action) {
						var result = eval('(' + form.responseText + ')');
						if(!result.success){
							g_showTip(result.resultDesc);	
						}else{
							g_showTip('删除成功');	
							if(reloadGridId!=null){
								Ext.getCmp(reloadGridId).queryData(submitType,params);
							}
						}
					},
					failure : function() {
						g_showTip("删除失败!");
					}
				});
				return false;
			}
		</script>
	</head>
	<body>
	</body>
</html>