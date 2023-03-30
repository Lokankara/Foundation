package com.validator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SolutionApplication {
    public static void main(final String[] args) {
        SpringApplication.run(SolutionApplication.class, args);
    }
}

