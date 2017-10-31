<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="it.uniroma3.icr.model.Job"%>
<%@ page import="it.uniroma3.icr.model.Task"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="springForm"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ICR</title>

<!--[if lte IE 8]><script src="/js/ie/html5shiv.js"></script><![endif]-->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/newTaskWord.css" />
<!--[if lte IE 8]><link rel="stylesheet" href="/css/ie8.css" /><![endif]-->
<!--[if lte IE 9]><link rel="stylesheet" href="/css/ie9.css" /><![endif]-->

<!-- Favicon -->
<link rel="shortcut icon"
	href="<c:url value="${pageContext.request.contextPath}/img/siteImages/favicon.ico"/>">
<style type="text/css">
<c:forEach varStatus="vs" var="result" items="${taskResults.resultList}">
input[name="resultList[${vs.index}].answer"] {
	display: none;
}

input[name="resultList[${vs.index}].answer"]+label {
	background:
		url("${pageContext.request.contextPath}/${result.image.path}")
		no-repeat;
	background-size: contain;
	height: ${result.image.height*4}px;
	width: ${result.image.width*4}px;
	opacity: 0.5;
	display: inline-block;
	padding: 0 0 0 0px;
}

input[name="resultList[${vs.index}].answer"]:checked+label {
	background:
		url("${pageContext.request.contextPath}/${result.image.path}")
		no-repeat;
		background-size: contain;
	height: ${result.image.height*4}px;
	width: ${result.image.width*4}px;
	opacity: 1;
	display: inline-block;
	padding: 0 0 0 0px;
}
</c:forEach>
</style> 
</head>

<body class="landing">
	<ul>
		<li>
			<h2>${student.name} ${student.surname}</h2>

			<h2>Stai svolgendo il task relativo al ${task.job.title}
				${task.job.symbol.transcription}</h2>
		</li>

	</ul>

	<div align="center">
		<h3>Spunta le immagini che ti sembrano simili agli esempi
			sottostanti</h3>

		<table class="pos">
			<tr>
				<c:forEach varStatus="vs" var="sample" items="${positiveSamples}">

					<td><img src="${pageContext.request.contextPath}/${sample.path}" alt="${sample.id}" /></td>
				</c:forEach>
			</tr>
		</table>

	</div>

	<div align="center">
		<h3>Attenzione: non vanno bene immagini come queste</h3>
		<table class="neg">
			<tr>
				<c:forEach varStatus="vs" var="sample" items="${negativeSamples}">
					<td><img src="${pageContext.request.contextPath}/${sample.path}" alt="${sample.id}" /></td>

				</c:forEach>
			</tr>
		</table>
	</div>


	<form:form method="post" action="secondConsoleWordSocial?social=${social}"
		modelAttribute="taskResults" name="form">


	<div align="center">
			<c:forEach varStatus="vs" var="result"
					items="${taskResults.resultList}"><!--
					<c:choose>
						<c:when test="${vs.index == 0}">
							<c:set var="previousWord" value="${result.image.word.name}"></c:set>
						</c:when>
						<c:when test="${vs.index > 0}">
							<c:if test="${previousWord ne result.image.word.name}">
								--><br/><!--
							</c:if>
							<c:set var="previousWord" value="${result.image.word.name}"></c:set>
						</c:when>
					</c:choose>-->
		     <input id="resultList[${vs.index}].id" name="resultList[${vs.index}].answer" name="foo" type="checkbox" value="Yes"/>
					<label for="resultList[${vs.index}].id"></label>
					<form:hidden path="resultList[${vs.index}].id"/>
					<form:hidden path="resultList[${vs.index}].image.id"/>
					<form:hidden path="resultList[${vs.index}].task.id"/>
					<form:hidden path="resultList[${vs.index}].task.studentsocial.id"/>
					<form:hidden path="resultList[${vs.index}].task.batch"/>
					<form:hidden path="resultList[${vs.index}].task.job.id"/>
					<form:hidden path="resultList[${vs.index}].task.startDate"/>
			</c:forEach>
		</div>

		<div align="center">
			<input type="submit" name="action"
				value="Conferma e vai al prossimo task">
		</div>

		<br />

		<div align="center">
			<input type="submit" name="action"
				value="Conferma e torna alla pagina dello studente">
		</div>

	</form:form>

	<div align="center">
		<form:form method="post" action="homeStudentSocial?social=${social}">
			<input type="submit" value="Torna alla pagina dello studente">
		</form:form>

	</div>


	<!-- Scripts -->
	<script src="${pageContext.request.contextPath}/js/selectAll.js"></script>

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