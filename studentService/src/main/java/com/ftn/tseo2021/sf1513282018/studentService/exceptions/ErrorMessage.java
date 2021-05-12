package com.ftn.tseo2021.sf1513282018.studentService.exceptions;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorMessage implements Serializable {

	private static final long serialVersionUID = -5081526033849796551L;
	
	private int statusCode;
	private Date timeStamp;
	private String message;
	private String description;
	
	public ErrorMessage(int statusCode, String message, String description) {
		this.statusCode = statusCode;
		this.message = message;
		this.description = description;
		this.timeStamp = new Date();
	}
	
}
