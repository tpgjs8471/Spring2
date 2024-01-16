<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:include page="../layout/header.jsp"></jsp:include>
<jsp:include page="../layout/nav.jsp"></jsp:include>

<div class="container-md">
<h2>Member Modify Page</h2>

<form action="/member/modify" method="post">

		<div class="mb-3">
		  <label for="e" class="form-label">Email</label>
		  <input type="text" name="email" class="form-control" id="email" value="${mvo.email }">
		</div>
		<div class="mb-3">
		  <label for="p" class="form-label">PassWord</label>
		  <input type="password" name="pwd" class="form-control" id="p" value="">
		</div>
		<div class="mb-3">
		  <label for="n" class="form-label">nick_name</label>
		  <input type="text" name="nickName" class="form-control" id="n" value="${mvo.nickName }">
		</div>
		<button type="submit" class="btn btn-primary btn-sm">수정완료</button>
		<a href="/member/delete?email=${mvo.email}"><button type="button" class="btn btn-danger btn-sm">회원탈퇴</button></a>
		<!-- 해당 멤버의 권한 출력 (2개일수도 있음)-->
		
</form>
</div>


<jsp:include page="../layout/footer.jsp"></jsp:include>