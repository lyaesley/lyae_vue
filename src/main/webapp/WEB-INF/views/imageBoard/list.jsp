<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
	.rotate90 {
	    -webkit-transform: rotate(90deg);
	    -moz-transform: rotate(90deg);
	    -o-transform: rotate(90deg);
	    -ms-transform: rotate(90deg);
	    transform: rotate(90deg);
	}
	.rotate180 {
	    -webkit-transform: rotate(180deg);
	    -moz-transform: rotate(180deg);
	    -o-transform: rotate(180deg);
	    -ms-transform: rotate(180deg);
	    transform: rotate(180deg);
	}
	.rotate270 {
	    -webkit-transform: rotate(270deg);
	    -moz-transform: rotate(270deg);
	    -o-transform: rotate(270deg);
	    -ms-transform: rotate(270deg);
	    transform: rotate(270deg);
    }
</style>
</head>
<body>
	<c:if test="${not empty listImg}">
		<c:forEach var="node" items="${listImg}">
			<img alt="${node.name}" src="${node.thumName}" class="rotate${node.fix }">
		</c:forEach>
	</c:if>
</body>
</html>