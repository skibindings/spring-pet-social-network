<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<html>

<head>
	<title>Авторизация</title>
	<link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet" type="text/css">
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

<h1>Авторизация</h1>

	<form:form action="${pageContext.request.contextPath}/authenticate"
			   method="POST">
	
		
		<c:if test="${param.success != null}">
		
			<i class="success">Вы успешно зарегистрировались!</i>
			
		</c:if>
	
		<c:if test="${param.error != null}">
		
			<i class="failed">Вы ввели неверную пару логин/пароль!</i>
			
		</c:if>
		
			
		<p>
			Логин: <input type="text" name="username" />
		</p>

		<p>
			Пароль: <input type="password" name="password" />
		</p>
		
		<input type="submit" value="Войти" />
		
	</form:form>
	<hr>
	<a href="<c:url value="/register" />">Регистрация</a>
</body>

</html>












