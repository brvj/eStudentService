package com.ftn.tseo2021.sf1513282018.studentService.security;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher.TeachingRepository;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.EntityValidationException;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;

@Component
public class PersonalizedAuthorizator {
	
	@Autowired
	private PrincipalHolder principalHolder;
	
	public CustomPrincipal getPrincipal() {
		return principalHolder.getCurrentPrincipal();
	}
	
	@Autowired
	private TeachingRepository teachingRepo;

	@Autowired
	private EnrollmentRepository enrollmentRepo;
	
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
	
	public void assertPrincipalUsernameIs(String username, Class<? extends RuntimeException> exceptionType, String errorMessage) {
		if (getPrincipal().isSuperadmin()) return;
		else if (getPrincipal().getUsername() == username) return;
		
		throwException(exceptionType, errorMessage);
	}
	
	public void assertPrincipalUsernameIs(String username, Class<? extends RuntimeException> exceptionType) {
		assertPrincipalUsernameIs(username, exceptionType, null);
	}
	
	public void assertTeacherIsTeachingCourse(int courseId, Class<? extends RuntimeException> exceptionType, String errorMessage) {
		if (teachingRepo.existsByTeacher_IdAndCourse_Id(getPrincipal().getTeacherId(), courseId)) return;
		
		throwException(exceptionType, errorMessage);
	}
	
	public void assertTeacherIsTeachingCourse(int courseId, Class<? extends RuntimeException> exceptionType) {
		assertTeacherIsTeachingCourse(courseId, exceptionType, null);
	}

	private void assertStudentIsEnrollingCourse(int courseId, Class<? extends RuntimeException> exceptionType, String errorMessage){
		if (enrollmentRepo.existsByStudent_IdAndCourse_Id(getPrincipal().getStudentId(), courseId)) return;

		throwException(exceptionType, errorMessage);
	}

	public void assertStudentIsEnrollingCourse(int courseId, Class<? extends RuntimeException> exceptionType){
		assertStudentIsEnrollingCourse(courseId, exceptionType, null);
	}
	
	public void assertStudentIdIs(int studentId, Class<? extends RuntimeException> exceptionType, String errorMessage) {
		if (getPrincipal().getStudentId() == studentId) return;
		
		throwException(exceptionType, errorMessage);
	}
	
	public void assertStudentIdIs(int studentId, Class<? extends RuntimeException> exceptionType) {
		assertStudentIdIs(studentId, exceptionType, null);
	}

	private void assertPrincipalTeacherIdIs(int id, Class<? extends RuntimeException> exceptionType, String errorMessage) {
		if (getPrincipal().isSuperadmin()) return;
		else if (getPrincipal().getTeacherId() == id) return;

		throwException(exceptionType, errorMessage);
	}

	public void assertPrincipalTeacherIdIs(int id, Class<? extends RuntimeException> exceptionType) {
		assertPrincipalTeacherIdIs(id, exceptionType, null);
	}
}
