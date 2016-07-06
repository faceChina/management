/**
 * 菜单管理主页面
 * @class ModuleManageView
 * @extends Ext.Panel
 * west 菜单树  center tabpanel 两个标签页：模块信息管理  模块关联按钮信息管理
 */
ModuleManageView = Ext.extend(Ext.Panel,{
	west:null,
	center:null,
	constructor:function(cfg){
		Ext.applyIf(this,cfg);
		this.initUIComponents(this);
		ModuleManageView.superclass.constructor.call(this, {
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
		
		selfCmp.west = new LeftViewTreePanelModule({
		id:'f1_treepanel_module_view',
		border:false,
		floating:true,
		title:'菜单树', 
		width:300,
		region:'west'
		});
		selfCmp.center = new Ext.Panel({id:'f1_panel_module_view',margins : '5 0 0 5',cmargins : '5 0 0 5',region:'center',layout:'fit'});
}
		
	
});