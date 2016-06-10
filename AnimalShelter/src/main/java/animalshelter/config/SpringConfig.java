package animalshelter.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import animalshelter.dao.repository.AnimalRepository;

@Configuration
@ComponentScan("animalshelter")
public class SpringConfig {

	public AnimalRepository animalRepository() {
		return new AnimalRepository();
	}
	
}
