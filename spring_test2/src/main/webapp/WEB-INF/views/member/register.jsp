<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:include page="../layout/header.jsp"></jsp:include>
<jsp:include page="../layout/nav.jsp"></jsp:include>

<div class="container-md">
<h2>Member Register Page</h2>

<form action="/member/register" method="post" enctype="multipart/form-data">

		<div class="mb-3">
		  <label for="email" class="form-label">Email</label>
		  <input type="text" name="email" class="form-control" id="e" placeholder="Email...">
		</div>
		<div class="mb-3">
		  <label for="pw" class="form-label">PassWord</label>
		  <input type="password" name="pwd" class="form-control" id="p" placeholder="PW...">
		</div>
		<div class="mb-3">
		  <label for="name" class="form-label">nickName</label>
		  <input type="text" name="nickName" class="form-control" id="n" placeholder="Name...">
		</div>
		<button type="submit" class="btn btn-primary btn-sm">회원가입</button>
		
</form>

</div>


<jsp:include page="../layout/footer.jsp"></jsp:include>