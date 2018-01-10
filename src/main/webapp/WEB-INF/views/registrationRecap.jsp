<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="springForm"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="refresh" content="5; url=index" />
<title>ICR</title>

<!--[if lte IE 8]><script src="/js/ie/html5shiv.js"></script><![endif]-->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css" />


<!--[if lte IE 8]><link rel="stylesheet" href="/css/ie8.css" /><![endif]-->
<!--[if lte IE 9]><link rel="stylesheet" href="/css/ie9.css" /><![endif]-->

<!-- Favicon -->
<link rel="shortcut icon"
	href="<c:url value="${pageContext.request.contextPath}/img/siteImages/favicon.ico"/>">

</head>
<body class="landing">

<jsp:include page="menu.jsp" />
	
	<div class="relative">
		<h2>Hai completato  la registrazione</h2>
		<h2>Ora puoi effettuare il login (dal menu in alto a destra)</h2>		
	</div>
	
	<table>
		<tr>
			<th>Nome</th>
			<th>Cognome</th>
			<th>Scuola</th>
			<th>Anno</th>
			<th>Sezione</th>
			<th>Email</th>
		</tr>

		<tr>
			<th>${student.name}</th>
			<th>${student.surname}</th>
			<th>${student.school}</th>
			<th>${student.schoolGroup}</th>
			<th>${student.section}</th>
			<th>${student.email}</th>
		</tr>
	</table>
	
	
	
	<!-- Scripts -->
	
	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.scrollex.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.scrolly.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/skel.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/util.js"></script>
	<!--[if lte IE 8]><script src="/js/ie/respond.min.js"></script><![endif]-->
	<script src="${pageContext.request.contextPath}/js/main.js"></script>

</body>
</html>