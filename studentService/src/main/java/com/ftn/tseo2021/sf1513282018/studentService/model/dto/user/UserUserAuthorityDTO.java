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
public class UserUserAuthorityDTO implements UserAuthorityDTO {

	private static final long serialVersionUID = -5954351103270462690L;
	
	private Integer id;
	
	private DefaultAuthorityDTO authority;

}
