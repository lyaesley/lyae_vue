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
					<th>경기날짜</th>
					<th>상태</th>
					<th>매치번호</th>
					<th>홈팀</th>
					<th>결과(하프타임)</th>
					<th>어웨이팀</th>
					<th>배당</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="list" items="${requestScope.table}" varStatus="status">
			<tr>
					<td>${list.matchDate}</td>
					<td>${list.status}</td>
					<td>${list.matchday}</td>
					<td>${list.homeTeamName}</td>
					<td>${list.result.goalsHomeTeam} : ${list.result.goalsAwayTeam}  (${list.result.halfTime.goalsHomeTeam} : ${list.result.halfTime.goalsAwayTeam})</td>
					<td>${list.awayTeamName}</td>
					<td>${list.odds.homeWin} ${list.odds.draw} ${list.odds.awayWin}</td>
			</tr>
		</c:forEach>
		</tbody>
	</c:if>
</table>