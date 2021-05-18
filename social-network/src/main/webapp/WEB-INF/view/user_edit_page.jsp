<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
	<title>Редактирование профиля</title>
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

  <a href="/users/${session_username}" class="active">
  Моя страница
  </a>
  
  <c:if test="${edit_user.dep != null}">
  <a href="/deps/${edit_user.dep.id}">Мой отдел</a>
  </c:if>
  
  <a href="/users/${session_username}/friends">Друзья</a>
  <a href="/chats">Мессенджер</a>
  
  
  <a class="logout" href="<c:url value="/logout" />">Выйти</a>
  
  <security:authorize access="hasRole('ADMIN')">
  <a class="logout" href="<c:url value="/admin_panel" />">Администрирование</a>
  </security:authorize>
  
  <a class="logout" href="<c:url value="/search" />">Поиск</a>
  
</div>

<h1>Редактирование профиля</h1>

	<form:form action="${pageContext.request.contextPath}/user_edit_submit"
			   modelAttribute="edit_user" method="POST">
		
		<form:hidden path="username"/>
		<form:hidden path="mongoId"/>
		<form:hidden path="dep"/>
		
		<hr>
		
		<p>
			Имя: <form:input path="name" type="text" maxlength="25"/>
			<form:errors path="name" cssClass="failed"/>
		</p>
		
		<p>
			Фамилия: <form:input path="surname" type="text" maxlength="25"/>
			<form:errors path="surname" cssClass="failed"/>
		</p>
		
		<p>
			Электронный адрес: <form:input path="email" type="email" name="email" maxlength="70"/>
			<form:errors path="email" cssClass="failed"/>
		</p>
		
		<p>
			Дата рождения: <form:input path="birthdate" type="date" name="birthdate"/>
			<form:errors path="birthdate" cssClass="failed"/>
		</p>
		
		<hr>
		
		<p>
			Город: <form:input path="city" type="text" maxlength="50"/>
		</p>
		
		<p>
			Номер телефона: <form:input path="phoneNumber" type="tel" pattern="+[0-9]{11}"/>
			<form:errors path="phoneNumber" cssClass="failed"/>
		</p>
		
		<p>
			О себе: <form:textarea path="about" rows="6" cols="50" maxlength="300"/>
		</p>
		
		<input type="submit" value="Подтвердить" />
		
	</form:form>

</body>

</html>












