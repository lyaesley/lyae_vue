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
    .mask{
    	position:fixed;
    	display:none;
    	background-color: rgba(0,0,0, 0.8);
    	width:100%;
    	height:100%;
    	top:0;
    	left:0;
    	z-index:1000;
    	
    }
	.window{
        position: fixed;
	    width: 600px;
	    min-width: 300px;
	    top: 100px;
	    right: 100px;
	    bottom: 100px;
	    left: 100px;
	    margin: 0 auto;
	    overflow: auto;
	}
	.origin{
	 width:100%;
	}
</style>
<script>
var page = {
	thumb : function(name,fix) {
		$('.window .origin').attr('src', name).attr('class', 'origin rotate'+fix);
		$('.mask').show();
	},
	
	init : function(){
		$('.mask').click(function(e) {
			$('.mask').hide();
		});
	}
};
	
$(page.init);
</script>
</head>
<body>
<div class="content">
	<c:if test="${not empty listImg}">
		<c:forEach var="node" items="${listImg}">
			<img alt="${node.thumName}" src="${node.thumName}" class="rotate${node.fix } thumb" onclick="page.thumb('${node.name}','${node.fix }')" style="padding:20px;">
		</c:forEach>
	</c:if>
</div>

<div class="mask">
	<div class="window">
		<img alt="" src="" class="origin">
	</div>
</div>

</body>
</html>