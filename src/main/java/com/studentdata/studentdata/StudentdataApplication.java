package com.studentdata.studentdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class StudentdataApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentdataApplication.class, args);
	}

}
