
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>


<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
>

<head>
<meta charset="utf-8">

<title>ICR</title>

<!--[if lte IE 8]><script src="/js/ie/html5shiv.js"></script><![endif]-->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/registration.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/facebookGoogleButtons.css" />



<!--[if lte IE 8]><link rel="stylesheet" href="/css/ie8.css" /><![endif]-->
<!--[if lte IE 9]><link rel="stylesheet" href="/css/ie9.css" /><![endif]-->


<!-- Favicon -->
<link rel="shortcut icon"
	href="<c:url value="${pageContext.request.contextPath}/img/siteImages/favicon.ico"/>">
<script type="text/javascript" src="${pageContext.request.contextPath}/webjars/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/webjars/bootstrap/js/bootstrap.min.js"></script>
	
</head>

<body class="landing" >
	<jsp:include page="../menu.jsp" />
	<div class="form">
		<div class="tab-content" style='margin-top:-10px'>
			<div id="signup">
				<h1>Login</h1>

				<form method="POST" action="${pageContext.request.contextPath}/login">
					<div class="top-row">
						<div class="field-wrap">
							<label> Username </label> <input type='text' name='username'
								placeholder="Username" />
								<font size="3" color="red">${error}</font>
						</div>

						<div class="field-wrap">
							<label> Password </label> <input type='password'
								name='password' placeholder="Password">
						</div>

						<button type="submit" class="button button-block" style='cursor:pointer'>Invia</button>
					</div>
		</form>
		
		</div>
		
		<div>
		
		


		<form class="form-inline1" action="${pageContext.request.contextPath}/connect/facebook" method="POST" >
			<input type="hidden" name="scope" value="email" />
			
			
			<div class="social-wrap c">
		 <button class="facebook" type="submit" style='cursor:pointer'>Accedi con Facebook</button> </div>
		 <input type="hidden" name="daFB" />
		</form>
		
		<form class="form-inline2" action="${pageContext.request.contextPath}/connect/google" method="POST" >
			<input type="hidden" name="scope" value="email" />
			
			<div class="social-wrap c">
		<button class="googleplus" type="submit" style='cursor:pointer' >Accedi con Google+</button> </div>
		<input type="hidden" name="daGoogle" />
		</form>
		
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
		<script src="${pageContext.request.contextPath}/js/backButton.js"></script>

</body>
</html>