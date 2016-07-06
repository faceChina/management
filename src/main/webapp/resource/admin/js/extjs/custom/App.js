Ext.ns('App');
var frontUrl = 'http://127.0.0.1:8080/tycmsweb/';
//文章添加时调用前台生成页面
function changeFrmUrl(url){
	document.getElementById('frontUrlFrm').src = frontUrl+url;
}
/**
 * 文章拷贝时调用前台生成页面
 * @param {} rcnIds
 * @param {} rctIds
 */
function createCopyInfoHtml(rcnIds,rctIds){
	if(rcnIds!=null){
		var frmspan = document.getElementById('frontUrlFrmSpan');
		frmspan.innerHTML='';
		rcnIds = rcnIds.replace(";",",");
		var cnIds = rcnIds.split(',');
		var t = '';
		for(i=0;i<cnIds.length;i++){
			if(rctIds!=null){
				var ctIds = rctIds.split(',');
				for(j=0;j<ctIds.length;j++){
					t += '<iframe style="display:none" src="'+frontUrl+'front/info/creatHtml.do?cn_id='+cnIds[i]+'&ct_id='+ctIds[j]+'" ></iframe>';
				}
			}
		}
		frmspan.innerHTML = t;
	}
}
/**
 * 移动文章时调用前台生成页面
 * @param {} cnIds
 * @param {} rctIds
 */
function createMoveInfoHtml(cnId,ctIds){
	//先删除
	var frmspan = document.getElementById('frontUrlFrmSpan');
	frmspan.innerHTML='';
	var t = '';
	var ctid = ctIds.split(',');
	if(ctid!=null)for(i=0;i<ctid.length;i++){
		t += '<iframe style="display:none" src="'+frontUrl+'front/info/creatHtml.do?&cn_id='+cnId+'&ct_id='+ctid[i]+'"></iframe>'
	}
	frmspan.innerHTML = t;
}

function Array_AddItem(MyArr,item) {      
	for(var i = 0; i < MyArr.length; i++){      
		if(MyArr[i] == item)  return;   // 如果数组已含有此元素，则退出。
	}      
	MyArr.push (item);
	return MyArr;
}       
     
function Array_RemoveItem (MyArr,item){      
	var tmpArray = new Array();   // 新建一过度数组
	for(var i = 0; i < MyArr.length; i++){      
	// 遍历数组，如果数组里的某个元素不等于要移除的元 素，则添加到过度数组中。如果遍历完成后还没有此要移除的元素，此过渡数组与原数组
	// 一样。反之，则会比原数组少一个需要移除的元素，则达到移除的效果。
		if(MyArr[i] != item)  tmpArray.push(MyArr[i]);      
	}    
	// 最后将 过渡数组赋值给原数组。
	return tmpArray;
}

App.displayButtonByCheckbox = function(btnId,disabled){
	if(disabled)Ext.getCmp(btnId).enable();
	else Ext.getCmp(btnId).disable();
}

/**
 * 导航栏切换
 * @param {} componentName 	组件名称
 * @param {} componentId	组件ID
 * @param {} params			组件参数
 * @param {} precall		
 * @param {} callback		回调函数
 */
App.changeNavigation = function(componentName,componentId, params, precall, callback){
	if (precall != null) {
		precall.call(this);
	}
	var indexPangeCenter = Ext.getCmp('base-frame-center');
		indexPangeCenter.removeAll(true);
		//导入JS并展示它
		$ImportJs(componentName, function(view) {
				indexPangeCenter.add(view);
				indexPangeCenter.doLayout();
			}, params);
}
/**
 * 获得拼接后对象str
 * @param {} source		xxxx;xxxx;xxxx; || xxxx,xxxx,xxxx
 * @param {} separator  ; || ,
 * return xxxx;xxxx;xxxx || xxxx,xxxx,xxxx
 */
App.getObjectStr = function(source,separator){
	if(source!=null&&source.length>0){
		var length = source.length;
		var lastChar = source.substring(length-1,length);
		if(lastChar == separator){
			return source.substring(0,length-1);
		}else return source
	}
	
}
/**
 * FusionChart 图形控件方法
 * @param {} swfFileName
 * @param {} myChartId
 * @param {} width
 * @param {} height
 * @param {} dataXmlUrl
 * @param {} renderDiv
 */
App.myFusionChart = function(swfFileName,myChartId,width,height,dataXmlUrl,renderDiv){
	var _myChart = new FusionCharts(__ctxPath+"/app/common/plugins/FusionCharts/"+swfFileName,myChartId, width, height);    
	_myChart.setDataURL(__ctxPath+dataXmlUrl);    
	_myChart.render(renderDiv);  
}

/*
 * 通过组件ID转成组件名、
 * 如compId = "system-log-view"
 * 生成组件名为：SystemLogView
 * ZZB
 **/
App.getCmpNameById = function (compId) {
	var tmp = compId.split("-");
	var i;
	// 最终的组件名
	var finalCmpName = "";			
	var firstChar = "";
	var otherChar = "";
	if (tmp.length == 1) {
		// 没有下划线
		firstChar = tmp[0].substring(0, 1).toUpperCase();
		otherChar = tmp[0].substring(1).toLowerCase();
		finalCmpName = firstChar + otherChar;
	} else {
		for (i = 0; i < tmp.length; i++) {
			firstChar = tmp[i].substring(0, 1).toUpperCase();
			otherChar = tmp[i].substring(1).toLowerCase();
			finalCmpName = finalCmpName + firstChar + otherChar;
		}
	}
	return finalCmpName;
}
/**
 * 是否授权
 * @param {} resourceName 'system-log-view-btn-delete'
 * @return {Boolean}
 */
App.isAuth = function (resourceName) {
	if (curUser.allowAccessBtnResources.indexOf(',__ALL,') != -1) {
		return true;
	}
	if (curUser.allowAccessBtnResources.indexOf(','+resourceName+',') != -1) {
		return true;
	}
	return false;
}
/**
 * 是否有栏目权限
 * @param {} cnActionKey 栏目id-操作KEY
 * @return {Boolean}
 */
App.isCnAuth = function (cnActionKey) {
	if (curUser.admin) {
		return true;
	}
	if (curUserCnPower.cnBtns.indexOf(','+cnActionKey+',') != -1) {
		return true;
	}
	return false;
}
/**
 * 判断某字符串中是否存在另一个字符串
 * @param {} sourceStr	数据源字符串
 * @param {} findStr	要查找的字符串
 * @return {Boolean}	返回值为真假
 */
App.isHave = function (sourceStr,findStr) {
	if (sourceStr.indexOf(findStr) != -1) {
		return true;
	}
	return false;
}

/**
 * 获得简单数据源
 * 									是否必须			介绍
 * @param {} _sdUrl					Y				数据源链接地址	'/sys/sysLog/display.do'
 * @param {} _sdJrFields			Y				获得的数据列数组	['logId','logUserAccount','logUserName','logContent','logResult','logDate']
 * @param {} _isAutoLoad			N/true false    是否自动加载
 * @param {} _sdBpStart				N/0				起始记录行数，默认为0
 * @param {} _sdBpLimit				N/15			每页显示数，默认为15
 * @param {} _sdJrRoot				N/root			默认返回数组对象名称
 * @param {} _sdJrTotalProperty		N/totalCount	默认返回记录数
 * @return {}
 */
App.getSimpleStoreData = function(_sdUrl,_sdJrFields,_isAutoLoad,_sdBpStart,_sdBpLimit,_sdJrRoot,_sdJrTotalProperty){
	return new Ext.data.Store({
		autoLoad:_isAutoLoad==null?false:_isAutoLoad,
		proxy:new Ext.data.HttpProxy({
			url:ROOT_PATH+_sdUrl
		}),
		baseParams:{
			start:_sdBpStart==null||_sdBpStart==''||_sdBpStart=='undefined'?0:_sdBpStart,
			limit:_sdBpLimit==null||_sdBpLimit==''||_sdBpLimit=='undefined'?15:_sdBpLimit
		},
		reader:new Ext.data.JsonReader({
			root : _sdJrRoot==null||_sdJrRoot==''||_sdJrRoot=='undefined'?'result':_sdJrRoot,
			totalProperty : _sdJrTotalProperty==null||_sdJrTotalProperty==''||_sdJrTotalProperty=='undefined'?'totalCount':_sdJrTotalProperty,
			fields : _sdJrFields,
			successProperty: 'success'
		}),
		listeners: { 
//                load : function(store, records, options) { 
//                }, 
			exception : function(proxy, type, action, options, res, arg) {
				var result = Ext.util.JSON.decode(res.responseText);
           		Ext.Msg.show({ 
                	title: '系统提示', 
                	msg: '获取数据失败！', 
                	buttons: Ext.Msg.OK 
          		}); 
        	} 
     	} 
	});
}

/**
 * 获取一个复选框的值
 * @param {} gridPanel	要获取复选框值的列表面板对象
 * @return {String}
 */
App.getCheckBoxValue = function(gridPanel,valueNames){
	var checkBoxValue = App.getCheckBoxValues('',gridPanel,valueNames);
	if(checkBoxValue==''){
		Evecom.tips.msg("提示",'请选择您要操作的记录');
		return '';
	}else if(checkBoxValue.split(',').length>1){
		Evecom.tips.msg("提示",'您只能对一条记录进行操作');
		return '';
	}else{
		return checkBoxValue;
	}
}
/**
 * 根据值回填，
 * @param {} gridPanel	表单面板
 * @param {} values		值集合 xxx;xxx;xxx;xxx
 * @param {} verifyName 要比较的列
 */
App.setGridPanelRecordChecked = function(gridPanel,values,verifyName){
	var _val = new Array();
	var _selRows = new Array();
	var selects = gridPanel.getSelectionModel();
	if(values!=null)_val = values.split(';');
	for(var i=0,j=0,size=_val.length;i<size;i++){
		var _r = 0;
		gridPanel.getStore().each(function(record) {
			if(_val[i]==record.data[verifyName]){
				_selRows[j]=_r;
				j++
			}
			_r++;
		});
	}
	selects.selectRows(_selRows);
}

/**
 * 获得多选的自定义值
 * @param {} defaultValue		返回的默认值，也可以当作是错误值
 * @param {} gridPanel			列表面板对象
 * @param {} valueNames			要获得哪些值的名称 'id,name'
 * @return {} xxxx,xxxx,xxx
 */
App.getCheckBoxValues = function(defaultValue,gridPanel,valueNames){
	var selects = gridPanel.getSelectionModel().getSelections();
	var temp = '';
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
	return temp==''?defaultValue:temp;
}
/**
 * 通过树进行切换某个容器下的组件对象
 * @param {} frameId		容器编号
 * @param {} cmpObject		组件对象
 */
App.changeObjectCmpByTree = function(frameId,cmpObject){
	//通过容器ID获得容器
	var framePanel = Ext.getCmp(frameId);
	//移除掉容器中已有的控件
	framePanel.removeAll(true);
	//通过自定义的组件名称创建该组件并添加到容器中
	framePanel.add(cmpObject);
	framePanel.doLayout();
}
/**
 * 通过树节点创建自定义面板
 * @param {} node
 * @param {} frameId
 */
App.changeCustomPanelByTree = function(node,frameId){
	//通过容器ID获得容器
	var framePanel = Ext.getCmp(frameId);
	//移除掉容器中已有的控件
	framePanel.removeAll(true);
	//实例化操作
	//node.attributes.cmpParams 格式 cmpParams = 'title:"这个是标题",id:"cmp-id"...'
	if(node.attributes.cmpParams==null||node.attributes.cmpParams.length<1)node.attributes.cmpParams='';
	//node.attributes.cmpName 格式 cmpName = 'SystemResourceCenterPanel'
	var str = 'new '+node.attributes.cmpName+'({'+node.attributes.cmpParams+'});';
	//
	var view = eval(str);
	framePanel.add(view);
	framePanel.doLayout();
}


/**
 * 切换tab面板通用方法
 * @param {} node			树节点
 * @param {} tabFrameId		tab框架编号
 * @param {} componentName	要实例化面板的名称，这里的面板都是自定义的面板
 * @param {} params			实例化面板的初始参数，值可为 null
 * @param {} tabGridId		要实例化面板下的grid面板编号，用于刷新该面板，若为null就说明没有grid面板，也就不刷新
 */
App.changeTabByTree = function(node,tabFrameId,componentName,params,tabGridId){
	//获得tab框架
	var tabFrame = Ext.getCmp(tabFrameId);
	//根据id获得子面板
	var tab = tabFrame.getItem(node.id);
	//判断是否存在
	if(!tab){
		if(params==null||params.length<1)params='';
		var str = 'new '+componentName+'({'+params+'});';
		var view = eval(str);
		tab = tabFrame.add(view);
	}
	//刷新面板数据
	if(tabGridId!=null){
		var tabGrid = tab.getComponent(tabGridId);
		if(tabGrid){tabGrid.getStore().load();}
	}
	tabFrame.activate(tab);
}


App.del = function(delUrl,ids,objectNames,gridStoreArray,treeStoreArray){
	//分割对象名称
	var _strNames = '';
	for(i=0,size=objectNames.length;i<size;i++){
		_strNames+='<font color="blue">'+objectNames[i]+'</font>';
		if((i+1)<size){
			_strNames+=',';
		}
	}
	Ext.Msg.confirm("提示", "您确定要删除以下记录吗？<br /><div style:'line-height:24px'>"+_strNames+"</div>", function(msg){
		if(msg=='yes'){
			Ext.Ajax.request({
				url:__ctxPath+delUrl,
				method:'post',
				params : {
					ids : ids,
					objectNames:objectNames
				},
				success : function(response,result) {
					Evecom.tips.msg("提示", "成功删除所选记录！");
					var data = Ext.decode(response.responseText);
					if(!Ext.isEmpty(data.ids)){
						var frmspan = document.getElementById('frontUrlFrmSpan');
						frmspan.innerHTML='';
						var idArray = (data.ids).split(',');
						var t = '<iframe style="display:none" src="'+frontUrl+'front/info/creatHtml.do?1=1';
						var _id = '';
						for(i =0;i<idArray.length;i++){
							_id += '&ids='+idArray[i];
						}
						t+=_id;
						t+='&cn_id='+data.cnId+'"></iframe>';
						frmspan.innerHTML = t;
					}
					
					if(gridStoreArray!=null&&gridStoreArray.length>0){
						for(var i=0,size=gridStoreArray.length;i<size;i++){
							gridStoreArray[i].reload();
						}
					}
					if(treeStoreArray!=null&&treeStoreArray.length>0){
						for(var i=0,size=treeStoreArray.length;i<size;i++){
							treeStoreArray[i].root.reload();
						}
					}
				},
				failure : function() {
					Evecom.tips.msg("提示", "删除失败");
				}
			});
		}
	});
}
  /**
	*在线访谈-直播间专用
	*
    */
App.updateStatus=function(updateUrl,ids,objectNames,gridStoreArray,treeStoreArray){
	//分割对象名称
	var _strNames = '';
	for(i=0,size=objectNames.length;i<size;i++){
		_strNames+='<font color="blue">'+objectNames[i]+'</font>';
		if((i+1)<size){
			_strNames+=',';
		}
	}
	Ext.Msg.confirm("提示", "您确定提问吗？<br /><div style:'line-height:24px'>"+_strNames+"</div>", function(msg){
		if(msg=='yes'){
			Ext.Ajax.request({
				url:__ctxPath+updateUrl,
				method:'post',
				params : {
					ids : ids,
					objectNames:objectNames
				},
				success : function() {
					Evecom.tips.msg("提示", "提问成功！");
					if(gridStoreArray!=null&&gridStoreArray.length>0){
						for(var i=0,size=gridStoreArray.length;i<size;i++){
							gridStoreArray[i].reload();
						}
					}
					if(treeStoreArray!=null&&treeStoreArray.length>0){
						for(var i=0,size=treeStoreArray.length;i<size;i++){
							treeStoreArray[i].root.reload();
						}
					}
				},
				failure : function() {
					Evecom.tips.msg("提示", "提问失败");
				}
			});
		}
	});
}
/**
 * 获得当前 激活的面板
 * @param {} tabFrameId tab框架Id
 * @return {}
 */
App.getTabPanelActiveTab = function(tabFrameId){
	var tabFrame = Ext.getCmp(tabFrameId);
	return tabFrame.getActiveTab();
}

/**
 * 选择所有父节点  在checkchange中调用
 * @param {} node
 * @param {} checked
 */
App.treeSelChild = function treeSelChild(node, checked) {
	checked ? node.expand() : null;   
/*//	checked ? node.expand() : node.collapse();   
	if (node.hasChildNodes()) {   
		node.eachChild(function(child) {   
			child.attributes.checked = checked;   
			var cb = child.ui.checkbox;   
			if (cb) cb.checked = checked;   
			treeSelChild(child, checked);   
		});   
	} */  
	node.cascade(function(n){
		if(checked)n.expand(); 
		//else n.collapse();
		n.attributes.checked = checked;
		n.ui.checkbox.checked = checked;
		return true;
	});
}   
/**
 * 选择所有子节点	在checkchange中调用
 * @param {} node
 * @param {} checked
 */
App.treeSelParent = function treeSelParent(node, checked) {   
	var parentNode = node.parentNode;   
	if (parentNode != undefined) {
		if(parentNode.attributes.entityParentId!=undefined){
			if(parentNode.attributes.entityParentId!=-1){
				if (checked) {   
					parentNode.attributes.checked = checked;   
					var cb = parentNode.ui.checkbox;   
					if (cb) cb.checked = checked;   
					treeSelParent(parentNode, checked);   
				}
				/*else{
					var isChecked=false;
					parentNode.eachChild(function(child) {   
						if(child.attributes.checked ==true){
							isChecked=true
							return true;
						};   
					});
					if(!isChecked){
						parentNode.attributes.checked = false;   
						var cb = parentNode.ui.checkbox;   
						if (cb) cb.checked = false;   
						treeSelParent(parentNode, false);  
					}
				}*/
			}else{
				parentNode.attributes.checked = true;   
					var cb = parentNode.ui.checkbox;   
					if (cb) cb.checked = true;  
			}
		}
			
	}	
} 
