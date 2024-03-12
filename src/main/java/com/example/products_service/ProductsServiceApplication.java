package com.example.products_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.logging.Logger;

@SpringBootApplication
public class ProductsServiceApplication {

	private static final Logger log = Logger.getLogger(ProductsServiceApplication.class.toString());


	public static void main(String[] args) {
		SpringApplication.run(ProductsServiceApplication.class, args);
	}

}
