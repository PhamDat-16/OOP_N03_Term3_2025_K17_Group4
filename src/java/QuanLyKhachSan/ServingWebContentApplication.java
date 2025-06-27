package QuanLyKhachSan;

import QuanLyKhachSan.service.HotelManagement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServingWebContentApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServingWebContentApplication.class, args);
    }

    @Bean
    public HotelManagement hotelManagement() {
        return new HotelManagement();
    }
}