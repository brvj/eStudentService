package com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.user;

import java.util.List;

import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultAuthorityDTO;

import lombok.Getter;
import lombok.Setter;

@Getterpublic class UserCreate extends BaseUser
		implements com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.user.UserCreate {

	public String password;
	@Setter
	public Integer institutionId;
	@Setter
	public List<DefaultAuthorityDTO> authorities;
	
	public UserCreate(String username, String firstName, String lastName, String email, String phoneNumber,
			String password, Integer institutionId, List<DefaultAuthorityDTO> authorities) {
		super(username, firstName, lastName, email, phoneNumber);
		this.password = password;
		this.institutionId = institutionId;
		this.authorities = authorities;
	}

}
