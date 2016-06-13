package animalshelter.security;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import animalshelter.dao.DbUtils;
import animalshelter.dao.exception.DataBaseException;
import animalshelter.dao.exception.JdbcDriverNotFound;

@Component
public class Security {

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	DbUtils dbUtils;

	public Security() {

	}

	public boolean authorization(String login, String pwd) throws JdbcDriverNotFound, DataBaseException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String hashPassword = null;

		try {
			con = dbUtils.getConnection();
			String sql = "SELECT password FROM users WHERE name = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, login);
			rs = stmt.executeQuery();

			if (rs.next()) {
				hashPassword = rs.getString("password");
			} else {
				return false;
			}
			
		} catch (ClassNotFoundException e) {
			throw new JdbcDriverNotFound();
		} catch (SQLException e) {
			throw new DataBaseException();
		}
		
		return encoder.matches(pwd, hashPassword) ? true : false;
	}

}
