package com.ftn.tseo2021.sf1513282018.studentService.service.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.CourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.institution.InstitutionService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.InstitutionCourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Course;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;
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
	
	private void authorize(Integer institutionId) throws PersonalizedAccessDeniedException {
		if (principalHolder.getCurrentPrincipal().getInstitutionId() != institutionId) 
			throw new PersonalizedAccessDeniedException();
	}

	@AuthorizeAny
	@Override
	public DefaultCourseDTO getOne(Integer id) {
		Optional<Course> course = courseRepo.findById(id);
		return courseConverter.convertToDTO(course.orElse(null));
	}

	@AuthorizeAdmin
	@Override
	public Integer create(DefaultCourseDTO dto) throws IllegalArgumentException, PersonalizedAccessDeniedException {
		Course course = courseConverter.convertToJPA(dto);
		course.getInstitution().setId(principalHolder.getCurrentPrincipal().getInstitutionId());

		course = courseRepo.save(course);

		return course.getId();
	}

	@AuthorizeAdmin
	@Override
	public void update(Integer id, DefaultCourseDTO dto) throws EntityNotFoundException, IllegalArgumentException, PersonalizedAccessDeniedException {
		DefaultInstitutionDTO institution = institutionService.getOne(dto.getInstitution().getId());
		authorize(institution.getId());

		if(!courseRepo.existsById(id)) throw new EntityNotFoundException();

		Course cNew = courseConverter.convertToJPA(dto);
		
		Course c = courseRepo.findById(id).get();
		c.setName(cNew.getName());
		courseRepo.save(c);
	}

	@AuthorizeAdmin
	@Override
	public boolean delete(Integer id) throws PersonalizedAccessDeniedException {
		Course course= courseRepo.getOne(id);

		authorize(course.getInstitution().getId());

		if(!courseRepo.existsById(id)) return false;

		courseRepo.deleteById(id);
		return true;
	}

	@SuppressWarnings("unchecked")
	@AuthorizeAdmin
	@Override
	public List<InstitutionCourseDTO> filterCourses(int institutionId, Pageable pageable, InstitutionCourseDTO filterOptions) 
		throws PersonalizedAccessDeniedException {
		authorize(institutionId);
		
		if (filterOptions == null) {
			Page<Course> page = courseRepo.findByInstitution_Id(institutionId, pageable);
			return (List<InstitutionCourseDTO>) courseConverter.convertToDTO(page.getContent(), InstitutionCourseDTO.class);
		}
		else {
			Page<Course> page = courseRepo.filterCourses(institutionId, filterOptions.name, pageable);
			return (List<InstitutionCourseDTO>) courseConverter.convertToDTO(page.getContent(), InstitutionCourseDTO.class);
		}
	}

	@AuthorizeAny
	@Override
	public List<CourseTeachingDTO> getCourseTeachings(int courseId, Pageable pageable) throws EntityNotFoundException {
		if (!courseRepo.existsById(courseId)) throw new EntityNotFoundException();
		
		return teachingService.filterTeachingsByCourse(courseId, pageable, null);
	}

	@AuthorizeAny
	@Override
	public List<CourseEnrollmentDTO> getCourseEnrollments(int courseId, Pageable pageable)
			throws EntityNotFoundException {
		if (!courseRepo.existsById(courseId)) throw new EntityNotFoundException();
		
		return enrollmentService.filterEnrollmentsByCourse(courseId, pageable, null);
	}

	@AuthorizeAny
	@Override
	public List<CourseExamObligationDTO> getCourseExamObligations(int courseId, Pageable pageable)
			throws EntityNotFoundException, PersonalizedAccessDeniedException {
		if (!courseRepo.existsById(courseId)) throw new EntityNotFoundException();
		
		return examObligationService.filterExamObligations(courseId, pageable, null);
	}

	@AuthorizeAny
	@Override
	public List<CourseExamDTO> getCourseExams(int courseId, Pageable pageable) throws EntityNotFoundException {
		if (!courseRepo.existsById(courseId)) throw new EntityNotFoundException();
		
		return examService.filterExamsByCourse(courseId, pageable, null);
	}
}
