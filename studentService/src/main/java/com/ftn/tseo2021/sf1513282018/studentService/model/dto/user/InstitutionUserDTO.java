package com.ftn.tseo2021.sf1513282018.studentService.model.dto.user;

import java.util.List;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionUserDTO implements UserDTO {

	private static final long serialVersionUID = 1848266090123385427L;
	
	private Integer id;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private List<DefaultAuthorityDTO> authorities;

}
