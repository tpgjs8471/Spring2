<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %> 
<jsp:include page="../layout/header.jsp"></jsp:include>
<jsp:include page="../layout/nav.jsp"></jsp:include>

	<div class="container-sm">
	<sec:authentication property="principal.mvo.email" var="authEmail" />
	<sec:authentication property="principal.mvo.nickName" var="authNick"/>
		<form action="/board/register" method="post" enctype="multipart/form-data">
	
			<div class="mb-3">
			  <label for="title" class="form-label">title</label>
			  <input type="text" name="title" class="form-control" id="title" placeholder="title...">
			</div>
			<div class="mb-3">
			  <label for="writer" class="form-label">Writer</label>
			  <input type="text" readonly="readonly" name="writer" class="form-control" id="writer" value="${authNick}">
			</div>
			<div class="mb-3">
			  <label for="content" class="form-label">Content</label>
			  <textarea class="form-control" id="content" name="content" rows="3"></textarea>
			</div>
			<!-- file upload 추가 -->
			<div class="mb-3">
			  <label for="file" class="form-label">Files...</label>
			  <input type="file" id="files" name="files" multiple="multiple" style="display: none">	<br>	  
			  <!-- 파일 버튼 트리거 사용하기 위해서 주는 버튼 -->
			  <button type="button" id="trigger" class="btn btn-primary btn-sm">업로드</button>
			</div>
			<div class="mb-3" id="fileZone">
				<!-- 파일 목룍 표시 라인 -->		
			</div>
			
			<button type="submit" class="btn btn-primary btn-sm" id="regBtn">등록</button>
		</form>
	</div>

<script src="/resources/js/boardRegister.js"></script>
<jsp:include page="../layout/footer.jsp"></jsp:include>