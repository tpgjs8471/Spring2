<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %> 
    <jsp:include page="../layout/header.jsp"></jsp:include>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
  <div class="container-fluid">
  <!-- �ΰ��� �־ �� -->
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
		    <a class="nav-link" href="/board/list">�Խ��� ����</a>
		  </li>
		  
			<!--���� ������ ������� ������ �����ͼ� �ش� ������ �ִ��� üũ -->
			<!-- ���� ������� ������ principal �� ����Ǿ� �ֵ� -->
			<!-- anyMatch() : stream ��Ī �޼��� (�ּ��� 1���� ��Ұ� �־��� ���ǿ� �´��� ����)-->
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
		    			<a class="nav-link" href="/member/modify">ȸ����������_${authNick}(${authEmail})</a>
		  			</li>
				</c:otherwise>
			</c:choose>
			
		  <li class="nav-item">
		    <a class="nav-link" href="/board/register">�Խ��� �۾���</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link" href="" id="logoutLink">�α׾ƿ�</a>
		  </li>
		  <form action="/member/logout" method="post" id="logoutForm">
		  	<!-- ����(�α���) �� �̸��� -->
		  	<input type="hidden" name="email" value="${authEmail}">
		  </form>
			</sec:authorize>
		  
		  <sec:authorize access="isAnonymous()">
		  <li class="nav-item">
		    <a class="nav-link" href="/member/register">ȸ������</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link" href="/member/login">�α���</a>
		  </li>
		  </sec:authorize>
		  
	  </ul>
    </div>
  </div>
</nav>

<script type="text/javascript">
document.getElementById('logoutLink').addEventListener('click',(e)=>{
    e.preventDefault(); // ����ȭ
    document.getElementById('logoutForm').submit();
})
</script>