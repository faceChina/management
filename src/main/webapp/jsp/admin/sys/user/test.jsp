<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑岗位用户</title> 
<%@include file="../../common/base.jsp" %>

<link rel='stylesheet' type='text/css' href='<%=request.getContextPath()%>/js/extjs/custom/ItemSelector/Multiselect.css'>
<script type="text/javascript" src="../../../js/extjs/custom/ItemSelector/Multiselect.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/extjs/custom/ItemSelector/DDView.js" charset="UTF-8"></script>





 
  <script type="text/javascript">
   var delSysModuleUrl =ROOT_PATH+"/user/queryUserByItemSelector.do?organId=4028bb8138f04a370138f04a699a0000";//模块删除url
  
Ext.onReady(function(){

    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';
    

	/*
	 * Ext.ux.Multiselect Example Code
	 */

			var formMultiselect = new Ext.form.FormPanel({ 
				labelWidth: 75,
				labelAlign: "right",
				width:700,
				items:[{
					xtype:"multiselect",
					fieldLabel:"Multiselect",
					name:"multiselect",
					dataFields:["code", "desc"], 
					data:[[123,"One Hundred Twenty Three"],
						["1", "One"], ["2", "Two"], ["3", "Three"], ["4", "Four"], ["5", "Five"],
						["6", "Six"], ["7", "Seven"], ["8", "Eight"], ["9", "Nine"]],
					valueField:"code",
					displayField:"desc",
					width:250,
					height:200,
					allowBlank:true,
					legend:"Multiselect",
					tbar:[{text:"clear",handler:function(){formMultiselect.getForm().findField("multiselect").reset();}}]
				}],
				buttons:[{
					text:"Get Value",
					handler: function(){
						alert(formMultiselect.getForm().getValues(true));
					}
				},{
					text:"Set Value (2,3)",
					handler: function(){
						formMultiselect.getForm().findField("multiselect").setValue("2,3");
					}
				},{
					text:"Mark Invalid",
					handler: function(){
						formMultiselect.getForm().findField("multiselect").markInvalid("Invalid");
					}
				},{
					text:"Toggle Enabled",
					handler: function(){
						var m=formMultiselect.getForm().findField("multiselect");
						if (!m.disabled)m.disable();
						else m.enable();
					}
				},{
					text:"Reset",
					handler: function(){
						formMultiselect.getForm().findField("multiselect").reset();
					}
				}]
			});
			formMultiselect.render("form-ct-multiselect");
	
	
	/*
	 * Ext.ux.ItemSelector Example Code
	 */
	var formStore =new Ext.data.JsonStore({
		root: 'data',
		fields: [ 'suId','suRealname' ],
		url: delSysModuleUrl
});
formStore.load();
var _smsTemplateToStore = new Ext.data.Store({   
    id:"_smsTemplateToStore",   
    proxy : new Ext.data.HttpProxy({url:delSysModuleUrl}),   
    reader: new Ext.data.JsonReader({   
        totalProperty:"totalProperty",   
        root:"root"},   
    [   
        {name:"suId"},   
        {name:"suRealname"}   
            ]   
    )   
    });  
			var formItemSelector = new Ext.form.FormPanel({ 
				width:700,
        bodyStyle: 'padding:10px;',

				items:[{   
            xtype:"itemselector",   
            name:"itemselector",   
          
            //labelWidth:1,   
            dataFields:["suId", "suRealname"],   
            toData:[],   
           msWidth:200,   
            msHeight:260,
            valueField:"suId",   
            displayField:"suRealname",   
            imagePath:"<%=request.getContextPath()%>/js/extjs/custom/ItemSelector/",
            toLegend:"待选用户列表",
            fromLegend:"已选用户列表",  
            fromData:[],   
            fromStore:formStore,
            toStore:formStore,
             drawUpIcon:false 
        ,drawDownIcon:false 
        ,drawLeftIcon:true   
        ,drawRightIcon:true  
        ,drawTopIcon:false   
        ,drawBotIcon:false      
        }],
          
				buttons:[{
					text:"Get Value",
					handler: function(){
					formItemSelector.getForm().getValues
						alert(formItemSelector.getForm().getValues(true));
					}
				},{	
					text:"Mark Invalid",
					handler: function(){
						formItemSelector.getForm().findField("itemselector").markInvalid("Invalid");
					}
				}],
				bodyStyle:'background-color: transparent ;'
			});
			formItemSelector.render("form-ct-itemselector");
	
	
});
</script>

</head>
<body>
  <div id="form-ct-multiselect"></div>
  
        <div id="form-ct-itemselector"></div>
  
</body>
</html>