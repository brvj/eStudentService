package com.ftn.tseo2021.sf1513282018.studentService.converter.student;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamObligationDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.EnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.ExamObligationTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamObligationRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.EnrollmentRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamObligationDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.ExamOblExamObligationTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultExamObligationTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.EnrollmentExamObligationTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamObligation;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Enrollment;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.ExamObligationTaking;

@Component
public class ExamObligationTakingConverter implements DtoConverter<ExamObligationTaking, ExamObligationTakingDTO, DefaultExamObligationTakingDTO> {
	
	@Autowired
	DtoConverter<Enrollment, EnrollmentDTO, DefaultEnrollmentDTO> enrollmentConverter;
	
	@Autowired
	DtoConverter<ExamObligation, ExamObligationDTO, DefaultExamObligationDTO> examObligationConverter;
	
	@Autowired
	EnrollmentRepository enrollmentRepo;
	
	@Autowired
	ExamObligationRepository examObligationRepo;
	
	@Override
	public ExamObligationTaking convertToJPA(ExamObligationTakingDTO source) throws IllegalArgumentException {
		if (source instanceof DefaultExamObligationTakingDTO) return convertToJPA((DefaultExamObligationTakingDTO) source);
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", source.getClass().toString()));
	}

	@Override
	public List<ExamObligationTaking> convertToJPA(List<? extends ExamObligationTakingDTO> sources) throws IllegalArgumentException {
		List<ExamObligationTaking> result = new ArrayList<ExamObligationTaking>();
		
		if (sources.get(0) instanceof DefaultExamObligationTakingDTO) {
			for (ExamObligationTakingDTO dto : sources) result.add(convertToJPA((DefaultExamObligationTakingDTO) dto));
			return result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", sources.get(0).getClass().toString()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ExamObligationTakingDTO> T convertToDTO(ExamObligationTaking source,
			Class<? extends ExamObligationTakingDTO> returnType) {
		if (returnType == DefaultExamObligationTakingDTO.class) return (T) convertToDefaultDTO(source);
		else if (returnType == EnrollmentExamObligationTakingDTO.class) return (T) convertToEnrollmentExamObligationTakingDTO(source);
		else if (returnType == ExamOblExamObligationTakingDTO.class) return (T) convertToExamOblExamObligationTakingDTO(source);
		else throw new IllegalArgumentException(String.format(
				"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public List<? extends ExamObligationTakingDTO> convertToDTO(List<ExamObligationTaking> sources,
			Class<? extends ExamObligationTakingDTO> returnType) {
		if (returnType == DefaultExamObligationTakingDTO.class) {
			List<DefaultExamObligationTakingDTO> result = new ArrayList<>();
			for (ExamObligationTaking jpa : sources) result.add(convertToDefaultDTO(jpa));
			return result;
		}
		else if (returnType == EnrollmentExamObligationTakingDTO.class) {
			List<EnrollmentExamObligationTakingDTO> result = new ArrayList<>();
			for (ExamObligationTaking jpa : sources) result.add(convertToEnrollmentExamObligationTakingDTO(jpa));
			return result;
		}
		else if (returnType == ExamOblExamObligationTakingDTO.class) {
			List<ExamOblExamObligationTakingDTO> result = new ArrayList<>();
			for (ExamObligationTaking jpa : sources) result.add(convertToExamOblExamObligationTakingDTO(jpa));
			return result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public DefaultExamObligationTakingDTO convertToDTO(ExamObligationTaking source) {
		return convertToDefaultDTO(source);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultExamObligationTakingDTO> convertToDTO(List<ExamObligationTaking> sources) {
		return (List<DefaultExamObligationTakingDTO>) convertToDTO(sources, DefaultExamObligationTakingDTO.class);
	}
	
	private DefaultExamObligationTakingDTO convertToDefaultDTO(ExamObligationTaking source) {
		if (source == null) return null;
		
		DefaultExamObligationTakingDTO dto = new DefaultExamObligationTakingDTO(
				source.getId(), source.getScore(), 
				enrollmentConverter.convertToDTO(source.getEnrollment()), 
				examObligationConverter.convertToDTO(source.getExamObligation()));
		
		return dto;
	}
	
	private EnrollmentExamObligationTakingDTO convertToEnrollmentExamObligationTakingDTO(ExamObligationTaking source) {
		if (source == null) return null;
		
		EnrollmentExamObligationTakingDTO dto = new EnrollmentExamObligationTakingDTO(source.getId(), source.getScore(), 
				examObligationConverter.convertToDTO(source.getExamObligation()));
		
		return dto;
	}
	
	private ExamOblExamObligationTakingDTO convertToExamOblExamObligationTakingDTO(ExamObligationTaking source) {
		if (source == null) return null;
		
		ExamOblExamObligationTakingDTO dto = new ExamOblExamObligationTakingDTO(source.getId(), source.getScore(), 
				enrollmentConverter.convertToDTO(source.getEnrollment()));
		
		return dto;
	}
	
	private ExamObligationTaking convertToJPA(DefaultExamObligationTakingDTO source) throws IllegalArgumentException {
		if (source == null) return null;
		
		if (source.getEnrollment() == null || source.getExamObligation() == null || 
				!enrollmentRepo.existsById(source.getEnrollment().getId()) || 
				!examObligationRepo.existsById(source.getExamObligation().getId()))
			throw new IllegalArgumentException();
		
		ExamObligationTaking taking = new ExamObligationTaking(source.getId(), source.getScore(), 
				enrollmentRepo.findById(source.getEnrollment().getId()).get(), 
				examObligationRepo.findById(source.getExamObligation().getId()).get());
		
		return taking;
	}

}
