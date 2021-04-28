package com.ftn.tseo2021.sf1513282018.studentService.model.dto.user;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO implements Serializable {

	private static final long serialVersionUID = -8820224951269883294L;
	
	private String username;
	private String password;

}
