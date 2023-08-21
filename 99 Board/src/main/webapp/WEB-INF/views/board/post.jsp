<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- link -->
<%@include file="/resources/module/link.jsp" %>

<!-- css -->
<link rel="stylesheet" type="text/css" href="../resources/css/board/post.css">

</head>
<body>

	<!-- 헤더  -->
	<%@include file="/resources/module/header.jsp" %>



	<section class="container">
		<!-- 메시지 -->
		<div class="msg">${msg}</div>
		<!-- 페이지경로표시 -->
		<div>
			<a href="${pageContext.request.contextPath}/"> <i
				class="bi bi-house-door"></i>
			</a> > BOARD > POST
		</div>

		<h1>자유게시판</h1>
		<p></p>
		<form action="${pageContext.request.contextPath}/board/post?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data">
			<table class="table w-50">
				<tr>
					<td>Title</td>
					<td><input type="text" class="form-control" name="title" /></td>
				</tr>
				<tr>
					<td>Email</td>
					<td><input type="text" class="form-control" name="email"  value=<sec:authentication property='principal.member.email' /> /></td>
				</tr>				
				<tr>
					<td>Content</td>
					<td><textarea id="" cols="30" rows="10" name="content" class="form-control" ></textarea></td>
				</tr>
				<tr>
					<td>Files</td>
					<td><input type="file" class="form-control" name="files"   multiple /></td>
				</tr>	
				<tr>
					<td colspan=2>
						<input type="submit" value="전송" class="btn btn-primary" />
						<input type="reset" value="초기화" class="btn btn-secondary" />					
					</td>				 
				</tr>												
			</table>
			<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }" />
		</form>
		
	</section>


<%@include file="/resources/module/footer.jsp" %>


</body>
</html>