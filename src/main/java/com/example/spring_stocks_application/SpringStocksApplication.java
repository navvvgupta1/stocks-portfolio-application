package com.example.spring_stocks_application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringStocksApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringStocksApplication.class, args);
	}

}
