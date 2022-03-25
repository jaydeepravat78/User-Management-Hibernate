<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Forgot Password</title>
<link href="Assets/Libraries/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<!-- bootstrap -->

<link rel="stylesheet" href="Assets/Fonts/font.css"></link>
<style>
html, body {
	margin: 0;
	padding: 0;
	font-family: 'Poppins', sans-serif;
}

form {
	width: 70%;
	display: block;
	margin: 20px auto;
	background-color: aliceblue;
	padding: 20px;
	border-radius: 20px;
}

h1 {
	text-transform: uppercase;
}
</style>
</head>
<body>
	<div class="container">
		<h1 class="text-center">Forgot Password</h1>
		<form class="form-horizontal" action="#">
			<div class="form-group">
				<label for="inputEmail" class="col-sm-2 control-label">Email</label>
				<div class="col-sm-10">
					<input type="email" name="user_email" class="form-control"
						id="inputEmail" placeholder="Email">
				</div>
			</div>
			<div class="form-group">
				<label for="inputLang" class="col-sm-2 control-label">Language</label>
				<div class="col-sm-6">
					<label class="checkbox-inline"> <input type="checkbox"
						id="inputLang1" name="lang" value="Java"> Java
					</label> <label class="checkbox-inline"> <input type="checkbox"
						id="inputLang2" name="lang" value="C++"> C++
					</label> <label class="checkbox-inline"> <input type="checkbox"
						id="inputLang3" name="lang" value="Python"> Python
					</label> <label class="checkbox-inline"> <input type="checkbox"
						id="inputLang4" name="lang" value="Kotlin"> Kotlin
					</label>
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
				<label for="inputPassword" class="col-sm-2 control-label">New Password</label>
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
					<button type="submit" class="btn btn-lg btn-success">Change Password</button>
				</div>
			</div>
		</form>
	</div>
	<script src="Assets/JS/jquery-3.6.0.min.js"></script>
	<!-- jquery -->
	<script src="Assets/Libraries/bootstrap/js/bootstrap.min.js"></script>
	<!--  bootstrap -->
</body>
</html>