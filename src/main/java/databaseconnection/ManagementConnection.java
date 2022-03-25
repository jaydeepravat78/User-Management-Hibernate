package databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class ManagementConnection {
	private final String jdbcDriver = "com.mysql.cj.jdbc.Driver";
	private final String jdbcURL = "jdbc:mysql://localhost:3306/user_management";
	private final String jdbcUserName = "root";
	private final String jdbcPassword = "Jay@2000";
	private static ManagementConnection connection = null;
	private static final Logger log = Logger.getLogger(ManagementConnection.class.getClass());
	private static Connection con = null;

	private ManagementConnection() {
		BasicConfigurator.configure();
	}

	public static ManagementConnection getUsersConnection() {
		if (connection == null)
			return connection = new ManagementConnection();
		return connection;
	}

	public Connection getConnection() {
		try {
			Class.forName(jdbcDriver);
			con = DriverManager.getConnection(jdbcURL, jdbcUserName, jdbcPassword);
		} catch (Exception e) {
			log.error(e);
		}
		return con;
	}

	public Connection destroyConnection() {
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
				log.error(e);
			}
		return con;
	}
}
