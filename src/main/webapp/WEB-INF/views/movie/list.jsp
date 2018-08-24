<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script>
$(document).ready(function(){
	window.vue  = new Vue({
		el: '#movie',
		data : {
			schText : '${movieNm}',
			result : '',
			isShowDirector : {},
			isShowActor : {}
		},
		
		created : function(){ 
			/* this.result = ${result}; */
			var result = ${result};
			
			result.items.forEach( function(e) {
					e.director = e.director.substr(0, e.director.length-1).split("|");
					e.actor = e.actor.substr(0, e.actor.length-1).split("|");
					e.isShowDirector = false;
					e.isShowActor = false;
					e.isMoreDirector = e.director.length > 1 ;
					e.isMoreActor = e.actor.length > 1 ;
				});
			
			this.result = result;

			/* Vue 반응형 속성 추가  */
			/* for(var i in this.result.items) {
				this.$set(this.isShowDirector, i, false);
				this.$set(this.isShowActor, i, false);
			} */
		},
		
		methods : {
			send : function() {
				util.moveParam({
					movieNm : this.schText
				});
			},
			
			checkLength : function(text) {
				var add = '<a v-on:click="fullText('+text+')">...</a>';
				
				return ( (text.split('|').length >= 3) || (text.split('|', 1).toString().length > 9) ) ;
				/* return ( (text.split('|').length >= 3) || (text.split('|', 1).toString().length > 9)) ? text.split('|', 1).toString().substr(0,9)+add : text.split('|', 1).toString(); */
				
			},
			
			shortText : function(text) {
				var dot = '<b class="text-primary" >...</b>';
				/* return ( (text.split('|').length >= 3) || (text.split('|', 1).toString().length > 9)) ? text.split('|', 1).toString().substr(0,9) : text.split('|', 1).toString(); */
			},
			
			fullText : function(text) {
				return text.substr(0,text.length-1).split("|").join(", ");
			}
		}
		
	});
});
</script>

<style>
<!--
.card-text:not(:hover) .more-view { display:none }
.card-text:not(:hover) .hide-view { display:none }
 
 /* 애니메이션 진입 및 진출은 다른 지속 시간 및  */
/* 타이밍 기능을 사용할 수 있습니다. */
.slide-fade-enter-active {
  transition: all .3s ease;
}
.slide-fade-leave-active {
  transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}
.slide-fade-enter, .slide-fade-leave-to
/* .slide-fade-leave-active below version 2.1.8 */ {
  transform: translateX(10px);
  opacity: 0;
}
-->
</style>

<div id="movie">
	<form method="get">
		<section class="content-header">
		<!-- search form -->
		 <div class="input-group">
		  <input type="text" style="display: none;" />	
		  <input type="text" class="form-control" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2" v-model="schText" v-on:keyup.enter="send">
		  <div class="input-group-append">
		    <button class="btn btn-primary" type="button" v-on:click="send">
		      <i class="fas fa-search"></i> 
		    </button>
		  </div>
		</div>
		<!-- /.search form -->
		</section>
		<section class="content">
			<div class="card-deck" >
			  <div class="card movieDeck" v-for="(node, index) in result.items">
			    <img class="card-img-top" :src="node.image" alt="Movie image" onerror="this.src='/img/no-image.png';">
			    <div class="card-body">
			      <h5 class="card-title text-center" v-html="node.title"></h5><h6 class="text-center">{{node.subtitle}}({{node.pubDate}})</h6>
			      <!-- <p class="card-text"><b>감독</b> 
				      <span v-if="isShowDirector[index]">{{fullText(node.director)}} <span v-on:click="isShowDirector[index] = !isShowDirector[index]"> <b class="text-danger"> 닫기</b></span></span>
				      <span v-else v-on:click="isShowDirector[index] = !isShowDirector[index]" ><span v-html="shortText(node.director)"></span></span>
				      <span v-else><span v-html="shortText(node.director)"></span><span v-on:click="isShowDirector[index] = !isShowDirector[index]"><b class="text-primary">...</b></span></span>
				      <span class="more-view">더보기</span>
			      </p>
			      <p class="card-text"><b>출연</b> 
				      <span v-if="isShowActor[index]" >{{fullText(node.actor)}} <span v-on:click="isShowActor[index] = !isShowActor[index]" > <br><b class="text-danger">닫기</b> </span> </span>
				      <span v-else v-on:click="isShowActor[index] = !isShowActor[index]"><span v-html="shortText(node.actor)"></span></span>
				      <span class="more-view">더보기</span>
			      </p> -->
			      <br>
			      <p class="card-text"><b>감독</b> 
					<transition name="slide-fade" mode="out-in">
			      		<span  v-bind:key="node.isShowDirector" v-bind:class="{'text-primary' : !node.isShowDirector && node.isMoreDirector}">{{ node.isShowDirector ? node.director.join(", ") : node.director[0]}}</span> 
					</transition>
		      			<!-- 위  transition 사용(애니메이션)으로 주석처리. 위 내용과 같은 기능(애니메이션 제외) -->
		      			<!-- <span v-if="node.isShowDirector">{{node.director.join(", ")}}</span> 
			      		<span v-else v-bind:class="{'text-primary' : node.isMoreDirector}" key="index+1">{{node.director[0]}}</span>  -->
			      		<span v-on:click="node.isShowDirector=!node.isShowDirector">
				      		<span class="more-view text-primary" v-if="!node.isShowDirector && node.isMoreDirector">더보기</span>
				      		<span class="hide-view text-danger" v-if="node.isShowDirector && node.isMoreDirector">닫기</span>
			      		</span>
			      </p>
			      <p class="card-text"><b>출연</b> 
			      	<transition name="slide-fade" mode="out-in">
			      		<span  v-bind:key="node.isShowActor" v-bind:class="{'text-primary' : !node.isShowActor && node.isMoreActor}">{{ node.isShowActor ? node.actor.join(", ") : node.actor[0]}}</span> 
					</transition>
			      	<!-- 	<span v-if="node.isShowActor">{{node.actor.join(", ")}}</span> 
			      		<span v-else v-bind:class="{'text-primary' : node.isMoreActor}">{{node.actor[0]}}</span> --> 
			      		<span class="more-view text-primary" v-if="!node.isShowActor && node.isMoreActor" v-on:click="node.isShowActor=!node.isShowActor">더보기</span>
			      		<span class="hide-view text-danger" v-if="node.isShowActor && node.isMoreActor" v-on:click="node.isShowActor=!node.isShowActor">닫기</span>
			      </p>
			      <p class="card-text"><b>평점</b> {{node.userRating}} </p>
			    </div>
			    <div class="card-footer">
			      <!-- <small class="text-muted">{{node.link}}</small> -->
			      <a v-bind:href="node.link" target="_blank" class="btn btn-success btn-block"><b>Link</b></a>
			    </div>
			  </div>
			</div>
		</section>
	</form>
</div>