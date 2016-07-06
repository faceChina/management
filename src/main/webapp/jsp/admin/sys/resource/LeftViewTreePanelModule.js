/**
 * 页面左边菜单树界面
 * @class ModuleLeftTreePanelView
 * @extends EvecomTreePanel
 */
LeftViewTreePanelModule = Ext.extend(Com.panel.EvecomTreePanel,{
	constructor:function(cfg){
		Ext.applyIf(this,cfg);
		this.initUIComponents(this);
		LeftViewTreePanelModule.superclass.constructor.call(this, {
	   	rootText: '资源菜单',
	   	rootId:'0',
	   	iconCls :"resources_mag",
	    rootIcon:'menuTreeicon',//此变量样式在indexModuleManage.jsp中定义
	    serviceUrl:queryModuleTreeNodeUrl,
	       listeners: {
			            click: function(node) {
			            	if(node.attributes.id!='0'){
			            jspInitRightFormPanel(node.attributes.id);//刷新右页面模块信息 此方法在indexModuleManage.jsp里定义
			            	}
					  }
			        }
				});
	},
	initUIComponents:function(selfCmp){
			  var tbar = new Ext.Toolbar({});
               tbar.add(['-',{
               	id:"tbar_module_addFirstModule",
				text : "新增一级菜单",
				iconCls : "all_add",
				handler : function(){
					jspInitAddModuleWindow('0');//增加一级菜单 传入父级ID 默认为0
				}
			}]);
		selfCmp.tbar=tbar
	}
});