package com.ftn.tseo2021.sf1513282018.studentService.converter.course;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.institution.InstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.CourseRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamObligationRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.institution.InstitutionRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.EnrollmentRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher.TeachingRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.CourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultCourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Course;

@Component
public class CourseConverter implements DtoConverter<Course, CourseDTO, DefaultCourseDTO> {

	@Autowired
	InstitutionRepository institutionRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	TeachingRepository teachingRepository;

	@Autowired
	ExamRepository examRepository;

	@Autowired
	ExamObligationRepository examObligationRepository;

	@Autowired
	EnrollmentRepository enrollmentRepository;

	@Autowired
	DtoConverter<Institution, InstitutionDTO, DefaultInstitutionDTO> institutionConverter;

	@Override
	public Course convertToJPA(CourseDTO source) {
		if(source instanceof DefaultCourseDTO) return convertToJPA((DefaultCourseDTO) source);
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", source.getClass().toString()));
	}

	@Override
	public List<Course> convertToJPA(List<? extends CourseDTO> sources) {
		List<Course> result = new ArrayList<Course>();

		if(sources.get(0) instanceof DefaultCourseDTO){
			for(CourseDTO dto : sources) result.add(convertToJPA((DefaultCourseDTO) dto));
			return result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", sources.get(0).getClass().toString()));
	}

	@Override
	public <T extends CourseDTO> T convertToDTO(Course source, Class<? extends CourseDTO> returnType) {
		if(returnType == DefaultCourseDTO.class) return (T) convertToDefaultCourseDTO(source);
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", returnType.toString()));
	}

	@Override
	public List<? extends CourseDTO> convertToDTO(List<Course> sources, Class<? extends CourseDTO> returnType) {
		if(returnType == DefaultCourseDTO.class){
			List<DefaultCourseDTO> result = new ArrayList<DefaultCourseDTO>();
			for(Course jpa : sources) result.add(convertToDefaultCourseDTO(jpa));
			return  result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", returnType.toString()));
	}

	@Override
	public DefaultCourseDTO convertToDTO(Course source) {
		return convertToDefaultCourseDTO(source);
	}

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

	private Course convertToJPA(DefaultCourseDTO source){
		if(source == null) return null;

		if(!institutionRepository.existsById(source.getInstitution().getId()))
			throw new IllegalArgumentException();

		Course course = new Course(source.getId(), source.getName(), institutionRepository.findById(source.getInstitution().getId()).get(),
				new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());

		return course;
	}
}
