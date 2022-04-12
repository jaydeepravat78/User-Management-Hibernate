package utility;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class ManagementConnection {

	private static final String jdbcDriver = "Driver";
	private static final String jdbcURL = "url";
	private static final String jdbcUserName = "user";
	private static final String jdbcPassword = "password";
	private static final String PATH = "dbconfig.properties";
	private static final Logger log = Logger.getLogger(ManagementConnection.class.getClass());
	private static ManagementConnection connection = null;

	private ManagementConnection() {
		BasicConfigurator.configure();
	}

	public static ManagementConnection getUsersConnection() {
		if (connection == null)
			return connection = new ManagementConnection();
		return connection;
	}

	public Connection getConnection() {
		Connection con = null;
		InputStream input = null;
		try {
			input = ManagementConnection.class.getResourceAsStream(PATH);
			Properties properties = new Properties();
			properties.load(input);
			Class.forName(properties.getProperty(jdbcDriver));
			con = DriverManager.getConnection(properties.getProperty(jdbcURL), properties.getProperty(jdbcUserName),
					properties.getProperty(jdbcPassword));
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (input != null)
				try {
					input.close();
				} catch (IOException e) {
					log.error(e);
				}
		}
		return con;
	}

}
