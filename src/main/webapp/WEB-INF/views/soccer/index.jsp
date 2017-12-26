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
			
			api : function() {
				var tb = $('#res-table').show().empty();
				var th = $('<tr></tr>');
				var td = $('<tr></tr>');
							
				$.ajax({
					 url: "/soccer/api/seasonsList",
					 type: "post",
					dataType: "json",
					success: function(data) {
						$.each(data, function(index, node) {
							/* console.log( index, node); */
							$.each(node, function(key, value) {
								/* console.log( key, value); */
								if(index === 0){
									console.log("index : "+index);	
									th.append('<th>'+key+'</th>');
									tb.append(th);
								}else{
									console.log("else "+index);	
								}
								tb.append('<tr><td><input type="text" name="'+key+'" value="'+value+'"/></td></tr>');
							});
						});
						
					}
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
             <button type="submit" name="search" id="search-btn" class="btn btn-flat" onclick="page.api();"><i class="fa fa-search"></i></button>
           </span>
        </div>
      <!-- /.search form -->
	</section>
	<section class="content">
	<!-- 검색 결과 테이블 -->
	<%-- 
	<c:set var="table" value="${seasonsList}" scope="request"/>
	<jsp:include page="/WEB-INF/views/soccer/seasonsList.jsp" />
	 --%>
	 <table id="res-table" class="table table-hover" style="display: none"></table>
	 
	</section>
</div>