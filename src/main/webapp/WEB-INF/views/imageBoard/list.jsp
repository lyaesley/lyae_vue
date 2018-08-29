<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style type="text/css">
.rotate90 {
	-webkit-transform: rotate(90deg);
	-moz-transform: rotate(90deg);
	-o-transform: rotate(90deg);
	-ms-transform: rotate(90deg);
	transform: rotate(90deg);
}

.rotate180 {
	-webkit-transform: rotate(180deg);
	-moz-transform: rotate(180deg);
	-o-transform: rotate(180deg);
	-ms-transform: rotate(180deg);
	transform: rotate(180deg);
}

.rotate270 {
	-webkit-transform: rotate(270deg);
	-moz-transform: rotate(270deg);
	-o-transform: rotate(270deg);
	-ms-transform: rotate(270deg);
	transform: rotate(270deg);
}

.thumbnail {
	margin: 5px;
	width: 250px;
	height: 250px;
	float: left;
    background-size: cover;
    background-position: center;
}

</style>
<script>
$(document).ready(function(){
	window.vue = new Vue({
		el: '#imgList',
		data : {
			imgList : '',
			orignImg : ''
		},
		
		created : function() {
			this.imgList = ${imgList};
		},
		
		methods : {
			setOrignImg : function(text) {
				this.orignImg = text.replace('/thumb','');
			}
		},
		
	});
});
</script>

<div id="imgList">
	<div class="content-wrapper">
		<div class="thumb">
			<div v-for="(node, index) in imgList" v-bind:style="{'background-image' : 'url(' + node.thumName + ')'}" v-bind:src="node.thumName" 
			v-on:click="setOrignImg(node.thumName)" class="thumbnail" data-toggle="modal" data-target="#exampleModal"></div>
						
		</div>
	</div><!-- /.content-wrapper -->

<!-- Button trigger modal -->
<!-- <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
  Launch demo modal
</button> -->

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <p><img alt="" v-bind:src="orignImg" style="width: 100%;"/></p>
        <p>메모</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div>
  </div>
</div>

</div> <!-- /#imgList -->
