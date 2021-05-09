package com.ftn.tseo2021.sf1513282018.studentService.service.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamObligationDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.CourseService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.ExamObligationTakingService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.ForbiddenAccessException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultCourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamObligation;
import com.ftn.tseo2021.sf1513282018.studentService.security.AuthorizeAdmin;
import com.ftn.tseo2021.sf1513282018.studentService.security.AuthorizeAny;
import com.ftn.tseo2021.sf1513282018.studentService.security.AuthorizeTeacher;
import com.ftn.tseo2021.sf1513282018.studentService.security.PrincipalHolder;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamObligationRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamObligationService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.CourseExamObligationDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamObligationDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.ExamOblExamObligationTakingDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

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
	private PrincipalHolder principalHolder;

	private void authorize(Integer institutionId) throws ForbiddenAccessException {
		if (principalHolder.getCurrentPrincipal().getInstitutionId() != institutionId)
			throw new ForbiddenAccessException();
	}

	@AuthorizeAny
	@Override
	public DefaultExamObligationDTO getOne(Integer id) {
		Optional<ExamObligation> examObligation = examObligationRepo.findById(id);
		return examObligationConverter.convertToDTO(examObligation.orElse(null));
	}

	@AuthorizeAdmin
	@AuthorizeTeacher
	@Override
	public Integer create(DefaultExamObligationDTO dto) throws IllegalArgumentException, ForbiddenAccessException {
		authorize(dto.getCourse().getInstitution().getId());

		ExamObligation examObligation = examObligationConverter.convertToJPA(dto);

		examObligation = examObligationRepo.save(examObligation);

		return examObligation.getId();
	}

	@AuthorizeAdmin
	@AuthorizeTeacher
	@Override
	public void update(Integer id, DefaultExamObligationDTO dto) throws EntityNotFoundException, IllegalArgumentException, ForbiddenAccessException {
		authorize(dto.getCourse().getInstitution().getId());

		if(!examObligationRepo.existsById(id)) throw new EntityNotFoundException();

		ExamObligation eoNew = examObligationConverter.convertToJPA(dto);
		
		ExamObligation eo = examObligationRepo.findById(id).get();
		eo.setPoints(eoNew.getPoints());
		eo.setDescription(eoNew.getDescription());
		eo.setExamObligationType(eoNew.getExamObligationType());
		examObligationRepo.save(eo);
	}

	@AuthorizeAdmin
	@AuthorizeTeacher
	@Override
	public boolean delete(Integer id) throws ForbiddenAccessException {
		ExamObligation examObligation = examObligationRepo.getOne(id);

		authorize(examObligation.getCourse().getInstitution().getId());

		if(!examObligationRepo.existsById(id)) return false;

		examObligationRepo.deleteById(id);
		return true;
	}

	@SuppressWarnings("unchecked")
	@AuthorizeAny
	@Override
	public List<CourseExamObligationDTO> filterExamObligations(int courseId, Pageable pageable, CourseExamObligationDTO filterOptions) throws ForbiddenAccessException {
		DefaultCourseDTO course = courseService.getOne(courseId);

		authorize(course.getInstitution().getId());

		if (filterOptions == null) {
			Page<ExamObligation> page = examObligationRepo.findByCourse_Id(courseId, pageable);
			return (List<CourseExamObligationDTO>) examObligationConverter.convertToDTO(page.getContent(), CourseExamObligationDTO.class);
		}
		else {
			Page<ExamObligation> page = examObligationRepo.filterExamObligations(courseId,
					filterOptions.getDescription(), filterOptions.getExamObligationType().getId(), pageable);
			return (List<CourseExamObligationDTO>) examObligationConverter.convertToDTO(page.getContent(), CourseExamObligationDTO.class);
		}
	}

	@AuthorizeAny
	@Override
	public List<ExamOblExamObligationTakingDTO> getExamObligationTakings(int examObligationId, Pageable pageable)
			throws EntityNotFoundException {
		if(!examObligationRepo.existsById(examObligationId)) throw new EntityNotFoundException();
		
		return examObligationTakingService.filterTakingsByExamObligation(examObligationId, pageable, null);
	}
}
