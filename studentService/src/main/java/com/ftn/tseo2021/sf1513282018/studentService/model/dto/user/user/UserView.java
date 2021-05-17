package com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.user;

import java.util.List;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.user.views.InstitutionUser;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultAuthorityDTO;

import lombok.Getter;

@Getter
public class UserView extends BaseUser implements com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.user.views.UserView, InstitutionUser {

	public Integer id;
	public DefaultInstitutionDTO institution;
	public List<DefaultAuthorityDTO> authorities;
	
	public UserView(Integer id, String username, String firstName, String lastName, String email, String phoneNumber,
			DefaultInstitutionDTO institution, List<DefaultAuthorityDTO> authorities) {
		super(username, firstName, lastName, email, phoneNumber);
		this.id = id;
		this.institution = institution;
		this.authorities = authorities;
	}
}
