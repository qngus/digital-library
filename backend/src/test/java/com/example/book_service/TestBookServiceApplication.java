package com.example.book_service;

import org.springframework.boot.SpringApplication;

public class TestBookServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(BookServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
