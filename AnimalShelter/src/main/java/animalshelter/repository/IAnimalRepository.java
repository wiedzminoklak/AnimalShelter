package animalshelter.repository;

import java.util.List;

import animalshelter.dao.Animal;

public interface IAnimalRepository {
	
	Animal getAnimalById(Long id);

	List<Animal> getAll();
}
