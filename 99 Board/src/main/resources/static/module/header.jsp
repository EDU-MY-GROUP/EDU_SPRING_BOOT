<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>	

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css">

	<header>
		<div class="top-header">
	
			<!-- 미 로그인 시 처리 -->
			<sec:authorize access="isAnonymous()">
					<ul>
						<li><a href="javascript:void(0)" onclick="logout()">로그인</a></li>
						<li><a href="javascript:void(0)">고객센터</a></li>		
					</ul>		
			</sec:authorize>	 
			
			<!-- 로그인 이후 처리 -->
			<sec:authorize access="isAuthenticated()">
					<ul>
						<li><sec:authentication property='principal.member.email' /> 님환영합니다.</li>
						<li><a href="javascript:void(0)">나의정보</a></li>
						<li><a href="javascript:void(0)">고객센터</a></li>		
						<li><a href="javascript:void(0)" onclick="logout()">로그아웃</a></li>
					</ul>
			</sec:authorize> 
				

		</div>

		<%@include file="/resources/module/nav.jsp" %>
		
	</header>




	<!-- 로그아웃 폼  -->
	<form action="${pageContext.request.contextPath}/logout" method="post" name=logoutfrm>
	<input hidden name="${_csrf.parameterName }" value="${_csrf.token }" />
	</form>
		
	<script defer>
			const logout = function(){
				const logoutfrm = document.logoutfrm;
				logoutfrm.submit();
			}
	</script>
	