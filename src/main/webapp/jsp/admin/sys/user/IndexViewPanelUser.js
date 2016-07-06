/**
 * 用户管理主页面
 * @class UserManageView
 * @extends Ext.Panel
 * west 部门树  center tabpanel 两个标签页：部门的用户管理  部门岗位的用户管理
 */
UserManageView = Ext.extend(Ext.Panel,{
	west:null,
	center:null,
	constructor:function(cfg){
		Ext.applyIf(this,cfg);
		this.initUIComponents(this);
		UserManageView.superclass.constructor.call(this, {
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
		selfCmp.west = new LeftViewTreePanelOrgan({
		id:'f1_treepanel_organ_view',
		border:false,
		floating:true,
		title:'部门树', 
		width:300,
		region:'west'
		});
		selfCmp.center = new Ext.Panel({id:'f1_panel_user_view',margins : '5 0 0 5',cmargins : '5 0 0 5',region:'center',layout:'fit'});
}
		
	
});