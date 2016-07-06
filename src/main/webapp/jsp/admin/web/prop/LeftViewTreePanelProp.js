/**
 * 页面左边菜单树界面
 * @class ModuleLeftTreePanelView
 * @extends EvecomTreePanel
 */
LeftViewTreePanelProp = Ext.extend(Com.panel.EvecomTreePanel,{
	constructor:function(cfg){
		Ext.applyIf(this,cfg);
		this.initUIComponents(this);
		LeftViewTreePanelProp.superclass.constructor.call(this, {
		   	rootText: '标准属性',
		   	rootId:'0',
		   	iconCls :"sysmenu_mag",
		    rootIcon:'menuTreeicon',//此变量样式在indexModuleManage.jsp中定义
		    serviceUrl:queryPropListUrl,
		    listeners: {
		    	click: function(node) {
		    		if(node.attributes.id!='0'){
		    			//刷新右页面参数信息 此方法在indexDictManage.jsp里定义
	            		jspInitRightFormPanel(node.attributes.id,node.attributes.text,node.attributes.customJson,node.attributes.leaf);
	            	}
			    }
	        }
		});
	},
	initUIComponents:function(selfCmp){
		var tbar = new Ext.Toolbar({});
		tbar.add(['-',{
			id:"tbar_prop_add",
			text : "新增",
			iconCls : "all_add",
			handler : function(){
				jspInitAddPropWindow('0');//增加一级菜单 传入父级ID 默认为0
			}
		}]);
		tbar.add(['-',{
			disabled:true,
			id:'tbar_prop_addEdit',
			text : "发布",
			iconCls : "tab_form_edit",
			handler : function(){
				if(null==jspChoosePropId || ""==jspChoosePropId || "undefined"==jspChoosePropId){
					g_showTip('请选择要发布的类目属性');
					return;
				}
				
				if(jspChooseIsStatus==1 && jspIsAdd){
					g_showTip('该类目属性已发布');
					return;
				}

				var msgValue = "确定要发布<font color='red'>" + jspChooseText + "</font>";
				if(!jspIsAdd){
					msgValue += "文件夹下所有目录属性";
				}
				msgValue += "吗?";
				Ext.Msg.confirm("温馨提示", msgValue, function(msg){
					if(msg=='yes'){
						editPropStatus(jspChoosePropId,'1');
					}
				});
			}
		}]);
//		tbar.add([{
//			disabled:true,
//			hidden:true,
//			id:'tbar_prop_hide',
//			text : "隐藏",
//			iconCls : "tab_form_edit",
//			handler : function(){
//				if(null==jspChoosePropId || ""==jspChoosePropId || "undefined"==jspChoosePropId){
//					g_showTip('请选择要隐藏的商品类目');
//					return;
//				}
//				
//				/*
//				if(jspChooseIsStatus && jspIsAdd){
//					g_showTip('隐藏成功');
//					return;
//				}
//				*/
//
//				var msgValue = "确定要隐藏<font color='red'>" + jspChooseText + "</font>";
//				if(!jspIsAdd){
//					msgValue += "文件夹下所有商品类目";
//				}
//				msgValue += "吗?";
//				Ext.Msg.confirm("温馨提示", msgValue, function(msg){
//					if(msg=='yes'){
//						editPropStatus(jspChoosePropId,'-1');
//					}
//				});
//			}
//		}]);
		tbar.add(['-',{
			disabled:true,
			id:'tbar_prop_del',
			text : "删除",
			iconCls : "all_delete",
			handler : function(){
				if(null==jspChoosePropId || ""==jspChoosePropId || "undefined"==jspChoosePropId){
					g_showTip('请选择要删除的类目属性');
					return;
				}
				
				if(jspChooseIsStatus==1||jspChooseIsStatus==-1){
					if(jspIsAdd){
						g_showTip('该类目属性已发布,不可删除');
						return;
					}else{
						g_showTip('该文件夹下已有类目属性发布,不可删除');
						return;
					}
				}

				var msgValue = "确定要删除<font color='red'>" + jspChooseText + "</font>";
				if(!jspIsAdd){
					msgValue += "文件夹下所有类目属性";
				}
				msgValue += "吗?";
				Ext.Msg.confirm("温馨提示", msgValue, function(msg){
					if(msg=='yes'){
						delPropStatus(jspChoosePropId);
					}
				});
			}
		}]);   
		
		tbar.add(['-',{
			//disabled:true,
			hidden:true,
			id:'tbar_prop_update',
			text : "修改",
			iconCls : "com_gird_cm_edit2",
			handler : function(){
				updateProp(jspChoosePropId);
			}
		}]);
		tbar.add([{
			disabled:true,
			hidden:false,
			id:'tbar_prop_detail',
			text : "详情",
			iconCls : "fj_desc",
			handler : function(){
				detailProp(jspChoosePropId);
			}
		}]);
		selfCmp.tbar=tbar
	}
});