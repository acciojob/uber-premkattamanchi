package com.driver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class Uber {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Uber.class, args);
	}

}
