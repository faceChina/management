/**
 * 页面左边数据字典关联信息管理界面
 * @class ModuleButtonGridView
 * @extends Ext.Panel
 * gridPanel 数据字典关联列表
 */
EditGridDict = Ext.extend(Ext.Panel,{
	gridPanel:null,
	dictId:null,
	constructor:function(cfg){
		Ext.applyIf(this,cfg);
		this.initUIComponents(this);
		EditGridDict.superclass.constructor.call(this, {
			       iconCls :"dataedit_mag",
				   border:false,
				   layout:'fit',
				   items : [this.gridPanel]
				});
	},
	initUIComponents:function(selfCmp){
		   var tbar = new Ext.Toolbar({});
               tbar.add(['-',{
                id:'tbar_dictchild_add',
				text : "新增",
				iconCls : "all_add",
				handler : function(){
					jspInitAddDictChildWindow();
				}
			}]);
			    tbar.add(['-',{
			    id:'tbar_dictchild_del',
				text : "删除",
				iconCls : "all_delete",
				handler : function(){
                delGridData('grid-dictchild-view','id,name',delDictUrl,1,{"dictId":jspChooseDictId},200);
                jspReloadDictChildGrid();
				}
			}]);
//			var arr = new Array();
//            arr[0] = "tbar_dictchild_add,sjzd_edit";
//            arr[1] = "tbar_dictchild_del,sjzd_edit";
//            addAuthorToolBar(arr);
		//列表显示控件定义
        var dictChildGridC=[{header : "参数ID",dataIndex : "id",hidden : true},
                              {id:"name",header:"参数名称",width:200,dataIndex:"name"},
                              {id:"code",header:"参数编码",width:200,dataIndex:"code"},
                              {id:"memo",header:"参数备注",width:200,dataIndex:"memo"},
                              {id:"eidtColumn",header:"操作",width:200,renderer:renderOptView}];
		//后台到前台列表字段绑定规格
		var dictChildGridT=['id','name','code','memo','smbValue'];
		   selfCmp.gridPanel=new Com.panel.EvecomBaseGridPanel({
		   queryUrl:queryDictGridUrl+'?dictId='+jspChooseDictId,//jsp传来
		   selType:1,
		   pkField:"smbId",
		   title:"参数列表",
		   fieldsArr:dictChildGridT,
		   columnsArr:dictChildGridC,
		   tbar:tbar,
		   viewConfig:{
				forceFit:true//自主决定每列的宽度，填充满表格
		   },
		   id:"grid-dictchild-view"
		});
	}
	//setModuleValue:function(moduleIdValue){
		//this.moduleId = moduleIdValue;
	//}
});
Ext.reg('editGridDict', EditGridDict);