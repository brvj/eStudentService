package com.ftn.tseo2021.sf1513282018.studentService.exceptions;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler({ResourceNotFoundException.class, PersonalizedAccessDeniedException.class, EntityNotFoundException.class})
	public ResponseEntity<ErrorMessage> handleNotFound(Exception ex, WebRequest request) {
		ErrorMessage error = new ErrorMessage(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
		return handleException(ex, error, null, HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler(EntityValidationException.class)
	public ResponseEntity<ErrorMessage> handleEntityValidation(EntityValidationException ex, WebRequest request) {
		ErrorMessage error = new ErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage(), null);
		return handleException(ex, error, null, HttpStatus.UNPROCESSABLE_ENTITY, request);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorMessage> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
		ErrorMessage error = new ErrorMessage(HttpStatus.FORBIDDEN.value(), ex.getMessage(), null);
		return handleException(ex, error, null, HttpStatus.FORBIDDEN, request);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorMessage> handleOther(Exception ex, WebRequest request) {
		ErrorMessage error = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null);
		return handleException(ex, error, null, HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
			Exception ex, @Nullable Object body, @Nullable HttpHeaders headers, HttpStatus status, WebRequest request) {

		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
		}
		
		if (body == null || !(body instanceof ErrorMessage)) {
			body = new ErrorMessage(status.value(), ex.getMessage(), null);
		}
		
		if (headers == null) {
			headers = new HttpHeaders();
		}
		
		return new ResponseEntity<>(body, headers, status);
	}

	private ResponseEntity<ErrorMessage> transformResponseEntity(ResponseEntity<Object> responseEntity) {
		ErrorMessage body = (ErrorMessage) responseEntity.getBody();
		return new ResponseEntity<>(body, responseEntity.getHeaders(), responseEntity.getStatusCode());
	}
	
	private ResponseEntity<ErrorMessage> handleException(
			Exception ex, @Nullable Object body, @Nullable HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		return transformResponseEntity(handleExceptionInternal(ex, body, headers, status, request));
	}
	
}
