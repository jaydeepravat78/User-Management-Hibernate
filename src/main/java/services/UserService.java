package services;

import java.util.List;

import models.Address;
import models.User;

public interface UserService {
	User login(String email, String password);

	boolean signUp(User u);

	boolean forgotPsw(User u);

	List<User> getUsers();

	boolean deleteUser(int id);

	boolean checkEmail(String email);

	User getUser(int id);

	boolean updateUser(User u);

	String addAllUsers(List<User> users);

	boolean updateNewAddress(List<Address> address, int id);

}
