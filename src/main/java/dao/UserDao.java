package dao;

import java.util.List;

import models.Address;
import models.User;

public interface UserDao {
	User getUserData(String email, String password);

	boolean addUser(User u);

	boolean updatePassword(User u);

	List<User> getAllUser();

	boolean deleteUser(int id);

	boolean emailCheck(String email);

	User getUserData(int id);

	boolean updateUserData(User u);
	
	boolean addNewAddress(Address address, int id);
	
	boolean updateOldAddress(Address address);
	
	boolean deleteOldAddress(int addressId); 
}
