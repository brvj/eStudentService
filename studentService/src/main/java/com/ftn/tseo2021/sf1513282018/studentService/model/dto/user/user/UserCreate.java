package com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.user;

import java.util.List;

import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultAuthorityDTO;

import lombok.Getter;

@Getter
public class UserCreate extends BaseUser
		implements com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.user.UserCreate {

	public String password;
	public DefaultInstitutionDTO institution;
	public List<DefaultAuthorityDTO> authorities;
	
	public UserCreate(String username, String firstName, String lastName, String email, String phoneNumber,
			String password, DefaultInstitutionDTO institution, List<DefaultAuthorityDTO> authorities) {
		super(username, firstName, lastName, email, phoneNumber);
		this.password = password;
		this.institution = institution;
		this.authorities = authorities;
	}

}
