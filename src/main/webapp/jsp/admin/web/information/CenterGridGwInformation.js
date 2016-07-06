/**
 * 页面右边属性列表
 * @class CenterGridGwInformation
 * @extends Ext.Panel
 */

CenterGridGwInformation = Ext.extend(Ext.Panel,{
	//刷脸精选查询地址
	queryInformationDetailGridUrl:ROOT_PATH+"/web/information/list.htm",
	gridPanel:null,
	constructor:function(cfg){
		Ext.applyIf(this,cfg);
		this.initUIComponents(this);
		CenterGridGwInformation.superclass.constructor.call(this, {
			iconCls :"resources_mag",
			border:false,
			title:"资讯中心",
			layout:'fit',
			items : [this.gridPanel]
		});
	},
	//初始化顶部工具栏
	initToolBar:function(selfCmp){
		var toolbar = new Ext.Toolbar({
			id:'informationToolBar',
			items : []
		});
		
		var myProxy = new Ext.data.MemoryProxy([[null,"全部"],[0, "未发布"],[1,"已发布"]])
		
		var tToolbar = ['->',
		{// 顶部工具栏 '-'为竖线分隔符
			xtype : 'label',// 声明label标签类型
			text : '标题:'// label名称
		}, '-', {
			id:'title',
			xtype : 'textfield',
			ref : 'title',// 必须
			defaultValue : ''
		},'-',{
			xtype : 'label',
			text : '状态:'
		}, '-', {
			id:'status',
			xtype : 'combo',
			ref : 'status',
			mode: "local",
			valueField: "value",
		    displayField: "key",
		    triggerAction: "all",
		    //emptyText : '全部',
		    //value : 'null',
		    store: new Ext.data.Store({
		        proxy: new Ext.data.MemoryProxy([[null,"全部"],[0, "隐藏"],[1,"显示"]]),
		        reader: new Ext.data.ArrayReader({},[{
											            name: "value"
											        },{
											            name: "key"
											        }]),
		        autoLoad: true
		    })
		},'-',{
			text : '查 询',
			iconCls : 'event_search',
			handler : function() {// 所调用的函数
				AppUtil.search(Ext.getCmp("grid-information-view"),['informationToolBar']);
			}
		}, '-', {
			text : '清 空',
			iconCls : 'event_clear',
			handler : function() {
				for(var j = 0; j < toolbar.items.length; j++) {
					if(toolbar.items.items[j].ref) {
						toolbar.items.items[j].setValue(toolbar.items.items[j].defaultValue);
					}
				}
				
				//AppUtil.resetSearch(['organchildToolBar']);
			}
		}]// ->组件向右靠齐
		toolbar.add([{
			id:'tbar_information_add',
			text : "新增",
			iconCls : "all_add",
			handler : function(){
				var url = ROOT_PATH+"/jsp/admin/web/information/article-cont.jsp"
//				window.open(url, 'editfancyMessage', 'height=1000,top=0, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no')
				if(window.parent.isExitsTab('information_tab')){
					window.parent.activateTab('information_tab');
					var tab = window.parent.getTab('information_tab');
					window.parent.frames["Frm-" + tab.id].closeInformationTab('新增资讯',url,'all_add_menu');
				}else{
					window.parent.addTabUrl('information_tab','新增资讯',url,'all_add_menu');
				}
			}
		}]);
		toolbar.add(['-',{
			id:'tbar_information_delete',
			text : "删除",
			iconCls : "all_delete",
			handler : function(){
				var msgValue = "确定要删除所选的吗?";
				Ext.Msg.confirm("温馨提示", msgValue, function(msg){
					if(msg=='yes'){
						deleteInformation();
					}
				});
			}
		}]);
		toolbar.add(tToolbar);
		return toolbar;
	},
	initUIComponents:function(selfCmp){
		// 定义列表的toolbar
		var toolbar = this.initToolBar(selfCmp);
		//自定义显示值
		function rendererType(value){
			if(value == 1){
	 			return "单图文";
	 		}else if(value == 2){
	 			return "多图文";
	 		}else{
	 			return "未知";
	 		}
		}
		function rendererStatus(value){
			if(value == 0){
	 			return "隐藏";
	 		}else if(value == 1){
	 			return "<font color='#10b606'>显示</font>";
	 		}else if(value == -1){
	 			return "删除";
	 		}else{
	 			return "未知";
	 		}
		}
		//列表显示控件定义
		var childGridC;
		//后台到前台列表字段绑定规格
		var childGridT;
		
		//列表显示控件定义
        childGridC = [
                      {header:"ID",dataIndex : "id",hidden : true},
                      {id:"title", header:"资讯标题",dataIndex:"title"},
                      {id:"announcer", header:"发布者",dataIndex:"announcer"},
                      /*{id:"summary", header:"内容摘要",dataIndex:"summary"},
                      {id:"content", header:"资讯内容",dataIndex:"content"},
                      {id:"source", header:"来源",dataIndex:"source"},*/
                      /*{id:"seoTitle", header:"SEO标题",dataIndex:"seoTitle"},
                      {id:"seoKeywords", header:"SEO关键字",dataIndex:"seoKeywords"},
                      {id:"seoDescription", header:"SEO描述",dataIndex:"seoDescription"},*/
                      {id:"createTime", header:"创建时间",dataIndex:"createTime"},
                      {id:"updateTime", header:"更新时间",dataIndex:"updateTime"},
                      {id:"publishTime", header:"发布时间",dataIndex:"publishTime"},
                      {id:"status", header:"状态",dataIndex:"status",renderer:rendererStatus},
                      {header:"排序",renderer:function(value,cellmeta){
                    	  var returnStr = '<a href="javaScript:updateSort(-2)" style="text-decoration:none"><img src="/management/resource/admin/img/information/up-first.jpg"/></a>&nbsp;&nbsp;&nbsp;&nbsp;'
                    	  				+ '<a href="javaScript:updateSort(-1)" style="text-decoration:none"><img src="/management/resource/admin/img/information/up.jpg"/></a>&nbsp;&nbsp;&nbsp;&nbsp;'
                    	  				+ '<a href="javaScript:updateSort(1)" style="text-decoration:none"><img src="/management/resource/admin/img/information/down.jpg"/></a>&nbsp;&nbsp;&nbsp;&nbsp;'
                    	  				+ '<a href="javaScript:updateSort(2)" style="text-decoration:none"><img src="/management/resource/admin/img/information/down-last.jpg"/></a>&nbsp;&nbsp;&nbsp;&nbsp;'
                          return returnStr;
                      }},
                      {header:"操作",renderer:function(value,cellmeta){
                    	  var returnStr = '<a href="javaScript:deleteInformationById()" style="text-decoration:none">删除</a>';
                    		  returnStr = '<a href="javaScript:editInformation()" style="text-decoration:none">编辑</a>&nbsp;&nbsp;|&nbsp;&nbsp;' +	returnStr;
                          return returnStr;
                      }}
                  ];
		//后台到前台列表字段绑定规格
        childGridT=['id','announcer','title',/*'summary','content','source',*//*'seoTitle','seoKeywords','seoDescription',*/'createTime','updateTime','publishTime','status'];
		
        selfCmp.gridPanel=new Com.panel.EvecomBaseGridPanel({
        	queryUrl:this.queryInformationDetailGridUrl,//jsp传来
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
        	id:"grid-information-view",
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
        				//console.log(v_params);
        			}
        		}
        		var postParams = this.store.baseParams;
        		postParams.start =0;
        		postParams.pageSize = 15;
        		//console.log(postParams);
        		this.store.load({
        			params:postParams
        		});
        	} 
        });
	}
});
Ext.reg('centerGridGwInformation', CenterGridGwInformation);