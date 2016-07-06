var custom = {};
custom.folderName="";//文件夹名称
custom.tableName ="C_TOPIC_DETAIL";//附件存入的表名
custom.tableId ="";//附件存入的表ID
custom.btype ="";//文件业务类型
custom.tempFileId ="";//临时生成的硬盘文件ID
Ext.ns('Ext.ux.evecom.form');
Ext.ux.evecom.form.FileUploadWindow = Ext.extend(Ext.Window,{
	constructor:function(cfg){
		Ext.applyIf(this,cfg);
		//初始化组件方法
		this.initUIComponents(this);
		Ext.ux.evecom.form.FileUploadWindow.superclass.constructor.call(this, {
			modal:true,
			layout:'fit',
			iconCls : "addtitleimg_upload"
		});
	},
	initUIComponents:function(thiz){
		var allowfiletype=".jpg.png.gif.jpeg";//文件上传类型
		var _fp = new Ext.FormPanel({
	        fileUpload: true,
	        border:false,
			frame:true,
	        region:'north',
	        height:80,
	        padding:5,
	        labelWidth: 80,
	        defaults: {
	            anchor: '95%',
	            allowBlank: false,
	            msgTarget: 'side'
	        },
	        items: [
	            {xtype:'hidden',name:'tableName',id:'tableName',value:custom.tableName},
	            {xtype:'hidden',name:'tableId',id:'tableId',value:custom.tableId},
	        	{
		            xtype: 'fileuploadfield',
		            id: 'form-file',
		            emptyText: '点击右侧按钮选择文件...',
		            fieldLabel: thiz.fileFieldLabel == undefined ?'请选择文件':thiz.fileFieldLabel,
		            name: 'photo-path',
		            buttonText: '',
		            buttonCfg: {
		                iconCls: 'query_panel_mag'
		            }, listeners:{
                		'fileselected':function (fb,v){		
		                        var temp=v.replace(/^.*(\.[^\.\?]*)\??.*$/,'$1'); //获取文件后缀名
		                        var temp1=temp.toLocaleLowerCase() //转换成小写字符
		                        if(allowfiletype.indexOf(temp1)==-1)
		                        {
		                                Ext.Msg.alert("错误","只能上传.jpg.png.gif.jpeg类型图片，请重新选择！");
		                                fb.setValue("");
		                        }
                			}
       					 }
	        	}
	        ],
	        bbar: ['->',{
	            text: '上传',
	            iconCls : 'db-icn-upload_',
	            scope:this,
	            handler: function(){
	            	console.dir(thiz.btype);
	                if(_fp.getForm().isValid()){
		                _fp.getForm().submit({
		                    url: ROOT_PATH+'/common/'+thiz.btype+'/upload.htm',
		                    waitMsg: '上传中...',
		                	success:function(resp,options){
		                    	Ext.getCmp("qg"+thiz.btype).setValue(options.result.resultDesc);
		                    	thiz.close();
		                    },
		                    failure : function(fp, o) {
                    		        g_showTip(o.result.msg);
							}
		                });
	                }
	            }
	        },'-',{
	            text: '关闭',
	            iconCls : 'all_cancel',
	            handler: function(){
	            thiz.close();
	            }
	        }]
	    });
	    thiz.items = [_fp];
	}
});


FileUploadPanel = Ext.extend(Ext.Panel,{
	constructor:function(cfg){
		Ext.applyIf(this,cfg);
		//初始化组件方法
		this.initUIComponents(this);
		FileUploadPanel.superclass.constructor.call(this, {
		});
	},
	initUIComponents:function(thiz){
		custom.folderName = thiz.folderName;
		custom.tableName = thiz.tableName;
		custom.tableId= thiz.tableId;
		custom.btype= thiz.btype;
		custom.tempFileId="";
		if(thiz.display!=undefined)thiz.hidden=thiz.display?false:true;
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
	      				xtype:'hidden',
	      				fieldLabel : thiz.fileFieldLabel,
	      				name:thiz.textFieldName,
	              		id:thiz.textFieldId,
	              		value:0
	              	}
	              	,{
	      				xtype:'textfield',
	              		id:"qg"+thiz.btype,
	            		flex:3
	              	}
	              	//提交按钮
	              	,{
	              		xtype:'button',
	              		text:'添加',
	              		width:80,
	              		iconCls : "addtitleimg_upload",
	              		handler:function(){
	              			var _win = Ext.getCmp('_FileUploadWindow');
	              			if(!_win){
	              				new Ext.ux.evecom.form.FileUploadWindow({
	              					id:'_FileUploadWindow',
	              					title:'上传文件',
	              					width:600,
	              					btype:thiz.btype,
	              					autoHeight:true
	              				}).show();
	              			}else _win.show();
	              		}
	              	}
	         	]
			}
		];
		if(thiz.tableId!=''){//新增表单操作
			custom.tableId=thiz.tableId;
			custom.btype=thiz.btype;
			Ext.Ajax.request({
			url:ROOT_PATH+'/common/queryTitleImgInfo.htm?tableId='+thiz.tableId+'&type='+thiz.btype,
			success:function(resp,options){
				if(null!= resp.responseText){
				var respText = Ext.util.JSON.decode(resp.responseText);
				thiz.setText(respText.resultDesc,thiz.btype);//设置名称
				}
			}
			,failure: function(response, options){
				Ext.MessageBox.alert('系统提示','撤销之前上传的文件失败');
			}
		});
		}
	},
	setText:function(value,btype){
		Ext.getCmp("qg"+btype).setValue(value);
	},
	editUploadTableId:function(tableId){
		if(custom.tableId.indexOf("TEMP")!=-1){//新增操作时 需要修改临时的TABLEID
			Ext.Ajax.request({
		    url:ROOT_PATH+'/common/updateTempTableId.htm?tableId='+tableId+'&tempTableId='+custom.tableId,
			success:function(response,options){
			}
			,failure: function(response, options){
				g_showTip('回转更新关联的附件操作失败');
			}
		});
	  }
	}
});

Ext.reg('fileUploadPanel',FileUploadPanel);