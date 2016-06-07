package animalshelter.repository;

import java.util.List;

import animalshelter.dao.Animal;

public interface IAnimalRepository {
	
	Animal getAnimalById(Long id);

	List<Animal> getAll();
	
	boolean addAnimal(Animal animal);
	
	boolean deleteAnimal(Long id);
	
	Animal findAnimalById(Long id);
}
