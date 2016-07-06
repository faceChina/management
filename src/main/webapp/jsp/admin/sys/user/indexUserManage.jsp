<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门用户管理</title> 
<%@include file="../../common/base.jsp" %>


<script type="text/javascript" src="<%=request.getContextPath()%>/monalisa/sys/user/IndexViewPanelUser.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/monalisa/sys/user/LeftViewTreePanelOrgan.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/monalisa/sys/user/RightViewTabPanelUser.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/monalisa/sys/user/EditGridOrganUser.js" charset="UTF-8"></script>
<link rel='stylesheet' type='text/css' href='../../../js/extjs/custom/ItemSelector/Multiselect.css'>
<script type="text/javascript" src="../../../js/extjs/custom/ItemSelector/Multiselect.js" charset="UTF-8"></script>
<script type="text/javascript" src="../../../js/extjs/custom/ItemSelector/DDView.js" charset="UTF-8"></script>



 
  <script type="text/javascript">
     var querySysOrganTreeNodeUrl = ROOT_PATH+'/organ/querySysOrganTreeNode.do';//部门树节点查询url
	 var addSysUserUrl = ROOT_PATH+'/user/addSysUser.do';//新增用户url
	 var delSysUserUrl =ROOT_PATH+"/user/delSysUser.do";//根据选中的多项用户ID进行删除url  
	 var queryOrganUserListUrl = ROOT_PATH+"/user/queryOrganUserList.do";//部门的用户列表查询url
      
<%--  var menuTreeIcon ='<%=net.constant.ExtImgCssCode.DEPT_TREE_ICON%>'; --%>
 var jspChooseOrganId;
//重新创建右边部门信息模板信息 如果右边部门信息组件存在 则加载左边树的信息  用于 点击左边树的节点或是新增一级菜单时所用
    function jspInitRightFormPanel(organId){
    jspChooseOrganId=organId;
    var tabPanelUser = Ext.getCmp('f1_tabpanel_user_view');//右边TAB总体页面
    var userInfoTabPanel =new RightViewTabPanelUser({
		id:'f1_tabpanel_user_view',
		region:'center',
		activeTab: 0,
		width:340,
		border:false,
		labelWidth :60,
		organId:organId
		})
    changeObjectCmpByTree('f1_panel_user_view',userInfoTabPanel);//删除原有右边用户信息模块里的组件 重新创建
   }
   
    
    function jspOpenAddUserWindow(){
    window.open ('addUserWindow.jsp?organId='+jspChooseOrganId, 'addUserWindow', 'height=380, width=650, top=160, left=450, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no')
 ///window.showModalDialog("addUserWindow.jsp?organId="+jspChooseOrganId,"dialogWidth=200px;dialogHeight=100px");   
    }
    //刷新部门用户列表
     function jspReloadOrganUserGrid(){
      var gridPanel = Ext.getCmp('grid-organUser-view');
     gridPanel.getStore().proxy = new Ext.data.HttpProxy({url:queryOrganUserListUrl});
	 gridPanel.getStore().load({params:{"organId":jspChooseOrganId,start:0, limit:15}});
    }
    //弹出编辑用户信息窗口
    function editOrganUserInfo(userId){
       window.open ('editUserWindow.jsp?userId='+userId, 'editUserWindow', 'height=380, width=650, top=160, left=450, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no')

    }
      //编辑用户信息操作栏
       function renderOptView(value, cellmeta, record, rowIndex, columnIndex, store) {
        var suId = ""+record.data['suId']+"";
        var arr = new Array();
        arr[0] ="user_edit.png#修改用户#editOrganUserInfo(\""+suId+"\")#jgrygl_update";
       var str = dataGridButtonAuthor(arr);
        return str;
    } 
  
 Ext.onReady(function() {
     Ext.QuickTips.init();
     var userManageView = new UserManageView({id:'f_panel_user_view'});
      var viewport = new Ext.Viewport( {
            layout : 'fit',
            border : false,
            items : [userManageView]
        });
    
     
});
</script>
</head>
<body>
</body>
</html>