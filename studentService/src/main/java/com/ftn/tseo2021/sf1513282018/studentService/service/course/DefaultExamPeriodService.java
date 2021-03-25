package com.ftn.tseo2021.sf1513282018.studentService.service.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.institution.InstitutionRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamPeriod;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamPeriodRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamPeriodService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamPeriodDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DefaultExamPeriodService implements ExamPeriodService {
	
	@Autowired
	ExamPeriodRepository examPeriodRepo;

	@Autowired
	DtoConverter<ExamPeriod, ExamPeriodDTO, DefaultExamPeriodDTO> examPeriodConverter;

	@Autowired
	InstitutionRepository institutionRepo;

	@Autowired
	ExamRepository examRepo;

	@Override
	public DefaultExamPeriodDTO getOne(Integer id) {
		Optional<ExamPeriod> examPeriod = examPeriodRepo.findById(id);
		return examPeriodConverter.convertToDTO(examPeriod.orElse(null));
	}

	@Override
	public Integer create(DefaultExamPeriodDTO t) {
		ExamPeriod examPeriod = examPeriodConverter.convertToJPA(t);

		examPeriod = examPeriodRepo.save(examPeriod);

		return examPeriod.getId();
	}

	@Override
	public void update(Integer id, DefaultExamPeriodDTO t) {
		if(!examPeriodRepo.existsById(id)) throw new EntityNotFoundException();

		t.setId(id);
		ExamPeriod examPeriod = examPeriodConverter.convertToJPA(t);

		examPeriodRepo.save(examPeriod);
	}

	@Override
	public boolean delete(Integer id) {
		if(!examPeriodRepo.existsById(id)) return false;

		examPeriodRepo.deleteById(id);
		return true;
	}

	@Override
	public List<DefaultExamPeriodDTO> getAllByInstitution(DefaultInstitutionDTO t) {
		Set<ExamPeriod> examPeriods = examPeriodRepo.findAllByInstitution(institutionRepo.getOne(t.getId()));
		List<ExamPeriod> examPeriodsList = new ArrayList<ExamPeriod>();

		examPeriodsList.addAll(examPeriods);

		return examPeriodConverter.convertToDTO(examPeriodsList);
	}

	@Override
	public DefaultExamPeriodDTO getByExam(DefaultExamDTO t) {
		Optional<ExamPeriod> examPeriod = examPeriodRepo.findByExamsContaining(examRepo.getOne(t.getId()));

		return examPeriodConverter.convertToDTO(examPeriod.get());
	}

	@Override
	public List<DefaultExamPeriodDTO> filterExamPeriods(DefaultExamPeriodDTO t, Pageable pageable) {
		Page<ExamPeriod> page = examPeriodRepo.filterExamPeriods(t.getInstitution().getId(), t.getName(), t.getStartDate(),
				t.getEndDate(), pageable);

		return examPeriodConverter.convertToDTO(page.getContent());
	}
}
