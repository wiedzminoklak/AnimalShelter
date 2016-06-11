package animalshelter.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import animalshelter.dao.exception.DataBaseException;

public class DbUtils {

	static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	static final String USER = "animal_shelter";
	static final String PASS = "dragon";

	private static Connection connection;

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if (connection == null) {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
		}
		return connection;
	}

	public static void close(Connection con, Statement stmt) throws DataBaseException {
		try {
			stmt.close();
			con.close();
		} catch (SQLException e) {
			throw new DataBaseException();
		}

	}

	public static void close(Connection con, Statement stmt, ResultSet rs) throws DataBaseException {
		try {
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			throw new DataBaseException();
		}
	}
}
