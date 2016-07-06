/**
 * 类目标准属性列表
 * @class ModuleManageView
 * @extends Ext.Panel
 * west 类目标准属性列表  center tabpanel 两个标签页：模块信息管理  模块关联按钮信息管理
 */
PropManageView = Ext.extend(Ext.Panel,{
	west:null,
	center:null,
	constructor:function(cfg){
		Ext.applyIf(this,cfg);
		this.initUIComponents(this);
		PropManageView.superclass.constructor.call(this, {
					layout : 'border',
					border:false,
					items : [this.west,this.center]
				});
	},
	/**
	 * 页面初始化布局
	 * @param {} selfCmp
	 */
	initUIComponents:function(selfCmp){
		
		selfCmp.west = new LeftViewTreePanelProp({
		id:'f1_treepanel_prop_view',
		border:false,
		floating:true,
		title:'商品属性管理', 
		width:300,
		region:'west',
	   	iconCls :"sysmenu_mag"
		});
		selfCmp.center = new Ext.Panel({id:'f1_panel_prop_view',margins : '5 0 0 5',cmargins : '5 0 0 5',region:'center',layout:'fit'});
}
		
	
});