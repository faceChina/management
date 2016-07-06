AddWindowPropValue = Ext.extend(Ext.Window, {
					constructor : function(cfg) {
						Ext.applyIf(this, cfg);
						this.initUIComponents(this);
						AddWindowPropValue.superclass.constructor.call(this, {
							width : 360,
							height : this.isColor?300:180,//300,
							modal : true,
							layout : 'fit',
							iconCls : "window_add",
							buttonAlign : 'center',
							items : [ this.formPanel ]
						});
					},
					formPanel : null,
					initUIComponents : function(selfCmp) {
						var color =new Ext.ColorPalette({
							value: '993300',
							listeners: {
						        select: function(picker, selColor) {
						            var hex = Ext.getCmp("addHex");
						            hex.setValue("#"+selColor);
						        }
						    }
						});
						
						var myItems = [{xtype : 'hidden', name : 'propertyId', id : 'addSmPid', value : selfCmp.propertyId == '' ? 0 : selfCmp.propertyId }];
						myItems.push({xtype : 'hidden', name : 'hex', id : 'addHex', value : !selfCmp.isColor ? "" : "#993300" });
						myItems.push({
										layout:'column',
										border:false,
										items:[
											       {
											    	   columnWidth:.8,
											    	   layout: 'form',
											    	   border:false,
											    	   defaultType:'textfield',
											    	   items:[
											    	          {
											    	        	  fieldLabel:'<font color=red>*</font>属性值编码',
											    	        	  allowBlank:false,
											    	        	  blankText:"属性值编码不能为空!",
											    	        	  name: 'code'
											    	          },
											    	          {
											    	        	  fieldLabel:'<font color=red>*</font>属性值名称',
											    	        	  allowBlank:false,
											    	        	  blankText:"属性值名称不能为空!",
											    	        	  name: 'name'
											    	          },
											    	          {
											    	        	  fieldLabel:'<font color=red>*</font>属性值别名',
											    	        	  allowBlank:false,
											    	        	  blankText:"属性值别名不能为空!",
											    	        	  name: 'alias'
											    	          }
									    	          ]
											       }
										       ]
									});
						if(selfCmp.isColor){
							myItems.push({
											fieldLabel:'<font color=red>*</font>Hex色值',
											id:'hex',
											columnWidth:.5,
							                layout: 'form',
							                border:false,
							                hideMode:'visibility',
							                defaultType: 'textfield',
							                hidden:!selfCmp.isColor,
							                items:[
							                       color
						                       ]
										});
						}
						/*
						console.log(selfCmp.isColor);
						if(!selfCmp.isColor){
							color.hide();
						}else{
							color.show();
						}*/
						/**
						 * 表单
						 */
						selfCmp.formPanel = new Ext.form.FormPanel({
							id : 'formPanel_module_add',
							border : false,
							plain : true,
							width : 340,
							frame : true,
							labelWidth : 80,
							padding : 10,
							layout : 'form',
							defaults : {
								anchor : '-20'
							},
							url : addPropValueUrl,
							items : myItems/*[ 
							        {xtype : 'hidden', name : 'propertyId', id : 'addSmPid', value : selfCmp.propertyId == '' ? 0 : selfCmp.propertyId },
							        {xtype : 'hidden', name : 'hex', id : 'addHex', value : "false" == selfCmp.isColor ? "" : "#993300" },
									//第一行
									{
										layout:'column',
							            border:false,
							            items : [
											{
											columnWidth:.8,
							                layout: 'form',
							                border:false,
							                defaultType: 'textfield',
							                items:[
												{
												    fieldLabel:'<font color=red>*</font>属性值编码',
												    allowBlank:false,
												    blankText:"属性值编码不能为空!",
												    name: 'code'
												},
								                {
												    fieldLabel:'<font color=red>*</font>属性值名称',
								                    allowBlank:false,
								                    blankText:"属性值名称不能为空!",
								                    name: 'name'
								 
												},
												{
												    fieldLabel:'<font color=red>*</font>属性值别名',
								                    allowBlank:false,
								                    blankText:"属性值别名不能为空!",
								                    name: 'alias'
												}
								             ]
										  },
										]
									},
									{
										fieldLabel:'<font color=red>*</font>Hex色值',
										id:'hex',
										columnWidth:.5,
						                layout: 'form',
						                border:false,
						                hideMode:'visibility',
						                defaultType: 'textfield',
						                hidden:!selfCmp.isColor,
						                items:[
						                       color
					                       ]
									}
								]*/
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
														var tree = Ext.getCmp('f1_tabpanel_prop_view');
															if (tree != null) {
																tree.root.reload();
															}
															jspReloadGrid(selfCmp.propertyId);
															selfCmp.close();
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