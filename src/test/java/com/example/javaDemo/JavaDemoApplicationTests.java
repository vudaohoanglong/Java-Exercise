package com.example.javaDemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class JavaDemoApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
	void test() {
		Pattern pattern = Pattern.compile("\\w");
		assertTrue(pattern.matcher("foo").matches());
	}
}
