<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>	

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>


<!-- link -->
<%@include file="/resources/module/link.jsp" %>
<!-- css -->
<link rel="stylesheet" type="text/css" href="../resources/css/board/list.css">

</head>
<body>
	<!-- header -->
	<%@include file="/resources/module/header.jsp" %>



	<section class="container">
		<!-- 메시지 -->
		<div class="msg">${msg}</div>
		<!-- 페이지경로표시 -->
		<div>
			<a href="/"> <i
				class="bi bi-house-door"></i>
			</a> > BOARD
		</div>
		
		<h1>자유게시판</h1>
		<p>Page No : ( <span style="color:red;">${pagedto.criteria.pageno} </span> / ${pagedto.totalpage} )</p>
		<table class="table">
			<thead>
				<tr>
					<th>NO</th>
					<th>제목</th>
					<th>작성자</th>
					<th>날짜</th>
					<th>조회수</th>
				</tr>
			</thead>
			<tbody>
			
			<c:forEach var="dto" items="${list}" varStatus="status" >
			 	<tr>
					<td>${dto.no}</td>
					<td><a href="/board/read?bno=${dto.no}&pageno=${pagedto.criteria.pageno}">${dto.title}</a> </td>
					<td>${dto.email }</td>
					<td>${dto.regdate }</td>
					<td>${dto.count }</td>
				</tr>	
			</c:forEach>
				
			</tbody>
			<tfoot>
				
				<tr>
					<!-- 페이지네이션 -->
					<td colspan="3">
						<nav aria-label="Page navigation example" >
						  <ul class="pagination"  style="height:30px;">
						  
						  <!-- PREV 버튼 -->
						  <c:if test="${pagedto.prev}">
							 	 <li class="page-item">
							      <a class="page-link" href="/board/list?pageno=${pagedto.nowBlock * pagedto.pagePerBlock - pagedto.pagePerBlock*2 + 1}" aria-label="Previous">
							        <span aria-hidden="true">&laquo;</span>
							      </a>
							    </li> 
						  </c:if>

						    
						    <!-- 페이지번호 -->
						    <c:forEach begin="${pagedto.startPage}" end="${pagedto.endPage }" var="pageno" step="1">
							    <li class="page-item"><a class="page-link" href="/board/list?pageno=${pageno}">${pageno}</a></li>
						    </c:forEach>
						    
						

						    
						    <!-- NEXT버튼 -->
						    <c:if test="${pagedto.next}">
							    <li class="page-item">
							      <a class="page-link" href="/board/list?pageno=${pagedto.nowBlock*pagedto.pagePerBlock+1}" aria-label="Next">
							        <span aria-hidden="true">&raquo;</span>
							      </a>
							    </li>
						     </c:if>
						     
						  </ul>
						</nav>
					</td>
					<!-- 글쓰기/처음으로 -->
					<td colspan="2" style="text-align:right;">
						<a href="/board/post" class="btn btn-danger">글쓰기</a>
						<a href="/board/list" class="btn btn-success">처음으로</a>
					</td>
					 
				</tr>
			</tfoot>	
		</table>
		
		
	
	</section>


<%@include file="/resources/module/footer.jsp" %>




</body>
</html>