package com.ftn.tseo2021.sf1513282018.studentService.service.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.institution.InstitutionService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.ExamTakingService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Exam;
import com.ftn.tseo2021.sf1513282018.studentService.security.PrincipalHolder;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAdmin;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAny;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeTeacher;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.CourseExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.ExamExamTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.ExamPeriodExamDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

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
	private PrincipalHolder principalHolder;

	private void authorize(Integer institutionId) throws PersonalizedAccessDeniedException {
		if (principalHolder.getCurrentPrincipal().getInstitutionId() != institutionId)
			throw new PersonalizedAccessDeniedException();
	}

	@AuthorizeAny
	@Override
	public DefaultExamDTO getOne(Integer id) {
		Optional<Exam> exam = examRepo.findById(id);
		return examConverter.convertToDTO(exam.orElse(null));
	}

	@AuthorizeAdmin
	@AuthorizeTeacher
	@Override
	public Integer create(DefaultExamDTO dto) throws IllegalArgumentException, PersonalizedAccessDeniedException {
		Exam exam = examConverter.convertToJPA(dto);

		exam = examRepo.save(exam);

		return exam.getId();
	}

	@AuthorizeAdmin
	@AuthorizeTeacher
	@Override
	public void update(Integer id, DefaultExamDTO dto) throws EntityNotFoundException, IllegalArgumentException, PersonalizedAccessDeniedException {
		DefaultInstitutionDTO institution = institutionService.getOne(dto.getExamPeriod().getInstitution().getId());
		authorize(institution.getId());

		if(!examRepo.existsById(id)) throw new EntityNotFoundException();

		Exam eNew = examConverter.convertToJPA(dto);

		Exam e = examRepo.findById(id).get();
		e.setDateTime(eNew.getDateTime());
		e.setClassroom(eNew.getClassroom());
		e.setPoints(eNew.getPoints());
		e.setDescription(eNew.getDescription());
		examRepo.save(e);
	}

	@AuthorizeAdmin
	@AuthorizeTeacher
	@Override
	public void delete(Integer id) throws PersonalizedAccessDeniedException {
		Exam exam = examRepo.getOne(id);
		authorize(exam.getExamPeriod().getInstitution().getId());

		if(!examRepo.existsById(id)) {}

		examRepo.deleteById(id);
	}

	@SuppressWarnings("unchecked")
	@AuthorizeAny
	@Override
	public List<CourseExamDTO> filterExamsByCourse(int courseId, Pageable pageable, CourseExamDTO filterOptions) {
		if (filterOptions == null) {
			Page<Exam> page = examRepo.findByCourse_Id(courseId, pageable);
			return (List<CourseExamDTO>) examConverter.convertToDTO(page.getContent(), CourseExamDTO.class);
		}
		else {
			Page<Exam> page = examRepo.filterExamsByCourse(courseId, filterOptions.getDescription(),
					filterOptions.getClassroom(), null, null, pageable);
			return (List<CourseExamDTO>) examConverter.convertToDTO(page.getContent(), CourseExamDTO.class);
		}
	}

	@SuppressWarnings("unchecked")
	@AuthorizeAny
	@Override
	public List<ExamPeriodExamDTO> filterExamsByExamPeriod(int examPeriodId, Pageable pageable, ExamPeriodExamDTO filterOptions) {
		if (filterOptions == null) {
			Page<Exam> page = examRepo.findByExamPeriod_Id(examPeriodId, pageable);
			return (List<ExamPeriodExamDTO>) examConverter.convertToDTO(page.getContent(), ExamPeriodExamDTO.class);
		}
		else {
			Page<Exam> page = examRepo.filterExamsByExamPeriod(examPeriodId, filterOptions.getDescription(), 
					filterOptions.getClassroom(), null, null, pageable);
			return (List<ExamPeriodExamDTO>) examConverter.convertToDTO(page.getContent(), ExamPeriodExamDTO.class);
		}
	}

	@AuthorizeAny
	@Override
	public List<ExamExamTakingDTO> getExamExamTakings(int examId, Pageable pageable) throws EntityNotFoundException {
		if(!examRepo.existsById(examId)) throw new EntityNotFoundException();
		
		return examTakingService.filterTakingsByExam(examId, pageable, null);
	}
}
