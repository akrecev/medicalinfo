package ru.kretsev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class FhirServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FhirServiceApplication.class, args);
    }
}
