<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %> 
    <jsp:include page="../layout/header.jsp"></jsp:include>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
  <div class="container-fluid">
  <!-- 로고를 넣어도 됨 -->
    <a class="navbar-brand" href="/">Spring</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="nav justify-content-center">
		  <li class="nav-item">
		    <a class="nav-link active" aria-current="page" href="/">Home</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link" href="/board/list">게시판 보기</a>
		  </li>
		  
			<!--현재 인증한 사용자의 정보를 가져와서 해당 권한이 있는지 체크 -->
			<!-- 현재 사용자의 정보는 principal 에 저장되어 있따 -->
			<!-- anyMatch() : stream 매칭 메서드 (최소한 1개의 요소가 주어진 조건에 맞는지 조사)-->
			<sec:authorize access="isAuthenticated()">
			<sec:authentication property="principal.mvo.email" var="authEmail"/>
			<sec:authentication property="principal.mvo.nickName" var="authNick"/>
			<sec:authentication property="principal.mvo.authList" var="auths"/>
			
			<c:choose>
				<c:when test="${auths.stream().anyMatch(authVO -> authVO.auth.equals('ROLE_ADMIN')).get()}">
					<li class="nav-item">
		    			<a class="nav-link" href="/member/list">Member List/${authNick}(${authEmail}/ADMIN)</a>
		  			</li>
				</c:when>
				<c:otherwise>
					<li class="nav-item">
		    			<a class="nav-link" href="/member/modify">회원정보수정_${authNick}(${authEmail})</a>
		  			</li>
				</c:otherwise>
			</c:choose>
			
		  <li class="nav-item">
		    <a class="nav-link" href="/board/register">게시판 글쓰기</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link" href="" id="logoutLink">로그아웃</a>
		  </li>
		  <form action="/member/logout" method="post" id="logoutForm">
		  	<!-- 인증(로그인) 된 이메일 -->
		  	<input type="hidden" name="email" value="${authEmail}">
		  </form>
			</sec:authorize>
		  
		  <sec:authorize access="isAnonymous()">
		  <li class="nav-item">
		    <a class="nav-link" href="/member/register">회원가입</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link" href="/member/login">로그인</a>
		  </li>
		  </sec:authorize>
		  
	  </ul>
    </div>
  </div>
</nav>

<script type="text/javascript">
document.getElementById('logoutLink').addEventListener('click',(e)=>{
    e.preventDefault(); // 무력화
    document.getElementById('logoutForm').submit();
})
</script>