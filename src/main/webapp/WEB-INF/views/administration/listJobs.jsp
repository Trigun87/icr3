<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ICR</title>

<!--[if lte IE 8]><script src="/js/ie/html5shiv.js"></script><![endif]-->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css" />
<!--[if lte IE 8]><link rel="stylesheet" href="/css/ie8.css" /><![endif]-->
<!--[if lte IE 9]><link rel="stylesheet" href="/css/ie9.css" /><![endif]-->



<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/siteImages/favicon.ico" />
</head>
<body class="landing">
	<jsp:include page="menu.jsp" />

	<div class="relative">
		<h2>Job Creati</h2>
	</div>

	<table>
		<tr>
			<th>ID</th>
			<th>Titolo</th>
			<th>Descrizione</th>
			<th>Difficoltà</th>
			<th>#Studenti</th>
			<th>#Caratteri</th>
			<th>#Parole</th>
			<th>Dimensione Task</th>
			<th>Manoscritto</th>
			<th>Simbolo</th>
		</tr>
		<c:forEach var="job" items="${jobs}">
			<tr>
				<th><c:out value="${job.id}"></c:out></th>
				<th><c:out value="${job.title}"></c:out></th>
				<th><c:out value="${job.description}"></c:out></th>
				<th><c:out value="${job.difficulty}"></c:out></th>
				<th><c:out value="${job.students}"></c:out></th>
				<th><c:out value="${job.numberOfImages}"></c:out></th>
				<th><c:out value="${job.numberOfWords}"></c:out></th>
				<th><c:out value="${job.taskSize}"></c:out></th>
				<th><c:out value="${job.manuscript.name}"></c:out></th>
				<th><c:out value="${job.symbol.transcription}"></c:out></th>
			</tr>
		</c:forEach>
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