package com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.UserDTO;

@JsonDeserialize(as = com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.user.UserUpdate.class)
public interface UserUpdate extends UserDTO {
	
	@NotBlank
	public String getUsername();
	@NotBlank
	public String getFirstName();
	@NotBlank
	public String getLastName();
	@Email
	public String getEmail();
	@NotBlank
	public String getPhoneNumber();

}
