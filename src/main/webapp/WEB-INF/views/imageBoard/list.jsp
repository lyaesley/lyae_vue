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
    
    flex: 1 0 250px
}

.flex-container {
  display: flex;
  flex-flow: row wrap;
  background-color: #f1f1f1;
}

</style>
<script>
$(document).ready(function(){
	window.vue = new Vue({
		el: '#imgList',
		data : {
			imgList : '',
			orignImg : '',
			file : '',
			files : '',
			fileRes : ''
		},
		
		created : function() {
			this.imgList = ${imgList};
		},
		
		methods : {
			setOrignImg : function(text) {
				this.orignImg = text.replace('/thumb','');
			},
			
			handleFilesUploadOne : function() {
		        this.file = this.$refs.file.files[0];
	      	},
			
	      	submitFilesOne : function() {
			  
	          	let formData = new FormData();
		        formData.append('file', this.file);
					
			 	axios.post('/file/uploadOne', formData, {
		   			headers: {
			       	'Content-Type': 'multipart/form-data'
			   		}
			  	})
			  	.then( function(result) {
				  	console.log("1" + result)
			      	this.fileRes = result;
			  	})
			  	.catch( function(error) {
			  	  	console.log(error);
			  	})
			 	.finally( function() {console.log('finally')});
			},
			

			handleFilesUpload : function() {
		        this.files = this.$refs.files.files;
	      	},
	      	
	      	submitFiles : function() {
				var formData = new FormData();
			  
				for( var i = 0; i < this.files.length; i++ ){
			          let file = this.files[i];
			          formData.append('files', file);
		        }
					
			 	axios.post('/file/upload', formData, {
		   			headers: {
			       	'Content-Type': 'multipart/form-data'
			   		}
			  	})
			  	.then( response => {
				  	console.log('1'+response.data);
			      	this.fileRes = response.data;
			      	this.imgList = this.imgList.concat(this.fileRes);
			  	})
			  	.catch( function(error) {
			  	  	console.log(error);
			  	})
			 	.finally( function() {console.log('finally')});
			},
			
			ajax : function() {
				var files = this.files;
				$.ajax({
					url: "/file/upload",
				       type: "POST",
				       enctype: 'multipart/form-data',
				       data: {files: files },
				       dataType: "json",
				       processData: false,  // tell jQuery not to process the data
				       contentType: false,   // tell jQuery not to set contentType
				       success: function(data) {
				    	   alert(data);
				       }
				});
				
			}
		}	
	});
});
</script>

<div id="imgList">
	<div class="content-wrapper">
			<div>
				<input type="file" name="file" id="file" ref="file" v-on:change="handleFilesUploadOne()"/>
				<input type="button" value="UploadOne" v-on:click="submitFilesOne()"/>
				<input type="file" name="files" id="files" ref="files" multiple v-on:change="handleFilesUpload()"/>
				<input type="button" value="Upload" v-on:click="submitFiles()"/>
				<input type="button" value="ajax" v-on:click="ajax()"/>
			</div>
		<div class="flex-container">
			<div v-for="(node, index) in imgList" v-bind:style="{'background-image' : 'url(' + node.thumPath + ')'}" v-bind:src="node.thumPath" 
			v-on:click="setOrignImg(node.thumPath)" class="thumbnail" data-toggle="modal" data-target="#exampleModal"></div>
						
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
        <button type="submit" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div>
  </div>
</div>

</div> <!-- /#imgList -->
