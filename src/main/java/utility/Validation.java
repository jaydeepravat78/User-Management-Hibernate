package utility;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import services.UserServiceImpl;
import services.UserService;

public class Validation {
	public static Map<String, String> error = new HashMap<>();

	public static void checkName(String name) {
		if (name.isEmpty())
			error.put("user_name", "*Name field cannot be empty");
		else if (!Pattern.matches("^[a-zA-Z]+$", name))
			error.put("user_name", "*Name should only contains alphabet");

	}

	public static void checkEmail(String email) {
		UserService service = new UserServiceImpl();
		if (email.isEmpty())
			error.put("user_email", "*Email should not be empty");
		else if (!Pattern.matches("^(.+)@(.+)$", email))
			error.put("user_email", "*Please enter a valid email");
		else if (service.checkEmail(email))
			error.put("user_email", "*Email already exists");
	}

	public static void checkPhone(String phone) {
		if (phone.isEmpty())
			error.put("user_phone", "*Phone should not be empty");
		else if (!Pattern.matches("[7-9][0-9]{9}", phone))
			error.put("user_phone", "*Please enter a valid phone number");
	}

	public static void checkLang(String[] lang) {
		if (lang == null)
			error.put("lang", "*Select atleast one language");
	}

	public static void checkGender(String gender) {
		if (gender == null)
			error.put("gender", "*Please select gender");
	}

	public static void checkGame(String game) {
		if (game.isEmpty())
			error.put("games", "*Please select a game");
	}

	public static void checkSecQues(String secQues) {
		if (secQues.isEmpty())
			error.put("secQues", "*Please answer this question");
	}

	public static void checkPassword(String Password) {
		if (Password.isEmpty())
			error.put("user_password", "*Please enter a password");
		else if (!Pattern.matches("^[a-z0-9!@#$%^&*()_\\.\\-_]{8,30}$", Password))
			error.put("user_password", "*Password should have minimum 8 character");
	}

	public static void confirmPassword(String password, String confirmPassword) {
		if (password.equals(confirmPassword))
			error.put("confirm_psw", "*Password doesn't match");

	}

	public static void checkStreet(String[] street) {
		for (int i = 0; i < street.length; i++)
			if (street[i].isEmpty())
				error.put("user_street", "*Please enter a street");
	}

	public static void checkCity(String[] city) {
		for (int i = 0; i < city.length; i++)
			if (city[i].isEmpty())
				error.put("user_city", "*Please enter a city");
	}

	public static void checkState(String[] state) {
		for (int i = 0; i < state.length; i++)
			if (state[i].isEmpty())
				error.put("user_state", "*Please enter a state");
	}

	public static void checkPhoto(InputStream photo) {
		if (photo == null)
			error.put("user_photo", "*Please enter a photo");
	}
}
