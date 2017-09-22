<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div id="menu">	
	<div class="list-group">
		<input class="form-control" type="text" placeholder="메뉴검색" onkeyup="devo.menu.find(this.value);"/>
		<ul class="list-group">
		<c:forEach var="node"  items="${_menu}" varStatus="loop">
			<c:choose>
				<c:when test="${node.url eq _path}">
						<li class="list-group-item active"><a href="${node.url}" title="${node.desc}">${node.name}</a></li>
				</c:when>
				<c:otherwise>
						<li class="list-group-item"><a href="${node.url}" title="${node.desc}">${node.name}</a></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		</ul>
	</div>
</div>
<script>
	var subDir = [];
	
	<c:forEach var="subDir" items="${subDir}">
	  subDir.push('${subDir}');
	</c:forEach>

	var page = {
		addSubMenu : function(){
			console.log("addSubMenu");
			if(subDir.length != 0){
				for(var menu in subDir){
					console.log("menu : " + menu);
				}
			}
		},
		
		init : function(){
			console.log("init start");
			page.addSubMenu;
			console.log("init end");
		}
	};

$(page.init);

</script>