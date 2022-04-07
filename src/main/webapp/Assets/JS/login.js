$("#loginForm").validate({
	rules: {
		'user_email': {
			required: true,
			email: true,
		},
		'user_password': {
			required: true,
		},
	},
	messages: {
		'user_email': {
			required: "*Please enter your email",
			email: "*Please enter a valid email address!",
		},
		'user_password': {
			required: "*Please enter a password",
		},
	}
});