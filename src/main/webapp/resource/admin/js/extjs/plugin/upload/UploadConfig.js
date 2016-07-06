getRootPath=function (){
	var strFullPath=window.document.location.href;
	var strPath=window.document.location.pathname;
	var pos=strFullPath.indexOf(strPath);
	var prePath=strFullPath.substring(0,pos);
	var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1);
	return(prePath+postPath);
}
showUploadPanel = function(config) {
	if(!config){
		config = {};
	}
	var uploadPanel = new UploadPanel({
			initUrl : config.initUrl || getRootPath()+'/file-init',
			initParams : config.initParams || {ids : null},
			uploadUrl : config.uploadUrl || getRootPath()+'/file-upload?path='+config.path+'&tableName='+config.tableName+'&tableId='+config.tableId,
			deleteUrl : config.deleteUrl || getRootPath()+'/file-delete',  //删除的时候需要这个。
			downloadUrl : config.downloadUrl || getRootPath()+'/file-download',//下载时需要这个。
			filePostName : config.filePostName || 'myUpload', // 这里很重要，默认值为'fileData',这里匹配action中的setMyUpload属性
			
			fileCount : config.fileCount || 5,
			fileSize : config.fileSize || '20 MB',
			flashUrl : '/company/resource/admin/js/extjs/plugin/upload/swfupload.swf',
			height : 350,
			border : true,
			fileTypes : config.fileTypes || '*.txt;*.xls;*.doc;*.jpg;*.gif;*.png;*.rar;*.zip;*.ceb;*.pdf;*.vsd', // 在这里限制文件类型:'*.jpg,*.png,*.gif'
			fileTypesDescription : config.fileTypesDescription || '支持文件类型'//,
	});

	var win = new Ext.Window({
		title : config.winTitle || '多文件上传',
		width : config.width || 565,
		height : config.height || 415,
		modal : true,
		closable: config.closable || false,
		resizable : config.resizable || true,
		draggable : config.draggable || true,
		layout : 'fit',
		closeAction : 'hide',
		items : [uploadPanel],
		listeners : {
			hide : {
				fn : function () { //关闭时回填 的函数
					var isCallBack = config.isCallBack;
					var uploadType = config.uploadType;
					var isCallBackFunc=config.isCallBackFunction;
					var backFun=config.backFunction;
					if(isCallBack){
						//关闭时首先先给文本框或面板 的ID和值清空掉，待上传完后重新把值附进去
						/*if(config.putObject.xtype=="textfield"){
							var putObjectId=config.putObject.id;
							document.getElementById(putObjectId).value="";
							config.ids.setValue("");
						}else{
							var putObjectId=config.putObject.id;
							config.putObject.body.update("");
							config.ids.setValue("");
						}*/
						config.ids.setValue("");
						
						//上传完后去取信息
						var arrFileInfo = uploadPanel.getFileInfos();
						//附件是照片类型上传后关闭的事件
						if(uploadType=="image"){
							if(arrFileInfo.length>0){
								var fileInfo = arrFileInfo[0].split(",");
								alert( ROOT_PATH+ "/attachFiles/"+ fileInfo[2] );
								config.putObject.body.update('<img style="border:0;width:100%; height:100%;"  src="'
											+ ROOT_PATH
											+ "/attachFiles/"
											+ fileInfo[2] 
											+'" border="0"/>');
								var fileIds="";
								for(var i=0;i<arrFileInfo.length;i++){
									var fileInfo = arrFileInfo[i].split(",");
									fileIds+=fileInfo[0]+",";
								}
								if(config.ids!=null){//此方法待测验完善
									fileIds=(fileIds!="")?fileIds.substring(0,fileIds.length-1):"";
									config.ids.setValue(fileIds);
								}

							}
						}
						
						//附件是其它类型的关闭事件
						if(uploadType=="other"){
							if(arrFileInfo.length>0){
								var fileIds = '';var fileVal = '';var filePaths = '';
								for(var i=0;i<arrFileInfo.length;i++){
									var fileInfo = arrFileInfo[i].split(",");
									//累加PATH 
									filePaths += ","+"/attachFiles/"+ fileInfo[2];
									
									//累加ID
									fileIds += ","+fileInfo[0];
									//累加 要显示的东西
									var file_pic_id=config.ids.id;
									var downloadPath=config.downloadUrl || getRootPath()+"/file-download";
									var deletePath=config.deleteUrl || getRootPath()+'/file-delete';
									/*Ext.DomHelper.append(config.putObject.body,
									"<div id='fileDivId"+fileInfo[0]+"'><a href='"+downloadPath+"?fileId="+fileInfo[0]+"' onclick='' target='_blank'>"
										+ fileInfo[1]
										+ "</a> <img class='img-delete' src='"
										+ ROOT_PATH
										+ "/images/system/delete.gif' onclick=deleteFile('"+fileInfo[0]+"','"+file_pic_id+"','"+deletePath+"')></img>&nbsp;|&nbsp;</div>");
									*/
									/*if(config.putObject.xtype=="textfield"){
										fileVal += fileInfo[1]+" | ";
									}else{
										var file_pic_id=config.ids.id;
										var downloadPath=config.downloadUrl || getRootPath()+"/file-download";
										var deletePath=config.deleteUrl || getRootPath()+'/file-delete';
										Ext.DomHelper.append(config.putObject.body,
										"<div id='fileDivId"+fileInfo[0]+"'><a href='"+downloadPath+"?fileId="+fileInfo[0]+"' onclick='' target='_blank'>"
											+ fileInfo[1]
											+ "</a> <img class='img-delete' src='"
											+ ROOT_PATH
											+ "/images/system/delete.gif' onclick=deleteFile('"+fileInfo[0]+"','"+file_pic_id+"','"+deletePath+"')></img>&nbsp;|&nbsp;</div>");
									}*/
								}
								
								//下面累加ID
								if(config.ids!=undefined){
									if (config.ids.getValue() != "") {
										config.ids.setValue(config.ids.getValue()+fileIds);
									}else{
										config.ids.setValue(fileIds.replace(',',''));
									}
								}
								
								//下面累加PATH ,config.filePaht为调用时传的属性和ids一样（上面for里面的fileInfo[2]是路径,0：Id，1：Name，2：Path）
								if(config.filePath!=undefined){
									if (config.filePath.getValue() != "") {
										config.filePath.setValue(config.filePath.getValue() + filePaths);
								    }else{
										config.filePath.setValue(filePaths.replace(',',''));
									}
								}
								//是文本框的情况下把name设置进去 
								/*
								if(config.putObject.xtype=="textfield"){
//									if(config.putObject.getValue() != ""){//这里为文本框累加显示内容
//										alert(config.putObject.getValue()+fileVal);
//										config.putObject.setValue(config.putObject.getValue()+ fileVal);
//									}else{
										config.putObject.setValue(fileVal.substring(0,fileVal.length-2));
//									}
								}*/
							}		
						}
					}
				}
			}
		},
		tbar : ['<font color=\"red\">支持文件类型：'+uploadPanel.fileTypes+'</font> '],
		bbar : ['<font color=\"red\">文件大小限制：'+uploadPanel.fileSize+' / 文件个数限制：'+uploadPanel.fileCount+'</font>',
			{
			iconCls : 'btn-cancel',
			text : "关闭",
			handler : function() {
				win.hide();
				if(config.backFunction){
					config.backFunction.call(this);
				}
			}
		}]
		//buttons : []
	});
	win.show();
};

