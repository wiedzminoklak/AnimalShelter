package animalshelter.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import animalshelter.dao.Animal;

@Repository
public class AnimalRepository implements IAnimalRepository {

	private List<Animal> animals;
	
	public AnimalRepository() {
		animals = new ArrayList<>();
		
		animals.add(new Animal(0L, "dog", "Fafik"));
		animals.add(new Animal(1L, "dog", "Azor"));
		animals.add(new Animal(2L, "cat", "Grumpy"));
		animals.add(new Animal(3L, "pig", "Dolly"));
		animals.add(new Animal(4L, "cat", "Rufus"));
		animals.add(new Animal(5L, "dog", "Brutus"));
	}
	
	@Override
	public Animal getAnimalById(Long id) {
		Animal animal = null;
		for(Animal a : animals) {
			if(a.getId() == id) {
				animal = a;
			}
		}
		return animal;
	}

	@Override
	public List<Animal> getAll() {
		return animals;
	}
	
	@Override
	public boolean addAnimal(Animal animal) {
		boolean exists = false;
		
		for(Animal a : animals) {
			if(a.getId() == animal.getId()) {
				exists = true;
				break;
			}
		}
		
		if(!exists) {
			animals.add(animal);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean deleteAnimal(Long id) {
		Animal animal = findAnimalById(id);
		
		if(animal == null) {
			return false;
		}
		
		animals.remove(animal);
		return true;
	}

	@Override
	public Animal findAnimalById(Long id) {
		Animal animal = null;
		
		for(Animal a : animals) {
			if(a.getId() == id) {
				animal = a;
				break;
			}
		}
		
		return animal;
	}

}
