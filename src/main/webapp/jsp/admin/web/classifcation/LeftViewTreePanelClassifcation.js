/**
 * 页面左边分类数界面
 * @class LeftViewTreePanelClassifcation
 * @extends EvecomTreePanel
 */
LeftViewTreePanelClassifcation = Ext.extend(Com.panel.EvecomTreePanel,{
	constructor:function(cfg){
		tpid:null;
		Ext.applyIf(this,cfg);
		this.initUIComponents(this);
		LeftViewTreePanelClassifcation.superclass.constructor.call(this, {
			rootText: '标准类目',
			rootId:'0',
			iconCls :" ",
			collapsible:true,
			iconCls :"organ_mag",
			rootIcon:'icon',
			serviceUrl: queryClassifcationTreeNodeUrl,
			listeners: {
				click: function(node) {
					//刷新右页面参数信息 此方法在indexOrganManage.jsp里定义
					jspInitRightFormPanel(node.attributes.id,node.attributes.leaf,node.attributes.text,node.attributes.customJson);
					nodePath = node.getPath();
					
					if(node.attributes.id=='0'){
						disableAllButton();
						Ext.getCmp('tbar_classifcation_add').enable();
					}
				}
			}
		});
	},
	initUIComponents:function(selfCmp){
		var tbar = new Ext.Toolbar({});
		tbar.add(['-',{
			disabled:true,
			id:'tbar_classifcation_add',
			text : "新增下级类目",
			iconCls : "all_add",
			handler : function(){
				if(!jspIsAdd){
					jspInitAddTypeWindow(jspChooseId);//增加一级菜单 传入父级ID 默认为0
				}else{
					g_showTip('商品类目最多只能存在三级');
				}
			}
		}]);
		tbar.add(['-',{
			disabled:true,
			id:'tbar_classifcation_addEdit',
			text : "发布",
			iconCls : "tab_form_edit",
			handler : function(){
				if(null==jspChooseId || ""==jspChooseId || "undefined"==jspChooseId){
					g_showTip('请选择要发布的商品类目');
					return;
				}
				
				if(jspChooseIsStatus==1 && jspIsAdd){
					g_showTip('发布成功');
					return;
				}

				var msgValue = "确定要发布<font color='red'>" + jspChooseText + "</font>";
				if(!jspIsAdd){
					msgValue += "文件夹下所有商品类目";
				}
				msgValue += "吗?";
				Ext.Msg.confirm("温馨提示", msgValue, function(msg){
					if(msg=='yes'){
						editClassifcationStatus(jspChooseId,'1');
					}
				});
			}
		}]);
		tbar.add([{
			disabled:true,
			hidden:true,
			id:'tbar_classifcation_hide',
			text : "隐藏",
			iconCls : "tab_form_edit",
			handler : function(){
				if(null==jspChooseId || ""==jspChooseId || "undefined"==jspChooseId){
					g_showTip('请选择要隐藏的商品类目');
					return;
				}
				
				/*
				if(jspChooseIsStatus && jspIsAdd){
					g_showTip('隐藏成功');
					return;
				}
				*/

				var msgValue = "确定要隐藏<font color='red'>" + jspChooseText + "</font>";
				if(!jspIsAdd){
					msgValue += "文件夹下所有商品类目";
				}
				msgValue += "吗?";
				Ext.Msg.confirm("温馨提示", msgValue, function(msg){
					if(msg=='yes'){
						editClassifcationStatus(jspChooseId,'-1');
					}
				});
			}
		}]);
		tbar.add(['-',{
			disabled:true,
			id:'tbar_classifcation_del',
			text : "删除",
			iconCls : "all_delete",
			handler : function(){
				if(null==jspChooseId || ""==jspChooseId || "undefined"==jspChooseId){
					g_showTip('请选择要删除的商品类目');
					return;
				}
				
				if(jspChooseIsStatus==1||jspChooseIsStatus==-1){
					if(jspIsAdd){
						g_showTip('该商品类目已发布或已隐藏,不可删除');
						return;
					}else{
						g_showTip('该文件夹下已有商品类目发布或已隐藏,不可删除');
						return;
					}
				}

				var msgValue = "确定要删除<font color='red'>" + jspChooseText + "</font>";
				if(!jspIsAdd){
					msgValue += "文件夹下所有商品类目";
				}
				msgValue += "吗?";
				Ext.Msg.confirm("温馨提示", msgValue, function(msg){
					if(msg=='yes'){
						delClassifcationStatus(jspChooseId);
					}
				});
			}
		}]);
		tbar.add(['-',{
			disabled:true,
			hidden:true,
			id:'tbar_classifcation_update',
			text : "修改",
			iconCls : "com_gird_cm_edit2",
			handler : function(){
				updateClassifcation(jspChooseId);
			}
		}]);
		selfCmp.tbar=tbar;
	}
});