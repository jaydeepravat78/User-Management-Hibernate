jQuery.validator.addMethod("password_regex", function(value, element) {
	return this.optional(element) || /^[a-z0-9!@#$%^&*()_\.\-_]{8,30}$/i.test(value);
});


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
			password_regex: "*Password should contain atlease 8 characters",
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