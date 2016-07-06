/*
        	var tree = new  Com.panel.EvecomTreePanel({
   id: 'lefttree',
   title: 'evecomtree',
   border: false,
   rootId:'f6cef0af-52d2-4752-8a37-1b28bce6ec9f',
   renderTo: 'tree',
   checkBox:true,
   rootIcon:"deptTreeicon",
   clickMethodjs:clickMethod,
   treeUrl:G_ROOT_PATH+"/user/getDeptTreeNode.do"
});
 */
  Com.panel.EvecomTreePanel = Ext.extend(Ext.tree.TreePanel, {
	  
	    manageAble: true,
	    autoScroll: true,
        initComponent: function() {
        var me = this
        //设置根节点
        var rootConfig = {
            iconCls:me.rootIcon,
            text: me.rootText,
            id: me.rootId,
            draggable: false,
            leaf: false,
            expanded : true
        }
        this.root = new Ext.tree.AsyncTreeNode(rootConfig)
            this.loader = new Ext.tree.TreeLoader({
            dataUrl: me.serviceUrl
        
        });
         // 当选中父节点时，让其子节点相应选中
               me.on('checkchange', function(node, checked) { 
                node.expand();  
                node.attributes.checked = checked;  
                node.eachChild(function(child) {  
                    child.ui.toggleCheck(checked);  
                    child.attributes.checked = checked;  
                    child.fireEvent('checkchange', child, checked);  
                });  
            }, me); 
        me.on("render", function(me) {
                me.getRootNode().expand(false)
        }),
        Com.panel.EvecomTreePanel.superclass.initComponent.call(this);
    }
   // ,onDestroy:function(){
       //Com.panel.EvecomBaseTreePanel.superclass.onDestroy.call(this);
   //}

})
Ext.reg('EvecomTreePanel', Com.panel.EvecomTreePanel);