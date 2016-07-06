<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>模块定制管理</title> 
<%@include file="../../common/base.jsp" %>

<script type="text/javascript" src="${ctx}/jsp/admin/sys/resource/AddWindowModule.js" charset="UTF-8"></script>
<script type="text/javascript" src="${ctx}/jsp/admin/sys/resource/EditFormPanelModule.js" charset="UTF-8"></script>
<script type="text/javascript" src="${ctx}/jsp/admin/sys/resource/LeftViewTreePanelModule.js" charset="UTF-8"></script>
<script type="text/javascript" src="${ctx}/jsp/admin/sys/resource/RightViewTabPanelModule.js" charset="UTF-8"></script>
<script type="text/javascript" src="${ctx}/jsp/admin/sys/resource/IndexViewPanelModule.js" charset="UTF-8"></script>
  <script type="text/javascript">
 var jspChooseModuleId;//此模块ID赋值 以备模块按钮列表关联模块查询
 var queryModuleTreeNodeUrl = ROOT_PATH+"/admin/modual/tree.htm";//查询模块节点树
 var addSysModuleUrl = ROOT_PATH+"/admin/modual/add.htm";//模块新增url
 var modifySysModuleUrl =ROOT_PATH+"/admin/modual/edit.htm";//模块修改url
 var delSysModuleUrl =ROOT_PATH+"/admin/modual/del.htm";//模块删除url
 var querySysModuleObject =ROOT_PATH+"/admin/modual/get.htm";//模块明细信息查询
 
    //重新创建右边菜单信息模板信息 如果右边菜单信息组件存在 则加载左边树的信息  用于 点击左边树的节点或是新增一级菜单时所用
    function jspInitRightFormPanel(moduleId){
    jspChooseModuleId=moduleId;
    var tabPanelModule = Ext.getCmp('f1_tabpanel_module_view');//左边TAB总体页面
    if(!tabPanelModule){//如果左边Tab没被创建过 则新创建TAB右边模块信息 并加入
	    var moduleInfoFormPanel =new RightViewTabPanel({
			id:'f1_tabpanel_module_view',
			region:'center',
			activeTab: 0,
			width:340,
			border:false,
			labelWidth :60
			})
		 changeObjectCmpByTree('f1_panel_module_view',moduleInfoFormPanel);//删除原有右边模块信息模块里的组件 重新创建
    }
     var formPanelEditModule = Ext.getCmp('f2_formPanel_module_edit');//左边TAB module信息组件 重新加载
     formPanelEditModule.loadForm(moduleId);//加载模块表单信息
    }
    //新增一级菜单或下级菜单时需要打开新增窗口
     function jspInitAddModuleWindow(smPid){
                 var formWindow = Ext.getCmp('window-module-add');
		if(!formWindow){
			new AddWindowModule({title:'新增菜单',id:'window-module-add',pid:smPid}).show();
		}else formWindow.show();
    }
 Ext.onReady(function() {
     Ext.QuickTips.init();
     var moduleManageView = new ModuleManageView({id:'f_panel_module_view'});
     new Ext.Viewport( {
            layout : 'fit',
            border : false,
            items : [moduleManageView]
      });
});
</script>
</head>
<body>
</body>
</html>