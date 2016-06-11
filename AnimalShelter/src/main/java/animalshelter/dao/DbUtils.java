package animalshelter.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import animalshelter.dao.exception.DataBaseException;

@Component
public class DbUtils {

	@Value("${db.driver}")
	private String driver;
	
	@Value("${db.url}")
	private String url;
	
	@Value("${db.user}")
	private String user;
	
	@Value("${db.password}")
	private String password;

	public DbUtils() {
		
	}

	public Connection getConnection() throws ClassNotFoundException, SQLException {
			Class.forName(driver);
			return  DriverManager.getConnection(url, user, password);
		
	}

	public void close(Connection con, Statement stmt) throws DataBaseException {
		try {
			stmt.close();
			con.close();
		} catch (SQLException e) {
			throw new DataBaseException();
		}

	}

	public void close(Connection con, Statement stmt, ResultSet rs) throws DataBaseException {
		try {
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			throw new DataBaseException();
		}
	}
}
