<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- link -->
<%@include file="/resources/module/link.jsp" %>

<!-- css -->
<link rel="stylesheet" type="text/css" href="../resources/css/board/update.css">
 

</head>
<body>

	<%@include file="/resources/module/header.jsp" %>

	<section class="container">
		<!-- 메시지 -->
		<div class="msg">${msg}</div>
		<!-- 페이지경로표시 -->
		<div>
			<a href="${pageContext.request.contextPath}/"> <i
				class="bi bi-house-door"></i>
			</a> > BOARD > READ > UPDATE
		</div>

		<h1>자유게시판</h1>
		<p></p>
		<form action="" method="post"  onsubmit="return false" name='updatefrm'>
			<table class="table w-50">
				<tr>
					<td>Title</td>
					<td><input type="text" class="form-control" name="title" value="${boarddto.title }" /></td>
				</tr>
				<tr>
					<td>Email</td>
					<td><input type="text" class="form-control" name="email"  value="${boarddto.email}" /></td>
				</tr>				
				<tr>
					<td>Content</td>
					<td><textarea id="" cols="30" rows="10" name="content" class="form-control" >${boarddto.content }</textarea></td>
				</tr>
				<tr>
					<td>Files</td>
					<td>
						<c:set var="filenames" value="${fn:split(boarddto.filename,';')}" />
						<c:set var="filesizes" value="${fn:split(boarddto.filesize,';')}" />
			      
	 					<c:forEach var="i" begin="0" step="1" end="${fn:length(filenames)-1}" >
								<%-- <span>${filenames[i]}</span> <a href="javascript:remove('${pageContext.request.contextPath}','${filenames[i]}','${filesizes[i]}')"> <i class="bi bi-trash3"></i>  </a>	<br> --%>
						<span>${filenames[i]}</span> <a href="${pageContext.request.contextPath}/board/removefile?filename=${filenames[i]}&filesize=${filesizes[i]}'&dirpath=${boarddto.dirpath}"> <i class="bi bi-trash3"></i>  </a>	<br>
					
						</c:forEach>  
						 
					</td>
				</tr>	
				<tr>
					<td colspan=2>
						<a   class="btn btn-primary"  href="javascript:updatereq('${pageContext.request.contextPath}')">수정하기</a>
						<a class="btn btn-secondary"  href="javascript:history.go(-1)">이전으로</a>				
					</td>				 
				</tr>												
			</table>
			<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }" />
		</form>
		
	</section>

<%@include file="/resources/module/footer.jsp" %>


	<script defer>
		const updatereq =function(path){
				
			let form = document.updatefrm;
			form.action=path+"/board/update"
			form.submit();			
		}
	</script>


</body>
</html>