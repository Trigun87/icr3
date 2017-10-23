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

	<!-- Page Wrapper -->
	<div id="page-wrapper">

		<!-- Header -->
		<header id="header" class="alt">
		
			<nav id="nav">
				<ul>
					<li class="special"><a href="#menu" class="menuToggle"><span>Menu</span></a>
						<div id="menu">
							<ul>
								<li><a href="logout">Logout</a></li>
								<li><a href="homeAdmin">Torna alla HomePage</a></li>
							</ul>
						</div></li>
				</ul>
			</nav>
		</header>
	</div>

	<div class="form">
      
    
      
      <div class="tab-content">
        <div id="signup">   
          <h1>Registrazione Admin</h1>
          
          <form:form method="post" action="addAdmin" modelAttribute="administrator" name="form">
          
          
          
          <div class="field-wrap">
            <label>
              Username	
            </label>
					<form:input type="text" path='username' placeholder="Username" />
					<font size="3" color="red">${usernameError}</font>
					${errUsername}
          </div>
          
          <div class="field-wrap">
            <label>
              Password
            </label>
					<form:input type="password" path='password' placeholder="Password" />
					${errPassword}
					
          </div>
          
          <font color="white"><springForm:errors path="username" /></font> <font
					color="white"><springForm:errors path="password" /></font>
          
          <button type="submit" class="button button-block">Conferma</button> 
          </form:form>
          </div>
          
         

        
        <div id="login">   
         
        </div>
        
      </div><!-- tab-content -->
      
</div> <!-- /form -->
      
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