<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ICR</title>

<!--[if lte IE 8]><script src="/js/ie/html5shiv.js"></script><![endif]-->
<link rel="stylesheet" href="css/main.css" />
<!--[if lte IE 8]><link rel="stylesheet" href="/css/ie8.css" /><![endif]-->
<!--[if lte IE 9]><link rel="stylesheet" href="/css/ie9.css" /><![endif]-->


<!-- Favicon -->
<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/siteImages/favicon.ico" />

</head>
<body class="landing">
	<jsp:include page="../menu.jsp" />
	<div class="relative">
		<h2>Simboli con maggioranza di voti</h2>
	</div>

	<table>
		<tr>
			<th>ID Immagine</th>
			<th>Simbolo</th>
			<th>Numero di risposte positive</th>
		</tr>
		<c:forEach var="mj" items="${majority}">
			<tr>
				<th><c:out value="${mj.imageId}"></c:out></th>
				<th><c:out value="${mj.transcription}"></c:out></th>
				<th><c:out value="${mj.numberOfYes}"></c:out></th>
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