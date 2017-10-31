<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="springForm"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<body>
<body class="landing">
	<jsp:include page="menu.jsp" />

	<div class="form">



		<div class="tab-content">
			<div id="signup">
				<h1>Seleziona lo studente</h1>

				<form:form method="post" action="selectStudent" modelAttribute="student"
					name="form">

					<div class="field-wrap">

						<div class="field-wrap">
							<label> Studente </label>
							<form:select path="surname">

								<form:options items="${students}" />

							</form:select>
						</div>

							<button type="submit" class="button button-block">Conferma</button>
					</div>

				</form:form>

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


</body>
</html>