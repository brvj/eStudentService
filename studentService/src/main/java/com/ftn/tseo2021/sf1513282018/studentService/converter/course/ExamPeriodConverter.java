package com.ftn.tseo2021.sf1513282018.studentService.converter.course;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.institution.InstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.institution.InstitutionRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.InstitutionExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamPeriod;

@Component
public class ExamPeriodConverter implements DtoConverter<ExamPeriod, ExamPeriodDTO, DefaultExamPeriodDTO> {

	@Autowired
	InstitutionRepository institutionRepository;

	@Autowired
	DtoConverter<Institution, InstitutionDTO, DefaultInstitutionDTO> institutionConverter;

	@Override
	public ExamPeriod convertToJPA(ExamPeriodDTO source) {
		if(source instanceof DefaultExamPeriodDTO) return convertToJPA((DefaultExamPeriodDTO) source);
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", source.getClass().toString()));
	}

	@Override
	public List<ExamPeriod> convertToJPA(List<? extends ExamPeriodDTO> sources) {
		List<ExamPeriod> result = new ArrayList<ExamPeriod>();

		if(sources.get(0) instanceof DefaultExamPeriodDTO){
			for(ExamPeriodDTO dto : sources) result.add(convertToJPA((DefaultExamPeriodDTO) dto));
			return result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", sources.get(0).getClass().toString()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ExamPeriodDTO> T convertToDTO(ExamPeriod source, Class<? extends ExamPeriodDTO> returnType) {
		if(returnType == DefaultExamPeriodDTO.class) return (T) convertToDefaultExamPeriodDTO(source);
		else if (returnType == InstitutionExamPeriodDTO.class) return (T) convertToInstitutionExamPeriodDTO(source);
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", returnType.toString()));
	}

	@Override
	public List<? extends ExamPeriodDTO> convertToDTO(List<ExamPeriod> sources,
			Class<? extends ExamPeriodDTO> returnType) {
		if(returnType == DefaultExamPeriodDTO.class){
			List<DefaultExamPeriodDTO> result = new ArrayList<>();
			for(ExamPeriod jpa : sources) result.add(convertToDefaultExamPeriodDTO(jpa));
			return  result;
		}
		else if(returnType == InstitutionExamPeriodDTO.class){
			List<InstitutionExamPeriodDTO> result = new ArrayList<>();
			for(ExamPeriod jpa : sources) result.add(convertToInstitutionExamPeriodDTO(jpa));
			return  result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", returnType.toString()));
	}

	@Override
	public DefaultExamPeriodDTO convertToDTO(ExamPeriod source) {
		return convertToDefaultExamPeriodDTO(source);
	}

	@Override
	public List<DefaultExamPeriodDTO> convertToDTO(List<ExamPeriod> sources) {
		return (List<DefaultExamPeriodDTO>) convertToDTO(sources, DefaultExamPeriodDTO.class);
	}

	private DefaultExamPeriodDTO convertToDefaultExamPeriodDTO(ExamPeriod source){
		if(source == null) return null;

		DefaultExamPeriodDTO dto = new DefaultExamPeriodDTO(source.getId(), source.getName(), source.getStartDate(),
				source.getEndDate(), institutionConverter.convertToDTO(source.getInstitution()));

		return dto;
	}
	
	private InstitutionExamPeriodDTO convertToInstitutionExamPeriodDTO(ExamPeriod source) {
		if (source == null) return null;
		
		InstitutionExamPeriodDTO dto = new InstitutionExamPeriodDTO(source.getId(), source.getName(), source.getStartDate(), source.getEndDate());
		
		return dto;
	}

	private ExamPeriod convertToJPA(DefaultExamPeriodDTO source){
		if(source == null) return null;

		if(!institutionRepository.existsById(source.getInstitution().getId()))
			throw new IllegalArgumentException();

		ExamPeriod examPeriod = new ExamPeriod(source.getId(), source.getName(), source.getStartDate(), source.getEndDate(),
				institutionRepository.findById(source.getInstitution().getId()).get(), new HashSet<>());

		return examPeriod;
	}
}
