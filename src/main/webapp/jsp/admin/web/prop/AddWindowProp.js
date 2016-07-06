AddWindowProp = Ext.extend(Ext.Window, {
	constructor : function(cfg) {
		Ext.applyIf(this, cfg);
		this.initUIComponents(this);
		AddWindowProp.superclass.constructor.call(this, {
			width : 320,
			height : 190,
			modal : true,
			layout : 'fit',
			iconCls : "window_add",
			buttonAlign : 'center',
			items : [ this.formPanel ]
		});
	},
	/**
	 * 定义要用到的面板
	 */
	formPanel : null,
	initUIComponents : function(selfCmp) {
		/**
		 * 表单
		 */
		var url = addPropUrl;
		var isDisabled = false;
		var isIdDIsabled = false;
		var isUpdateDisable = false;
		if(selfCmp.isUpdate){
			url = updatePropDetailUrl;
			isUpdateDisable = true;
		}else if(selfCmp.isDetail){
			isDisabled = true;
		}else if(selfCmp.isAdd){
			isIdDIsabled = true;
		}
		
		selfCmp.formPanel = new Ext.form.FormPanel({
			id : 'formPanel_module_add',
			border : false,
			plain : true,
			autoWidth : true,
			frame : true,
			labelWidth : 70,
			padding : 10,
			layout : 'form',
			defaults : {
				anchor : '-20'
			},
			url : url,
			items : [ 
			        {xtype : 'hidden', name : 'id', id : 'id', disabled :isIdDIsabled,value : selfCmp.id },
			        {xtype : 'hidden', name : 'pid', id : 'addSmPid', value : selfCmp.pid == '' ? 0 : selfCmp.pid },
			        {xtype : 'hidden', name : 'isColorProp', id : 'isColorProp', value : 1 },
			        {xtype : 'hidden', name : 'isEnumProp', id : 'isEnumProp', value : 0 },
			        {xtype : 'hidden', name : 'isInputProp', id : 'isInputProp', value : 0 },
			        {xtype : 'hidden', name : 'isKeyProp', id : 'isKeyProp', value : 0 },
			        {xtype : 'hidden', name : 'isSalesProp', id : 'isSalesProp', value : 1 },
			        {xtype : 'hidden', name : 'isAllowAlias', id : 'isAllowAlias', value : 1 },
			        {xtype : 'hidden', name : 'isStandard', id : 'isStandard', value : 1 },
			        {xtype : 'hidden', name : 'isRequired', id : 'isRequired', value : 1 },
			        {xtype : 'hidden', name : 'isMulti', id : 'isMulti', value : 1 },
					//第一行
					{
						layout:'column',
			            border:false,
			            items : [
							{
							columnWidth:.9,
			                layout: 'form',
			                border:false,
			                defaultType: 'textfield',
			                items:[
				                {
								    fieldLabel:'<font color="red">*</font>属性名称',
				                    allowBlank:false,
				                    blankText:"属性名称不能为空!",
					    			disabled:isDisabled,
				                    name: 'name',
				                    maxLength:60
				 
								}
				             ]
						  }
						]
					},{
						layout:'column',
			            border:false,
			            items : [
							{
							columnWidth:.9,
			                layout: 'form',
			                border:false,
			                defaultType: 'textfield',
			                items:[
				                {
								    fieldLabel:'<font color="red">*</font>属性别名',
				                    allowBlank:false,
				                    blankText:"属性别名不能为空!",
				                    name: 'alias',
					    			disabled:isDisabled,
				                    maxLength:60
				 
								}
				             ]
						  },
						]
					},
				    {
						layout : 'column',
						border : false,
						padding : 10,
						items : [ {
							xtype : "panel",
							layout : "column", // 也可以是table,实现多列布局
							fieldLabel : '类目偏好',
							isFormField : true, // 非常重要,否则panel默认不显示fieldLabel
							items : [ {
								columnWidth : .8, // 宽度为50%
								disabled:isDisabled,
								xtype : "radio",
								boxLabel : "是否颜色属性", 
								checked : isUpdateDisable ? false : true,
								name : "isProp",
								id:'colorProp'
							},{
								columnWidth : .8, // 宽度为50%
								disabled:isDisabled,
								xtype : "radio",
								boxLabel : "是否枚举属性", 
								checked : isUpdateDisable ? true : false,
								name : "isProp",
								id:'enumProp'
							}
							          /*{
								columnWidth : .5, // 宽度为50%
								disabled:isDisabled,
								xtype : "checkbox",
								boxLabel : "是否颜色属性", // 显示在复选框右边的文字
								name : "isColorProp"
							}, {
								columnWidth : .5,
								//disabled:isDisabled,
								disabled:true,
								checked:true,
								xtype : "checkbox",
								boxLabel : "是否枚举属性",
								name : "isEnumProp"
							} , {
								columnWidth : .5,
								//disabled:isDisabled,
								disabled:true,
								xtype : "checkbox",
								boxLabel : "是否输入属性",
								name : "isInputProp"
							}, {
								columnWidth : .5,
								//disabled:isDisabled,
								disabled:true,
								checked:true,
								xtype : "checkbox",
								boxLabel : "是否关键属性",
								name : "isKeyProp"
							}, {
								columnWidth : .5,
								//disabled:isDisabled,
								disabled:true,
								checked:true,
								xtype : "checkbox",
								boxLabel : "是否销售属性",
								name : "isSalesProp"
							}, {
								columnWidth : .5,
								//disabled:isDisabled,
								disabled:true,
								checked:true,
								defaults:'on',
								xtype : "checkbox",
								boxLabel : "是否允许别名",
								name : "isAllowAlias"
							}, {
								columnWidth : .5,
								disabled:true,
								checked:true,
								xtype : "checkbox",
								boxLabel : "是否标准属性",
								name : "isStandard"
							}, {
								columnWidth : .5,
								//disabled:isDisabled,
								disabled:true,
								checked:true,
								xtype : "checkbox",
								boxLabel : "是否为必选属性",
								name : "isRequired"
							}, {
								columnWidth : .5,
								//disabled:isDisabled,
								disabled:true,
								checked:true,
								xtype : "checkbox",
								boxLabel : "是否可以多选属性",
								name : "isMulti"
							}*/]
						} ]
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
				disabled:isDisabled,
				handler : function() {
					if (selfCmp.formPanel.getForm().isValid()) {
						if(Ext.getCmp('colorProp').checked){
							Ext.getCmp('isColorProp').setValue(1);
							Ext.getCmp('isEnumProp').setValue(0);
						}else{
							Ext.getCmp('isColorProp').setValue(0);
							Ext.getCmp('isEnumProp').setValue(1);
						}
						
						selfCmp.formPanel.getForm().submit({
							waitMsg : "正在提交信息",
							success : function(form, action) {
								var tree = Ext.getCmp('f1_treepanel_prop_view');
								console.log(tree)
								if (tree != null) {
									tree.root.reload();
								}
								jspInitRightFormPanel(selfCmp.pid,selfCmp.text);
								selfCmp.close();
								disableAllButton();
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
	},
	loadForm:function(tid){
		this.tid=tid;
		this.formPanel.getForm().load({
			deferredRender : false,
			url : queryPropDetail + "?id=" + tid,
			waitMsg : "信息加载中...",
			success : function(form,action) {
				if(action.result.data.isColorProp){
					Ext.getCmp('colorProp').setValue(true);
					Ext.getCmp('isColorProp').setValue(1);
					Ext.getCmp('isEnumProp').setValue(0);
				}else if(action.result.data.isEnumProp){
					Ext.getCmp('enumProp').setValue(true);
					Ext.getCmp('isColorProp').setValue(0);
					Ext.getCmp('isEnumProp').setValue(1);
				}
				//disableAllButton();
			},
			failure : function(form,action) {
				g_showTip(action.result.resultDesc);
				if(Ext.getCmp('window-prop-update')){
					Ext.getCmp('window-prop-update').hide();
				}
				if(Ext.getCmp('window-prop-detail')){
					Ext.getCmp('window-prop-detail').hide();
				}
			}
		});
	}
});