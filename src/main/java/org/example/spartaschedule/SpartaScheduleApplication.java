package org.example.spartaschedule;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpartaScheduleApplication {
    public static void main(String[] args) {
        // dotenv 설정
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

        SpringApplication.run(SpartaScheduleApplication.class, args);
    }

}
