<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/siteImages/favicon.ico" />

</head>
<body>
	<jsp:include page="../menu.jsp" />
	<div class="relative">
		<h2>Risposte ai simboli</h2>
	</div>
	<table>
		<tr>
			<th>Trascrizione Simbolo</th>
			<th>Task Completati</th>

		</tr>
		<c:forEach var="sa" items="${symbolsAnswers}">
			<tr>
				<th><c:out value="${sa.transcription}"></c:out></th>
				<th><c:out value="${sa.completedTasks}"></c:out></th>



			</tr>
		</c:forEach>
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