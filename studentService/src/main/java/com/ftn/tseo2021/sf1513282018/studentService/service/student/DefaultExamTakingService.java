package com.ftn.tseo2021.sf1513282018.studentService.service.student;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.ExamTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.ExamTakingRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.ExamTakingService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultExamTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.ExamTaking;

public class DefaultExamTakingService implements ExamTakingService {
	
	@Autowired
	ExamTakingRepository examTakingRepo;
	
	@Autowired
	DtoConverter<ExamTaking, ExamTakingDTO, DefaultExamTakingDTO> examTakingConverter;

	@Override
	public DefaultExamTakingDTO getOne(Integer id) {
		Optional<ExamTaking> taking = examTakingRepo.findById(id);
		return examTakingConverter.convertToDTO(taking.orElse(null));
	}

	@Override
	public Integer create(DefaultExamTakingDTO dto) throws IllegalArgumentException {
		ExamTaking taking = examTakingConverter.convertToJPA(dto);
		
		taking = examTakingRepo.save(taking);
		
		return taking.getId();
	}

	@Override
	public void update(Integer id, DefaultExamTakingDTO dto) throws EntityNotFoundException, IllegalArgumentException {
		if (!examTakingRepo.existsById(id)) throw new EntityNotFoundException();
		
		ExamTaking tNew = examTakingConverter.convertToJPA(dto);
//		REAL PUT
//		tNew.setId(id);
//		examTakingRepo.save(tNew);
		
//		SIMULATE PATCH
		ExamTaking t = examTakingRepo.getOne(id);
		t.setScore(tNew.getScore());
		examTakingRepo.save(t);
		
	}

	@Override
	public boolean delete(Integer id) {
		if (!examTakingRepo.existsById(id)) return false;
		examTakingRepo.deleteById(id);
		return true;
	}

	@Override
	public List<DefaultExamTakingDTO> getByExamId(int examId, Pageable pageable) {
		Page<ExamTaking> page = examTakingRepo.filterExamTakings(examId, null, null, null, pageable);
		return examTakingConverter.convertToDTO(page.getContent());
	}

	@Override
	public List<DefaultExamTakingDTO> getByEnrollmentId(int enrollmentId, Pageable pageable) {
		Page<ExamTaking> page = examTakingRepo.filterExamTakings(null, enrollmentId, null, null, pageable);
		return examTakingConverter.convertToDTO(page.getContent());
	}

}
