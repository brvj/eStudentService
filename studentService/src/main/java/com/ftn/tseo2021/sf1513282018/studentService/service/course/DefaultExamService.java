package com.ftn.tseo2021.sf1513282018.studentService.service.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.CourseService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamPeriodService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.institution.InstitutionService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.ExamTakingService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.*;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Course;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Exam;
import com.ftn.tseo2021.sf1513282018.studentService.security.CustomPrincipal;
import com.ftn.tseo2021.sf1513282018.studentService.security.PersonalizedAuthorizator;
import com.ftn.tseo2021.sf1513282018.studentService.security.PrincipalHolder;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAdmin;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAny;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeTeacher;

import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeTeacherOrAdmin;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class DefaultExamService implements ExamService {
	
	@Autowired
	private ExamRepository examRepo;

	@Autowired
	private DtoConverter<Exam, ExamDTO, DefaultExamDTO> examConverter;

	@Autowired
	private ExamTakingService examTakingService;

	@Autowired
	private InstitutionService institutionService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private ExamPeriodService examPeriodService;

	@Autowired
	private PersonalizedAuthorizator authorizator;

	private CustomPrincipal getPrincipal() { return authorizator.getPrincipal(); }

	@AuthorizeAny
	@Override
	public DefaultExamDTO getOne(Integer id) {
		Optional<Exam> exam = examRepo.findById(id);

		if(getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(exam.orElse(null).getExamPeriod().getInstitution().getId(), PersonalizedAccessDeniedException.class);
		else if(getPrincipal().isTeacher())
			authorizator.assertTeacherIsTeachingCourse(exam.orElse(null).getCourse().getId(), PersonalizedAccessDeniedException.class);
		else if(getPrincipal().isStudent())
			authorizator.assertStudentIsEnrollingCourse(exam.orElse(null).getCourse().getId(), PersonalizedAccessDeniedException.class);

		return examConverter.convertToDTO(exam.orElse(null));
	}

	@AuthorizeTeacherOrAdmin
	@Override
	public Integer create(DefaultExamDTO dto) throws IllegalArgumentException, PersonalizedAccessDeniedException {
		if(getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(dto.getExamPeriod().getInstitution().getId(), PersonalizedAccessDeniedException.class);
		else if(getPrincipal().isTeacher())
			authorizator.assertTeacherIsTeachingCourse(dto.getCourse().getId(), PersonalizedAccessDeniedException.class);

		Exam exam = examConverter.convertToJPA(dto);

		exam = examRepo.save(exam);

		return exam.getId();
	}

	@AuthorizeTeacherOrAdmin
	@Override
	public void update(Integer id, DefaultExamDTO dto) throws EntityNotFoundException, IllegalArgumentException, PersonalizedAccessDeniedException {
		DefaultInstitutionDTO institution = institutionService.getOne(dto.getExamPeriod().getInstitution().getId());
		if(getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(institution.getId(), PersonalizedAccessDeniedException.class);
		else if(getPrincipal().isTeacher())
			authorizator.assertTeacherIsTeachingCourse(dto.getCourse().getId(), PersonalizedAccessDeniedException.class);

		if(!examRepo.existsById(id)) throw new EntityNotFoundException();

		Exam eNew = examConverter.convertToJPA(dto);

		Exam e = examRepo.findById(id).get();
		e.setDateTime(eNew.getDateTime());
		e.setClassroom(eNew.getClassroom());
		e.setPoints(eNew.getPoints());
		e.setDescription(eNew.getDescription());
		examRepo.save(e);
	}

	@AuthorizeTeacherOrAdmin
	@Override
	public void delete(Integer id) throws PersonalizedAccessDeniedException {
		Exam exam = examRepo.getOne(id);
		if(getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(exam.getExamPeriod().getInstitution().getId(), PersonalizedAccessDeniedException.class);
		else if(getPrincipal().isTeacher())
			authorizator.assertTeacherIsTeachingCourse(exam.getCourse().getId(), PersonalizedAccessDeniedException.class);

		if(!examRepo.existsById(id)) {}

		examRepo.deleteById(id);
	}

	@SuppressWarnings("unchecked")
	@AuthorizeAny
	@Override
	public Page<CourseExamDTO> filterExamsByCourse(int courseId, Pageable pageable, CourseExamDTO filterOptions) {
		DefaultCourseDTO course = courseService.getOne(courseId);
		if(getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(course.getInstitution().getId(), PersonalizedAccessDeniedException.class);
		else if(getPrincipal().isTeacher())
			authorizator.assertTeacherIsTeachingCourse(courseId, PersonalizedAccessDeniedException.class);
		else if(getPrincipal().isStudent())
			authorizator.assertStudentIsEnrollingCourse(courseId, PersonalizedAccessDeniedException.class);

		if (filterOptions == null) {
			Page<Exam> page = examRepo.findByCourse_Id(courseId, pageable);
			return page.map(new Function<Exam, CourseExamDTO>() {
				@Override
				public CourseExamDTO apply(Exam exam) {
					return examConverter.convertToDTO(exam, CourseExamDTO.class);
				}
			});
		}
		else {
			Page<Exam> page = examRepo.filterExamsByCourse(courseId, filterOptions.getDescription(),
					filterOptions.getClassroom(), null, null, pageable);
			return page.map(new Function<Exam, CourseExamDTO>() {
				@Override
				public CourseExamDTO apply(Exam exam) {
					return examConverter.convertToDTO(exam, CourseExamDTO.class);
				}
			});
		}
	}

	@SuppressWarnings("unchecked")
	@AuthorizeAny
	@Override
	public Page<ExamPeriodExamDTO> filterExamsByExamPeriod(int examPeriodId, Pageable pageable, ExamPeriodExamDTO filterOptions) {
		DefaultExamPeriodDTO ep = examPeriodService.getOne(examPeriodId);
		if(getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(ep.getInstitution().getId(), PersonalizedAccessDeniedException.class);
		else if(getPrincipal().isTeacher()) {
			authorizator.assertPrincipalIsFromInstitution(ep.getInstitution().getId(), PersonalizedAccessDeniedException.class);
			// TO DO - implementirati vracanje onih ispita koji pripadaju kursu koji profesor predaje
		}
		else if(getPrincipal().isStudent())
			authorizator.assertStudentIsEnrollingCourse(ep.getInstitution().getId(), PersonalizedAccessDeniedException.class);
			// TO DO - implementirati vracanje onih ispita koji pripadaju kursu u kom student ucestvuje

		if (filterOptions == null) {
			Page<Exam> page = examRepo.findByExamPeriod_Id(examPeriodId, pageable);
			return page.map(new Function<Exam, ExamPeriodExamDTO>() {
				@Override
				public ExamPeriodExamDTO apply(Exam exam) {
					return examConverter.convertToDTO(exam, ExamPeriodExamDTO.class);
				}
			});
		}
		else {
			Page<Exam> page = examRepo.filterExamsByExamPeriod(examPeriodId, filterOptions.getDescription(), 
					filterOptions.getClassroom(), null, null, pageable);
			return page.map(new Function<Exam, ExamPeriodExamDTO>() {
				@Override
				public ExamPeriodExamDTO apply(Exam exam) {
					return examConverter.convertToDTO(exam, ExamPeriodExamDTO.class);
				}
			});
		}
	}

	@AuthorizeAny
	@Override
	public List<ExamExamTakingDTO> getExamExamTakings(int examId, Pageable pageable) throws EntityNotFoundException {
		if(!examRepo.existsById(examId)) throw new EntityNotFoundException();
		
		return examTakingService.filterTakingsByExam(examId, pageable, null);
	}
}
