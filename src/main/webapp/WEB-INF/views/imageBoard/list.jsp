<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
 		position: absolute;
	    top: 0;
	    right: 0;
	    bottom: 0;
	    left: 0;
	    display: flex;
	    align-items: center;
	    justify-content: center;
	    display: -webkit-flex;
	    -webkit-align-item:center;
	    -webkit-justify-content: center;
	
/* 
        position: fixed;
        width: fit-content;
   		height: fit-content;
	    top: 100px;
	    right: 100px;
	    bottom: 100px;
	    left: 100px;
	    margin: 0 auto;
	     */
	    /* overflow: auto; */
	}
	.origin{
	 /* width:50%; */
	 /* height: 77%; */
	}
	.thumb img{
		margin:20px;
	}
</style>
<script>
var page = {
	
	init : function(){
		/* 서브폴더 메뉴 리스트에 추가*/
		
		
		$('.mask').click(function(e) {
			$('.mask').hide();
		});
		
		$('.thumb img').click(function(){
			var name = $(this).attr('src').replace('/thumb','');
			var fix =  $(this).attr('class');
			$('.window .origin').attr('src', name).attr('class', 'origin '+fix);
			$('.mask').show();
		});
	}
};
	
$(page.init);

</script>
<div class="content-wrapper">
	<div class="thumb">
		<c:if test="${not empty listImg}">
			<c:forEach var="node" items="${listImg}">
				<img alt="${node.thumName}" src="${node.thumName}" class="rotate${node.fix }">
			</c:forEach>
		</c:if>
	</div>
</div><!-- /.content-wrapper -->

	<div class="mask">
		<div class="window">
			<img alt="" src="" class="origin">
		</div>
	</div>
