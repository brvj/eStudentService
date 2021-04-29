package com.ftn.tseo2021.sf1513282018.studentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentServiceApplication {
	
	@Autowired
	StartData sData;

	public static void main(String[] args) {
		SpringApplication.run(StudentServiceApplication.class, args);
	}

}
