package com.ftn.tseo2021.sf1513282018.studentService.service.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.institution.InstitutionService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.ForbiddenAccessException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamPeriod;
import com.ftn.tseo2021.sf1513282018.studentService.security.AuthorizeAdmin;
import com.ftn.tseo2021.sf1513282018.studentService.security.AuthorizeAny;
import com.ftn.tseo2021.sf1513282018.studentService.security.PrincipalHolder;

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
import java.util.Optional;

@Service
public class DefaultExamPeriodService implements ExamPeriodService {
	
	@Autowired
	private ExamPeriodRepository examPeriodRepo;

	@Autowired
	private DtoConverter<ExamPeriod, ExamPeriodDTO, DefaultExamPeriodDTO> examPeriodConverter;

	@Autowired
	private ExamService examService;

	@Autowired
	private InstitutionService institutionService;
	
	@Autowired
	private PrincipalHolder principalHolder;
	
	private void authorize(Integer institutionId) throws ForbiddenAccessException {
		if (principalHolder.getCurrentPrincipal().getInstitutionId() != institutionId) 
			throw new ForbiddenAccessException();
	}

	@AuthorizeAny
	@Override
	public DefaultExamPeriodDTO getOne(Integer id) {
		Optional<ExamPeriod> examPeriod = examPeriodRepo.findById(id);
		return examPeriodConverter.convertToDTO(examPeriod.orElse(null));
	}

	@AuthorizeAdmin
	@Override
	public Integer create(DefaultExamPeriodDTO dto) throws IllegalArgumentException, ForbiddenAccessException {
		ExamPeriod examPeriod = examPeriodConverter.convertToJPA(dto);
		examPeriod.getInstitution().setId(principalHolder.getCurrentPrincipal().getInstitutionId());

		examPeriod = examPeriodRepo.save(examPeriod);

		return examPeriod.getId();
	}

	@AuthorizeAdmin
	@Override
	public void update(Integer id, DefaultExamPeriodDTO dto) throws EntityNotFoundException, IllegalArgumentException, ForbiddenAccessException {
		DefaultInstitutionDTO institution = institutionService.getOne(dto.getInstitution().getId());
		authorize(institution.getId());

		if(!examPeriodRepo.existsById(id)) throw new EntityNotFoundException();

		ExamPeriod epNew = examPeriodConverter.convertToJPA(dto);

		ExamPeriod ep = examPeriodRepo.findById(id).get();
		ep.setName(epNew.getName());
		ep.setStartDate(epNew.getStartDate());
		ep.setEndDate(epNew.getEndDate());
		examPeriodRepo.save(ep);
	}

	@AuthorizeAdmin
	@Override
	public boolean delete(Integer id) throws ForbiddenAccessException {
		ExamPeriod examPeriod = examPeriodRepo.getOne(id);

		authorize(examPeriod.getInstitution().getId());

		if(!examPeriodRepo.existsById(id)) return false;

		examPeriodRepo.deleteById(id);
		return true;
	}

	@SuppressWarnings("unchecked")
	@AuthorizeAny
	@Override
	public List<InstitutionExamPeriodDTO> filterExamPeriods(int institutionId, Pageable pageable, InstitutionExamPeriodDTO filterOptions) 
			throws ForbiddenAccessException {
		authorize(institutionId);
		
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
