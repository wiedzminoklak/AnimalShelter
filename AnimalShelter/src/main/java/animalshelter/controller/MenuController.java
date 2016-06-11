package animalshelter.controller;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import animalshelter.constant.Const;
import animalshelter.dao.exception.DataBaseException;
import animalshelter.dao.exception.FullShelterException;
import animalshelter.dao.exception.JdbcDriverNotFound;
import animalshelter.dao.repository.AnimalRepository;
import animalshelter.dao.repository.IAnimalRepository;
import animalshelter.model.Animal;
import animalshelter.validator.Validator;
import animalshelter.validator.exception.BadDoubleValidationException;
import animalshelter.validator.exception.BadIntegerValidationException;
import animalshelter.validator.exception.BadKindValidationException;
import animalshelter.validator.exception.BadLongValidationException;
import animalshelter.validator.exception.BadNameValidationException;

@Component
public class MenuController {

	private boolean isEnd;
	@Autowired
	private IAnimalRepository animalRepository;

	public MenuController() {
		isEnd = false;
		animalRepository = new AnimalRepository();
	}

	public void start() {
		try (Scanner scanner = new Scanner(System.in)) {
			while (!isEnd) {
				System.out.println("1) Show all animal");
				System.out.println("2) Add animal");
				System.out.println("3) Edit animal");
				System.out.println("4) Delete animal");
				System.out.println("5) Status");
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
					case "3":
						editAnimal();
						break;
					case "4":
						deleteAnimal();
						break;
					case "5":
						status();
						break;
					case "0":
						exit();
						break;
					default:
						unknownAction();
					}
				} catch (BadLongValidationException e) {
					System.out.println("Id must be integer!");
				} catch (BadKindValidationException e) {
					System.out.println("Kind can contains only letters and spaces!");
				} catch (BadNameValidationException e) {
					System.out.println("Name can contains only letters!;");
				} catch (BadIntegerValidationException e) {
					System.out.println("Age must be integer!");
				} catch (BadDoubleValidationException e) {
					System.out.println("Double must be integer!");
				} catch (JdbcDriverNotFound e) {
					System.out.println("Application cannot find JDBC driver!");
				} catch (DataBaseException e) {
					System.out.println("Database error please try later!");
				} catch (FullShelterException e) {
					System.out.println("Cannot add animal. Shelter is full!");
				}
			}
		}
	}

	private void showAllAnimals() throws JdbcDriverNotFound, DataBaseException {
		for (Animal a : animalRepository.getAll()) {
			System.out.println("Id: " + a.getId());
			System.out.println("Kind: " + a.getKind());
			System.out.println("Name: " + a.getName());
			System.out.println("Age: " + a.getAge());
			System.out.println("Weight: " + a.getWeight());
			System.out.println("Arrival date: " + a.getArrivalDate());
			System.out.println("-----------------------------------");
		}
	}

	private void addAnimal() throws BadLongValidationException, BadKindValidationException, BadNameValidationException, BadIntegerValidationException, BadDoubleValidationException, JdbcDriverNotFound, DataBaseException, FullShelterException {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Kind: ");
		String kind = Validator.validateKind(scanner.nextLine());

		System.out.print("Name: ");
		String name = Validator.validateName(scanner.nextLine());
		
		System.out.print("Age: ");
		Integer age = Validator.validateInteger(scanner.nextLine());

		System.out.print("Weight: ");
		Double weight = Validator.validateDouble(scanner.nextLine());

		Animal animal = new Animal();
		animal.setKind(kind);
		animal.setName(name);
		animal.setAge(age);
		animal.setWeight(weight);

		if (animalRepository.addAnimal(animal)) {
			System.out.println("Animal added");
		} else {
			System.out.println("Animal with this id already exists");
		}

	}

	private void editAnimal() throws BadLongValidationException, BadKindValidationException, BadNameValidationException, BadIntegerValidationException, BadDoubleValidationException, JdbcDriverNotFound, DataBaseException {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Id of animal to eidt: ");
		Long id = Validator.validateLong(scanner.nextLine());

		System.out.print("Kind: ");
		String kind = Validator.validateKind(scanner.nextLine());

		System.out.print("Name: ");
		String name = Validator.validateName(scanner.nextLine());
		
		System.out.print("Age: ");
		Integer age = Validator.validateInteger(scanner.nextLine());

		System.out.print("Weight: ");
		Double weight = Validator.validateDouble(scanner.nextLine());

		Animal animal = animalRepository.findAnimalById(id);

		if (animal != null) {
			animal.setKind(kind);
			animal.setName(name);
			animal.setAge(age);
			animal.setWeight(weight);
			animalRepository.editAnimal(animal);
			System.out.println("Animal edited");
		} else {
			System.out.println("This id does't exist");
		}
	}

	private void deleteAnimal() throws BadLongValidationException, JdbcDriverNotFound, DataBaseException {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Id of animal to delete: ");
		Long id = Validator.validateLong(scanner.nextLine());

		if (animalRepository.deleteAnimal(id)) {
			System.out.println("Animal deleted");
		} else {
			System.out.println("This id does't exist");
		}
	}

	private void status() throws JdbcDriverNotFound, DataBaseException {
		System.out.println("Animals in shelter: " + animalRepository.getCapacity() + "/" + Const.MAX_CAPACITY);
	}
	
	private void exit() {
		isEnd = true;
	}

	private void unknownAction() {
		System.out.println("You choosed unknwon action");
	}

}
