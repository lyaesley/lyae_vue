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
			msg : '',
			numeric : ''
		},
		
		watch : {
			numeric : function() {
				if(this.numeric == ''){
					return '';
				}
				this.numeric = this.numeric.replace(/[^0-9]/g,'');
			}	
		},
		
		computed : {
			test : function() {
				if(this.msg == ''){
					return '';
				}
				return this.msg.replace(/[^0-9]/g,'');
			}	
		},
		
		methods : {
			
		}
		
	});
});
</script>

<div id ="test">

<p> 계산된 속성 (computed)</p>
<input type="text" v-model="msg"/> {{msg}} / {{test}}
<br><br>

<p> 감시자 (watch)</p>
<input type="text" v-model="numeric"/> {{numeric}}
<br><br>

<p>
computed / watch 둘다 변경을 감지함. 
computed 는 v-model 로 사용 불가한듯? 얘는 return이 필요함. 의존하는변수가 변경되면 바인딩됨.
watch 는 v-model 로 사용 가능. watch는 data 변수와 watch 변수이름이 같음.
</p>



	
	
</div>