/**
 * 编辑每日客片窗口
 * @class EditWindowType
 * @extends Ext.Window
 */
AddGridWindowType = Ext.extend(Ext.Window,{
	/**
	 * 重写构造函数，cfg为自定义参数
	 * @param {} cfg
	 */
	constructor : function(cfg){
		Ext.applyIf(this,cfg);
		this.initUIComponents(this);
		AddGridWindowType.superclass.constructor.call(this, {
			modal : true,
			layout:'fit',
			iconCls : "window_add",
			buttonAlign : 'center',
			constrain:true,//用于限制窗口不超出浏览器的可视范围
			items:[this.gridPanel]
		});
	},
	/**
	 * 定义要用到的面板
	 */
	//初始化顶部工具栏
	initToolBar:function(selfCmp){
		var toolbar = new Ext.Toolbar({
			id:'addClassifcationRelationToolBar',
			items : []
		});
		var tToolbar = ['-',
		{// 顶部工具栏 '-'为竖线分隔符
			xtype : 'label',// 声明label标签类型
			text : '属性名称:'// label名称
		}, '-', {
			xtype : 'textfield',
			ref : 'name',// 必须
			defaultValue : ''
		},'-',
		/*
		{// 顶部工具栏 '-'为竖线分隔符
			xtype : 'label',// 声明label标签类型
			text : '别名:'// label名称
		}, '-', {
			xtype : 'textfield',
			ref : 'alias',// 必须
			defaultValue : ''
		},'-',*/{
			text : '查 询',
			iconCls : 'event_search',
			handler : function() {// 所调用的函数
				AppUtil.search(Ext.getCmp("grid-addClassifcationRelation-view"),['addClassifcationRelationToolBar']);
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
			}
		}, '-', '->']// ->组件向右靠齐
		toolbar.add(tToolbar);
		toolbar.add(['-',{
			id:'tbar_relation_del',
			text : "保存",
			iconCls : "all_add",
			handler : function(){
				addGridData('grid-classifcationRelation-view','id,name',addRealtionUrl,1,{"classificationId":jspChooseId},'grid-addClassifcationRelation-view');
				selfCmp.close();
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
        var addrelationGridC = 
        	[
        	 	{header:"ID",dataIndex : "id",align:'center',width:20,hidden:true},
        	 	{id:"name", header:"属性名称",dataIndex:"name"},
        	 	{id:"alias", header:"别名",dataIndex:"alias"},
        	 	{id:"isColorProp", header:"是否颜色属性",dataIndex:"isColorProp",renderer:myRenderer},
        	 	{id:"isEnumProp", header:"是否枚举属性",dataIndex:"isEnumProp",renderer:myRenderer},
        	 	{id:"isInputProp", header:"是否输入属性",dataIndex:"isInputProp",renderer:myRenderer},
        	 	{id:"isKeyProp", header:"是否关键属性",dataIndex:"isKeyProp",renderer:myRenderer},
        	 	{id:"isSalesProp", header:"是否销售属性",dataIndex:"isSalesProp",renderer:myRenderer},
        	 	{id:"isAllowAlias", header:"是否允许别名",dataIndex:"isAllowAlias",renderer:myRenderer},
        	 	{id:"isStandard", header:"是否标准属性",dataIndex:"isStandard",renderer:myRenderer},
        	 	{id:"isRequired", header:"是否为必选属性",dataIndex:"isRequired",renderer:myRenderer},
        	 	{id:"isMulti", header:"是否可以多选属性",dataIndex:"isMulti",renderer:myRenderer},
        	 	{id:"isSalesProp", header:"是否销售属性",dataIndex:"isSalesProp",renderer:myRenderer}
    	 	];
		//后台到前台列表字段绑定规格
        var addrelationGridT=['id','name','alias','isColorProp','isEnumProp','isInputProp','isKeyProp','isSalesProp',
                              'isAllowAlias','isStandard','isRequired','isMulti'];
        selfCmp.gridPanel=new Com.panel.EvecomBaseGridPanel({
        	queryUrl:queryTopicDetailGridUrl+"?classificationId="+jspChooseId,//jsp传来
        	selType:1,
        	isAutoQuery:true,
        	pkField:"id",
        	region : "center",
        	fieldsArr: addrelationGridT,
        	columnsArr: addrelationGridC,
			viewConfig:{
				forceFit:true//自主决定每列的宽度，填充满表格
		   	},
        	tbar:toolbar,
        	id:"grid-addClassifcationRelation-view"
        });
	}
});
Ext.reg('addGridWindowType', AddGridWindowType);