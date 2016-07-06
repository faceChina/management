/**
 * 新增模块窗口
 * @class AddWindowModule
 * @extends Ext.Window
 * formPanel 新增模块信息表单
 */
AddWindowModule = Ext.extend(Ext.Window,{
	/**
	 * 重写构造函数，cfg为自定义参数
	 * @param {} cfg
	 */
	constructor : function(cfg){
		Ext.applyIf(this,cfg);
		this.initUIComponents(this);
		AddWindowModule.superclass.constructor.call(this, {
			width : 500,
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
			id:'formPanel_module_add',
			//defaultType : "textfield",
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
			url:addSysModuleUrl,
			items:[
			{xtype:'hidden',name:'pid',id:'addSmPid',value:selfCmp.pid==''?0:selfCmp.pid},
			{xtype:'hidden',name:'premierId',id:'addSmPremierId',value:''},
				//第一行
				{
			layout:'column',
            border:false,
            items : [
				{
				columnWidth:.5,
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
				columnWidth:.5,
                layout: 'form',
                border:false,
                defaultType: 'textfield',
                items:[
                {
				    fieldLabel: '菜单编号',
                    allowBlank:false,
                    blankText:"菜单编号不能为空!",
                    name: 'code',
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
				columnWidth:.5,
                layout: 'form',
                border:false,
                 defaultType:'numberfield',
                items:[
                {
				    fieldLabel: '菜单顺序',
                    allowBlank:false,
                    blankText : "菜单顺序不能为空!",
                    name: 'sort',
				}]
				},
				{
				columnWidth:.5,
                layout: 'form',
                border:false,
                defaultType: 'textfield',
                items:[
                {
				    fieldLabel: '菜单图片',
                    name: 'icon',
                    allowBlank:false,
                    blankText:"菜单图片不能为空!",
                    maxLength:36
				}]
				}
				]
				},
				//第三行
				{
				 xtype:"textfield",
             	 fieldLabel:"菜单URL",
              	 name:"url", 
              	 anchor:'92%',
                 maxLength:200

				}
				]
		});
		/**
		 * 保存按钮栏
		 */
		
		var btnToolbar = new Ext.Toolbar({
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
									waitMsg : "正在提交菜单信息",
									success : function(form, action) {
									var tree = Ext.getCmp('f1_treepanel_module_view');
										if (tree != null) {
											tree.root.reload();
										}
										selfCmp.close();
										jspInitRightFormPanel(action.result.resultDesc);
									},
									failure : function(form, action) {
									g_showTip('菜单信息保存失败');
									}
								});
					}
				}
			}]
		});
		this.bbar=btnToolbar;
	}
});