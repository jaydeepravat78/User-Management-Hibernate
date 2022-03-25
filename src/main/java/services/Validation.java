package services;

public class Validation {
	public static boolean empty(String ... values) {
		for(int i = 0; i < values.length; i++)
			if(values[i].isEmpty())
				return false;
		return true;
	}
}
