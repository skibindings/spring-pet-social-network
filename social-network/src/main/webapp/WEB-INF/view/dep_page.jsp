<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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
		textarea { resize: none; }
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

<h1>
${page_dep.name} 
</h1>

Руководитель: <a class="active" href="/users/${page_dep.leader.username}"> ${page_dep.leader.name} ${page_dep.leader.surname}</a>
<br>
<a class="active" href="/deps/${page_dep.id}/members">Список участников</a>
<hr>

<c:if test="${session_user.username == page_dep.leader.username}">
<p>
<a class="active" href="/dep_edit">Редактировать информацию</a>
<a class="active" href="/deps/add_members">Добавить участников</a>
</p>
</c:if>

<h2>
Описание
</h2>
<p>
${page_dep.description} 
</p>
<hr>
<p>
Новости:
</p>

<c:if test="${session_user.username == page_dep.leader.username}">

<form:form action="/deps/${page_dep.id}/post_submit"
			   modelAttribute="new_post" method="POST">
		<form:textarea path="text" rows="6" cols="50" maxlength="300"/>
   <input type="submit" value="Опубликовать" />
</form:form>
</c:if>

	<c:forEach var="post" items="${posts}">			
		<p>
			${post.timestamp}
		</p>
		<div>
			${post.text}
		</div>
	</c:forEach>

<hr>

Developed by Kirill Skibin (2021)

</body>

</html>