package dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysql.cj.util.Base64Decoder;

import models.Address;
import models.User;
import utility.KeyGeneration;
import utility.ManagementConnection;

public class UserDaoImpl implements UserDao {

	private static final Logger log = Logger.getLogger(UserDaoImpl.class.getClass());

	private static final String GET_USER_DETAILS = "select * from users where email = ?";
	private static final String GET_ALL_USER_ADDRESS = "select * from addresses where user_id = ?";
	private static final String ADD_USER = "insert into users (name, email, password, phone, gender, lang, isAdmin, game, photo, secQuestion) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String ADD_ADDRESS = "insert into addresses (user_id, street, city, state) values (?, ?, ?, ?)";
	private static final String UPDATE_PSW = "update users set password = ? where email = ?";
	private static final String GET_ALL_USERS = "select * from users where isAdmin = 0";
	private static final String DELETE_USER = "delete from users where id = ?";
	private static final String UPDATE_USER = "update users set name = ?, password = ?, phone = ?, gender = ?, lang = ?,  photo = ?, secQuestion = ?, game = ? where id = ?";
	private static final String GET_USER = "select * from users where id = ?";
	private static final String UPDATE_ADDRESS = "update addresses set street = ?, city = ?, state = ? where address_id = ?";
	private static final String DElETE_ADDRESS = "delete from addresses where address_id = ?";

	/**
	 * 
	 * <p>
	 * The following method check if user is authorized or not if so it return all
	 * the data from database
	 * </p>
	 * 
	 * @param email, password email and password that user enters
	 * @return User {@link User}
	 * @throws SQLException if there is error in SQL
	 * 
	 * 
	 */
	@Override
	public User getUserData(String email, String password) {
		// TODO Auto-generated method stub
		User user = null;
		Connection con = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		try {
			con = ManagementConnection.getUsersConnection().getConnection();
			stmt = con.prepareStatement(GET_USER_DETAILS);
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				if (password.equals(KeyGeneration.decrypt(rs.getString("password")))) {
					user = new User();
					user.setId(rs.getInt("id"));
					user.setName(rs.getString("name"));
					user.setEmail(rs.getString("email"));
					user.setPassword(KeyGeneration.decrypt(rs.getString("password")));
					user.setGender(rs.getString("gender"));
					user.setPhone(rs.getString("phone"));
					user.setLang(rs.getString("lang").split(" "));
					user.setGame(rs.getString("game"));
					user.setSecQues(rs.getString("secQuestion"));
					user.setAdmin(rs.getBoolean("isAdmin"));
					List<Address> addresses = new ArrayList<>();
					InputStream profilePic =  rs.getBinaryStream("photo");
					if(profilePic != null) {
						byte[] imageBytes;
						try {
							imageBytes = profilePic.readAllBytes();
							user.setProfilePic(Base64.getEncoder().encodeToString(imageBytes));
						} catch (IOException e) {
							log.error(e);
						}
					}
					stmt2 = con.prepareStatement(GET_ALL_USER_ADDRESS);
					stmt2.setInt(1, user.getId());
					ResultSet rs2 = stmt2.executeQuery();
					while (rs2.next()) {
						Address address = new Address();
						address.setStreet(rs2.getString("street"));
						address.setCity(rs2.getString("city"));
						address.setState(rs2.getString("state"));
						addresses.add(address);
					}
					user.setAddresses(addresses);
				}
			}
		} catch (SQLException e) {
			log.error(e);
		} finally {
			con = ManagementConnection.getUsersConnection().destroyConnection();
			try {
				stmt.close();
			} catch (SQLException e) {
				log.error(e);
			}
		}
		return user;
	}

	/**
	 * <p>
	 * The following method add a user details in database
	 * </p>
	 * 
	 * @param user {@link User}
	 * @throws SQLException
	 * 
	 */
	@Override
	public boolean addUser(User user) {
		Connection con = null;
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		PreparedStatement stmt3 = null;
		try {
			con = ManagementConnection.getUsersConnection().getConnection();
			stmt1 = con.prepareStatement(ADD_USER);
			stmt1.setString(1, user.getName());
			stmt1.setString(2, user.getEmail());
			stmt1.setString(3, user.getPassword());
			stmt1.setString(4, user.getPhone());
			stmt1.setString(5, user.getGender());
			String language = "";
			String[] lang = user.getLang();
			for (int i = 0; i < lang.length; i++) {
				language += lang[i] + " ";
			}
			stmt1.setString(6, language);
			stmt1.setInt(7, 0);
			stmt1.setString(8, user.getGame());
			
			InputStream profilePic = new ByteArrayInputStream(Base64.getDecoder().decode(user.getProfilePic().getBytes()));
			stmt1.setBlob(9, profilePic);
			stmt1.setString(10, user.getSecQues());
			int i = stmt1.executeUpdate();
			if (i == 1) {
				stmt2 = con.prepareStatement(GET_USER_DETAILS);
				stmt2.setString(1, user.getEmail());
				ResultSet rs = stmt2.executeQuery();
				if (rs.next()) {
					int id = rs.getInt("id");
					stmt3 = con.prepareStatement(ADD_ADDRESS);
					List<Address> addresses = user.getAddresses();
					if (addresses != null) {
						for (Address address : addresses) {
							stmt3.setInt(1, id);
							stmt3.setString(2, address.getStreet());
							stmt3.setString(3, address.getCity());
							stmt3.setString(4, address.getState());
							stmt3.addBatch();
						}
						stmt3.executeBatch();
					}
					return true;
				}
			}
		} catch (SQLException e) {
			log.error(e);
		} finally {
			con = ManagementConnection.getUsersConnection().destroyConnection();
			if (stmt1 != null)
				try {
					stmt1.close();
				} catch (SQLException e) {
					log.error(e);
				}
			if (stmt2 != null)
				try {
					stmt2.close();
				} catch (SQLException e) {
					log.error(e);
				}
			if (stmt3 != null)
				try {
					stmt3.close();
				} catch (SQLException e) {
					log.error(e);
				}
		}
		return false;
	}

	/**
	 * 
	 * <p>
	 * The following method update the password of user if user is authorized
	 * </p>
	 * 
	 * @param user {@link User}
	 * @throws SQLException
	 * 
	 */
	@Override
	public boolean updatePassword(User user) {
		Connection con = null;
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		try {
			con = ManagementConnection.getUsersConnection().getConnection();
			stmt1 = con.prepareStatement(GET_USER_DETAILS);
			stmt1.setString(1, user.getEmail());
			ResultSet rs = stmt1.executeQuery();
			if (rs.next()) {
				if (user.getGame().equals(rs.getString("game"))
						&& user.getSecQues().equals(rs.getString("secQuestion"))) {
					stmt2 = con.prepareStatement(UPDATE_PSW);
					stmt2.setString(1, user.getPassword());
					stmt2.setString(2, user.getEmail());
					int i = stmt2.executeUpdate();
					return i == 1;
				}
			}
		} catch (SQLException e) {
			log.error(e);
		} finally {
			con = ManagementConnection.getUsersConnection().destroyConnection();
			if (stmt1 != null)
				try {
					stmt1.close();
				} catch (SQLException e) {
					log.error(e);
				}
			if (stmt2 != null)
				try {
					stmt2.close();
				} catch (SQLException e) {
					log.error(e);
				}
		}
		return false;
	}

	/**
	 * 
	 * <p>
	 * The following get all user details from database except admin
	 * </p>
	 * 
	 * @return List of users
	 * @exception SQLException
	 * 
	 * 
	 */
	@Override
	public List<User> getAllUser() {
		Connection con = null;
		Statement stmt = null;
		List<User> users = null;
		try {
			con = ManagementConnection.getUsersConnection().getConnection();
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(GET_ALL_USERS);
			users = new ArrayList<>();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setGame(rs.getString("game"));
				user.setGender(rs.getString("gender"));
				users.add(user);
			}
		} catch (SQLException e) {
			log.error(e);
		} finally {
			con = ManagementConnection.getUsersConnection().destroyConnection();
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					log.error(e);
				}
		}
		return users;
	}

	/**
	 * 
	 * <p>
	 * The following method delete a user details from the database
	 * </p>
	 * 
	 * @param id
	 * @exception SQLException
	 * 
	 */
	@Override
	public boolean deleteUser(int userId) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ManagementConnection.getUsersConnection().getConnection();
			stmt = con.prepareStatement(DELETE_USER);
			stmt.setInt(1, userId);
			int i = stmt.executeUpdate();
			return i == 1;
		} catch (SQLException e) {
		} finally {
			con = ManagementConnection.getUsersConnection().destroyConnection();
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					log.error(e);
				}
		}
		return false;
	}

	/**
	 * 
	 * <p>
	 * The following method check if an email already exists or not in the database
	 * </p>
	 * 
	 * @param email
	 * @exception SQLException
	 */
	@Override
	public boolean emailCheck(String email) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ManagementConnection.getUsersConnection().getConnection();
			stmt = con.prepareStatement(GET_USER_DETAILS);
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			log.error(e);
		} finally {
			con = ManagementConnection.getUsersConnection().destroyConnection();
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					log.error(e);
				}
		}
		return false;
	}

	/**
	 * 
	 * <p>
	 * The following method return user data of a particular id of user
	 * </p>
	 * 
	 * @param id
	 * @exception SQLException
	 */
	@Override
	public User getUserData(int id) {
		User user = null;
		Connection con = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		try {
			con = ManagementConnection.getUsersConnection().getConnection();
			stmt = con.prepareStatement(GET_USER);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(KeyGeneration.decrypt(rs.getString("password")));
				user.setGender(rs.getString("gender"));
				user.setPhone(rs.getString("phone"));
				user.setLang(rs.getString("lang").split(" "));
				user.setGame(rs.getString("game"));
				user.setSecQues(rs.getString("secQuestion"));
				user.setAdmin(rs.getBoolean("isAdmin"));
				InputStream profilePic =  rs.getBinaryStream("photo");
				if(profilePic != null) {
					byte[] imageBytes;
					try {
						imageBytes = profilePic.readAllBytes();
						user.setProfilePic(Base64.getEncoder().encodeToString(imageBytes));
					} catch (IOException e) {
						log.error(e);
					}
				}
				List<Address> addresses = new ArrayList<>();
				stmt2 = con.prepareStatement(GET_ALL_USER_ADDRESS);
				stmt2.setInt(1, user.getId());
				ResultSet rs2 = stmt2.executeQuery();
				while (rs2.next()) {
					Address address = new Address();
					address.setAddress_id(rs2.getInt("address_id"));
					address.setStreet(rs2.getString("street"));
					address.setCity(rs2.getString("city"));
					address.setState(rs2.getString("state"));
					addresses.add(address);
				}
				user.setAddresses(addresses);
			}
		} catch (SQLException e) {
			log.error(e);
		} finally {
			con = ManagementConnection.getUsersConnection().destroyConnection();
			try {
				stmt.close();
			} catch (SQLException e) {
				log.error(e);
			}
		}
		return user;
	}

	/**
	 * 
	 * <p>
	 * The following method update the user data
	 * </p>
	 * 
	 * @param user {@link User}
	 * @exception SQLException
	 * 
	 * 
	 */
	public boolean updateUserData(User user) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ManagementConnection.getUsersConnection().getConnection();
			stmt = con.prepareStatement(UPDATE_USER);
			stmt.setString(1, user.getName());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getPhone());
			stmt.setString(4, user.getGender());
			String language = "";
			String[] lang = user.getLang();
			for (int i = 0; i < lang.length; i++) {
				language += lang[i] + " ";
			}
			stmt.setString(5, language);
			InputStream profilePic = new ByteArrayInputStream(Base64.getDecoder().decode(user.getProfilePic().getBytes()));
			stmt.setBlob(6, profilePic);
			stmt.setString(7, user.getSecQues());
			stmt.setString(8, user.getGame());
			stmt.setInt(9, user.getId());
			int i = stmt.executeUpdate();
			return i == 1;
		} catch (SQLException e) {
			log.error(e);
		} finally {
			con = ManagementConnection.getUsersConnection().destroyConnection();
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					log.error(e);
				}
		}
		return false;
	}

	/**
	 * 
	 * <p>
	 * The following method add new address of the user
	 * </p>
	 * 
	 * @param address id
	 * @exception SQLException
	 * 
	 */
	@Override
	public boolean addNewAddress(Address address, int id) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ManagementConnection.getUsersConnection().getConnection();
			stmt = con.prepareStatement(ADD_ADDRESS);
			stmt.setInt(1, id);
			stmt.setString(2, address.getStreet());
			stmt.setString(3, address.getCity());
			stmt.setString(4, address.getState());
			int i = stmt.executeUpdate();
			return i == 1;
		} catch (SQLException e) {
			log.error(e);
		} finally {
			con = ManagementConnection.getUsersConnection().destroyConnection();
			try {
				stmt.close();
			} catch (SQLException e) {
				log.error(e);
			}
		}
		return false;
	}

	/**
	 * 
	 * <p>
	 * The following method Updates the address of the user
	 * </p>
	 * 
	 * @param address id
	 * @exception SQLException
	 * 
	 */

	@Override
	public boolean updateOldAddress(Address address) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ManagementConnection.getUsersConnection().getConnection();
			stmt = con.prepareStatement(UPDATE_ADDRESS);
			stmt.setString(1, address.getStreet());
			stmt.setString(2, address.getCity());
			stmt.setString(3, address.getState());
			stmt.setInt(4, address.getAddress_id());
			int i = stmt.executeUpdate();
			return i == 1;
		} catch (SQLException e) {
			log.error(e);
		} finally {
			con = ManagementConnection.getUsersConnection().destroyConnection();
			try {
				stmt.close();
			} catch (SQLException e) {
				log.error(e);
			}
		}
		return false;
	}

	/**
	 * 
	 * <p>
	 * The following method deletes the address of user
	 * </p>
	 * 
	 * @param addressId
	 * @exception SQLException
	 * 
	 */

	@Override
	public boolean deleteOldAddress(int addressId) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ManagementConnection.getUsersConnection().getConnection();
			stmt = con.prepareStatement(DElETE_ADDRESS);
			stmt.setInt(1, addressId);
			int i = stmt.executeUpdate();
			return i == 1;
		} catch (SQLException e) {
			log.error(e);
		} finally {
			con = ManagementConnection.getUsersConnection().destroyConnection();
			try {
				stmt.close();
			} catch (SQLException e) {
				log.error(e);
			}
		}
		return false;
	}
}