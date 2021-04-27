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

	<c:if test="${session_user.dep != null}">
		<c:if test="${session_user.dep.id != page_dep.id}">
			<a href="/deps/${session_user.dep.id}">Мой отдел</a>
		</c:if>
		<c:if test="${session_user.dep.id == page_dep.id}">
			<a class="active" href="/deps/${session_user.dep.id}">Мой отдел</a>
		</c:if>
	</c:if>
  
  <a href="#news">Друзья</a>
  <a href="#contact">Мессенджер</a>
  
  
  <a class="logout" href="<c:url value="/logout" />">Выйти</a>
  
  <security:authorize access="hasRole('ADMIN')">
  <a class="logout" href="<c:url value="/admin_panel" />">Администрирование</a>
  </security:authorize>
  
  
</div>

<h1>Сотрудники отдела '${page_dep.name}'</h1>
<hr>
<h2>Количество: (${members_num})</h2>

<table>
	<tr>
		<th>Логин</th>
		<th>Имя</th>
		<th>Фамилия</th>
		<c:if test="${page_dep.leader.username == session_user.username}">
			<th>Действия</th>
		</c:if>
	</tr>
	<c:forEach var="tempUser" items="${page_dep.users}">
		<!-- construct an "update" link with customer id -->
		<c:url var="userProfileLink" value="/users/${tempUser.username}"/>			
		<c:url var="deleteMemberLink" value="/deps/${session_user.dep.id}/remove_member/${tempUser.username}"/>	
		<tr>
			<td> <a href="${userProfileLink}">${tempUser.username}</a> </td>
			<td> ${tempUser.name} </td>
			<td> ${tempUser.surname} </td>
			<c:if test="${page_dep.leader.username == session_user.username}">
				<td>
					<c:if test="${tempUser.username != session_user.username}">
						<a href="${deleteMemberLink}">Удалить сотрудника</a> 
					</c:if>
				</td>
			</c:if>
		</tr>
	</c:forEach>
</table>

</html>












