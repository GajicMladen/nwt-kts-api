package com.example.nwtktsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NwtKtsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NwtKtsApiApplication.class, args);
	}

}
