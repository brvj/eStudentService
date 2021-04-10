package com.ftn.tseo2021.sf1513282018.studentService.service.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.CourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamObligationDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.EnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamObligationService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.EnrollmentService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeachingService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamObligationDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.InstitutionCourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Course;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Exam;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamObligation;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Enrollment;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teaching;
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
	private CourseRepository courseRepo;

	@Autowired
	private DtoConverter<Course, CourseDTO, DefaultCourseDTO> courseConverter;

	@Autowired
	private TeachingService teachingService;

	@Autowired
	private DtoConverter<Teaching, TeachingDTO, DefaultTeachingDTO> teachingConverter;

	@Autowired
	private EnrollmentService enrollmentService;

	@Autowired
	private DtoConverter<Enrollment, EnrollmentDTO, DefaultEnrollmentDTO> enrollmentConverter;

	@Autowired
	private ExamObligationService examObligationService;

	@Autowired
	private DtoConverter<ExamObligation, ExamObligationDTO, DefaultExamObligationDTO> examObligationConverter;

	@Autowired
	private ExamService examService;

	@Autowired
	private DtoConverter<Exam, ExamDTO, DefaultExamDTO> examConverter;
	
	@Override
	public boolean existsById(Integer id) {
		return courseRepo.existsById(id);
	}

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
		Optional<Course> course = courseRepo.findByTeachingsContaining(teachingConverter.convertToJPA
				(teachingService.getOne(t.getId())));

		return courseConverter.convertToDTO(course.get());
	}

	@Override
	public DefaultCourseDTO getByEnrollment(DefaultEnrollmentDTO t) {
		Optional<Course> course = courseRepo.findByEnrollmentsContaining(enrollmentConverter.convertToJPA
				(enrollmentService.getOne(t.getId())));

		return courseConverter.convertToDTO(course.get());
	}

	@Override
	public DefaultCourseDTO getByExamObligation(DefaultExamObligationDTO t) {
		Optional<Course> course = courseRepo.findByExamObligationsContaining(examObligationConverter.convertToJPA
				(examObligationService.getOne(t.getId())));

		return courseConverter.convertToDTO(course.get());
	}

	@Override
	public DefaultCourseDTO getByExam(DefaultExamDTO t) {
		Optional<Course> course = courseRepo.findByExamsContaining(examConverter.convertToJPA
				(examService.getOne(t.getId())));

		return courseConverter.convertToDTO(course.get());
	}

	@Override
	public List<DefaultCourseDTO> filterCourses(DefaultCourseDTO filterOptions, Pageable pageable) {
		Page<Course> page = courseRepo.filterCourses(filterOptions.getInstitution().getId(), filterOptions.getName(),
				pageable);

		return courseConverter.convertToDTO(page.getContent());
	}

	@Override
	public List<InstitutionCourseDTO> getByInstitutionId(int institutionId, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DefaultTeachingDTO> getCourseTeachings(int courseId, Pageable pageable) throws EntityNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DefaultEnrollmentDTO> getCourseEnrollments(int courseId, Pageable pageable)
			throws EntityNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DefaultExamObligationDTO> getCourseExamObligations(int courseId, Pageable pageable)
			throws EntityNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DefaultExamDTO> getCourseExams(int courseId, Pageable pageable) throws EntityNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
}
