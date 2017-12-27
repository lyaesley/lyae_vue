<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<table	class="table table-hover">
	<c:if test="${empty requestScope.table}">
		<tr>
			<td>결과가 없습니다.</td>
		</tr>
	</c:if>
	<c:if test="${not empty requestScope.table}">
		<thead>
		<tr>
			<c:forEach var="col" items="${requestScope.table}">
				<th>${col.key}</th>
			</c:forEach>
		</tr>
		</thead>
		<tbody>
		<tr>
		<c:forEach var="node" items="${requestScope.table}">
					<c:set var="value" value="${node.value}" />
					<c:choose>
						<c:when test="${fn:startsWith(value, 'http://') || fn:startsWith(value, 'https://')}">
							<td><a href="${value}" onclick="page.find(this.href); return false;">link</a></td>
						</c:when>
						<c:otherwise>
							<td>${value}</td>
						</c:otherwise>
					</c:choose>
		</c:forEach>
		</tr>
		</tbody>
	</c:if>
</table>