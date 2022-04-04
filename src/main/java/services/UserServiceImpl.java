package services;

import java.util.List;

import dao.UserDao;
import dao.UserDaoImpl;
import models.User;
import utility.KeyGeneration;

public class UserServiceImpl implements UserService{
	public static final UserDao dao = new UserDaoImpl();

	@Override
	public User login(String email, String password) {
		User u = dao.getUserData(email, password);
		return u;
	}

	@Override
	public boolean signUp(User u) {
		u.setPassword(KeyGeneration.encrypt(u.getPassword()));
		return dao.addUser(u);
	}

	@Override
	public boolean forgotPsw(User u) {
		u.setPassword(KeyGeneration.encrypt(u.getPassword()));
		return dao.updatePassword(u);
	}

	@Override
	public List<User> getUsers() {
		return dao.getAllUser();
	}

	@Override
	public boolean deleteUser(int id) {
		return dao.deleteUser(id);
	}

	@Override
	public boolean checkEmail(String email) {
		return dao.emailCheck(email);
	}

	@Override
	public User getUser(int id) {
		return dao.getUserData(id);
	}
	
	@Override
	public boolean updateUser(User u) {
		u.setPassword(KeyGeneration.encrypt(u.getPassword()));
		return dao.updateUserData(u);
	}
	@Override
	public boolean addAllUsers(List<User> users) {
		// TODO Auto-generated method stub
		return dao.addMultipleUsers(users);
	}
}
