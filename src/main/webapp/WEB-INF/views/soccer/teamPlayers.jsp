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
					<th>이름</th>
					<th>포지션</th>
					<th>번호</th>
					<th>생년월일</th>
					<th>국가</th>
					<th>계약기간</th>
					<th>시장가치</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="list" items="${requestScope.table}" varStatus="status">
			<tr>
					<td>${list.name}</td>
					<td>${list.position}</td>
					<td>${list.number}</td>
					<td>${list.birth}</td>
					<td>${list.nationality}</td>
					<td>${list.contractUntil}</td>
					<td>${list.marketValue}</td>
			</tr>
		</c:forEach>
		</tbody>
	</c:if>
</table>