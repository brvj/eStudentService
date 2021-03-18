package com.ftn.tseo2021.sf1513282018.studentService.converter.teacher;

import java.util.List;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherTitleDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherTitleDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.TeacherTitle;

public class TeacherTitleConverter implements DtoConverter<TeacherTitle, TeacherTitleDTO, DefaultTeacherTitleDTO> {

	@Override
	public TeacherTitle convertToJPA(TeacherTitleDTO source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TeacherTitle> convertToJPA(List<? extends TeacherTitleDTO> sources) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends TeacherTitleDTO> T convertToDTO(TeacherTitle source,
			Class<? extends TeacherTitleDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends TeacherTitleDTO> convertToDTO(List<TeacherTitle> sources,
			Class<? extends TeacherTitleDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultTeacherTitleDTO convertToDTO(TeacherTitle source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DefaultTeacherTitleDTO> convertToDTO(List<TeacherTitle> sources) {
		// TODO Auto-generated method stub
		return null;
	}

}
