package utility;

import java.io.InputStream;
import java.util.regex.Pattern;

import models.User;
import services.UserServiceImpl;
import services.UserService;

public class Validation {

	public static String error = "";
	private static User user;
	
	public static void getUser(User u) {
		user = u;
	}
	public static void checkName(String name) {
		if (name.isEmpty())
			error += "*Name field cannot be empty\n";
		else if (!Pattern.matches("^[a-zA-Z]+$", name))
			error += "*Name should only contains alphabet\n";

	}

	public static void checkEmail(String email) {
		UserService service = new UserServiceImpl();
		if (email.isEmpty())
			error += "*Email should not be empty\n";
		else if (!Pattern.matches("^(.+)@(.+)$", email))
			error += "*Please enter a valid email\n";
		else if (service.checkEmail(email))
			error += "*Email already exists\n";
	}

	public static void checkPhone(String phone) {
		if (phone.isEmpty())
			error += "*Phone should not be empty";
		else if (!Pattern.matches("[7-9][0-9]{9}", phone))
			error += "*Please enter a valid phone number\n";
	}

	public static void checkLang(String[] lang) {
		if (lang == null)
			error += "*Select atleast one language\n";
	}

	public static void checkGender(String gender) {
		if (gender == null)
			error += "*Please select gender\n";
	}

	public static void checkGame(String game) {
		if (game.isEmpty())
			error += "*Please select a game\n";
	}

	public static void checkSecQues(String secQues) {
		if (secQues.isEmpty())
			error += "*Please answer this question\n";
	}

	public static void checkPassword(String Password) {
		if (Password.isEmpty())
			error += "*Please enter a password";
		else if (!Pattern.matches("^[a-z0-9!@#$%^&*()_\\.\\-_]{8,30}$", Password))
			error += "*Password should have minimum 8 character\n";
	}

	public static void confirmPassword(String password, String confirmPassword) {
		if (password.equals(confirmPassword))
			error += "*Password doesn't match\n";

	}

	public static void checkStreet(String[] street) {
		for (int i = 0; i < street.length; i++)
			if (street[i].isEmpty())
				error += "*Please enter a street\n";
	}

	public static void checkCity(String[] city) {
		for (int i = 0; i < city.length; i++)
			if (city[i].isEmpty())
				error += "*Please enter a city\n";
	}

	public static void checkState(String[] state) {
		for (int i = 0; i < state.length; i++)
			if (state[i].isEmpty())
				error += "*Please enter a state\n";
	}

	public static void checkPhoto(InputStream photo) {
		if (photo == null)
			error += "*Please enter a photo\n";
	}
}
