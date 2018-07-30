<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script>
$(document).ready(function(){
	window.menu = new Vue({
		el : '#nav',
		data : {
			menus : [],
			path : '${_path}'
		},
		
		created : function() {
			this.menus = ${__menu};
			
		},
		methods : {
			isSubMenu : function(menu){
				return menu.item.length > 0;
			},
			
			isSelected : function(menu) {
				if(!matchMedia('(min-width: 768px)').matches) {
					return false;
				}
				
				var url = this.path;
				if (menu.item && menu.item.length > 0) {
					return menu.item.filter(function(e){ return e.url == url}).length > 0;
					//return menu.item.filter(e => e.url == url).length > 0;
				} else {
					return menu.purl == url || menu.url == url;
				}
			}
		}
	});
});
</script>

<!-- Sidebar -->

<ul id="nav" class="sidebar navbar-nav">
	<template  v-for="menu in menus" v-if="isSubMenu(menu)">
		<li class="nav-item dropdown"  v-bind:class="{show : isSelected(menu)}">
	  	<a class="nav-link dropdown-toggle" v-bind:href="menu.purl" href="#" id="pagesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" v-bind:aria-expanded="isSelected(menu)">
	      <i v-bind:class="menu.icon"></i>
	      <span>{{menu.name}}</span>
	    </a>
	    <div class="dropdown-menu" aria-labelledby="pagesDropdown" v-bind:class="{show : isSelected(menu)}">
	   		<a class="dropdown-item" v-bind:href="sub.url"  v-for="sub in menu.item" v-bind:class="{active : isSelected(sub)}">{{sub.name}}</a>
	    </div>
	  </li>    
	</template>
	<template v-else>
	  	<li class="nav-item" v-bind:class="{show : isSelected(menu)}">
	  	<a class="nav-link " v-bind:href="menu.purl">
	      <i v-bind:class="menu.icon"></i>
	      <span>{{menu.name}}</span>
	    </a>	
	  </li>
	</template>  
  <li class="nav-item">
    <a class="nav-link" href="index.html">
      <i class="fas fa-fw fa-tachometer-alt"></i>
      <span>Dashboard</span>
    </a>
  </li>
  <li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle" href="#" id="pagesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
      <i class="fas fa-fw fa-folder"></i>
      <span>Pages</span>
    </a>
    <div class="dropdown-menu" aria-labelledby="pagesDropdown">
      <h6 class="dropdown-header">Login Screens:</h6>
      <a class="dropdown-item" href="login.html">Login</a>
      <a class="dropdown-item" href="register.html">Register</a>
      <a class="dropdown-item" href="forgot-password.html">Forgot Password</a>
      <div class="dropdown-divider"></div>
      <h6 class="dropdown-header">Other Pages:</h6>
      <a class="dropdown-item" href="404.html">404 Page</a>
      <a class="dropdown-item" href="blank.html">Blank Page</a>
    </div>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="charts.html">
      <i class="fas fa-fw fa-chart-area"></i>
      <span>Charts</span></a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="tables.html">
      <i class="fas fa-fw fa-table"></i>
      <span>Tables</span></a>
  </li>

</ul>
