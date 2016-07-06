<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>模块定制管理</title> 
		<%@include file="../../common/base.jsp" %>
		<script type="text/javascript" src="${ctx}/jsp/admin/web/prop/AddWindowProp.js" charset="UTF-8"></script>
		<script type="text/javascript" src="${ctx}/jsp/admin/web/prop/LeftViewTreePanelProp.js" charset="UTF-8"></script>
		<script type="text/javascript" src="${ctx}/jsp/admin/web/prop/AddWindowPropValue.js" charset="UTF-8"></script>
		<script type="text/javascript" src="${ctx}/jsp/admin/web/prop/RightCenterGridProp.js" charset="UTF-8"></script>
		<script type="text/javascript" src="${ctx}/jsp/admin/web/prop/IndexViewPanelProp.js" charset="UTF-8"></script>
		
	  	<script type="text/javascript">
			var jspChoosePropId;
		 	var jspIsColor = false;
		 	var jspChooseText;
		 	var jspIsAdd = true;
		 	var jspChooseIsStatus;
		 	var queryPropListUrl = ROOT_PATH+"/web/prop/list.htm";
		 	var addPropUrl = ROOT_PATH+"/web/prop/add.htm";
		 	var modifySysModuleUrl = ROOT_PATH+"/web/prop/add.htm";
		 	var queryWebTopicDetailGridUrl = ROOT_PATH+"/web/prop/propValueList.htm";
			var addPropValueUrl = ROOT_PATH+"/web/prop/savePropValue.htm";
			//修改类目属性状态
			var editPropStatusUrl = ROOT_PATH+"/web/prop/editStatus.htm";
			//删除类目属性
			var delPropStatusUrl = ROOT_PATH+"/web/prop/delete.htm";
			//查询商品属性
			var queryPropDetail = ROOT_PATH+"/web/prop/selectById.htm";
			//修改商品属性
			var updatePropDetailUrl = ROOT_PATH+"/web/prop/update.htm";
			//删除商品属性
			var deletePropValueUrl = ROOT_PATH + "/web/prop/deletePropValueList.htm";
			//修改商品属性排序
			var updatePropValueSortUrl = ROOT_PATH + "/web/prop/updatePropValueSort.htm";
			 
			//重新创建右边菜单信息模板信息 如果右边菜单信息组件存在 则加载左边树的信息  用于 点击左边树的节点或是新增一级菜单时所用
		    function jspInitRightFormPanel(propId,propText,customJson,isAdd){
	    		jspChoosePropId=propId;
	    		jspChooseText = propText;
	    		jspIsAdd = isAdd;
	    		
	    		var result = eval('(' + customJson + ')');
	    		if(null!=result && ''!=result && 'undefined'!=result){
	    			jspIsColor = result.isColor;
    		 		jspChooseIsStatus = result.status;
				}
	    		
			    var tabPanelProp = Ext.getCmp('f1_gridpanel_prop_view');//左边TAB总体页面
		    	//if(!tabPanelProp){//如果左边Tab没被创建过 则新创建TAB右边模块信息 并加入
			    	var propPanel = new RightCenterGridProp({
						id:'f1_gridpanel_prop_view',
						region:'center',
						activeTab: 0,
						width:340,
						isColor:jspIsColor,
						border:false,
						labelWidth :60
					})
				 	changeObjectCmpByTree('f1_panel_prop_view',propPanel);//删除原有右边模块信息模块里的组件 重新创建
		   		//}
			    jspReloadGrid(propId);
			    controlButtin();
	    	}
		  	//控制页面按钮显示
			function controlButtin(){
				if(jspChooseIsStatus == 1){
		 			//Ext.getCmp('tbar_prop_add').disable();
		 			Ext.getCmp('tbar_prop_addEdit').disable();
		 			//Ext.getCmp('tbar_prop_addEdit').hide();
		 			Ext.getCmp('tbar_prop_del').disable();
		 			Ext.getCmp('tbar_prop_detail').enable();
		 			Ext.getCmp('tbar_prop_detail').show();
		 			Ext.getCmp('tbar_prop_update').hide();
		 			
		 			//Ext.getCmp('tbar_prop_hide').enable();
		 			//Ext.getCmp('tbar_prop_hide').show();
		 			
		 			if(Ext.getCmp('tbar_casechild_add')){
		 				Ext.getCmp('tbar_casechild_add').disable();
		 			}
		 			if(Ext.getCmp('tbar_propValue_delete')){
		 				Ext.getCmp('tbar_propValue_delete').disable();
		 			}

		 			/*
		 			if(Ext.getCmp('tbar_propValue_down')){
		 				Ext.getCmp('tbar_propValue_down').disable();
		 			}
		 			if(Ext.getCmp('tbar_propValue_up')){
		 				Ext.getCmp('tbar_propValue_up').disable();
		 			}
		 			*/
		 		}else if(jspChooseIsStatus == -1){
		 			//Ext.getCmp('tbar_prop_add').disable();
		 			Ext.getCmp('tbar_prop_addEdit').enable();
		 			//Ext.getCmp('tbar_prop_addEdit').show();
		 			Ext.getCmp('tbar_prop_del').disable();
		 			Ext.getCmp('tbar_prop_detail').enable();
		 			Ext.getCmp('tbar_prop_detail').show();
		 			Ext.getCmp('tbar_prop_update').hide();
		 			
		 			//Ext.getCmp('tbar_prop_hide').disable();
		 			//Ext.getCmp('tbar_prop_hide').hide();
		 			
		 			if(Ext.getCmp('tbar_casechild_add')){
		 				Ext.getCmp('tbar_casechild_add').disable();
		 			}
		 			if(Ext.getCmp('tbar_propValue_delete')){
		 				Ext.getCmp('tbar_propValue_delete').disable();
		 			}

		 			/*
		 			if(Ext.getCmp('tbar_propValue_down')){
		 				Ext.getCmp('tbar_propValue_down').disable();
		 			}
		 			if(Ext.getCmp('tbar_propValue_up')){
		 				Ext.getCmp('tbar_propValue_up').disable();
		 			}
		 			*/
		 		}else{
		 			Ext.getCmp('tbar_prop_addEdit').enable();
		 			//Ext.getCmp('tbar_prop_addEdit').show();
		 			Ext.getCmp('tbar_prop_del').enable();
		 			Ext.getCmp('tbar_prop_detail').hide();
		 			Ext.getCmp('tbar_prop_update').enable();
		 			Ext.getCmp('tbar_prop_update').show();
		 			
		 			//Ext.getCmp('tbar_prop_hide').disable();
		 			//Ext.getCmp('tbar_prop_hide').hide();

		 			if(Ext.getCmp('tbar_casechild_add')){
		 				Ext.getCmp('tbar_casechild_add').enable();
		 			}
		 			if(Ext.getCmp('tbar_propValue_delete')){
		 				Ext.getCmp('tbar_propValue_delete').enable();
		 			}

		 			/*
		 			if(Ext.getCmp('tbar_propValue_down')){
		 				Ext.getCmp('tbar_propValue_down').enable();
		 			}
		 			if(Ext.getCmp('tbar_propValue_up')){
		 				Ext.getCmp('tbar_propValue_up').enable();
		 			}
		 			*/
		 		}
		  	}
		  	//修改商品类目状态
			function editPropStatus(chooseId,status){
				$.ajax({
					url:editPropStatusUrl,
					type:'post',
					dataType:'text',
					data:{'propId':chooseId,'status':status},
					success:function(data){
						var result = eval('(' + data + ')');
						if(result.success){
							if(status == '1'){
								g_showTip('发布成功');
							}else if(status == '-1'){
								g_showTip('隐藏成功');
							}else{
								g_showTip('操作成功');
							}
							//刷新树
							var tree = Ext.getCmp('f1_treepanel_prop_view');
							if (tree != undefined) {
								tree.root.reload();
							}
							disableAllButton();
						}else{
							g_showTip(result.resultDesc);
						}
					},
					error:function(){
						g_showTip('系统繁忙');	
					}
				});
			}
			//删除商品类目
			function delPropStatus(chooseId){
				$.ajax({
					url:delPropStatusUrl,
					type:'post',
					dataType:'text',
					data:{'propId':chooseId},
					success:function(data){
						var result = eval('(' + data + ')');
						if(result.success){
							g_showTip('删除成功');
							//刷新树
							var tree = Ext.getCmp('f1_treepanel_prop_view');
							if (tree != undefined) {
								tree.root.reload();
							}
							jspReloadGrid(chooseId); 
							disableAllButton();
						}else{
							g_showTip(result.resultDesc);
						}
					},
					error:function(){
						g_showTip('系统繁忙');	
					}
				});
			}
			//修改商品属性
			function updateProp(chooseId){
				var formWindow = Ext.getCmp('window-prop-update');
				if(!formWindow){
					formWindow = new AddWindowProp({title:'修改商品属性',id:'window-prop-update',isUpdate:true}).show();
					formWindow.loadForm(chooseId);
				}else{
					formWindow.show();
					formWindow.loadForm(chooseId);
				}
			}
			//显示商品属性
			function detailProp(chooseId){
				var formWindow = Ext.getCmp('window-prop-detail');
				if(!formWindow){
					formWindow = new AddWindowProp({title:'商品属性详细信息',id:'window-prop-detail',isDetail:true}).show();
					formWindow.loadForm(chooseId);
				}else{
					formWindow.show();
					formWindow.loadForm(chooseId);
				}
			}
			//删除商品属性与商品属性值关联关系
			function deletePropValue(chooseId){
				delGridData('grid-propValuechild-view','id,name',deletePropValueUrl,1,{"propertyId":chooseId});
			}
			//刷新明细列表方法
			function jspReloadGrid(propId){
				var gridPanel = Ext.getCmp('grid-propValuechild-view');
				gridPanel.queryData(1,{"propertyId":propId});
			}
			//禁用所有按钮
			function disableAllButton(){
				Ext.getCmp('tbar_prop_addEdit').disable();
				Ext.getCmp('tbar_prop_del').disable();
	 			Ext.getCmp('tbar_prop_update').disable();
	 			Ext.getCmp('tbar_prop_detail').disable();

	 			if(Ext.getCmp('tbar_casechild_add')){
	 				Ext.getCmp('tbar_casechild_add').disable();
	 			}
	 			if(Ext.getCmp('tbar_propValue_delete')){
	 				Ext.getCmp('tbar_propValue_delete').disable();
	 			}

	 			/*
	 			if(Ext.getCmp('tbar_propValue_down')){
	 				Ext.getCmp('tbar_propValue_down').disable();
	 			}
	 			if(Ext.getCmp('tbar_propValue_up')){
	 				Ext.getCmp('tbar_propValue_up').disable();
	 			}
	 			*/
			}  
		    function jspInitAddPropWindow(smPid){
		        var formWindow = Ext.getCmp('window-prop-add');
				if(!formWindow){
					new AddWindowProp({title:'新增商品属性',id:'window-prop-add',pid:smPid,isAdd:true}).show();
				}else formWindow.show();
			}
		    function jspAddWindowPropValue(){
		    	var formWindow = Ext.getCmp('window-propValue-add');
				if(!formWindow){
					new AddWindowPropValue({title:'新增商品属性值',id:'window-propValue-add',propertyId:jspChoosePropId,isColor:jspIsColor}).show();
				}else formWindow.show();
		    }
			//修改商品属性值排序
			function updatePropValueSort(sort,ids){
				//console.log({'sort':sort,'ids':ids});
				Ext.Ajax.request({
					url:updatePropValueSortUrl,
					method:'post',
					params :{'sort':sort,'ids':ids},
					success:function(form, action) {
						var result = eval('(' + form.responseText + ')');
						if(!result.success){
							g_showTip(result.resultDesc);	
						}else{
							//g_showTip('操作成功');
							jspReloadGrid(jspChoosePropId);
							//disableAllButton();
						}
					},
					failure : function() {
						g_showTip("操作失败");
					}
				});
			}
		 	Ext.onReady(function() {
	     		Ext.QuickTips.init();
		     	var propManageView = new PropManageView({id:'f_panel_prop_view'});
		     	new Ext.Viewport( {
	            	layout : 'fit',
		            border : false,
		            items : [propManageView]
		      	});
			});
		 	//重写回调函数
		 	function delDataByValue(delUrl,resultValues,reloadGridId,submitType,params,windowWidth,methodaudit){
				var value = resultValues.split(',');
				var idArray = new Array();
				var names=new Array();
				
				for(var i=0;i<value.length;i++){
					var temp = value[i].split('-');
					idArray[i] = temp[0];
					names[i] = temp[1];
				}
				/*
				var msgValue ="确定要删除吗?";
				var _strNames = '';
				if(names[0]!='undefined'){
					for(i=0,size=names.length;i<size;i++){
						_strNames+='<font color="red">'+names[i]+'</font>';
						if((i+1)<size){
							_strNames+=',';
						}
					}
					msgValue="确定要删除以下记录吗:<br /><div style:'line-height:24px'>"+_strNames+"</div>";
				}
				Ext.MessageBox.minWidth = windowWidth;
	
				Ext.Msg.confirm("温馨提示", msgValue, function(msg){
					if(msg=='yes'){
				*/
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
									//disableAllButton();
								}
								/* 
								g_showTip("删除成功");
								if(reloadGridId!=null){
									Ext.getCmp(reloadGridId).queryData(submitType,params);
								}
								if(methodaudit!=undefined){
									methodaudit();
								} */
							},
							failure : function() {
								g_showTip("删除失败!");
							}
						});
				/*
					}
				});
				*/
				return false;
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
		</script>
	</head>
	<body>
	</body>
</html>