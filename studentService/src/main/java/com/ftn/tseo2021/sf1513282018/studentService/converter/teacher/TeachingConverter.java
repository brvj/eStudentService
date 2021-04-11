package com.ftn.tseo2021.sf1513282018.studentService.converter.teacher;

import java.util.ArrayList;
import java.util.List;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.CourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherRoleDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.CourseRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher.TeacherRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher.TeacherRoleRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.CourseTeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultCourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherRoleDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.TeacherTeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Course;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teacher;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.TeacherRole;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teaching;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeachingConverter implements DtoConverter<Teaching, TeachingDTO, DefaultTeachingDTO> {

	@Autowired
	private DtoConverter<Teacher, TeacherDTO, DefaultTeacherDTO> teacherConverter;

	@Autowired
	private DtoConverter<TeacherRole, TeacherRoleDTO, DefaultTeacherRoleDTO> teacherRoleConverter;

	@Autowired
	private DtoConverter<Course, CourseDTO, DefaultCourseDTO> courseConverter;

	@Autowired
	private TeacherRepository teacherRepo;

	@Autowired
	private TeacherRoleRepository teacherRoleRepo;

	@Autowired
	private CourseRepository courseRepo;

	@Override
	public Teaching convertToJPA(TeachingDTO source) throws IllegalArgumentException {
		if(source instanceof DefaultTeachingDTO) return convertToJPA((DefaultTeachingDTO) source);
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", source.getClass().toString()));
	}

	@Override
	public List<Teaching> convertToJPA(List<? extends TeachingDTO> sources) throws IllegalArgumentException {
		List<Teaching> result = new ArrayList<>();

		if(sources != null){
			if(sources.get(0) instanceof DefaultTeachingDTO){
				sources.stream().forEach(teachingDTO -> {
					result.add(convertToJPA((DefaultTeachingDTO) teachingDTO));
				});
			} else throw new IllegalArgumentException(String.format("Converting from %s type is not supported!", sources.get(0).getClass().toString()));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends TeachingDTO> T convertToDTO(Teaching source, Class<? extends TeachingDTO> returnType) throws IllegalArgumentException {
		if(returnType == DefaultTeachingDTO.class) return (T) convertToDefaultTeachingDTO(source);
		else if (returnType == TeacherTeachingDTO.class) return (T) convertToTeacherTeachingDTO(source);
		else if (returnType == CourseTeachingDTO.class) return (T) convertToCourseTeachingDTO(source);
		else throw new IllegalArgumentException(String.format(
					"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public List<? extends TeachingDTO> convertToDTO(List<Teaching> sources, Class<? extends TeachingDTO> returnType) throws IllegalArgumentException {
		if(returnType == DefaultTeachingDTO.class){
			List<DefaultTeachingDTO> result = new ArrayList<>();
			sources.stream().forEach(teaching -> {
				result.add(convertToDefaultTeachingDTO(teaching));
			});
			return result;

		}
		else if (returnType == TeacherTeachingDTO.class){
			List<TeacherTeachingDTO> result = new ArrayList<>();
			sources.stream().forEach(teaching -> {
				result.add(convertToTeacherTeachingDTO(teaching));
			});
			return result;

		}
		else if (returnType == CourseTeachingDTO.class){
			List<CourseTeachingDTO> result = new ArrayList<>();
			sources.stream().forEach(teaching -> {
				result.add(convertToCourseTeachingDTO(teaching));
			});
			return result;

		}
		else throw new IllegalArgumentException(String.format(
				"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public DefaultTeachingDTO convertToDTO(Teaching source) {
		return convertToDefaultTeachingDTO(source);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultTeachingDTO> convertToDTO(List<Teaching> sources) {
		return (List<DefaultTeachingDTO>) convertToDTO(sources, DefaultTeachingDTO.class);
	}

	private Teaching convertToJPA(DefaultTeachingDTO source) throws IllegalArgumentException {
		if (source == null) return null;

		if (source.getTeacher() == null || source.getCourse() == null || source.getTeacherRole() == null 
				|| !teacherRepo.existsById(source.getTeacher().getId()) 
				|| !courseRepo.existsById(source.getCourse().getId())
				|| !teacherRoleRepo.existsById(source.getTeacherRole().getId())) {
			throw new IllegalArgumentException();
		}

		Teaching teaching = new Teaching();
//		teaching.setId(source.getId());
		teaching.setStartDate(source.getStartDate());
		teaching.setTeacher(teacherRepo.getOne(source.getTeacher().getId()));
		teaching.setCourse(courseRepo.getOne(source.getCourse().getId()));
		teaching.setTeacherRole(teacherRoleRepo.getOne(source.getTeacherRole().getId()));

		return teaching;
	}

	private DefaultTeachingDTO convertToDefaultTeachingDTO(Teaching source){
		if(source == null) return null;

		DefaultTeachingDTO dto = new DefaultTeachingDTO(source.getId(), source.getStartDate(), 
				teacherConverter.convertToDTO(source.getTeacher()), 
				teacherRoleConverter.convertToDTO(source.getTeacherRole()), 
				courseConverter.convertToDTO(source.getCourse()));

		return dto;
	}
	
	private TeacherTeachingDTO convertToTeacherTeachingDTO(Teaching source) {
		if (source == null) return null;
		
		TeacherTeachingDTO dto = new TeacherTeachingDTO(source.getId(), source.getStartDate(), 
				teacherRoleConverter.convertToDTO(source.getTeacherRole()), 
				courseConverter.convertToDTO(source.getCourse()));
		
		return dto;
	}
	
	private CourseTeachingDTO convertToCourseTeachingDTO(Teaching source) {
		if (source == null) return null;
		
		CourseTeachingDTO dto = new CourseTeachingDTO(source.getId(), source.getStartDate(), 
				teacherConverter.convertToDTO(source.getTeacher()), 
				teacherRoleConverter.convertToDTO(source.getTeacherRole()));
		
		return dto;
	}

}
