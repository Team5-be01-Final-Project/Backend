package com.sales.BPS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class BpsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BpsApplication.class, args);
	}

}
