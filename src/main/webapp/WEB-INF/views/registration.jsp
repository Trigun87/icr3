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
<meta charset="utf-8">
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
	<div class="form">

		<div class="tab-content">
			<div id="signup">
				<h1>Registrati</h1>

				<form:form method="post" action="addUser" modelAttribute="student"
					name="f1" onsubmit="return matchpass()">

					<div class="top-row">
						<div class="field-wrap">
							<label> Nome </label>
							<form:input type="text" path="name" placeholder="Nome" value="${nome}" />
							<font size="3" color="red">${errName}</font>
							
						</div>

						<div class="field-wrap">
							<label> Cognome </label>
							<form:input type="text" path="surname" placeholder="Cognome" value="${cognome}" />
							<font size="3" color="red">${errSurname}</font>
						</div>

						<div class="field-wrap">
							<label> Email </label>
							<form:input type="text" path="email" placeholder="Email" value="${Email}" />
							<font size="3" color="red">${errEmail}</font>
						</div>
						
						<div class="field-wrap">
							<label> Scuola </label>
							<form:select type="text" path='school'>
										<form:options items="${schools}" />
							</form:select>	
						</div>

						<div class="field-wrap">
							<label> Anno </label>
							<form:select path="schoolGroup">
								<form:options items="${schoolGroups}" />
							</form:select>
						</div>

						<div class="field-wrap">
							<label> Sezione </label>
							<form:input type="text" path='section' placeholder="Sezione" />
							<font size="3" color="red">${errSection}</font>
						</div>

						<div class="field-wrap">
							<label> Username </label>
							<form:input type="text" path='username' placeholder="Username" value="${email}" />
							<font size="3" color="red">${usernameError}</font>
						</div>

						<div class="field-wrap">
							<label> Password </label>
							<form:input type="password" path='password'
								placeholder="Password" name="password" />
								<font size="3" color="red">${errPassword}</font>
						</div>
						<div class="field-wrap">
							<label> Conferma Password </label> <input type="password"
								placeholder="Conferma Password" name="password2" />
						</div>

						<font color="white"><springForm:errors path="username" /></font>
						<font color="white"><springForm:errors path="password" /></font>

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
	<!--[if lte IE 8]><script src="/js/ie/respond.min.js"></script><![endif]-->
	<script src="${pageContext.request.contextPath}/js/main.js"></script>
	<script src="${pageContext.request.contextPath}/js/checkPassword.js"></script>




</body>
</html>