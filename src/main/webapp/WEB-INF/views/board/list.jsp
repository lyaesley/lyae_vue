<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	게시판 리스트 : ${name} <br>
	test : ${_test } <br>
	path : ${_path } <br>
	<img alt="사진" src="/pic/1.jpg">
	<img alt="사진" src="/pic/사막.jpg">
	<img alt="사진" src="/pic/Tulips.jpg">
	<img alt="사진" src="/pic/하위/2.jpg">
</body>
</html>