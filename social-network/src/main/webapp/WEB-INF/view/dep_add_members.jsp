<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
	<title>Список участников</title>
	<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" type="text/css">
	<style>
		.failed {
			color: red;
		}
		.success {
			color: blue;
		}
	</style>
	
</head>

<body>

<c:set var="session_username" scope="session">
<security:authentication property="principal.username" /> 
</c:set>

<div class="topnav">


    <a href="/users/${session_username}">Моя страница</a>

	<a class="active" href="/deps/${session_user.dep.id}">Мой отдел</a>
  
  <a href="/users/${session_username}/friends">Друзья</a>
  <a href="/chats">Мессенджер</a>
  
  
  <a class="logout" href="<c:url value="/logout" />">Выйти</a>
  
  <security:authorize access="hasRole('ADMIN')">
  <a class="logout" href="<c:url value="/admin_panel" />">Администрирование</a>
  </security:authorize>
  
  <a class="logout" href="<c:url value="/search" />">Поиск</a>
  
</div>

<h1>Добавление сотрудников '${session_user.dep.name}'</h1>
<hr>
<h2>Доступные кандидатуры</h2>

<table>
	<tr>
		<th>Логин</th>
		<th>Имя</th>
		<th>Фамилия</th>
		<th>Действия</th>
	</tr>
	<c:forEach var="tempUser" items="${users}">
		<!-- construct an "update" link with customer id -->
		<c:url var="userProfileLink" value="/users/${tempUser.username}"/>			
		<c:url var="addMemberLink" value="/deps/${session_user.dep.id}/add_member/${tempUser.username}"/>	
		<tr>
			<td> <a href="${userProfileLink}">${tempUser.username}</a> </td>
			<td> ${tempUser.name} </td>
			<td> ${tempUser.surname} </td>
			<td>
			 <a href="${addMemberLink}">Добавить сотрудника</a> 
			</td>
		</tr>
	</c:forEach>
</table>

</html>












