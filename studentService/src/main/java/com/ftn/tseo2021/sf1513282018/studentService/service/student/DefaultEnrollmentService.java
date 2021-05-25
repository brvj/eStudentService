package com.ftn.tseo2021.sf1513282018.studentService.service.student;

import java.util.List;
import java.util.function.Function;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.EnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.EnrollmentRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.CourseService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.EnrollmentService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.ExamObligationTakingService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.ExamTakingService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.StudentService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.EntityValidationException;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.ResourceNotFoundException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.CourseEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultCourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.EnrollmentExamObligationTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.EnrollmentExamTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.StudentEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Enrollment;
import com.ftn.tseo2021.sf1513282018.studentService.security.CustomPrincipal;
import com.ftn.tseo2021.sf1513282018.studentService.security.PersonalizedAuthorizator;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAdmin;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAny;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeStudentOrAdmin;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeTeacherOrAdmin;

@Service
public class DefaultEnrollmentService implements EnrollmentService {
	
	@Autowired
	private EnrollmentRepository enrollmentRepo;
	
	@Autowired
	private DtoConverter<Enrollment, EnrollmentDTO, DefaultEnrollmentDTO> enrollmentConverter;
	
	@Autowired
	private ExamObligationTakingService examObligationTakingService;
	
	@Autowired
	private ExamTakingService examTakingService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private PersonalizedAuthorizator authorizator;
	
	private CustomPrincipal getPrincipal() { return authorizator.getPrincipal(); }

	@AuthorizeAny
	@Override
	public DefaultEnrollmentDTO getOne(Integer id) {
		Enrollment e = enrollmentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		
		if (getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(e.getCourse().getInstitution().getId(), PersonalizedAccessDeniedException.class);
		else if (getPrincipal().isStudent())
			authorizator.assertPrincipalIdIs(e.getStudent().getUser().getId(), PersonalizedAccessDeniedException.class);
		else if (getPrincipal().isTeacher())
			authorizator.assertTeacherIsTeachingCourse(e.getCourse().getId(), PersonalizedAccessDeniedException.class);
		
		return enrollmentConverter.convertToDTO(e);
	}

	@AuthorizeAdmin
	@Override
	public Integer create(DefaultEnrollmentDTO dto) {
		try {
		DefaultCourseDTO course = courseService.getOne(dto.getCourse().getId());
		DefaultStudentDTO student = studentService.getOne(dto.getStudent().getId());
		if (course.getInstitution().getId() != student.getInstitution().getId()) throw new EntityValidationException();
		
		authorizator.assertPrincipalIsFromInstitution(course.getInstitution().getId(), EntityValidationException.class);
		}
		catch (ResourceNotFoundException | NullPointerException e) {throw new EntityValidationException();}
		
		Enrollment e = enrollmentConverter.convertToJPA(dto);
		
		e = enrollmentRepo.save(e);
		
		return e.getId();
	}

	@AuthorizeAdmin
	@Override
	public void update(Integer id, DefaultEnrollmentDTO dto) {
		Enrollment e = enrollmentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		
		authorizator.assertPrincipalIsFromInstitution(e.getCourse().getInstitution().getId(), PersonalizedAccessDeniedException.class);
		
		if (dto.getCourse() == null || dto.getStudent() == null || dto.getCourse().getId() != e.getCourse().getId() || dto.getStudent().getId() != e.getStudent().getId())
			throw new EntityValidationException();

		Enrollment eNew = enrollmentConverter.convertToJPA(dto);
		
		e.setStartDate(eNew.getStartDate());
		enrollmentRepo.save(e);
	}

	@AuthorizeAdmin
	@Override
	public void delete(Integer id) {
		Enrollment e = enrollmentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());

		authorizator.assertPrincipalIsFromInstitution(e.getCourse().getInstitution().getId(), PersonalizedAccessDeniedException.class);
		
		enrollmentRepo.deleteById(id);
	}

	@AuthorizeStudentOrAdmin
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentEnrollmentDTO> filterEnrollmentsByStudent(int studentId, Pageable pageable, StudentEnrollmentDTO filterOptions) {
		if (getPrincipal().isStudent())
			authorizator.assertStudentIdIs(studentId, PersonalizedAccessDeniedException.class);
		else if (getPrincipal().isAdmin()) {
			DefaultStudentDTO s = studentService.getOne(studentId);
			authorizator.assertPrincipalIsFromInstitution(s.getInstitution().getId(), PersonalizedAccessDeniedException.class);
		}
		
		if (filterOptions == null) {
			Page<Enrollment> page = enrollmentRepo.findByStudent_Id(studentId, pageable);
			return (List<StudentEnrollmentDTO>) enrollmentConverter.convertToDTO(page.getContent(), StudentEnrollmentDTO.class);
		}
		else {
			Page<Enrollment> page = enrollmentRepo.filterEnrollmentsByStudent(studentId, null, null, null, null, null, null, null, pageable);
			return (List<StudentEnrollmentDTO>) enrollmentConverter.convertToDTO(page.getContent(), StudentEnrollmentDTO.class);
		}
	}

	@AuthorizeTeacherOrAdmin
	@SuppressWarnings("unchecked")
	@Override
	public Page<CourseEnrollmentDTO> filterEnrollmentsByCourse(int courseId, Pageable pageable, CourseEnrollmentDTO filterOptions) {
		if (getPrincipal().isTeacher())
			authorizator.assertTeacherIsTeachingCourse(courseId, PersonalizedAccessDeniedException.class);
		else if (getPrincipal().isAdmin()) {
			DefaultCourseDTO course = courseService.getOne(courseId);
			authorizator.assertPrincipalIsFromInstitution(course.getInstitution().getId(), PersonalizedAccessDeniedException.class);
		}
		if (filterOptions == null) {
			Page<Enrollment> page = enrollmentRepo.findByCourse_Id(courseId, pageable);
			return page.map(new Function<Enrollment, CourseEnrollmentDTO>() {
				@Override
				public CourseEnrollmentDTO apply(Enrollment enrollment) {
					return enrollmentConverter.convertToDTO(enrollment, CourseEnrollmentDTO.class);
				}
			});
		}
		else {
			Page<Enrollment> page = enrollmentRepo.filterEnrollmentsByCourse(courseId, null, null, null, null, null, null, null, pageable);
			return page.map(new Function<Enrollment, CourseEnrollmentDTO>() {
				@Override
				public CourseEnrollmentDTO apply(Enrollment enrollment) {
					return enrollmentConverter.convertToDTO(enrollment, CourseEnrollmentDTO.class);
				}
			});
		}
	}

	@Override
	public List<EnrollmentExamObligationTakingDTO> getEnrollmentExamObligationTakings(int enrollmentId,
			Pageable pageable) {
		if (!enrollmentRepo.existsById(enrollmentId)) throw new EntityNotFoundException();
		
		return examObligationTakingService.filterTakingsByEnrollment(enrollmentId, pageable, null);
	}

	@Override
	public List<EnrollmentExamTakingDTO> getEnrollmentExamTakings(int enrollmentId, Pageable pageable) {
		if (!enrollmentRepo.existsById(enrollmentId)) throw new EntityNotFoundException();
		
		return examTakingService.filterTakingsByEnrollment(enrollmentId, pageable, null);
	}

}
