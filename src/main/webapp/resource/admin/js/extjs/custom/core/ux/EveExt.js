
/**
 * 通用分页工具栏
 * @class EVE.PagingToolbar
 * @extends Ext.PagingToolbar
 */
/**
 * @fileoverview用于扩展一些常用的ExtJs，以简化大量重复性的代码
 */
Ext.ns('EVE');
Ext.ns('EVE.ux.plugins');

/**
 * 全局弹出窗口
 * @class EVE.Window
 * @extends Ext.Window
 */
EVE.Window = Ext.extend(Ext.Window,{
	formPanel : null,
    constructor:function(config){
		Ext.apply(this,config);
		var buttons = [];
		if(config.buttons!=null){
			buttons = config.buttons;
		}else{
			buttons.push({
				text : "提交",
				iconCls : "btn-save",
				scope:this,
				handler : function() {
					if(config.save!=null){
						config.save.call(this);
					}else{
						alert("未重写save方法!");
					}
				}
			});
			buttons.push({
				text : "重置",
				iconCls : "btn-reset",
				scope:this,
				handler : function() {
					if(config.reset!=null){
						config.reset.call(this);
					}else{
						var formPanel= this.getComponent(0);
				        formPanel.getForm().reset();
					}
				}
			});
			buttons.push({
			    text : "取消",
				iconCls : "btn-cancel",
				scope:this,
				handler : function() {
					if(config.close!=null){
						config.close.call(this);
					}else{
						this.close();
					}
				}
			});
		}
		var width = config.width;
		var height = config.height;
		EVE.Window.superclass.constructor.call(this,{
			    layout :config.layout?config.layout:"fit",
			    border :true,
				modal :true,
				plain :true,
				animCollapse : true,
				pageY : (document.body.clientHeight-height)/2,
				pageX : (document.body.clientWidth-width)/2,
				animateTarget : Ext.getBody(),
				constrain : true,
			    bodyStyle : "padding:5px;",
			    buttonAlign : "center",
		        buttons : buttons
		});
	}
});

/**
 * 系统统一列模型
 * @class EVE.ColumnModel
 * @extends Ext.grid.ColumnModel
 */
EVE.ColumnModel = Ext.extend(Ext.grid.ColumnModel,{
	checkBoxModel:null,
	constructor:function(config){
		Ext.apply(this,config);
		var column = new Array();
		// 定义列表的复选框
	    this.checkBoxModel = new Ext.grid.CheckboxSelectionModel({handleMouseDown : Ext.emptyFn,singleSelect:config.singleSelect?config.singleSelect:false});
		if(!config.singleSelect){
			column.push(this.checkBoxModel);
		}
		column.push(new Ext.grid.RowNumberer());
		var cols = config.cols;
		for(var i=0;i<cols.length;i++){
			column.push(cols[i]);
		}
		EVE.ColumnModel.superclass.constructor.call(this,{
			columns:column,
			defaults : {
				sortable : false,
				menuDisabled : false,
				width : 100
			}
		});
	
	}
});

/**
 * 分页工具栏上的选择没有条数的控件
 * @class EVE.PagesizeCombo
 * @extends Ext.form.ComboBox
 */
EVE.PagesizeCombo = Ext.extend(Ext.form.ComboBox,{
	constructor:function(config){
			Ext.apply(this,config);
			EVE.PagesizeCombo.superclass.constructor.call(this,{
				    name : 'pageSize',
					hiddenName : 'pageSize',
					typeAhead : true,
					triggerAction : 'all',
					lazyRender : true,
					mode : 'local',
					store : new Ext.data.ArrayStore({
								fields : ['value', 'text'],
								data : [[10, '10条/页'], [15, '15条/页'],[20, '20条/页'], [50, '50条/页'], [100, '100条/页'], [250, '250条/页'], [500, '500条/页']]
							}),
					valueField : 'value',
					displayField : 'text',
					value : '15',
					editable : false,
					width : 85
			});
	}
});
/**
 * 通用分页工具栏
 * @class EVE.PagingToolbar
 * @extends Ext.PagingToolbar
 */
EVE.PagingToolbar = Ext.extend(Ext.PagingToolbar,{
 	  constructor:function(config){
				Ext.apply(this,config);
				var bbar = this;
				var store = config.store;
				var pagesize_combo = new EVE.PagesizeCombo();
			    var number = parseInt(pagesize_combo.getValue());
			    pagesize_combo.on("select", function(comboBox) {
					bbar.pageSize = parseInt(comboBox.getValue());
					number = parseInt(comboBox.getValue());
					store.reload({
								params : {
									start : 0,
									limit : bbar.pageSize
								}
							});
				});
				EVE.PagingToolbar.superclass.constructor.call(this,{
					    pageSize : number,
						store : config.store,
						displayInfo : true,
						displayMsg : '显示{0}条到{1}条,共{2}条',
						plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
						// emptyMsg
						// :
						// "没有符合条件的记录",
						items : ['-', '&nbsp;&nbsp;', pagesize_combo]
				});
	 }
});
/**
 * 
 * @class EVE.GridPanel
 * @extends Ext.grid.GridPanel
 */
EVE.GridPanel=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		Ext.apply(this,config);
		var grid = this;
		var store  = this.initStore(config);
		var bbar = this.initPagingBar(store);
		EVE.GridPanel.superclass.constructor.call(this,{
					frame : true,
					region : "center",
					trackMouseOver : true,
					disableSelection : false,
					loadMask : true,
					store:store,
					bbar:bbar,
					sm:config.cm.checkBoxModel,
					viewConfig : {
						forceFit : true,
						enableRowBody : false,
						showPreview : false
					}
					
		});
		grid.store.load({
			params : {
				start : 0,
				limit : grid.getBottomToolbar().pageSize
			}
		});
	},
	initPagingBar:function(store){
		return new EVE.PagingToolbar({store:store});
	},
	initStore:function(config){
		var store = null;
		var store = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : config.url
					}),
			reader : new Ext.data.JsonReader({
						root : "data",
						totalProperty : "total",
						fields :config.fields
			}),
			remoteSort : true
		});
		// 翻页排序时带上查询条件
		store.on('beforeload', function() {
			if(config.tbar){
				var toolbarId = config.tbar.getId();
				this.baseParams = AppUtil.loadBaseParams([toolbarId]);
			}
	       
		});
		return store;
	}
});
/**
 * 统一搜索面板
 * @class EVE.SearchPanel
 * @extends Ext.form.FormPanel
 */
EVE.EveFormPanel = Ext.extend(Ext.form.FormPanel, {
	constructor : function(f) {
		var e = f.colNums ? f.colNums : 1;
		Ext.apply(this, f);
		if (e > 1 && f.items) {
			this.items = [];
			var j = null;
			var g = 0;
			for (var d = 0; d < f.items.length; d++) {
				var h = f.items[d];
				if (h.xtype != "hidden") {
					if (g % e == 0) {
						j = {
							xtype : "compositefield",
							fieldLabel : h.fieldLabel,
							items : [],
							defaults : {
								style : "margin:0 0 0 0"
							}
						};
						this.items.push(j);
					} else {
						var a = ":";
						if (this.superclass.labelSeparator) {
							a = this.superclass.labelSeparator;
						}
						var c = 100;
						if (this.labelWidth) {
							c = this.labelWidth;
						}
						if (h.labelWidth) {
							c = h.labelWidth;
						}
						var b = "text-align:left";
						if ("right" == this.labelAlign) {
							b = "text-align:right";
						}
						if (h.fieldLabel) {
							j.items.push({
										xtype : "label",
										width : c,
										style : b,
										text : h.fieldLabel + a
									});
						}
					}
					j.items.push(h);
					g++;
				} else {
					this.items.push(h);
				}
			}
		}
		EVE.SearchPanel.superclass.constructor.call(this, {
			autoHeight : true,
			border : false,
			frame:true,
			//style : "padding:6px;background-color: white",
			buttonAlign : "center"
		});
	}
});
/*
 * 为Form表单设置加载数据,使用方式如下：
 * this.formPanel.loadData({
 *				url:__ctxPath + '/system/getAppRole.do?roleId=' + this.roleId,
 *				preName:'AppRole',
 *				root:'data'
 *			});
 */
Ext.override(Ext.Panel, {
	loadData:function(conf){
		if(!conf.root){
			conf.root='data';
		}
		var ct=this;
		//遍历该表单下所有的子项控件，并且为它赋值	
		var setByName=function(container,data){
			var items=container.items;
			if(items!=null&&items!=undefined&&items.getCount){
				for(var i=0;i<items.getCount();i++){
					var comp=items.get(i);
					if(comp.items){
						setByName(comp,data);
						continue;
					}
					//判断组件的类型，并且根据组件的名称进行json数据的自动匹配
					var xtype=comp.getXType();
					try{
						if(xtype=='textfield' || xtype=='textarea' || xtype=='radio' || xtype=='checkbox' 
							|| xtype=='datefield' || xtype=='combo' || xtype=='hidden' || xtype=='datetimefield'
							||xtype=='htmleditor'||xtype=='displayfield'||xtype=='diccombo'
							|| xtype=='fckeditor'||xtype=='numberfield' || xtype=='singleSelectTreeCombo'||'multiSelectTreeCombo'
							){
							var name=comp.getName();
							if(name){
								if(conf.preName){
									if(name.indexOf(conf.preName)!=-1){
										name=name.substring(conf.preName.length+1);
									}
								}
								var val=eval(conf.root+'.'+name);
								if(val!=null && val!=undefined){
									comp.setValue(val);
								}
							}
						}
					}catch(e){
						//alert(e);
					}
				}
			}
		};
		if (!ct.loadMask) {
			ct.loadMask = new Ext.LoadMask(Ext.getBody());
			ct.loadMask.show();
		}
		var scope=conf.scope?conf.scope:ct;
		var params=conf.params?conf.params:{};
		Ext.Ajax.request({
			method:'POST',
			url:conf.url,
			scope:scope,
			params:params,
			success:function(response,options){
				var json=Ext.util.JSON.decode(response.responseText);
				var data=null;
				if(conf.root){
					data=eval('json.'+conf.root);
				}else{
					data=json;
				}
				setByName(ct,data);
				if(ct.loadMask){
					ct.loadMask.hide();
					ct.loadMask = null;
				}
				if(conf.success){
				    conf.success.call(scope,response,options);
				}
			},//end of success
			failure:function(response,options){
				if(ct.loadMask){
					ct.loadMask.hide();
					ct.loadMask = null;
				}
				if(conf.failure){
				    conf.failure.call(scope,response,options);
				}
			}
		});
	}
});