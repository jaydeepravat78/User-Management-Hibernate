package services;

import java.util.List;

import models.User;

public interface UserService {
	public User login(String email, String password);

	public boolean signUp(User u);

	public boolean forgotPsw(User u);

	public List<User> getUsers();

	public boolean deleteUser(int id);

	public boolean checkEmail(String email);

	public User getUser(int id);
}
