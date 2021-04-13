package com.ftn.tseo2021.sf1513282018.studentService.service.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamObligationDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.ExamObligationTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.ExamObligationTakingService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultExamObligationTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamObligation;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.ExamObligationTaking;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamObligationRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamObligationService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.CourseExamObligationDTO;
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
	private ExamObligationRepository examObligationRepo;

	@Autowired
	private DtoConverter<ExamObligation, ExamObligationDTO, DefaultExamObligationDTO> examObligationConverter;

	@Autowired
	private ExamObligationTakingService examObligationTakingService;

	@Autowired
	private DtoConverter<ExamObligationTaking, ExamObligationTakingDTO, DefaultExamObligationTakingDTO> examObligationTakingConverter;
	
	@Override
	public boolean existsById(Integer id) {
		return examObligationRepo.existsById(id);
	}

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

	@SuppressWarnings("unchecked")
	@Override
	public List<CourseExamObligationDTO> filterExamObligations(int courseId, Pageable pageable, CourseExamObligationDTO filterOptions) {
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

	@Override
	public List<DefaultExamObligationTakingDTO> getExamObligationTakings(int examObligationId, Pageable pageable)
			throws EntityNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
}
