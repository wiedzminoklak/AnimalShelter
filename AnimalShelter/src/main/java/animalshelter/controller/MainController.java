package animalshelter.controller;

import java.util.Scanner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import animalshelter.config.SpringConfig;
import animalshelter.dao.exception.DataBaseException;
import animalshelter.dao.exception.JdbcDriverNotFound;
import animalshelter.security.Security;

@Component
public class MainController {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
		Security security = context.getBean("security", Security.class);
		MenuController menuController = context.getBean("menuController", MenuController.class);

		Scanner scanner = new Scanner(System.in);

		try {
			boolean isCorrect = false;
			String login;
			String password;
			
			do {
				System.out.print("Login: ");
				login = scanner.nextLine();

				System.out.print("Password: ");
				password = scanner.nextLine();
				
				if (login.equals("") && password.equals("")) {
					break;
				}

				isCorrect = security.authorization(login, password);

				if (isCorrect) {
					menuController.start();
				} else {
					System.out.println("Wrong login or password!");
				}
			} while (!isCorrect);
		} catch (JdbcDriverNotFound e) {
			System.out.println("Application cannot find JDBC driver!");
		} catch (DataBaseException e) {
			System.out.println("Database error please try later!");
		}

		scanner.close();
		context.close();
	}

}
