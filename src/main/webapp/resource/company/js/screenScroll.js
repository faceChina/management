(function($){
	$.screenScroll=function(options){
		var opts=$.extend({},$.screenScroll.defaults,options);
			
		var jmain=$(opts.outBoxId);	
		var jhead=$(opts.headId);
		var jfoot=$(opts.footId);
		var jrow=$(opts.eachScreenClass);
		var jcol=$(opts.eachPartClass);
		
		var jheadHeight=jhead.height();
		var jfootHeight=jfoot.height();
		
		var len = jrow.length;
		var canMousewheel = true;
		var currentIndex = 0; 
		var setTimeoutId;
		
		
		//滚动到row,toIndex为row的索引
		function go(toIndex){
			var direction; //方向
			canMousewheel = false; //鼠标滚轮暂时不可用
			toIndex = Math.max(0,Math.min(len-1,toIndex)); //确保 0 =< toIndex <= len-1
			direction = toIndex > currentIndex ? "down" : "up"; //判断方向
			
			//当只有页面底部隐藏时才能执行滚动
			if(jfoot.css("display")=="none"){
				if(toIndex != currentIndex){
					goOut(currentIndex,direction);
					goIn(toIndex,direction);
				}
				
				footScroll();
				
				if(toIndex == currentIndex) {
					canMousewheel=true;
					return; //判断索引为当前索引，则返回
				}
				currentIndex = toIndex;
			}else{
				footScroll();
			}
			
			//页面底部滚动效果
			function footScroll(){
				if(currentIndex==len-1 && toIndex >= currentIndex){
					jfoot.show();
					$("body").stop().animate({scrollTop:jfootHeight},500,"easeInOutExpo",function(){
						canMousewheel=true;
					});
				}else{
					$("body").stop().animate({scrollTop:0},500,"easeInOutExpo",function(){
						canMousewheel=true;
						jfoot.hide();
					});
				}
			}
		}
		
		//进场
		function goIn(toIndex,direction){
			if(toIndex==0){
				var nowHeight=$(window).height()-jheadHeight;
				jhead.css("overflow","hidden").stop().animate({height:jheadHeight},1200);
				jmain.stop().animate({scrollTop:toIndex*nowHeight},1200,"easeInOutExpo",function(){
					canMousewheel=true;
					jmain.css("height",nowHeight);
					jrow.css("height",nowHeight);
					jrow.eq(toIndex).find(".view-prev").animate({opacity:0}) //滚动到第一张时，隐藏向上按钮
				});
			}else{
				var nowHeight=$(window).height()-110;//自定义隐藏后要显示的高度=110
				jhead.css("overflow","hidden").stop().animate({height:110},1200);
				jmain.css("height",nowHeight);
				jrow.css("height",nowHeight);
				jmain.stop().animate({scrollTop:toIndex*nowHeight},1200,"easeInOutExpo",function(){
					canMousewheel=true;
				});
			}
			
			if(toIndex==len-1){
				jrow.eq(len-1).find(".view-next").animate({opacity:0}) //滚动到最后一张时，隐藏向下按钮
			}
				
		}
		
		//出场
		function goOut(currentIndex,direction){
			var currentRow = jrow.eq(currentIndex);
			var currentCol = currentRow.find(opts.eachPartClass);
			var currentChildren = currentCol.data("children");
			var len = currentChildren.length;
			currentChildren.each(function(index){
				var toScrollHeight = direction === "down" ? -100*(len-index) : 100*(index+1);
				$(this).stop().animate({"top":toScrollHeight},800,"easeInOutExpo",function(){
					$(this).css("top",0);
				});
			});
		}
		
		// 下一屏
		$(opts.nextBtn).each(function(index) {
			$(this).on("click", function() {
				go(index+1);
			});
		});
		
		// 上一屏
		$(opts.preBtn).each(function(index) {
			$(this).on("click", function() {
				go(index-1);
			});
		});
		
		//定位
		$(opts.listBtn).each(function(index){
			$(this).click(function(){
				go(index);
			});
		});
		
		//初始化页面
		function initPage(){
			var nowHeight=$(window).height()-jhead.height();
			jrow.css({height:nowHeight,overflow:"hidden"});
			jmain.css({height:nowHeight,overflow:"hidden"}).scrollTop(nowHeight*currentIndex);
			jfoot.hide();
		}
		initPage();
		
		//初始化缓存
		jcol.each(function(){
			var defaultMarginTop = parseInt($(this).css("margin-top"));
			var children = $(this).children();
			$(this).data({"defaultMarginTop":defaultMarginTop,"children":children});
			children.css({"position":"relative"});
		});
		
		// 改变窗口大小事件
		$(window).on("resize", function() {
			initPage();
		});
		
		//鼠标滚轮
		function mouseWheel(e){
			var direction;
			var e = e || event;
			//console.log(canMousewheel);
			if(!canMousewheel) return;
			
			clearTimeout(setTimeoutId);
			//获取滚轮方向
			if(e.wheelDelta){	//IE/Opear/Chrome
				direction=e.wheelDelta;
			}else if(e.detail){	//Firefox
				direction=e.detail*(-1);
			}
			setTimeoutId=setTimeout(function(){
				direction < 0 ? go(currentIndex+1) : go(currentIndex -1)
			},100);
			
		}
		
		//绑定滚轮事件
		if(jmain[0].addEventListener){ //FF3.5以下
			jmain[0].addEventListener("DOMMouseScroll",function(e){
				mouseWheel(e);
			},false);
		}
		jmain[0].onmousewheel=function(e){
			mouseWheel(e);
		};
		
	};//end $.screenScroll
	
	$.screenScroll.defaults={
			headId:"",//页面头部ID
			footId:"",//页面底部ID
			outBoxId:"",//需要做效果的最外层ID
			eachScreenClass:"",//需要做效果的每一屏Class
			eachPartClass:"",//需要做效果的每一屏里面的每个部分的总外框
			nextBtn:"",//向上
			preBtn:"",//向下
			listBtn:""//按钮列表
	};
})(jQuery);