<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>脸谱中国运营后台管理中心</title>
		<%@include file="../../common/base.jsp"%>
		<script type="text/javascript" src="${jspPath}/sys/main/UserLeftMenuTreePanel.js" charset="UTF-8"></script>
		
		<script type="text/javascript">
			var defaultId = '0';
		 	var pageSysUrl =ROOT_PATH+"/jsp/admin/";
			var opentabId;
			
			var cntTab = new Ext.TabPanel(
			{//右边标签面板  
		     	region:'center',
		     	//deferredRender:false,  
		     	activeTab:0,
		     	enableTabScroll:true,
		     	id:"cntTab",
		     	margins: '1 0 1 0',
		     	loadMask:{msg:'加载中...'},
		     	listeners:{
			     	beforeremove:removeTabEvent
			 	} 
			});
			
			Ext.onReady(fn);
		 	
			function removeTabEvent(tabpanel, tab) {
				//移除tab            
				var frmId="Frm-"+tab.id;
				g_clearIframe(frmId);
				tabpanel.un('beforeremove', removeTabEvent);
				Ext.destroy(tab);
			    tabpanel.remove(tab);
			    //Ext.Panel.superclass.onDestroy.call(tab);
				//增加beforeremove事件      
				tabpanel.addListener('beforeremove', removeTabEvent, tabpanel);
				Ext.destroy(Ext.query(".x-panel-bwrap"));
			    //x-panel-bwrap
				
				delete frmId;
				 //这一句很关键	
				return false;
			}
			function addTabUrl(tabId,strTitle,strUrl,iconCls){
		    	if(cntTab.items.length>9){
		    		g_showTip('提示信息', "最多只能打开10个tab页"); 
			      	return false;
			    }
				var subTab = cntTab.getComponent("ext-comp-"+tabId);  
			    if (!subTab) {          	   
		        	cntTab.add({
			        	border:false,
			    		frame:false,
			            id : "ext-comp-"+tabId,  //加上报错
			            title : strTitle,
			            layout:"fit",
			            iconCls:iconCls,
			            autoDestroy:true,
			            closable:true,
			            destroy:function (){//销毁tabpanel
		                    if(this.fireEvent("destroy",this)!=false){
	                        	this.el.remove();
		                        subTab = null;
		                        if(Ext.isIE){
		                            CollectGarbage();
		                        }
		                    }
		                },
			            html :'<iframe name="Frm-ext-comp-'+tabId+'" id="Frm-ext-comp-'+tabId+'" scrolling="auto" frameborder="0" width="100%" height="100%" src="'+strUrl+'" top="0"></iframe>'  
		            });
			        
		       		cntTab.setActiveTab(cntTab.getComponent("ext-comp-"+tabId));       
			    }else{
			       cntTab.setActiveTab(subTab);
			    }
			    delete subTab;
			}
			function testchild(){
				window.frames['Frm-ext-comp'].testfather();
			}
			function menuNdClk(menuNode){
		   		if(typeof menuNode.attributes.url!="undefined" && menuNode.attributes.url!=null){
			  		addTabUrl(menuNode.attributes.id,menuNode.attributes.text,menuNode.attributes.url,menuNode.attributes.iconCls);
			  		opentabId = menuNode.attributes.id;
		  		}
			}
			function isExitsTab(id){
		 		var item=cntTab.getComponent("ext-comp-"+id);
			 	if(!item){
			 		return false;
			 	}else{
			 		cntTab.setActiveTab(item);
			 		return true;
			 	}
		 	}
			function closeTab(tabId){
				var item=cntTab.getComponent("ext-comp-"+tabId);
				cntTab.remove(item);
			}
			function closeTabToAddTabUrl(tabId,strTitle,strUrl,iconCls){
				var item=cntTab.getComponent("ext-comp-"+tabId);
				cntTab.remove(item);
				
				addTabUrl(tabId,strTitle,strUrl,iconCls);
			}
			function activateTab(tabId){
				var item=cntTab.getComponent("ext-comp-"+tabId);
				cntTab.activate(item);
			}
			function getTab(tabId){
				var item=cntTab.getComponent("ext-comp-"+tabId);
				return item;
			}
			function fn(){
				Ext.QuickTips.init(); 
				 
				new Ext.Viewport({
					enableTabScroll:true,
					layout:"border",
					autoDestroy:true,
					items:[
						{
							//title:"面板",
							region:"north",
							collapsible:false,
							layoutConfig:{animate:false},
							border:false,
							html:"<iframe name='headerPage' id='headerPageId' scrolling='no' frameborder='0' width='100%'  src='top.jsp' height='98' marginheight='0' marginwidth='0' scrolling='no' frameborder='no' align='top'></iframe>"
						},
						{
							title:"系统菜单",
							iconCls: 'websites_mag',
							id:'sys_menu_west',
							region:"west",
							width:200,
							layoutConfig:{animate:false},
							layout:"fit",
							collapsible:true,
							margins: '1 1 1 0',
							items: [
					        	{
							   		xtype: 'userLeftMenuTreePanel'
							 	}
				        	]
						},
						cntTab
					]
				});
			}    
	     	//修改用户密码
	     	function passWordEdit(){
            	var formWindow = Ext.getCmp('window-password_edit');
				if(!formWindow){
					new PassWordEditWindow({title:'用户密码修改',id:'window-password_edit',sessionUserId:sessionUserId,loginName:loginName,passWord:passWord}).show();
				}else formWindow.show();
	     	}
			function chgTheme(strCssName){
				changeTheme(strCssName);
				document.location="<%=request.getContextPath()%>/frm/main.jsp";
			}
			function chooseLeftMenu(modulePid,moduleName){
				Ext.getCmp('sys_menu_west').setTitle(moduleName);
				var userMenuTreePanel = Ext.getCmp("treePanel-userMenu-view");
				userMenuTreePanel.onDestroy();
				var menuPanel = new UserLeftMenuTreePanel({modulePid:modulePid});
				userMenuTreePanel.add(menuPanel);
				userMenuTreePanel.doLayout();
			}
			function closeWin(){
			    g_CloseWin("winNewId","frmId");
			}
			function excFun(){
			  	var tabId=(cntTab.getActiveTab()).id
			  	var frmId="Frm-"+tabId;
			  	window.frames[frmId].refreshGrid();
			}
			function m_showMsg(strTitle,strMsg){
			  	Ext.Msg.alert(strTitle,strMsg);
			}
			
			function g_setFormValues(strUrl,formInfo){	
		      	g_formLoadData(formInfo[0].id,strUrl);
			}
			/**
			* 注销用户
			*/
			function logout(){
				window.location.href = './login.jsp';
			}
			function closeSysWin(){
				window.close();
			}
			/*
			* 弹出窗口_by Lyk
			*/
			function showWin(obj){
				var kk_formWin = new Ext.Window({
			  		id:"winId",
				  	title : "test",
				  	width : "800",
				  	heigth : "600",
				  	resizable : true,
				  	//autoHeight : true,
				  	modal : true,
				  	closeAction : 'close',
				  	items : obj
			 	});
				kk_formWin.show();
			}
		</script>
	</head>
	<body style="">
		<input type="hidden" id="menuid" value="aaaa">
	</body>
</html>


