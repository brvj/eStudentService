package com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.user.views;

import java.util.List;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.UserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultAuthorityDTO;

public interface UserView extends UserDTO {
	
	public Integer getId();
	public String getUsername();
	public String getFirstName();
	public String getLastName();
	public String getEmail();
	public String getPhoneNumber();
	public List<DefaultAuthorityDTO> getAuthorities();
	public DefaultInstitutionDTO getInstitution();

}
