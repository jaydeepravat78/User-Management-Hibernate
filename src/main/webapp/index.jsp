<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false" session="true"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Log In</title>
<link href="Assets/Libraries/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<!-- bootstrap -->

<link rel="stylesheet" href="Assets/Fonts/font.css"></link>
<link type="text/css" rel="stylesheet" href="Assets/CSS/custom.css"></link>

</head>
<body>
	<c:if test="${sessionScope.user != null}">
		<c:redirect url="home.jsp" />
	</c:if>
	<c:if test="${sessionScope.admin != null}">
		<c:redirect url="dashboard.jsp"></c:redirect>
	</c:if>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">

		<div class="error text-center">
			<c:if test="${not empty errorMessage}">
				<c:out value="${errorMessage}" />
			</c:if>
		</div>
		<form class="form-horizontal uform" action="LoginController"
			method="Post" id="loginForm">
			<h2 class="text-center">Login</h2>
			<div class="form-group">
				<label for="inputEmail" class="col-sm-2 control-label">Email</label>
				<div class="col-sm-10">
					<input type="email" name="user_email" class="form-control"
						id="inputEmail" placeholder="abc@gmail.com">
				</div>
			</div>
			<div class="form-group">
				<label for="inputPassword" class="col-sm-2 control-label">Password</label>
				<div class="col-sm-10">
					<input type="password" name="user_password" class="form-control"
						id="inputPassword" placeholder="********">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<a href="forgot.jsp">Forgot Password?</a>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<a href="registration.jsp">Create a new account</a>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<input type="submit" class="btn btn-lg btn-success" value="Log in">
				</div>
			</div>
		</form>
	</div>
	<jsp:include page="footer.html"></jsp:include>
	<script src="Assets/JS/jquery-3.6.0.min.js"></script>
	<!-- jquery -->
	<script src="Assets/Libraries/bootstrap/js/bootstrap.min.js"></script>
	<!--  bootstrap -->

	<script src="Assets/Libraries/validate/jquery.validate.min.js"></script>
	<!-- validate js -->
	<script src="Assets/JS/login.js"></script>
</body>
</html>