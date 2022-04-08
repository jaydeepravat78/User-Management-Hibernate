<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Forgot Password</title>
<link href="Assets/Libraries/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<!-- bootstrap -->

<link rel="stylesheet" href="Assets/Fonts/font.css"></link>
<link rel="stylesheet" href="Assets/CSS/forgot.css"></link>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
		<div class="error text-center">
			<c:if test="${not empty errorMessage}">
				<c:out value="${errorMessage}" />
			</c:if>
		</div>
		<form class="form-horizontal" action="ForgotController" method="post"
			id="forgot-form">
			<h2 class="text-center">Forgot Password</h2>
			<div class="form-group">
				<label for="inputEmail" class="col-sm-2 control-label">Email</label>
				<div class="col-sm-10">
					<input type="email" name="user_email" class="form-control"
						id="inputEmail" placeholder="Email" value="${errorEmail }">
				</div>
			</div>
			<div class="form-group">
				<label for="secQues" class="col-sm-2 control-label">Security
					Question</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="secQues" id="secQues"
						placeholder="What is the name of your first school?">
				</div>
			</div>
			<div class="form-group">
				<label for="inputGame" class="col-sm-2 control-label">Favorite
					Game</label>
				<div class="col-sm-10">
					<select id="inputGame" name="games" class="form-control">
						<option value="">Select</option>
						<option value="GTA">GTA</option>
						<option value="Fifa">Fifa</option>
						<option value="Battlefield">Battlefield</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label for="inputPassword" class="col-sm-2 control-label">New
					Password</label>
				<div class="col-sm-10">
					<input type="password" class="form-control" name="user_password"
						id="inputPassword" placeholder="********">
				</div>
			</div>
			<div class="form-group">
				<label for="inputConfirmPassword" class="col-sm-2 control-label">Confirm
					Password</label>
				<div class="col-sm-10">
					<input type="password" class="form-control" name="confirm_psw"
						id="inputConfirmPassword" placeholder="********">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<a href="index.jsp">Got password?</a>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-lg btn-success">Change
						Password</button>
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
	<script src="Assets/JS/forgot.js"></script>
	<!--  js script -->
</body>
</html>