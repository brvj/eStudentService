package com.ftn.tseo2021.sf1513282018.studentService.converter.course;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamObligationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamObligationTypeDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamObligationTypeDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamObligationType;

@Component
public class ExamObligationTypeConverter implements DtoConverter<ExamObligationType, ExamObligationTypeDTO, DefaultExamObligationTypeDTO> {

	@Autowired
	ExamObligationRepository examObligationRepository;

	@Override
	public ExamObligationType convertToJPA(ExamObligationTypeDTO source) {
		if(source instanceof DefaultExamObligationTypeDTO) return convertToJPA((DefaultExamObligationTypeDTO) source);
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", source.getClass().toString()));
	}

	@Override
	public List<ExamObligationType> convertToJPA(List<? extends ExamObligationTypeDTO> sources) {
		List<ExamObligationType> result = new ArrayList<ExamObligationType>();

		if(sources.get(0) instanceof DefaultExamObligationTypeDTO){
			for(ExamObligationTypeDTO dto : sources) result.add(convertToJPA((DefaultExamObligationTypeDTO) dto));
			return result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", sources.get(0).getClass().toString()));
	}

	@Override
	public <T extends ExamObligationTypeDTO> T convertToDTO(ExamObligationType source,
			Class<? extends ExamObligationTypeDTO> returnType) {
		if(returnType == DefaultExamObligationTypeDTO.class) return (T) convertToDefaultExamObligationTypeDTO(source);
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", returnType.toString()));
	}

	@Override
	public List<? extends ExamObligationTypeDTO> convertToDTO(List<ExamObligationType> sources,
			Class<? extends ExamObligationTypeDTO> returnType) {
		if(returnType == DefaultExamObligationTypeDTO.class){
			List<DefaultExamObligationTypeDTO> result = new ArrayList<DefaultExamObligationTypeDTO>();
			for(ExamObligationType jpa : sources) result.add(convertToDefaultExamObligationTypeDTO(jpa));
			return  result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", returnType.toString()));
	}

	@Override
	public DefaultExamObligationTypeDTO convertToDTO(ExamObligationType source) {
		return convertToDefaultExamObligationTypeDTO(source);
	}

	@Override
	public List<DefaultExamObligationTypeDTO> convertToDTO(List<ExamObligationType> sources) {
		return (List<DefaultExamObligationTypeDTO>) convertToDTO(sources, DefaultExamObligationTypeDTO.class);
	}

	private DefaultExamObligationTypeDTO convertToDefaultExamObligationTypeDTO(ExamObligationType source){
		if (source == null) return null;

		DefaultExamObligationTypeDTO dto = new DefaultExamObligationTypeDTO(source.getId(), source.getName());

		return dto;
	}

	private ExamObligationType convertToJPA(DefaultExamObligationTypeDTO source){
		if(source == null) return null;

		ExamObligationType examObligationType = new ExamObligationType(source.getId(), source.getName(), new HashSet<>());

		return examObligationType;
	}
}
