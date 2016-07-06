<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑用户信息</title> 
<%@include file="../../common/base.jsp" %>


<script type="text/javascript" src="<%=request.getContextPath()%>/monalisa/sys/user/EditFormPanelOrganUser.js" charset="UTF-8"></script>





 
  <script type="text/javascript">
	 var modifySysUserUrl = ROOT_PATH+'/user/modifySysUser.do';//修改用户信息url
	 var querySysUserObjectUrl =ROOT_PATH+"/user/querySysUserObject.do?userId=";//查询用户明细信息Url
	 
	 var userId = '<%=request.getParameter("userId")%>';
     function reloadUserGrid(){
     window.opener.jspReloadOrganUserGrid();
      window.opener = null; 
      window.open('', '_self', '');
      window.close();
     }
 
     
 Ext.onReady(function() {
     Ext.QuickTips.init();
     var editFormPanelOrganUser = new EditFormPanelOrganUser({title:'编辑用户',id:'f_window_editUser_view'});
     editFormPanelOrganUser.loadForm(userId);
      var viewport = new Ext.Viewport( {
            layout : 'fit',
            border : false,
            items : [editFormPanelOrganUser]
        });
    
     
});
</script>
</head>
<body>
</body>
</html>