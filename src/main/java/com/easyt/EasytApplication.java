package com.easyt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EasytApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasytApplication.class, args);
		// System.out.println(new BCryptPasswordEncoder().encode(""));
	}

}
