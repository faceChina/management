Ext.apply(Ext.form.VTypes, {
			userOrganTreeVa : function(val, field) { //验证部门用户下拉框树 只能选择用户
			var organUserValue = field.hiddenField;
			if(organUserValue.indexOf(",")>0){
				return true;
			}else{
				return false;
			}
			},
			planTypeTreeVa:function(val,field){//验证预案类别下拉框树，只能选择小类
			var planTypeValue = field.hiddenField;
			if(planTypeValue!=null){
				return true;
			}else{
				return false;
			}
			},
			
			
			recipientsNumber: function(val,field){
				var falg=true;
				var materialname=Ext.get(field.confirmTo);
				if(val>materialname.getValue()){
				 falg=false;
				}else{
				 falg=true;
				}
				return falg;
			},
			
			
			
			password111:function(val,field){//val指这里的文本框值，field指这个文本框组件，大家要明白这个意思
      if(field.confirmTo){//confirmTo是我们自定义的配置参数，一般用来保存另外的组件的id值
 
          var pwd=Ext.get("supervisaldept");//取得confirmTo的那个id的值
          return (val==pwd.getValue());
 
      }
 
      return true;
 
    }

		});		