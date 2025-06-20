package springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"model"})
@EnableJpaRepositories(basePackages = {"springboot.repository"})
public class ServingWebContentApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServingWebContentApplication.class, args);
    }
}