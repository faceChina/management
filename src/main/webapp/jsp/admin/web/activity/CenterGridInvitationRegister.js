/**
 * 页面右边属性列表
 * @class CenterGridInvitationRegister
 * @extends Ext.Panel
 */

CenterGridInvitationRegister = Ext.extend(Ext.Panel,{
	//刷脸用户查询地址
	queryInvitationRegisterGridUrl:ROOT_PATH+"/web/activity/invitationRegisterList.htm",
	gridPanel:null,
	constructor:function(cfg){
		Ext.applyIf(this,cfg);
		this.initUIComponents(this);
		CenterGridInvitationRegister.superclass.constructor.call(this, {
			iconCls :"websitehref_mag",
			border:false,
			title:"邀请注册列表",
			layout:'fit',
			items : [this.gridPanel]
		});
	},
	//初始化顶部工具栏
	initToolBar:function(selfCmp){
		var toolbar = new Ext.Toolbar({
			id:'invitationRegisterToolBar',
			items : ['-']
		});
		
		var tToolbar = [
		{// 顶部工具栏 '-'为竖线分隔符
			xtype : 'label',// 声明label标签类型
			text : '昵称:'// label名称
		}, {
			id:'nickname',
			xtype : 'textfield',
			ref : 'nickname',// 必须
			defaultValue : ''
		},'-',{
			id:'loginAccountType',
			xtype : 'combo',
			ref : 'loginAccountType',
			mode: "local",
			valueField: "value",
		    displayField: "key",
		    triggerAction: "all",
		    //emptyText : '全部',
		    value : '注册账号',
		    width:100,
		    store: new Ext.data.Store({
		        proxy: new Ext.data.MemoryProxy([[1, "注册账号"],[2,"邀请账户"],[3,"上级邀请账户"]]),
		        reader: new Ext.data.ArrayReader({},[{
											            name: "value"
											        },{
											            name: "key"
											        }]),
		        autoLoad: true
		    })
		},':', {
			id:'loginAccount',
			xtype : 'textfield',
			ref : 'loginAccount',// 必须
			defaultValue : ''
		},'-',{
			xtype : 'label',
			text : '受邀注册时间:'
		}, {
			xtype: 'datefield', 
	        width: 200, 
	        name: 'createTimeStart', 
	        id:'createTimeStart', 
	        fieldLabel: '开始时间', 
	        labelAlign: 'right', 
	        labelWidth: 185, 
	        format: 'Y-m-d', 
	        selectOnFocus:true, 
	        editable:false, 
	        dateRange:{begin:'starttime',end:'endtime'}, 
	        vtype:'dateRange', 
	        endDateField: 'endtime', 
	        anchor: '100%'
		},{
			xtype : 'label',
			text : '至'
		}, {
			xtype: 'datefield', 
	        width: 200, 
	        name: 'createTimeEnd', 
	        id:'createTimeEnd', 
	        fieldLabel: '结束时间', 
	        labelAlign: 'right', 
	        labelWidth: 185, 
	        format: 'Y-m-d', 
	        selectOnFocus:true, 
	        editable:false, 
	        dateRange:{begin:'starttime',end:'endtime'}, 
	        vtype:'dateRange', 
	        endDateField: 'endtime', 
	        anchor: '100%'
		},'-',{
			text : '查 询',
			iconCls : 'event_search',
			handler : function() {// 所调用的函数
				var grid = Ext.getCmp("grid-invitationRegister-view")
				var toolbarIdArray = ['invitationRegisterToolBar'];
				
				var store = grid.getStore();
				var bbar = grid.getBottomToolbar();
				var params = {};
				if(toolbarIdArray && toolbarIdArray.length > 0) {
					for(var i = 0; i < toolbarIdArray.length; i++) {
						var currentToolbar = Ext.getCmp(toolbarIdArray[i]);
						for(var j = 0; j < currentToolbar.items.length; j++) {
							if(currentToolbar.items.items[j].ref) {
								params[currentToolbar.items.items[j].ref] = currentToolbar.items.items[j].getValue();
							}
						}
					}
				}
				
				
				var startTime = Ext.getCmp("createTimeStart").getValue(); 
				var endTime = Ext.getCmp("createTimeEnd").getValue(); 
				
				if(!isNull(startTime)){
					startTime = startTime.format('Y-m-d');
				}
				
				if(!isNull(endTime)){
					endTime = endTime.format('Y-m-d');
				}
				
				params['createTimeStart'] = startTime; 
				params['createTimeEnd'] = endTime;
				
				params.start = 0;
				params.limit = bbar.pageSize;
				
				store.load({
					params :params
				});
			}
		}, '-',{
			text : '清 空',
			iconCls : 'event_clear',
			handler : function() {
				for(var j = 0; j < toolbar.items.length; j++) {
					if(toolbar.items.items[j].ref) {
						toolbar.items.items[j].setValue(toolbar.items.items[j].defaultValue);
					}
				}
				
				Ext.getCmp("createTimeStart").setValue('');
				Ext.getCmp("createTimeEnd").setValue('');
				//AppUtil.resetSearch(['organchildToolBar']);
				Ext.getCmp("loginAccountType").setValue('1');
			}
		}, '-',{
			text : '批量导出',
			iconCls : 'drwz_xf',
			handler : function() {
				var grid = Ext.getCmp("grid-invitationRegister-view")
				var toolbarIdArray = ['invitationRegisterToolBar'];
				
				var store = grid.getStore();
				var bbar = grid.getBottomToolbar();
				var params = {};
				if(toolbarIdArray && toolbarIdArray.length > 0) {
					for(var i = 0; i < toolbarIdArray.length; i++) {
						var currentToolbar = Ext.getCmp(toolbarIdArray[i]);
						for(var j = 0; j < currentToolbar.items.length; j++) {
							if(currentToolbar.items.items[j].ref) {
								params[currentToolbar.items.items[j].ref] = currentToolbar.items.items[j].getValue();
							}
						}
					}
				}
				
				
				var startTime = Ext.getCmp("createTimeStart").getValue(); 
				var endTime = Ext.getCmp("createTimeEnd").getValue(); 
				
				if(!isNull(startTime)){
					startTime = startTime.format('Y-m-d');
				}
				
				if(!isNull(endTime)){
					endTime = endTime.format('Y-m-d');
				}
				
				params['createTimeStart'] = startTime; 
				params['createTimeEnd'] = endTime;
				
				exportData(params);
			}
		}]// ->组件向右靠齐
		
		toolbar.add(tToolbar);
		
		return toolbar;
	},
	initUIComponents:function(selfCmp){
		// 定义列表的toolbar
		var toolbar = this.initToolBar(selfCmp);
		//列表显示控件定义
		var childGridC;
		//后台到前台列表字段绑定规格
		var childGridT;
		
		//列表显示控件定义
        childGridC = [
                      {id:"createTime", header:"受邀注册时间",dataIndex:"createTime"},
                      {id:"nickName", header:"受邀者昵称",dataIndex:"nickName"},
                      {id:"loginAccount", header:"注册账号",dataIndex:"loginAccount"},
                      {id:"amount", header:"注册送颜值",dataIndex:"amount"},
                      {id:"invitationNickName", header:"邀请者昵称",dataIndex:"invitationNickName"},
                      {id:"invitationLoginAccount", header:"邀请账号",dataIndex:"invitationLoginAccount"},
                      {id:"invitationAmount", header:"邀请者送颜值",dataIndex:"invitationAmount"},
                      {id:"superNickName", header:"邀请者上级",dataIndex:"superNickName"},
                      {id:"superLoginAccount", header:"上级邀请账号",dataIndex:"superLoginAccount"},
                      {id:"superAmount", header:"送颜值",dataIndex:"superAmount"}
                  ];
		//后台到前台列表字段绑定规格
        childGridT=['createTime','nickName','loginAccount','amount',"invitationNickName","invitationLoginAccount","invitationAmount","superNickName","superLoginAccount","superAmount"];
		
        selfCmp.gridPanel=new Com.panel.EvecomBaseGridPanel({
        	queryUrl:this.queryInvitationRegisterGridUrl,//jsp传来
        	selType:1,
        	isAutoQuery:false,
        	singleSelect:false,
        	pkField:"id",
        	region : "center",
        	fieldsArr: childGridT,
        	columnsArr: childGridC,
			viewConfig:{
				forceFit:true
		   	},
        	tbar:toolbar,
        	id:"grid-invitationRegister-view",
        	initPagingBar:function(store){
        		return new EVE.PagingToolbar({store:store,pageNum:20});
        	},
        	queryData : function(submitType, params) {
        		if (submitType == 0) {
        			var formParamValues = Ext.getCmp(params).getForm().getValues();
        			this.store.baseParams = formParamValues;
        		} else {
        			if (params != '') {
        				var v_params = "{";
        				for (var p in params) {
        					try {
        						if(params[p] == null){
        							v_params += "\"" + p + "\":" + params[p] + ",";
        						}else{
        							v_params += "\"" + p + "\":\"" + params[p] + "\",";
        						}
        					} catch (e) {
        						
        					}
        				}
        				v_params = v_params.substring(0, v_params.length - 1) + "}";
        				this.store.baseParams = Ext.util.JSON.decode(v_params);
        				console.log(v_params);
        			}
        		}
        		var postParams = this.store.baseParams;
        		postParams.start = 0;
        		postParams.pageSize = 20;
        		this.store.load({
        			params:postParams
        		});
        	}
        });
	}
});
Ext.reg('centerGridInvitationRegister', CenterGridInvitationRegister);