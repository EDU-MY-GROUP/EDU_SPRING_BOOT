<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>


<!--
link
-->
<!-- BS5 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<!-- BSICON -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
<!-- JQ -->
<script src="https://code.jquery.com/jquery-3.6.3.min.js" integrity="sha256-pvPw+upLPUjgMXY0G+8O0xUf+/Im1MZjXxxgOcBQBXU=" crossorigin="anonymous"></script>
<!-- Google Meterial Icon -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">


<!-- css -->
<link rel="stylesheet" type="text/css" href="css/login.css">
 
</head>
<body>
<section class="container">
	<div class="msg">${msg}<br>${error}</div>
	<div class="loginheader">
		<h1>로그인</h1>
	</div>
	<form:form modelAttribute="loginDto" name="loginfrm" action="${pageContext.request.contextPath}/login"  method="post">
	 
		<div class="msg"><form:errors path="email" /></div>
		<input type="text" name="username" placeholder="아이디"  value="${cookie.email.value}" class="form-control" />
		<div style="font-size:0.5rem;color:red;text-align:left;margin-bottom:0px;"><form:errors path="pwd" /></div>
		<input type="password" name="password"  placeholder="비밀번호"  class="form-control" />
		<div class="login_menu">
			<div >
				<div>
					<input type="checkbox" name="rememberId"  class="form-check-input" id="chk1" /> 
					<label for="chk1">ID 저장</label>
				</div>
				&nbsp;
				<div>
					<input type="checkbox" name="remember-me"  class="form-check-input" id="chk2" /> 
					<label for="chk2">자동 로그인</label>
				</div>
			</div>
			<div >
					<a href="/member/authentication">회원가입</a> |
					<a href="javascript:void(0)">아이디분실</a> |
					<a href="/auth/resetpwd">패스워드분실</a>
			</div>
		</div>

		<button class="btn btn-primary w-100">로그인</button>

		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }" />
	</form:form>
	
	<div id="sns_login">
		<div>
			<a href="javascript:void(0)"><img src="icon/naver_round.png" /></a><br>
			<label>네이버 로그인</label>
		</div>
		<div>
			<a href="javascript:void(0)"><img src="icon/kakao.webp" /></a><br>
			<label>카카오 로그인</label>
		</div>
		<div>
			<a href="javascript:void(0)"><img src="icon/google-icon.png"</a><br>
			<label>Google 로그인</label>
		</div>
	</div>

</section>


</body>
</html>