/**
 * 主页显示界面
 * @class CenterShowPanelMain
 * @extends Ext.Panel
 */
CenterShowPanelMain = Ext.extend(Ext.Panel,{
	center:null,
	constructor:function(cfg){
		Ext.applyIf(this,cfg);
		this.initUIComponents(this);
		CenterShowPanelMain.superclass.constructor.call(this, {
				   border:false,
				   layout : 'fit',
				   items : [this.center]
				});
	},
	initUIComponents:function(selfCmp){
		var tabpanel_other=new Ext.Panel({title:"其它"});//无用途
		var tabpanel_home={title:"首页",layout : 'anchor',items:[{
			anchor:'50%',
			applyTo:'my-tabs',
			border:false
		}]};
		selfCmp.center={xtype:"tabpanel",autoTabs: true,activeTab: 0,
		items:[tabpanel_home,tabpanel_other]} 
		
	}
});
