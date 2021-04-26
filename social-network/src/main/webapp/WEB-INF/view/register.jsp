<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<html>

<head>
	<title>Регистрация</title>
	<link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet" type="text/css">
	<style>
		.failed {
			color: red;
		}
		textarea { resize: none; }
	</style>
	
</head>

<body>

<h1>Регистрация</h1>

	<form:form action="${pageContext.request.contextPath}/register_submit"
			   modelAttribute="new_user" method="POST">
	
		<!-- Check for login error -->
        <c:choose>
			<c:when test = "${error == 1}">
			   <i class="failed">Пользователь с таким логином уже существует!</i>
			</c:when>
        </c:choose>

		<p>
			Логин(*): <form:input path="userSecurity.username" type="text" name="login" maxlength="50"/>
			<form:errors path="userSecurity.username" cssClass="failed"/>
		</p>

		<p>
			Пароль(*): <form:input path="userSecurity.password" type="password" name="password" maxlength="50"/>
			<form:errors path="userSecurity.password" cssClass="failed"/>
		</p>
		
		<p>
			Электронный адрес(*): <form:input path="userProfile.email" type="email" name="email" maxlength="70"/>
			<form:errors path="userProfile.email" cssClass="failed"/>
		</p>
		
		<hr>
		
		<p>
			Имя(*): <form:input path="userProfile.name" type="text" maxlength="25"/>
			<form:errors path="userProfile.name" cssClass="failed"/>
		</p>
		
		<p>
			Фамилия(*): <form:input path="userProfile.surname" type="text" maxlength="25"/>
			<form:errors path="userProfile.surname" cssClass="failed"/>
		</p>
		
		<p>
			Дата рождения(*): <form:input path="userProfile.birthdate" type="date" name="birthdate"/>
			<form:errors path="userProfile.birthdate" cssClass="failed"/>
		</p>
		
		<hr>
		
		<p>
			Город: <form:input path="userProfile.city" type="text" maxlength="50"/>
		</p>
		
		<p>
			Номер телефона: <form:input path="userProfile.phoneNumber" type="tel" pattern="+[0-9]{11}"/>
			<form:errors path="userProfile.phoneNumber" cssClass="failed"/>
		</p>
		
		<p>
			Расскажите о себе: <form:textarea path="userProfile.about" rows="6" cols="50" maxlength="300"/>
		</p>
		
		<input type="submit" value="Зарегистрироваться" />
		
	</form:form>
	<hr>
	<a href="<c:url value="/login" />">Назад</a>
</body>

</html>












