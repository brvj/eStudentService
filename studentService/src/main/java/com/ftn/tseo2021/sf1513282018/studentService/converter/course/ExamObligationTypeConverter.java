package com.ftn.tseo2021.sf1513282018.studentService.converter.course;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamObligationTypeDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamObligationTypeDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamObligationType;

@Component
public class ExamObligationTypeConverter implements DtoConverter<ExamObligationType, ExamObligationTypeDTO, DefaultExamObligationTypeDTO> {

	@Override
	public ExamObligationType convertToJPA(ExamObligationTypeDTO source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExamObligationType> convertToJPA(List<? extends ExamObligationTypeDTO> sources) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends ExamObligationTypeDTO> T convertToDTO(ExamObligationType source,
			Class<? extends ExamObligationTypeDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends ExamObligationTypeDTO> convertToDTO(List<ExamObligationType> sources,
			Class<? extends ExamObligationTypeDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultExamObligationTypeDTO convertToDTO(ExamObligationType source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DefaultExamObligationTypeDTO> convertToDTO(List<ExamObligationType> sources) {
		// TODO Auto-generated method stub
		return null;
	}

}
