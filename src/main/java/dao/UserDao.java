package dao;

import models.User;

public interface UserDao {
	public User login(String email, String password);
}
