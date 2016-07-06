/**
 * 新增部门用户窗口
 * @class AddFormPanelOrganUser
 * @extends Ext.Panel
 * formPanel 新增部门用户信息表单
 */
AddFormPanelOrganUser = Ext.extend(Ext.Panel,{
	/**
	 * 重写构造函数，cfg为自定义参数
	 * @param {} cfg
	 */
	constructor : function(cfg){
		Ext.applyIf(this,cfg);
		this.initUIComponents(this);
		AddFormPanelOrganUser.superclass.constructor.call(this, {
			modal : true,
			layout:'fit',
			iconCls : "user_edit_mag",
			buttonAlign : 'center',
			items:[this.formPanel]
		});
	},
	/**
	 * 定义要用到的面板
	 */
	formPanel:null,
	initUIComponents:function(selfCmp){
		/**
		 * 表单
		 */
		selfCmp.formPanel = new Ext.form.FormPanel({
			 id:'formPanel_organUser_add',
			 labelWidth:80,
			 layout: 'form',
			 bodyStyle:"padding:5px 20px 0",
			frame:true,
			labelAlign:"right",
			url:addSysUserUrl,
 		items:[
			{xtype:'hidden',name:'organFormId',id:'addOrganId',value:selfCmp.organId},
				//第一行
			{
			layout:'column',
            items : [
				{//第一行第一列
				columnWidth:.5, 
                layout: 'form',
                items:[
                {   
                    xtype:"textfield",
				    fieldLabel:'姓名',
				    width:200,
                    allowBlank:false,
                    blankText:"姓名不能为空!",
                    name: 'sysUser.suRealname',
                    id: 'suRealname',
                    maxLength:30
				}]
				},
				{//第一行第二列
				columnWidth:.5, 
                layout: 'form',
                items:[
                {
                	xtype:"textfield",
				    fieldLabel: '登录名',
                    name: 'sysUser.suLoginname',
                    width:200,
                    id: 'suLoginname',
                    maxLength:50
				}]
				}
				]
				},
				//第二行
				{
			layout:'column',
            items : [
				{//第二行第一列
				columnWidth:.5, 
                layout: 'form',
                items:[
                {   
                   xtype:"combo",
                  name:"sysUser.suSex",
                   id: 'suSex',
                   width:200,
                  fieldLabel:"性别",
                  allowBlank:false,
                  store:["男","女"],
                  emptyText:"请选择性别",
                  triggerAction:'all'
				}]
				},
				{//第二行第二列
				columnWidth:.5, 
                layout: 'form',
                items:[
                {
                	xtype:"textfield",
				    fieldLabel:'民族',
				    width:200,
                    name: 'sysUser.suNation',
                    id: 'suNation',
                    maxLength:10
				}]
				}
				]
				},
				//第三行
				
				//第四行
				{
			layout:'column',
            items : [
				{//第四行第一列
				columnWidth:.5, 
                layout: 'form',
                items:[
                {   
                    xtype:"textfield",
				    fieldLabel:'籍贯',
                    name: 'sysUser.suNativePlace',
                    width:200,
                    id: 'suNativePlace',
                    maxLength:20
				}]
				},
				{//第四行第二列
				columnWidth:.5, 
                layout: 'form',
                items:[
                {
                	xtype:"textfield",
				    fieldLabel: '手机',
                    name: 'sysUser.suTel',
                    width:200,
                    id: 'suTel',
                    maxLength:50
				}]
				}
				
				]
				},
				//第五行
				{
			layout:'column',
            items : [
				{//第五行第一列
				columnWidth:.5, 
                layout: 'form',
                items:[
                {   
                    xtype:"textfield",
				    fieldLabel:'单位电话',
                    name: 'sysUser.suUnitPhone',
                    width:200,
                    id: 'suUnitPhone',
                    maxLength:50
				}]
				},
				{//第五行第二列
				columnWidth:.5, 
                layout: 'form',
                items:[
                {
                	xtype:"textfield",
				    fieldLabel: '职务',
                    name: 'sysUser.suPost',
                    width:200,
                    id: 'suPost',
                    maxLength:20
				}]
				}
				
				]
				},
				//第六行
				{
			layout:'column',
            items : [
				{//第六行第一列
				columnWidth:.5, 
                layout: 'form',
                items:[
                {   
                    xtype:"textfield",
				    fieldLabel:'内网邮箱',
                    name: 'sysUser.suIntranetEmail',
                    width:200,
                    id: 'suIntranetEmail',
                    maxLength:50
				}]
				},
				{//第六行第二列
				columnWidth:.5, 
                layout: 'form',
                items:[
                {
                	xtype:"textfield",
				    fieldLabel: '外网邮箱',
                    name: 'sysUser.suNetworkEmail',
                    width:200,
                    id: 'suNetworkEmail',
                    maxLength:50
				}]
				}
				
				]
				},
				//第七行
				{
			layout:'column',
            items : [
				{//第七行第一列
				columnWidth:.5, 
                layout: 'form',
                items:[
                {   
                    xtype:"textfield",
				    fieldLabel:'家庭电话',
                    name: 'sysUser.suHomePhone',
                    width:200,
                    id: 'suHomePhone',
                    maxLength:50
				}]
				},
				{//第七行第二列
				columnWidth:.5, 
                layout: 'form',
                items:[
                {
                	xtype:"textfield",
				    fieldLabel: '家庭住址',
                    name: 'sysUser.suHomeAddress',
                    width:200,
                    id: 'suHomeAddress',
                    maxLength:500
				}]
				}
				
				]
				},
				
				{//第八行
				 xtype:"textarea",
             	 fieldLabel:"备注",
              	 name:"sysUser.suMemo", 
              	 id: 'suMemo',
              	 anchor:'88%',
                 maxLength:2000
				}
				]
		});
		/**
		 * 保存按钮栏
		 */
		
		var btnToolbar = new Ext.Toolbar({
			height : 30,
			items : [ '->','-',{
				text : "取消",
				iconCls : "all_cancel",
				handler : function() {
					selfCmp.close();
				}
			},'->','-',{
				text : "保存",
				iconCls : "all_save",
				style:'align:right',
				handler : function() {
					if (selfCmp.formPanel.getForm().isValid()) {
						selfCmp.formPanel.getForm().submit({
									waitMsg : "正在提交用户信息",
									success : function(form, action) {
										g_showTip('用户信息保存成功');
										//selfCmp.close();
										reloadUserGrid();
									},
									failure : function(form, action) {
									g_showTip('用户信息保存失败');
									}
								});
					}
				}
			}]
		});
		this.bbar=btnToolbar;
	}
});