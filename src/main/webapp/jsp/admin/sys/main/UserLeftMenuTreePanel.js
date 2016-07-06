UserLeftMenuTreePanel = Ext.extend(Ext.Panel, {
	loadUrl:ROOT_PATH+"/admin/index/left.htm",
	id:"treePanel-userMenu-view",
	rootId:0,
	border:false,
	frame:false,
	initComponent: function(){
		this.width=200;
		this.height=320;
		this.layout="accordion";
		this.collapsible=false;   
		this.collapseMode='mini'; 
		this.collapseFirst=true; 
		this.split=true;
		this.layoutConfig= {
		    animate: true
		};
		if(!this.modulePid){
		this.modulePid = 0;
		}
		var menuNodeList=null;
		$.ajax({ 
		type:"POST", 
		async:false, 
		url:this.loadUrl+"?moduleId="+this.modulePid,
			success:function(data){
			 menuNodeList = JSON.parse(data);
			}
		});
		if(menuNodeList!=null){
			var treePnlArr=new Array();	
			for(var i=0;i<menuNodeList.length;i++){
				var parentNd;
					parentNd = new Ext.tree.TreeNode({
				        text: menuNodeList[i].text,
				        draggable:false,
				        border : false,
				        id:""+menuNodeList[i].id
				    });
					var treePnl=new Ext.tree.TreePanel({
						border:false,
						frame:false,
						width:100,
						title:menuNodeList[i].text,
						border : false,
						rootVisible:false,
						root:parentNd,
						iconCls:menuNodeList[i].iconCls
				   });
				   treePnlArr[treePnlArr.length]=treePnl;
					if(menuNodeList[i].childList.length > 0){
						var cArray = menuNodeList[i].childList;
						for(var k=0;k<cArray.length;k++){
							parentNd.appendChild(new Ext.tree.TreeNode({
								id:""+cArray[k].id,
								text:cArray[k].text,
					            url:pageSysUrl+cArray[k].pageUrl,
					            iconCls:cArray[k].iconCls,
								listeners:{
									'click':function(node){
									    menuNdClk(node);
									}
								}
							}));
						}
					}
			}
			if(menuNodeList.length>0){
			   this.items=treePnlArr;
			}
}
	
		UserLeftMenuTreePanel.superclass.initComponent.call(this);
   },onDestroy:function(){
	   if(this.body){
           var c = this.body;
           c.removeAllListeners();
           c.update("");
       }  
	   Ext.destroy(this.items);
	   delete this.width;
	   delete this.height;
	   delete this.layout;
	   delete this.collapsible;
	   delete this.collapseMode;
	   delete this.collapseFirst;
	   delete this.split;
		
	   delete this.layoutConfig;
	   delete this.items;
	   
       UserLeftMenuTreePanel.superclass.onDestroy.call(this);
   }
   
});
Ext.reg('userLeftMenuTreePanel', UserLeftMenuTreePanel);