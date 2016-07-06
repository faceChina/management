<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>商品品类管理</title>
		<%@include file="../../common/base.jsp" %>
		<script type="text/javascript" src="${jspPath}web/classifcation/IndexViewPanelClassifcation.js" charset="UTF-8"></script>
		<script type="text/javascript" src="${jspPath}web/classifcation/LeftViewTreePanelClassifcation.js" charset="UTF-8"></script>
		<script type="text/javascript" src="${jspPath}web/classifcation/RightCenterGridClassifcation.js" charset="UTF-8"></script>
		<script type="text/javascript" src="${jspPath}web/classifcation/EditWindowClassifcation.js" charset="UTF-8"></script>
		<script type="text/javascript" src="${jspPath}web/classifcation/AddGridWindowClassifcationRelationType.js" charset="UTF-8"></script>
		
		<script type="text/javascript">
			//为左边选中的节点赋值 以备组织机构列表关联模块查询
			var jspChooseId;
			//是否是叶子节点
			var jspIsAdd = true;
			//左边选中的节点name
			var jspChooseText;
			//左边选中的节点状态
			var jspChooseIsStatus;
			var jspChooseLevel;
			//树节点路径
			var nodePath;
			//品类树查询地址
			var queryClassifcationTreeNodeUrl = ROOT_PATH+"/web/classification/tree.htm";
			var updateWebTopicParentInfoUrl = ROOT_PATH+"/web/classification/save.htm";
			var queryClassifcationPropRealtionGridUrl = ROOT_PATH+"/web/classification/relationList.htm";
			var queryTopicDetailGridUrl = ROOT_PATH+"/web/prop/propListFilterClass.htm";
			//修改商品类目状态
			var editClassifcationStatusUrl = ROOT_PATH+"/web/classification/editStatus.htm";
			//删除商品类目
			var delClassifcationStatusUrl = ROOT_PATH+"/web/classification/delete.htm";
			//保存属性关联
			var addRealtionUrl = ROOT_PATH+"/web/classification/saveRelationList.htm";
			//查询商品类目信息
			var queryClssifcationDetail = ROOT_PATH + "/web/classification/selectById.htm";
			//修改商品类目信息
			var updateClssifcationDetail = ROOT_PATH + "/web/classification/update.htm";
			//删除商品类目关联关系
			var deleteClssifcationPropRealtion = ROOT_PATH + "/web/classification/deleteRealtionList.htm";
			//修改商品类目和商品属性关联关系排序
			var updateClassificationPropRealtionSortUrl = ROOT_PATH + "/web/classification/updateRealtionSort.htm";
			//重新创建右边参数信息 如果右边参数信息组件存在 则加载左边树的信息  用于点击左边树的节点时所用
			function jspInitRightFormPanel(id,isadd,text,customJson){
				//关闭品类关联窗口
			 	var formWindow = Ext.getCmp('window-addClassifcationRelation-add');
			 	if(formWindow){
			 		formWindow.close();
			 	}
			 	
				jspChooseId= null;
			    jspChooseId=id;
			    jspIsAdd=isadd;
			    jspChooseText=text;
			    
			    if(null!=customJson && ''!=customJson && 'undefined'!=customJson){
			    	jspChooseIsStatus = eval('(' + customJson + ')').status;
			    	jspChooseLevel = eval('(' + customJson + ')').level;
			    }else{
			    	jspChooseLevel = 0;
			    }
			    
			    if(isadd){
			    	var panelModule = Ext.getCmp('f1_gridpanel_classifcation_view');
			    	if(!panelModule){
				    	var rightCenterGridClassifcation =new RightCenterGridClassifcation({
							id:'f1_gridpanel_classifcation_view',
							region:'fit',
							border:false,
							tid:id,
							labelWidth :60
						});
				 		changeObjectCmpByTree('f1_panel_classifcation_view',rightCenterGridClassifcation);//删除原有右边模块信息模块里的组件 重新创建
			 		}
			 		jspReloadGrid();
				}
			    
			    controlButtin();
			    
			    /*
			    var indexViewPanel = Ext.getCmp('f_panel_classifcation_view');
			    indexViewPanel.west = new LeftViewTreePanelClassifcation({
					id:'f1_treepanel_classifcation_view',
					border:false,
					floating:true,
					title:'商品类目管理',
					layoutConfig:{animate:false},
					iconCls :"organ_mag",	
					width:300,
					region:'west'
				});
			    indexViewPanel.center = new Ext.Panel({id:'f1_panel_classifcation_view',margins : '5 0 0 5',cmargins : '5 0 0 5',region:'center',layout:'fit'});
			    */
			}
			//控制页面按钮显示
			function controlButtin(){
				//已发商品类目不可删除,发布,修改
		 		if(jspChooseIsStatus == 1){
		 			Ext.getCmp('tbar_classifcation_del').disable();
		 			Ext.getCmp('tbar_classifcation_update').disable();
		 			Ext.getCmp('tbar_classifcation_update').hide();
		 			
		 			if(jspIsAdd){
			 			Ext.getCmp('tbar_classifcation_addEdit').disable();
			 			Ext.getCmp('tbar_classifcation_addEdit').hide();
			 			Ext.getCmp('tbar_classifcation_hide').enable();
			 			Ext.getCmp('tbar_classifcation_hide').show();
		 			}else{
			 			Ext.getCmp('tbar_classifcation_addEdit').disable();
			 			Ext.getCmp('tbar_classifcation_addEdit').hide();
			 			Ext.getCmp('tbar_classifcation_hide').enable();
			 			Ext.getCmp('tbar_classifcation_hide').show();
		 			}
		 			
		 			if(Ext.getCmp('tbar_typeRelation_add')){
		 				Ext.getCmp('tbar_typeRelation_add').disable();
		 			}
		 			if(Ext.getCmp('tbar_typeRelation_delete')){
		 				Ext.getCmp('tbar_typeRelation_delete').disable();
		 			}
		 			
		 			/*
		 			if(Ext.getCmp('tbar_typeRelation_down')){
		 				Ext.getCmp('tbar_typeRelation_down').disable();
		 			}
		 			if(Ext.getCmp('tbar_typeRelation_up')){
		 				Ext.getCmp('tbar_typeRelation_up').disable();
		 			}
		 			*/
		 		}else if(jspChooseIsStatus == -1){
		 			Ext.getCmp('tbar_classifcation_del').disable();
		 			Ext.getCmp('tbar_classifcation_update').disable();
		 			Ext.getCmp('tbar_classifcation_update').hide();
		 			
		 			if(jspIsAdd){
			 			Ext.getCmp('tbar_classifcation_addEdit').enable();
			 			Ext.getCmp('tbar_classifcation_addEdit').show();
			 			Ext.getCmp('tbar_classifcation_hide').disable();
			 			Ext.getCmp('tbar_classifcation_hide').hide();
		 			}else{
			 			Ext.getCmp('tbar_classifcation_addEdit').enable();
			 			Ext.getCmp('tbar_classifcation_addEdit').show();
			 			Ext.getCmp('tbar_classifcation_hide').disable();
			 			Ext.getCmp('tbar_classifcation_hide').hide();
		 			}
		 			
		 			if(Ext.getCmp('tbar_typeRelation_add')){
		 				Ext.getCmp('tbar_typeRelation_add').disable();
		 			}
		 			if(Ext.getCmp('tbar_typeRelation_delete')){
		 				Ext.getCmp('tbar_typeRelation_delete').disable();
		 			}
		 			
		 			/*
		 			if(Ext.getCmp('tbar_typeRelation_down')){
		 				Ext.getCmp('tbar_typeRelation_down').disable();
		 			}
		 			if(Ext.getCmp('tbar_typeRelation_up')){
		 				Ext.getCmp('tbar_typeRelation_up').disable();
		 			}
		 			*/
		 		}else{
		 			Ext.getCmp('tbar_classifcation_del').enable();
		 			Ext.getCmp('tbar_classifcation_update').enable();
		 			Ext.getCmp('tbar_classifcation_update').show();
		 			
		 			//非叶子节点不可新增关联关系
		 			if(jspIsAdd){
			 			Ext.getCmp('tbar_classifcation_addEdit').enable();
			 			Ext.getCmp('tbar_classifcation_addEdit').show();
			 			Ext.getCmp('tbar_classifcation_hide').disable();
			 			Ext.getCmp('tbar_classifcation_hide').hide();
			 			if(Ext.getCmp('tbar_typeRelation_add')){
			 				Ext.getCmp('tbar_typeRelation_add').enable();
			 			}
			 			if(Ext.getCmp('tbar_typeRelation_delete')){
			 				Ext.getCmp('tbar_typeRelation_delete').enable();
			 			}
			 			
			 			if(Ext.getCmp('tbar_typeRelation_down')){
			 				Ext.getCmp('tbar_typeRelation_down').enable();
			 			}
			 			if(Ext.getCmp('tbar_typeRelation_up')){
			 				Ext.getCmp('tbar_typeRelation_up').enable();
			 			}
			 		}else{
			 			Ext.getCmp('tbar_classifcation_addEdit').enable();
			 			Ext.getCmp('tbar_classifcation_addEdit').show();
			 			Ext.getCmp('tbar_classifcation_hide').disable();
			 			Ext.getCmp('tbar_classifcation_hide').hide();
			 			if(Ext.getCmp('tbar_typeRelation_add')){
			 				Ext.getCmp('tbar_typeRelation_add').disable();
			 			}
			 			if(Ext.getCmp('tbar_typeRelation_delete')){
			 				Ext.getCmp('tbar_typeRelation_delete').disable();
			 			}
			 			
			 			/*
			 			if(Ext.getCmp('tbar_typeRelation_down')){
			 				Ext.getCmp('tbar_typeRelation_down').disable();
			 			}
			 			if(Ext.getCmp('tbar_typeRelation_up')){
			 				Ext.getCmp('tbar_typeRelation_up').disable();
			 			}
			 			*/
			 		}
		 		}
		 		//商品类目最多只能存在三级
		 		if(jspIsAdd){
		 			Ext.getCmp('tbar_classifcation_add').disable();
		 		}else{
		 			Ext.getCmp('tbar_classifcation_add').enable();
		 		}
			}
			//修改商品类目状态
			function editClassifcationStatus(chooseId,status){
				$.ajax({
					url:editClassifcationStatusUrl,
					type:'post',
					dataType:'text',
					data:{'classifcationId':chooseId,'status':status},
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
							var tree = Ext.getCmp('f1_treepanel_classifcation_view');
							if (tree != undefined) {
								tree.root.reload();
								//tree.expandAll();
								tree.expandPath(nodePath);
								//tree.selectPath(nodePath);
								/*
								var node = tree.getSelectionModel().getSelectedNode();  
        						var path = node.getPath('f1_treepanel_classifcation_view');  
						        //展开路径,并在回调函数里面选择该节点  
					        	tree.expandPath(path,'f1_treepanel_classifcation_view',function(bSucess,oLastNode){  
						          	tree.getSelectionModel().select(oLastNode);  
						        }); */
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
			function delClassifcationStatus(chooseId){
				$.ajax({
					url:delClassifcationStatusUrl,
					type:'post',
					dataType:'text',
					data:{'classifcationId':chooseId},
					success:function(data){
						var result = eval('(' + data + ')');
						if(result.success){
							g_showTip('删除成功');
							//刷新树
							var tree = Ext.getCmp('f1_treepanel_classifcation_view');
							if (tree != undefined) {
								tree.root.reload();
								//tree.expandAll();
								tree.expandPath(nodePath);
							}
							jspReloadGrid(); 
						}else{
							g_showTip(result.resultDesc);
						}
						disableAllButton();
					},
					error:function(){
						g_showTip('系统繁忙');	
					}
				});
			}
			//修改商品类目
			function updateClassifcation(chooseId){
				var formWindow = Ext.getCmp('window-classifcation-update');
				if(!formWindow){
					formWindow = new EditWindowClassifcation({title:'修改',width:300,height:130,id:'window-classifcation-update',isUpdate:true,level:2}).show();
					formWindow.loadForm(chooseId);
				}else{
					formWindow.show();
					formWindow.loadForm(chooseId);
				}
			}
			//禁用所有按钮
			function disableAllButton(){
				Ext.getCmp('tbar_classifcation_add').disable();
				Ext.getCmp('tbar_classifcation_addEdit').disable();
	 			Ext.getCmp('tbar_classifcation_del').disable();
	 			Ext.getCmp('tbar_classifcation_update').disable();
	 			Ext.getCmp('tbar_classifcation_hide').disable();
	 			if(Ext.getCmp('tbar_typeRelation_add')){
	 				Ext.getCmp('tbar_typeRelation_add').disable();
	 			}
	 			if(Ext.getCmp('tbar_typeRelation_delete')){
	 				Ext.getCmp('tbar_typeRelation_delete').disable();
	 			}
	 			
	 			/*
	 			if(Ext.getCmp('tbar_typeRelation_down')){
	 				Ext.getCmp('tbar_typeRelation_down').disable();
	 			}
	 			if(Ext.getCmp('tbar_typeRelation_up')){
	 				Ext.getCmp('tbar_typeRelation_up').disable();
	 			}
	 			*/
			}
			//删除商品类目和商品属性关联关系
			function deleteClassificationPropRealtion(chooseId){
				addGridData('grid-classifcationRelation-view','propId,name',deleteClssifcationPropRealtion,1,{"classificationId":jspChooseId},'grid-classifcationRelation-view');
			}
			//新增一级目录
			function jspInitAddTypeWindow(tpid){
			    var formWindow = Ext.getCmp('window-classifcation-add');
			    /*
				if(!formWindow){
					new EditWindowClassifcation({title:'新增',width:300,height:130,id:'window-classifcation-add',tpid:tpid,level:jspChooseLevel}).show();
				}else{
					formWindow.setTitle('新增');
					formWindow.show();
				}
			    */
			    if(formWindow){
			    	formWindow.close();
				}

			    console.log(jspChooseLevel);
				new EditWindowClassifcation({title:'新增',width:300,height:130,id:'window-classifcation-add',tpid:tpid,level:jspChooseLevel}).show();
			}
			//刷新明细列表方法
			function jspReloadGrid(){
				var gridPanel = Ext.getCmp('grid-classifcationRelation-view');
				//清空所有选择
				//gridPanel.getSelectionModel().clearSelections(); 
		     	gridPanel.getStore().proxy = new Ext.data.HttpProxy({url:queryClassifcationPropRealtionGridUrl});
			 	gridPanel.getStore().load({params:{"classificationId":jspChooseId,start:0, limit:15}});
			}
			//新增参数信息
			function jspInitAddWebTopicDetailWindow(){
				var formWindow = Ext.getCmp('window-addClassifcationRelation-add');
				if(!formWindow){
					new AddGridWindowType({title:'商品类目属性关联',height:500,x:330 ,id:'window-addClassifcationRelation-add'}).show();
				}else{
					formWindow.show();
				}
			}
			//修改商品类目和商品属性关联关系排序
			function updateClassificationPropRealtionSort(sort,ids){
				//console.log({'sort':sort,'ids':ids});
				Ext.Ajax.request({
					url:updateClassificationPropRealtionSortUrl,
					method:'post',
					params :{'sort':sort,'ids':ids},
					success:function(form, action) {
						var result = eval('(' + form.responseText + ')');
						if(!result.success){
							g_showTip(result.resultDesc);	
						}else{
							//g_showTip('操作成功');
							jspReloadGrid();
							//disableAllButton();
						}
					},
					failure : function() {
						g_showTip("操作失败");
					}
				});
			}
			/*
			function jspInitAddWebTopicDetailWindow(tpid){
			    var formWindow = Ext.getCmp('window-prop-add');
				if(!formWindow){
					new EditWindowProp({title:'新增',width:500,height:350,id:'window-prop-add',tpid:tpid}).show();
				}else{
					formWindow.show();
				}
			}*/
			Ext.onReady(function() {
				Ext.QuickTips.init();
				var indexViewPanelClassifcation = new IndexViewPanelClassifcation({id:'f_panel_classifcation_view'});
				new Ext.Viewport( {
					layout : 'fit',
						border : false,
						items : [indexViewPanelClassifcation]
				});
			});
			//重写回调函数
			function addDataByValue(addUrl,resultValues,reloadGridId,submitType,params){
				var value = resultValues.split(',');
				var idArray = new Array();
				for(i=0;i<value.length;i++){
					var temp = value[i].split('-');
					idArray[i]=temp[0];
				}
				vparams = params;
				vparams.ids= idArray;
				Ext.Ajax.request({
					url:addUrl,
					method:'post',
					params :vparams,
					success:function(form, action) {
						var result = eval('(' + form.responseText + ')');
						if(!result.success){
							g_showTip(result.resultDesc);	
						}else{
							g_showTip('操作成功');	
							if(reloadGridId!=null){
								Ext.getCmp(reloadGridId).queryData(submitType,params);
							}
							//disableAllButton();
						}
					},
					failure : function() {
						g_showTip("操作失败");
					}
				});
			}
			//定义是否销售属性渲染器
			function defRenderer(value){
				if(value){
    	 			return "是";
    	 		}else{
    	 			return "否";
    	 		}
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

				//console.log(params);
				return params;
			}
		</script>
	</head>
	<body>
	</body>
</html>