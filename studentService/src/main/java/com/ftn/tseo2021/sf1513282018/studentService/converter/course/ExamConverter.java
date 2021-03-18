package com.ftn.tseo2021.sf1513282018.studentService.converter.course;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Exam;

@Component
public class ExamConverter implements DtoConverter<Exam, ExamDTO, DefaultExamDTO> {

	@Override
	public Exam convertToJPA(ExamDTO source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Exam> convertToJPA(List<? extends ExamDTO> sources) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends ExamDTO> T convertToDTO(Exam source, Class<? extends ExamDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends ExamDTO> convertToDTO(List<Exam> sources, Class<? extends ExamDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultExamDTO convertToDTO(Exam source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DefaultExamDTO> convertToDTO(List<Exam> sources) {
		// TODO Auto-generated method stub
		return null;
	}

}
