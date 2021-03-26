package com.ftn.tseo2021.sf1513282018.studentService.model.dto.user;

import java.util.List;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.UserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultUserDTO implements UserDTO {
	
	private static final long serialVersionUID = 3898756269245748525L;

	private Integer id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private DefaultInstitutionDTO institution;
	private List<DefaultAuthorityDTO> authorities;

}
