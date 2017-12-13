<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>${_title}</title>
		<tiles:insertAttribute name="taglib" />
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
	    <tiles:insertAttribute name="header" />
	    <tiles:insertAttribute name="menu" />
	    <tiles:insertAttribute name="main" />
	    <tiles:insertAttribute name="footer" />
	    <tiles:insertAttribute name="right" />
 	</div>

</body>
</html>