package com.spring.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecurityAuthenticationAndAuthorizationWithJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityAuthenticationAndAuthorizationWithJwtApplication.class, args);

		System.out.println("Welcome to Security Authentication and Authorization...!!!");

	}

}
