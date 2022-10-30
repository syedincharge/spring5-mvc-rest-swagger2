package guru.springframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("guru.springframework.config.swagger")
@ComponentScan(basePackages = {"guru.*"})
@EntityScan(basePackages = {"guru.*"})
@EnableJpaRepositories(basePackages = {"guru.*"})
public class Spring5MvcRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring5MvcRestApplication.class, args);
	}

}
