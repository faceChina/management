<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增用户</title> 
<%@include file="../../common/base.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/monalisa/sys/user/AddFormPanelOrganUser.js" charset="UTF-8"></script>
  <script type="text/javascript">
	 var addSysUserUrl = ROOT_PATH+'/user/addSysUser.do';//新增用户url
	 var organId = '<%=request.getParameter("organId")%>';
     function reloadUserGrid(){
     window.opener.jspReloadOrganUserGrid();
      window.opener = null; 
      window.open('', '_self', '');
      window.close();
     }
 
     
 Ext.onReady(function() {
     Ext.QuickTips.init();
     var addFormPanelOrganUser = new AddFormPanelOrganUser({title:'新增用户',id:'f_window_addUser_view',organId:organId});
      var viewport = new Ext.Viewport( {
            layout : 'fit',
            border : false,
            items : [addFormPanelOrganUser]
        });
    
     
});
</script>
</head>
<body>
</body>
</html>