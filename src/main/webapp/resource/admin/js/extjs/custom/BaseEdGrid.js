
Com.panel.EvecomBaseEdGridPanel = Ext.extend(Ext.grid.EditorGridPanel, {
   queryUrl:"",
   selType:0,
  
  constructor : function(config) {
		Ext.apply(this,config);
		var grid = this;
	   var v_fieldsArr=config.fieldsArr;
	   var v_columnsArr=config.columnsArr;
       var colArr=[];       
	   if(this.selType==0){
		  colArr=v_columnsArr;
	   }else if(this.selType==1){
		   var smCol=new Ext.grid.CheckboxSelectionModel({singleSelect:this.vsingleSelect,grid:this});
		  colArr[0]=smCol
		  for(var z=0;z<v_columnsArr.length;z++)
		     colArr[colArr.length]=v_columnsArr[z];
			this.sm=smCol;
	   }
	   
	   this.columns=colArr;
	   	this.store = this.initStore(config);
		var bbar = this.initPagingBar(this.store);
		Com.panel.EvecomBaseEdGridPanel.superclass.constructor.call(this,{
			frame : true,
			//region : "center",
			//trackMouseOver : true,
			//disableSelection : false,
			//loadMask : true,
			store:this.store,
			bbar:bbar,
			 clicksToEdit: 1
		
		});
//	   var v_store=new Ext.data.JsonStore({
//                    root: 'DATA',
//			        idProperty: this.pkField,
//					proxy: new Ext.data.HttpProxy({url: this.queryUrl,method:this.vMethod}),
//					fields:v_fieldsArr				
//					});
//	   var v_GridId=this.id;
//	   if(this.gridType==0){
//		   this.tbar=[
//						{
//						 	xtype: 'button',
//						 	scope:this,
//						 	text : '新增一行',
//						 	disabled:this.addDisable,
//						    handler : function() {
//						    	Ext.getCmp(v_GridId).insertRows();
//						    }
//						},'-',{
//						 	xtype: 'button',
//						 	text : '删除一行',					 	
//						 	disabled:this.delDisable,
//						    handler : function() {
//						    	Ext.getCmp(v_GridId).deleteRows();
//						    }
//						}
//		          ];
//	   }
	   //this.store=v_store;
	   
       //Com.panel.EvecomBaseEdGridPanel.superclass.initComponent.call(this);
       //this.store.load({params:{start:0, limit:15}});
   },
   initPagingBar:function(store){
		return new EVE.PagingToolbar({store:store});
	},
	initStore:function(config){
		var store = null;
		var store = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : config.queryUrl
					}),
			reader : new Ext.data.JsonReader({
						root : "data",
						totalProperty : "total",
						fields :config.fieldsArr
			}),
			remoteSort : config.remoteSort
		});
		// 翻页排序时带上查询条件
		store.on('beforeload', function() {
			if(config.tbar){
				var toolbarId = config.tbar.getId();
				this.baseParams = AppUtil.loadBaseParams([toolbarId]);
			}
	       
		});
		return store;
	},
   queryData:function(params){
	   var v_params="{";
	    for(var p in params){
	    	   try{
	    		   v_params+="\""+p+"\":\""+params[p]+"\",";
	    	   }catch(e){}
	    }
	    v_params+="aa:11}";

	    this.store.baseParams=Ext.util.JSON.decode(v_params);
   	    this.store.load({params:{start:0, pageSize:g_grid_pageSize}});
   },
  insertRows : function(){
    /*
      this = EditorGridPanel
      this.store.recordType() = Recrod类型
    */
    var recrod = new this.store.recordType();
    /*
     因为有的时候我们不是通过Ext.data.Record.create方法来构靠造这个Record的,
     而是通过Ext.Data.Reader 或者 Store 的 fields 来构造Record的,
     所以要用到下面的赋值代码
     */
    recrod.data = {};
    //获得Record的字段
    var keys = this.store.fields.keys;
    for(var i=0;i<keys.length;i++){
        recrod.data[keys[i]] = '';
    }
    //停止EditorGridPanel编辑
    this.stopEditing();
    //将Record数据添入Store中
    this.store.insert(0,recrod);
    //开EditorGridPanel编辑,光标在第1行第2列
    this.startEditing(0, 1);}
,
 deleteRows:function(){
	     var v_Grid=this;
	     top.Ext.Msg.confirm(
	    		 '信息','确定要删除？',
	    		 function(btn) { 
					 if(btn == 'yes') { 
						    var row=v_Grid.getSelectionModel().getSelections();//选择行的个数
					        if(row.length==0){   
					            Ext.Msg.alert("提示信息","请您至少选择一条记录.");   
					        }else if(row.length>1){     
					            Ext.Msg.alert("提示信息","对不起只能选择一条记录.");   
					        }else if(row.length==1)   
					        {  
					        	v_Grid.store.remove(row[0]);
					        	var pkValue=row[0].get(v_Grid.pkField);
					        	if(pkValue.length>0){
					        	   v_Grid.delPkList[(v_Grid.delPkList).length]=pkValue;
					        	}
					        }
					        
					 }
	              }
	     )
 },onDestroy:function(){
	 if(this.body){
         var c = this.body;
         c.removeAllListeners();
         c.update("");
     }
     if(this.rendered){  	   
         Ext.destroy(this.view, this.loadMask);
     }else if(this.store){
         this.store.destroy();
     }
     
     Ext.destroy(this.colModel,this.columns,this.bbar,this.store);
     
     delete this.queryUrl;
     delete this.selType;
     delete this.pkField;
     delete this.fieldsArr;
     delete this.columnsArr;
     delete this.addDisable;
     delete this.delDisable;
     delete this.delPkList;
     delete this.vsingleSelect;
     delete this.gridType;
     delete this.store;
     delete this.colModel;
     delete this.view;
     delete this.loadMask;
     
     Ext.grid.GridPanel.superclass.onDestroy.call(this);
   }
   
});

Ext.reg('EvecomBaseEdGridPanel', Com.panel.EvecomBaseEdGridPanel);