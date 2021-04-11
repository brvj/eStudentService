package com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.institution.InstitutionDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DefaultInstitutionDTO implements InstitutionDTO {

	private static final long serialVersionUID = 5985801823507871857L;
	
	private Integer id;
    private String name;
    private String address;
    private String phoneNumber;
}
