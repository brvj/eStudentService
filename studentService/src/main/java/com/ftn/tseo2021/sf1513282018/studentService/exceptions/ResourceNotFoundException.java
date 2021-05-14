package com.ftn.tseo2021.sf1513282018.studentService.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2897652939460991646L;
	
	public ResourceNotFoundException(String message) {
		super( message );
	}

	public ResourceNotFoundException() {
		super();
	}

	public ResourceNotFoundException(String message, Throwable cause) {
		super( message, cause );
	}

	public ResourceNotFoundException(Throwable cause) {
		super( cause );
	}

}
