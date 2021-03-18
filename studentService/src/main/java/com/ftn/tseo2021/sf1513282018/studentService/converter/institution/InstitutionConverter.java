package com.ftn.tseo2021.sf1513282018.studentService.converter.institution;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.institution.InstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;

@Component
public class InstitutionConverter implements DtoConverter<Institution, InstitutionDTO, DefaultInstitutionDTO> {

	@Override
	public Institution convertToJPA(InstitutionDTO source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Institution> convertToJPA(List<? extends InstitutionDTO> sources) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends InstitutionDTO> T convertToDTO(Institution source, Class<? extends InstitutionDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends InstitutionDTO> convertToDTO(List<Institution> sources,
			Class<? extends InstitutionDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultInstitutionDTO convertToDTO(Institution source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DefaultInstitutionDTO> convertToDTO(List<Institution> sources) {
		// TODO Auto-generated method stub
		return null;
	}

}
