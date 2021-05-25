package com.ftn.tseo2021.sf1513282018.studentService.service.student;

import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.ExamObligationTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.ExamObligationTakingRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamObligationService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.EnrollmentService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.ExamObligationTakingService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.EntityValidationException;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.ResourceNotFoundException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamObligationDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.ExamOblExamObligationTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultExamObligationTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.EnrollmentExamObligationTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.ExamObligationTaking;
import com.ftn.tseo2021.sf1513282018.studentService.security.CustomPrincipal;
import com.ftn.tseo2021.sf1513282018.studentService.security.PersonalizedAuthorizator;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAny;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeStudentOrAdmin;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeTeacherOrAdmin;

@Service
public class DefaultExamObligationTakingService implements ExamObligationTakingService {
	
	@Autowired
	private ExamObligationTakingRepository examObligationTakingRepo;
	
	@Autowired
	private DtoConverter<ExamObligationTaking, ExamObligationTakingDTO, DefaultExamObligationTakingDTO> examObligationTakingConverter;

	@Autowired
	private EnrollmentService enrollmentService;
	
	@Autowired
	private ExamObligationService examObligationService;
	
	@Autowired
	private PersonalizedAuthorizator authorizator;
	
	private CustomPrincipal getPrincipal() { return authorizator.getPrincipal(); }
	
	@AuthorizeAny
	@Override
	public DefaultExamObligationTakingDTO getOne(Integer id) {
		ExamObligationTaking taking = examObligationTakingRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		
		if (getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(taking.getExamObligation().getCourse().getInstitution().getId(), PersonalizedAccessDeniedException.class);
		else if (getPrincipal().isStudent())
			authorizator.assertStudentIdIs(taking.getEnrollment().getStudent().getId(), PersonalizedAccessDeniedException.class);
		else if (getPrincipal().isTeacher())
			authorizator.assertTeacherIsTeachingCourse(taking.getExamObligation().getCourse().getId(), PersonalizedAccessDeniedException.class);
		
		return examObligationTakingConverter.convertToDTO(taking);
	}

	@AuthorizeTeacherOrAdmin
	@Override
	public Integer create(DefaultExamObligationTakingDTO dto) {
		try {
			DefaultEnrollmentDTO enrollment = enrollmentService.getOne(dto.getEnrollment().getId());
			DefaultExamObligationDTO exam = examObligationService.getOne(dto.getExamObligation().getId());
			if (enrollment.getCourse().getId() != exam.getCourse().getId()) throw new EntityValidationException();
			
			if (getPrincipal().isAdmin())
				authorizator.assertPrincipalIsFromInstitution(enrollment.getCourse().getInstitution().getId(), EntityValidationException.class);
			else if (getPrincipal().isTeacher())
				authorizator.assertTeacherIsTeachingCourse(enrollment.getCourse().getId(), EntityValidationException.class);
		} 
		catch (ResourceNotFoundException | NullPointerException e) {throw new EntityValidationException();}
				
		
		ExamObligationTaking taking = examObligationTakingConverter.convertToJPA(dto);
		
		taking = examObligationTakingRepo.save(taking);
		
		return taking.getId();
	}

	@AuthorizeTeacherOrAdmin
	@Override
	public void update(Integer id, DefaultExamObligationTakingDTO dto) {
		ExamObligationTaking t = examObligationTakingRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		
		if (getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(t.getEnrollment().getCourse().getInstitution().getId(), PersonalizedAccessDeniedException.class);
		else if (getPrincipal().isTeacher())
			authorizator.assertTeacherIsTeachingCourse(t.getEnrollment().getCourse().getId(), PersonalizedAccessDeniedException.class);
		
		if (dto.getEnrollment() == null || dto.getExamObligation() == null || dto.getEnrollment().getId() != t.getEnrollment().getId() || dto.getExamObligation().getId() != t.getExamObligation().getId())
			throw new EntityValidationException();
		
		ExamObligationTaking tNew = examObligationTakingConverter.convertToJPA(dto);

		t.setScore(tNew.getScore());
		examObligationTakingRepo.save(t);
		
	}

	@AuthorizeTeacherOrAdmin
	@Override
	public void delete(Integer id) {
		ExamObligationTaking t = examObligationTakingRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		
		if (getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(t.getEnrollment().getCourse().getInstitution().getId(), PersonalizedAccessDeniedException.class);
		else if (getPrincipal().isTeacher())
			authorizator.assertTeacherIsTeachingCourse(t.getEnrollment().getCourse().getId(), PersonalizedAccessDeniedException.class);
		
		examObligationTakingRepo.deleteById(id);
	}

	@AuthorizeTeacherOrAdmin
	@SuppressWarnings("unchecked")
	@Override
	public Page<ExamOblExamObligationTakingDTO> filterTakingsByExamObligation(int examObligationId, Pageable pageable, ExamOblExamObligationTakingDTO filterOptions) {
		DefaultExamObligationDTO exam = examObligationService.getOne(examObligationId);
		
		if (getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(exam.getCourse().getInstitution().getId(), PersonalizedAccessDeniedException.class);
		else if (getPrincipal().isTeacher())
			authorizator.assertTeacherIsTeachingCourse(exam.getCourse().getId(), PersonalizedAccessDeniedException.class);
		
		if (filterOptions == null) {
			Page<ExamObligationTaking> page = examObligationTakingRepo.findByExamObligation_Id(examObligationId, pageable);
			return page.map(new Function<ExamObligationTaking, ExamOblExamObligationTakingDTO>() {
				@Override
				public ExamOblExamObligationTakingDTO apply(ExamObligationTaking examObligationTaking) {
					return examObligationTakingConverter.convertToDTO(examObligationTaking, ExamOblExamObligationTakingDTO.class);
				}
			});
		}
		else {
			Page<ExamObligationTaking> page = examObligationTakingRepo.filterTakingsByExamObligation(examObligationId, null, null, pageable);
			return page.map(new Function<ExamObligationTaking, ExamOblExamObligationTakingDTO>() {
				@Override
				public ExamOblExamObligationTakingDTO apply(ExamObligationTaking examObligationTaking) {
					return examObligationTakingConverter.convertToDTO(examObligationTaking, ExamOblExamObligationTakingDTO.class);
				}
			});
		}
	}

	@AuthorizeStudentOrAdmin
	@SuppressWarnings("unchecked")
	@Override
	public List<EnrollmentExamObligationTakingDTO> filterTakingsByEnrollment(int enrollmentId, Pageable pageable, EnrollmentExamObligationTakingDTO filterOptions) {
		DefaultEnrollmentDTO enrollment = enrollmentService.getOne(enrollmentId);
		
		if (getPrincipal().isStudent())
			authorizator.assertStudentIdIs(enrollment.getStudent().getId(), PersonalizedAccessDeniedException.class);
		else if (getPrincipal().isAdmin()) {
			authorizator.assertPrincipalIsFromInstitution(enrollment.getStudent().getInstitution().getId(), PersonalizedAccessDeniedException.class);
		}
		
		if (filterOptions == null) {
			Page<ExamObligationTaking> page = examObligationTakingRepo.findByEnrollment_Id(enrollmentId, pageable);
			return (List<EnrollmentExamObligationTakingDTO>) examObligationTakingConverter.convertToDTO(page.getContent(), EnrollmentExamObligationTakingDTO.class);
		}
		else {
			Page<ExamObligationTaking> page = examObligationTakingRepo.filterTakingsByEnrollment(enrollmentId, null, null, pageable);
			return (List<EnrollmentExamObligationTakingDTO>) examObligationTakingConverter.convertToDTO(page.getContent(), EnrollmentExamObligationTakingDTO.class);
		}
	}

}
