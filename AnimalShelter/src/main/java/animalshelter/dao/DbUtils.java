package animalshelter.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUtils {

	static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	static final String USER = "animal_shelter";
	static final String PASS = "dragon";

	private static Connection connection;

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if (connection == null) {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(DB_URL,USER,PASS);
		}
		return connection;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Connection connection = getConnection();
		String sql = "SELECT * FROM animal_shelter.animal";
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()) {
			System.out.println("id = " + rs.getString("kind_id") + " | name = " + rs.getString("name"));
		}
		
	}
}
