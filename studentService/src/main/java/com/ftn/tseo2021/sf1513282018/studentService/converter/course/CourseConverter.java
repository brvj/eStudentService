package com.ftn.tseo2021.sf1513282018.studentService.converter.course;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.CourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultCourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Course;

@Component
public class CourseConverter implements DtoConverter<Course, CourseDTO, DefaultCourseDTO> {

	@Override
	public Course convertToJPA(CourseDTO source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Course> convertToJPA(List<? extends CourseDTO> sources) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends CourseDTO> T convertToDTO(Course source, Class<? extends CourseDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends CourseDTO> convertToDTO(List<Course> sources, Class<? extends CourseDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultCourseDTO convertToDTO(Course source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DefaultCourseDTO> convertToDTO(List<Course> sources) {
		// TODO Auto-generated method stub
		return null;
	}

}
