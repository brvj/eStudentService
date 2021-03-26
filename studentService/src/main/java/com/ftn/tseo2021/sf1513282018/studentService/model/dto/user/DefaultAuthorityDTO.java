package com.ftn.tseo2021.sf1513282018.studentService.model.dto.user;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.AuthorityDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultAuthorityDTO implements AuthorityDTO {

	private static final long serialVersionUID = -5059737201034862323L;
	
	private Integer id;
	
	private String name;

}
