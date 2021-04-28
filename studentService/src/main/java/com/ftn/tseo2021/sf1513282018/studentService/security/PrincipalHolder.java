package com.ftn.tseo2021.sf1513282018.studentService.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PrincipalHolder {
	
	public CustomPrincipal getCurrentPrincipal() {
		return (CustomPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
