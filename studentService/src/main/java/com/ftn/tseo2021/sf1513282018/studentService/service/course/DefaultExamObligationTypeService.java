package com.ftn.tseo2021.sf1513282018.studentService.service.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamObligationTypeDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamObligationType;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamObligationTypeRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamObligationTypeService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamObligationTypeDTO;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public class DefaultExamObligationTypeService implements ExamObligationTypeService {
	
	@Autowired
	ExamObligationTypeRepository examObligationTypeRepo;

	@Autowired
	DtoConverter<ExamObligationType, ExamObligationTypeDTO, DefaultExamObligationTypeDTO> examObligationTypeConverter;

	@Override
	public DefaultExamObligationTypeDTO getOne(Integer id) {
		Optional<ExamObligationType> examObligationType = examObligationTypeRepo.findById(id);
		return examObligationTypeConverter.convertToDTO(examObligationType.orElse(null));
	}

	@Override
	public Integer create(DefaultExamObligationTypeDTO t) {
		ExamObligationType examObligationType = examObligationTypeConverter.convertToJPA(t);

		examObligationType = examObligationTypeRepo.save(examObligationType);

		return examObligationType.getId();
	}

	@Override
	public void update(Integer id, DefaultExamObligationTypeDTO t) {
		if(!examObligationTypeRepo.existsById(id)) throw new EntityNotFoundException();

		t.setId(id);
		ExamObligationType examObligationType = examObligationTypeConverter.convertToJPA(t);

		examObligationTypeRepo.save(examObligationType);
	}

	@Override
	public boolean delete(Integer id) {
		if(!examObligationTypeRepo.existsById(id)) return  false;

		examObligationTypeRepo.deleteById(id);
		return true;
	}
}
