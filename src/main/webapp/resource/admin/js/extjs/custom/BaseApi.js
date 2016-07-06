/*
 * 提示框
 */
function g_showTip(msg,title,iconImg,width,heigth,fn,scope){
	var vwidth=260;
	var vmsg="操作成功";
	var vtitle="温馨提示";
	if(typeof msg!="undefined")
		vmsg=msg;
	if(typeof title!="undefined")
		vtitle=title;
	if(typeof width!="undefined")
		vwidth=width;
	if(typeof width!="undefined")
		vwidth=width;
	var vheigth=180;
	if(typeof heigth!="undefined")
		vheigth=heigth;
	viconImg=Ext.MessageBox.INFO;
	if(typeof iconImg!="undefined")
		viconImg=iconImg;
	Ext.MessageBox.show({
        title : vtitle,
        msg : vmsg,
        buttons: Ext.MessageBox.OK,
        fn: fn,
        scope : scope,
        width:vwidth,
        heigth:vheigth,
        icon:viconImg,
        closable:true
    }); 
	delete vwidth;
	delete vheigth;

}
function changeObjectCmpByTree(frameId,cmpObject){
	var framePanel = Ext.getCmp(frameId);
	framePanel.removeAll(true);
	framePanel.add(cmpObject);
	framePanel.doLayout();
}
/*
     所有页面用户的权限按钮
 */
function addAuthorToolBar(idButtonArray){
	if(is_view_author){
   for(var i=0;i<idButtonArray.length;i++){
   	var value = idButtonArray[i].split(",");
    var tbarId =value[0];
    var tbarCode = value[1];
    if(user_buttonCode_session.indexOf(tbarCode)==-1){
    	if(Ext.getCmp(tbarId)!=undefined){
     	//Ext.getCmp(tbarId).setDisabled(true);
    	}
    }
	}
	}
}
/*
     grid 编辑栏按钮全选 buttonArray[0] 图片 buttonArray[1] title buttonArray[2]按钮执行方法 buttonArray[3]按钮权限值 为no时 不用权限
 */
function dataGridButtonAuthor(buttonArray){
	var str = "";
	for(var i=0;i<buttonArray.length;i++){
			var value = buttonArray[i].split("#");
    		var imgName = value[0];
   			var title = value[1];
   			var jsMethod = value[2];
   			var buttonCode = value[3];
   		    var buttonHtmlValue ="<img style='cursor: pointer;' src='"+EDIT_BUTTON_PATH+"/"+imgName+"' title='"+title+"' onclick='"+jsMethod+"'>&nbsp;&nbsp";
//			if(!is_view_author||buttonCode=='no'||user_buttonCode_session.indexOf(buttonCode)!=-1){
   		    str+=buttonHtmlValue;
//   		    }
	}
	return str;
	}


/*
主页面布局所用
 */
function g_clearIframe(frmId){
	if(window.frames[frmId]!=null){	
		try{
			//window.frames[frmId].destroyDoc();   	
	    }catch(e){}
		try{
	    	window.frames[frmId].document.src="about:blank";	    	
	    }catch(e){}
	    
	    try{
	    	window.frames[frmId].document.write("");
	    }catch(e){}
	   
    }
}
	/*
 * formpanel或是tree树删除
 * frameId 删除
 * treeId 更新树、
 * comfirmmsg为警告提示语
 * wmsg 操作结果提示语
 * windowWidth 提示框宽度 
 */
function delObjectById(url,frameId,treeId,comfirmmsg,wmsg,windowWidth,reroladUrl){
		Ext.MessageBox.minWidth = windowWidth;
	Ext.Msg.confirm("警告", comfirmmsg, function(msg){
		if(msg=='yes'){
		      var vLoadMask = new Ext.LoadMask(Ext.getBody(),{msg: '正在'+wmsg+'，请稍后...'});
               vLoadMask.show(); //显示遮罩窗口
							  Ext.Ajax.request({           
     						  url: url, 
						     success: function(response, options) { 
						     	  vLoadMask.hide();
						           g_showTip(''+wmsg+'成功');
						           if(frameId!=''){
						           var framePanel = Ext.getCmp(frameId);
							        framePanel.destroy();   
						           }
						            if(treeId!=''||url!=undefined){
						            	  var treePanel = Ext.getCmp(treeId);
						            	if(reroladUrl!=undefined){
						              var loader = new Ext.tree.TreeLoader({dataUrl:reroladUrl});
						            		loader.load(treePanel.root);
      										treePanel.getRootNode().expand(true,true);
						            	}else{
						            		 treePanel.root.reload();  
						            	}
						         
							      
						           }
},
				failure : function(response, options) {
					 g_showTip(''+wmsg+'失败');			
					}
       
   });
		}
   })
}
function getCheckBoxValues(gridId,valueNames){
	var gridPanel = Ext.getCmp(gridId);
	var selects = gridPanel.getSelectionModel().getSelections();
	var temp ='';
	if(selects!=null&&selects.length>0){
		var selectsSize = selects.length;
		var values = valueNames.split(',');
		var valuesSize = values.length;
		for(i=0;i<selectsSize;i++){
			for(j=0;j<valuesSize;j++){
				temp += selects[i].data[values[j]];
				if((j+1)<valuesSize)temp+='-';
			}
			if((i+1)<selectsSize)temp+=',';
		}
	}
	return temp;
}

		/*
 * grid批量新增框 公用方法
 * gridId grid组件ID
 * idAndNames  删除的id和名称的对象字段名
 * delUrl 删除地址
 * submitType 提交方式 /*0 整个form提交  params传form的id 1为具体参数 详细见BaseGrid.js
 * params 参数 例{"user.code":invCd1,invNm1:invNm1}
 * windowWidth 提示框宽度 
 */
	function addGridData(gridId,idAndNames,addUrl,submitType,params,paramGridId){
		var selectedValue='';
	if(paramGridId!=''){
			selectedValue = getCheckBoxValues(paramGridId,idAndNames);
	}else{
		selectedValue = getCheckBoxValues(gridId,idAndNames);
	}
	if(selectedValue!=''){
	   return addDataByValue(addUrl,selectedValue,gridId,submitType,params);
	}else{
	g_showTip('请选择要新增的记录!');
	return false;
	}
	}
	Date.prototype.pattern=function(fmt) {        
    var o = {        
    "M+" : this.getMonth()+1, //月份        
    "d+" : this.getDate(), //日        
    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时        
    "H+" : this.getHours(), //小时        
    "m+" : this.getMinutes(), //分        
    "s+" : this.getSeconds(), //秒        
    "q+" : Math.floor((this.getMonth()+3)/3), //季度        
    "S" : this.getMilliseconds() //毫秒        
    };        
    var week = {        
    "0" : "\u65e5",        
    "1" : "\u4e00",        
    "2" : "\u4e8c",        
    "3" : "\u4e09",        
    "4" : "\u56db",        
    "5" : "\u4e94",        
    "6" : "\u516d"       
    };        
    if(/(y+)/.test(fmt)){        
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));        
    }        
    if(/(E+)/.test(fmt)){        
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "\u661f\u671f" : "\u5468") : "")+week[this.getDay()+""]);        
    }        
    for(var k in o){        
        if(new RegExp("("+ k +")").test(fmt)){        
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));        
        }        
    }        
    return fmt;        
}  

function addDataByValue(addUrl,resultValues,reloadGridId,submitType,params){
		var value = resultValues.split(',');
		var idArray = new Array();
		for(i=0;i<value.length;i++){
			var temp = value[i].split('-');
			idArray[i]=temp[0];
		}
		vparams=params;
		vparams.ids= idArray;
		Ext.Ajax.request({
				url:addUrl,
				method:'post',
				params :vparams,
				success : function() {
					g_showTip("保存成功");
					if(reloadGridId!=null){
					Ext.getCmp(reloadGridId).queryData(submitType,params);
					}
				},
				failure : function() {
					g_showTip("保存失败!");
				}
			});
	return false;
}

function delDataByValue(delUrl,resultValues,reloadGridId,submitType,params,windowWidth,methodaudit){
		var value = resultValues.split(',');
		var idArray = new Array();
		var names=new Array();
		for(i=0;i<value.length;i++){
			var temp = value[i].split('-');
			idArray[i]=temp[0];
			names[i]=temp[1];
		}
	var msgValue ="确定要删除吗?";
	var _strNames = '';
	if(names[0]!='undefined'){
	for(i=0,size=names.length;i<size;i++){
		_strNames+='<font color="blue">'+names[i]+'</font>';
		if((i+1)<size){
			_strNames+=',';
		}
	}
	msgValue="确定要删除以下记录吗:<br /><div style:'line-height:24px'>"+_strNames+"</div>";
	}
	Ext.MessageBox.minWidth = windowWidth;

	Ext.Msg.confirm("温馨提示", msgValue, function(msg){
		if(msg=='yes'){
			Ext.Ajax.request({
				url:delUrl,
				method:'post',
				params : {
					ids : idArray
				},
				success : function() {
					g_showTip("删除成功");
					if(reloadGridId!=null){
					Ext.getCmp(reloadGridId).queryData(submitType,params);
					}
					if(methodaudit!=undefined){
					methodaudit();
					}
				},
				failure : function() {
					g_showTip("删除失败!");
				}
			});
		}
	});
		return false;
		}
		/*
 * grid删除框 公用方法
 * gridId grid组件ID
 * idAndNames  删除的id和名称的对象字段名
 * delUrl 删除地址
 * submitType 提交方式 /*0 整个form提交  params传form的id 1为具体参数 详细见BaseGrid.js
 * params 参数 例{"user.code":invCd1,invNm1:invNm1}
 * windowWidth 提示框宽度 
 */
	function delGridData(gridId,idAndNames,delUrl,submitType,params,windowWidth,methodaudit){
	var selectedValue = getCheckBoxValues(gridId,idAndNames);
	if(selectedValue!=''){
	   return delDataByValue(delUrl,selectedValue,gridId,submitType,params,windowWidth,methodaudit);
	}else{
	g_showTip('请选择要删除的记录!');
	return false;
	}
	}
	Date.prototype.pattern=function(fmt) {        
    var o = {        
    "M+" : this.getMonth()+1, //月份        
    "d+" : this.getDate(), //日        
    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时        
    "H+" : this.getHours(), //小时        
    "m+" : this.getMinutes(), //分        
    "s+" : this.getSeconds(), //秒        
    "q+" : Math.floor((this.getMonth()+3)/3), //季度        
    "S" : this.getMilliseconds() //毫秒        
    };        
    var week = {        
    "0" : "\u65e5",        
    "1" : "\u4e00",        
    "2" : "\u4e8c",        
    "3" : "\u4e09",        
    "4" : "\u56db",        
    "5" : "\u4e94",        
    "6" : "\u516d"       
    };        
    if(/(y+)/.test(fmt)){        
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));        
    }        
    if(/(E+)/.test(fmt)){        
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "\u661f\u671f" : "\u5468") : "")+week[this.getDay()+""]);        
    }        
    for(var k in o){        
        if(new RegExp("("+ k +")").test(fmt)){        
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));        
        }        
    }        
    return fmt;        
}  
function getNewDate(){
	
  return  new Date().pattern("yyyy-MM-dd HH:mm:ss");   
}
function randomIconNum(u,d){
     ++d;
  return parseInt(Math.random()*(u-d)+d);
 } 