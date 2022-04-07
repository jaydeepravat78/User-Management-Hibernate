jQuery.validator.addMethod("password_regex", function(value, element) {
	return this.optional(element) || /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,30}$/i.test(value);
}, "*Password should have minimum 8 and maximum 30 character (1 [a-z A-Z 0-9 special character])"); // password check 

$("#forgot-form").validate({
	rules: {
		'user_email': {
			required: true,
			email: true,
		},
		'user_password': {
			required: true,
			password_regex: true,
		},
		'confirm_psw': {
			required: true,
			equalTo: '#inputPassword',
		},
		'games': {
			required: true,
		},
		'secQues': {
			required: true,
		}
	},
	messages: {
		'user_email': {
			required: "*Please enter your email",
			email: "*Please enter a valid email address!",
		},
		'user_password': {
			required: "*Please enter a password",
		},
		"confirm_psw": {
			required: "*Please confirm the password",
			equalTo: "*Doesn't match with password"
		},
		'games': {
			required: "*Please select a game",
		},
		'secQues': {
			required: "*Please answer this question",
		}
	},
	submitHandler: function(form) {
		form.submit();
	}
});