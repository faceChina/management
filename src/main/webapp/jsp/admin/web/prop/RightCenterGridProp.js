/**
 * 页面右边属性列表
 * @class RightCenterGridProp
 * @extends Ext.Panel
 */
RightCenterGridProp = Ext.extend(Ext.Panel,{
	gridPanel:null,
	constructor:function(cfg){
		Ext.applyIf(this,cfg);
		this.initUIComponents(this);
		RightCenterGridProp.superclass.constructor.call(this, {
			iconCls :"xfhelp_mag",
			border:false,
			title:"标准属性值列表",
			layout:'fit',
			items : [this.gridPanel]
		});
	},
	//初始化顶部工具栏
	initToolBar:function(selfCmp){
		var toolbar = new Ext.Toolbar({
			id:'propValuechildToolBar',
			items : []
		});
		var tToolbar = ['-',
		{// 顶部工具栏 '-'为竖线分隔符
			xtype : 'label',// 声明label标签类型
			text : '属性值名称:'// label名称
		}, '-', {
			xtype : 'textfield',
			ref : 'name',// 必须
			defaultValue : ''
		},'-',{
			text : '查 询',
			iconCls : 'event_search',
			handler : function() {// 所调用的函数
				AppUtil.search(Ext.getCmp("grid-propValuechild-view"),['propValuechildToolBar']);
			}
		}, '-', {
			text : '清 空',
			iconCls : 'event_clear',
			handler : function() {
				for(var j = 0; j < toolbar.items.length; j++) {
					if(toolbar.items.items[j].ref) {
						toolbar.items.items[j].setValue(toolbar.items.items[j].defaultValue);
					}
				}
				//AppUtil.resetSearch(['organchildToolBar']);
			}
		}]// ->组件向右靠齐

		toolbar.add(tToolbar);
		
		toolbar.add(['->',{
			id:'tbar_casechild_add',
			text : "新增",
			iconCls : "all_add",
			handler : function(){
				if(!jspChooseIsStatus){
					jspAddWindowPropValue();
				}else{
					g_showTip('已发布的商品属性无法操作');
				}
			}
		}]);
		toolbar.add(['-',{
			id:'tbar_propValue_delete',
			text : "删除",
			iconCls : "all_delete",
			handler : function(){
				var msgValue = "确定要删除<font color='red'>" + jspChooseText + "</font>下的标准属性吗?";
				Ext.Msg.confirm("温馨提示", msgValue, function(msg){
					if(msg=='yes'){
						deletePropValue(jspChoosePropId);
					}
				});
			}
		}]);
		toolbar.add(['-',{
			id:'tbar_propValue_down',
			text : "下移",
			//disabled : true,
			iconCls : "drwz_xf",
			handler : function(){
				var selectedValue = getCheckValues("grid-propValuechild-view","id",1);
				if(selectedValue == null){
					return;
				}
				if(selectedValue != ''){
					updatePropValueSort(1,selectedValue);
				}else{
					g_showTip('请选择数据!');
				}
			}
		}]);
		toolbar.add(['-',{
			id:'tbar_propValue_up',
			text : "上移",
			//disabled : true,
			iconCls : "fj_upload",
			handler : function(){
				var selectedValue = getCheckValues("grid-propValuechild-view","id",-1);
				if(selectedValue == null){
					return;
				}
				if(selectedValue != ''){
					updatePropValueSort(-1,selectedValue);
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
		//selfCmp.isColor = true;
		//列表显示控件定义
		var childGridC;
		//后台到前台列表字段绑定规格
		var childGridT;
		
		if(selfCmp.isColor){
			//列表显示控件定义
	        childGridC = [
	                      {header:"ID",dataIndex : "id",hidden : true},
	                      {id:"propertyId", dataIndex:"propertyId",hidden : true},
	                      {id:"name", header:"属性值名称",dataIndex:"name"},
	                      {id:"hex", header:"Hex色值",dataIndex:"hex"},
	                      {id:"code", header:"属性值编码",dataIndex:"code"},
	                      {id:"createTime", header:"创建时间",dataIndex:"createTime"}
                      ];
			//后台到前台列表字段绑定规格
	        childGridT=['id','propertyId','name','hex','code','createTime'];
		}else{
			//列表显示控件定义
	        childGridC=[
                         {header:"ID",dataIndex : "id",hidden : true},
                         {id:"propertyId", dataIndex:"propertyId",hidden : true},
                         {id:"name", header:"属性值名称",dataIndex:"name"},
                         {id:"code", header:"属性值编码",dataIndex:"code"},
                         {id:"createTime", header:"创建时间",dataIndex:"createTime"}
                     ];
			//后台到前台列表字段绑定规格
	        childGridT=['id','propertyId','name','code','createTime'];
		}
		
        selfCmp.gridPanel=new Com.panel.EvecomBaseGridPanel({
        	queryUrl:queryWebTopicDetailGridUrl,//jsp传来
        	selType:1,
        	isAutoQuery:false,
        	pkField:"id",
        	region : "center",
        	fieldsArr: childGridT,
        	columnsArr: childGridC,
			viewConfig:{
				forceFit:true//自主决定每列的宽度，填充满表格
		   	},
        	tbar:toolbar,
        	id:"grid-propValuechild-view"
        });
	}
});
Ext.reg('rightCenterGridProp', RightCenterGridProp);