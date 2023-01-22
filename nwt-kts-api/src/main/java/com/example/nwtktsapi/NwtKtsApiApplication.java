package com.example.nwtktsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NwtKtsApiApplication {

	public static void main(String[] args) {
		org.h2.engine.Mode mode = org.h2.engine.Mode.getInstance("ORACLE");
		mode.limit = true;
		SpringApplication.run(NwtKtsApiApplication.class, args);
	}

}
