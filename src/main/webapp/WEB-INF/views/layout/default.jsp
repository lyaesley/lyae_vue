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
<meta name="description" content="">
<meta name="author" content="">
<title>${_title}</title>
		<tiles:insertAttribute name="taglib" />
</head>
<body id="page-top">
	    <tiles:insertAttribute name="nav" />
<div id="wrapper">
	    <tiles:insertAttribute name="side" />
 <div id="content-wrapper">
  <div class="container-fluid">
	    <tiles:insertAttribute name="main" />
  </div> <!-- /.container-fluid -->
		<tiles:insertAttribute name="footer" />	    	
 </div> <!-- /.content-wrapper -->
</div><!-- /#wrapper -->
	
	<!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
      <i class="fas fa-angle-up"></i>
    </a>

	<!-- Custom scripts for all pages-->
	<script src="/js/sb-admin.min.js"></script>
</body>
</html>