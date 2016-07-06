/**
 * 编辑参数类别窗口
 * @class EditWindowModuleButton
 * @extends Ext.Window
 * formPanel 编辑参数类别表单
 */
EditWindowDict = Ext.extend(Ext.Window,{
	dictId:null,
	/**
	 * 重写构造函数，cfg为自定义参数
	 * @param {} cfg
	 */
	constructor : function(cfg){
		Ext.applyIf(this,cfg);
		this.initUIComponents(this);
		EditWindowDict.superclass.constructor.call(this, {
			modal : true,
			layout:'fit',
			iconCls : "com_gird_cm_edit",
			buttonAlign : 'center',
			items:[this.formPanel]
		});
	},
	/**
	 * 定义要用到的面板
	 */
	formPanel:null,
	initUIComponents:function(selfCmp){
		/**
		 * 表单
		 */
		selfCmp.formPanel = new Ext.form.FormPanel({
			id:'formPanel_dict_edit',
			border:false,
			plain:true,
			labelWidth :70,
			padding:10,
			frame:true,
			layout: 'form',
			defaults: {
	            anchor: '-20'
	        },
			url:modifyDictUrl,
			items:[
			{xtype:'hidden',name:'id',id:'aid'},
			{xtype:'hidden',name:'pid'},
			{xtype:'hidden',name:'type'},
				{//第1行
				 xtype:"textfield",
             	 fieldLabel:"参数名称",
              	 name:"name", 
              	 allowBlank:true,
                 blankText:"参数名称不能为空!",
              	 maxLength:20,
              	 anchor:'92%'
				},{//第2行
				 xtype:"textfield",
             	 fieldLabel:"参数编码",
              	 name:"code", 
              	 allowBlank:false,
                 blankText:"参数编码不能为空!",
              	 maxLength:50,
              	 anchor:'92%'
				},{//第3行
				 xtype:"textfield",
             	 fieldLabel:"参数备注",
              	 name:"memo", 
              	 id: 'sbdDesc',
              	 allowBlank:true,
                 blankText:"参数备注不能为空!",
              	 maxLength:100,
              	 anchor:'92%'
				}
				]
		});
		/**
		 * 保存按钮栏
		 */
		
		var dictToolbar = new Ext.Toolbar({
			height : 30,
			items : [ '->','-',{
				text : "取消",
				iconCls : "all_cancel",
				handler : function() {
					selfCmp.close();
				}
			},'->','-',{
				text : "保存",
				iconCls : "all_save",
				style:'align:right',
				handler : function() {
					if (selfCmp.formPanel.getForm().isValid()) {
						selfCmp.formPanel.getForm().submit({
									waitMsg : "正在提交参数信息",
									success : function(form, action) {
										selfCmp.close();
										var tree = Ext.getCmp('f1_treepanel_dict_view');
										if (tree != null) {
											tree.root.reload();
										}
										jspInitRightFormPanel(action.result.resultDesc);
										//jspReloadDictChildGrid();
									},
									failure : function(form, action) {
									g_showTip('参数信息保存失败');
									}
								});
					}
				}
			}]
		});
		this.bbar=dictToolbar;
	},
	loadForm:function(dictId){
		this.formPanel.load({
				deferredRender : false,
				url : querySysDictObjectUrl+"?dictId="+dictId,
				waitMsg : "参数信息加载中...",
				success : function(form,action) {
					Ext.getCmp('aid').setValue(action.result.data.id);
				},
				failure : function(form,action) {
					alert(action.result.resultDesc);
				}
			});
	}
});