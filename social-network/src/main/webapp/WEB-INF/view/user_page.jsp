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
  
  <a href="#news">Друзья</a>
  <a href="#contact">Мессенджер</a>
  
  
  <a class="logout" href="<c:url value="/logout" />">Выйти</a>
  
  <security:authorize access="hasRole('ADMIN')">
  <a class="logout" href="<c:url value="/admin_panel" />">Администрирование</a>
  </security:authorize>
  
  
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


<c:if test="${user_profile.username == session_username}">
<a class="active" href="/user_edit">Редактировать профиль</a>
</c:if>

<hr>
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