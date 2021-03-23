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
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultCourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherRoleDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Course;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teacher;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.TeacherRole;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teaching;
import org.springframework.beans.factory.annotation.Autowired;

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
	public Teaching convertToJPA(TeachingDTO source) {
		if(source instanceof DefaultTeachingDTO) return convertToJPA((DefaultTeachingDTO) source);
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", source.getClass().toString()));
	}

	@Override
	public List<Teaching> convertToJPA(List<? extends TeachingDTO> sources) {
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

	@Override
	public <T extends TeachingDTO> T convertToDTO(Teaching source, Class<? extends TeachingDTO> returnType) {
		if(returnType == DefaultTeachingDTO.class) return (T) convertToDefaultTeachingDTO(source);
		else throw new IllegalArgumentException(String.format(
					"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public List<? extends TeachingDTO> convertToDTO(List<Teaching> sources, Class<? extends TeachingDTO> returnType) {
		if(returnType == DefaultTeachingDTO.class){
			List<DefaultTeachingDTO> result = new ArrayList<>();
			sources.stream().forEach(teaching -> {
				result.add(convertToDefaultTeachingDTO(teaching));
			});
			return result;

		}else throw new IllegalArgumentException(String.format(
				"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public DefaultTeachingDTO convertToDTO(Teaching source) {
		return convertToDefaultTeachingDTO(source);
	}

	@Override
	public List<DefaultTeachingDTO> convertToDTO(List<Teaching> sources) {
		return (List<DefaultTeachingDTO>) convertToDTO(sources, DefaultTeachingDTO.class);
	}

	private Teaching convertToJPA(DefaultTeachingDTO source){
		if(source == null) throw new NullPointerException();

		int teacherId = source.getTeacher().getId();
		int teacherRoleId = source.getTeacherRole().getId();
		int courseId = source.getCourse().getId();

		if(!teacherRepo.existsById(teacherId) || !teacherRoleRepo.existsById(teacherRoleId) || !courseRepo.existsById(courseId)){
			throw new IllegalArgumentException();
		}

		Teacher teacher = teacherRepo.findById(teacherId).get();
		TeacherRole teacherRole = teacherRoleRepo.findById(teacherRoleId).get();
		Course course = courseRepo.findById(courseId).get();

		Teaching teaching = new Teaching(source.getId(), source.getStartDate(), teacher, teacherRole, course);

		return teaching;
	}

	private DefaultTeachingDTO convertToDefaultTeachingDTO(Teaching source){
		if(source == null) throw new NullPointerException();

		DefaultTeacherDTO teacherDTO = (source.getTeacher() != null)? teacherConverter.convertToDTO(source.getTeacher()) : null;
		DefaultTeacherRoleDTO teacherRoleDTO = (source.getTeacherRole() != null)? teacherRoleConverter.convertToDTO(source.getTeacherRole()) : null;
		DefaultCourseDTO courseDTO = (source.getCourse() != null)? courseConverter.convertToDTO(source.getCourse()) : null;

		DefaultTeachingDTO dto = new DefaultTeachingDTO(source.getId(), source.getStartDate(), teacherDTO, teacherRoleDTO, courseDTO);

		return dto;
	}

}
