<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ext" value=".htm" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="resourcePath" value="${pageContext.request.contextPath}/resource/admin/web" />
<c:set var="jspPath" value="${pageContext.request.contextPath}/jsp/admin/web/fancy" />
<c:set var="picPath" value="${picUrl }" />
<!-- 图片上传地址 -->
<c:set var="uploadUrl" value="${ctx}/any/files/upload${ext}"/>
<!-- 可上传图片格式 -->
<c:set var="imageSuffix" value="jpg|png|jpeg|JPG|PNG|JPEG" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>精选消息-单图文模式</title>
		<%@include file="../../common/base.jsp" %>
<link href="${ctx}/resource/admin/css/fancy/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resource/admin/css/fancy/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resource/admin/css/fancy/main-gw.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/resource/company/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/admin/js/fancy/wechat.js"></script>
<script type="text/javascript" src="${ctx}/resource/admin/js/ueditor/ueditor.config.js"></script>
  <script type="text/javascript" charset="utf-8" src="${ctx}/resource/admin/js/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" src="${ctx}/resource/admin/js/fancy/ajaxupload3.9.js"></script>
		<script type="text/javascript" src="${jspPath}web/fancy/CenterGridFancyMessage.js" charset="UTF-8"></script>
<style type="text/css">
	body {
		width: 100%;
		heigth:130px;
	}
</style>
</head>

<body>
	<!-- header -->
	<!-- header end -->
	<!-- body -->
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-12">
					<div class="row">
						<div class="box">
							<div class="content">
								<table class="table table-thleft table-noborder">
									<tbody>
										<tr>
											<th width="10%">名称：</th>
											<td>
												<textarea id="j-name" class="c-inputbdbg" rows="2" maxlength="64">${dto.name }</textarea>
											</td>
										</tr>
										<tr data-module>
											<th width="10%">类型：</th>
											<td><select class="form-control" id="j-select2">
													<option selected="selected" value="1">单图文模式</option>
													<c:if test="${empty dto }">
													<option value="2">多图文模式</option>
													</c:if>
											</select></td>
											<input type="hidden" id="j-id" name="id" value="${dto.id }">
										</tr>
									</tbody>
								</table>

								<div class="main_bd" id="j-txtimg">
									<div class="media_preview_area">
										<div class="appmsg  editing">
											<div id="js_appmsg_preview" class="appmsg_content">
											    <div id="appmsgItem1" data-fileid="" data-id="1" class="js_appmsg_item" style="padding:0 14px;">

												<h4 class="appmsg_title j_name">
												<c:if test="${empty dto }">标题</c:if>
												<c:if test="${!empty dto }">${dto.itemList[0].name}</c:if>
												</h4>
												<div class="appmsg_info">
													<em class="appmsg_date"></em>
												</div>
												<div class="appmsg_thumb_wrp js_appmsg_thumb">
													<img class="appmsg_thumb" src="${picPath}${dto.itemList[0].picturePath}" <c:if test="${!empty dto }"> style="display:block;" </c:if> >
													<i class="appmsg_thumb default">封面图片</i>
												</div>
												<p class="appmsg_dsce j_brief">${dto.itemList[0].brief }</p>
											</div>
											</div>
										</div>
									</div>

								<div class="media_edit_area">
									<div id="js_appmsg_editor">
									<div class="appmsg_editor" style="margin-top: 0px;">
										<div class="inner">
											<input type="hidden" class="frm_input j-datawx" id="id" name="id" value="${dto.itemList[0].id }">
											<div class="appmsg_edit_item">
												<label for="" class="frm_label">标题<span class="fontcor-red">*</span>(必填,不能超过64个字)</label>
												<span>
													<!-- <input type="text" class="frm_input j-datawx" name="name" value="${dto.itemList[0].name }" maxlength="32" onkeyup="wechat.blurText(this)"> -->
													<textarea class="frm_input j-datawx c-inputbdbg" name="name" onkeyup="wechat.blurText(this)" maxlength="64">${dto.itemList[0].name }</textarea>
												</span>
											</div>
											<div class="appmsg_edit_item">
												<label for="" class="frm_label">作者(选填,不能超过8个字)</label>
												<span class="frm_input_box"><input type="text" class="frm_input j-datawx" name="author" maxlength="8" value="${dto.itemList[0].author }"></span>
											</div>
											<div class="appmsg_edit_item">
												<label for="" class="frm_label">
													<strong class="title">封面<span class="fontcor-red">*</span>(必须上传一张图片)</strong>
													<p class="js_cover_tip tips">（支持JPG、PNG格式，建议大图360*200PX，小图200*200PX）</p>
												</label>
												<div class="upload_wrap">
													<button type="button" class="btn btn-default uploadImage" name="uploadImg" value="">上传</button>
													<input type="hidden" name="picturePath" id="picturePath" class="j-datawx picturePath" value="${dto.itemList[0].picturePath}">
												</div>
											</div>
												<!-- <p><a class="js_addDesc" href="javascript:void(0);" onclick="hideOrShow(this)">添加摘要</a></p> -->
											<div class="js_desc_area appmsg_edit_item">
												<label for="" class="frm_label">摘要</label>
													<span class="frm_textarea_box"><textarea class="frm_textarea j-datawx" onkeyup="wechat.blurText(this)" name="brief" maxlength="120">${dto.itemList[0].brief }</textarea></span>
											</div>
											<div class="appmsg_edit_item">
												<label for="" class="frm_label">正文<span class="fontcor-red"></span>（默认为正文，如需绑定外部链接，请在下方类型中选择）</label>
												<div class="js-ueditor">
													<script id="editor" type="text/plain" style="height:250px;">${dto.itemList[0].content}</script>
												</div>
											</div>
											<p style="margin-bottom:5px;">
												<label for="" class="frm_label">类型</label>
												<select class="form-control input-short-5 j-linkaddr j-datawx" name="type" onchange="changeType(this)">
													<option <c:if test="${dto.itemList[0].type == 1  }"> selected="selected" </c:if> value="1">正文</option>
													<option <c:if test="${dto.itemList[0].type == 2  }"> selected="selected" </c:if> value="2">链接</option>
												</select>
											</p>
											<!-- <p style="margin-bottom:5px;">（注：选择链接至图文时，正文内容不显示）</p> -->
											<div class="js_url_area appmsg_edit_item" <c:if test="${dto.itemList[0].type != 2  }"> style="display:none" </c:if> >
												<span class="frm_input_box"><input type="text" class="js_url frm_input j-datawx" name="link" value="<c:if test="${empty dto.itemList[0].link }">http://</c:if><c:if test="${!empty dto.itemList[0].link }">${dto.itemList[0].link }</c:if>"></span>
											</div>
										</div>
										<i class="arrow arrow_out" style="margin-top: 0px;"></i>
										<i class="arrow arrow_in" style="margin-top: 0px;"></i>
									</div>
									</div>
								</div>
								<div class="clearfix"></div>
								<div class="text-center" style="border-top:1px solid #ccc;margin-top:20px;padding-top:20px;">
									<button type="button" id="previewButton" class="btn btn-default btn-demo" onclick="demo(0,this,0)" <c:if test="${dto.itemList[0].type == 2  }">style="display: none;"</c:if>>预览</button>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<button type="button" class="btn btn-default btn-demo" <c:if test="${dto.status == 1}">disabled</c:if> onclick="demo(1,this)">立即发布</button>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<button type="button" class="btn btn-default btn-demo" onclick="demo(0,this)">保存</button>
								</div>
								</div>
							</div>
						</div>
					</div>
			</div>
		</div>
	</div>
<input type="hidden" name="jsonData" id="weixinItem" value="">

	<script type="text/javascript">
	
		$(function(){
			//关注时、消息时显示，关键词时隐藏
// 			toHide();
			$("#j-select1").change(function() {
				var str = $(this).val();
				if (str == "关键词回复") {
					$("#j-keyword").removeClass("hide");
				} else {
					$("#j-keyword").addClass("hide");
				}
			});


			$("#j-select2").change(function() {
				var str = $(this).val();
				if (str == "1") {
					window.location.href="message-set1.jsp"; 
				} else if (str == "2") {
					window.location.href="message-set2.jsp"; 
				} else {
					$("#j-txt").addClass("hide");
					$("#j-txtimg").addClass("hide");
					$("#j-txtimgmore").addClass("hide");
				}
			});

			$(".j-linkaddr").change(function() {
				var str = $(this).val();
				if (str == "1") {
					$(".appmsg_editor:visible .js_url_area").hide();
				} else if (str == "2") {
					$(".appmsg_editor:visible .js_url_area").show();
				}
				});
			UE.getEditor('editor');
            wechat.delft();
            
            var button = $('.uploadImage');
			button.each(function(){
				new AjaxUpload(this, {
					action : '${uploadUrl}',
					data : {},
					onSubmit : function(file, ext) {
						var imageSuffix = new RegExp('${imageSuffix}');
						if (!(ext && imageSuffix.test(ext.toUpperCase()))) {
							art.dialog.alert("只支持上传jpg|jpeg|png格式图片");
							return false;
						}
					},
					autoSubmit : true,
					responseType : 'json',
					onChange : function(file, ext) {
					},
					onComplete : function(file, response) {
					
						if (response.flag == "SUCCESS") {
							
							$('#appmsgItem'+(+wechat.numb+1)).find('.js_appmsg_thumb img').attr("src", '${picUrl}' + response.source).show();
							$('.picturePath').val(response.path);
							
						} else {
							alert(response.info);
							return;
						}
					}
				});
			});
			})

		/*删除*/
		function del(el) {
			art.dialog.confirm('确认删除？', function() {
				$(el).parent("td").parent("tr").next().next().remove();
				$(el).parent("td").parent("tr").next().remove();
				$(el).parent("td").parent("tr").remove();
			}, function() {
				//art.dialog.tips('执行取消操作');
				return true;
			});
		}
		/*选择*/
		function change(el) {
			var val = $(el).val();
			if (val == "链接") {
				$(el).parent("td").parent("tr").next().removeClass("hide");
			} else {
				$(el).parent("td").parent("tr").next().addClass("hide");
			}

		}

		/* 点击显示隐藏*/
		function hideOrShow(thiz){
			var el = $(thiz).parent();
			el.hide().next().show();
		}

	
        function demo(status,thiz,type){
            var name = $('#j-name').val();
           	if(name == ''){
           		wechat.pageTips('请输入名称');
           		return;
           	} 
           	
           	var _url = "";
           	if ($("#j-id").val() == "") { //判断是添加还是编辑
           		_url = "${ctx}/web/fancy/add.htm";
           	} else {
           		_url = "${ctx}/web/fancy/edit.htm";
           	}
           	
            if (wechat.submitFrom()){
            	$.ajax({
                    type: "POST",
                    url: _url,
                    data: {id:$("#j-id").val(),name:$("#j-name").val(),type:$("#j-select2").val(),jsonData:$("#weixinItem").val(),status:status},
                    dataType: "json",
                    beforeSend: function () {
                   		$(".btn-demo").attr("disabled",true);
                    },
                    success: function(data){
                    	if (data.success=='undefined'||data.success==''||data.success==null||data.success) {
                    		if(type==0){
                    			window.open("${ctx}/web/fancy/previewHome.htm?id=" + data.id,"","width=450,height=900,menubar=yes");
                    			
                    			var url = ROOT_PATH + "/web/fancy/initEdit.htm?id=" + data.id
        						
                				if(window.parent.isExitsTab('fancyMessage_tab')){
                					window.parent.activateTab('fancyMessage_tab');
                					var tab = window.parent.getTab('fancyMessage_tab');
                					window.parent.frames["Frm-" + tab.id].closeFancyMessageTab('修改精选',url,'sysmenu_mag',0);
                				}else{
                					window.parent.addTabUrl('fancyMessage_tab','修改精选',url,'sysmenu_mag',0);
                				}
                    		}else{
                   				window.parent.frames["Frm-ext-comp-" + window.parent.opentabId].reloadGrid();
								window.parent.g_showTip("操作成功!");
								$(".btn-demo").removeAttr("disabled");
                   				//if(status==1){
									window.parent.closeTab('fancyMessage_tab');
                   				//}
                    		}
	                  	}
                    },
                    error:function(){
                    	$(".btn-demo").removeAttr("disabled"); 
	     				alert("系统异常");
					}
                });
            }
            console.log($('#weixinItem').val());
        }
        
        function closeFancyMessageTab(name,url,icon,type){
        	if(type==0){
        		window.parent.closeTabToAddTabUrl('fancyMessage_tab',name,url,icon);
        	}else{
	        	Ext.Msg.confirm("温馨提示", "确认要关闭此窗口?", function(msg){
					if(msg=='yes'){
		        		window.parent.closeTabToAddTabUrl('fancyMessage_tab',name,url,icon);
					}
				});
        	}
        	/*
        	art.dialog.confirm('确认要关闭此窗口吗?', function() {
        		window.parent.closeTab('fancyMessage_tab');
        		window.parent.addTabUrl('fancyMessage_tab',name,url,icon);
			}, function() {
				return true;
			});
        	*/
        }
        
        function changeType(obj){
        	if(obj.value == "1"){
        		$('#previewButton').show();
        	}else{
        		$('#previewButton').hide();
        	}
        }
       window.onload=function(){
    	  //图文模式右侧编辑
    	  var endwidth = $('.main_bd').width() - $('.media_preview_area').outerWidth(true);
    	  $('.media_edit_area').css({width : endwidth + "px"});
       }
	</script>
	</body>
</html>