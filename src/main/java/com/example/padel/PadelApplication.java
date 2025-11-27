package com.example.padel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PadelApplication {

    public static void main(String[] args) {
        SpringApplication.run(PadelApplication.class, args);
    }

}
