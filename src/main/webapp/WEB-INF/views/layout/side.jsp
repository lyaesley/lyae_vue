<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- Sidebar -->
<ul id="nav" class="sidebar navbar-nav">
  <li class="nav-item active">
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
  <template v-for="menu in menus">
      <template v-if="isSubMenu(menu)">
  		<li class="nav-item dropdown">
	  	<a class="nav-link dropdown-toggle" v-bind:href="menu.purl" href="#" id="pagesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	      <i v-bind:class="menu.icon"></i>
	      <span>{{menu.name}}</span>
	    </a>
	    <div class="dropdown-menu" aria-labelledby="pagesDropdown">
	    	<template v-for="sub in menu.item">
	    		<a class="dropdown-item" v-bind:href="sub.url">{{sub.name}}</a>
	    	</template>
	    </div>
	  </li>    
      </template>
  	  <template v-else>
  	  	<li class="nav-item">
	  	<a class="nav-link " v-bind:href="menu.purl">
	      <i v-bind:class="menu.icon"></i>
	      <span>{{menu.name}}</span>
	    </a>	
	  </li>  
  	  </template>
  </template>
</ul>

<script>
	var nav = new Vue({
		el : '#nav',
		data : {
			menus : [],
		},
		
		created : function() {
			this.menus = ${__menu};
		},
		methods : {
			isSubMenu : function(menu) {
				return menu.item != '' ? true : false;
			} 
		}
	});
	
	var menu = {
		init : function() {
			
			// 선택된 subMenu 가 있을시 상위메뉴 활성화
			$('li.active').closest('li.treeview').addClass('active menu-open');
		}
	};
	
	$(menu.init);
</script>