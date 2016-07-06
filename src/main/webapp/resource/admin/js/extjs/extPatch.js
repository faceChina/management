/*
if(Ext.isIE){
    CollectGarbage();
}
*/
Ext.override(Ext.form.BasicForm,{ 
	beforeAction : function(action){
	    var o = action.options;
	    if(o.waitMsg){
	        if(this.waitMsgTarget === true){
	            this.el.mask(o.waitMsg, 'x-mask-loading');
	        }else if(this.waitMsgTarget){
	            this.waitMsgTarget = Ext.get(this.waitMsgTarget);
	            this.waitMsgTarget.mask(o.waitMsg, 'x-mask-loading');
	        }else{
	            Ext.MessageBox.wait(o.waitMsg, o.waitTitle || this.waitTitle || '请稍等...');
	        }
	    }
	}
});

Ext.override(Ext.Window, {
    onDestroy:function(){
       
	   if (this.rendered){
          this.hide();	  
       }
	   if(this.manager){
	        this.manager.unregister(this);
	   }
	   
	   if(this.doAnchor){
	      Ext.EventManager.removeResizeListener(this.doAnchor, this);
	      Ext.EventManager.un(window, 'scroll', this.doAnchor, this);
       }
	   Ext.EventManager.removeResizeListener(this.onWindowResize, this);
	   /*
	    if(this.items){
		   this.items.each(function(item){
               if(item && item.body){            	   
                   (item.body).removeAllListeners();	                    
                   item.body = null;
               }
               Ext.destroy(item);
           }, this);
        }
        */
	    this.removeAll();
        Ext.destroy(
       	   this.items,
           this.focusEl,
           this.resizer,
           this.dd,
           this.proxy
        );
	    if (!this.mask)
	       Ext.destroy(this.mask);
		
	    this.removeAll();
	    Ext.Window.superclass.onDestroy.call(this);
	    delete this.items;
	    delete this.focusEl;
	    delete this.resizer;
	    delete this.dd;
	    delete this.proxy;
	    
	    if (!this.mask) return;
		delete this.mask;
}
});
Ext.override(Ext.form.RadioGroup, {
    getValue : function() {
        var v;
        this.items.each(function(item) {
            if (item.getValue()) {
                v = item.getRawValue();
                return false;
			}
		});
		return v;
	},
	setValue : function(v) {
        if (this.rendered) {
            this.items.each(function(item) {
                item.setValue(item.getRawValue() == v);
			});
        } else {
            for (k in this.items) {
                this.items[k].checked = this.items[k].inputValue == v;
            }
        }
	}
});

