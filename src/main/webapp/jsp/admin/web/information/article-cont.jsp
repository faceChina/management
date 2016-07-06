<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ext" value=".htm" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="resourcePath" value="${pageContext.request.contextPath}/resource/admin" />
<c:set var="jspPath" value="${pageContext.request.contextPath}/jsp/admin/web/information" />
<c:set var="picPath" value="${picUrl }" />
<!-- 图片上传地址 -->
<c:set var="uploadUrl" value="${ctx}/any/files/upload${ext}"/>
<!-- 可上传图片格式 -->
<c:set var="imageSuffix" value="jpg|png|jpeg|JPG|PNG|JPEG" />

<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="UTF-8" />
		<%@include file="../../common/base.jsp" %>
		<title>新增资讯-发布内容</title>	
		<!-- top -->
		<link href="${ctx}/resource/admin/css/fancy/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/resource/admin/css/fancy/main.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/resource/admin/css/fancy/main-gw.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${ctx}/resource/company/js/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="${ctx}/resource/admin/js/fancy/wechat.js"></script>
		<script type="text/javascript" src="${ctx}/resource/admin/js/ueditor/ueditor.config.js"></script>
	  	<script type="text/javascript" charset="utf-8" src="${ctx}/resource/admin/js/ueditor/ueditor.all.min.js"> </script>
		<script type="text/javascript" src="${ctx}/resource/admin/js/ueditor/lang/zh-cn/zh-cn.js"></script>
		<script type="text/javascript" src="${ctx}/resource/admin/js/fancy/ajaxupload3.9.js"></script>
		<script type="text/javascript" src="${ctx}/resource/company/js/jquery.validate.js"></script>
		<!--top end -->
	</head>
	<body>
		<!-- body  -->
		<div class="container" id="j-content">
			<div class="row">
				<div class="col-md-10">
					<div class="row">
						<div class="box">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active">
										<a href="#members-set" data-toggle="tab">
											<c:choose>
												<c:when test="${gwInformation.id == null}">
													新增资讯
												</c:when>
												<c:otherwise>
													修改资讯
												</c:otherwise>
											</c:choose>
										</a>
									</li>
								</ul>
							</div>
							<div class="content">
								<form class="form-horizontal" id="j-form">
									<div class="form-group">
										<label for="inputText" class="col-md-2 width70 control-label">标题 <b class="clr-attention">*</b></label>
										<div class="col-md-7">
											<input type="text" class="form-control" placeholder="" name="title" id="title" value="${gwInformation.title }">
											<input type="hidden" name="id" id="id" value="${gwInformation.id }"> 
										</div>
									</div>
									<div class="form-group">
										<label for="inputText" class="col-md-2 width70 control-label">作者</label>
										<div class="col-md-7">
											<input type="text" class="form-control" placeholder="" name="announcer" id="announcer" value="${gwInformation.announcer }">
										</div>
									</div>
									<div class="form-group">
										<label for="inputText" class="col-md-2 width70 control-label">来源</label>
										<div class="col-md-7">
											<input type="text" class="form-control" placeholder="" name="source" id="source" value="${gwInformation.source }">
										</div>
									</div>
									
									<div class="form-group">
										<label for="inputText" class="col-md-2 width70 control-label">文章配图</label>
										<div class="col-md-3">
											<img src=
											<c:choose>
												<c:when test="${gwInformation.picPath==null || gwInformation.picPath==''}">
													"${resourcePath }/img/fancy/img-default.jpg"
												</c:when>
												<c:otherwise>
													"${picPath }/${gwInformation.picPath }"
												</c:otherwise>
											</c:choose> 
												 width="163" height="80" name="img" id="img">
											<input type="hidden" name="picturePath" id="picturePath" class="j-datawx picturePath" value="${gwInformation.picPath }"> 
										</div>
										<div class="col-md-3">
											<!--
											<button type="button" class="btn btn-default"></button>
											-->
											<div class="uploadImg" style="display:block;">
												<div class="btn btn-default btn-upload">
													选择封面<input type="file" value="" name="uploadimg" id="uploadimg" class="form-control" data-tips="请上传尺寸大小为 **** 像素的图片，以png格式为准。">
												</div>
												<div class="btn btn-default delete">
													删除
												</div>
											</div>
											<p class="help-block">推荐大小为407×200</p>
										</div>
									</div>
									<div class="form-group">
										<label for="inputText" class="col-md-2 width70 control-label">文章摘要<b class="clr-attention">*</b></label>
										<div class="col-md-7">
											<textarea class="form-control" rows="3" name="summary" id="summary" onkeyup="toLimitString(this,60)">${gwInformation.summary }</textarea>
										</div>
										<div class="col-md-3 help-block">
											  限制<span class="fontcor-red">60</span>字内
										</div>
									</div>

									<div class="form-group">
										<label for="inputText" class="col-md-2 width70 control-label">文章内容<b class="clr-attention">*</b></label>
										<div class=" col-md-10" >
											<script id="editor" name="editor" type="text/javascript" style="height:510px;" src="${resourcePath }/js/ueditor/ueditor.js">${gwInformation.content }</script>
										</div>
									</div>
									<div class="form-group">
										<label for="inputText" class="col-md-2 width70 control-label">发布方式</label>
											<div class="col-md-3">
												 <select class="form-control" name="status" id="status">
												  	<option value="1" <c:if test="${gwInformation.status==1 }">selected="selected"</c:if>>显示</option>
												  	<option value="0" <c:if test="${gwInformation.status==0 }">selected="selected"</c:if>>隐藏</option>
												  </select>
											</div>
									</div>

									<div class="other-set"><b>SEO设置</b></div>
									<br/>

									<div class="form-group">
										<label for="inputText" class="col-md-3 control-label">SEO TITLE：</label>
										<div class="col-md-6">
											  <input type="text" class="form-control" placeholder="" name="seoTitle" id="seoTitle" value="${gwInformation.seoTitle }">
										</div>
									</div>

									<div class="form-group">
										<label for="inputText" class="col-md-3 control-label">SEO KEY WORDS：</label>
										<div class="col-md-6">
											   <input type="text" class="form-control" placeholder="" name="seoKeywords" id="seoKeywords" value="${gwInformation.seoKeywords }">
										</div>
									</div>

									<div class="form-group">
										<label for="inputText" class="col-md-3 control-label">SEO DESCRIPTION：</label>
										<div class="col-md-6">
											  <textarea class="form-control" rows="3" name="seoDescription" id="seoDescription" onkeyup="toLimitString(this,60)">${gwInformation.seoDescription }</textarea>
										</div>
									</div>
	
									<div class="text-center">
										<button type="submit" name="" class="btn btn-default btn-demo" onclick="toSubmit()">保存</button>
										<button type="submit" name="" id="" class="btn btn-default btn-demo" onclick="toReset()">取消</button>
									</div>
								</form>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- body end -->
		
		<script type="text/javascript">
			$(function(){
				var button = $('#uploadimg');
				button.each(function(){
					new AjaxUpload(this, {
						action : '${uploadUrl}',
						data : {},
						onSubmit : function(file, ext) {
							var imageSuffix = new RegExp('${imageSuffix}');
							if (!(ext && imageSuffix.test(ext.toUpperCase()))) {
								window.parent.g_showTip("只支持上传jpg|jpeg|png格式图片");
								return false;
							}
						},
						autoSubmit : true,
						responseType : 'json',
						onChange : function(file, ext) {
						},
						onComplete : function(file, response) {
							if (response.flag == "SUCCESS") {
								$('#img').attr("src", '${picUrl}' + response.source).show();
								$('#picturePath').val(response.path);
							} else {
								window.parent.g_showTip(response.info);
								return;
							}
						}
					});
				});
				
				$('.delete').click(function(){
					$('#img').attr('src','${resourcePath }/img/fancy/img-default.jpg');
					$('#picturePath').val('');
				});
			});
			
			//获取数据
			function getData(){
				var content = UE.getEditor('editor').getContent();
				
				if(content==''){
					window.parent.g_showTip('请输入文章内容');
					return null;
				}
				
				var title = $("#title").val();
				var announcer = $("#announcer").val();
				var source = $("#source").val();
				var picPath = $("#picturePath").val();
				var summary = $("#summary").val();
				var status = $("#status").val();
				var seoTitle = $("#seoTitle").val();
				var seoKeywords = $("#seoKeywords").val();
				var seoDescription = $("#seoDescription").val();
				
				var arr = {'title':title,'announcer':announcer,'source':source,'picPath':picPath,'summary':summary,'status':status,
												'seoTitle':seoTitle,'seoKeywords':seoKeywords,'seoDescription':seoDescription,'content':content};
				return arr;
			} 
			//发布验证
			function toSubmit(){
				var bool = $("#j-form").validate({
					rules:{
						title:"required",
						summary:"required"
					},
					messages:{
						title:"请输入文章标题",
						summary:"请输入文章摘要"
					},
					errorPlacement:function(error,element){
						error.appendTo($(element).parents(".col-md-7"));
					},
					debug:true
				}).form();
				
				if(bool){
					var arr = getData();
					
					if(arr==null){
						return;
					}
					
					var id = $('#id').val();
					
					var url = '${ctx}/web/information/add${ext}';	
					
					if(id!='' && id!='undefined' && id!=null){
						url = '${ctx}/web/information/edit${ext}';
						arr.id = id;
					}
					
					$.ajax({
	                    type: "POST",
	                    url: url,
	                    data: {'jsonData':JSON.stringify(arr)},
	                    dataType: "json",
	                    beforeSend: function () {
	                   		$(".btn-demo").attr("disabled",true);
	                    },
	                    success: function(data){
	                    	if (data.success) {
                   				window.parent.frames["Frm-ext-comp-" + window.parent.opentabId].reloadGrid();
								window.parent.g_showTip("操作成功!");
								$(".btn-demo").removeAttr("disabled");
								window.parent.closeTab('information_tab');
		                  	}else{
		                  		$(".btn-demo").removeAttr("disabled");
		                  		window.parent.g_showTip(data.resultDesc);
		                  	}
	                    },
	                    error:function(){
	                    	$(".btn-demo").removeAttr("disabled"); 
	                    	window.parent.g_showTip("系统异常");
						}
	                });
				}
			}
			
			//取消
			function toReset(){
				window.parent.closeTab('information_tab');
			}
			
			//显示隐藏
			function toShow(){
				$("#artical-cont1").css("display", "block");
				$("#artical-cont2").css("display", "none");
			}
			function toHide(){
				$("#artical-cont1").css("display", "none");
				$("#artical-cont2").css("display", "block");
			}
			
			//限字
			function toLimitString(thiz,num){
				var str = $(thiz).val();
				var len = str.length;
				if(len >= num){
					$(thiz).val(str.substring(0,num));
				}
			}
			
			function closeInformationTab(name,url,icon,type){
	        	if(type==0){
	        		window.parent.closeTabToAddTabUrl('information_tab',name,url,icon);
	        	}else{
		        	Ext.Msg.confirm("温馨提示", "确认要关闭此窗口?", function(msg){
						if(msg=='yes'){
			        		window.parent.closeTabToAddTabUrl('information_tab',name,url,icon);
						}
					});
	        	}
	        }
		</script>
	</body>
</html>