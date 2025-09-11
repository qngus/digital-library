package com.example.book_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class BookServiceApplicationTests {

	@Test
	void contextLoads() {
		// Validates the application's context loads properly
	}

}
