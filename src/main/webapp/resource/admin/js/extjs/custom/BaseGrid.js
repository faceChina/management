/*
 * var
 * testGridT=[{header:"标识",dataIndex:"id"},{header:"名称",dataIndex:"name"},{header:"简称",dataIndex:"jc"}]
 * var testGridC=["id","name","jc"]; var testGrid={ xtype:
 * 'EvecomBaseGridPanel', queryUrl:'', selType:1, pkField:"id", title:"测试",
 * fieldsArr:testGridC, columnsArr:testGridT }
 * 
 * xxx.store.load({params:{start:0, limit:grid_pageSize}});
 * 
 */
Com.panel.EvecomBaseGridPanel = Ext.extend(Ext.grid.GridPanel, {
	isAutoQuery:true,
	constructor : function(config) {
		Ext.apply(this,config);
		var grid = this;
		var v_fieldsArr = config.fieldsArr;
		var v_columnsArr = config.columnsArr;
		var colArr = [];
		if (this.selType == 0) {
			colArr = v_columnsArr;
		} else if (this.selType == 1) {
			// colArr[0]=new Ext.grid.RowNumberer({width: 30});
			var smCol = new Ext.grid.CheckboxSelectionModel({
						singleSelect : this.vsingleSelect
					});

			// if(!this.vsingleSelect)
			colArr[0] = smCol
			for (var z = 0; z < v_columnsArr.length; z++)
				colArr[colArr.length] = v_columnsArr[z];
			if (!this.vsingleSelect)
				this.sm = smCol;
		}
		this.columns = colArr;
		this.store = this.initStore(config);
		var bbar = this.initPagingBar(this.store);
		Com.panel.EvecomBaseGridPanel.superclass.constructor.call(this,{
			frame : true,
			//region : "center",
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			store:this.store,
			bbar:bbar
		
		});
		if(this.isAutoQuery){
			grid.store.load({
			params : {
				start : 0,
				limit:grid.getBottomToolbar().pageSize
			}
		});
		}
	},
	initPagingBar:function(store){
		return new EVE.PagingToolbar({store:store});
	},
	initStore:function(config){
       var store=new Ext.data.JsonStore({
                    root: 'data',
			        totalProperty: 'total',
			        remoteSort: config.remoteSort,
					proxy: new Ext.data.HttpProxy({url: config.queryUrl}),
					fields:config.fieldsArr				
					});
		return store;
		
		
	},
	queryData : function(submitType, params) {
		/*
		 * 0 整个form提交 params传form的id 1为具体参数
		 * Ext.getCmp("girdId").queryData(1,{"user.code":invCd1,invNm1:invNm1});
		 * Ext.getCmp("girdId").queryData(0,"formpanelid");
		 */
		if (submitType == 0) {
			var formParamValues = Ext.getCmp(params).getForm()
					.getValues();
			this.store.baseParams = formParamValues;
		} else {
			if (params != '') {
				var v_params = "{";
				for (var p in params) {
					try {
						v_params += "\"" + p + "\":\"" + params[p]
								+ "\",";
					} catch (e) {
					}
				}
				v_params = v_params.substring(0, v_params.length - 1)
						+ "}";
				this.store.baseParams = Ext.util.JSON.decode(v_params);
			}
		}
		var postParams = this.store.baseParams;
		postParams.start =0;
		postParams.pageSize = 15;
		this.store.load({
			params:postParams
		});
				
	},
	onDestroy : function() {
		if (this.body) {
			var c = this.body;
			c.removeAllListeners();
			c.update("");
		}
		if (this.rendered) {
			Ext.destroy(this.view, this.loadMask);
		} else if (this.store) {
			this.store.destroy();
		}
		Ext.destroy(this.colModel, this.columns, this.tbar, this.bbar,
				this.store);
		delete this.queryUrl;
		delete this.selType;
		delete this.pkField;
		delete this.fieldsArr;
		delete this.columnsArr;
		delete this.remoteSort;
		delete this.vsingleSelect;
		delete this.store;
		delete this.colModel;
		delete this.view;
		delete this.loadMask;

		Ext.grid.GridPanel.superclass.onDestroy.call(this);
	}
});
Ext.reg('EvecomBaseGridPanel', Com.panel.EvecomBaseGridPanel);