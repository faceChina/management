
$(document).ready(function() {
//轮播图
jQuery(".slider").slide({ titCell:".hd ul", mainCell:".bd ul", effect:"fold",  autoPlay:true, autoPage:true, trigger:"click",
mouseOverStop:true,
startFun:function(){
	var timer = jQuery(".slider .timer");
	timer.stop(true,true).animate({ "width":"0%" },0).animate({ "width":"100%" },2500);
}
});	
$('.website_top_ewm').click(function(event) {
$('.website_top_alertewm').toggleClass('website_show');
});
$('.website_top_top').click(function(event) {
pageScroll();
});
});


function pageScroll(){//返回顶部
window.scrollBy(0,-100);
scrolldelay = setTimeout('pageScroll()',100);
var sTop=document.documentElement.scrollTop+document.body.scrollTop;
if(sTop==0) clearTimeout(scrolldelay);	
}