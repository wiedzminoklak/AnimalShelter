package animalshelter.dao.repository;

import java.util.List;

import animalshelter.dao.exception.DataBaseException;
import animalshelter.dao.exception.FullShelterException;
import animalshelter.dao.exception.JdbcDriverNotFound;
import animalshelter.model.Animal;

public interface IAnimalRepository {

	int getCapacity() throws JdbcDriverNotFound, DataBaseException;
	
	Animal getAnimalById(Long id) throws JdbcDriverNotFound, DataBaseException;

	List<Animal> getAll() throws JdbcDriverNotFound, DataBaseException;

	boolean addAnimal(Animal animal) throws JdbcDriverNotFound, DataBaseException, FullShelterException;

	boolean deleteAnimal(Long id) throws JdbcDriverNotFound, DataBaseException;
	
	boolean editAnimal(Animal animal) throws JdbcDriverNotFound, DataBaseException;

	Animal findAnimalById(Long id) throws JdbcDriverNotFound, DataBaseException;

}
