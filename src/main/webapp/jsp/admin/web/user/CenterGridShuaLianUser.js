/**
 * 页面右边属性列表
 * @class CenterGridGwInformation
 * @extends Ext.Panel
 */

CenterGridShuaLianUser = Ext.extend(Ext.Panel,{
	//刷脸用户查询地址
	queryShualianDetailGridUrl:ROOT_PATH+"/web/user/sl/userlist.htm",
	gridPanel:null,
	constructor:function(cfg){
		Ext.applyIf(this,cfg);
		this.initUIComponents(this);
		CenterGridShuaLianUser.superclass.constructor.call(this, {
			iconCls :"sl_user",
			border:false,
			title:"刷脸用户列表",
			layout:'fit',
			items : [this.gridPanel]
		});
	},
	//初始化顶部工具栏
	initToolBar:function(selfCmp){
		var toolbar = new Ext.ButtonGroup({
			id:'shualianToolBar',
	        columns: 12,
	        border:false, 
	        style: {
	            marginBottom: '10px',//距底部高度
	            marginLeft:'10px',//距左边宽度
	            marginRight:'10px'//距右边宽度
	        },
			items : []
		});
		
		var tToolbar = [
		{// 顶部工具栏 '-'为竖线分隔符
			xtype : 'label',// 声明label标签类型
			text : '账号:'// label名称
		}, {
			id:'user.loginAccount',
			xtype : 'textfield',
			ref : 'user.loginAccount',// 必须
			defaultValue : ''
		},{
			xtype : 'label',
			text : '昵称:'
		}, {
			id:'user.nickname',
			xtype : 'textfield',
			ref : 'user.nickname',// 必须
			defaultValue : ''
		},{
			xtype : 'label',
			text : '姓名:'
		}, {
			id:'user.contacts',
			xtype : 'textfield',
			ref : 'user.contacts',// 必须
			defaultValue : ''
		},{
			xtype : 'label',
			text : '注册时间:'
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
		},{
			text : '查 询',
			iconCls : 'event_search',
			handler : function() {// 所调用的函数
				var grid = Ext.getCmp("grid-shualian-view")
				var toolbarIdArray = ['shualianToolBar'];
				
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
		}, {
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
			}
		}]// ->组件向右靠齐
		
		var userMap = [
	        {
				xtype : 'label',
				text : '今日新增用户:'
			}, 
			{
				id:'todayNewUserNum',
				xtype : 'label',
				text : '0'
			},
			{
				xtype : 'label',
				text : '总注册用户数:'
			}, 
			{
				id:'UserSumNum',
				xtype : 'label',
				text : '0'
			}
		];
		
		
		toolbar.add(tToolbar);
		toolbar.add(userMap);
		
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
                      {id:"userId", header:"用户ID",dataIndex:"userId",hidden : true},
                      {id:"loginAccount", header:"账号",dataIndex:"user.loginAccount"},
                      {id:"nickname", header:"昵称",dataIndex:"user.nickname"},
                      {id:"contacts", header:"姓名",dataIndex:"user.contacts"},
                      {id:"position", header:"职位",dataIndex:"businessCard.position"},
                      {id:"createTime", header:"注册时间",dataIndex:"user.createTime"},
                      {id:"lastLoginTime", header:"最后登录",dataIndex:"lastLoginTime",hidden : true},
                      {id:"loginNum", header:"登录次数",dataIndex:"loginNum",hidden : true},
                      {id:"shopNum", header:"现有店铺",dataIndex:"shopNum"},
                      {id:"invitationNum", header:"邀请用户",dataIndex:"invitationNum"},
                      {header:"操作",renderer:function(value,cellmeta){
                    	  var returnStr = '<a href="javaScript:queryShuaLianUserById()" style="text-decoration:none">查看</a>&nbsp;&nbsp;|&nbsp;&nbsp;';
                    		  returnStr += '<a href="javaScript:editShuaLianUserById()" style="text-decoration:none">编辑</a>';
                          return returnStr;
                      }}
                  ];
		//后台到前台列表字段绑定规格
        childGridT=['userId','user.loginAccount','user.nickname','user.contacts','businessCard.position','user.createTime',
                    														'lastLoginTime','loginNum','shopNum','invitationNum'];
		
        selfCmp.gridPanel=new Com.panel.EvecomBaseGridPanel({
        	queryUrl:this.queryShualianDetailGridUrl,//jsp传来
        	selType:1,
        	isAutoQuery:false,
        	singleSelect:false,
        	pkField:"id",
        	region : "center",
        	fieldsArr: childGridT,
        	columnsArr: childGridC,
			viewConfig:{
				forceFit:true//自主决定每列的宽度，填充满表格
		   	},
        	tbar:toolbar,
        	id:"grid-shualian-view",
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
Ext.reg('centerGridShuaLianUser', CenterGridShuaLianUser);