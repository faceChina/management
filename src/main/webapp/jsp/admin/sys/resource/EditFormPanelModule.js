/**
 * 系统管理-模块管理-模块信息编辑
 * @class EditFormPanelModule
 * @extends Ext.Panel
 * formPanel 为模块明细信息
 */

EditFormPanelModule = Ext.extend(Ext.Panel,{
	formPanel:null,
	moduleId:null,
	panelTbar:null,
	constructor:function(cfg){
		Ext.applyIf(this,cfg);
		this.initUIComponents(this); 
		EditFormPanelModule.superclass.constructor.call(this, {
			        iconCls :"module_menu",
			        tbar:this.panelTbar,
			        bodyBorder:false, 
					items : [this.formPanel]
				});
	},
	/**
	 * 页面初始化布局
	 * @param {} selfCmp
	 */
	initUIComponents:function(selfCmp){
		   var tbar = new Ext.Toolbar({});
               tbar.add(['-',{
				text : "保存",
				iconCls : "all_save",
				handler : function(){
				if (selfCmp.formPanel.getForm().isValid()) {
			        selfCmp.formPanel.getForm().submit({
						waitMsg : "正在提交菜单信息",
						success : function(form, action) {
						var tree = Ext.getCmp('f1_treepanel_module_view');
							if (tree != undefined) {//刷新菜单树
								tree.root.reload();
							}
							g_showTip('菜单信息保存成功');
						},
						failure : function(form, action) {
						g_showTip('菜单信息保存失败');
						}
					});
		}
				}
			}])
			    tbar.add(['-',{
				text : "删除",
				iconCls : "all_delete",
				handler : function(){
				var smId = selfCmp.findById('id').getValue();
				var url = delSysModuleUrl+"?moduleId="+smId;
                delObjectById(url,'f1_tabpanel_module_view','f1_treepanel_module_view','确定要删除此菜单?','删除',300);
				}
			}])
			   tbar.add(['-',{
    	        id:'bn-add-childmodule',
				text : "新增下级菜单",
				iconCls : "all_add",
				handler : function(){
					var smId = selfCmp.findById('id').getValue();
					jspInitAddModuleWindow(smId);
						
				}
			}])
			selfCmp.panelTbar=tbar;
			selfCmp.formPanel = new Ext.form.FormPanel({
			 id:'formPanel_module_edit',
			 labelWidth:80,
			 layout: 'form',
			 bodyStyle:"padding:5px 20px 0",
			frame:true,
			url:modifySysModuleUrl,
 		items:[
			{xtype:'hidden',name:'pid',id:'pid'},
			{xtype:'hidden',name:'id',id:'id'},
			{xtype:'hidden',name:'premierId',id:'premierId'},
				//第一行
				{
			layout:'column',
            border:false,
            items : [
				{
				columnWidth:.3, 
                layout: 'form',
                border:false,
                 defaultType: 'textfield',
                items:[
                {
				     fieldLabel:'菜单名称',
                    allowBlank:false,
                    blankText:"菜单名称不能为空!",
                    name: 'name',
                    maxLength:60
				}]
				},
				{
				columnWidth:.7, 
                layout: 'form',
                border:false,
                defaultType: 'textfield',
                items:[
                {
				       fieldLabel: '菜单编号',
                    allowBlank:false,
                    blankText:"菜单编号不能为空!",
                    name: 'code',
                    id: 'code',
                    maxLength:20
				}]
				}
				]
				},
				//第二行
				{
			layout:'column',
            border:false,
            items : [
				{
				columnWidth:.3, 
			
                layout: 'form',
                border:false,
                 defaultType: 'numberfield',
                items:[
                {
				    fieldLabel: '菜单顺序',
                    allowBlank:false,
                    blankText : "菜单顺序不能为空!",
                    name: 'sort',
                    id: 'sort'
				}]
				},
				{
				columnWidth:.7, 
			
                layout: 'form',
                border:false,
                defaultType: 'textfield',
                items:[
                {
				    fieldLabel: '菜单图片',
                    name: 'icon',
                    id: 'icon',
                    maxLength:36
				}]
				}
				]
				},
				//第三行
				{
				 width:220,
				 xtype:"textfield",
             	 fieldLabel:"菜单URL",
              	 name:"url", 
              	 id: 'url',
              	 anchor:'51%',
              	 maxLength:200

				}
				]
		});

		
	

	
	},
	loadForm:function(moduleId){
		this.moduleId=moduleId;
		this.formPanel.getForm().load({
				deferredRender : false,
				url : querySysModuleObject+"?moduleId="+moduleId,
				waitMsg : "菜单信息加载中...",
				success : function(form,action) {
				},
				failure : function(form,action) {
					alert(action.result.resultDesc);				}
			});
	}
});
Ext.reg('editFormPanelModule', EditFormPanelModule);