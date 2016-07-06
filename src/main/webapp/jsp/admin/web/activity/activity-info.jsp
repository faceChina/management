<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="resourcePath" value="${pageContext.request.contextPath}/resource/admin/" />

<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="UTF-8" />
		<title>数据查看</title>	
		<!-- top -->
		<script  type="text/javascript" src="${resourcePath}js/jquery-1.6.min.js"></script>
		
		<link href="${ctx}/resource/admin/css/fancy/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/resource/admin/css/fancy/main.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/resource/admin/css/fancy/main-gw.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/resource/admin/css/user/main-gl.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/resource/admin/css/font/iconfont.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="${ctx}/resource/admin/js/My97DatePicker/calendar.js"></script>
		<script type="text/javascript" src="${ctx}/resource/admin/js/My97DatePicker/WdatePicker.js"></script>
		<link rel="stylesheet" href="${ctx}/resource/admin/js/My97DatePicker/skin/default/datepicker.css"/>
		
		<script type="text/javascript" src="${ctx}/resource/admin/js/artDialog/jquery.artDialog.js"></script>
		<script type="text/javascript" src="${ctx}/resource/admin/js/artDialog/iframeTools.js"></script>
		<!--top end -->
		
		<script type="text/javascript">
			var startShop = 0;
			var pageSizeShop = 10;
			var totalShop = 0;
			
			$(function(){
				getSlcoinTotal();
				getSlcoinTotalByShop();
				getSlcoinReportByTime(0);
				getSlcoinCurrentTime();
				
				$("#query").click(function(){
					getSlcoinTotalByShop();
					getSlcoinReportByTime(0);
					getSlcoinCurrentTime();
				});
			});
			//查询颜值总计
			function getSlcoinTotal(){
				$.ajax({
	                type: "POST",
	                url: '${ctx }/web/activity/slcoinTotal.htm',
	                dataType: "json",
	                async:true,
	                beforeSend: function () {
	                },
	                success: function(data){
	                	if(data.success){
	                		$('#giveTotal').html(data.info.giveTotal);
	                		$('#useTotal').html(data.info.useTotal);
	                	}else{
	                		art.dialog.tips(data.resultDesc);
	                	}
	                },
	                error:function(){
	        			art.dialog.tips('系统异常');
					}
	            });
			}
			//查询店铺使用情况
			function getSlcoinTotalByShop(){
				var beginTime = $('#beginTime').val();
				var endTime = $('#endTime').val();
				$.ajax({
	                type: "POST",
	                url: '${ctx }/web/activity/shopList.htm',
	                data:{'beginTime':beginTime,'endTime':endTime,'start':0,'pageSize':10},
	                dataType: "json",
	                async:true,
	                beforeSend: function () {
	                },
	                success: function(data){
	                	if(data.success){
	                		packageShopData(data.info);
	                	}else{
	                		art.dialog.tips(data.info);
	                	}
	                },
	                error:function(){
	        			art.dialog.tips('系统异常');
					}
	            });
			}
			function packageShopData(data){
				var html = '';
        		
        		for(var i=0;i<data.datas.length;i++){
        			var shop = data.datas[i];
        			html += '<tr><td>' + shop.name + '</td><td>' + shop.total + '</td></tr>';
        		}
        		
        		$('#shopList').html(html);
			}
			//查询每日发放/使用情况
			function getSlcoinReportByTime(start){
				var beginTime = $('#beginTime').val();
				var endTime = $('#endTime').val();
				$.ajax({
	                type: "POST",
	                url: '${ctx }/web/activity/slcoinDateReport.htm',
	                data:{'beginTime':beginTime,'endTime':endTime,'start':start,'pageSize':pageSizeShop},
	                dataType: "json",
	                async:true,
	                beforeSend: function () {
	                },
	                success: function(data){
	                	if(data.success){
	                		totalShop = data.info.totalRow;
	                		startShop = data.info.start;
	                		packageTimeData(data.info);
	                	}else{
	                		art.dialog.tips(data.info);
	                	}
	                },
	                error:function(){
	        			art.dialog.tips('系统异常');
					}
	            });
			}
			function packageTimeData(data){
				$(".tabinfo a").removeClass('none');
				
				var html = '';
        		
        		for(var i=0;i<data.datas.length;i++){
        			var time = data.datas[i];
        			html += '<tr><td>' + time.beginTime + '</td><td>' + time.giveTotal + '</td><td>' + time.useTotal + '</td></tr>';
        		}
        		
        		$('#timeList').html(html);
        		
        		if((startShop+pageSizeShop) >= totalShop){
					$('#nextShop').addClass('none');
				}
        		if((startShop-pageSizeShop) < 0){
					$('#upShop').addClass('none');
				}
			}
			//查询每日发放/使用情况:下一页
			function nextPageByShop(obj){
				if((startShop+pageSizeShop) >= totalShop){
					return;
				}

				startShop = startShop + pageSizeShop;
				
				getSlcoinReportByTime(startShop);
			}
			//查询每日发放/使用情况:上一页
			function upPageByShop(obj){
				if((startShop-pageSizeShop) < 0){
					return;
				}
				
				startShop = startShop - pageSizeShop;

				getSlcoinReportByTime(startShop);
			}
			//查询每日发放/使用情况
			function getSlcoinCurrentTime(){
				var beginTime = $('#beginTime').val();
				var endTime = $('#endTime').val();
				$.ajax({
	                type: "POST",
	                url: '${ctx }/web/activity/slcoinCurrentTime.htm',
	                data:{'beginTime':beginTime,'endTime':endTime},
	                dataType: "json",
	                async:true,
	                beforeSend: function () {
	                },
	                success: function(data){
	                	if(data.success){
	                		$('#giveTotalByTime').html(data.info.giveTotal);
	                		$('#useTotalByTime').html(data.info.useTotal);
	                	}else{
	                		art.dialog.tips(data.info);
	                	}
	                },
	                error:function(){
	        			art.dialog.tips('系统异常');
					}
	            });
			}
		</script>
	</head>
	<body>
		<!-- body  -->
		<div class="container" id="j-content">
			<div class="row">
				<div class="col-md-12">
					<div class="row">
						<div class="box box-auto">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#" data-toggle="tab">数据查看</a></li>
								</ul>
							</div>
	
							<div class="content">
								<form action id="jform" class="form-horizontal">
									<div class="col-md-12 information-top">
										<div class="information-mark"><b>颜值总计</b></div>
										<div class="col-md-6">
											累计发放颜值：<b class="color-danger" id="giveTotal">0</b>点
										</div>
										<div class="col-md-6">
											累计使用颜值：<b class="color-danger" id="useTotal">0</b>点
										</div>
									</div>
									<div class="form-group">
										<label class="control-label pull-left">
											<b>选择日期</b>
										</label>
										<div class="col-md-3">
											<input type="text" id="beginTime" class="form-control dateFirst" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" readonly="readonly" name="beginTime">
										</div>
	
										<label class="control-label pull-left">
											<b>至</b>
										</label>
										<div class="col-md-3">
											<input type="text" id="endTime" class="form-control dateFirst" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" readonly="readonly" name="endTime">
										</div>
										<div class="col-md-2">
											<button type="button" id="query" class="btn btn-default">查询</button>
											<button type="button" id="reset" class="btn btn-default">重置</button>
										</div>
									</div>
									<br>
									<div class="form-group">
										<label>
											<b>每日发放/ 使用情况</b>&nbsp;&nbsp;&nbsp;当前时间： 共发放颜值<b class="color-danger" id="giveTotalByTime">0</b>点；已使用<b class="color-danger" id="useTotalByTime">0</b>点。
										</label>
										<div class="pull-right tabinfo">
											<a href="javascript:void(0);" class="none" onclick="upPageByShop(this)" id="upShop">
												<span class="icon iconfont icon-back"></span>
											</a>
											<a href="javascript:void(0);" onclick="nextPageByShop(this)" id="nextShop">
												<span class="icon iconfont icon-right"></span>
											</a>
											<!-- 
											<a href="javascript:void(0);" class="none" onclick="datalist(this,'7','left')">
												<span class="icon iconfont icon-back"></span>
											</a>
											<a href="javascript:void(0);" onclick="datalist(this,'7','right')">
												<span class="icon iconfont icon-right"></span>
											</a>
											 -->
										</div>
									</div>
									
									<table class="table table-bordered information">
										<thead>
											<tr>
												<th width="100">日期</th>
												<th width="100">发放</th>
												<th width="100">使用</th>
											</tr>
										</thead>
										<tbody id="timeList">
										</tbody>
									</table>
									<hr>
									<div class="form-group">
										<label>
											<b>店铺使用情况</b>
										</label>
									</div>
									<table class="table table-bordered">
										<thead>
											<tr>
												<th class="text-center">店铺名称</th>
												<th class="text-center">使用颜值数量</th>
											</tr>
										</thead>
										<tbody id="shopList">
										</tbody>
									</table>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- body end -->
	</body>
	<script type="text/javascript">
		var jsonStr = [
			{"id":"1","list":[
				{"date":"8月1日","grant":"300","used":"50"},
				{"date":"8月2日","grant":"200","used":"150"},
				{"date":"8月3日","grant":"100","used":"20"},
				{"date":"8月4日","grant":"200","used":"50"},
				{"date":"8月5日","grant":"120","used":"10"},
				{"date":"8月6日","grant":"10","used":"20"},
				{"date":"8月7日","grant":"20","used":"50"}]
			},
			{"id":"2","list":[
				{"date":"9月1日","grant":"300","used":"250"},
				{"date":"9月2日","grant":"260","used":"10"},
				{"date":"9月3日","grant":"300","used":"40"},
				{"date":"9月4日","grant":"200","used":"50"},
				{"date":"9月5日","grant":"90","used":"10"},
				{"date":"9月6日","grant":"120","used":"60"},
				{"date":"9月7日","grant":"20","used":"50"}]
			},
			{"id":"3","list":[
				{"date":"10月1日","grant":"10","used":"20"},
				{"date":"10月2日","grant":"100","used":"150"},
				{"date":"10月3日","grant":"30","used":"120"},
				{"date":"10月4日","grant":"20","used":"150"},
				{"date":"10月5日","grant":"10","used":"110"},
				{"date":"10月6日","grant":"20","used":"210"},
				{"date":"10月7日","grant":"30","used":"520"}]
			}
		];

		var tabpage = 0;
		$(function(){
			//默认查看第一页
			//datalist('',7,'right');

			//重置时间
			$("#reset").click(function(){
				$("#beginTime, #endTime").val("");
			});
		})
		
		function datalist(obj,num,ward){
			
			//传入每页上限数量和页数
			// var ajaxListInfo=function(num,endpage){
			// 	$.ajax({
			// 		url:'data.json',
			// 		data:{},
			// 		type:'post',
			// 		dataType:'json',
			// 		success:function(data) {
						//异步传递总页数
						//var endpage = 3;
						// if(data.success){
							//判定切换方向
							if(ward == "left"){
								tabpage--;
							}else{
								tabpage++;
							}
							if(jsonStr.length < tabpage){
								tabpage = jsonStr.length;
								return false;
							}else if(tabpage < 1){
								tabpage = 1;
								$(obj).addClass('none');
								return false;
							}else{
								var contr = ""
								for(var i=0;i<jsonStr.length;i++){
									if(tabpage == jsonStr[i].id){
										for(var j=0;j<jsonStr[i].list.length;j++){
											contr += '<tr>' +
														'<td>' + jsonStr[i].list[j].date + '</td>' +
														'<td>' + jsonStr[i].list[j].grant + '</td>' +
														'<td>' + jsonStr[i].list[j].used + '</td>' +
													 '</tr>';
										}
									}
								}

								$('.table.information tbody').html("");
								$('.table.information tbody').append(contr);
								if(tabpage == 1 || jsonStr.length == tabpage){
									$(obj).addClass('none');
								}else{
									$(".tabinfo a").removeClass('none');
								}
								return true;
							}
			// 			}else{
			//
			// 			}    
			// 		}
			// 	});
			// }
		}
	</script>
</html>