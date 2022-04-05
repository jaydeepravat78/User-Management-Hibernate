package services;

import java.util.List;

import dao.UserDao;
import dao.UserDaoImpl;
import models.Address;
import models.User;
import utility.KeyGeneration;
import utility.Validation;

public class UserServiceImpl implements UserService {
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
	public String addAllUsers(List<User> users) {
		String error = "";
		int count = 1;
		for(User user: users) {
			String inputError = "";
			inputError += Validation.checkName(user.getName());
			inputError += Validation.checkEmail(user.getEmail());
			inputError += Validation.checkPassword(user.getPassword());
			inputError += Validation.checkPhone(user.getPhone());
			inputError += Validation.checkGender(user.getGender());
			inputError += Validation.checkLang(user.getLang());
			inputError += Validation.checkGame(user.getGame());
			inputError += Validation.checkSecQues(user.getSecQues());
			if(inputError.isEmpty()) {
				user.setPassword(KeyGeneration.encrypt(user.getPassword()));
				dao.addUser(user);
			}
			else {
				error += "At row " + count + " " + inputError;
			}
			count++;
		}
		return error;
	}
	
	@Override
	public boolean updateNewAddress(List<Address> newAddresses, int userId) {
		// TODO Auto-generated method stub
		List<Address> oldAddresses = getUser(userId).getAddresses();
		for(Address oldAddress: oldAddresses) {
			Address newAddress = (Address) newAddresses.stream().filter(address -> address.getAddress_id() == oldAddress.getAddress_id()).findAny().orElse(null);
			if(newAddress != null) {
				dao.updateOldAddress(newAddress);
			}
			else {
				dao.deleteOldAddress(oldAddress.getAddress_id());
			}
		}
		for(Address newAddress: newAddresses) {
			if(newAddress.getAddress_id() == 0) {
				dao.addNewAddress(newAddress, userId);
			}
		}
		return false;
	}
}
