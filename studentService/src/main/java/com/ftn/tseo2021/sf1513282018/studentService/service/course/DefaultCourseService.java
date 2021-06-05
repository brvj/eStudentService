package com.ftn.tseo2021.sf1513282018.studentService.service.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.CourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.course.CourseFilterOptions;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.institution.InstitutionService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.InstitutionCourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Course;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;
import com.ftn.tseo2021.sf1513282018.studentService.security.CustomPrincipal;
import com.ftn.tseo2021.sf1513282018.studentService.security.PersonalizedAuthorizator;
import com.ftn.tseo2021.sf1513282018.studentService.security.PrincipalHolder;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAdmin;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAny;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.CourseRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.CourseService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamObligationService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.EnrollmentService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeachingService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.CourseEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.CourseExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.CourseExamObligationDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.CourseTeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultCourseDTO;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class DefaultCourseService implements CourseService {
	
	@Autowired
	private CourseRepository courseRepo;

	@Autowired
	private DtoConverter<Course, CourseDTO, DefaultCourseDTO> courseConverter;
	
	@Autowired
	private TeachingService teachingService;
	
	@Autowired
	private EnrollmentService enrollmentService;
	
	@Autowired
	private ExamService examService;
	
	@Autowired
	private ExamObligationService examObligationService;

	@Autowired
	private InstitutionService institutionService;
	
	@Autowired
	private PrincipalHolder principalHolder;

	@Autowired
	private PersonalizedAuthorizator authorizator;

	private CustomPrincipal getPrincipal() { return authorizator.getPrincipal(); }

	@AuthorizeAny
	@Override
	public DefaultCourseDTO getOne(Integer id) {
		Optional<Course> course = courseRepo.findById(id);

		if(getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(course.orElse(null).getInstitution().getId(), PersonalizedAccessDeniedException.class);
		else if(getPrincipal().isTeacher())
			authorizator.assertTeacherIsTeachingCourse(id, PersonalizedAccessDeniedException.class);
		else if(getPrincipal().isStudent())
			authorizator.assertStudentIsEnrollingCourse(id, PersonalizedAccessDeniedException.class);

		return courseConverter.convertToDTO(course.orElse(null));
	}

	@AuthorizeAdmin
	@Override
	public Integer create(DefaultCourseDTO dto) throws IllegalArgumentException, PersonalizedAccessDeniedException {
		if(getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(dto.getInstitution().getId(), PersonalizedAccessDeniedException.class);

		Course course = courseConverter.convertToJPA(dto);
		course.getInstitution().setId(principalHolder.getCurrentPrincipal().getInstitutionId());

		course = courseRepo.save(course);

		return course.getId();
	}

	@AuthorizeAdmin
	@Override
	public void update(Integer id, DefaultCourseDTO dto) throws EntityNotFoundException, IllegalArgumentException, PersonalizedAccessDeniedException {
		if(getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(dto.getInstitution().getId(), PersonalizedAccessDeniedException.class);

		if(!courseRepo.existsById(id)) throw new EntityNotFoundException();

		Course cNew = courseConverter.convertToJPA(dto);
		
		Course c = courseRepo.findById(id).get();
		c.setName(cNew.getName());
		courseRepo.save(c);
	}

	@AuthorizeAdmin
	@Override
	public void delete(Integer id) throws PersonalizedAccessDeniedException {
		Course course= courseRepo.getOne(id);

		if(getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(course.getInstitution().getId(), PersonalizedAccessDeniedException.class);

		if(!courseRepo.existsById(id)) {}

		courseRepo.deleteById(id);
	}

	@SuppressWarnings("unchecked")
	@AuthorizeAdmin
	@Override
	public Page<InstitutionCourseDTO> filterCourses(int institutionId, Pageable pageable, CourseFilterOptions filterOptions)
		throws PersonalizedAccessDeniedException {
		if(getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(institutionId, PersonalizedAccessDeniedException.class);
		
		if (filterOptions == null) {
			Page<Course> page = courseRepo.findByInstitution_Id(institutionId, pageable);
			return page.map(new Function<Course, InstitutionCourseDTO>() {
				@Override
				public InstitutionCourseDTO apply(Course course) {
					return courseConverter.convertToDTO(course, InstitutionCourseDTO.class);
				}
			});
		}
		else {
			Page<Course> page = courseRepo.filterCourses(institutionId, filterOptions.getName(), pageable);
			return page.map(new Function<Course, InstitutionCourseDTO>() {
				@Override
				public InstitutionCourseDTO apply(Course course) {
					return courseConverter.convertToDTO(course, InstitutionCourseDTO.class);
				}
			});		}
	}

	@AuthorizeAny
	@Override
	public Page<CourseTeachingDTO> getCourseTeachings(int courseId, Pageable pageable) throws EntityNotFoundException {
		if (!courseRepo.existsById(courseId)) throw new EntityNotFoundException();
		
		return teachingService.filterTeachingsByCourse(courseId, pageable, null);
	}

	@AuthorizeAny
	@Override
	public Page<CourseEnrollmentDTO> getCourseEnrollments(int courseId, Pageable pageable)
			throws EntityNotFoundException {
		if (!courseRepo.existsById(courseId)) throw new EntityNotFoundException();
		
		return enrollmentService.filterEnrollmentsByCourse(courseId, pageable, null);
	}

	@AuthorizeAny
	@Override
	public Page<CourseExamObligationDTO> getCourseExamObligations(int courseId, Pageable pageable)
			throws EntityNotFoundException, PersonalizedAccessDeniedException {
		if (!courseRepo.existsById(courseId)) throw new EntityNotFoundException();
		
		return examObligationService.filterExamObligations(courseId, pageable, null);
	}

	@AuthorizeAny
	@Override
	public Page<CourseExamDTO> getCourseExams(int courseId, Pageable pageable) throws EntityNotFoundException {
		if (!courseRepo.existsById(courseId)) throw new EntityNotFoundException();
		
		return examService.filterExamsByCourse(courseId, pageable, null);
	}
}
