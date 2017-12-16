<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script>
	var page = {
			find : function() {
				var query = $('#query').val();
				
				util.moveParam({
					query : query
				});
			},
			
			init : function() {
				$('#query').val(util.nvl(util.getParam('query'), ''));
				
			   var maxHeight = -1;
			   $('.movie-list').each(function() {
			     maxHeight = maxHeight > $(this).height() ? maxHeight : $(this).height();
			   });

			   $('.movie-list').each(function() {
			     $(this).height(maxHeight);
			   });
			   
			}
	};

	$(page.init);
	
</script>

<div class="content-wrapper">
	<section class="content-header">
      <!-- search form -->
        <div class="input-group sidebar-form">
          <input type="text" name="q" id="query" class="form-control" placeholder="Search..." mode="enter" mode-func="page.find"/>
           <span class="input-group-btn">
             <button type="submit" name="search" id="search-btn" class="btn btn-flat" onclick="page.find();"><i class="fa fa-search"></i></button>
           </span>
        </div>
      <!-- /.search form -->
	</section>
	<section class="content">
	<!-- 영화 검색 결과 테이블 -->
	<c:set var="table" value="${result }" scope="request"/>
		<c:if test="${empty requestScope.table}">
			영화 제목을 검색해 주세요.
		</c:if>
		<div class="row">
		<c:if test="${not empty requestScope.table}">
			<c:forEach var="node"  items="${requestScope.table.items}" varStatus="status">
				<div class="box box-primary movie-list ">
		            <div class="box-body box-profile">
		              <img class="profile-user-img img-responsive img-circle" src="${node.image }" onerror="this.src='/dist/img/user2-160x160.jpg';" alt="picture">
		
		              <h3 class="profile-username text-center">${node.title }</h3>
		
		              <p class="text-muted text-center">${node.subtitle }</p>
		
		              <ul class="list-group list-group-unbordered">
		                <li class="list-group-item">
		                  <b>감독</b> <a class="pull-right">${node.director }</a>
		                </li>
		                <li class="list-group-item">
		                  <b>배우</b> <a class="pull-right">${node.actor }</a>
		                </li>
		                <li class="list-group-item">
		                  <b>평점</b> <a class="pull-right">${node.userRating }</a>
		                </li>
		              </ul>
		
		              <a href="${node.link }" target="_blank" class="btn btn-primary btn-block"><b>Follow</b></a>
		            </div>
				</div>
			</c:forEach>
		</c:if>
	<%-- <jsp:include page="/WEB-INF/views/movie/table.jsp" /> --%>
	</div><!--/.row -->
	</section>
</div>