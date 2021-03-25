package com.ftn.tseo2021.sf1513282018.studentService.service.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamObligationDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.ExamObligationTakingRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultExamObligationTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamObligation;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamObligationRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamObligationService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamObligationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultExamObligationService implements ExamObligationService {
	
	@Autowired
	ExamObligationRepository examObligationRepo;

	@Autowired
	DtoConverter<ExamObligation, ExamObligationDTO, DefaultExamObligationDTO> examObligationConverter;

	@Autowired
	ExamObligationTakingRepository examObligationTakingRepo;

	@Override
	public DefaultExamObligationDTO getOne(Integer id) {
		Optional<ExamObligation> examObligation = examObligationRepo.findById(id);
		return examObligationConverter.convertToDTO(examObligation.orElse(null));
	}

	@Override
	public Integer create(DefaultExamObligationDTO t) {
		ExamObligation examObligation = examObligationConverter.convertToJPA(t);

		examObligation = examObligationRepo.save(examObligation);

		return examObligation.getId();
	}

	@Override
	public void update(Integer id, DefaultExamObligationDTO t) {
		if(!examObligationRepo.existsById(id)) throw new EntityNotFoundException();

		t.setId(id);
		ExamObligation examObligation = examObligationConverter.convertToJPA(t);

		examObligationRepo.save(examObligation);
	}

	@Override
	public boolean delete(Integer id) {
		if(!examObligationRepo.existsById(id)) return false;

		examObligationRepo.deleteById(id);
		return true;
	}

	@Override
	public DefaultExamObligationDTO getByExamObligationTaking(DefaultExamObligationTakingDTO t) {
		Optional<ExamObligation> examObligation = examObligationRepo.findByExamObligationTaking(
				examObligationTakingRepo.getOne(t.getId()));

		return examObligationConverter.convertToDTO(examObligation.get());
	}

	@Override
	public List<DefaultExamObligationDTO> filterExamObligations(DefaultExamObligationDTO filterOptions, Pageable pageable) {
		Page<ExamObligation> page = examObligationRepo.filterExamObligations(filterOptions.getCourse().getId(),
				filterOptions.getDescription(), filterOptions.getExamObligationType().getId(), pageable);

		return examObligationConverter.convertToDTO(page.getContent());
	}
}
