<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script>
$(document).ready(function(){
	window.input  = new Vue({
		el: '#test',
		data : {
			msg : ''
		},
		methods : {
			
		}
		
	});
});
</script>

<div id ="test">

<p> input .number</p>
<input type="number" v-model.number="msg"/>{{msg}}

	
	
</div>