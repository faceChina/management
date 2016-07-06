<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="UTF-8" />
		<title>脸谱中国-登录页面</title>
		<%@include file="../../common/base.jsp" %>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resource/company/css/base.css">
		<style>
			body{
				width:100%;
				background:#ebebeb;
			}
			.login{
				width:1000px;
				margin:0 auto;
			}
			.login-head{
				margin-top:20px;
			}
			.login-head h2{
				line-height:76px;
			}
			.login-head h2 span{
				color:#ccc;
			}
			.login-center{
				margin-top:175px;
			}
			.login-center .right{
				width:450px;
				height:332px;
			 	border:1px solid #ccc;
				border-top:none;
				border-radius:10px;
				box-shadow:0px 4px 3px #ccc; 
			}
			.login-center .right h3{
				height:73px;
				padding-left:25px;
				background:#f8f8f8;
				border-top-left-radius:10px;
				border-top-right-radius:10px;
			 	border-bottom:1px solid #ccc; 
				line-height:73px;
				color:#cc0000;
			}
			.login-center .right .login-field{
				width:390px;
				margin:0 auto;
			}
			.login-center .right .login-field input{
				width:375px;
				height:50px;
				margin-top:20px;
				padding-left:15px;
				border-radius:6px;
				border:1px solid #ccc;
				color:#999;
			}
			.login-center .right .login-field button{
				width:390px;
				height:50px;
				margin-top:20px;
				background:#db0000;
				border:none;
				font-size:20px;
				font-weight:bold;
				color:#fff;
				cursor:pointer;
			} 
			.footerBox{
				position:fixed;
				bottom:0;
			}
		</style>
		<script language="javascript"> 
			if (window != top){
				top.location.href = location.href; 
			}
		</script>
		<script>
			function loginSub(){
				var userName = document.getElementById("loginName").value;
				var userPwd = document.getElementById("passWord").value;
				if(userName==""){
					alert("请输入用户名！");
					return false;
				}
				if(userPwd==""){
					alert("请输入密码！");
					return false;
				}
				$.ajax({ 
					type:"POST", 
					async:false, 
					url:ROOT_PATH+'/admin/index/login.htm?loginName='+userName+'&passWrod='+userPwd,
					success:function(result){
						var data = JSON.parse(result);
					 	if(!data.success){
					 		alert(data.info);
					 	}else{
					  	window.location.href=ROOT_PATH+'/jsp/admin/sys/main/main.jsp';
					 	}
				   	},
			   		error:function(){
	     				alert("登录异常");
					}	
				});
			}
			document.onkeydown=function(e){
		  		if(!e)e=window.event;
		  		if((e.keyCode||e.which)==13){
		    		loginSub();
		  		}
			}
			
		</script>
	</head>
	<body>
		<!--   <div id="login-top"> -->
		<!--     <h1></h1> -->
		<!--   <div id="login-content"> -->
		<!--     <form> -->
		<!--       <div class="notification information png_bg"> -->
		<!--         <div> Welcome to 管家国际后台管理系统. </div> -->
		<!--       </div> -->
		<!--       <p> -->
		<!--         <label>帐 号 ：</label> -->
		<!--         <input class="text-input" type="text" id="loginName"/> -->
		<!--       </p> -->
		<!--       <div class="clear"></div> -->
		<!--       <p> -->
		<!--         <label>密 码 ：</label> -->
		<!--         <input class="text-input" type="password" id="passWord" /> -->
		<!--       </p> -->
		<!--       <div class="clear"></div> -->
		<!--       <p> -->
		<!--            <input class="button" type="reset" value="重置"/> -->
		<!--          <input class="button" type="button" value="登 录" onclick="loginSub()"/> -->
		<!--          </p> -->
		<!--     </form> -->
		<!--   </div> -->
		<!-- </div> -->
		
		<div class="login">
			<div class="login-head">
				<img src="${resourcePath}img/logo.png" alt="logo" class="left"></img>
				<h2><span> | </span>运营后台管理中心</h2>
			</div>
			<div class="login-center">
				<img src="${resourcePath}img/login.jpg" alt="" class="left"></img>
				<div class="right">
					<h3>欢迎登录</h3>
					<div class="login-field">
						<input type="text"  id="loginName" placeholder="用户名" value=""/>
						<input type="password" id="passWord"  placeholder="密码" value=""/>
						<button type="button" onclick="loginSub()">登录</button>
					</div>
				</div>
			</div>
		</div>
		<!-- footer -->
		<script type="text/javascript" src="../public/footer.js"></script>
		<!-- footer end -->
	</body>
</html>
