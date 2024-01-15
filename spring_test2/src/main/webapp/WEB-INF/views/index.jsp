<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="layout/header.jsp"></jsp:include>
<jsp:include page="layout/nav.jsp"></jsp:include>
<div class="container my-3">
	<P>  The time on the server is ${serverTime}. </P>	
</div>
<a href="/board/register"><button>Register Board</button></a>


<jsp:include page="layout/footer.jsp"></jsp:include>