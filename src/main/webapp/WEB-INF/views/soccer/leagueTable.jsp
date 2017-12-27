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
					<th>순위</th>
					<th>팀</th>
					<th>경기수</th>
					<th>승점</th>
					<th>승</th>
					<th>무</th>
					<th>패</th>
					<th>득점</th>
					<th>실점</th>
					<th>득실차</th>
					<th>홈</th>
					<th>어웨이</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="list" items="${requestScope.table}" varStatus="status">
			<tr>
					<td>${list.rank}</td>
					<td><a href="${list.links.team.href}" onclick="page.find(this.href); return false;"><img src="${list.logo}" width="25" height="25"> ${list.teamName}</a></td>
					<td>${list.playedGames}</td>
					<td>${list.points}</td>
					<td>${list.wins}</td>
					<td>${list.draws}</td>
					<td>${list.losses}</td>
					<td>${list.goals}</td>
					<td>${list.goalsAgainst}</td>
					<td>${list.goalDifference}</td>
					<td>${list.home}</td>
					<td>${list.away}</td>
			</tr>
		</c:forEach>
		</tbody>
	</c:if>
</table>