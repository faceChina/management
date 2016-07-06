/**
 * 右边数据字典信息维护的父页面
 * @class RightViewTabPanel
 * @extends Ext.TabPanel
 * tabpanel 标签页：数据字典相关信息管理
 */
RightViewTabPanelDict = Ext.extend(Ext.TabPanel,{
      v_item:null,
      frame:true,
      dictId:null,
       initComponent: function(){  	
  	    this.items=[{
		            xtype:'editGridDict',
		            title:'编辑参数信息',
		            layout:'fit',
		            id:'f2_grid_dictchild_edit'
		            }];
	   RightViewTabPanelDict.superclass.initComponent.call(this);      
   }
});