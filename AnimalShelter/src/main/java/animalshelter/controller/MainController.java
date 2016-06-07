package animalshelter.controller;

import java.util.List;

import animalshelter.dao.Animal;
import animalshelter.repository.AnimalRepository;
import animalshelter.repository.IAnimalRepository;

public class MainController {

	public static void main(String[] args) {
		IAnimalRepository animalRepository = new AnimalRepository();
		List<Animal> animals = animalRepository.getAll();
		for(Animal a : animals) {
			System.out.println("Id: " + a.getId());
			System.out.println("Kind: " + a.getKind());
			System.out.println("Name: " + a.getName());
			System.out.println("-----------------------------------");
		}
	}

}
