package com.ftn.tseo2021.sf1513282018.studentService.service.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.CourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamObligationRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.EnrollmentRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher.TeachingRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamObligationDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.CourseRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.CourseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultCourseDTO;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultCourseService implements CourseService {
	
	@Autowired
	CourseRepository courseRepo;

	@Autowired
	DtoConverter<Course, CourseDTO, DefaultCourseDTO> courseConverter;

	@Autowired
	TeachingRepository teachingRepo;

	@Autowired
	EnrollmentRepository enrollmentRepo;

	@Autowired
	ExamObligationRepository examObligationRepo;

	@Autowired
	ExamRepository examRepo;

	@Override
	public DefaultCourseDTO getOne(Integer id) {
		Optional<Course> course = courseRepo.findById(id);
		return courseConverter.convertToDTO(course.orElse(null));
	}

	@Override
	public Integer create(DefaultCourseDTO t) {
		Course course = courseConverter.convertToJPA(t);

		course = courseRepo.save(course);

		return course.getId();
	}

	@Override
	public void update(Integer id, DefaultCourseDTO t) {
		if(!courseRepo.existsById(id)) throw new EntityNotFoundException();

		t.setId(id);
		Course course = courseConverter.convertToJPA(t);

		courseRepo.save(course);
	}

	@Override
	public boolean delete(Integer id) {
		if(!courseRepo.existsById(id)) return false;

		courseRepo.deleteById(id);
		return true;
	}

	@Override
	public DefaultCourseDTO getByTeaching(DefaultTeachingDTO t) {
		Optional<Course> course = courseRepo.findByTeachingsContaining(teachingRepo.getOne(t.getId()));

		return courseConverter.convertToDTO(course.get());
	}

	@Override
	public DefaultCourseDTO getByEnrollment(DefaultEnrollmentDTO t) {
		Optional<Course> course = courseRepo.findByEnrollmentsContaining(enrollmentRepo.getOne(t.getId()));

		return courseConverter.convertToDTO(course.get());
	}

	@Override
	public DefaultCourseDTO getByExamObligation(DefaultExamObligationDTO t) {
		Optional<Course> course = courseRepo.findByExamObligationsContaining(examObligationRepo.getOne(t.getId()));

		return courseConverter.convertToDTO(course.get());
	}

	@Override
	public DefaultCourseDTO getByExam(DefaultExamDTO t) {
		Optional<Course> course = courseRepo.findByExamsContaining(examRepo.getOne(t.getId()));

		return courseConverter.convertToDTO(course.get());
	}

	@Override
	public List<DefaultCourseDTO> filterCourses(DefaultCourseDTO filterOptions, Pageable pageable) {
		Page<Course> page = courseRepo.filterCourses(filterOptions.getInstitution().getId(), filterOptions.getName(),
				pageable);

		return courseConverter.convertToDTO(page.getContent());
	}
}
