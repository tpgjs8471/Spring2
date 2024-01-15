<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../layout/header.jsp"></jsp:include>
<jsp:include page="../layout/nav.jsp"></jsp:include>


<div class="container-md">
<h1>Board List Page</h1>
<br>
<!-- search 검색 라인 -->
	<div>
		<form action="/board/list" method="get">
			<!-- 검색어를 보낼때 페이징도 같이 넣어서 보내야함 -->
			<!-- selected = "selected" 면 selected 만 써도 됨 -->
			<div class="input-group mb-3">
			<c:set value="${ph.pgvo.type}" var="typed"></c:set>
				<select name="type">
						<option ${ph.pgvo.type==null ? 'selected' : '' }>Choose....</option>
						<option value="t" ${typed eq 't' ? 'selected' : '' }> Title </option>
						<option value="w" ${typed eq 'w' ? 'selected' : '' }> Writer </option>
						<option value="c" ${typed eq 'c' ? 'selected' : '' }> Content </option>
						<option value="tc" ${typed eq 'tc' ? 'selected' : '' }> Title&Content </option>
						<option value="tw" ${typed eq 'tw' ? 'selected' : '' }> Title&Writer </option>
						<option value="twc" ${typed eq 'twc' ? 'selected' : '' }> All </option>
				</select>
				<input type="text" class="form-control" name="keyword" value="${ph.pgvo.keyword }" placeholder="Search....">
				<input type="hidden" name="pageNo" value="1">
				<input type="hidden" name="qty" value="${ph.pgvo.qty}">
				<button type="submit" class="btn btn-success position-relative">Search
					<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
						<c:choose>
							<c:when test="${ph.totalCount > 100}">99+</c:when>
							<c:when test="${ph.totalCount < 100}">${ph.totalCount}</c:when>
						</c:choose>
					</span>
				</button>			
			</div>
		</form>
	</div>	
	<table class="table">
		  <thead>
		    <tr>
		      <th scope="col">#</th>
		      <th scope="col">title</th>
		      <th scope="col">writer</th>
		      <th scope="col">readCount</th>
		      <th scope="col">comment_qty</th>
		      <th scope="col">file_qty</th>
		      <th scope="col">modDate</th>
		    </tr>
		  </thead>
		<tbody>
		
			 <c:forEach items="${list}" var="bvo">
			   <tr>
			     <th scope="row">${bvo.bno }</th>
			     <td><a href="/board/detail?bno=${bvo.bno}">${bvo.title}</a></td>
			     <td>${bvo.writer}</td>
			     <td>${bvo.readCount}</td>
			     <td>${bvo.cmtQty}</td>
			     <td>${bvo.hasFile}</td>
			     <td>${bvo.modDate}</td>
			   </tr>
			 </c:forEach>
			 
		</tbody>
	</table>
	
	<!-- 페이징 라인 -->
	<nav aria-label="Page navigation example">
	  <ul class="pagination">
	    <!-- 이전 -->
	    <!-- 
	    ph. prev eq false ? 'disabled' : ''
	     -->
	    <c:if test="${ph.prev }">
		    <li class="page-item">
		      <a class="page-link" href="/board/list?pageNo=${ph.startPage-1}&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}"
		      	aria-label="Previous">
		        <span aria-hidden="true">&laquo;</span>
		      </a>
		    </li>
	    </c:if>
	    
	    <!-- 페이지번호 -->
	    <c:forEach begin="${ph.startPage }" end="${ph.endPage }" var="i">
	    	<li class="page-item">
	    		<a class="page-link" href="/board/list?pageNo=${i}&qty=${ph.pgvo.qty}">${i}</a>
	    	</li>
	    </c:forEach>
	    
	    <!-- 다음 -->
	    <c:if test="${ph.next }">
		    <li class="page-item">
		      <a class="page-link" href="/board/list?pageNo=${ph.endPage+1}&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}"
		      aria-label="Next">
		        <span aria-hidden="true">&raquo;</span>
		      </a>
		    </li>   
	     </c:if>
	  </ul>
	</nav>
</div>

<jsp:include page="../layout/footer.jsp"></jsp:include>