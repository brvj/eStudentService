package com.ftn.tseo2021.sf1513282018.studentService.converter.teacher;

import java.util.List;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teaching;

public class TeachingConverter implements DtoConverter<Teaching, TeachingDTO, DefaultTeachingDTO> {

	@Override
	public Teaching convertToJPA(TeachingDTO source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Teaching> convertToJPA(List<? extends TeachingDTO> sources) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends TeachingDTO> T convertToDTO(Teaching source, Class<? extends TeachingDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends TeachingDTO> convertToDTO(List<Teaching> sources, Class<? extends TeachingDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultTeachingDTO convertToDTO(Teaching source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DefaultTeachingDTO> convertToDTO(List<Teaching> sources) {
		// TODO Auto-generated method stub
		return null;
	}

}
