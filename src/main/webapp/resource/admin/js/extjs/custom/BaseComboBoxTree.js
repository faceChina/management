/**
取输入框的值用Ext.getCmp("mytree").hiddenField   除非hiddenField:'text',初始化为text 否则不填 都为取树的ID   isTreeAll 如果是true 树全部展开 否则按节点加载
    Ext.onReady(function(){
new Ext.form.TrueComboBoxTree({
           id: 'mytree',
            isTreeAll:true,
           renderTo : 'comboBoxTree',
          hiddenField:'id',
           fieldLabel:'机构',
            tree: new Ext.tree.TreePanel({
                root: new Ext.tree.AsyncTreeNode({ text: '--选择--', id:'f6cef0af-52d2-4752-8a37-1b28bce6ec9f'}),
                rootVisible: false,
                border: false,
                dataUrl:  ,
                listeners: {
                    beforeload: function(n) { if (n) { this.getLoader().baseParams.id = n.attributes.id; } }
                }
            })
           
        }) 
        
   });
  function tt(){
  alert(Ext.getCmp("mytree").hiddenField);
  }
**/
BaseComboBoxTree = Ext.extend(Ext.form.ComboBox, {

        fieldLabel:'下拉框树',
        store: new Ext.data.SimpleStore({ fields: [], data: [[]] }),
        editable: false,
        shadow: false,
        mode: 'local',
        triggerAction: 'all',
        selectedClass: '',
        onSelect: null,
        canCollapse: true,
        constructor: function(_cfg) {
            if (_cfg == null) {
                _cfg = {};
            }
            Ext.apply(this, _cfg);
            this.treerenderid = Ext.id();
            this.tpl = String.format('<tpl for="."><div style="height:200px"><div id="ext-combobox-tree{0}"></div></div></tpl>', this.treerenderid);
            BaseComboBoxTree.superclass.constructor.apply(this, arguments);
            this.hiddenField='';
            if (this.tree) {
                var cmb = this;
                this.tree.on('click', function(node) {
                	
                    cmb.canCollapse = true;
                    if (Ext.isFunction(cmb.onSelect)) {
                        cmb.onSelect(cmb, node);
                    } else {
                        cmb.setValue(node.text);
                        if(cmb.hiddenField=='text'){
                        cmb.hiddenField = node.text;
                        }else{
                         cmb.hiddenField = node.id;
                        }
                    } 
                    cmb.collapse();
                });
                //以下事件，让combobox能正常关闭
                this.tree.on('expandnode', function() { cmb.canCollapse = true; });
                this.tree.on('beforeload', function() { cmb.canCollapse = false; });
                this.tree.on('beforeexpandnode', function() { cmb.canCollapse = false; });
                this.tree.on('beforecollapsenode', function() { cmb.canCollapse = false; });
                if(cmb.isTreeAll){
                //树在Load时自动展开
					this.tree.on('load', function(node, checked) {
					node.expand(); 
					node.eachChild(function(child) {
					child.expand(); 
					}); 
					}); 
                }
            }
            this.on('expand', this.expandHandler, this);
            this.on('collapse', this.collapseHandler, this);
        },
        
        expandHandler: function expand() {
            this.canCollapse = true;
            if (this.tree) {
                this.tree.render('ext-combobox-tree' + this.treerenderid);
                this.canCollapse = true;          
                this.tree.getRootNode().expand();
                
            }
        },
        collapseHandler: function collapse() {
            if (!this.canCollapse) {
                this.expand();
            }
        }
    
    });
    Ext.reg('baseComboBoxTree',BaseComboBoxTree);