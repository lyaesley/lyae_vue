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
			selected : '',
			picked : '',
			optionList : [],
			radioList: [{text : '일반', code: 'One'},
						{text : '법인', code: 'Two'},
						{text : '미성년자', code: 'Three'},
						{text : '외국인', code: 'Four'},
						{text : '외국인 미성년자', code: 'Five'}],
			
			selectedCom : '',						
			radio : '',
			optionListCom : [],
			radioListCom: [{text : '일반', code: '1'},
							{text : '법인', code: '2'},
							{text : '미성년자', code: '3'},
							{text : '외국인', code: '4'},
							{text : '외국인 미성년자', code: '5'}]
		},
		
		computed : {
			selectRadioCom : function() {
				console.log('computed');
				this.selectedCom = '';
				switch(this.radio){
				case '1':
					this.optionListCom = [{text : '-- 선택 --', code : ''}
											,{text : '111', code : '1'}
											,{text : '222', code : '2'}
											,{text : '333', code : '3'}];
					break;
				case '2':
					this.optionListCom = [{text : '-- 선택 --', code : ''}
											,{text : 'aaa', code : '1'}
											,{text : 'bbb', code : 'b'}
											,{text : 'ccc', code : 'c'}];
					break;
				case '3':
					this.optionListCom = [{text : '-- 선택 --', code : ''}
											,{text : 'jjj', code : 'j'}
											,{text : 'kkk', code : 'k'}
											,{text : 'lll', code : 'l'}];
					break;
				default :
					this.optionListCom = []
					break;
				}
				return this.optionListCom;
			}
		},
		
		methods : {
			selectRadio : function() {
				console.log('method');
				this.selected = '';
				switch(this.picked){
				case 'One':
					this.optionList = [{text : '-- 선택 --', code : ''}
										,{text : '111', code : '1'}
										,{text : '222', code : '2'}
										,{text : '333', code : '3'}];
					break;
				case 'Two':
					this.optionList = [{text : '-- 선택 --', code : ''}
										,{text : 'aaa', code : '1'}
										,{text : 'bbb', code : 'b'}
										,{text : 'ccc', code : 'c'}];
					break;
				case 'Three':
					this.optionList = [{text : '-- 선택 --', code : ''}
										,{text : 'jjj', code : 'j'}
										,{text : 'kkk', code : 'k'}
										,{text : 'lll', code : 'l'}];
					break;
				default :
					this.optionList = []
					break;
				}
			}
		}
		
	});
});
</script>

<div id ="selectBox">

<p> v-on:change, 메소드 사용</p>
	<template  v-for="node in radioList">
		<input type="radio" v-bind:id="node.code" v-bind:value="node.code" v-model="picked" v-on:change="selectRadio">
		<label :for="node.code">{{node.text}}</label>
	</template>
	
	<select v-model="selected">
		<option v-for="node in optionList" v-bind:value="node.code">{{node.text}}</option>
	</select>
	<br><br>
	
<p> vue computed 사용</p>
	<template  v-for="node in radioListCom">
		<input type="radio" v-bind:id="node.code" v-bind:value="node.code" v-model="radio">
		<label :for="node.code">{{node.text}}</label>
	</template>
	
	<select v-model="selectedCom">
		<option v-for="node in selectRadioCom" v-bind:value="node.code">{{node.text}}</option>
	</select>
	
</div>