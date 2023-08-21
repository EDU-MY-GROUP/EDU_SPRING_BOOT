<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!--
link
-->
<%@page include file="module/link.jsp" %>


<!-- css -->
<link rel="stylesheet" type="text/css" href="css/common.css">
<link rel="stylesheet" type="text/css" href="css/main.css">
<link rel="stylesheet" type="text/css" href="css/main.css">
<link rel="stylesheet" type="text/css" href="css/footer.css">


<!-- js -->


</head>
<body>

	<!-- header -->
	<header>
		<div class="top-header">

			<!-- 미 로그인 시 처리 -->
			<sec:authorize access="isAnonymous()">
					<ul>
						<li><a href="login"  ">로그인</a></li>
						<li><a href="javascript:void(0)">고객센터</a></li>
					</ul>
			</sec:authorize>

			<!-- 로그인 이후 처리 -->
			<!--
			<sec:authorize access="isAuthenticated()">
					<ul>
						<li><sec:authentication property='principal.member.email' /> 님환영합니다.</li>
						<li><a href="javascript:void(0)">나의정보</a></li>
						<li><a href="javascript:void(0)">고객센터</a></li>
						<li><a href="javascript:void(0)" onclick="logout()">로그아웃</a></li>
					</ul>
			</sec:authorize>
			-->


		</div>
			<nav>
        		<ul>
        			<li><a href="javascript:void(0)">회사소개</a></li>
        			<li><a href="${pageContext.request.contextPath}/notice/list">공지사항</a></li>
        			<li><a href="${pageContext.request.contextPath}/board/list">자유게시판</a></li>
        			<li><a href="${pageContext.request.contextPath}/api/basic">API</a></li>
        		</ul>
        	</nav>

	</header>


	

	<section class="banner1">
	
	</section>
	
	<section class="banner2">
		<div class="items">
			<div class="item"></div>
			<div class="item"></div>
			<div class="item"></div>
			<div class="item"></div>
			<div class="item"></div>
			<div class="item"></div>
		</div>
	</section>
 
    <!-- Footer -->
    <div class="footer">
        <div class="footer-header">
        <hr>
            <ul>
                <li><a href="">회사소개</a></li>
                <li><a href="">개인정보처리방침</a></li>
                <li><a href="">이용약관</a></li>
                <li><a href="">전자금융거래이용약관</a></li>
                <li><a href="">지식재산권센터</a></li>
                <li><a href="">FAMILY SITE</a></li>
            </ul>
        <hr>
        </div>
        <div class="footer-body">
            <div>
                <div>(주) 00닷컴</div>
                <span>고객센터1577-0000</span> <button>전화문의</button> <button>1:1 고객센터톡</button>
                <div>
                    대표자: 홍길동 서울특별시 강남구 테헤란로 000 사업자등록번호: 000-00-00000 통신판매업 신고번호: 제0000-서울강남-00000호
                </div>
                <div>
                    개인정보보호책임자: 홍길동 Fax: 00-1234-1234 test@Test.com
                </div>
                <div>
                    <button>사업자정보확인</button> <button>소비자 분쟁해결 기준</button>
                </div>
            </div>

            <div>

            </div>
        </div>
    </div>
</body>
</html>