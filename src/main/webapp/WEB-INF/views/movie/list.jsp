<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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

<div id="page-wrapper">
	<section class="content-header">
      <!-- search form -->
        <div class="input-group sidebar-form">
          <input type="text" name="q" id="query" class="form-control" placeholder="Search..." mode="enter" mode-func="page.find"/>
           <span class="input-group-btn">
             <button type="submit" name="search" id="search-btn" class="btn btn-flat" onclick="page.find();"><i class="fa fa-search"></i></button>
           </span>
        </div>
        <div class="input-group mb-3">
		  <input type="text" class="form-control" placeholder="Search..." aria-label="Recipient's username" aria-describedby="button-addon2">
		  <div class="input-group-append">
		    <button class="btn btn-outline-secondary" type="button" id="button-addon2">Button</button>
		  </div>
		</div>
		
		<div class="row">
		  <div class="col-lg-6">
		    <div class="input-group">
		      <input type="text" class="form-control" placeholder="Search for...">
		      <span class="input-group-btn">
		        <button class="btn btn-default" type="button">Go!</button>
		      </span>
		    </div><!-- /input-group -->
		  </div><!-- /.col-lg-6 -->
		</div><!-- /.row -->
      <!-- /.search form -->
	</section>
	<section class="content">
	<!-- 영화 검색 결과 테이블 -->
	<c:set var="table" value="${result}" scope="request"/>
	<jsp:include page="/WEB-INF/views/movie/table.jsp" />
	</section>
</div>