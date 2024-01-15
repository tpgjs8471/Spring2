<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:include page="../layout/header.jsp"></jsp:include>
<jsp:include page="../layout/nav.jsp"></jsp:include>


<div class="container-md">
<h2>Board Modify</h2> <br>
<c:set value="${bdto.bvo}" var="bvo"></c:set>
<form action="/board/modify" method="post" enctype="multipart/form-data">

		<div class="mb-3">
		  <label for="bno" class="form-label">bno</label>
		  <input type="text" name="bno" class="form-control" id="bno" value="${bvo.bno }" readonly="readonly">
		</div>
		<div class="mb-3">
		  <label for="title" class="form-label">title</label>
		  <input type="text" name="title" class="form-control" id="title" value="${bvo.title }">
		</div>
		<div class="mb-3">
		  <label for="writer" class="form-label">Writer</label>
		  <input type="text" name="writer" class="form-control" id="writer" value="${bvo.writer }" readonly="readonly">
		</div>
<%-- 		<div class="mb-3">
		  <label for="reg_date" class="form-label">reg_date</label>
		<span class="badge text-bg-primary">${bvo.readCount}</span>
		  <input type="text" name="reg_date" class="form-control" id="reg_date" value="${bvo.regAt }" readonly="readonly">
		</div> --%>
		<div class="mb-3">
		  <label for="content" class="form-label">Content</label>
		  <textarea class="form-control" id="content" name="content" rows="3">${bvo.content }</textarea>
		</div>
		
		<!-- 파일 표시 라인 -->
<c:set value="${bdto.flist}" var="flist"></c:set>
		<div class="mb-3">
		  <label for="f" class="form-label">File</label>
		  <ul class="list-group list-group-flush">
		  	<c:forEach items="${flist}" var="fvo">
		  		<li class="list-group-item">
		  			<c:choose>
		  				<c:when test="${fvo.fileType == 1}">
		  					<div>
		  						<img alt="" src="/fileupload/${fvo.saveDir}/${fvo.uuid}_th_${fvo.fileName}">
		  					</div>
		  				</c:when>
		  				<c:otherwise>
			  				<div>
			  					<i class="bi bi-upload"></i>
			  				</div>
		  				</c:otherwise>
		  			</c:choose>
		  			<div class="ms-2 me-auto">
		  				<div class="fw-bold">${fvo.fileName}</div>
		  				<span class="badge rounded-pill text-bg-primary">${fvo.fileSize }Byte</span>
		  				<button type="button" data-uuid="${fvo.uuid}" class="btn btn-danger btn-sm file-x">X</button>
		  			</div>
		  		</li>
		  	</c:forEach>
		  </ul>
		  </div>
		  
		  <!-- 파일 추가 등록 라인 -->
			<div class="mb-3">
			  <input type="file" id="files" name="files" multiple="multiple" id="fileUpload" style="display: none">	<br>	  
			  <!-- 파일 버튼 트리거 사용하기 위해서 주는 버튼 -->
			  <button type="button" id="trigger" class="btn btn-primary btn-sm">업로드</button>
			</div>
			<!-- 파일 목룍 표시 라인 -->
		<div class="mb-3" id="fileZone">
		
		</div>
		<button type="submit" class="btn btn-success" id="regBtn">수정완료</button>
		<a href="/board/list"><button type="button" class="btn btn-primary">게시판</button></a>
</form>
</div>

<script src="/resources/js/boardModify.js"></script>
<script src="/resources/js/boardRegister.js"></script>
<jsp:include page="../layout/footer.jsp"></jsp:include>