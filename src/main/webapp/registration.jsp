<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<link>
<meta charset="ISO-8859-1">
<title>Registration</title>
<link href="Assets/Libraries/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
    
<link rel="stylesheet" href="Assets/Fonts/font.css"></link>
<!-- bootstrap -->
<link rel="stylesheet" href="Assets/CSS/register.css"></link>
</head>
<body>
	<div class="container">
		<h1 class="text-center">Register</h1>
		<form class="form-horizontal" id="reg_form" method="post" action="Register">
			<div class="form-group">
				<label for="inputName" class="col-sm-2 control-label">Name</label>
				<div class="col-sm-10">
					<input type="text" name="user_name" class="form-control"
						id="inputName" placeholder="Raj">
				</div>
			</div>
			<div class="form-group">
				<label for="inputEmail" class="col-sm-2 control-label">Email</label>
				<div class="col-sm-10">
					<input type="email" name="user_email" class="form-control"
						id="inputEmail" placeholder="raj@gmail.com">
				</div>
			</div>
			<div class="form-group">
				<label for="inputPassword" class="col-sm-2 control-label">Password</label>
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
				<label for="inputPhone" class="col-sm-2 control-label">Phone
					number</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="user_phone"
						id="inputPhone" placeholder="1234567890">
				</div>
			</div>
			<div class="form-group">
				<label for="inputGender" class="col-sm-2 control-label">Gender</label>
				<div class="col-sm-6">
					<label class="radio-inline"> <input type="radio"
						id="inputGender1" name="gender" value="male"> Male
					</label> <label class="radio-inline"> <input type="radio"
						id="inputGender2" name="gender" value="female"> Female
					</label>
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
			<div id="main-container">
				<div class="container-item">
					<div class="form-group">
						<label for="inputAddress" class="col-sm-2 control-label">Address</label>
						<div class="col-sm-10">
							<div class="col-sm-4">
								<input type="text" name="user_street" class="form-control"
									placeholder="Street">
							</div>
							<div class="col-sm-3">
								<input type="text" name="user_city" class="form-control"
									placeholder="City">
							</div>
							<div class="col-sm-3">
								<input type="text" name="user_state" class="form-control"
									placeholder="State">
							</div>
							<div class="col-sm-2">
								<a href="javascript:void(0)"
									class="remove-item btn btn-sm btn-danger">Delete</a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-offset-2 col-sm-10">
				<a id="add-more" href="javascript:;" class="btn btn-sm btn-warning addbtn">Add</a>
			</div>
			<div class="form-group">
				<label for="inputPhoto" class="col-sm-2 control-label">Photo</label>
				<div class="col-sm-10">	
					<input type="file" name="user_photo" class="btn btn-default">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<a href="index.jsp">Already have an account?</a>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-lg btn-success">Register</button>
				</div>
			</div>
		</form>
	</div>

	<script
		src="Assets/JS/jquery-3.6.0.min.js"></script>
	<!-- jquery -->
	<script src="Assets/Libraries/clonedata/cloneData.js"></script>
	<!-- clone data -->
	<script src="Assets/Libraries/bootstrap/js/bootstrap.min.js"></script>
	<!-- bootstrap jquery -->
	<script
		src="Assets/Libraries/validate/jquery.validate.min.js"></script>
	<!-- validate js -->
	<script src="Assets/JS/resgister.js"></script>
	<!--  js script -->
</body>
</html>