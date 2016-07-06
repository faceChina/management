/**
 * 页面左边部门树界面
 * @class LeftViewTreePanelOrgan
 * @extends EvecomTreePanel
 */
LeftViewTreePanelOrgan = Ext.extend(Com.panel.EvecomTreePanel,{
	constructor:function(cfg){
		Ext.applyIf(this,cfg);
		this.initUIComponents(this);
		LeftViewTreePanelOrgan.superclass.constructor.call(this, {
	   	rootText: '机构部门',
	   	rootId:'0',
	   	iconCls :"organ_mag",
	    rootIcon:menuTreeIcon,//此变量样式在indexModuleManage.jsp中定义
	    serviceUrl:querySysOrganTreeNodeUrl,
	       listeners: {
			            click: function(node) {
			            	if(node.attributes.id!='0'){
			            jspInitRightFormPanel(node.attributes.id);//刷新右页面用户列表信息 
			            	}
					  }
			        }
				});
	},
	initUIComponents:function(selfCmp){
	}
});