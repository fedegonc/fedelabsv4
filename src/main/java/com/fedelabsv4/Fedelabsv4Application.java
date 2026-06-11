package com.fedelabsv4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class Fedelabsv4Application {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();

        System.setProperty(
            "spring.data.mongodb.uri",
            dotenv.get("SPRING_DATA_MONGODB_URI")
        );

        SpringApplication.run(Fedelabsv4Application.class, args);
    }
}