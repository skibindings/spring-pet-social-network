<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" type="text/css">
	<style>
		.admin {
			color: red;
		}
		.leader {
			color: blue;
		}
	</style>
</head>



<body>

<c:set var="session_username" scope="session">
<security:authentication property="principal.username" /> 
</c:set>

<div class="topnav">

  <c:if test="${user_profile.username == session_username}">
  <a class="active" href="/users/${session_username}">Моя страница</a>
  </c:if>
  <c:if test="${user_profile.username != session_username}">
  <a href="/users/${session_username}">Моя страница</a>
  </c:if>
  
  <c:if test="${session_user.dep != null}">
  <a href="/deps/${session_user.dep.id}">Мой отдел</a>
  </c:if>
  
  <a href="/users/${session_username}/friends">Друзья</a>
  <a href="/chats">Мессенджер</a>
  
  
  <a class="logout" href="<c:url value="/logout" />">Выйти</a>
  
  <security:authorize access="hasRole('ADMIN')">
  <a class="logout" href="<c:url value="/admin_panel" />">Администрирование</a>
  </security:authorize>
  
  <a class="logout" href="<c:url value="/search" />">Поиск</a>
  
</div>

<h1>
${user_profile.name} ${user_profile.surname} 
<c:if test="${user_profile.admin}">
<font color=red>*АДМИНИСТРАТОР*</font>
</c:if>
</h1>

<c:set var="position" scope="session">
Сотрудник
</c:set>

<c:if test="${user_profile.manager}">
<c:set var="position" scope="session">
Руководитель
</c:set>
</c:if>

<h2>
<font color=blue>
<c:if test="${user_profile.dep != null}">
${position} отдела '${user_profile.dep.name}'
</c:if>
<c:if test="${user_profile.dep == null}">
Без отдела
</c:if>
</font>
</h2>

<c:if test="${user_profile.username != session_username}">
	<c:if test="${!friend}">
		<c:if test="${!friend_request}">
			<a href="/send_friend_request/${user_profile.username}">Добавить в друзья</a>
		</c:if>
		<c:if test="${friend_request}">
			Запрос дружбы отправлен
		</c:if>
	</c:if>
	<br>
	<a href="/create_chat/${user_profile.username}">Чат</a>
</c:if>

<c:if test="${user_profile.dep != null}">
<a href="/deps/${user_profile.dep.id}">Отдел</a>
</c:if>
<a href="/users/${user_profile.username}/friends">Друзья</a>

<hr>

<c:if test="${user_profile.username == session_username}">
<a href="/user_edit">Редактировать профиль</a>
<hr>
</c:if>


<p>
День рождения: ${user_profile.birthdate}
</p>
<p>
Электронная почта: ${user_profile.email}
</p>

<c:if test="${user_profile.city != null}">
<p>
Город: ${user_profile.city}
</p>
</c:if>

<c:if test="${user_profile.phoneNumber != null}">
<p>
Номер телефона: ${user_profile.phoneNumber}
</p>
</c:if>

<c:if test="${user_profile.about != null}">
<p>
О себе: ${user_profile.about}
</p>
</c:if>

<hr>

Developed by Kirill Skibin (2021)

</body>

</html>