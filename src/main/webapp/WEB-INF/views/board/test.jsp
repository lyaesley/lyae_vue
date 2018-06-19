<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="content-wrapper">
<section class="content-header">
 head 5
</section>
<section class="content">
	<div id="app">
	  {{ message }}
	</div>
</section>
</div>	

<script>
var df = {
	b : 'b다',
	c : 'c다',
  	init : function(){
  		console.log('1이다');
  	}
	

};
$(function() {
	df.init();	
});

/* df.init = function() {
		console.log('2이다'); 
	   this.vue = new Vue({
		  el: '#app',
		  data: {
		    message: '안녕하세요 Vue!'
		  }
		});
}; */

var a ={
	ccc : new Vue({
		  el: '#app',
		  data: {
		    message: '안녕하세요 Vue!'
		  }
		})
};
</script>
