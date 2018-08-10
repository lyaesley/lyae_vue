<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script>
$(document).ready(function(){
	window.vue  = new Vue({
		el: '#movie',
		data : {
			schTxt : '${movieNm}',
			result : ''
		},
		
		created : function(){ 
			/* this.result = ${result}; */
			this.result = ${result};
		},
		
		methods : {
			send : function() {
				util.moveParam({
					movieNm : this.schTxt
				});
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
		  <input type="text" class="form-control" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2" v-model="schTxt" v-on:keyup.enter="send">
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
			      <p><h5 class="card-title" v-html="node.title"></h5>{{node.subtitle}}</p>
			      <p class="card-text">{{node.pubDate}}</p>
			      <p class="card-text">{{node.director}}</p>
			      <p class="card-text">{{node.actor}}</p>
			      <p class="card-text">{{node.userRating}}</p>
			    </div>
			    <div class="card-footer">
			      <small class="text-muted">{{node.link}}</small>
			    </div>
			  </div>
			</div>
		<!-- <hr>
			<div class="card-deck">
			  <div class="card" v-for="(node, index) in result.items" v-if="index >= result.items.length / 2"  >
			    <img class="card-img-top" :src="node.image" alt="Movie image" onerror="this.src='/img/no-image.png';">
			    <div class="card-body">
			      <h5 class="card-title" v-html="node.title"></h5>
			      <p class="card-text">{{index}}</p>
			    </div>
			    <div class="card-footer">
			      <small class="text-muted">Last updated 3 mins ago</small>
			    </div>
			  </div>
			</div>
		</section> -->
	</form>
</div>