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
		<tr>
			<c:forEach var="col" items="${requestScope.table.boxOfficeResult.dailyBoxOfficeList[0]}">
				<th>${col.key}</th>
			</c:forEach>
		</tr>
		<c:forEach var="node" items="${requestScope.table.boxOfficeResult.dailyBoxOfficeList}">
			<tr>
				<c:forEach var="tr" items="${node}">
					<c:set var="value" value="${tr.value}" />
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
		</c:forEach>
	</c:if>
</table>