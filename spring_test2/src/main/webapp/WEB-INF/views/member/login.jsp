<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:include page="../layout/header.jsp"></jsp:include>
<jsp:include page="../layout/nav.jsp"></jsp:include>


<div class="container-md">

<h2>Member Login Page</h2> <br>

	<form action="/member/login" method="post">
		<div class="mb-3">
		  <label for="id" class="form-label">ID</label>
		  <input type="text" name="email" class="form-control" id="e" placeholder="ID...">
		</div>
		<div class="mb-3">
		  <label for="pw" class="form-label">PW</label>
		  <input type="password" name="pwd" class="form-control" id="p" placeholder="PW...">
		</div>
		<button type="submit" class="btn btn-primary btn-sm">확인</button>
	</form>
	<!-- not empty errMsg / errMsg 가 채워져 있다면~ -->
<c:if test="${not empty param.errMsg }">
	<div class="mb-3">
		<c:choose>
			<c:when test="${param.errMsg eq 'Bad credentials' }">
				<c:set value="이메일 & 비밀번호가 일치하지 않습니다" var="errText"></c:set>
			</c:when>
			<c:otherwise>
				<c:set value="오류가 발생하였습니다. 관리자에게 문의해주세요" var="errText"></c:set>
			</c:otherwise>
		</c:choose>
		${errText}
	</div>
</c:if>
	
</div>
<jsp:include page="../layout/footer.jsp"></jsp:include>