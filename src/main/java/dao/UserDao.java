package dao;

import java.util.List;

import models.Address;
import models.User;

public interface UserDao {
	public User getUserData(String email, String password);

	public boolean addUser(User u);

	public boolean updatePassword(User u);

	public List<User> getAllUser();

	public boolean deleteUser(int id);

	public boolean emailCheck(String email);

	public User getUserData(int id);

	public boolean updateUserData(User u);
	
	public boolean addNewAddress(Address address, int id);
	
	public boolean updateOldAddress(Address address);
	
	public boolean deleteOldAddress(int addressId); 
}
