package com.ftn.tseo2021.sf1513282018.studentService.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.exceptions.EntityValidationException;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;

@Component
public class PersonalizedAuthorizator {
	
	@Autowired
	private PrincipalHolder principalHolder;
	
	public CustomPrincipal getPrincipal() {
		return principalHolder.getCurrentPrincipal();
	}
	
	private void throwException(Class<? extends RuntimeException> exceptionType, String errorMessage) {
		if (exceptionType == PersonalizedAccessDeniedException.class)
			throw new PersonalizedAccessDeniedException(errorMessage);
		if (exceptionType == EntityValidationException.class)
			throw new EntityValidationException(errorMessage);
		
		throw new RuntimeException(errorMessage);
	}
	
	public void assertPrincipalIsFromInstitution(int institutionId, Class<? extends RuntimeException> exceptionType, String errorMessage) {
		if (getPrincipal().isSuperadmin()) return;
		else if (getPrincipal().getInstitutionId() == institutionId) return;
		
		throwException(exceptionType, errorMessage);
	}
	
	public void assertPrincipalIsFromInstitution(int institutionId, Class<? extends RuntimeException> exceptionType) {
		assertPrincipalIsFromInstitution(institutionId, exceptionType, null);
	}
	
	public void assertPrincipalIdIs(int id, Class<? extends RuntimeException> exceptionType, String errorMessage) {
		if (getPrincipal().isSuperadmin()) return;
		else if (getPrincipal().getId() == id) return;
		
		throwException(exceptionType, errorMessage);
	}
	
	public void assertPrincipalIdIs(int id, Class<? extends RuntimeException> exceptionType) {
		assertPrincipalIdIs(id, exceptionType, null);
	}
	
}
