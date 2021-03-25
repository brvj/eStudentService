package com.ftn.tseo2021.sf1513282018.studentService.service.student;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.ExamObligationTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.ExamObligationTakingRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.ExamObligationTakingService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultExamObligationTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.ExamObligationTaking;

public class DefaultExamObligationTakingService implements ExamObligationTakingService {
	
	@Autowired
	ExamObligationTakingRepository examObligationTakingRepo;
	
	@Autowired
	DtoConverter<ExamObligationTaking, ExamObligationTakingDTO, DefaultExamObligationTakingDTO> examObligationTakingConverter;

	@Override
	public DefaultExamObligationTakingDTO getOne(Integer id) {
		Optional<ExamObligationTaking> taking = examObligationTakingRepo.findById(id);
		return examObligationTakingConverter.convertToDTO(taking.orElse(null));
	}

	@Override
	public Integer create(DefaultExamObligationTakingDTO dto) throws IllegalArgumentException {
		ExamObligationTaking taking = examObligationTakingConverter.convertToJPA(dto);
		
		taking = examObligationTakingRepo.save(taking);
		
		return taking.getId();
	}

	@Override
	public void update(Integer id, DefaultExamObligationTakingDTO dto) throws EntityNotFoundException, IllegalArgumentException {
		if (!examObligationTakingRepo.existsById(id)) throw new EntityNotFoundException();
		
		ExamObligationTaking tNew = examObligationTakingConverter.convertToJPA(dto);

//		REAL PUT
//		tNew.setId(id);
//		examObligationTakingRepo.save(tNew);
		
//		SIMULATE PATCH
		ExamObligationTaking t = examObligationTakingRepo.getOne(id);
		t.setScore(tNew.getScore());
		examObligationTakingRepo.save(t);
		
	}

	@Override
	public boolean delete(Integer id) {
		if (!examObligationTakingRepo.existsById(id)) return false;
		examObligationTakingRepo.deleteById(id);
		return true;
	}

	@Override
	public List<DefaultExamObligationTakingDTO> getByExamObligationId(int examObligationId, Pageable pageable) {
		Page<ExamObligationTaking> page = examObligationTakingRepo.filterExamObligationTakings(examObligationId, null, null, null, pageable);
		return examObligationTakingConverter.convertToDTO(page.getContent());
	}

	@Override
	public List<DefaultExamObligationTakingDTO> getByEnrollmentId(int enrollmentId, Pageable pageable) {
		Page<ExamObligationTaking> page = examObligationTakingRepo.filterExamObligationTakings(null, enrollmentId, null, null, pageable);
		return examObligationTakingConverter.convertToDTO(page.getContent());
	}

}
