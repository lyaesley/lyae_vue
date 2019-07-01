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

function send() {
    console.log("111");
    var formData = $('#order_form').serializeArray();
    console.log(formData);
    $.ajax({
    				url: "/jstest/ajax.do",
    				type: "post",
    				data: formData,
    		       success: function(data) {
    		    	   $('#content').html('');
    		    	   $('#content').html(data);
    		       },
    		       error : function(e) {
    					alert('페이지 로딩이  실패 하였습니다.'+ e.statusText);
    				}
    			});
}
</script>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.2/jquery.min.js"></script>

<div id ="test">

	<form name="order_form" id="order_form">
	<div>
	    <table>
	        <tr>
	            <td><input type="text" name="JsVOList[0].title" value="제목1"/></td>
	            <td><input type="text" name="JsVOList[1].title" value="제목2"/></td>
	            <td><input type="text" name="JsVOList[2].title" value="제목3"/></td>
	            <td><input type="text" name="JsVOList[3].title" value="제목4"/></td>
	        </tr>
	        <tr>
	            <td><textarea name="JsVOList[0].conts" row=20>내용1</textarea></td>
	            <td><textarea name="JsVOList[1].conts" row=20></textarea></td>
	            <td><textarea name="JsVOList[2].conts" row=20></textarea></td>
	            <td><textarea name="JsVOList[3].conts" row=20>내용4</textarea></td>
	        </tr>

	    </table>
	    <input type="button" onclick="send();" value="전송"/>
	<div>
	
</div>