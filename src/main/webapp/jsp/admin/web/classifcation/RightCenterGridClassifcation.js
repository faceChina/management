/**
 * 页面右边分类关联界面
 * @class RightCenterGridClassifcation
 * @extends Ext.Panel
 */
RightCenterGridClassifcation = Ext.extend(Ext.Panel,{
	gridPanel:null,
	constructor:function(cfg){
		Ext.applyIf(this,cfg);
		this.initUIComponents(this);
		RightCenterGridClassifcation.superclass.constructor.call(this, {
			iconCls :"sysmenu_mag",
			border:false,
			title:"类目与属性关联",
			layout:'fit',
			items : [this.gridPanel]
		});
	},
	//初始化顶部工具栏
	initToolBar:function(selfCmp){
		var toolbar = new Ext.Toolbar({
			id:'typeRelationToolBar',
			items : []
		});

		toolbar.add([{
			id:'tbar_typeRelation_add',
			text : "新增",
			iconCls : "all_add",
			handler : function(){
				if(jspIsAdd&&jspChooseIsStatus!=1&&jspChooseIsStatus!=-1){
					jspInitAddWebTopicDetailWindow();
				}else{
					g_showTip('已发布的商品类目无法操作');	
				}
			}
		}]);
		toolbar.add([{
			id:'tbar_typeRelation_delete',
			text : "删除",
			iconCls : "all_delete",
			handler : function(){
				var msgValue = "确定要删除<font color='red'>" + jspChooseText + "</font>的关联关系吗?";
				Ext.Msg.confirm("温馨提示", msgValue, function(msg){
					if(msg=='yes'){
						deleteClassificationPropRealtion(jspChooseId);
					}
				});
			}
		}]);
		
		toolbar.add(['->',{
			id:'tbar_typeRelation_down',
			text : "下移",
			//disabled : true,
			iconCls : "drwz_xf",
			handler : function(){
//				var gridPanel = Ext.getCmp("grid-classifcationRelation-view");
//				var selects = gridPanel.getSelectionModel().getSelections();
//				var next = gridPanel.getSelectionModel().selectNext();
//				console.log(gridPanel.getSelectionModel().selectRow(0));
//				console.log(gridPanel.getStore().getAt(i+1));//每行records对象);
				//gridPanel.getStore().indexOf(selects[0]);
				var selectedValue = getCheckValues("grid-classifcationRelation-view","id",1);
				if(selectedValue == null){
					return;
				}
				if(selectedValue != ''){
					updateClassificationPropRealtionSort(1,selectedValue);
				}else{
					g_showTip('请选择数据!');
				}
			}
		}]);toolbar.add(['-',{
			id:'tbar_typeRelation_up',
			text : "上移",
			//disabled : true,
			iconCls : "fj_upload",
			handler : function(){
				var selectedValue = getCheckValues("grid-classifcationRelation-view","id",-1);
				if(selectedValue == null){
					return;
				}
				if(selectedValue != ''){
					updateClassificationPropRealtionSort(-1,selectedValue);
				}else{
					g_showTip('请选择数据!');
				}
			}
		}]);
		return toolbar;
	},
	initUIComponents:function(selfCmp){
		// 定义列表的toolbar
		var toolbar = this.initToolBar(selfCmp);
		//自定义显示值
		function myRenderer(value){
			if(value){
	 			return "是";
	 		}else{
	 			return "否";
	 		}
		}
		//列表显示控件定义
        var classifcationRelationGridC = 
        	[
     	 		{id:"id", hidden : true,dataIndex:"id"},
        	 	{id:"classificationId", hidden : true,dataIndex:"classificationId"},
        	 	{id:"propId", hidden : true,dataIndex:"propId"},
        	 	{id:"propName", header:"属性名称",dataIndex:"propName"},
        	 	{id:"isColorProp", header:"是否颜色属性",dataIndex:"isColorProp",renderer:myRenderer},
        	 	{id:"isEnumProp", header:"是否枚举属性",dataIndex:"isEnumProp",renderer:myRenderer},
        	 	{id:"isInputProp", header:"是否输入属性",dataIndex:"isInputProp",renderer:myRenderer},
        	 	{id:"isKeyProp", header:"是否关键属性",dataIndex:"isKeyProp",renderer:myRenderer},
        	 	{id:"isSalesProp", header:"是否销售属性",dataIndex:"isSalesProp",renderer:myRenderer},
        	 	{id:"isAllowAlias", header:"是否允许别名",dataIndex:"isAllowAlias",renderer:myRenderer},
        	 	{id:"isStandard", header:"是否标准属性",dataIndex:"isStandard",renderer:myRenderer},
        	 	{id:"isRequired", header:"是否为必选属性",dataIndex:"isRequired",renderer:myRenderer},
        	 	{id:"isMulti", header:"是否可以多选属性",dataIndex:"isMulti",renderer:myRenderer}
    	 	];
		//后台到前台列表字段绑定规格
        var classifcationRelationGridT=['id','classificationId','propId','propName','isColorProp','isEnumProp','isInputProp','isKeyProp','isSalesProp',
                                        'isAllowAlias','isStandard','isRequired','isMulti'];
        selfCmp.gridPanel=new Com.panel.EvecomBaseGridPanel({
        	queryUrl:queryClassifcationPropRealtionGridUrl+"?classificationId="+jspChooseId,//jsp传来
        	selType:1,
        	isAutoQuery:false,
        	pkField:"id",
        	region : "center",
        	fieldsArr: classifcationRelationGridT,
        	columnsArr: classifcationRelationGridC,
			viewConfig:{
				forceFit:true//自主决定每列的宽度，填充满表格
		   	},
        	tbar:toolbar,
        	id:"grid-classifcationRelation-view"
        });
	}
});
Ext.reg('RightCenterGridClassifcation', RightCenterGridClassifcation);