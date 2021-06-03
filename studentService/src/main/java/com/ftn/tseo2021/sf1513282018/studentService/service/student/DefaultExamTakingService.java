package com.ftn.tseo2021.sf1513282018.studentService.service.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.ExamTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.ExamTakingRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.EnrollmentService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.ExamTakingService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.EntityValidationException;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.ResourceNotFoundException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.ExamExamTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultExamTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.EnrollmentExamTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.ExamTaking;
import com.ftn.tseo2021.sf1513282018.studentService.security.CustomPrincipal;
import com.ftn.tseo2021.sf1513282018.studentService.security.PersonalizedAuthorizator;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAny;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeStudentOrAdmin;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeTeacherOrAdmin;

@Service
public class DefaultExamTakingService implements ExamTakingService {
	
	@Autowired
	private ExamTakingRepository examTakingRepo;
	
	@Autowired
	private DtoConverter<ExamTaking, ExamTakingDTO, DefaultExamTakingDTO> examTakingConverter;
	
	@Autowired
	private EnrollmentService enrollmentService;
	
	@Autowired
	private ExamService examService;
	
	@Autowired
	private PersonalizedAuthorizator authorizator;
	
	private CustomPrincipal getPrincipal() { return authorizator.getPrincipal(); }

	@AuthorizeAny
	@Override
	public DefaultExamTakingDTO getOne(Integer id) {
		ExamTaking taking = examTakingRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		
		if (getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(taking.getExam().getCourse().getInstitution().getId(), PersonalizedAccessDeniedException.class);
		else if (getPrincipal().isStudent())
			authorizator.assertPrincipalStudentIdIs(taking.getEnrollment().getStudent().getId(), PersonalizedAccessDeniedException.class);
		else if (getPrincipal().isTeacher())
			authorizator.assertTeacherIsTeachingCourse(taking.getExam().getCourse().getId(), PersonalizedAccessDeniedException.class);
		
		return examTakingConverter.convertToDTO(taking);
	}

	@AuthorizeTeacherOrAdmin
	@Override
	public Integer create(DefaultExamTakingDTO dto) {
		try {
			DefaultEnrollmentDTO enrollment = enrollmentService.getOne(dto.getEnrollment().getId());
			DefaultExamDTO exam = examService.getOne(dto.getExam().getId());
			if (enrollment.getCourse().getId() != exam.getCourse().getId()) throw new EntityValidationException();
			
			if (getPrincipal().isAdmin())
				authorizator.assertPrincipalIsFromInstitution(enrollment.getCourse().getInstitution().getId(), EntityValidationException.class);
			else if (getPrincipal().isTeacher())
				authorizator.assertTeacherIsTeachingCourse(enrollment.getCourse().getId(), EntityValidationException.class);
		} 
		catch (ResourceNotFoundException | NullPointerException e) {throw new EntityValidationException();}
		
		ExamTaking taking = examTakingConverter.convertToJPA(dto);
		
		taking = examTakingRepo.save(taking);
		
		return taking.getId();
	}

	@AuthorizeTeacherOrAdmin
	@Override
	public void update(Integer id, DefaultExamTakingDTO dto) {
		ExamTaking t = examTakingRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		
		if (getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(t.getEnrollment().getCourse().getInstitution().getId(), PersonalizedAccessDeniedException.class);
		else if (getPrincipal().isTeacher())
			authorizator.assertTeacherIsTeachingCourse(t.getEnrollment().getCourse().getId(), PersonalizedAccessDeniedException.class);
		
		if (dto.getEnrollment() == null || dto.getExam() == null || dto.getEnrollment().getId() != t.getEnrollment().getId() || dto.getExam().getId() != t.getExam().getId())
			throw new EntityValidationException();
		
		ExamTaking tNew = examTakingConverter.convertToJPA(dto);
		
		t.setScore(tNew.getScore());
		examTakingRepo.save(t);
	}

	@AuthorizeTeacherOrAdmin
	@Override
	public void delete(Integer id) {
		ExamTaking t = examTakingRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		
		if (getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(t.getEnrollment().getCourse().getInstitution().getId(), PersonalizedAccessDeniedException.class);
		else if (getPrincipal().isTeacher())
			authorizator.assertTeacherIsTeachingCourse(t.getEnrollment().getCourse().getId(), PersonalizedAccessDeniedException.class);
		
		examTakingRepo.deleteById(id);
	}

	@AuthorizeTeacherOrAdmin
	@SuppressWarnings("unchecked")
	@Override
	public List<ExamExamTakingDTO> filterTakingsByExam(int examId, Pageable pageable, ExamExamTakingDTO filterOptions) {
		DefaultExamDTO exam = examService.getOne(examId);
		
		if (getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(exam.getCourse().getInstitution().getId(), PersonalizedAccessDeniedException.class);
		else if (getPrincipal().isTeacher())
			authorizator.assertTeacherIsTeachingCourse(exam.getCourse().getId(), PersonalizedAccessDeniedException.class);
		
		if (filterOptions == null) {
			Page<ExamTaking> page = examTakingRepo.findByExam_Id(examId, pageable);
			return (List<ExamExamTakingDTO>) examTakingConverter.convertToDTO(page.getContent(), ExamExamTakingDTO.class);
		}
		else {
			Page<ExamTaking> page = examTakingRepo.filterTakingsByExam(examId, null, null, pageable);
			return (List<ExamExamTakingDTO>) examTakingConverter.convertToDTO(page.getContent(), ExamExamTakingDTO.class);
		}
	}

	@AuthorizeStudentOrAdmin
	@SuppressWarnings("unchecked")
	@Override
	public List<EnrollmentExamTakingDTO> filterTakingsByEnrollment(int enrollmentId, Pageable pageable, EnrollmentExamTakingDTO filterOptions) {
		DefaultEnrollmentDTO enrollment = enrollmentService.getOne(enrollmentId);
		
		if (getPrincipal().isStudent())
			authorizator.assertPrincipalStudentIdIs(enrollment.getStudent().getId(), PersonalizedAccessDeniedException.class);
		else if (getPrincipal().isAdmin()) {
			authorizator.assertPrincipalIsFromInstitution(enrollment.getStudent().getInstitution().getId(), PersonalizedAccessDeniedException.class);
		}
		
		if (filterOptions == null) {
			Page<ExamTaking> page = examTakingRepo.findByEnrollment_Id(enrollmentId, pageable);
			return (List<EnrollmentExamTakingDTO>) examTakingConverter.convertToDTO(page.getContent(), EnrollmentExamTakingDTO.class);
		}
		else {
			Page<ExamTaking> page = examTakingRepo.filterTakingsByEnrollment(enrollmentId, null, null, pageable);
			return (List<EnrollmentExamTakingDTO>) examTakingConverter.convertToDTO(page.getContent(), EnrollmentExamTakingDTO.class);
		}
	}

}
