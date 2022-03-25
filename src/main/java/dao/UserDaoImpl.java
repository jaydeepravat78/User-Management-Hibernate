package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import databaseconnection.ManagementConnection;
import models.User;

public class UserDaoImpl implements UserDao {
	private static final Logger log = Logger.getLogger(UserDaoImpl.class.getClass());
	private static final String LOGIN_DETAILS = "select * from users where user_email = ? and user_password = ?";

	@Override
	public User login(String email, String password) {
		// TODO Auto-generated method stub
		BasicConfigurator.configure();
		User u = null;
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ManagementConnection.getUsersConnection().getConnection();
			stmt = con.prepareStatement(LOGIN_DETAILS);
			stmt.setString(1, email);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			u = new User();
			if(rs.next()) 
				u.setUser_id(rs.getInt(1));
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
		return u;
	}
}
