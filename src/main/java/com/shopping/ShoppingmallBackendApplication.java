package com.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShoppingmallBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingmallBackendApplication.class, args);
	}
}
