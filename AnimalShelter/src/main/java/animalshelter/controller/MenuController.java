package animalshelter.controller;

import java.util.Scanner;

import animalshelter.dao.Animal;
import animalshelter.dao.exception.BadIdValidtionException;
import animalshelter.dao.exception.BadKindValidationException;
import animalshelter.dao.exception.BadNameValidationException;
import animalshelter.repository.AnimalRepository;
import animalshelter.repository.IAnimalRepository;
import validator.Validator;

public class MenuController {

	private boolean isEnd;
	private IAnimalRepository animalRepository;

	public MenuController() {
		isEnd = false;
		animalRepository = new AnimalRepository();
	}

	public boolean isEnd() {
		return isEnd;
	}

	public void start() {
		try (Scanner scanner = new Scanner(System.in)) {
			while (!isEnd) {
				System.out.println("1) Show all animal");
				System.out.println("2) Add animal");
				System.out.println("0) Exit");

				String choose = scanner.next();
				scanner.nextLine();
				try {
					switch (choose) {
					case "1":
						showAllAnimals();
						break;
					case "2":
						addAnimal();
						break;
					case "0":
						exit();
						break;
					default:
						unknownAction();
					}
				} catch (BadIdValidtionException e) {
					System.out.println("Id must be integer!");
				} catch (BadKindValidationException e) {
					System.out.println("Kind can contains only letters and spaces");
				} catch (BadNameValidationException e) {
					System.out.println("Name can contains only letters;");
				}
			}
		}
	}

	private void showAllAnimals() {
		for (Animal a : animalRepository.getAll()) {
			System.out.println("Id: " + a.getId());
			System.out.println("Kind: " + a.getKind());
			System.out.println("Name: " + a.getName());
			System.out.println("-----------------------------------");
		}
	}

	private void addAnimal() throws BadIdValidtionException, BadKindValidationException, BadNameValidationException {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Id: ");
		Long id = Validator.validateId(scanner.nextLine());

		System.out.print("Kind: ");
		String kind = Validator.validateKind(scanner.nextLine());

		System.out.print("Name: ");
		String name = Validator.validateName(scanner.nextLine());

		Animal animal = new Animal(id, kind, name);

		if (animalRepository.addAnimal(animal)) {
			System.out.println("Animal added");
		} else {
			System.out.println("Animal with this id already exists");
		}

	}

	private void exit() {
		isEnd = true;
	}

	private void unknownAction() {
		System.out.println("You choosed unknwon action");
	}

}
