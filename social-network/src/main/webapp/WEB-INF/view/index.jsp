<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" type="text/css">
</head>
<body>

<div class="topnav">
  <a class="active" href="#home">Моя страница</a>
  <a href="#news">Друзья</a>
  <a href="#contact">Мессенджер</a>
  <a class="logout" href="<c:url value="/logout" />">Выйти</a>
</div>

Hello world, SPRING!

<br><br>

Student name: Kirill

</body>

</html>