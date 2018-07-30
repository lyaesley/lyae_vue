<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script>
$(document).ready(function(){
	window.selectBox  = new Vue({
		el: '#selectBox',
		data : {
			picked : '',
			selBox : ''
		},
		
		methods : {
			
		}
		
	});
});
</script>

<div id ="selectBox">
	<input type="radio" id="one" value="One" v-model="picked">
	<label for="one">일반</label>
	<input type="radio" id="Two" value="Two" v-model="picked">
	<label for="Two">법인</label>
	<input type="radio" id="Three" value="Three" v-model="picked">
	<label for="Three">미성년자</label>
	<input type="radio" id="four" value="four" v-model="picked">
	<label for="four">외국인</label>
	<input type="radio" id="five" value="five" v-model="picked">
	<label for="five">외국인 미성년자</label>
	<select v-model="selBox">
		
	</select>
</div>