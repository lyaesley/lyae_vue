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
<title>${_title}</title>
<script src="//code.jquery.com/jquery.min.js"></script>

<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/common.css">

</head>
<body>
	<div id="wrap">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <tiles:insertAttribute name="header" />
                </div>
            </div>
            <div class="row">
                <div class="col-md-2">
                    <tiles:insertAttribute name="menu" />
                </div>
                <div class="col-md-8">
                    <tiles:insertAttribute name="main" />
                </div>
                <div class="col-md-2">
                    <tiles:insertAttribute name="right" />
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <tiles:insertAttribute name="footer" />
                </div>
            </div>
        </div>
    </div>
</body>
</html>