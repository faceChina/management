/**
 * 编辑每日客片窗口
 * @class EditWindowClassifcation
 * @extends Ext.Window
 */
EditWindowClassifcation = Ext.extend(Ext.Window,{
	/**
	 * 重写构造函数，cfg为自定义参数
	 * @param {} cfg
	 */
	constructor : function(cfg){
		Ext.applyIf(this,cfg);
		this.initUIComponents(this);
		EditWindowClassifcation.superclass.constructor.call(this, {
			modal : true,
			layout:'fit',
			iconCls : "window_add",
			buttonAlign : 'center',
			constrain:true,//用于限制窗口不超出浏览器的可视范围
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
		/*
		var myItem = new Ext.form.RadioGroup({
				anchor:'92%',
                fieldLabel:'是否有子节点',
                items:[
                    {boxLabel:'是',inputValue:'0',name:"leaf"},
                    {boxLabel:'否',inputValue:'1',name:"leaf",checked: true}
                ]
			}) ;
		
		if(1 <= selfCmp.level){
			myItem.hide();
		}
		*/
		var myLeaf;
		if(0 == selfCmp.level){
			myLeaf = 0;
		}else{
			myLeaf = 1;
		}
		
		var url = updateWebTopicParentInfoUrl;
		if(selfCmp.isUpdate){
			url = updateClssifcationDetail;
		}
		
		selfCmp.formPanel = new Ext.form.FormPanel({
			id:'formPanel_classifcation_add',
			defaultType : "textfield",
			border:false,
			plain:true,
			labelWidth :90,
			frame:true,
			padding:10,
			layout: 'form',
			defaults: {
	            anchor: '-20'
	        },
			url:url,
			items:[
				{xtype:'hidden',name:'sequenceTemp',id:'sequenceTemp'},
				{xtype:'hidden',name:'id',id:'tid',value:selfCmp.tid},
				{xtype:'hidden',name:'parentId',id:'parentId',value:selfCmp.tpid},
				{xtype:'hidden',name:'serviceId',id:'serviceId',value:"1"},
				{xtype:'hidden',name:'category',id:'category',value:"1"},
				{xtype:'hidden',name:'leaf',id:'leaf',value:myLeaf},
				//第一行
				{
					 xtype:"textfield",
	             	 fieldLabel:"类目名称",
	              	 name:"name", 
	              	 //disabled:isDisabled,
	              	 id: 'tName',
	              	 allowBlank:false,
	                 blankText:"类目名称不能为空!",
	              	 maxLength:50,
	              	 anchor:'92%'
				},	
				//myItem
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
				text : "提交",
				iconCls : "all_save",
				style:'align:right',
				handler : function() {
					if (selfCmp.formPanel.getForm().isValid()) {
						selfCmp.formPanel.getForm().submit({
							waitMsg : "正在提交信息...",
							success : function(form, action) {
								var tree = Ext.getCmp('f1_treepanel_classifcation_view');
								if (tree != undefined) {
									//刷新树
									tree.root.reload();
									//tree.expandAll();
									tree.expandPath(nodePath);
								}
								g_showTip('操作成功！');		
								selfCmp.close();
								disableAllButton();
							},
							failure : function(form, action) {
								g_showTip('操作失败！');
							}
						});
					}
				}
			}]
		});
		this.bbar=btnToolbar;
	},
	loadForm:function(tid){
		this.tid=tid;
		this.formPanel.getForm().load({
			deferredRender : false,
			url : queryClssifcationDetail + "?id=" + tid,
			waitMsg : "信息加载中...",
			success : function(form,action) {
				//disableAllButton();
			},
			failure : function(form,action) {
				g_showTip(action.result.resultDesc);
				Ext.getCmp('window-classifcation-update').hide();
			}
		});
	}
});