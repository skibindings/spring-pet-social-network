<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
	<title>Администрирование</title>
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


  <a href="/users/${session_username}">
  Моя страница
  </a>
  
  <c:if test="${session_user.dep != null}">
  <a href="/deps/${session_user.dep.id}">Мой отдел</a>
  </c:if>
  
  <a href="#news">Друзья</a>
  <a href="#contact">Мессенджер</a>
  
  
  <a class="logout" href="<c:url value="/logout" />">Выйти</a>
  
  <security:authorize access="hasRole('ADMIN')">
  <a class="activeright" href="<c:url value="/admin_panel" />">Администрирование</a>
  </security:authorize>
  
  
</div>

<h1>Администрирование</h1>
<hr>
<h2>Администраторы (${adminsNum})</h2>

<table>
	<tr>
		<th>Логин</th>
		<th>Имя</th>
		<th>Фамилия</th>
	</tr>
	<c:forEach var="tempUser" items="${admins}">
		<!-- construct an "update" link with customer id -->
		<c:url var="userProfileLink" value="/users/${tempUser.username}"/>				
		<tr>
			<td> <a href="${userProfileLink}">${tempUser.username}</a> </td>
			<td> ${tempUser.name} </td>
			<td> ${tempUser.surname} </td>
		</tr>
	</c:forEach>
</table>

<h2>Руководители (${managersNum})</h2>

<table>
	<tr>
		<th>Логин</th>
		<th>Имя</th>
		<th>Фамилия</th>
		<th>Действия</th>
	</tr>
	<c:forEach var="tempUser" items="${managers}">
		<!-- construct an "update" link with customer id -->
		<c:url var="userProfileLink" value="/users/${tempUser.username}"/>
		<c:url var="deleteProfileLink" value="/user_delete/${tempUser.username}"/>
		<c:url var="demoteLink" value="/user_demote/${tempUser.username}"/>						
		<tr>
			<td> <a href="${userProfileLink}">${tempUser.username}</a> </td>
			<td> ${tempUser.name} </td>
			<td> ${tempUser.surname} </td>
			<td> <a href="${deleteProfileLink}">Удалить профиль</a> <a href="${demoteLink}">Убрать из руководителей</a> </td>
		</tr>
	</c:forEach>
</table>

<h2>Сотрудники (${employeesNum})</h2>

<table>
	<tr>
		<th>Логин</th>
		<th>Имя</th>
		<th>Фамилия</th>
		<th>Действия</th>
	</tr>
	<c:forEach var="tempUser" items="${employees}">
		<!-- construct an "update" link with customer id -->
		<c:url var="userProfileLink" value="/users/${tempUser.username}"/>	
		<c:url var="deleteProfileLink" value="/user_delete/${tempUser.username}"/>
		<c:url var="promoteLink" value="/user_promote/${tempUser.username}"/>					
		<tr>
			<td> <a href="${userProfileLink}">${tempUser.username}</a> </td>
			<td> ${tempUser.name} </td>
			<td> ${tempUser.surname} </td>
			<td> 
			<a href="${deleteProfileLink}">Удалить профиль</a> 
			<a href="${promoteLink}">Назначить руководителем</a> </td>
		</tr>
	</c:forEach>
</table>

</html>












