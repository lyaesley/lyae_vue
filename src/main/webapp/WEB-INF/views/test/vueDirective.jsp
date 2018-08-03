<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script>
$(document).ready(function(){
	window.vue  = new Vue({
		el: '#test',
		data : {
			msg : 'hello!',
			number: '',
			
		},
		
		watch : {
			number : function() {
				/* console.log(val); */
				this.number = this.number.replace(/[^0-9]/g,'');
			}
		},
		
		methods : {
			
		}
	});
});

Vue.directive('focus', {
	inserted : function(el) {
		el.focus();
	}
}); 

Vue.directive('demo', {
	  bind: function (el, binding, vnode) {
	    var s = JSON.stringify
	    el.innerHTML =
	      'name: '       + s(binding.name) + '<br>' +
	      'value: '      + s(binding.value) + '<br>' +
	      'expression: ' + s(binding.expression) + '<br>' +
	      'argument: '   + s(binding.arg) + '<br>' +
	      'modifiers: '  + s(binding.modifiers) + '<br>' +
	      'vnode keys: ' + Object.keys(vnode).join(', ')
	  }
});

Vue.directive('numericOnly', {
	bind: function(el, binding) {
		el.addEventListener('keydown', function(e) {
			var key = e.keyCode ? e.keyCode : e.which;
			/* // {Delete : 46, Backspace : 8, Tab : 9, ESC : 27, Enter : 13, } */
			/* // {Mozilla: {DEL : 110, .dot : 190}} */
			if ([46, 8, 9, 27, 13].indexOf(key) !== -1 ||
		            // Allow: Ctrl+A
		            (key === 65 && e.ctrlKey === true) ||
		            // Allow: Ctrl+C
		            (key === 67 && e.ctrlKey === true) ||
		            // Allow: Ctrl+X
		            (key === 88 && e.ctrlKey === true) ||
		            // Allow: Ctrl+V
		            //(key === 86 && e.ctrlKey === true) ||
		            // Allow: home, end, left, right
		            (key >= 35 && key <= 39)) {
		            // let it happen, don't do anything
		            return; 
		        }
			
			/* // Ensure that it is a number and stop the keypress */
			if( (e.shiftKey || (key < 48 || key > 57)) && (key < 96 || key > 105) ){
	            e.preventDefault();
	        }
			
			
		});
		
		el.addEventListener('keyup', function(e) {
			var key = e.keyCode ? e.keyCode : e.which;
			el.value = el.value.replace(/[^0-9]/g, "");
		});
	},
	
	update : function(el, binding) {
		console.log("update");
		console.log("oldValue : " + binding.oldValue);
		
	}
	
});

</script>

<div id="test">
	<input v-focus type="number" />
	<div v-demo:foo.a.b="msg"></div>
	<br> <input v-focus v-numeric-only="number" v-model="number"><br>
</div>