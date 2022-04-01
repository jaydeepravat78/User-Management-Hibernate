<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
	<jsp:include page="header.jsp"></jsp:include>

	<div class="container">
		<div class="error text-center">
			<c:if test="${not empty error}">
				<c:out value="${error}" />
			</c:if>
		</div>
		<form class="form-horizontal" id="reg_form" method="post"
			action='<c:if test="${requestScope.userData != null}">UpdateController?id=${requestScope.userData.getId()}</c:if><c:if test="${requestScope.userData == null}">RegisterController</c:if>'
			enctype="multipart/form-data">
			<h2 class="text-center">
				<c:if test="${requestScope.userData != null}">Edit Profile</c:if>
				<c:if test="${requestScope.userData == null}">Register</c:if>
			</h2>
			<div class="form-group">
				<label for="inputName" class="col-sm-2 control-label">Name</label>
				<div class="col-sm-10">
					<input type="text" name="user_name" class="form-control"
						id="inputName"
						value='<c:out value="${requestScope.userData.getName()}" />'
						placeholder="Raj">
				</div>
			</div>
			<div class="form-group">
				<label for="inputEmail" class="col-sm-2 control-label">Email</label>
				<div class="col-sm-10">
					<input type="text" name="user_email" class="form-control"
						id="inputEmail"
						value='<c:out value="${requestScope.userData.getEmail()}" />'
						placeholder="raj@gmail.com"
						<c:if test="${requestScope.userData != null }">disabled</c:if>>
				</div>
			</div>
			<div class="form-group">
				<label for="inputPassword" class="col-sm-2 control-label">Password</label>
				<div class="col-sm-10">
					<input type="password" class="form-control" name="user_password"
						id="inputPassword"
						value='<c:out value="${requestScope.userData.getPassword()}" />'
						placeholder="********">
				</div>
			</div>
			<div class="form-group">
				<label for="inputConfirmPassword" class="col-sm-2 control-label">Confirm
					Password</label>
				<div class="col-sm-10">
					<input type="password" class="form-control" name="confirm_psw"
						id="inputConfirmPassword"
						value='<c:out value="${requestScope.userData.getPassword()}" />'
						placeholder="********">
				</div>
			</div>
			<div class="form-group">
				<label for="inputPhone" class="col-sm-2 control-label">Phone
					number</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="user_phone"
						id="inputPhone"
						value='<c:out value="${requestScope.userData.getPhone()}" />'
						placeholder="1234567890">
				</div>
			</div>
			<div class="form-group">
				<label for="inputGender" class="col-sm-2 control-label">Gender</label>
				<div class="col-sm-6">
					<label class="radio-inline"> <input type="radio"
						id="inputGender1" name="gender" value="male"
						<c:if test="${requestScope.userData.getGender() == 'male'}"> Checked </c:if> />
						Male
					</label> <label class="radio-inline"> <input type="radio"
						id="inputGender2" name="gender" value="female"
						<c:if test="${requestScope.userData.getGender() == 'female'}">Checked</c:if> />
						Female
					</label>
				</div>
			</div>
			<div class="form-group">
				<label for="inputLang" class="col-sm-2 control-label">Language</label>
				<div class="col-sm-6">
					<label class="checkbox-inline"> <input type="checkbox"
						id="inputLang1" name="lang" value="Java"
						<c:forEach var="language" items="${requestScope.userData.getLang()}"> <c:if test="${language eq 'Java'}">checked='checked'</c:if> </c:forEach>>
						Java
					</label> <label class="checkbox-inline"> <input type="checkbox"
						id="inputLang2" name="lang" value="C++"
						<c:forEach var="language" items="${requestScope.userData.getLang()}"> <c:if test="${language eq 'C++'}">checked='checked'</c:if> </c:forEach>>
						C++
					</label> <label class="checkbox-inline"> <input type="checkbox"
						id="inputLang3" name="lang" value="Python"
						<c:forEach var="language" items="${requestScope.userData.getLang()}"> <c:if test="${language eq 'Python'}">checked='checked'</c:if> </c:forEach>>
						Python
					</label> <label class="checkbox-inline"> <input type="checkbox"
						id="inputLang4" name="lang" value="Kotlin"
						<c:forEach var="language" items="${requestScope.userData.getLang()}"> <c:if test="${language eq 'Kotlin'}">checked='checked'</c:if> </c:forEach>>
						Kotlin
					</label>
				</div>
			</div>
			<div class="form-group">
				<label for="inputGame" class="col-sm-2 control-label">Favorite
					Game</label>
				<div class="col-sm-10">
					<select id="inputGame" name="games" class="form-control">
						<option value="">Select</option>
						<option value="GTA"
							<c:if test="${requestScope.userData.getGame() == 'GTA'}"> <%= "selected='selected'" %> </c:if>>GTA</option>
						<option value="Fifa"
							<c:if test="${requestScope.userData.getGame() == 'Fifa'}"> <%= "selected='selected'" %> </c:if>>Fifa</option>
						<option value="Battlefield"
							<c:if test="${requestScope.userData.getGame() == 'Battlefield'}"> <%= "selected='selected'" %> </c:if>>Battlefield</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label for="secQues" class="col-sm-2 control-label">Security
					Question</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="secQues" id="secQues"
						placeholder="What is the name of your first school?"
						value="${requestScope.userData.getSecQues()}">
				</div>
			</div>
			<div id="main-container">
				<div class="container-item">
					<c:if test="${requestScope.userData != null }">
						<c:forEach var="address"
							items="${requestScope.userData.getAddresses()}">
							<div class="form-group">
								<label for="inputAddress" class="col-sm-2 control-label">Address</label>
								<div class="col-sm-10">
									<div class="col-sm-4">
										<input type="text" name="user_street" class="form-control"
											placeholder="Street" value="${address.getStreet()}">
									</div>
									<div class="col-sm-3">
										<input type="text" name="user_city" class="form-control"
											placeholder="City" value="${address.getCity()}">
									</div>
									<div class="col-sm-3">
										<input type="text" name="user_state" class="form-control"
											placeholder="State" value="${address.getState()}">
									</div>
									<div class="col-sm-2">
										<a href="javascript:void(0)"
											class="remove-item btn btn-sm btn-danger">Delete</a>
									</div>
								</div>
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${requestScope.userData == null}">
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
					</c:if>
				</div>
			</div>
			<div class="col-sm-offset-2 col-sm-10">
				<a id="add-more" href="javascript:;"
					class="btn btn-sm btn-warning addbtn">Add</a>
			</div>
			<div class="form-group">
				<label for="inputPhoto" class="col-sm-2 control-label">Photo</label>
				<div class="col-sm-5">
					<input type="file" name="user_photo" class="btn"
						value="data:image/jpg;base64,${requestScope.userData.getBase64Photo()}">
				</div>
				<c:if test="${requestScope.userData != null }">
					<div class="col-sm-5">

						<img class="img-circle img-thumbnail img-responsive"
							alt="profilePic"
							src="data:image/jpg;base64,${requestScope.userData.getBase64Photo()}">
					</div>
				</c:if>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<c:choose>
						<c:when test="${sessionScope.admin != null}">
							<a href="dashboard.jsp">Dashboard</a>
						</c:when>
						<c:when test="${requestScope.userData != null }">
							<a href="home.jsp">Home</a>
						</c:when>
						<c:when test="${requestScope.userData == null}">
							<a href="index.jsp">Already have an account?</a>
						</c:when>
					</c:choose>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-lg btn-success">
						<c:if test="${requestScope.userData != null }">
					Update</c:if>
						<c:if test="${requestScope.userData == null }">Register</c:if>
					</button>
					<button type="reset" class="btn btn-lg btn-danger btn-reset">Reset</button>
				</div>
			</div>
		</form>
	</div>
	<jsp:include page="footer.html"></jsp:include>
	<script src="Assets/JS/jquery-3.6.0.min.js"></script>
	<!-- jquery -->
	<script src="Assets/Libraries/clonedata/cloneData.js"></script>
	<!-- clone data -->
	<script src="Assets/Libraries/bootstrap/js/bootstrap.min.js"></script>
	<!-- bootstrap jquery -->
	<script src="Assets/Libraries/validate/jquery.validate.min.js"></script>
	<!-- validate js -->
	<script src="Assets/JS/resgister.js"></script>
	<!--  js script -->
</body>
</html>