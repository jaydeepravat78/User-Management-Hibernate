package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import models.Address;
import models.User;
import utility.KeyGeneration;
import utility.ManagementConnection;

public class UserDao implements Dao {
	private static final String GET_USER_DETAILS = "select * from users where email = ?";
	private static final String GET_ALL_USER_ADDRESS = "select * from addresses where user_id = ?";
	private static final String ADD_USER = "insert into users (name, email, password, phone, gender, lang, isAdmin, game, photo, secQuestion) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String ADD_ADDRESS = "insert into addresses (user_id, street, city, state) values (?, ?, ?, ?)";
	private static final String UPDATE_PSW = "update users set password = ? where email = ?";
	private static final String GET_ALL_USERS = "select * from users where isAdmin = 0";
	private static final String DELETE_USER = "delete from users where id = ?";
	private static final String UPDATE_USER = "update users set name = ?, password = ?, phone = ?, gender = ?, lang = ?,  photo = ?, secQuestion = ?, game = ? where id = ?";
	private static final String GET_USER = "select * from users where id = ?";

	/**
	 * 
	 * @param email, password
	 * @return User {@link User}
	 * @throws SQLException
	 * 
	 *                      The following method checks if user if present in the
	 *                      system or not If user is present in system then it
	 *                      return all the data from the database
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
					user.setPhoto(rs.getBinaryStream("photo"));
					if (user.getPhoto() != null) {
						byte[] imageBytes;
						try {
							imageBytes = user.getPhoto().readAllBytes();
							String base64Image = Base64.getEncoder().encodeToString(imageBytes);
							user.setBase64Photo(base64Image);
						} catch (IOException e) {
							e.printStackTrace();
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
			e.printStackTrace();
		} finally {
			con = ManagementConnection.getUsersConnection().destroyConnection();
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	/**
	 * @param user {@link User}
	 * @throws SQLException The following method add a user in database
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
			stmt1.setBlob(9, user.getPhoto());
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
					for (Address address : addresses) {
						stmt3.setInt(1, id);
						stmt3.setString(2, address.getStreet());
						stmt3.setString(3, address.getCity());
						stmt3.setString(4, address.getState());
						stmt3.addBatch();
					}
					stmt3.executeBatch();
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con = ManagementConnection.getUsersConnection().destroyConnection();
			if (stmt1 != null)
				try {
					stmt1.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (stmt2 != null)
				try {
					stmt2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (stmt3 != null)
				try {
					stmt3.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return false;
	}

	/**
	 * @param user {@link User}
	 * @throws SQLException The following method update the password of user if
	 *                      valid
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
			e.printStackTrace();
		} finally {
			con = ManagementConnection.getUsersConnection().destroyConnection();
			if (stmt1 != null)
				try {
					stmt1.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (stmt2 != null)
				try {
					stmt2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return false;
	}

	/**
	 * @return List of users
	 * @exception SQLException The following get all user data from database except
	 *                         Admin
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
			e.printStackTrace();
		} finally {
			con = ManagementConnection.getUsersConnection().destroyConnection();
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return users;
	}

	/**
	 * @param id
	 * @exception SQLException The following method delete a user from the database
	 * 
	 */
	@Override
	public boolean deleteUser(int id) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ManagementConnection.getUsersConnection().getConnection();
			stmt = con.prepareStatement(DELETE_USER);
			stmt.setInt(1, id);
			int i = stmt.executeUpdate();
			return i == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con = ManagementConnection.getUsersConnection().destroyConnection();
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return false;
	}

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
			e.printStackTrace();
		} finally {
			con = ManagementConnection.getUsersConnection().destroyConnection();
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return false;
	}

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
					List<Address> addresses = new ArrayList<>();
					user.setPhoto(rs.getBinaryStream("photo"));
					if (user.getPhoto() != null) {
						byte[] imageBytes;
						try {
							imageBytes = user.getPhoto().readAllBytes();
							String base64Image = Base64.getEncoder().encodeToString(imageBytes);
							user.setBase64Photo(base64Image);
						} catch (IOException e) {
							e.printStackTrace();
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
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con = ManagementConnection.getUsersConnection().destroyConnection();
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

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
			stmt.setBlob(6, user.getPhoto());
			stmt.setString(7, user.getSecQues());
			stmt.setString(8, user.getGame());
			stmt.setInt(9, user.getId());
			int i = stmt.executeUpdate();
			return i == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}