package com.ftn.tseo2021.sf1513282018.studentService.converter.course;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamObligationDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamObligationDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamObligation;

@Component
public class ExamObligationConverter implements DtoConverter<ExamObligation, ExamObligationDTO, DefaultExamObligationDTO> {

	@Override
	public ExamObligation convertToJPA(ExamObligationDTO source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExamObligation> convertToJPA(List<? extends ExamObligationDTO> sources) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends ExamObligationDTO> T convertToDTO(ExamObligation source,
			Class<? extends ExamObligationDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends ExamObligationDTO> convertToDTO(List<ExamObligation> sources,
			Class<? extends ExamObligationDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultExamObligationDTO convertToDTO(ExamObligation source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DefaultExamObligationDTO> convertToDTO(List<ExamObligation> sources) {
		// TODO Auto-generated method stub
		return null;
	}

}
