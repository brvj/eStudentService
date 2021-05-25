package com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.user;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.UserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultAuthorityDTO;

@JsonDeserialize(as = com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.user.UserCreate.class)
public interface UserCreate extends UserDTO {
	
	@NotBlank
	public String getUsername();
	public String getPassword();
	@NotBlank
	public String getFirstName();
	@NotBlank
	public String getLastName();
	@NotBlank
	@Email
	public String getEmail();
	@NotBlank
	public String getPhoneNumber();
//	@NotNull
	public Integer getInstitutionId();
	public void setInstitutionId(Integer id);
//	@NotEmpty
	public List<DefaultAuthorityDTO> getAuthorities();
	public void setAuthorities(List<DefaultAuthorityDTO> authorities);

}
