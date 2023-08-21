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
<link rel="stylesheet" type="text/css" href="../resources/css/board/read.css">
 

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
			</a> > BOARD > READ
		</div>

		<h1>자유게시판</h1>
		<p></p>
		
		<table class="table">
				<!-- Title / Count-->
				<tr>
					<th style="width:80px;">제목</th>
					<td style="width:480px;">${boarddto.title }</td>
					<th>조회수</th>
					<td>${boarddto.count }</td>
				</tr>
				<!-- Email / regdate -->
				<tr>
					<th>작성자</th>
					<td>${boarddto.email }</td>
					<th>등록날짜</th>
					<td>${boarddto.regdate }</td>
					
				</tr>
				<!-- Content -->
				<tr>
					<td  colspan=4 style="height:300px;overflow:auto;">${boarddto.content}</td>
				<!-- 	<td></td>
					<td></td>
					<td></td> -->
				</tr>
				<!-- Button -->
				<tr>
					<td colspan=4>
						<button class="btn btn-primary"  data-bs-toggle="modal" data-bs-target="#exampleModal">첨부파일</button>
						<a class="btn btn-primary" href="${pageContext.request.contextPath}/board/list?pageno=${pagedto.criteria.pageno}">이전으로</a>
						<a class="btn btn-primary" href="${pageContext.request.contextPath}/board/update?pageno=${pagedto.criteria.pageno}">수정하기</a>
						<a class="btn btn-primary" href="${pageContext.request.contextPath}/board/delete?pageno=${pagedto.criteria.pageno}&bno=${boarddto.no}&dirpath=${boarddto.dirpath}">삭제하기</a>
					</td>
<!-- 					<td></td>
					<td></td>
					<td></td> -->
					
				</tr>																					
		</table>
		
		<!-- Modal -->
		<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  
		  <div class="modal-dialog modal-dialog-centered">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLabel">첨부파일 리스트</h5>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		      </div>
		      
		      <div class="modal-body">
				<c:set var="filenames" value="${fn:split(boarddto.filename,';')}" />
				<c:set var="filesizes" value="${fn:split(boarddto.filesize,';')}" />
			      
	 			<c:forEach var="i" begin="0" step="1" end="${fn:length(filenames)-1}" >
					
						<a href="${pageContext.request.contextPath}/board/download?uuid=${boarddto.dirpath}&filename=${filenames[i]}">${filenames[i]} (${filesizes[i]}Byte)</a><br>
					
				</c:forEach>   	     
			    

		      </div>
		      <div class="modal-footer">
		       	<a type="button" class="btn btn-primary" href="${pageContext.request.contextPath}/board/downloadzip?uuid=${boarddto.dirpath}">ZIP 받기</a>
		        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
		 
		      </div>
		    </div>
		  </div>
		  
		</div>
	


	
		<!-- REPLY -->
		<style>
		/* 스크롤바 표시는 x 기능은 o */
			*::-webkit-scrollbar{
			  display: none; /* Chrome, Safari, Opera*/
			}
		</style>
		
		
		<div class='reply-header' style="margin-top:20px;font-size:1.3rem;">
			<span >댓글</span>&nbsp;&nbsp;<span id=replycnt></span>
		</div>
		<div class='reply-input' style="position:relative;width:100%;">
			<form action="" style="display:flex;" onsubmit="return false">	 
				<input type="text" id="comment"  style="width:100%;outline:none;border:0px;" placeholder="댓글입력.." />
				<button class="btn btn-primary" onclick="postreply('${pageContext.request.contextPath}')">전송</button>
				<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }" />
			</form>
		</div>
		<div class='reply-body' style=" height:300px; overflow:auto;" >
			<!--

			 -->
		</div>

	</section>


<%@include file="/resources/module/footer.jsp" %>


	<script defer>
		const postreply=function(path){
		
			$.ajax({
				url: path+"/board/replypost",
				type : "GET",
				dataType : 'html',
				data :{"bno":${boarddto.no},"comment":$('#comment').val() },
				success:function(result){			
					$('#comment').val('');			
					$('.reply-body *').remove();
					showreply(path);
				},
				error:function(){
					alert('error');
				}
			})
			
		}
		
		const showreply=function(path){
			
			$.ajax({
				url: path+"/board/replylist",
				type : "GET",
				dataType : 'html',
				data :{"bno":${boarddto.no} },
				success:function(result){
				 
					 $('.reply-body').html(result);
					 replycnt(path);
				},
				error:function(){
					alert('error');
				}
			})
			
		}
		
		showreply('${pageContext.request.contextPath}');	

		const replycnt=function(path){
			
			$.ajax({
				url: path+"/board/replycnt",
				type : "GET",
				dataType : 'html',
				data :{"bno":${boarddto.no} },
				success:function(result){
					 $('#replycnt').html(result);
				},
				error:function(){
					alert('error');
				}
			})
			
			
		}
		
	</script>
	

<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
 <script>
		Kakao.init('ca12c0d4f6ea096e99d25d2d2c9b7c77'); //발급받은 키 중 javascript키를 사용해준다.
		console.log(Kakao.isInitialized()); // sdk초기화여부판단

		//카카오로그아웃  
		function kakaoLogout(path) {
			if (Kakao.Auth.getAccessToken()) {
				Kakao.API.request({
					url : '/v1/user/unlink',
					success : function(response) {
						console.log("RESPONSE : " + response)
						location.href = path+"/auth/logout"
					},
					fail : function(error) {
						console.log(error)

					},
				})
				Kakao.Auth.setAccessToken(undefined)
			}
		}
 </script>
</body>
</html>