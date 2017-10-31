<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="springForm"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%@ page import="it.uniroma3.icr.model.Symbol"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ICR</title>

<!--[if lte IE 8]><script src="/js/ie/html5shiv.js"></script><![endif]-->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/registration.css" />
<!--[if lte IE 8]><link rel="stylesheet" href="/css/ie8.css" /><![endif]-->
<!--[if lte IE 9]><link rel="stylesheet" href="/css/ie9.css" /><![endif]-->

<!-- Favicon -->
<link rel="shortcut icon"
	href="<c:url value="${pageContext.request.contextPath}/img/siteImages/favicon.ico"/>">

</head>
<body class="landing">
	<jsp:include page="menu.jsp" />
	<br>
	<h1>Inserimento Manoscritto nel database</h1>
	<div class="form">
			<div class="tab-content">
				<div id="signup">
					<h1>Seleziona Manoscritto</h1>
					<form:form method="post" action="insertManuscript" modelAttribute="manuscript" name="form">
						<div class="field-wrap">
							<div class="field-wrap">
								<label> Manoscritto </label>
								<form:select path="name">

									<form:options items="${manuscripts}" />

								</form:select>
							</div>

							<div id="formsubmitbutton">

								
								<button type="submit" class="button button-block"
									onclick="ButtonClicked()" name="action" value="WORD"> PROSEGUI CON PAROLE</button>
								
							</div>
						</div>
					</form:form>

				</div>

				<div id="buttonreplacement"
					style="margin-left: 60px; display: none;">

					<img src="${pageContext.request.contextPath}/img/siteImages/loadIcon.gif" alt="">

				</div>

				<div id="login"></div>

			</div>
			<!-- tab-content -->

		</div>
		<!-- /form -->



		<!-- Scripts -->
		<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.scrollex.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.scrolly.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/skel.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/util.js"></script>
		<!--[if lte IE 8]>-->
		<script src="${pageContext.request.contextPath}/js/ie/respond.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/main.js"></script>
		<script src="${pageContext.request.contextPath}/js/file.js"></script>
		<script src="${pageContext.request.contextPath}/js/load.js"></script>
</body>
</html>