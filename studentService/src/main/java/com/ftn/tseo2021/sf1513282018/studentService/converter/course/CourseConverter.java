package com.ftn.tseo2021.sf1513282018.studentService.converter.course;

import java.util.ArrayList;
import java.util.List;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.institution.InstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.institution.InstitutionRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.CourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultCourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.InstitutionCourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Course;

@Component
public class CourseConverter implements DtoConverter<Course, CourseDTO, DefaultCourseDTO> {

	@Autowired
	InstitutionRepository institutionRepository;

	@Autowired
	DtoConverter<Institution, InstitutionDTO, DefaultInstitutionDTO> institutionConverter;

	@Override
	public Course convertToJPA(CourseDTO source) throws IllegalArgumentException {
		if(source instanceof DefaultCourseDTO) return convertToJPA((DefaultCourseDTO) source);
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", source.getClass().toString()));
	}

	@Override
	public List<Course> convertToJPA(List<? extends CourseDTO> sources) throws IllegalArgumentException {
		List<Course> result = new ArrayList<Course>();

		if(sources.get(0) instanceof DefaultCourseDTO){
			for(CourseDTO dto : sources) result.add(convertToJPA((DefaultCourseDTO) dto));
			return result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", sources.get(0).getClass().toString()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends CourseDTO> T convertToDTO(Course source, Class<? extends CourseDTO> returnType) throws IllegalArgumentException {
		if(returnType == DefaultCourseDTO.class) return (T) convertToDefaultCourseDTO(source);
		else if (returnType == InstitutionCourseDTO.class) return (T) convertToInstitutionCourseDTO(source);
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", returnType.toString()));
	}

	@Override
	public List<? extends CourseDTO> convertToDTO(List<Course> sources, Class<? extends CourseDTO> returnType) throws IllegalArgumentException {
		if(returnType == DefaultCourseDTO.class){
			List<DefaultCourseDTO> result = new ArrayList<>();
			for(Course jpa : sources) result.add(convertToDefaultCourseDTO(jpa));
			return  result;
		}
		else if(returnType == InstitutionCourseDTO.class){
			List<InstitutionCourseDTO> result = new ArrayList<>();
			for(Course jpa : sources) result.add(convertToInstitutionCourseDTO(jpa));
			return  result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", returnType.toString()));
	}

	@Override
	public DefaultCourseDTO convertToDTO(Course source) {
		return convertToDefaultCourseDTO(source);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultCourseDTO> convertToDTO(List<Course> sources) {
		return (List<DefaultCourseDTO>) convertToDTO(sources, DefaultCourseDTO.class);
	}

	private DefaultCourseDTO convertToDefaultCourseDTO(Course source) {
		if(source == null) return null;

		DefaultCourseDTO dto = new DefaultCourseDTO(source.getId(), source.getName(), institutionConverter.convertToDTO(
				source.getInstitution()));

		return dto;
	}
	
	private InstitutionCourseDTO convertToInstitutionCourseDTO(Course source) {
		if (source == null) return null;
		
		InstitutionCourseDTO dto = new InstitutionCourseDTO(source.getId(), source.getName());
		
		return dto;
	}

	private Course convertToJPA(DefaultCourseDTO source) throws IllegalArgumentException {
		if(source == null) return null;

		if(source.getInstitution() == null || 
				!institutionRepository.existsById(source.getInstitution().getId()))
			throw new IllegalArgumentException();

		Course course = new Course();
//		course.setId(source.getId());
		course.setName(source.getName());
		course.setInstitution(institutionRepository.getOne(source.getInstitution().getId()));

		return course;
	}
}
