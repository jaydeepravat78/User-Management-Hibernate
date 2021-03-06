
jQuery.validator.addMethod("name_regex", function(value, element) {
	return this.optional(element) || /^[a-zA-Z ]+$/i.test(value);
}, "*Name with only a-z A-Z."); // check if a valid name
jQuery.validator.addMethod("phone_regex", function(value, element) {
	return this.optional(element) || /^[7-9][0-9]{9}$/i.test(value);
}, "*Phone Number with only 0-9 and should start with 7-9. length: 10"); // phone number check
jQuery.validator.addMethod("password_regex", function(value, element) {
	return this.optional(element) || /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,30}$/i.test(value);
}, "*Password should have minimum 8 and maximum 30 character with a number, alphabet character and a special character"); // password check 
jQuery.validator.addMethod("validate_email", function(value, element) {
	return this.optional(element) || /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/i.test(value);	
}, "Please enter a valid Email.");

$(document).ready(function () {
	

$('#add-more').cloneData({
	mainContainerId: 'main-container', // Main container Should be ID
	cloneContainer: 'container-item', // Which you want to clone
	removeButtonClass: 'remove-item', // Remove button for remove cloned HTML
	removeConfirm: true, // default true confirm before delete clone item
	removeConfirmMessage: 'Are you sure want to delete?', // confirm delete message
	minLimit: 1, // Default 1 set minimum clone HTML required
	maxLimit: 0, // Default unlimited or set maximum limit of clone HTML
});


$("#reg_form").validate({
	rules: {
		'user_name': {
			required: true,
			minlength: 2,
			name_regex: true,
		},
		'user_email': {
			required: true,
			validate_email: true,
			remote: {
				url: "EmailCheckController",
				type: "post",
			}
		},
		'user_password': {
			required: true,
			password_regex: true,
		},
		'confirm_psw': {
			required: true,
			equalTo: '#inputPassword',
		},
		'user_phone': {
			required: true,
			phone_regex: true,
		},
		'gender': {
			required: true,
		},
		'lang': {
			required: true,
		},
		'games': {
			required: true,
		},
		'user_street' : {
			required: true,
		},
		'user_city' : {
			required: true,
		},
		'user_state' : {
			required: true,
		},
		'secQues': {
			required: true
		}
	},
	messages: {
		'user_name': {
			required: "*Please enter your name",
		},
		'user_email': {
			required: "*Please enter your email",
			remote: "*Email already exists",
		},
		'user_password': {
			required: "*Please enter a password",
		},
		"confirm_psw": {
			required: "*Please confirm the password",
			equalTo: "*Doesn't match with password"
		},
		"user_phone": {
			required: "*Please enter your phone number",
		},
		'gender': {
			required: "*Please enter your gender",
		},
		'lang': {
			required: "*Select atleast one language",
		},
		'games': {
			required: "*Please select a game",
		},
		'user_street' : {
			required: "*Please enter your street",
		},
		'user_city' : {
			required: "*Please enter your city",
		},
		'user_state' : {
			required: "*Please enter your State",
		},
		'secQues': {
			required: "*Please answer the security question",
		}
	},
	errorPlacement: function(error, element) {
		if (element.is(":radio") || element.is(":checkbox")) {
			error.appendTo(element.parents('.form-group'));
		}
		else {
			error.insertAfter(element);
		}
	},
	submitHandler: function(form) {
		form.submit();
	}
});
});