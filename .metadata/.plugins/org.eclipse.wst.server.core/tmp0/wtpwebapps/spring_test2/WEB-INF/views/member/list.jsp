<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:include page="../layout/header.jsp"></jsp:include>
<jsp:include page="../layout/nav.jsp"></jsp:include>
<div class="d-flex justify-content-center">
	<div  class="container">
	<div class="class=d-flex flex-column mb-3 ">
<h1>회원 리스트</h1>
<table class="table table-striped">
	<tr>
		<th>E-mail</th>
		<th>NickName</th>
		<th>Reg-Date</th>
		<th>Last-login</th>
		<th>Auth</th>
	</tr>
	<!-- var => 각 반복 동안 컬렉션의 각 항목을 나타내는 변수를 선언 -->
	<c:forEach items="${memlist}" var="mvo">
		<tr>
			<td>${mvo.email }</td>
			<td>${mvo.nickName}</td>
			<td>${mvo.regAt}</td>
			<td>${mvo.lastLogin }</td>
			<td>
				<c:forEach items="${mvo.authList}" var="authList">
					${authList.auth }
				</c:forEach>
			</td>
		</tr>
	</c:forEach>
</table>
</div>
</div>
</div>

<jsp:include page="../layout/footer.jsp"></jsp:include>