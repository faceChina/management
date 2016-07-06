/**
 * 页面右边属性列表
 * @class CenterGridGwInformation
 * @extends Ext.Panel
 */

CenterGridActivityManage = Ext.extend(Ext.Panel,{
	//刷脸用户查询地址
	queryActivityManageGridUrl:ROOT_PATH+"/web/activity/list.htm",
	gridPanel:null,
	constructor:function(cfg){
		Ext.applyIf(this,cfg);
		this.initUIComponents(this);
		CenterGridActivityManage.superclass.constructor.call(this, {
			iconCls :"websitehref_mag",
			border:false,
			title:"刷脸活动列表",
			layout:'fit',
			items : [this.gridPanel]
		});
	},
	//初始化顶部工具栏
	initToolBar:function(selfCmp){
		var toolbar = new Ext.Toolbar({
			id:'activityManageToolBar',
			items : []
		});
		
		var tToolbar = [
		{
			text : '创建活动',
			iconCls : 'all_add',
			handler : function() {
				addActivity();
			}
		}];
		
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
		
		function rendererStatus(value){
			if(value == 0){
	 			return "未开始";
	 		}else if(value == 1){
	 			return "进行中";
	 		}else if(value == 2){
	 			return "已结束";
	 		}else{
	 			return "未知";
	 		}
		}
		
		function changeRowClass(record, rowIndex, rowParams, store) {
		    if (record.get("nowStatus") == "2") {
		        return 'x-grid-record-gray';
		    }
		}
		
		//列表显示控件定义
        childGridC = [
                      {id:"id", header:"id",dataIndex:"id",hidden : true},
                      {id:"name", header:"活动名称",dataIndex:"name"},
                      {id:"activityTime", header:"活动时间",dataIndex:"activityTime"},
                      {id:"nowStatus", header:"状态",dataIndex:"nowStatus",renderer:rendererStatus},
                      {header:"操作",renderer:function(value,cellmeta){
                    	  var returnStr = '';
                    	  if(cellmeta.value == '未开始'){
                    		  returnStr = '<a href="javaScript:startActivityById()" style="text-decoration:none">立即开始</a>' 
                    		  			+ '&nbsp;&nbsp;&nbsp;&nbsp;<a href="javaScript:updateActivityById()" style="text-decoration:none">修改</a>'
                    		  			+ '&nbsp;&nbsp;&nbsp;&nbsp;<a href="javaScript:closeActivityById()" style="text-decoration:none">结束</a>&nbsp;&nbsp;&nbsp;&nbsp;';
                    	  }else if(cellmeta.value == '进行中'){
                        	  returnStr = '<a href="javaScript:closeActivityById()" style="text-decoration:none">结束</a>&nbsp;&nbsp;&nbsp;&nbsp;';
                    	  }else{
                    		  returnStr = '';
                    	  }
                    	  
                    	  returnStr += '<a href="javaScript:detailActivityById()" style="text-decoration:none">查看</a>';
                          return returnStr;
                      }}
                  ];
		//后台到前台列表字段绑定规格
        childGridT=['id','name','activityTime','nowStatus'];
		
        selfCmp.gridPanel=new Com.panel.EvecomBaseGridPanel({
        	queryUrl:this.queryActivityManageGridUrl,//jsp传来
        	selType:1,
        	isAutoQuery:false,
        	singleSelect:false,
        	pkField:"id",
        	region : "center",
        	fieldsArr: childGridT,
        	columnsArr: childGridC,
			viewConfig:{
				forceFit:true,//自主决定每列的宽度，填充满表格
				getRowClass: changeRowClass
		   	},
        	tbar:toolbar,
        	id:"grid-activityManage-view",
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
Ext.reg('centerGridActivityManage', CenterGridActivityManage);