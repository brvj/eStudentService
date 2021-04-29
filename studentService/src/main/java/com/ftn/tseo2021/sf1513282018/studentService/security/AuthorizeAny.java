package com.ftn.tseo2021.sf1513282018.studentService.security;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.access.prepost.PreAuthorize;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyAuthority('SUPERADMIN', 'ADMIN', 'TEACHER', 'STUDENT')")
public @interface AuthorizeAny {

}
