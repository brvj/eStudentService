package com.ftn.tseo2021.sf1513282018.studentService.converter.student;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.ExamObligationTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultExamObligationTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.ExamObligationTaking;

@Component
public class ExamObligationTakingConverter implements DtoConverter<ExamObligationTaking, ExamObligationTakingDTO, DefaultExamObligationTakingDTO> {

	@Override
	public ExamObligationTaking convertToJPA(ExamObligationTakingDTO source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExamObligationTaking> convertToJPA(List<? extends ExamObligationTakingDTO> sources) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends ExamObligationTakingDTO> T convertToDTO(ExamObligationTaking source,
			Class<? extends ExamObligationTakingDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends ExamObligationTakingDTO> convertToDTO(List<ExamObligationTaking> sources,
			Class<? extends ExamObligationTakingDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultExamObligationTakingDTO convertToDTO(ExamObligationTaking source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DefaultExamObligationTakingDTO> convertToDTO(List<ExamObligationTaking> sources) {
		// TODO Auto-generated method stub
		return null;
	}

}
