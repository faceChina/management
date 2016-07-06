
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
		var cols = config.cols;
		if(config.isExpander){
			column.push(cols[0]);
		}
		// 定义列表的复选框
	    this.checkBoxModel = new Ext.grid.CheckboxSelectionModel({handleMouseDown : Ext.emptyFn,singleSelect:config.singleSelect?config.singleSelect:false});
		column.push(this.checkBoxModel);
		/*if(!config.singleSelect){
			column.push(this.checkBoxModel);
		}*/
		column.push(new Ext.grid.RowNumberer());
		if(config.isExpander){
			for(var i=1;i<cols.length;i++){
				column.push(cols[i]);
		   }
		}else{
			for(var i=0;i<cols.length;i++){
			    column.push(cols[i]);
		   }
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
			var pageNum = 15;
			if(config!=null&&config!=''&&config!='undefined'){
				pageNum = config.pageNum;
				if(pageNum==null||pageNum==''||pageNum=='undefined'){
					pageNum = 15;
				}
			}
			EVE.PagesizeCombo.superclass.constructor.call(this,{
				    name : 'pageSize',
					hiddenName : 'pageSize',
					typeAhead : true,
					triggerAction : 'all',
					lazyRender : true,
					mode : 'local',
					store : new Ext.data.ArrayStore({
								fields : ['value', 'text'],
								data : [ [pageNum, pageNum+'条/页']]
							}),
					valueField : 'value',
					displayField : 'text',
					value : pageNum,
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
				var pagesize_combo = new EVE.PagesizeCombo(config);
			    var number = parseInt(pagesize_combo.getValue());
			    pagesize_combo.on("select", function(comboBox) {
					bbar.pageSize = parseInt(comboBox.getValue());
					number = parseInt(comboBox.getValue());
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
	 },
	//private
	onLoad : function(store, r, o){
	    if(!this.rendered){
	        this.dsLoaded = [store, r, o];
	        return;
	    }
	    var p = this.getParams();
	    this.cursor = (o.params && o.params[p.start]) ? o.params[p.start] : 0;
	    var d = this.getPageData(), ap = d.activePage, ps = d.pages;
	
	    this.afterTextItem.setText(String.format(this.afterPageText, d.pages));
	    this.inputItem.setValue(ap);
	    this.first.setDisabled(ap == 1);
	    this.prev.setDisabled(ap == 1);
	    this.next.setDisabled(ap == ps);
	    this.last.setDisabled(ap == ps);
	    this.refresh.enable();
	    this.updateInfo();
	    this.fireEvent('change', this, d);
	},
	doLoad : function(start){
        var o = {}, pn = this.getParams();
        o[pn.start] = start;
        o[pn.limit] = this.pageSize;

        var params = this.store.lastOptions.params;
        params[pn.start] = start;
        params[pn.limit] = this.pageSize;
        
//        if(this.fireEvent('beforechange', this, o) !== false){
//            this.store.load({params:o});
//        }
        if(this.fireEvent('beforechange', this, params) !== false){
            this.store.load({params:params});
        }
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
		EVE.EveFormPanel.superclass.constructor.call(this, {
			autoHeight : true,
			url : f.url,
			border : false,
			frame:true,
			//style : "padding:6px;background-color: white",
			buttonAlign : "center"
		});
	}
});
/**
 * 通用的下载Panel
 * @author huyu
 * @class Ext.DownloadGridPanel
 * @extends EVE.GridPanel
 * @Example 客户端的引用方式是: new Ext.DownloadGridPanel({arId:arId});
 * 你把表记录ID传递给我
 */
EVE.DownloadGridPanel =Ext.extend(EVE.GridPanel,{
	initToolBar:function(){
		var toolbar = new Ext.Toolbar({
		items : []
	    });
		toolbar.add(['-',{
			    xtype:'hidden',
			    id:'attachFileIds'
			}, {
			    xtype:'hidden',
			    id:'attachFileNames'
			},{
			text : "上传附件",
			iconCls : "all_add",
			scope:this,
			handler : function() {
				var tableName = this.tableName;
				var tableId = this.tableId;
				showUploadPanel({
					winTitle : "上传附件窗口",
					fileCount : 10,//文件个数限制 
					fileSize : '100 MB',//文件大小限制
					fileTypes :'*',//文件的类型 如：*.txt;*.xls;*.rar
					path:tableName,//文件路径[文章管理]
					isCallBack : true, //是否回显 < true||false >
					backFunction:function(){
						var store =Ext.getCmp("downloadFileGridPanel").getStore();
						store.proxy.conn.url = ROOT_PATH + "/common/listUploadAttachment.htm?Q_uaTableid_S_EQ="+tableId+"&Q_uaTable_S_EQ="+tableName+"&attachFileIds="+Ext.getCmp("attachFileIds").getValue();
						store.load();
					},
					uploadUrl:ROOT_PATH+'/common/muti/upload.htm?tableId='+tableId+"&tableName="+tableName,
					//putObject : Ext.getCmp("attachFileNames"),//回填时显示在当前组件中
					ids : Ext.getCmp("attachFileIds"),//回填时ID存在这个组件中 < Ext.getCmp("NewsImageIds")||null>
					//filePath:Ext.getCmp("excelPath"),//回显路径
					uploadType : 'other', //上传的类型< image||other >
					tableId:tableId,
					tableName:tableName
				});
			}
		}]);
		toolbar.add(['-', {
			text : "删除附件",
			iconCls : "all_delete",
			scope:this,
			handler : function() {
				var tableName = this.tableName;
				var tableId = this.tableId;
			    AppUtil.gridDel(Ext.getCmp("downloadFileGridPanel"),'id',ROOT_PATH + "/common/delAll.htm",function(){
		    	    var store =Ext.getCmp("downloadFileGridPanel").getStore();
					store.proxy.conn.url = ROOT_PATH + "/common/listUploadAttachment.htm?Q_uaTableid_S_EQ="+tableId+"&Q_uaTable_S_EQ="+tableName+"&attachFileIds="+Ext.getCmp("attachFileIds").getValue();
					store.load();
			    });
			}
		}]);
		return toolbar;
	},
	initColumnModel:function(){
		var columnModel = new EVE.ColumnModel({
			//singleSelect:true,//你是否要求列表是单选的，如果没有传,则是可以多选
			cols:[{
				header : "文件名称",
				dataIndex : "name",
				sortable:true,
				width : 100
			}, {
				header : "文件类型",
				dataIndex : "fileType",
				width : 50
			}, {
				header : "文件大小",
				dataIndex : "fileSize",
				width : 80
			},{
				header : "管理",
				dataIndex : "id",
				width : 60,
				renderer : function(id, i, grid) {
					var uaId = ""+grid.data['id']+"";
					var innerHTML = "";
						innerHTML += '&nbsp;<img title="预览" src="' + ROOT_PATH+'/resource/admin/css/exticon/icon/findfile_xfdata.png" style="cursor: pointer;"  onclick="EVE.DownloadGridPanel.prototype.preview(\''
					+ uaId+'\')"/>&nbsp;';
					return innerHTML;
				}
	     }]
		});
		return columnModel;
	},
	constructor:function(config){
		Ext.apply(this,config);
		this.tableId = (config.tableId)?config.tableId:null;
		this.tableName = (config.tableName)?config.tableName:null;
		var height=(config.height)?config.height:170;
		// 定义gridPanel的模型
		var columnModel = this.initColumnModel();
		// 定义列表的toolbar
	    var toolbar = this.initToolBar();
		EVE.DownloadGridPanel.superclass.constructor.call(this,{
			id:"downloadFileGridPanel",
			url : ROOT_PATH + "/common/listUploadAttachment.htm?Q_uaTableid_S_EQ="+this.tableId+"&Q_uaTable_S_EQ="+this.tableName,
			cm : columnModel,
			height:height,
			tbar : toolbar,
			fields : ["id","name","path","createTime","fileType","fileSize"]
		});
	},
	del:function(uaId){
		var params={
			url:ROOT_PATH + "/common/delAll.htm",
			ids:uaId,
			grid:Ext.getCmp("downloadFileGridPanel")
		};
		AppUtil.postDel(params);
	},
	download:function(uaId){
		if(uaId){
			window.location.href=ROOT_PATH + "/common/downloadUploadAttachment.htm?attachId="+uaId;
		}
	},
	preview:function(uaId){
		if(uaId){
		var picUrl=ROOT_PATH+'/common/previewImg.htm';
			Ext.Ajax.request({
					url :picUrl,
					waitMsg:'发送请求中...',
					params : {uaId :uaId},
					method : 'POST',
					success : function(response,options) {
						var respText = Ext.util.JSON.decode(response.responseText).data;
						if(respText){
							picUrl =respText.path;
							var win = new Ext.Window({
									title: '图像预览-->('+respText.name+')',
					           		closable:true,
					            	border:true,
					            	plain:true,
					            	iconCls:'findfile_xfdata',
					            	layout: 'fit',
					            	constrain:true,//将拖动范围限制在容器内
					            	autoDestroy:false,
									plain:true,
					            	autoWidth:true,
					            	autoHeight:true,
					            	html:'<img id="custom"style="height:300px;" src="/company/'+picUrl+'"/>'
								});
								win.show();
						}else{
							Ext.Msg.alert('操作信息','服务器未检索到图片，请联系管理员！');
						}
					},
					failure : function(response,options) {
						Ext.Msg.alert('操作信息','操作出错，请联系管理员！');
					}
				});
		}
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

/**
 * 扩展EXT没有的验证
 */
Ext.apply(Ext.form.VTypes,{
	daterange: function(val, field) {
	    var date = field.parseDate(val);
	    if (!date) {
	        return false;
	    }
	    if (field.startDateField && (!this.dateRangeMax || (date.getTime() != this.dateRangeMax.getTime()))) {
	        var start = field.up('form').down('#' + field.startDateField);
	        start.setMaxValue(date);
	        start.validate();
	        this.dateRangeMax = date;
	    }
	    else if (field.endDateField && (!this.dateRangeMin || (date.getTime() != this.dateRangeMin.getTime()))) {
	        var end = field.up('form').down('#' + field.endDateField);
	        end.setMinValue(date);
	        end.validate();
	        this.dateRangeMin = date;
	    }
	    /*
	     * Always return true since we're only using this vtype to set the
	     * min/max allowed values (these are tested for after the vtype test)
	     */
	    return true;
	},
	daterangeText: '开始时间必须小于结束日期！',
	password: function(val, field) {
	    if (field.initialPassField) {
	        var pwd = Ext.getCmp(field.initialPassField);
	        return (val == pwd.getValue());
	    }
	    return true;
	},
	passwordText: '两次密码输入不一致!',
    phone:function(val,field){
  	    var reg = /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/;
  	    return (reg.test(val));
    },
	phoneText:'请输入正确的电话号码,如:0593-3885551',
	mobile:function(val,field){
	  	    var reg = /^(\+86)?(1[0-9]{10})$/;
	  	    return (reg.test(val));
	},
	mobileText:'请输入正确的手机号格式,如:15859014458',
	phoneOrMobile : function(val,field) {
	  	var phoneReg = /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/;
	  	var mobileReg = /^(\+86)?(1[0-9]{10})$/;
	  	return (phoneReg.test(val) || mobileReg.test(val));
	},
	phoneOrMobileText:'请输入正确的电话号码或手机号,如:0593-3885551(电话号码),13452031733(手机号)',
	price:function(val,field){
	  	    //var reg = new RegExp("^((d{3,4})|d{3,4}-)?d{7,8}$");
	  	    var reg = /^\d{1,11}(\.\d{1,3})?$/;
	  	    return (reg.test(val));
	 },
	priceText:'请输入正确的价格格式,如:00.00',
	letterOrNum:function(val,field){
	  	    var reg = /^[A-Za-z0-9]+$/;
	  	    return (reg.test(val));
	},
    letterOrNumText:'该项只能输入字母或者数字',
    letter:function(val,field){
	  	    var reg = /^[A-Za-z]+$/;
	  	    return (reg.test(val));
	},
    letterText:'该项只能输入字母',
    number:function(val,field){
	  	    var reg = /^[0-9]*$/;
	  	    return (reg.test(val));
	},
    numberText:'该项只能输入数字',
    positiveInteger:function(val,field){
	  	    var reg = /^\+?[1-9][0-9]*$/;
	  	    return (reg.test(val));
	},
    positiveIntegerText:'该项只能输入正整数',
    website:function(val,field){
	  	    var reg = /http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;
	  	    return (reg.test(val));
	},
    websiteText:'请输入正确的网址'
});