package com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class BaseUser {
	
	public String username;
	public String firstName;
	public String lastName;
	public String email;
	public String phoneNumber;

}
