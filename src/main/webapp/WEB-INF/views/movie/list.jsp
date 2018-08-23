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
			this.result = ${result};
			
			/* Vue 반응형 속성 추가  */
			for(var i in this.result.items) {
				this.$set(this.isShowDirector, i, false);
				this.$set(this.isShowActor, i, false);
			}
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
				return ( (text.split('|').length >= 3) || (text.split('|', 1).toString().length > 9)) ? text.split('|', 1).toString().substr(0,9) : text.split('|', 1).toString();
			},
			
			fullText : function(text)	{
				console.log("11");
				console.log("22" + text);
			}
		}
		
	});
});
</script>

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
			      <!-- <p class="card-text"><b>감독</b> {{checkLength(node.director)}}</p> -->
			      <!-- <p class="card-text"><b>출연</b> {{checkLength(node.actor)}}</p> -->
			      <!-- <p class="card-text"><b>감독</b> <span v-html="checkLength(node.director)"></span></p> -->
			      <!-- <p class="card-text"><b>출연</b> <span v-html="checkLength(node.actor)"></span></p> -->
			      <p class="card-text"><b v-on:click="isShowDirector[index] = !isShowDirector[index]">감독</b> 
				      <span v-if="isShowDirector[index]" >{{node.director}}</span>
				      <span v-else>{{shortText(node.director)}}...</span>
			      </p>
			      <p class="card-text"><b v-on:click="isShowActor[index] = !isShowActor[index]">출연</b> 
				      <span v-if="isShowActor[index]" >{{node.actor}}</span>
				      <span v-else>{{shortText(node.actor)}}...</span>
			      </p>
			       <!-- <p class="card-text"  v-if="checkLength(node.actor)"><b>출연</b> {{shortText(node.actor)}}<a v-on:click="fullText(node.actor)">...</a></p> -->
			       <!-- <p class="card-text"  v-else><b>출연</b> {{shortText(node.actor)}}</p> -->
			      <p class="card-text"><b>평점</b> {{node.userRating}}</p>
			    </div>
			    <div class="card-footer">
			      <!-- <small class="text-muted">{{node.link}}</small> -->
			      <a v-bind:href="node.link" target="_blank" class="btn btn-primary btn-block"><b>Link</b></a>
			    </div>
			  </div>
			</div>
		</section>
	</form>
</div>