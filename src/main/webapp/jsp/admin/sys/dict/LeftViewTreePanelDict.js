/**
 * 页面左边参数类别树界面
 * @class ModuleLeftTreePanelView
 * @extends EvecomTreePanel
 */
LeftViewTreePanelDict = Ext.extend(Com.panel.EvecomTreePanel,{
	constructor:function(cfg){
		Ext.applyIf(this,cfg);
		this.initUIComponents(this);
		LeftViewTreePanelDict.superclass.constructor.call(this, {
	   	rootText: '参数类别菜单',
	   	rootId:'0',
	   	iconCls :"database_mag",
	    rootIcon:"icon",//此变量样式在indexManage.jsp中定义
	    serviceUrl:queryDictTreeNodeUrl,
	       listeners: {
			            click: function(node) {
			            	if(node.attributes.id!='0'){
			            jspInitRightFormPanel(node.attributes.id);//刷新右页面参数信息 此方法在indexDictManage.jsp里定义
			            	}
					  }
			        }
				});
	},
	initUIComponents:function(selfCmp){
			  var tbar = new Ext.Toolbar({});
               tbar.add(['-',{
                id:'tbar_dict_add',
				text : "新增",
				iconCls : "all_add",
				handler : function(){
					jspInitAddDictWindow('0');//增加一级菜单 传入父级ID 默认为0
				}
			}]);
			tbar.add(['-',{
			    id:'tbar_dict_addEdit',
				text : "编辑",
				iconCls : "tab_form_edit",
				handler : function(){
					editDictInfo();//编辑参数类型
				}
			}]);
			tbar.add(['-',{
			    id:'tbar_dict_del',
				text : "删除",
				iconCls : "all_delete",
				handler : function(){
					delDict();//删除参数类型
				}
			}]);
//			
//			 var arr = new Array();
//            arr[0] = "tbar_dict_add,sjzd_edit";
//            arr[1] = "tbar_dict_addEdit,sjzd_edit";
//            arr[2] = "tbar_dict_del,sjzd_edit";
//            addAuthorToolBar(arr);
		selfCmp.tbar=tbar
	}
});