jQuery.validator.addMethod("validate_email", function(value, element) {
	return this.optional(element) || /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/i.test(value);	
}, "Please enter a valid Email.");
$("#loginForm").validate({
	rules: {
		'user_email': {
			required: true,
			validate_email: true,
		},
		'user_password': {
			required: true,
		},
	},
	messages: {
		'user_email': {
			required: "*Please enter your email",
			validate_email: "*Please enter a valid email address!",
		},
		'user_password': {
			required: "*Please enter a password",
		},
	}
});