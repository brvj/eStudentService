package com.ftn.tseo2021.sf1513282018.studentService.service.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.EntityValidationException;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.ResourceNotFoundException;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamPeriod;
import com.ftn.tseo2021.sf1513282018.studentService.security.CustomPrincipal;
import com.ftn.tseo2021.sf1513282018.studentService.security.PersonalizedAuthorizator;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAdmin;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAny;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamPeriodRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamPeriodService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.ExamPeriodExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.InstitutionExamPeriodDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class DefaultExamPeriodService implements ExamPeriodService {
	
	@Autowired
	private ExamPeriodRepository examPeriodRepo;

	@Autowired
	private DtoConverter<ExamPeriod, ExamPeriodDTO, DefaultExamPeriodDTO> examPeriodConverter;

	@Autowired
	private ExamService examService;

	@Autowired
	private PersonalizedAuthorizator authorizator;
	
	@Autowired
	private CustomPrincipal getPrincipal() { return authorizator.getPrincipal(); }

	@AuthorizeAny
	@Override
	public DefaultExamPeriodDTO getOne(Integer id) {
		ExamPeriod ep = examPeriodRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());

		if(getPrincipal().isAdmin() || getPrincipal().isTeacher() || getPrincipal().isStudent())
			authorizator.assertPrincipalIsFromInstitution(ep.getInstitution().getId(), PersonalizedAccessDeniedException.class);

		return examPeriodConverter.convertToDTO(ep);
	}

	@AuthorizeAdmin
	@Override
	public Integer create(DefaultExamPeriodDTO dto){
		authorizator.assertPrincipalIsFromInstitution(dto.getInstitution().getId(), EntityValidationException.class);

		ExamPeriod ep = examPeriodConverter.convertToJPA(dto);

		ep = examPeriodRepo.save(ep);

		return ep.getId();
	}

	@AuthorizeAdmin
	@Override
	public void update(Integer id, DefaultExamPeriodDTO dto) throws EntityNotFoundException, IllegalArgumentException, PersonalizedAccessDeniedException {
		ExamPeriod ep = examPeriodRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());

		authorizator.assertPrincipalIsFromInstitution(ep.getInstitution().getId(), PersonalizedAccessDeniedException.class);

		ExamPeriod epNew = examPeriodConverter.convertToJPA(dto);

		ep.setName(epNew.getName());
		ep.setStartDate(epNew.getStartDate());
		ep.setEndDate(epNew.getEndDate());
		examPeriodRepo.save(ep);
	}

	@AuthorizeAdmin
	@Override
	public void delete(Integer id) throws PersonalizedAccessDeniedException {
		ExamPeriod examPeriod = examPeriodRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());

		authorizator.assertPrincipalIsFromInstitution(examPeriod.getInstitution().getId(), PersonalizedAccessDeniedException.class);

		examPeriodRepo.deleteById(id);
	}

	@SuppressWarnings("unchecked")
	@AuthorizeAny
	@Override
	public List<InstitutionExamPeriodDTO> filterExamPeriods(int institutionId, Pageable pageable, InstitutionExamPeriodDTO filterOptions) 
			throws PersonalizedAccessDeniedException {
		if(getPrincipal().isAdmin() || getPrincipal().isTeacher() || getPrincipal().isStudent())
			authorizator.assertPrincipalIsFromInstitution(institutionId, PersonalizedAccessDeniedException.class);
		
		if (filterOptions == null) {
			Page<ExamPeriod> page = examPeriodRepo.findByInstitution_Id(institutionId, pageable);
			return (List<InstitutionExamPeriodDTO>) examPeriodConverter.convertToDTO(page.getContent(), InstitutionExamPeriodDTO.class);
		}
		else {
			Page<ExamPeriod> page = examPeriodRepo.filterExamPeriods(institutionId, filterOptions.getName(), null, null, pageable);
			return (List<InstitutionExamPeriodDTO>) examPeriodConverter.convertToDTO(page.getContent(), InstitutionExamPeriodDTO.class);
		}

	}

	@AuthorizeAny
	@Override
	public List<ExamPeriodExamDTO> getExamPeriodExams(int examPeriodId, Pageable pageable) throws EntityNotFoundException {
		if(!examPeriodRepo.existsById(examPeriodId)) throw new EntityNotFoundException();

		return examService.filterExamsByExamPeriod(examPeriodId, pageable, null);
	}
}
