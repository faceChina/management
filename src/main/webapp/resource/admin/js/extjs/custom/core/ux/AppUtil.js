/**
 * 用途:该js主要存放系统的公共变量和公共方法
 */
Ext.ns("AppUtil");
/**
 * 统一提交搜索面板查询方法
 * @param {} formPanel
 */
AppUtil.searchForm=function(formPanel){
	var searchPanel = formPanel.searchPanel;
	var gridPanel = formPanel.gridPanel;
	if (searchPanel.getForm().isValid()) {
		var store = gridPanel.getStore();
		var bbar = gridPanel.getBottomToolbar();
		var searializeForm = Ext.Ajax.serializeForm(searchPanel.getForm().getEl());
		var decodeUrl = Ext.urlDecode(searializeForm);
		decodeUrl.start =0;
		decodeUrl.limit = store.baseParams.limit;
		//store.baseParams = decodeUrl;
		store.load({
			params:decodeUrl
		});
		//gridPanel.getBottomToolbar().moveFirst();
		/*var params = {};
		params.start = 0;
		params.limit = bbar.pageSize;
		store.baseParams = decodeUrl;
		store.load({
			params :params
		});*/
	}
}

/**
 * 表格上工具栏上删除操作
 * @param {grid,idName,url} conf
 * grid:被操作的网格组件
 * idName:ID的名字
 * url:提交的url
 */
AppUtil.gridDel = function(grid,idName,url){
	var selections = grid.getSelectionModel().getSelections();
	if (selections.length == 0) {
		Ext.Msg.alert("系统提示", "请选择要删除的记录！");
		return;
	}
	var ids = Array();
	for (var i = 0; i < selections.length; i++) {
		ids.push(eval('selections[i].data.'+idName));
	}
	var params={
		url:url,
		ids:ids,
		grid:grid
	};
	AppUtil.postDel(params);
};
/**
 * 全局删除记录函数
 * @param {} conf
 */
AppUtil.postDel = function(conf){
	
	Ext.Msg.confirm('信息确认', '您确认要删除所选记录吗？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url :conf.url,
					waitMsg:'发送请求中...',
					params : {ids : conf.ids},method : 'POST',
					success : function(response,options) {
						Ext.Msg.alert('操作信息','成功删除该记录！');
						if(conf.callback){
							conf.callback.call(this);
							return;
						}
						if(conf.grid){
							conf.grid.getStore().reload();
						}
					},
					failure : function(response,options) {
						Ext.Msg.alert('操作信息','操作出错，请联系管理员！');
					}
				});
			}
	});
};
/**
 * 通用搜索方法
 * @param {} grid
 * @param {} toolbarIdArray
**/
AppUtil.search = function(grid,toolbarIdArray) {
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
	params.start = 0;
	params.limit = bbar.pageSize;
	store.load({
		params :params
	});
};

/**
 * 通用提交表单方法
 * @param {} conf
 */
AppUtil.postForm = function(conf){
	if(conf.formPanel.getForm().isValid()){
			var scope=conf.scope?conf.scope:this;
			conf.formPanel.getForm().submit({
					scope:scope,
					//url : conf.url,
					method : 'post',
					//params:conf.params,
					waitMsg : '正在提交数据...',
					success : function(fp, action) {
						if(conf.callback){
							conf.callback.call(scope,fp,action);
						}else{
							Ext.Msg.alert(SYS_MSG_TITLE, SYS_MSG_SUCCESS);
						}
					},
					failure : function(fp, action) {
						if(conf.callback){
							conf.callback.call(scope,fp,action);
						}else{
							Ext.Msg.alert(SYS_MSG_TITLE, SYS_MSG_FAILURE);
						}
					}
				});
	}
};

/**
 * 装载toolBar上的查询参数
 * @param {} toolbarIdArray
 */
AppUtil.loadBaseParams = function(toolbarIdArray){
	var baseParams = {};
	if(toolbarIdArray && toolbarIdArray.length > 0) {
		for(var i = 0; i < toolbarIdArray.length; i++) {
			var currentToolbar = Ext.getCmp(toolbarIdArray[i]);
			for(var j = 0; j < currentToolbar.items.length; j++) {
				if(currentToolbar.items.items[j].ref) {
					baseParams[currentToolbar.items.items[j].ref] = currentToolbar.items.items[j].getValue();
				}
			}
		}
	}
	return baseParams;
};

/**
 * 有多个toolbar时调用的清空查询条件方法方法
 **/
AppUtil.resetSearch = function(toolbarIdArray) {
	if(toolbarIdArray && toolbarIdArray.length > 0) {
		for(var i = 0; i < toolbarIdArray.length; i++) {
			var currentToolbar = Ext.getCmp(toolbarIdArray[i]);
			for(var j = 0; j < currentToolbar.items.length; j++) {
				if(currentToolbar.items.items[j].ref) {
					currentToolbar.items.items[j].setValue(currentToolbar.items.items[j].defaultValue);
				}
			}
		}
	}
};