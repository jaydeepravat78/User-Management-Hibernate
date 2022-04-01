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
<title>Home</title>
<link href="Assets/Libraries/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<!-- bootstrap -->

<link rel="stylesheet" href="Assets/Fonts/font.css"></link>

<style>
	.profile {
		width: 250px;
		height: 250px;
		max-height: 250px;
		display: block;
		margin: 30px auto;
	}
</style>
</head>
<body>
	<jsp:include page="header.html"></jsp:include>
	<c:if test="${sessionScope.user != null}">
		<div class="container text-center">
			<h2 class="text-center">
				Welcome
				<c:out value="${sessionScope.user.getName()}" />
			</h2>
			<div>
				<img class="img-circle img-thumbnail img-responsive profile" alt="profilePic" src="data:image/jpg;base64,${sessionScope.user.getBase64Photo()}">
			</div>
			<a href="LogoutController" class="btn btn-danger">Logout</a> <a
				class="btn btn-primary" href="registration.jsp">Edit Profile</a>
		</div>
	</c:if>
	<c:if test="${sessionScope.user == null}">
		<c:redirect url="index.jsp" />
	</c:if>
	<jsp:include page="footer.html"></jsp:include>
	<script src="Assets/JS/jquery-3.6.0.min.js"></script>
	<!-- jquery -->
	<script src="Assets/Libraries/bootstrap/js/bootstrap.min.js"></script>
	<!--  bootstrap -->
</body>
</html>