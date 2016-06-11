package animalshelter.dao.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import animalshelter.constant.Const;
import animalshelter.dao.DbUtils;
import animalshelter.dao.exception.DataBaseException;
import animalshelter.dao.exception.FullShelterException;
import animalshelter.dao.exception.JdbcDriverNotFound;
import animalshelter.model.Animal;

@Repository
public class AnimalRepository implements IAnimalRepository {

	@Autowired
	DbUtils dbUtils;
	
	public AnimalRepository() {
	}

	@Override
	public int getCapacity() throws JdbcDriverNotFound, DataBaseException {
		return getAll().size();
	}

	@Override
	public Animal getAnimalById(Long id) throws JdbcDriverNotFound, DataBaseException {
		Animal animal = null;
		for (Animal a : getAll()) {
			if (a.getId() == id) {
				animal = a;
			}
		}
		return animal;
	}

	@Override
	public List<Animal> getAll() throws JdbcDriverNotFound, DataBaseException {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		List<Animal> animals = new ArrayList<>();
		
		try {
			con = dbUtils.getConnection();
			String sql = "SELECT a.animal_id, a.name, k.name as kind_name, a.age, a.weight, a.arrival_date "
					+ "FROM animal a " + "LEFT JOIN kind k " + "ON a.kind_id = k.kind_id";

			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Animal animal = new Animal();

				animal.setId(rs.getLong("animal_id"));
				animal.setName(rs.getString("name"));
				animal.setKind(rs.getString("kind_name"));
				animal.setAge(rs.getInt("age"));
				animal.setWeight(rs.getDouble("weight"));
				animal.setArrivalDate(rs.getDate("arrival_date"));

				animals.add(animal);
			}
		} catch (ClassNotFoundException e) {
			throw new JdbcDriverNotFound();
		} catch (SQLException e) {
			throw new DataBaseException();
		} finally {
			dbUtils.close(con, stmt, rs);
		}

		return animals;
	}

	@Override
	public boolean addAnimal(Animal animal) throws JdbcDriverNotFound, DataBaseException, FullShelterException {
		boolean exists = false;

		if (getCapacity() == Const.MAX_CAPACITY) {
			throw new FullShelterException();
		}

		for (Animal a : getAll()) {
			if (a.getId() == animal.getId()) {
				exists = true;
				break;
			}
		}

		if (!exists) {
			Connection con = null;
			PreparedStatement stmt = null;
			try {
				con = dbUtils.getConnection();
				String sql = "INSERT INTO animal (name, kind_id, age, weight, arrival_date)" + "VALUES (?, ?, ?, ?, ?)";
				stmt = con.prepareStatement(sql);
				stmt.setString(1, animal.getName());
				stmt.setInt(2, animal.getKind().equals("dog") ? 1 : 2);
				stmt.setInt(3, animal.getAge());
				stmt.setDouble(4, animal.getWeight());
				stmt.setDate(5, new java.sql.Date(new java.util.Date().getTime()));

				stmt.executeUpdate();
			} catch (ClassNotFoundException e) {
				throw new JdbcDriverNotFound();
			} catch (SQLException e) {
				throw new DataBaseException();
			} finally {
				dbUtils.close(con, stmt);
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean deleteAnimal(Long id) throws JdbcDriverNotFound, DataBaseException {
		Animal animal = findAnimalById(id);
		Connection con = null;
		PreparedStatement stmt = null;
		
		try {
			con = dbUtils.getConnection();
			String sql = "DELETE FROM animal WHERE animal_id = ?";
			stmt = con.prepareStatement(sql);
			stmt.setLong(1, id);
			stmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new JdbcDriverNotFound();
		} catch (SQLException e) {
			throw new DataBaseException();
		} finally {
			dbUtils.close(con, stmt);
		}

		if (animal == null) {
			return false;
		}

		return true;
	}

	@Override
	public boolean editAnimal(Animal animal) throws JdbcDriverNotFound, DataBaseException {
		Connection con = null;
		PreparedStatement stmt = null;
		
		try {
			con = dbUtils.getConnection();
			String sql = "UPDATE animal SET name = ?, kind_id = ?, age = ?, weight = ? WHERE animal_id = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, animal.getName());
			stmt.setInt(2, animal.getKind().equals("dog") ? 1 : 2);
			stmt.setInt(3, animal.getAge());
			stmt.setDouble(4, animal.getWeight());
			stmt.setLong(5, animal.getId());

			stmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new JdbcDriverNotFound();
		} catch (SQLException e) {
			throw new DataBaseException();
		} finally {
			dbUtils.close(con, stmt);
		}

		return true;
	}

	@Override
	public Animal findAnimalById(Long id) throws JdbcDriverNotFound, DataBaseException {
		Animal animal = null;

		for (Animal a : getAll()) {
			if (a.getId() == id) {
				animal = a;
				break;
			}
		}

		return animal;
	}

}
