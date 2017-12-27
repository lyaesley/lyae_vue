<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script>
	var page = {
			find : function(url) {
				var query = $('#query').val();
				
				util.moveParam({
					url : url
				});
			},
			
			api : function(url) {
				var tb = $('#res-table').show().empty();
				var th = $('<thead><tr></tr></thead>');
				var td = $('<tr></tr>');
				var tbody = $('<tbody></tbody>');
				console.log("api");
				$.ajax({
					url: "/soccer/api/seasonsList",
					type: "POST",
					data: {url : url },
					dataType: "json",
					success: function(data) {
						console.log("ajax");
						/* th 생성 */
						$.each(data[0], function(index, node) {
							th.append('<th>'+index+'</th>');
							tb.append(th);
						});
						
						/* td 생성 */
						$.each(data, function(index, node) {
							$.each(node, function(key, value) {
								/* console.log( key, value); */
								if(value.toString().indexOf("http://") == 0) {
									td.append('<td><a href="'+value+'" onclick="page.api(this.href); return false;">link</a></td>');
								} else {
									td.append('<td>'+value+'</td>');
								}
							});
								tbody.append(td);
								tb.append(tbody);
								td = $('<tr></tr>');
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
	<c:set var="mapping" value="${mapping }"/>
	<c:choose>
		<c:when test="${mapping eq 'seasonsList' }">
			<c:set var="table" value="${result}" scope="request"/>
			<jsp:include page="/WEB-INF/views/soccer/seasonsList.jsp" />	
		</c:when>
		<c:when test="${mapping eq 'leagueTable' }">
			<c:set var="table" value="${result}" scope="request"/>
			<jsp:include page="/WEB-INF/views/soccer/leagueTable.jsp" />	
		</c:when>
		<c:when test="${mapping eq 'teamInfo' }">
			<c:set var="table" value="${result}" scope="request"/>
			<jsp:include page="/WEB-INF/views/soccer/teamInfo.jsp" />	
		</c:when>
		<c:when test="${mapping eq 'players' }">
			<c:set var="table" value="${result}" scope="request"/>
			<jsp:include page="/WEB-INF/views/soccer/seasonsList.jsp" />	
		</c:when>
	</c:choose>
	
	 <!-- <table id="res-table" class="table table-hover table-condensed" style="display: none"></table> -->
	 
	</section>
</div>