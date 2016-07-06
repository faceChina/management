/**
 * 数据字典管理主页面
 * @class ModuleManageView
 * @extends Ext.Panel
 * west 数据字典树  center tabpanel 标签页：数据字典相关信息管理
 */
DictManageView = Ext.extend(Ext.Panel,{
	west:null,
	center:null,
	constructor:function(cfg){
		Ext.applyIf(this,cfg);
		this.initUIComponents(this);
		DictManageView.superclass.constructor.call(this, {
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
		selfCmp.west = new LeftViewTreePanelDict({
		id:'f1_treepanel_dict_view',
		border:false,
		floating:true,
		title:'参数类别树', 
		iconCls :"all_add",
		width:300,
		region:'west'
		});
		selfCmp.center = new Ext.Panel({id:'f1_panel_dict_view',margins : '5 0 0 5',cmargins : '5 0 0 5',region:'center',layout:'fit'});
}
		
	
});