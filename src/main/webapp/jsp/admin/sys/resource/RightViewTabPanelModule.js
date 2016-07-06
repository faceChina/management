/**
 * 右边模块信息维护的父页面
 * @class RightViewTabPanel
 * @extends Ext.TabPanel
 * tabpanel 两个标签页：模块信息管理  模块关联按钮信息管理
 */
RightViewTabPanel = Ext.extend(Ext.TabPanel,{
      v_item:null,
      frame:true,
      moduleId:null,
       initComponent: function(){  	
       

       	//var moduleId = Ext.getCmp('f1_tabpanel_module_view').moduleId;//初始化按钮列表的模块ID
       	//this.deferredRender=false;
  	    this.items=[{
		            xtype:'editFormPanelModule',
		            title:'模块信息维护',
		            layout:'fit',
		            id:'f2_formPanel_module_edit'
		            }];
	   RightViewTabPanel.superclass.initComponent.call(this);      
   }
});