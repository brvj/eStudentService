package com.ftn.tseo2021.sf1513282018.studentService.converter.course;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamPeriod;

@Component
public class ExamPeriodConverter implements DtoConverter<ExamPeriod, ExamPeriodDTO, DefaultExamPeriodDTO> {

	@Override
	public ExamPeriod convertToJPA(ExamPeriodDTO source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExamPeriod> convertToJPA(List<? extends ExamPeriodDTO> sources) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends ExamPeriodDTO> T convertToDTO(ExamPeriod source, Class<? extends ExamPeriodDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends ExamPeriodDTO> convertToDTO(List<ExamPeriod> sources,
			Class<? extends ExamPeriodDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultExamPeriodDTO convertToDTO(ExamPeriod source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DefaultExamPeriodDTO> convertToDTO(List<ExamPeriod> sources) {
		// TODO Auto-generated method stub
		return null;
	}

}
