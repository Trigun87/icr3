<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="it.uniroma3.icr.model.Task"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="springForm"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ICR</title>

<!--[if lte IE 8]><script src="/js/ie/html5shiv.js"></script><![endif]-->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css" />
<!--[if lte IE 8]><lfnink rel="stylesheet" href="/css/ie8.css" /><![endif]-->
<!--[if lte IE 9]><link rel="stylesheet" href="/css/ie9.css" /><![endif]-->

<!-- Favicon -->
<link rel="shortcut icon"
	href="<c:url value="${pageContext.request.contextPath}/img/siteImages/favicon.ico"/>">


</head>
<body class="landing">

	<!-- Page Wrapper -->
	<div id="page-wrapper">

		<!-- Header -->
		<header id="header" class="alt">

			<nav id="nav">
				<ul>
					<li class="special"><a href="#menu" class="menuToggle"><span>Menu</span></a>
						<div id="menu">
							<ul>
								<li><a href="homeStudentSocial?social=${social}">Torna alla pagine dello
										studente</a></li>
								<c:if test="${social=='fb'}">
 									<li><form name="submitForm1" method="POST" action="${pageContext.request.contextPath}/connect/facebook">
    								<input type="hidden" name="_method" value="delete" />
   									 <A HREF="javascript:document.submitForm1.submit()" style="
   									 		border: 0;
											font-size: 0.8em;
											letter-spacing: 0.225em;
											text-decoration: none;
											text-transform: uppercase;">
									Logout
									</A>
									</form>	
									</li>
 								</c:if>
								
								<c:if test="${social=='goo'}">
								<li>
								<form name="submitForm2" method="POST" action="${pageContext.request.contextPath}/connect/google">
    								<input type="hidden" name="_method" value="delete" />
   									 <A HREF="javascript:document.submitForm2.submit()" style="
   									 		border: 0;
											font-size: 0.8em;
											letter-spacing: 0.225em;
											text-decoration: none;
											text-transform: uppercase;">
									Logout
									</A>
								</form>
								</li>
								</c:if>
							</ul>
						</div></li>
				</ul>
			</nav>
		</header>
	</div>

	<div class="relative">
		<h2>Task Effettuati da: ${s.name}</h2>
	</div>

	<table>
		<tr>
			<th>Numero di Task Effettuati</th>
		</tr>

		<tr>
			<th><c:out value="${fn:length(studentTasks) }"></c:out></th>
		</tr>
	</table>

	<table>
		<tr>
			<th>ID</th>
			<th>Iniziato il:</th>
			<th>Terminato il:</th>
		</tr>

		<c:forEach var="task" items="${studentTasks}">
			<tr>
				<th><c:out value="${task.id}"></c:out></th>
				<th><c:out value="${task.startDate}"></c:out></th>
				<th><c:out value="${task.endDate}"></c:out></th>
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
	<script src="${pageContext.request.contextPath}/js/backButton.js"></script>

</body>
</html>