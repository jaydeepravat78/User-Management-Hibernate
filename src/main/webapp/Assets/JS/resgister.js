$('#add-more').cloneData({
	mainContainerId: 'main-container', // Main container Should be ID
	cloneContainer: 'container-item', // Which you want to clone
	removeButtonClass: 'remove-item', // Remove button for remove cloned HTML
	removeConfirm: true, // default true confirm before delete clone item
	removeConfirmMessage: 'Are you sure want to delete?', // confirm delete message
	minLimit: 1, // Default 1 set minimum clone HTML required
	maxLimit: 0, // Default unlimited or set maximum limit of clone HTML
});

jQuery.validator.addMethod("name_regex", function(value, element) {
	return this.optional(element) || /^[a-zA-Z ]+$/i.test(value);
}, "Name with only a-z A-Z."); // check if a valid name
jQuery.validator.addMethod("phone_regex", function(value, element) {
	return this.optional(element) || /^[0-9\.\-_]{10}$/i.test(value);
}, "Phone Number with only 0-9. length: 10"); // phone number check
jQuery.validator.addMethod("password_regex", function(value, element) {
	return this.optional(element) || /^[a-z0-9!@#$%^&*()_\.\-_]{8,30}$/i.test(value);
}, "Password of atleast 8 characters."); // password check


$("#reg_form").validate({
	rules: {
		'user_name': {
			required: true,
			minlength: 2,
			name_regex: true,
		},
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
		'user_street[][street]' : {
			required: true,
		},
		'user_photo': {
			required: true,
		}
	},
	messages: {
		'user_name': {
			required: "*Please enter your name",
		},
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
		'user_street[][street]' : {
			required: "*Please enter your street",
		},
		'user_photo': {
			required: "*Please enter a photo",
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
		var user_name = $('input[name="user_name"]').val();
		var user_email = $('input[name="user_email"]').val();
		var user_password = $('input[name="user_password"]').val();
		var user_phone = $('input[name="user_phone"]').val();
		var user_gender = $('input[name="gender"]').val();
		var languages = new Array();
		$(form).find("input[type=checkbox]:checked").each(function() {
			languages.push(this.value);
		});
		var game = $("input[name='games']").val();
		var street = new Array();
		var city = new Array();
		var state = new Array();
		for (var i = 0; i < $('.container-item').length; i++) {
			street.push($(form).find('input[name="user_street[' + i + '][street]"').val());
			city.push($(form).find('input[name="user_city[' + i + '][city]"').val());
			state.push($(form).find('input[name="user_state[' + i + '][state]"').val());
		}
		var profile = $(form).find('input[name="user_photo"').val();
		$.ajax({
			type: "post",
			url: "Register",
			data: {
				"user_name": user_name,
				"user_email": user_email,
				"user_password": user_password,
				"user_phone": user_phone,
				"user_gender": user_gender,
				"user_lang": languages,
				"user_street": street,
				"user_city": city,
				"user_state": state,
				"user_game": game,
				"user_pic": profile,
			},
			success: function(response) {
				window.location.href = "index.jsp";
			},
			error: function(xhr, ajaxOptions, thrownError) {
				console.log(xhr.status + " " + thrownError);
			}
		});
	}
});

