package com.ftn.tseo2021.sf1513282018.studentService.service.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamObligationDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.CourseService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.ExamObligationTakingService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.ResourceNotFoundException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.*;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamObligation;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Enrollment;
import com.ftn.tseo2021.sf1513282018.studentService.security.CustomPrincipal;
import com.ftn.tseo2021.sf1513282018.studentService.security.PersonalizedAuthorizator;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAny;

import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeTeacherOrAdmin;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamObligationRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamObligationService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.function.Function;

@Service
public class DefaultExamObligationService implements ExamObligationService {
	
	@Autowired
	private ExamObligationRepository examObligationRepo;

	@Autowired
	private DtoConverter<ExamObligation, ExamObligationDTO, DefaultExamObligationDTO> examObligationConverter;

	@Autowired
	private ExamObligationTakingService examObligationTakingService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private PersonalizedAuthorizator authorizator;

	private CustomPrincipal getPrincipal() { return authorizator.getPrincipal(); }

	@AuthorizeAny
	@Override
	public DefaultExamObligationDTO getOne(Integer id) {
		ExamObligation eo = examObligationRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());

		if(getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(eo.getCourse().getInstitution().getId(), PersonalizedAccessDeniedException.class);
		else if(getPrincipal().isTeacher())
			authorizator.assertTeacherIsTeachingCourse(eo.getCourse().getId(), PersonalizedAccessDeniedException.class);
		else if(getPrincipal().isStudent()) {
			authorizator.assertStudentIsEnrollingCourse(eo.getCourse().getId(), PersonalizedAccessDeniedException.class);
		}

		return examObligationConverter.convertToDTO(eo);
	}

	@AuthorizeTeacherOrAdmin
	@Override
	public Integer create(DefaultExamObligationDTO dto){
		if(getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(dto.getCourse().getInstitution().getId(), PersonalizedAccessDeniedException.class);
		else if(getPrincipal().isTeacher())
			authorizator.assertTeacherIsTeachingCourse(dto.getCourse().getId(), PersonalizedAccessDeniedException.class);

		ExamObligation eo = examObligationConverter.convertToJPA(dto);
		eo = examObligationRepo.save(eo);

		return eo.getId();
	}

	@AuthorizeTeacherOrAdmin
	@Override
	public void update(Integer id, DefaultExamObligationDTO dto) throws EntityNotFoundException, IllegalArgumentException, PersonalizedAccessDeniedException {
		ExamObligation eo = examObligationRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());

		if(getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(dto.getCourse().getInstitution().getId(), PersonalizedAccessDeniedException.class);
		else if(getPrincipal().isTeacher())
			authorizator.assertTeacherIsTeachingCourse(dto.getCourse().getId(), PersonalizedAccessDeniedException.class);

		ExamObligation eoNew = examObligationConverter.convertToJPA(dto);

		eo.setPoints(eoNew.getPoints());
		eo.setDescription(eoNew.getDescription());
		eo.setExamObligationType(eoNew.getExamObligationType());
		examObligationRepo.save(eo);
	}

	@AuthorizeTeacherOrAdmin
	@Override
	public void delete(Integer id) throws PersonalizedAccessDeniedException {
		ExamObligation examObligation = examObligationRepo.getOne(id);

		if(getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(examObligation.getCourse().getInstitution().getId(), PersonalizedAccessDeniedException.class);
		else if(getPrincipal().isTeacher())
			authorizator.assertTeacherIsTeachingCourse(examObligation.getCourse().getId(), PersonalizedAccessDeniedException.class);

		examObligationRepo.deleteById(id);
	}

	@SuppressWarnings("unchecked")
	@AuthorizeAny
	@Override
	public Page<CourseExamObligationDTO> filterExamObligations(int courseId, Pageable pageable, CourseExamObligationDTO filterOptions) throws PersonalizedAccessDeniedException {
		DefaultCourseDTO course = courseService.getOne(courseId);

		if(getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(course.getInstitution().getId(), PersonalizedAccessDeniedException.class);
		else if(getPrincipal().isTeacher())
			authorizator.assertTeacherIsTeachingCourse(course.getId(), PersonalizedAccessDeniedException.class);
		else if(getPrincipal().isStudent()) {
			authorizator.assertStudentIsEnrollingCourse(courseId, PersonalizedAccessDeniedException.class);
		}

		if (filterOptions == null) {
			Page<ExamObligation> page = examObligationRepo.findByCourse_Id(courseId, pageable);
			return page.map(new Function<ExamObligation, CourseExamObligationDTO>() {
				@Override
				public CourseExamObligationDTO apply(ExamObligation examObligation) {
					return examObligationConverter.convertToDTO(examObligation, CourseExamObligationDTO.class);
				}
			});
		}
		else {
			Page<ExamObligation> page = examObligationRepo.filterExamObligations(courseId,
					filterOptions.getDescription(), filterOptions.getExamObligationType().getId(), pageable);
			return page.map(new Function<ExamObligation, CourseExamObligationDTO>() {
				@Override
				public CourseExamObligationDTO apply(ExamObligation examObligation) {
					return examObligationConverter.convertToDTO(examObligation, CourseExamObligationDTO.class);
				}
			});
		}
	}

	@AuthorizeAny
	@Override
	public Page<ExamOblExamObligationTakingDTO> getExamObligationTakings(int examObligationId, Pageable pageable)
			throws EntityNotFoundException {
		if(!examObligationRepo.existsById(examObligationId)) throw new EntityNotFoundException();
		
		return examObligationTakingService.filterTakingsByExamObligation(examObligationId, pageable, null);
	}
}
