package com.ftn.tseo2021.sf1513282018.studentService.converter.student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.CourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.EnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.StudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.CourseRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.StudentRepository;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.EntityValidationException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.CourseEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultCourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.StudentEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Course;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Enrollment;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Student;

@Component
public class EnrollmentConverter implements DtoConverter<Enrollment, EnrollmentDTO, DefaultEnrollmentDTO> {
	
	@Autowired
	DtoConverter<Student, StudentDTO, DefaultStudentDTO> studentConverter;
	
	@Autowired
	DtoConverter<Course, CourseDTO, DefaultCourseDTO> courseConverter;
	
	@Autowired
	StudentRepository studentRepo;
	
	@Autowired
	CourseRepository courseRepo;
	
	@Override
	public Enrollment convertToJPA(EnrollmentDTO source) {
		if (source instanceof DefaultEnrollmentDTO) return convertToJPA((DefaultEnrollmentDTO) source);
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", source.getClass().toString()));
	}

	@Override
	public List<Enrollment> convertToJPA(List<? extends EnrollmentDTO> sources) {
		List<Enrollment> result = new ArrayList<Enrollment>();
		
		if (sources.get(0) instanceof DefaultEnrollmentDTO) {
			for (EnrollmentDTO dto : sources) result.add(convertToJPA((DefaultEnrollmentDTO) dto));
			return result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", sources.get(0).getClass().toString()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends EnrollmentDTO> T convertToDTO(Enrollment source, Class<? extends EnrollmentDTO> returnType) {
		if (returnType == DefaultEnrollmentDTO.class) return (T) convertToDefaultEnrollmentDTO(source);
		else if (returnType == StudentEnrollmentDTO.class) return (T) convertToStudentEnrollmentDTO(source);
		else if (returnType == CourseEnrollmentDTO.class) return (T) convertToCourseEnrollmentDTO(source);
		else throw new IllegalArgumentException(String.format(
				"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public List<? extends EnrollmentDTO> convertToDTO(List<Enrollment> sources,
			Class<? extends EnrollmentDTO> returnType) {
		if (returnType == DefaultEnrollmentDTO.class) {
			List<DefaultEnrollmentDTO> result = new ArrayList<>();
			for (Enrollment jpa : sources) result.add(convertToDefaultEnrollmentDTO(jpa));
			return result;
		}
		else if (returnType == StudentEnrollmentDTO.class) {
			List<StudentEnrollmentDTO> result = new ArrayList<>();
			for (Enrollment jpa : sources) result.add(convertToStudentEnrollmentDTO(jpa));
			return result;
		}
		else if (returnType == CourseEnrollmentDTO.class) {
			List<CourseEnrollmentDTO> result = new ArrayList<>();
			for (Enrollment jpa : sources) result.add(convertToCourseEnrollmentDTO(jpa));
			return result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public DefaultEnrollmentDTO convertToDTO(Enrollment source) {
		return convertToDefaultEnrollmentDTO(source);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultEnrollmentDTO> convertToDTO(List<Enrollment> sources) {
		return (List<DefaultEnrollmentDTO>) convertToDTO(sources, DefaultEnrollmentDTO.class);
	}
	
	private DefaultEnrollmentDTO convertToDefaultEnrollmentDTO(Enrollment source) {
		if (source == null) return null;
		
		DefaultEnrollmentDTO dto = new DefaultEnrollmentDTO(source.getId(), source.getStartDate(), 
				source.isPassed(), source.getScore(), source.getGrade(), 
				studentConverter.convertToDTO(source.getStudent()), 
				courseConverter.convertToDTO(source.getCourse()));
		
		return dto;
	}
	
	private StudentEnrollmentDTO convertToStudentEnrollmentDTO(Enrollment source) {
		if (source == null) return null;
		
		StudentEnrollmentDTO dto = new StudentEnrollmentDTO(source.getId(), source.getStartDate(), source.isPassed(), 
				source.getScore(), source.getGrade(), courseConverter.convertToDTO(source.getCourse()));
		
		return dto;
	}
	
	private CourseEnrollmentDTO convertToCourseEnrollmentDTO(Enrollment source) {
		if (source == null) return null;
		
		CourseEnrollmentDTO dto = new CourseEnrollmentDTO(source.getId(), source.getStartDate(), source.isPassed(), 
				source.getScore(), source.getGrade(), studentConverter.convertToDTO(source.getStudent()));
		
		return dto;
	}
	
	private Enrollment convertToJPA(DefaultEnrollmentDTO source) {
		if (source == null || source.getStudent() == null || source.getCourse() == null || 
				!studentRepo.existsById(source.getStudent().getId()) || 
				!courseRepo.existsById(source.getCourse().getId()))
			throw new EntityValidationException();
		
		Enrollment enrollment = new Enrollment();
//		enrollment.setId(source.getId());
		enrollment.setStartDate(LocalDate.now());
//		enrollment.setPassed(source.isPassed());
//		enrollment.setScore(source.getScore());
//		enrollment.setGrade(source.getGrade());
		enrollment.setStudent(studentRepo.getOne(source.getStudent().getId()));
		enrollment.setCourse(courseRepo.getOne(source.getCourse().getId()));
		
		return enrollment;
	}

}
