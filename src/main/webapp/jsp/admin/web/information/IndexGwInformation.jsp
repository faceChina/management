<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>精选管理</title>
		<%@include file="../../common/base.jsp" %>
		<script type="text/javascript" src="${jspPath}web/information/CenterGridGwInformation.js" charset="UTF-8"></script>
		
		<script type="text/javascript">
			//单击行获取的数据
			var rowClick = null;
			//删除资讯中心地址
			var deleteInformationUrl = ROOT_PATH+"/web/information/delete.htm";
			//资讯排序
			var updateInformationSortUrl = ROOT_PATH+"/web/information/updateSort.htm"; 
			//删除资讯
			function deleteInformation(){
				var titleExt = Ext.getCmp('title').getValue();
				var statusExt = Ext.getCmp('status').getValue();
				
				if(statusExt == ''){
					statusExt = null;
				}
				
				console.log({'title':titleExt,'status':statusExt});
				delGridData('grid-information-view','id',deleteInformationUrl,1,{'title':titleExt,'status':statusExt});
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
				
				var toolbar = Ext.getCmp('informationToolBar');
				for(var j = 0; j < toolbar.items.length; j++) {
					if(toolbar.items.items[j].ref) {
						toolbar.items.items[j].setValue(toolbar.items.items[j].defaultValue);
					}
				}
				
				//console.log('-----');
				//console.log({'name':nameExt,'type':typeExt,'status':statusExt});
				var gridPanel = Ext.getCmp('grid-information-view');
				gridPanel.queryData(1,{'status':null});
			}
			
			//根据Id删除刷脸精选
			function deleteInformationById(){
				var msgValue = "确定要删除<font color='red'>" + rowClick.title + "</font>吗?";
				Ext.Msg.confirm("温馨提示", msgValue, function(msg){
					if(msg=='yes'){
						var titleExt = Ext.getCmp('title').getValue();
						var statusExt = Ext.getCmp('status').getValue();
						
						if(statusExt == ''){
							statusExt = null;
						}
						
						var resultValues = rowClick.id;
						
						delDataByValue(deleteInformationUrl,resultValues,'grid-information-view',1,{'title':titleExt,'status':statusExt});
					}
				});
			}
			//编辑资讯
			function editInformation(){
				var url = ROOT_PATH + "/web/information/initEdit.htm?id=" + rowClick.id
						
				if(window.parent.isExitsTab('information_tab')){
					window.parent.activateTab('information_tab');
					var tab = window.parent.getTab('information_tab');
					window.parent.frames["Frm-" + tab.id].closeInformationTab('修改资讯',url,'sysmenu_mag');
				}else{
					window.parent.addTabUrl('information_tab','修改资讯',url,'sysmenu_mag');
				}
				//g_showTip("编辑刷脸精选ID:" + rowClick.id);
			}
			//排序
			function updateSort(type){
				if(type==2 || type==-2){
					updateSortDate(type,rowClick.id);
					return;
				}
				var selectedValue = getCheckValues("grid-information-view","id",type);
				if(selectedValue == null){
					return;
				}
				if(selectedValue != ''){
					updateSortDate(type,selectedValue);
				}else{
					g_showTip('请选择数据!');
					return;
				}
				
			}
			function updateSortDate(sort,selectedValue){
				Ext.Ajax.request({
					url:updateInformationSortUrl,
					method:'post',
					params :{'sort':sort,'ids':selectedValue},
					success:function(form, action) {
						var result = eval('(' + form.responseText + ')');
						if(!result.success){
							g_showTip(result.resultDesc);	
						}else{
							//g_showTip('操作成功');
							reloadGrid();
							//disableAllButton();
						}
					},
					failure : function() {
						g_showTip("操作失败");
					}
				});
			}
			
			
			//获取选中行的ID和其下一行的ID
			function getCheckValues(gridId,valueName,sort){
				var params = '';
				var gridPanel = Ext.getCmp(gridId);
				var selects = gridPanel.getSelectionModel().getSelections();
				if(selects!=null&&selects.length>0){
					var selectsSize = selects.length;
					if(selectsSize == 1){
						var row = gridPanel.getStore().indexOf(selects[0]);
						var nextModel = gridPanel.getStore().getAt(row + sort);
						params = selects[0].data[valueName] + "-" + nextModel.data[valueName];
					}else{
						g_showTip("暂不支持多行排序");
						return null;
						/*
						for(i=0;i<selectsSize;i++){
							if(params == ''){
								params = selects[i].data[valueName];
							}else{
								params += "," + selects[i].data[valueName];
							}
						}

						var row = gridPanel.getStore().indexOf(selects[selectsSize-1]);
						var nextModel = gridPanel.getStore().getAt(row + sort);
						params = params + "-" + nextModel.data[valueName];
						*/
					}
				}

				console.log(params);
				return params;
			}
			
			Ext.onReady(function() {
				Ext.QuickTips.init();
				var centerGridGwInformation = new CenterGridGwInformation({id:'f_panel_information_view'});
				new Ext.Viewport( {
					layout : 'fit',
					border : false,
					items : [centerGridGwInformation]
				});
				
				var gridPanel = Ext.getCmp('grid-information-view')
				gridPanel.addListener('cellclick',cellclick);
				gridPanel.queryData(1,{'status':null});
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
							reloadGrid();
							/*
							if(reloadGridId!=null){
								Ext.getCmp(reloadGridId).queryData(submitType,params);
							}
							*/
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