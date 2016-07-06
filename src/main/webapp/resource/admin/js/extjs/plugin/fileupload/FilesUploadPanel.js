Ext.ns('Ext.ux.evecom.form');
var custom = {};
/**
 * folderName 文件夹名称
 */
custom.folderName="";//文件夹名称
custom.uploadFileId="";//暂时不用
custom.tableName ="";//附件存入的表名
custom.tableId ="";//附件存入的表ID
custom.uploadNum="20";//上传文件数 默认为20个
custom.uploadFileSize="500";//上传文件大小 默认为10M
custom.FileRecord = Ext.data.Record.create([{
	name : 'fileId'
}, {
	name : 'fileRealName'
}, {
	name : 'fileType'
}, {
	name : 'fileSize'
}, {
	name : 'fileUploadDate'
}]);
/**
 * 通过文件编号删除附件
 * @param {} ids array
 */
custom.deleteFileByIds = function(id,type){
		Ext.Ajax.request({
			url:ROOT_PATH+'/file-delete?fileId='+id+'&fileType='+type+'&delType=delTempFile&folderName='+custom.folderName,
			success:function(response,options){
            custom.uploadFileId=custom.uploadFileId.replace(","+id,"");
			}
			,failure: function(response, options){
				Ext.MessageBox.alert('系统提示','撤销之前上传的文件失败');
			}
		});
	
}
/**
 * 移除文件，移除数据源中的记录及对input数值进行处理
 */
custom.removeFiles = function(gridPanelId,recFileId,tfId){
	var tf = document.getElementById(tfId);
	var rmId = 0;
	var ds = Ext.getCmp(gridPanelId).getStore();
	for(var i=0;i<ds.getCount();i++){
		var rec =ds.getAt(i);
		if((gridPanelId+'-'+rec.get('fileId'))==recFileId){
			rmId = rec.get('fileId');
			ds.remove(rec);
		}
	}
	var tfArray = tf.value.split(';');
	tf.value = Array_RemoveItem(tfArray,rmId).join(';');
}


custom.UploadPanel = function(cfg){
	Ext.apply(this,cfg);
	/**
	 * 列表面板
	 */
	this.gridPanel = new Ext.grid.GridPanel({
		region:'center',
		border :true,
		store: new Ext.data.Store({
			//编号 、 名称 、 文件类型 、 文件大小 、 上传状态 、 上传百分比 返回上传成功的文件编号
			fields:['fileId','fileName','fileType','fileSize','state','percent','fid','fileUploadDate']
		}),
	    columns: [
	    	new Ext.grid.RowNumberer(),
	    	{header: '文件编号',id:'fid',dataIndex: 'fid', hidden:true},
	    	{header: '文件夹的目录名称',id:'folderName',dataIndex: 'folderName', hidden:true},
	        {header: '文件名',id:'ae_fileName', width: 100, sortable: true,dataIndex: 'fileName', menuDisabled:true},
	        {header: '类型', width: 70, sortable: true,dataIndex: 'fileType', menuDisabled:true},
	        {header: '大小', width: 100, sortable: true,dataIndex: 'fileSize', menuDisabled:true,renderer:this.formatFileSize},
	        {header: '上传时间', width: 130, sortable: true,dataIndex: 'fileUploadDate', menuDisabled:true},
	        {header: '进度', width: 150, sortable: true,dataIndex: 'percent', menuDisabled:true,renderer:this.formatProgressBar,scope:this},
	        {header: '状态', width: 70, sortable: true,dataIndex: 'state', menuDisabled:true,renderer:this.formatFileState,scope:this},
	        {header: '&nbsp;',width:40,dataIndex:'fileId', menuDisabled:true,renderer:this.formatDelBtn}  
	    ]
	    
	});
	//swfupload 设置
	this.setting = {
		//上传路径
		upload_url:this.uploadUrl,
		//flash插件地址
		flash_url:'/company/'+ROOT_PATH+'/js/extjs/plugin/fileupload/swfupload.swf',
		//flash_url:Ext.isEmpty(this.flashUrl)?'http://www.swfupload.org/swfupload.swf':this.flashUrl,
		//文件限制大小 默认为100M
		file_size_limit:Ext.isEmpty(this.fileSize) ? '5 MB' : this.fileSize,
		//表单提交对象的名称
		file_post_name:Ext.isEmpty(this.filePostName) ? 'myUpload' : this.filePostName,
		//文件类型提示
		file_types_description:Ext.isEmpty(this.fileTypesDesc)?'文件类型':this.fileTypesDesc,
		file_types : Ext.isEmpty(this.fileTypes) ? '*.*' : this.fileTypes,
		//上传限制个数 为0就不限制
		file_upload_limit:Ext.isEmpty(this.fileUploadLimit)?'0':this.fileUploadLimit,
		//
		use_query_string:true,
		//上传提交的参数
		post_params : Ext.isEmpty(this.postParams) ? {} : this.postParams,
		button_cursor : SWFUpload.CURSOR.HAND,
		button_window_mode : SWFUpload.WINDOW_MODE.TRANSPARENT,
		custom_settings : {//自定义参数
			scope_handler : this
		},
		//用户成功地选择了文件，在file_dialog_complete_handler事件之前触发。如果选择了多个文件，则触发多次
		file_queued_handler : this.onFileQueued,
		//当Flash控件成功加载后触发的事件处理函数
		swfupload_loaded_handler : function(){
		},
		file_dialog_start_handler : function(){},// 当文件选取对话框弹出前出发的事件处理函数
		file_dialog_complete_handler : this.onDiaogComplete,//当文件选取对话框关闭后触发的事件处理
		upload_start_handler : this.onUploadStart,// 开始上传文件前触发的事件处理函数
		upload_success_handler : this.onUploadSuccess,// 文件上传成功后触发的事件处理函数 
		swfupload_loaded_handler : function(){},// 当Flash控件成功加载后触发的事件处理函数  
		upload_progress_handler : this.uploadProgress,
		upload_complete_handler : this.onUploadComplete,
		upload_error_handler : this.onUploadError,
		file_queue_error_handler : this.onFileError
	};
	
	custom.UploadPanel.superclass.constructor.call(this,{
		tbar:[
			{
				text:'选择文件',
				iconCls:'add_fj_temp',
				ref:'../addBtn'
			}
			,'-'
			,{
				text:'开始上传',
				iconCls:'upload_fj_temp',
				ref:'../uploadBtn',
				handler:this.startUpload,
				scope:this
			}
			,'-'
			,{
				text:'停止上传',
				ref:'../stopBtn',
				iconCls:'stop_fj_temp',
				handler:this.stopUpload,
				scope:this,
				disabled:true
			}
			,'-'
			,{
				text:'删除所有',
				ref:'../deleteBtn',
				iconCls:'delete_fj_temp',
				handler:this.deleteAll,
				scope:this
			}
		],
		layout:'fit',
		items:[this.gridPanel],
		listeners:{
			//在渲染之前
			'afterrender':function(){
				var em = this.getTopToolbar().get(0).el.child('em');
				var placeHolderId = Ext.id();
				em.createChild({
					tag:'div',
					id:placeHolderId
				});
				this.swfupload = new SWFUpload(Ext.apply(this.setting,{
					button_width : em.getWidth(),
					button_height : em.getHeight(),
					button_placeholder_id :placeHolderId
				}));
				this.swfupload.uploadStopped = false;
				Ext.get(this.swfupload.movieName).setStyle({
					position : 'absolute',
					top : 0,
					left : -2
				});	
			},
			scope : this,
			delay : 100
		}
	});
}

Ext.extend(custom.UploadPanel,Ext.Panel,{
	//切换按钮
	toggleBtn:function(bl){
		this.addBtn.setDisabled(bl);
		this.uploadBtn.setDisabled(bl);
		this.deleteBtn.setDisabled(bl);
		this.stopBtn.setDisabled(!bl);
		this.gridPanel.getColumnModel().setHidden(6,bl);
	}
	,onUploadStart : function(file) {  
	   var post_params = this.settings.post_params;  
	   Ext.apply(post_params,{//处理中文参数问题
	   		//fileName : file.name,
	        fileName : encodeURIComponent(file.name)
	   });  
	   this.setPostParams(post_params);  
	}
	//开始上传
	,startUpload : function() {
		if (this.swfupload) {
			if (this.swfupload.getStats().files_queued > 0) {
				this.swfupload.uploadStopped = false;
				this.toggleBtn(true);
				this.swfupload.startUpload();
			}else{
				g_showTip("请选择要上传的文件！");
			}
		}
	}
	//格式化文件大小？
	,formatFileSize : function(_v, celmeta, record) {
		return Ext.util.Format.fileSize(_v);
	}
	//文件状态
	,formatFileState : function(n){//文件状态
		switch(n){
			case -1 : return '未上传';
			break;
			case -2 : return '正在上传';
			break;
			case -3 : return '<div style="color:red;">上传失败</div>';
			break;
			case -4 : return '上传成功';
			break;
			case -5 : return '取消上传';
			break;
			default: return n;
		}
	}
	,formatProgressBar : function(v){
		var progressBarTmp = this.getTplStr(v);
		return progressBarTmp;
	}
	//进度条模板
	,getTplStr : function(v){
		var bgColor = "orange";
	    var borderColor = "#008000";
		return String.format(
			'<div>'+
				'<div style="border:1px solid {0};height:10px;width:{1}px;margin:4px 0px 1px 0px;float:left;">'+		
					'<div style="float:left;background:{2};width:{3}%;height:10px;"><div></div></div>'+
				'</div>'+
			'<div style="text-align:center;float:right;width:40px;margin:3px 0px 1px 0px;height:10px;font-size:12px;">{3}%</div>'+			
		'</div>', borderColor,(90),bgColor, v);
	}
	//编号 、 名称 、 文件类型 、 文件大小 、 上传状态 、 上传百分比
	//fields:['fileId','fileName','fileType','fileSize','state','percent']
	,onUploadComplete : function(file) {
		var thiz = this.customSettings.scope_handler;
		if(file.filestatus==-4){
			var ds = thiz.gridPanel.getStore();
			for(var i=0;i<ds.getCount();i++){
				var record =ds.getAt(i);
				if(record.get('fileId')==file.id){
					record.set('percent', 100);
					if(record.get('state')!=-3){
						record.set('state', file.filestatus);
					}
					record.commit();
				}
			}
		}
		if (this.getStats().files_queued > 0 && this.uploadStopped == false) {
			this.startUpload();
		}else{			
			thiz.toggleBtn(false);
			thiz.linkBtnEvent();
		}		
	}
	//编号 、 名称 、 文件类型 、 文件大小 、 上传状态 、 上传百分比
	//fields:['fileId','fileName','fileType','fileSize','state','percent']
	,onFileQueued : function(file) {
		var thiz = this.customSettings.scope_handler;
		//获得列表面板
		var gridPanel = thiz.gridPanel;
		//获得面板数据源
		var fileStore = gridPanel.getStore();
		//判断文件选择个数是否超过
		if(fileStore.getCount() >= thiz.fileCount){
			Ext.MessageBox.alert('提示','您只允许上传'+thiz.fileUploadLimit+'个附件！');
			thiz.swfupload.cancelUpload(file.id,false);
			return;
		}	
		fileStore.add(new Ext.data.Record({
			fileId : file.id,
			fileName : file.name,
			fileSize : file.size,
			fileType : file.type,
			state : file.filestatus,
			percent : 0
		})); 
	}
	//编号 、 名称 、 文件类型 、 文件大小 、 上传状态 、 上传百分比
	//fields:['fileId','fileName','fileType','fileSize','state','percent']
	,onUploadSuccess : function(file, serverData) {
		var thiz = this.customSettings.scope_handler;
		var fileStore = thiz.gridPanel.getStore();
		var ds = Ext.util.JSON.decode(serverData);
		if (ds.success) {			
			for(var i=0;i<fileStore.getCount();i++){
				var rec =fileStore.getAt(i);
				if(rec.get('fileId')==file.id){
					
					rec.set('state', file.filestatus);
					rec.set('fid',ds.fid);
					//rec.set('fileUploadDate',(file.creationdate).dateFormat('Y-m-d H:i:s'));
					rec.set('fileUploadDate',new Date().dateFormat('Y-m-d H:i:s'));
					rec.commit();
				}
			}	
			custom.uploadFileId=custom.uploadFileId+","+ds.fid;
		}else{
			for(var i=0;i<fileStore.getCount();i++){
				var rec =fileStore.getAt(i);
				if(rec.get('fileId')==file.id){
					rec.set('percent', 0);
					rec.set('fid',-1);
					rec.set('state', -3);
					rec.commit();
				}
			}
		}
		thiz.linkBtnEvent();
	}
	,uploadProgress : function(file, bytesComplete, totalBytes){//处理进度条
		var thiz = this.customSettings.scope_handler;
		var percent = Math.ceil((bytesComplete / totalBytes) * 100);
		percent = percent == 100? 99 : percent;
       	var ds = thiz.gridPanel.getStore();
		for(var i=0;i<ds.getCount();i++){
			var record =ds.getAt(i);
			if(record.get('fileId')==file.id){
				record.set('percent', percent);
				record.set('state', file.filestatus);
				record.commit();
			}
		}
	}
	,onUploadError : function(file, errorCode, message) {
		var thiz = this.customSettings.scope_handler;
		thiz.linkBtnEvent();
		var ds = thiz.gridPanel.getStore();
		for(var i=0;i<ds.getCount();i++){
			var rec =ds.getAt(i);
			if(rec.get('fileId')==file.id){
				rec.set('percent', 0);
				rec.set('state', file.filestatus);
				rec.commit();
			}
		}
	}
	,onFileError: function(file,n){
		switch(n){
			case -100 : tip('待上传文件列表数量超限，不能选择！');
			break;
			case -110 : tip('文件太大，不能选择！');
			break;
			case -120 : tip('该文件大小为0，不能选择！');
			break;
			case -130 : tip('该文件类型不可以上传！');
			break;
		}
		function tip(msg){
			Ext.Msg.show({
				title : '提示',
				msg : msg,
				width : 350,
				icon : Ext.Msg.WARNING,
				buttons :Ext.Msg.OK
			});
		}
	}
	,onDiaogComplete : function(){
		var thiz = this.customSettings.scope_handler;
		thiz.linkBtnEvent();
	}
	,stopUpload : function() {
		if (this.swfupload) {
			this.swfupload.uploadStopped = true;
			this.swfupload.stopUpload();
		}
	}
	,deleteAll : function(){
		var ds = this.gridPanel.store;
		for(var i=0;i<ds.getCount();i++){
			var record =ds.getAt(i);
			var file_id = record.get('fid');
			this.swfupload.cancelUpload(file_id,false);
			var type = record.get('fileType');
			Ext.Ajax.request({
		     url:ROOT_PATH+'/file-delete?fileId='+file_id+'&fileType='+type+'&delType=delTempFile&folderName='+custom.folderName,
			success:function(response,options){
            custom.uploadFileId=custom.uploadFileId.replace(","+id,"");
			}
			,failure: function(response, options){
				Ext.MessageBox.alert('系统提示','撤销之前上传的文件失败');
			}
		});
	         
		}
		ds.removeAll();
		this.swfupload.uploadStopped = false;
		
	}
	,formatDelBtn : function(v,p,record){
		 return "<a href='#'  id='"+v+"' onclick='custom.deleteFileByIds([\""+record.get('fid')+"\"],[\""+record.get('fileType')+"\"]);' style='color:blue' class='link-btn' ext:qtip='移除并删除该文件'>移除</a>";
	}
	,linkBtnEvent : function(){
		Ext.select('a.link-btn',false,this.gridPanel.el.dom).on('click',function(o,e){
			var ds = this.gridPanel.store;
			for(var i=0;i<ds.getCount();i++){
				var rec =ds.getAt(i);
				if(rec.get('fileId')==e.id){
					ds.remove(rec);
				}
			}			
			this.swfupload.cancelUpload(e.id,false);
		},this);
	}
});



FilesUploadPanel = Ext.extend(Ext.Panel,{
	constructor:function(cfg){
		Ext.applyIf(this,cfg);
		this.initUIComponents(this);
		FilesUploadPanel.superclass.constructor.call(this, {
			layout:'border',
			items : [this.gridPanel]
		});
	},
	initUIComponents:function(thiz){
	
		custom.folderName = thiz.folderName;
		custom.tableName = thiz.tableName;
		if(thiz.uploadNum!=undefined){
		 custom.uploadNum = thiz.uploadNum
		}
		if(thiz.uploadFileSize!=undefined){
		 custom.uploadFileSize = thiz.uploadFileSize
		}
		if(thiz.tableId==''){//新增表单操作
		var uuid = new UUID().createUUID();
		uuid = uuid.substring(4,uuid.length);
		custom.tableId ="TEMP"+uuid;
		}else{
		custom.tableId =thiz.tableId;
		}
		// 列表显示控件定义
	 var tbar = new Ext.Toolbar({
				defaults:{
					height:25
				},
				items:[
					{
						text:'附件上传',
						iconCls:'fj_upload',
						handler:function(){
									
									
									
									var _fileUploadLimit = custom.uploadNum-thiz.gridPanel.getStore().getCount();
									if(_fileUploadLimit!=0){
										//获得模块信息
										var _upWin = Ext.getCmp('multi-file-upload-window');
										if(!_upWin){
											var up = new custom.UploadPanel({
														fileTypes : '*',
														border:false,
														fileSize:custom.uploadFileSize+' MB',
														fileUploadLimit:_fileUploadLimit,
														uploadUrl:ROOT_PATH+'/file-upload?folderName='+thiz.folderName
													});
											_upWin = new Ext.Window({
												id:'multi-file-upload-window',
												height:400,
												width:750,
												modal : true,
												iconCls:'fj_desc',
												title:"<span style='font-family:\"微软雅黑\"; font-size:12px;'>上传说明:<font color='#EE6A50'> 还允许上传"+custom.uploadNum+"个文件,其中单个上传文件大小为"+custom.uploadFileSize+"MB。</font></span>",
												layout:'fit',
												closable:false,
												items:[up],
												bbar:[
													'->'
													,{
														text:'确定',
														iconCls : "all_save",
														handler:function(){
															up.gridPanel.getStore().each(function(rec){
																if(rec.get('state')==-4){
																    var file_id = rec.get('fid');//附件ID
																    var type = rec.get('fileType');//附件类型
																    var fileName = rec.get('fileName');//文件名称
																    var fileSize = rec.get('fileSize');//文件大小
																   fileSize=Ext.util.Format.fileSize(fileSize);
															
															    $.ajax({ 
																type:"POST", 
																async:false, 
																url:ROOT_PATH+'/common/addUploadFile.htm?uploadType=database&fileId='+file_id+'&filePath='+custom.folderName+'&fileName='+fileName+'&fileType='+type+'&tableId='+custom.tableId+'&tableName='+custom.tableName+'&fileSize='+fileSize,
																success:function(data){
													            custom.uploadFileId=custom.uploadFileId.replace(","+id,"");
																   },
																   error:function(){
															     g_showTip("操作失败!");
															}
															});
																}
															Ext.getCmp("grid-upload-view").queryData(1,'');
															
															});
															_upWin.close();
														}
													}
													,'-'
													,{
														text:'关闭',
														iconCls : "all_cancel",
														handler:function(){
															var ds = up.gridPanel.getStore();
															for(var i=0;i<ds.getCount();i++){
																var record =ds.getAt(i);
																var file_id = record.get('fid');
																var type = record.get('fileType');
																	Ext.Ajax.request({
																url:ROOT_PATH+'/file-delete?fileId='+file_id+'&fileType='+type+'&delType=delTempFile&folderName='+custom.folderName,
																success:function(response,options){
													            custom.uploadFileId=custom.uploadFileId.replace(","+id,"");
																}
																,failure: function(response, options){
																	g_showTip('撤销之前上传的文件失败');
																}
															});
															}
															_upWin.close();
														}
													}
												]
											});
											_upWin.show();
										}else _upWin.show();
									}else{
										g_showTip('附件已满，禁止上传','系统提示');
									}
								
							
							
						}
					},'-',
					{
						text:'删除附件',
						iconCls:'delete_fj',
						handler:function(){
				     delGridData('grid-upload-view', 'id,name', ROOT_PATH+'/common/delAll.htm', 1,'', 300);
						}
					}
				]
			});
      var uploadGridC = [{
					header : "附件ID",
					dataIndex : "uaId",
					hidden : true
				}, {
					id : "uaName",
					header : "附件名称",
					dataIndex : "uaName"
				}, {
					id : "uaFileSize",
					header : "附件大小",
					dataIndex : "uaFileSize"
				}, {
					id : "uaType",
					header : "文件类型",
					dataIndex : "uaType"
				}];
      var uploadGridT = ['uaId', 'uaName', 'uaFileSize','uaType'];
	  thiz.gridPanel = new Com.panel.EvecomBaseGridPanel({
					queryUrl : ROOT_PATH+'/common/queryUploadFileByTable.htm?tableId='+custom.tableId+'&tableName='+custom.tableName,
					selType : 1,
					isAutoQuery:true,
					pkField : "uaId",
					region : 'center',
				    title:'附件列表',
				    iconCls:'fjGrid_menu',
					fieldsArr : uploadGridT,
					columnsArr : uploadGridC,
					tbar : tbar,
					viewConfig:{
						forceFit:true//自主决定每列的宽度，填充满表格
		   			},
				   id : "grid-upload-view"
				});
		
		/**
		 * 
		 */
		
		/**
		 * 
		 */
	
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
Ext.reg('filesuploadpanel',FilesUploadPanel);
