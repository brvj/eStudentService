package com.ftn.tseo2021.sf1513282018.studentService.service.student;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.ExamObligationTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.ExamObligationTakingRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.ExamObligationTakingService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.ExamOblExamObligationTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultExamObligationTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.EnrollmentExamObligationTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.ExamObligationTaking;

@Service
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

		ExamObligationTaking t = examObligationTakingRepo.getOne(id);
		t.setScore(tNew.getScore());
		examObligationTakingRepo.save(t);
		
	}

	@Override
	public void delete(Integer id) {
		if (!examObligationTakingRepo.existsById(id)) {}
		examObligationTakingRepo.deleteById(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExamOblExamObligationTakingDTO> filterTakingsByExamObligation(int examObligationId, Pageable pageable, ExamOblExamObligationTakingDTO filterOptions) {
		if (filterOptions == null) {
			Page<ExamObligationTaking> page = examObligationTakingRepo.findByExamObligation_Id(examObligationId, pageable);
			return (List<ExamOblExamObligationTakingDTO>) examObligationTakingConverter.convertToDTO(page.getContent(), ExamOblExamObligationTakingDTO.class);
		}
		else {
			Page<ExamObligationTaking> page = examObligationTakingRepo.filterTakingsByExamObligation(examObligationId, null, null, pageable);
			return (List<ExamOblExamObligationTakingDTO>) examObligationTakingConverter.convertToDTO(page.getContent(), ExamOblExamObligationTakingDTO.class);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EnrollmentExamObligationTakingDTO> filterTakingsByEnrollment(int enrollmentId, Pageable pageable, EnrollmentExamObligationTakingDTO filterOptions) {
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
