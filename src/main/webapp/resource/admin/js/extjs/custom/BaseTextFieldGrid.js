
BaseTextFieldGrid = Ext.extend(Ext.form.TextField, {
	
initComponent : function() {
Ext.form.TextField.superclass.initComponent.call(this);
this.fieldLabel='事件名称';
this.addEvents('autosize', 'keydown', 'keyup', 'keypress', 'click');
},
initEvents : function() {
Ext.form.TextField.superclass.initEvents.call(this);
if (this.validationEvent == 'keyup') {
this.validationTask = new Ext.util.DelayedTask(this.validate, this);
this.mon(this.el, 'keyup', this.filterValidation, this);
} else if (this.validationEvent !== false) {
this.mon(this.el, this.validationEvent, this.validate, this, {
buffer : this.validationDelay
});
}
if (this.selectOnFocus || this.emptyText) {
this.on('focus', this.preFocus, this);


this.mon(this.el, 'mousedown', function() {
if (!this.hasFocus) {
this.el.on('mouseup', function(e) {
e.preventDefault();
}, this, {
single : true
});
}
}, this);


if (this.emptyText) {
this.on('blur', this.postBlur, this);
this.applyEmptyText();
}
}
if (this.maskRe || (this.vtype && this.disableKeyFilter !== true && (this.maskRe = Ext.form.VTypes[this.vtype + 'Mask']))) {
this.mon(this.el, 'keypress', this.filterKeys, this);
}
if (this.grow) {
this.mon(this.el, 'keyup', this.onKeyUpBuffered, this, {
buffer : 50
});
this.mon(this.el, 'click', this.autoSize, this);
}
if (this.enableKeyEvents) {
this.mon(this.el, 'keyup', this.onKeyUp, this);
this.mon(this.el, 'keydown', this.onKeyDown, this);
this.mon(this.el, 'keypress', this.onKeyPress, this);
}
this.mon(this.el, 'click', this.onClick, this);
},


// private
onClick : function(e) {
	

	var object = this;
	var toolbarEventText = new Ext.Toolbar({
        id:'toolbarEventText',
		items : []
	    });
		var tToolbarEventText = [
		{// 顶部工具栏 '-'为竖线分隔符
			xtype : 'label',// 声明label标签类型
			text : '事件名称'// label名称
		}, '-', {
			xtype : 'textfield',
			id : 'eventName'
		},'-',{
			text : '查 询',
			iconCls : 'event_search',
			handler : function() {// 所调用的函数
				var eventNameValue = Ext.getCmp("eventName").getValue();
					ds.load({
							params : {
								eventName:eventNameValue,
								start : 0,
								limit : 5
							}
						});
		}},  '-', '->']// ->组件向右靠齐
		toolbarEventText.add(tToolbarEventText);
      var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),  {header : "灾害事件主键",dataIndex : "ccId",hidden : true},
		                       {header:"事件名称",id:"ccName",dataIndex:"ccName"},
		                       {header:"事件分类",id:"ccCategoryCodeName",dataIndex:"ccCategoryCodeName",width:70},
		                       {header:"事发时间",id:"ccDate",dataIndex:"ccDate",width:110}]);
     var ds = new Ext.data.Store({
			autoLoad : false,
			proxy : new Ext.data.HttpProxy({
						url : '#'
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : 'total',
						root : 'data'
					}, [{
								name : 'ccId'
							}, {
								name : 'ccName'
							}, {
								name : 'ccCategoryCodeName'
							}, {
								name : 'ccCategoryCodeName'
							}, {
								name : 'ccDate'
							}])
		});
		
		var comboBoxGrid = new Ext.grid.GridPanel({
			id:'eventQuryGridId',
			ds : ds,
			cm : cm,
			width : 450,
			height : 220,
			region : 'center',
			autoSizeColumns : true,
			loadMask : true,
			frame : true,
			trackMouseOver : true,
			tbar:toolbarEventText,
			viewConfig : {
				forceFit : true
			},
			bbar : new Ext.PagingToolbar({
						pageSize : 5,
						store : ds,
						displayInfo : true,
						beforePageText : "第",
						afterPageText : "页  共{0}页",
						displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
						emptyMsg : "没有记录"
					})
		});
		comboBoxGrid.on('rowdblclick', function(grid, rowIndex, e) {
			selectMenu.hide();
			var record = grid.getSelectionModel().getSelected();
			var ccName = record.get("ccName");
			var ccId = record.get("ccId");

			//alert(slName);
			
			object.setValue(ccName);
			object.eventId =ccId; 
			
		});
		var selectMenu = new Ext.menu.Menu({
			items : [comboBoxGrid]
		});
		var url = ROOT_PATH+"/message/findEventReportedInWorkFlow.do?tag=audit&&queryFlag=0";
				ds.proxy.setUrl(url, true);
				if (this.menu == null) {

					this.menu = selectMenu;
				}
				ds.load({
							params : {
								start : 0,
								limit : 5
							}
						});
				this.menu.show(this.el, "tl-bl?");
},
getEventId:function(){
return this.eventId;
},
resetEventId :function(){
	this.eventId="";
}
});
Ext.reg('baseTextFieldGrid', BaseTextFieldGrid);
