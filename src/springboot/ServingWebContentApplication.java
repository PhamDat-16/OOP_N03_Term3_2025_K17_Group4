package springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"model"}) // Quét gói chứa Booking, Customer, Room
@EnableJpaRepositories(basePackages = {"springboot.repository"}) // Quét gói chứa các repository
public class ServingWebContentApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServingWebContentApplication.class, args);
    }
}