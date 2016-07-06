Ext.ns('Ext.ux.evecom.form');

Ext.ux.evecom.form.ImageUploadWindow = Ext.extend(Ext.Window,{
	constructor:function(cfg){
		Ext.applyIf(this,cfg);
		//初始化组件方法
		this.initUIComponents(this);
		Ext.ux.evecom.form.ImageUploadWindow.superclass.constructor.call(this, {
			modal:true,
			layout:'fit'
		});
	},
	initUIComponents:function(thiz){
		var _fp = new Ext.FormPanel({
	        fileUpload: true,
	        border:false,
	        region:'north',
	        height:63,
	        padding:5,
	        labelWidth: 60,
	        defaults: {
	            anchor: '95%',
	            allowBlank: false,
	            msgTarget: 'side'
	        },
	        items: [
	        	{
		            xtype: 'fileuploadfield',
		            id: 'form-file',
		            emptyText: '点击右侧按钮选择图片...',
		            fieldLabel: thiz.imageFieldLabel == undefined ?'图片':thiz.imageFieldLabel,
		            name: 'photo-path',
		            buttonText: '',
		            buttonCfg: {
		                iconCls: 'upload-image-icon'
		            }
	        	}
	        ],
	        bbar: ['->',{
	            text: '上传',
	            handler: function(){
	                if(_fp.getForm().isValid()){
	                	var _fname = document.getElementById(thiz.textFieldId).value;
		                _fp.getForm().submit({
		                    url: __ctxPath+'/sys/SingleFileUpload?userId='+curUser.userId+'&siteId='+curUser.curSite.siteId+'&attModuleFolder='+thiz.attModuleFolder+'&fname='+_fname,
		                    waitMsg: '上传中...',
		                    success: function(fp, o){
		                    	//document.getElementById(thiz.textFieldId).value = o.result.file.attFileRealName;
		                    	//设置隐藏表单为filepath
//		                    	document.getElementById(thiz.textFieldId).value = o.result.file.filepath;
//		                    	if(thiz.debug){
//		                    		document.getElementById(thiz.showId).value = o.result.file.filepath;
//		                    	}else document.getElementById(thiz.showId).value = o.result.file.attFileRealName;
		                    	var rs = '';
		                    	if(thiz.valueType=='url'){
		                    		rs = o.result.file.url;
		                    	}else if(thiz.valueType == 'id'){
		                    		rs = o.result.file.attFileId;
		                    	}else if(thiz.valueType == 'realname'){
		                    		rs = o.result.file.attFileRealName;
		                    	}else if(thiz.valueType == 'name'){
		                    		rs = o.result.file.attFileName;
		                    	}else if(thiz.valueType == 'filepath'){
		                    		rs = o.result.file.filepath;
		                    	}else rs = o.result.file.attFileRealName;
		                    	document.getElementById(thiz.textFieldId).value = rs;
//		                    	if(thiz.debug){
//		                    		document.getElementById(thiz.showId).value = rs;
//		                    	}
		                    	thiz.close();
		                    },
		                    failure : function(fp, o) {
								Evecom.tips.msg("操作提示",o.result.msg);
							}
		                });
	                }
	            }
	        },'-',{
	            text: '重置',
	            handler: function(){
	                _fp.getForm().reset();
	            }
	        },'-',{
	            text: '关闭',
	            handler: function(){
	            	thiz.close();
	            }
	        }]
	    });
	    //==========
	    thiz.items = [_fp];
	}
});


Ext.ux.evecom.form.ImageUploadPanel = Ext.extend(Ext.Panel,{
	constructor:function(cfg){
		Ext.applyIf(this,cfg);
		//初始化组件方法
		this.initUIComponents(this);
		Ext.ux.evecom.form.ImageUploadPanel.superclass.constructor.call(this, {
		});
	},
	initUIComponents:function(thiz){
		thiz.items = [
			{
				xtype:'compositefield',
				msgTarget : 'side',
	       		defaults: {
	       			flex: 1,
	           		height:22
	         	},
	         	items:[
	         		//显示时候用的input
	         		{
	      				xtype:'textfield',
	      				fieldLabel : thiz.imageFieldLabel,
	      				name:thiz.textFieldName,
	              		id:thiz.textFieldId,
	            		flex:3
	              	}
	              	//提交按钮
	              	,{
	              		xtype:'button',
	              		text:'添加图片',
	              		iconCls:'upload-image-icon',
	              		handler:function(){
	              			var _win = Ext.getCmp('_ImageUploadWindow');
	              			if(!_win){
	              				new Ext.ux.evecom.form.ImageUploadWindow({
	              					id:'_ImageUploadWindow',
	              					title:'上传图片',
	              					width:600,
	              					autoHeight:true,
	              					attModuleFolder:thiz.attModuleFolder,
	              					imageFieldLabel:thiz.imageFieldLabel,
	              					textFieldId:thiz.textFieldId,
	              					valueType:thiz.valueType==undefined?'realname':thiz.valueType
	              				}).show();
	              			}else _win.show();
	              		}
	              	}
	         	]
			}
		];
	}
});

Ext.reg('imageUploadPanel',Ext.ux.evecom.form.ImageUploadPanel);