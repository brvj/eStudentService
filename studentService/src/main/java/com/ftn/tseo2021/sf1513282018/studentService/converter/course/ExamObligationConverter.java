package com.ftn.tseo2021.sf1513282018.studentService.converter.course;

import java.util.ArrayList;
import java.util.List;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.CourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamObligationTypeDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.CourseRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamObligationTypeRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.ExamObligationTakingRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.CourseExamObligationDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultCourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamObligationTypeDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Course;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamObligationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamObligationDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamObligationDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamObligation;

@Component
public class ExamObligationConverter implements DtoConverter<ExamObligation, ExamObligationDTO, DefaultExamObligationDTO> {

	@Autowired
	ExamObligationTypeRepository examObligationTypeRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	DtoConverter<ExamObligationType, ExamObligationTypeDTO, DefaultExamObligationTypeDTO> examObligationTypeConverter;

	@Autowired
	DtoConverter<Course, CourseDTO, DefaultCourseDTO> courseConverter;

	@Autowired
	ExamObligationTakingRepository examObligationTakingRepository;

	@Override
	public ExamObligation convertToJPA(ExamObligationDTO source) throws IllegalArgumentException {
		if(source instanceof DefaultExamObligationDTO) return convertToJPA((DefaultExamObligationDTO) source);
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", source.getClass().toString()));
	}

	@Override
	public List<ExamObligation> convertToJPA(List<? extends ExamObligationDTO> sources) throws IllegalArgumentException {
		List<ExamObligation> result = new ArrayList<ExamObligation>();

		if(sources.get(0) instanceof DefaultExamObligationDTO){
			for(ExamObligationDTO dto : sources) result.add(convertToJPA((DefaultExamObligationDTO) dto));
			return result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", sources.get(0).getClass().toString()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ExamObligationDTO> T convertToDTO(ExamObligation source,
			Class<? extends ExamObligationDTO> returnType) throws IllegalArgumentException {
		if(returnType == DefaultExamObligationDTO.class) return (T) convertToDefaultExamObligationDTO(source);
		else if (returnType == CourseExamObligationDTO.class) return (T) convertToCourseExamObligationDTO(source);
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", returnType.toString()));
	}

	@Override
	public List<? extends ExamObligationDTO> convertToDTO(List<ExamObligation> sources,
			Class<? extends ExamObligationDTO> returnType) throws IllegalArgumentException {
		if(returnType == DefaultExamObligationDTO.class){
			List<DefaultExamObligationDTO> result = new ArrayList<>();
			for(ExamObligation jpa : sources) result.add(convertToDefaultExamObligationDTO(jpa));
			return  result;
		}
		else if(returnType == CourseExamObligationDTO.class){
			List<CourseExamObligationDTO> result = new ArrayList<>();
			for(ExamObligation jpa : sources) result.add(convertToCourseExamObligationDTO(jpa));
			return  result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", returnType.toString()));
	}

	@Override
	public DefaultExamObligationDTO convertToDTO(ExamObligation source) {
		return convertToDefaultExamObligationDTO(source);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultExamObligationDTO> convertToDTO(List<ExamObligation> sources) {
		return (List<DefaultExamObligationDTO>) convertToDTO(sources, DefaultExamObligationDTO.class);
	}

	private DefaultExamObligationDTO convertToDefaultExamObligationDTO(ExamObligation source){
		if(source == null) return null;

		DefaultExamObligationDTO dto = new DefaultExamObligationDTO(source.getId(), source.getPoints(), source.getDescription(),
				examObligationTypeConverter.convertToDTO(source.getExamObligationType()),
				courseConverter.convertToDTO(source.getCourse()));

		return dto;
	}
	
	private CourseExamObligationDTO convertToCourseExamObligationDTO(ExamObligation source) {
		if (source == null) return null;
		
		CourseExamObligationDTO dto = new CourseExamObligationDTO(source.getId(), source.getPoints(), source.getDescription(),
				examObligationTypeConverter.convertToDTO(source.getExamObligationType()));
		
		return dto;
	}

	private ExamObligation convertToJPA(DefaultExamObligationDTO source) throws IllegalArgumentException {
		if (source == null) return null;
		
		if (source.getExamObligationType() == null || source.getCourse() == null || 
				!examObligationTypeRepository.existsById(source.getExamObligationType().getId()) ||
				!courseRepository.existsById(source.getCourse().getId()))
			throw new IllegalArgumentException();

		ExamObligation examObligation = new ExamObligation();
//		examObligation.setId(source.getId());
		examObligation.setPoints(source.getPoints());
		examObligation.setDescription(source.getDescription());
		examObligation.setExamObligationType(examObligationTypeRepository.getOne(source.getExamObligationType().getId()));
		examObligation.setCourse(courseRepository.getOne(source.getCourse().getId()));

		return examObligation;
	}
}
