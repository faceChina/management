<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据字典管理</title> 
<%@include file="../../common/base.jsp" %>

<script type="text/javascript" src="${ctx}/jsp/admin/sys/dict/AddWindowDict.js" charset="UTF-8"></script>
<script type="text/javascript" src="${ctx}/jsp/admin/sys/dict/LeftViewTreePanelDict.js" charset="UTF-8"></script>
<script type="text/javascript" src="${ctx}/jsp/admin/sys/dict/RightViewTabPanelDict.js" charset="UTF-8"></script>
<script type="text/javascript" src="${ctx}/jsp/admin/sys/dict/IndexViewPanelDict.js" charset="UTF-8"></script>
<script type="text/javascript" src="${ctx}/jsp/admin/sys/dict/EditGridDict.js" charset="UTF-8"></script>
<script type="text/javascript" src="${ctx}/jsp/admin/sys/dict/AddWindowDictChild.js" charset="UTF-8"></script>
<script type="text/javascript" src="${ctx}/jsp/admin/sys/dict/EditWindowDict.js" charset="UTF-8"></script>
<script type="text/javascript" src="${ctx}/jsp/admin/sys/dict/EditWindowDictChild.js" charset="UTF-8"></script>

  <script type="text/javascript">
 var jspChooseDictId;//此模块ID赋值 以备数据字典列表关联模块查询
 var addSysDictUrl = ROOT_PATH+"/admin/dict/add.htm";//数据字典参新增url
 var queryDictGridUrl = ROOT_PATH+'/admin/dict/listChild.htm';//数据字典列表查询url
 var delDictUrl = ROOT_PATH+'/admin/dict/delAll.htm';//数据字典参数删除url
 var delDictObjectUrl = ROOT_PATH+'/admin/dict/del.htm';//数据字典大类删除url
 var modifyDictUrl =ROOT_PATH+"/admin/dict/edit.htm";//数据字典修改rl
 var querySysDictObjectUrl =ROOT_PATH+"/admin/dict/get.htm";//数据字典明细信息查询
 var queryDictTreeNodeUrl = ROOT_PATH+"/admin/dict/tree.htm";//查询数据字典参节点树
<%--  var menuTreeIcon ='<%=net.constant.ExtImgCssCode.DEPT_TREE_ICON%>'; --%>
    //重新创建右边参数信息 如果右边参数信息组件存在 则加载左边树的信息  用于 点击左边树的节点时所用
    function jspInitRightFormPanel(dictId){
    jspChooseDictId=dictId;
    var tabPanelDict = Ext.getCmp('f1_tabpanel_dict_vaddAuthorToolBariew');//左边TAB总体页面
    if(!tabPanelDict){//如果左边Tab没被创建过 则新创建TAB右边模块信息 并加入
    var dictInfoFormPanel =new RightViewTabPanelDict({
		id:'f1_tabpanel_dict_view',
		region:'center',
		activeTab: 0,
		width:340,
		border:false,
		labelWidth :60
		})
	 changeObjectCmpByTree('f1_panel_dict_view',dictInfoFormPanel);//删除原有右边模块信息模块里的组件 重新创建
    }else{//如果左边Tab创建过了，则重新换GRID的地址 进行参数信息重新查询
    	var gridPanel = Ext.getCmp('grid-dictchild-view');
		if (gridPanel != null) {
		gridPanel.getStore().proxy = new Ext.data.HttpProxy({url:queryDictGridUrl+'?dictId='+jspChooseDictId});
		gridPanel.getStore().load({params:{"dictId":jspChooseDictId,start:0, limit:15}});
		}
   	} 
    }
    //新增参数类别
     function jspInitAddDictWindow(smPid){
                    var formWindow = Ext.getCmp('window-dict-add');
					if(!formWindow){
						new AddWindowDict({title:'新增参数类别',id:'window-dict-add',pid:smPid}).show();
					}else formWindow.show();
    }
     //新增参数信息
     function jspInitAddDictChildWindow(){
        var formWindow = Ext.getCmp('window-dictchild-add');
		if(!formWindow){
			new AddWindowDictChild({title:'新增参数信息',width:300,height:180,id:'window-dictchild-add',dictId:jspChooseDictId}).show();
		}else formWindow.show();
    }
      //刷新参数信息列表
     function jspReloadDictChildGrid(){
     var gridPanel = Ext.getCmp('grid-dictchild-view');
     gridPanel.getStore().proxy = new Ext.data.HttpProxy({url:queryDictGridUrl});
	 gridPanel.getStore().load({params:{"dictId":jspChooseDictId,start:0, pageSize:15}});
    } 
     //参数信息明细信息
     function editDictChildInfo(sbdId){
        var formWindow = Ext.getCmp('window-dictchild-edit');
					if(!formWindow){
						formWindow = new EditWindowDictChild({title:'修改参数信息',width:300,height:170,id:'window-dictchild-edit'});
					}
					formWindow.loadForm(sbdId);
                    formWindow.show();
    }
     //参数类别明细信息
     function editDictInfo(){
     if(typeof jspChooseDictId!="undefined"){
        var formWindow = Ext.getCmp('window-dict-edit');
					if(!formWindow){
						formWindow = new EditWindowDict({title:'修改参数类别',width:300,height:170,id:'window-dictchild-edit'});
					}
					formWindow.loadForm(jspChooseDictId);
                    formWindow.show();
                    }else{
                g_showTip("请选择要编辑的参数类别");
                return ;
                }   
    }
      //删除参数类别
      function delDict(){
       if(typeof jspChooseDictId!="undefined"){
               var url = delDictObjectUrl+"?dictId="+jspChooseDictId;
               delObjectById(url,'','f1_treepanel_dict_view','确定要删除此参数类别?','删除',300);  
                }else{
                g_showTip("请选择要删除的参数类别");
                return ;
                }   
    }
     //加载参数信息，得到参数信息的id
       function renderOptView(value, cellmeta, record, rowIndex, columnIndex, store) {
        var id = ""+record.data['id']+"";
        var arr = new Array();
         arr[0] ="gird_edit.png#修改参数信息#editDictChildInfo(\""+id+"\")#sjzd_edit";
        var str = dataGridButtonAuthor(arr);
        return null;
    }
 Ext.onReady(function() {
     Ext.QuickTips.init();
     var dictManageView = new DictManageView({id:'f_panel_dict_view'});
      var viewport = new Ext.Viewport( {
            layout : 'fit',
            border : false,
            items : [dictManageView]
        });
});
</script>
</head>
<body>
</body>
</html>