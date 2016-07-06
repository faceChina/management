/**
 * 新增数据字典大类
 * @class AddWindowDict
 * @extends Ext.Window
 * formPanel 新增数据字典大类表单
 */
AddWindowDict = Ext.extend(Ext.Window,{
	/**
	 * 重写构造函数，cfg为自定义参数
	 * @param {} cfg
	 */
	constructor : function(cfg){
		Ext.applyIf(this,cfg);
		this.initUIComponents(this);
		AddWindowDict.superclass.constructor.call(this, {
			width : 300,
			height : 180,
			modal : true,
			layout:'fit',
			iconCls : "window_add",
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
			id:'formPanel_dict_add',
			border:false,
			plain:true,
			width:340,
			frame:true,
			labelWidth :70,
			padding:10,
			layout: 'form',
			defaults: {
	            anchor: '-20'
	        },
			url:addSysDictUrl,
			items:[
			{xtype:'hidden',name:'pid',id:'addSmPid',value:selfCmp.pid==''?0:selfCmp.pid},
				{//第1行
				columnWidth:.5,
                layout: 'form',
                border:false,
                 defaultType: 'textfield',
                items:[
                {
				    fieldLabel:'参数名称',
                    allowBlank:false,
                    blankText:"参数名称不能为空!",
                    name: 'name',
                    maxLength:20
				}]
				},
				{//第2行
				columnWidth:.5,
                layout: 'form',
                border:false,
                defaultType: 'textfield',
                items:[
                {
				    fieldLabel: '参数编码',
                    allowBlank:false,
                    blankText:"参数编码不能为空!",
                    name: 'code',
                    maxLength:50
				}]
				},
				{//第3行
				columnWidth:.5,
                layout: 'form',
                border:false,
                defaultType: 'textfield',
                items:[
                {
				    fieldLabel: '参数备注',
                    name: 'memo',
                    allowBlank:true,
                    blankText:"参数备注不能为空!",
                    maxLength:100
				}]
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
					
									waitMsg : "正在提交参数类别",
									success : function(form, action) {
									var tree = Ext.getCmp('f1_treepanel_dict_view');
										if (tree != null) {
											tree.root.reload();
										}
										selfCmp.close();
										jspInitRightFormPanel(action.result.resultDesc);
									},
									failure : function(form, action) {
									g_showTip('参数类别保存失败');
									}
								});
					}
				}
			}]
		});
		this.bbar=dictToolbar;
	}
});