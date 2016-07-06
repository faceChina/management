/*
 * //listeners:boxLin
var  boxLin={
      select: function(combo, record, index) {
        alert(index);
      }
}

 { border : false,
   xtype :'EvecomComboBox',
   optionKey:'STATE',
   fieldLabel :'状态',
   id :'stateId',
   name :'stateId',
   listeners:boxLin,
   optionUrl:'',
   storeFiled:['','']//默认查询数据字典 此项不用填,
   sysParam:'NATION'//默认查询数据字典时，加上查询的参数值
 }
    { border : false,
   			   xtype :'EvecomComboBox',
               fieldLabel :'状态',
               sysParam:'NATION',
               id :'stateId',
               emptyText:'参数值'
             }
 */
Com.panel.EvecomBaseCombox = Ext.extend(Ext.form.ComboBox, {
   optionKey:"", 
   optionUrl:"",
   dataArray:null,
   jsonData:null,
   initComponent: function(){
      this.triggerAction = 'all';
      var querystoreFiled = this.storeFiled;//查询store的array[]
      var idProperty = this.idProperty;
      var v_Url="";
      var sysParam = this.sysParam;
      var vComBox=this;
        if(this.optionUrl!=null&&(this.optionUrl).length>0){
	     v_Url=this.optionUrl;
      }else if(sysParam!=undefined){
         v_Url=ROOT_PATH+"/admin/dict/getByCode.htm?sbdCode="+sysParam;
       this.valueField='id'; 
	   this.displayField='name';
	   querystoreFiled = ['id','name'];
	   idProperty = 'id';
      }
      if(this.dataArray!=null){
    	  this.mode='local';
    	  this.store=new Ext.data.ArrayStore({
		   data: this.dataArray, 
		   fields: ['value','text']
	      });
    	  
      }else if(this.jsonData!=null){
    	  this.mode='local';
    	  this.store=new Ext.data.ArrayStore({
		  data: this.jsonData, 
		   fields: [{name:'value',mapping:"value"},{name:'text',mapping:'text'}]
	      });
    	  this.store.on('load',function(store,record,opts){
    		  vComBox.setValue((this.jsonData)[0].value);
    		  //alert(vComBox.getValue())
    	  })
    	  //this.store.load();
      }else{
    	  this.mode='local';
    	  
	      this.store = new Ext.data.JsonStore({
	      autoLoad : false, 
	       url : v_Url,
	       fields : querystoreFiled
	      });
	      this.store.load();
      }
	 
	

      Com.panel.EvecomBaseCombox.superclass.initComponent.call(this);

   },onDestroy:function(){
	   Ext.destroy(this.store);
	   delete this.mode;
	   delete this.optionKey;
	   delete this.optionUrl;
	   delete this.dataArray;
	   delete this.jsonData;
	   delete this.blankText;
	   delete this.emptyText;
	   delete this.store;
	   Com.panel.EvecomBaseCombox.superclass.onDestroy.call(this);
   }
})

Ext.reg('EvecomComboBox', Com.panel.EvecomBaseCombox);