/* 版本：slide.V.1.0.0
 * 功能：滑动门
 * 作者：凡开
 * 时间：2013-11-08
 * 使用方法：
 * $("plugin='slide'").each(function(index, element) {
        var _this=$(this);
		var _data=_this.data();
		_this.slide({
			slidehd:_data.slidehd,
			slidebd:_data.slidebd,
			events:_data.events,
			filter:_data.filter
		});
    });
 */

(function($){
	$.fn.slide=function(options){
		var _opts=$.extend({},$.fn.slide.defaults,options);
		
		var _this=$(this);
		var _handles=_this.find("[pluginHandles='handles']");
		var _handlesChild=_handles.children();
		var _content=_this.find("[pluginContent='content']");
		var _contentChild=_content.children();
		
		var _events=_opts.events;
		var _filter=_opts.filter;
		
		return this.each(function(){
			_contentChild.first().css("display","none");//初始化隐藏第一张图片
			_handlesChild.bind(_events,function(){
				var _num=$(this).index();
				_handlesChild.removeClass("current");
				$(this).addClass("current");
				
				//如果单击第一个，则显示全部
				if(_handlesChild.first().attr("class")=="current"){
					_contentChild.css("display","block");
					_contentChild.first().css("display","none");
					return;
				}
				
				
				if(_filter=="normal"){
					_contentChild.css("display","none");
					_contentChild.eq(_num).css("display","block");
				}else if(_opts.filter=="fade"){
					_contentChild.stop().animate({
						"opacity":0
					},300,function(){
						_contentChild.css("display","none");
						_contentChild.eq(_num).stop().css("display","block").animate({
							"opacity":1
						},300);
					});
				}
			});	
		});//return this.each
	}//$.fn.slide
	
	$.fn.slide.defaults={
		events:"click",
		filter:"normal"
	}//$.fn.slide.defaults
})(jQuery);

$(function(){
	$("[plugin='slide']").each(function() {
       $(this).slide();
    });
})