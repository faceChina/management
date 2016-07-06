/**
 * 
 * @class IndexViewPanelClassifcation
 * @extends Ext.Panel
 */
IndexViewPanelClassifcation = Ext.extend(Ext.Panel,{
	west:null,
	center:null,
	constructor:function(cfg){
		Ext.applyIf(this,cfg);
		this.initUIComponents(this);
		IndexViewPanelClassifcation.superclass.constructor.call(this, {
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
		selfCmp.west = new LeftViewTreePanelClassifcation({
			id:'f1_treepanel_classifcation_view',
			border:false,
			floating:true,
			title:'商品类目管理',
			layoutConfig:{animate:false},
			iconCls :"organ_mag",	
			width:300,
			region:'west'
		});
		selfCmp.center = new Ext.Panel({id:'f1_panel_classifcation_view',margins : '5 0 0 5',cmargins : '5 0 0 5',region:'center',layout:'fit'});
	}
});