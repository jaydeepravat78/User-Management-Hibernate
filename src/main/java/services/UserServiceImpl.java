package services;

import java.util.List;

import dao.Dao;
import dao.UserDao;
import models.User;
import utility.KeyGeneration;

public class UserServiceImpl implements UserService{
	public static final Dao dao = new UserDao();

	public User login(String email, String password) {
		User u = dao.getUserData(email, password);
		return u;
	}

	public boolean signUp(User u) {
		u.setPassword(KeyGeneration.encrypt(u.getPassword()));
		return dao.addUser(u);
	}

	public boolean forgotPsw(User u) {
		u.setPassword(KeyGeneration.encrypt(u.getPassword()));
		return dao.updatePassword(u);
	}

	public List<User> getUsers() {
		return dao.getAllUser();
	}

	public boolean deleteUser(int id) {
		return dao.deleteUser(id);
	}

	public boolean checkEmail(String email) {
		return dao.emailCheck(email);
	}
	
	public User getUser(int id) {
		return dao.getUserData(id);
	}
}
