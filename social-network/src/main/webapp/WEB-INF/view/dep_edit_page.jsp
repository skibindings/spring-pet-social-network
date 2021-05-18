<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
	<title>Редактирование отдела</title>
	<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" type="text/css">
	<style>
		.failed {
			color: red;
		}
		textarea { resize: none; }
	</style>
	
</head>

<body>

<c:set var="session_username" scope="session">
<security:authentication property="principal.username" /> 
</c:set>

<div class="topnav">

  <a href="/users/${session_username}">
  Моя страница
  </a>
  
  <a class="active" href="/deps/${session_user.dep.id}">Мой отдел</a>
  
  
  <a href="/users/${session_username}/friends">Друзья</a>
  <a href="/chats">Мессенджер</a>
  
  
  <a class="logout" href="<c:url value="/logout" />">Выйти</a>
  
  <security:authorize access="hasRole('ADMIN')">
  <a class="logout" href="<c:url value="/admin_panel" />">Администрирование</a>
  </security:authorize>
  
  <a class="logout" href="<c:url value="/search" />">Поиск</a>
  
</div>

<h1>Редактирование отдела</h1>

	<form:form action="${pageContext.request.contextPath}/dep_edit_submit"
			   modelAttribute="edit_dep" method="POST">
		
		<form:hidden path="id"/>
		<form:hidden path="mongoId"/>
		<form:hidden path="leader"/>
		
		<hr>
		
		<p>
			Название: <form:input path="name" type="text" maxlength="75"/>
			<form:errors path="name" cssClass="failed"/>
		</p>
		
		<p>
			Описание: <form:textarea path="description" rows="8" cols="70" maxlength="500"/>
			<form:errors path="description" cssClass="failed"/>
		</p>
		
		<input type="submit" value="Подтвердить" />
		
	</form:form>

</body>

</html>












