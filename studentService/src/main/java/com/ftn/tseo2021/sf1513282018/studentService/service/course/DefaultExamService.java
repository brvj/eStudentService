package com.ftn.tseo2021.sf1513282018.studentService.service.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.ExamTakingRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultExamTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Exam;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultExamService implements ExamService {
	
	@Autowired
	ExamRepository examRepo;

	@Autowired
	DtoConverter<Exam, ExamDTO, DefaultExamDTO> examConverter;

	@Autowired
	ExamTakingRepository examTakingRepo;

	@Override
	public DefaultExamDTO getOne(Integer id) {
		Optional<Exam> exam = examRepo.findById(id);
		return examConverter.convertToDTO(exam.orElse(null));
	}

	@Override
	public Integer create(DefaultExamDTO t) {
		Exam exam = examConverter.convertToJPA(t);

		exam = examRepo.save(exam);

		return exam.getId();
	}

	@Override
	public void update(Integer id, DefaultExamDTO t) {
		if(!examRepo.existsById(id)) throw new EntityNotFoundException();

		t.setId(id);
		Exam exam = examConverter.convertToJPA(t);

		examRepo.save(exam);
	}

	@Override
	public boolean delete(Integer id) {
		if(!examRepo.existsById(id)) return false;

		examRepo.deleteById(id);
		return true;
	}

	@Override
	public DefaultExamDTO getByExamTaking(DefaultExamTakingDTO t) {
		Optional<Exam> exam = examRepo.findByExamTakingsContaining(examTakingRepo.getOne(t.getId()));

		return examConverter.convertToDTO(exam.get());
	}

	@Override // treba prepraviti za filtriranje datuma
	public List<DefaultExamDTO> filterExams(DefaultExamDTO filterOptions, Pageable pageable) {
		Page<Exam> page = examRepo.filterExams(filterOptions.getCourse().getId(), filterOptions.getId(), filterOptions.getDescription(),
				filterOptions.getClassroom(), filterOptions.getDateTime(), filterOptions.getDateTime(), pageable);

		return examConverter.convertToDTO(page.getContent());
	}
}
