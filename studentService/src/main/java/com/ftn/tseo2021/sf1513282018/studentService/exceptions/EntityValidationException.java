package com.ftn.tseo2021.sf1513282018.studentService.exceptions;

import javax.validation.ValidationException;

public class EntityValidationException extends ValidationException {

	private static final long serialVersionUID = -7200493303651364297L;
	
	public EntityValidationException(String message) {
		super( message );
	}

	public EntityValidationException() {
		super();
	}

	public EntityValidationException(String message, Throwable cause) {
		super( message, cause );
	}

	public EntityValidationException(Throwable cause) {
		super( cause );
	}

}
