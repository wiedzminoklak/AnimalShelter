package animalshelter.controller;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import animalshelter.config.SpringConfig;

@Component
public class MainController {

	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
		MenuController menuController = context.getBean("menuController", MenuController.class);
		menuController.start();
	}

}
