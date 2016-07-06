/**
 * 编辑品类属性窗口
 * @class EditWindowClassifcation
 * @extends Ext.Window
 */
EditWindowProp = Ext.extend(Ext.Window,{
	/**
	 * 重写构造函数，cfg为自定义参数
	 * @param {} cfg
	 */
	constructor : function(cfg){
		Ext.applyIf(this,cfg);
		this.initUIComponents(this);
		EditWindowProp.superclass.constructor.call(this, {
			modal : true,
			layout:'fit',
			iconCls : "window_add",
			buttonAlign : 'center',
			AutoWidth:true,
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
		selfCmp.formPanel = new Ext.form.FormPanel({
			id:'formPanel_prop_add',
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
			url:updateWebTopicParentInfoUrl_prop,
			items:[
				{xtype:'hidden',name:'sequenceTemp',id:'sequenceTemp'},
				{xtype:'hidden',name:'id',id:'tid',value:selfCmp.tid},
				{xtype:'hidden',name:'parentId',id:'parentId',value:selfCmp.tpid},
				{xtype:'hidden',name:'serviceId',id:'serviceId',value:"1"},
				{xtype:'hidden',name:'category',id:'category',value:"1"},
				{xtype:'hidden',name:'classificationId',id:'classificationId',value:jspChooseId},
				//第一行
				{
					 xtype:"textfield",
	             	 fieldLabel:"属性名称",
	              	 name:"name", 
	              	 id: 'tName',
	              	 allowBlank:false,
	                 blankText:"属性名称不能为空!",
	              	 maxLength:50,
	              	 anchor:'92%'
				},
				{
					 xtype:"textfield",
	             	 fieldLabel:"别名",
	              	 name:"alias", 
	              	 id: 'tAlias',
	              	 allowBlank:false,
	                 blankText:"别名不能为空!",
	              	 maxLength:50,
	              	 anchor:'92%'
				},
				{
					 xtype:"numberfield",
	             	 fieldLabel:"排序",
	              	 name:"sort", 
	              	 id: 'tSort',
	              	 allowBlank:false,
	              	 maxLength:50,
	              	 anchor:'92%'
				},
				{
					xtype:'checkboxgroup',
					anchor:'92%',
	                items:[
                       	{boxLabel: '是否颜色属性', name: 'isColorProp'},
                       	{boxLabel: '是否枚举属性', name: 'isEnumProp'},
                       	{boxLabel: '是否输入属性', name: 'isInputProp'}
	                ]
				},
				{
					xtype:'checkboxgroup',
					anchor:'92%',
	                items:[
                       	{boxLabel: '是否关键属性', name: 'isKeyProp'},
                       	{boxLabel: '是否销售属性', name: 'isSalesProp'},
                       	{boxLabel: '是否允许别名', name: 'isAllowProp'}
	                ]
				},
				{
					xtype:'checkboxgroup',
					anchor:'92%',
	                items:[
                       	{boxLabel: '发布商品时是否为必选属性', name: 'isRequired'},
	                ]
				},
				{
					xtype:'checkboxgroup',
					anchor:'92%',
	                items:[
                       	{boxLabel: '发布商品时是否可以多选属性', name: 'isMulti'}
	                ]
				},
				{
					xtype:'checkboxgroup',
					anchor:'92%',
	                items:[
                       	{boxLabel: '是否标准属性', name: 'isStandard'},
	                ]
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
				text : "提交",
				iconCls : "all_save",
				style:'align:right',
				handler : function() {
					if (selfCmp.formPanel.getForm().isValid()) {
						selfCmp.formPanel.getForm().submit({
							waitMsg : "正在提交信息...",
							success : function(form, action) {
								if(!form.result.success){
									g_showTip(form.result.resultDesc);	
								}else{
									g_showTip('操作成功！');	
								}
								selfCmp.close();
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
				url : findTypeByIdURL+"?id="+tid,
				waitMsg : "信息加载中...",
				success : function(form,action) {
					sequenceTemp = Ext.getCmp('tSort').getValue();
					Ext.getCmp('sequenceTemp').setValue(sequenceTemp);
				},
				failure : function(form,action) {
					alert(action.result.resultDesc);				
				}
			});
	}
});