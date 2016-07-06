/**
 * 页面右边部门用户关联信息管理界面
 * @class EditGridOrganUser
 * @extends Ext.Panel
 * gridPanel 部门用户列表
 */
EditGridOrganUser = Ext.extend(Ext.Panel,{
	gridPanel:null,
	moduleId:null,
	constructor:function(cfg){
		Ext.applyIf(this,cfg);
		this.initUIComponents(this);
		EditGridOrganUser.superclass.constructor.call(this, {
			       iconCls :"organuser_menu",
				   border:false,
				   layout:'fit',
				   items : [this.gridPanel]
				});
	},
	initUIComponents:function(selfCmp){
		   var tbar = new Ext.Toolbar({});
               tbar.add(['-',{
               	id:'tbar_organUser_add',
				text : "新增",
				iconCls : "all_add",
				handler : function(){
					jspOpenAddUserWindow();
				}
			}])
			    tbar.add(['-',{
               	id:'tbar_organUser_del',
				text : "删除",
				iconCls : "all_delete",
				handler : function(){
                delGridData('grid-organUser-view','suId,suRealname',delSysUserUrl,1,{"organId":jspChooseOrganId},200);
				}
			}])
			var arr = new Array();
            arr[0] = "tbar_organUser_add,jgrygl_add";
            arr[1] = "tbar_organUser_del,jghygl_del";
            addAuthorToolBar(arr);
			
		//列表显示控件定义
		var userGridC=[{header : "用户id",dataIndex : "suId",hidden : true},
			{id:"suLoginname", header:"登录名",width:100,dataIndex:"suLoginname"},
			{id:"suRealname",header:"姓名",width:100,dataIndex:"suRealname"},
			{id:"suSex",header:"性别",width:100,dataIndex:"suSex"},
			{id:"suPost",header:"职务",width:100,dataIndex:"suPost"},
			{id:"suUnitPhone",header:"单位电话",width:100,dataIndex:"suUnitPhone"},
			{id:"suTel",header:"手机",width:100,dataIndex:"suTel"},
			{id:"suIntranetEmail",header:"内网邮箱",width:100,dataIndex:"suIntranetEmail"},
			{id:"bk",header:"操作",renderer:renderOptView}
		];	
		//后台到前台列表字段绑定规格
		   var userGridT=['suId','suLoginname','suRealname','suSex','suPost','suUnitPhone','suTel','suIntranetEmail'];
		   selfCmp.gridPanel=new Com.panel.EvecomBaseGridPanel({
		   queryUrl:queryOrganUserListUrl+"?organId="+jspChooseOrganId,//jsp传来
		   selType:1,
		   pkField:"suId",
		   title:"用户列表",
		   fieldsArr:userGridT,
		   columnsArr:userGridC,
		   tbar:tbar,
		   id:"grid-organUser-view"
		});
	}
});
Ext.reg('editGridOrganUser', EditGridOrganUser);