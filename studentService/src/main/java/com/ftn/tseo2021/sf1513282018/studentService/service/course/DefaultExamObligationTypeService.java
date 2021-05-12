package com.ftn.tseo2021.sf1513282018.studentService.service.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamObligationTypeDTO;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamObligationType;
import com.ftn.tseo2021.sf1513282018.studentService.security.PrincipalHolder;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAdmin;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAny;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeTeacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamObligationTypeRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamObligationTypeService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamObligationTypeDTO;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class DefaultExamObligationTypeService implements ExamObligationTypeService {
	
	@Autowired
	private ExamObligationTypeRepository examObligationTypeRepo;

	@Autowired
	private DtoConverter<ExamObligationType, ExamObligationTypeDTO, DefaultExamObligationTypeDTO> examObligationTypeConverter;

	@AuthorizeAny
	@Override
	public DefaultExamObligationTypeDTO getOne(Integer id) {
		Optional<ExamObligationType> examObligationType = examObligationTypeRepo.findById(id);
		return examObligationTypeConverter.convertToDTO(examObligationType.orElse(null));
	}

	@AuthorizeAdmin
	@AuthorizeTeacher
	@Override
	public Integer create(DefaultExamObligationTypeDTO dto) throws IllegalArgumentException {
		ExamObligationType examObligationType = examObligationTypeConverter.convertToJPA(dto);

		examObligationType = examObligationTypeRepo.save(examObligationType);

		return examObligationType.getId();
	}

	@AuthorizeAdmin
	@AuthorizeTeacher
	@Override
	public void update(Integer id, DefaultExamObligationTypeDTO dto) throws EntityNotFoundException, IllegalArgumentException {
		if(!examObligationTypeRepo.existsById(id)) throw new EntityNotFoundException();

		ExamObligationType eotNew = examObligationTypeConverter.convertToJPA(dto);
		
		ExamObligationType eot = examObligationTypeRepo.findById(id).get();
		eot.setName(eotNew.getName());
		examObligationTypeRepo.save(eot);
	}

	@AuthorizeAdmin
	@AuthorizeTeacher
	@Override
	public boolean delete(Integer id) {
		if(!examObligationTypeRepo.existsById(id)) return  false;

		examObligationTypeRepo.deleteById(id);
		return true;
	}
}
