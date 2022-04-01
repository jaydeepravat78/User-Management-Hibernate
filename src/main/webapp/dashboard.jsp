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
<title>Dashboard</title>

<link href="Assets/Libraries/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<!-- bootstrap -->

<link rel="stylesheet" href="Assets/Fonts/font.css">
<!-- font -->
<link rel="stylesheet"
	href="Assets/Libraries/datatable/datatables.min.css">
<!--  datatable -->
<style>
html, body {
	margin: 0;
	padding: 0;
	font-family: 'Poppins', sans-serif;
}
#users-table .odd {
	background-color: aliceblue;
}
#users-table thead {
	background-color: #5cb85c;
	color: white;
}
#users-table tbody tr:hover {
	background-color: #f6f6f6;
}
</style>
</head>
<body>
	<c:if test="${sessionScope.admin != null}">
		<jsp:include page="header.html"></jsp:include>
		<div class="container">
			<table id="users-table"
				class="table table-striped">
				<!-- cell-border hover stripe -->
				<!-- Table for data -->
				<thead>
					<tr>
						<th>ID</th>
						<th>Name</th>
						<th>Email</th>
						<th>Phone</th>
						<th>Gender</th>
						<th>Game</th>
						<th>Edit</th>
						<th>Delete</th>
					</tr>
					<!-- Headings -->
				</thead>
				<tbody>
				</tbody>
			</table>
			<a href="registration.jsp" class="btn btn-lg btn-success">Add
				User</a> <a href="#" class="btn btn-lg btn-primary">Add Users from
				excel</a> <a href="LogoutController" class="btn btn-lg btn-danger">Logout</a>
		</div>
	</c:if>
	<jsp:include page="footer.html"></jsp:include>
	<c:if test="${sessionScope.admin == null}">
		<c:redirect url="index.jsp" />
	</c:if>
	<script src="Assets/JS/jquery-3.6.0.min.js"></script>
	<!-- jquery -->
	<script src="Assets/Libraries/bootstrap/js/bootstrap.min.js"></script>
	<!--  bootstrap -->
	<script src="Assets/Libraries/datatable/datatables.min.js"></script>
	<!-- datatable -->
	<script src="Assets/JS/dashboard.js"></script>
</body>
</html>