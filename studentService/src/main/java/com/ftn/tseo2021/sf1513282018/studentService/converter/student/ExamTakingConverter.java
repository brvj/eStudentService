package com.ftn.tseo2021.sf1513282018.studentService.converter.student;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.ExamTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultExamTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.ExamTaking;

@Component
public class ExamTakingConverter implements DtoConverter<ExamTaking, ExamTakingDTO, DefaultExamTakingDTO> {

	@Override
	public ExamTaking convertToJPA(ExamTakingDTO source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExamTaking> convertToJPA(List<? extends ExamTakingDTO> sources) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends ExamTakingDTO> T convertToDTO(ExamTaking source, Class<? extends ExamTakingDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends ExamTakingDTO> convertToDTO(List<ExamTaking> sources,
			Class<? extends ExamTakingDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultExamTakingDTO convertToDTO(ExamTaking source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DefaultExamTakingDTO> convertToDTO(List<ExamTaking> sources) {
		// TODO Auto-generated method stub
		return null;
	}

}
