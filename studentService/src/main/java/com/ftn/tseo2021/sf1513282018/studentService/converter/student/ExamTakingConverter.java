package com.ftn.tseo2021.sf1513282018.studentService.converter.student;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.EnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.ExamTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.EnrollmentRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultExamTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.EnrollmentExamTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Exam;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Enrollment;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.ExamTaking;

@Component
public class ExamTakingConverter implements DtoConverter<ExamTaking, ExamTakingDTO, DefaultExamTakingDTO> {

	@Autowired
	DtoConverter<Enrollment, EnrollmentDTO, DefaultEnrollmentDTO> enrollmentConverter;
	
	@Autowired
	DtoConverter<Exam, ExamDTO, DefaultExamDTO> examConverter;
	
	@Autowired
	EnrollmentRepository enrollmentRepo;
	
	@Autowired
	ExamRepository examRepo;
	
	@Override
	public ExamTaking convertToJPA(ExamTakingDTO source) throws IllegalArgumentException {
		if (source instanceof DefaultExamTakingDTO) return convertToJPA((DefaultExamTakingDTO) source);
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", source.getClass().toString()));
	}

	@Override
	public List<ExamTaking> convertToJPA(List<? extends ExamTakingDTO> sources) throws IllegalArgumentException {
		List<ExamTaking> result = new ArrayList<ExamTaking>();
		
		if (sources.get(0) instanceof DefaultExamTakingDTO) {
			for (ExamTakingDTO dto : sources) result.add(convertToJPA((DefaultExamTakingDTO) dto));
			return result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", sources.get(0).getClass().toString()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ExamTakingDTO> T convertToDTO(ExamTaking source, Class<? extends ExamTakingDTO> returnType) {
		if (returnType == DefaultExamTakingDTO.class) return (T) convertToDefaultDTO(source);
		else if (returnType == EnrollmentExamTakingDTO.class) return (T) convertToEnrollmentExamTakingDTO(source);
		else throw new IllegalArgumentException(String.format(
				"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public List<? extends ExamTakingDTO> convertToDTO(List<ExamTaking> sources,
			Class<? extends ExamTakingDTO> returnType) {
		if (returnType == DefaultExamTakingDTO.class) {
			List<DefaultExamTakingDTO> result = new ArrayList<>();
			for (ExamTaking jpa : sources) result.add(convertToDefaultDTO(jpa));
			return result;
		}
		else if (returnType == EnrollmentExamTakingDTO.class) {
			List<EnrollmentExamTakingDTO> result = new ArrayList<>();
			for (ExamTaking jpa : sources) result.add(convertToEnrollmentExamTakingDTO(jpa));
			return result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public DefaultExamTakingDTO convertToDTO(ExamTaking source) {
		return convertToDefaultDTO(source);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultExamTakingDTO> convertToDTO(List<ExamTaking> sources) {
		return (List<DefaultExamTakingDTO>) convertToDTO(sources, DefaultExamTakingDTO.class);
	}
	
	private DefaultExamTakingDTO convertToDefaultDTO(ExamTaking source) {
		if (source == null) return null;
		
		DefaultExamTakingDTO dto = new DefaultExamTakingDTO(source.getId(), source.getScore(), 
				enrollmentConverter.convertToDTO(source.getEnrollment()), 
				examConverter.convertToDTO(source.getExam()));
		
		return dto;
	}
	
	private EnrollmentExamTakingDTO convertToEnrollmentExamTakingDTO(ExamTaking source) {
		if (source == null) return null;
		
		EnrollmentExamTakingDTO dto = new EnrollmentExamTakingDTO(source.getId(), source.getScore(), 
				examConverter.convertToDTO(source.getExam()));
		
		return dto;
	}
	
	private ExamTaking convertToJPA(DefaultExamTakingDTO source) throws IllegalArgumentException {
		if (source == null) return null;
		
		if (source.getEnrollment() == null || source.getExam() == null || 
				!enrollmentRepo.existsById(source.getEnrollment().getId()) ||
				!examRepo.existsById(source.getExam().getId()))
			throw new IllegalArgumentException();
		
		ExamTaking taking = new ExamTaking(source.getId(), source.getScore(), 
				enrollmentRepo.findById(source.getEnrollment().getId()).get(), 
				examRepo.findById(source.getExam().getId()).get());
		
		return taking;
	}

}
