package com.ftn.tseo2021.sf1513282018.studentService.model.dto.user;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.UserAuthorityDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultUserAuthorityDTO implements UserAuthorityDTO {

	private static final long serialVersionUID = -7126954312507891731L;
	
	private Integer id;
	
	private DefaultUserDTO user;
	
	private DefaultAuthorityDTO authority;

}
