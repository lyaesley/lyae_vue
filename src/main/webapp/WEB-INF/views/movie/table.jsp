<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%-- <table class="table table-hover">
<c:if test="${empty requestScope.table}">
	<tr>
		<td>결과가 없습니다.</td>
	</tr>
</c:if>

<c:if test="${not empty requestScope.table}">	
	<tr>
		<th>선택</th>
		<c:forEach var="col"  items="${requestScope.table[0]}">
			<th>${col.key}</th>
		</c:forEach>
	</tr>
	<c:forEach var="node"  items="${requestScope.table}" varStatus="status">
		<tr>
			<td><input type="checkbox" name="selectRow" value="${status.index }" /></td>
			<c:forEach var="tr"  items="${node}">
				<td>${tr.value}</td>
			</c:forEach>
		</tr>
	</c:forEach>
</c:if>
</table> --%>


<c:if test="${empty requestScope.table}">
		결과가 없습니다.
</c:if>
<c:if test="${not empty requestScope.table}">
	<c:forEach var="node"  items="${requestScope.table.items}" varStatus="status">
		<div class="box box-primary movie-list">
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

            
