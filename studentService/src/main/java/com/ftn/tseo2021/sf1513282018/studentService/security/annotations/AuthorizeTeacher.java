package com.ftn.tseo2021.sf1513282018.studentService.security.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('TEACHER')")
public @interface AuthorizeTeacher {

}
