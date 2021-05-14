package com.ftn.tseo2021.sf1513282018.studentService.exceptions;

public class PersonalizedAccessDeniedException extends RuntimeException {

	private static final long serialVersionUID = -897304866989838639L;
	
	public PersonalizedAccessDeniedException(String message) {
		super( message );
	}

	public PersonalizedAccessDeniedException() {
		super();
	}

	public PersonalizedAccessDeniedException(String message, Throwable cause) {
		super( message, cause );
	}

	public PersonalizedAccessDeniedException(Throwable cause) {
		super( cause );
	}

}
