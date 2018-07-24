<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/views/public/head.jsp"><jsp:param name="title" value="${_title}"/><jsp:param name="cmd" value="${_cmd}"/></jsp:include>
<script type="text/javascript">
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
<jsp:include page="/WEB-INF/views/public/main.jsp"><jsp:param name="task" value="${task}"/></jsp:include>

<!-- content -->
<div class="content-wrapper">
	<section class="content-header">
	     <!-- search form -->
	       <div class="input-group sidebar-form">
	         <input type="text" name="q" id="query" class="form-control" placeholder="Search..." mode="enter" mode-func="page.find"/>
	          <span class="input-group-btn">
	            <button type="button" name="search" id="search-btn" class="btn btn-flat" onclick="page.find();"><i class="fa fa-search"></i></button>
	          </span>
	       </div>
	     <!-- /.search form -->
	</section>
	<section class="content">
	<!-- 영화 검색 결과 테이블 -->
	<c:set var="table" value="${result}" scope="request"/>
	<jsp:include page="/WEB-INF/views/movie/table.jsp" />
	</section>
</div>
<!-- content -->
		
<jsp:include page="/WEB-INF/views/public/tail.jsp"></jsp:include>