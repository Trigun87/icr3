 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ICR</title>

<!--[if lte IE 8]><script src="/js/ie/html5shiv.js"></script><![endif]-->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css" />
<!--[if lte IE 8]><link rel="stylesheet" href="/css/ie8.css" /><![endif]-->
<!--[if lte IE 9]><link rel="stylesheet" href="/css/ie9.css" /><![endif]-->


<!-- Favicon -->
<link rel="shortcut icon"
	href="<c:url value="${pageContext.request.contextPath}/img/siteImages/favicon.ico"/>">

</head>
<body class="landing" onload="">

	<!-- Header -->
	<header id="header" class="alt">

		<nav id="nav">
			<ul>
				<li class="special"><a href="#menu" class="menuToggle"><span>Menu</span></a>
					<div id="menu">
						<ul>
							<li><a href="toSelectManuscript">Crea un nuovo Job</a></li>
							<li><a href="homeAdmin">Torna alla pagina di
									amministrazione</a></li>
							<li><a href="logout">Logout</a></li>
						</ul>
					</div></li>
			</ul>
		</nav>
	</header>
	<div class="relative">
		<h2>Riepilogo del Job creato</h2>
	</div>

	<table>
		<tr>
			<th>Titolo</th>
			<th>Numero di Studenti</th>
			<th>Numero di Caratteri</th>
			<th>Numero di Parole</th>
			<th>Dimensione del Task</th>
			<th>Manoscritto</th>
			<th>Simbolo</th>
		</tr>

		<tr>
			<th>${job.title}</th>
			<th>${job.students}</th>
			<th>${job.numberOfImages}</th>
			<th>${job.numberOfWords}</th>
			<th>${job.taskSize}</th>
			<th>${manuscript.name}</th>
			<th>${job.symbol.transcription}</th>
		</tr>
	</table>

	<!-- Scripts -->
	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.scrollex.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.scrolly.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/skel.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/util.js"></script>
	<!--[if lte IE 8]>-->
	<script src="${pageContext.request.contextPath}/js/ie/respond.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/main.js"></script>

</body>
</html>