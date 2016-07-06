/**
 * 右边用户信息维护的父页面
 * @class RightViewTabPanelUser
 * @extends Ext.TabPanel
 * tabpanel 两个标签页：部门的用户管理  部门岗位的用户管理
 */
RightViewTabPanelUser = Ext.extend(Ext.TabPanel,{
      v_item:null,
      frame:true,
      organId:null,
      initComponent: function(){ 
  	    this.items=[{
		            xtype:'editGridOrganUser',
		            title:'部门用户信息维护',
		            layout:'fit',
		            id:'f2_gridPanel_organUser_edit'
		            }];
	   RightViewTabPanelUser.superclass.initComponent.call(this);      
   }
});