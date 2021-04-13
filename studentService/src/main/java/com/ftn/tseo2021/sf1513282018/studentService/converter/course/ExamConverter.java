package com.ftn.tseo2021.sf1513282018.studentService.converter.course;

import java.util.ArrayList;
import java.util.List;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.CourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.CourseRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamPeriodRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.CourseExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultCourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.ExamPeriodExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Course;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamPeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Exam;

@Component
public class ExamConverter implements DtoConverter<Exam, ExamDTO, DefaultExamDTO> {

	@Autowired
	DtoConverter<Course, CourseDTO, DefaultCourseDTO> courseConverter;
	
	@Autowired
	CourseRepository courseRepo;

	@Autowired
	DtoConverter<ExamPeriod, ExamPeriodDTO, DefaultExamPeriodDTO> examPeriodConverter;
	
	@Autowired
	ExamPeriodRepository examPeriodRepo;

	@Override
	public Exam convertToJPA(ExamDTO source) throws IllegalArgumentException {
		if(source instanceof DefaultExamDTO) return convertToJPA((DefaultExamDTO) source);
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", source.getClass().toString()));
	}

	@Override
	public List<Exam> convertToJPA(List<? extends ExamDTO> sources) throws IllegalArgumentException {
		List<Exam> result = new ArrayList<Exam>();

		if(sources.get(0) instanceof DefaultExamDTO){
			for(ExamDTO dto : sources) result.add(convertToJPA((DefaultExamDTO) dto));
			return result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", sources.get(0).getClass().toString()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ExamDTO> T convertToDTO(Exam source, Class<? extends ExamDTO> returnType) throws IllegalArgumentException {
		if(returnType == DefaultExamDTO.class) return (T) convertToDefaultExamDTO(source);
		else if (returnType == CourseExamDTO.class) return (T) convertToCourseExamDTO(source);
		else if (returnType == ExamPeriodExamDTO.class) return (T) convertToExamPeriodExamDTO(source);
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", returnType.toString()));
	}

	@Override
	public List<? extends ExamDTO> convertToDTO(List<Exam> sources, Class<? extends ExamDTO> returnType) throws IllegalArgumentException {
		if(returnType == DefaultExamDTO.class){
			List<DefaultExamDTO> result = new ArrayList<>();
			for(Exam jpa : sources) result.add(convertToDefaultExamDTO(jpa));
			return  result;
		}
		else if(returnType == CourseExamDTO.class){
			List<CourseExamDTO> result = new ArrayList<>();
			for(Exam jpa : sources) result.add(convertToCourseExamDTO(jpa));
			return  result;
		}
		else if(returnType == ExamPeriodExamDTO.class){
			List<ExamPeriodExamDTO> result = new ArrayList<>();
			for(Exam jpa : sources) result.add(convertToExamPeriodExamDTO(jpa));
			return  result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", returnType.toString()));
	}

	@Override
	public DefaultExamDTO convertToDTO(Exam source) {
		return convertToDefaultExamDTO(source);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultExamDTO> convertToDTO(List<Exam> sources) {
		return (List<DefaultExamDTO>) convertToDTO(sources, DefaultExamDTO.class);
	}

	private DefaultExamDTO convertToDefaultExamDTO(Exam source){
		if (source == null) return null;

		DefaultExamDTO dto = new DefaultExamDTO(source.getId(), source.getDateTime(), courseConverter.convertToDTO(source.getCourse()),
				source.getDescription(), source.getClassroom(), source.getPoints(), examPeriodConverter.convertToDTO(source.getExamPeriod()));

		return dto;
	}
	
	private CourseExamDTO convertToCourseExamDTO(Exam source) {
		if (source == null) return null;
		
		CourseExamDTO dto = new CourseExamDTO(source.getId(), source.getDateTime(),
				source.getDescription(), source.getClassroom(), source.getPoints(), 
				examPeriodConverter.convertToDTO(source.getExamPeriod()));
		
		return dto;
	}
	
	private ExamPeriodExamDTO convertToExamPeriodExamDTO(Exam source) {
		if (source == null) return null;
		
		ExamPeriodExamDTO dto = new ExamPeriodExamDTO(source.getId(), source.getDateTime(),
				courseConverter.convertToDTO(source.getCourse()), source.getDescription(), 
						source.getClassroom(), source.getPoints());
		
		return dto;
	}

	private Exam convertToJPA(DefaultExamDTO source) throws IllegalArgumentException {
		if(source == null) return null;
		
		if (source.getExamPeriod() == null || source.getCourse() == null || 
				!examPeriodRepo.existsById(source.getExamPeriod().getId()) || 
				!courseRepo.existsById(source.getCourse().getId()))
			throw new IllegalArgumentException();

		Exam exam = new Exam();
//		exam.setId(source.getId());
		exam.setDateTime(source.getDateTime());
		exam.setClassroom(source.getClassroom());
		exam.setPoints(source.getPoints());
		exam.setDescription(source.getDescription());
		exam.setExamPeriod(examPeriodRepo.getOne(source.getExamPeriod().getId()));
		exam.setCourse(courseRepo.getOne(source.getCourse().getId()));

		return exam;
	}
}
