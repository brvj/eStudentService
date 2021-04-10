package com.ftn.tseo2021.sf1513282018.studentService.service.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.institution.InstitutionRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Exam;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamPeriod;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamPeriodRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamPeriodService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.InstitutionExamPeriodDTO;

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
	private ExamPeriodRepository examPeriodRepo;

	@Autowired
	private DtoConverter<ExamPeriod, ExamPeriodDTO, DefaultExamPeriodDTO> examPeriodConverter;

	@Autowired
	private InstitutionRepository institutionRepo;

	@Autowired
	private ExamService examService;

	@Autowired
	private DtoConverter<Exam, ExamDTO, DefaultExamDTO> examConverter;
	
	@Override
	public boolean existsById(Integer id) {
		return examPeriodRepo.existsById(id);
	}

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

	@SuppressWarnings("unchecked")
	@Override
	public List<InstitutionExamPeriodDTO> getByInstitutionId(int institutionId, Pageable pageable) {
		Page<ExamPeriod> examPeriods = examPeriodRepo.findAllByInstitution(institutionRepo.getOne(institutionId), pageable);

		return (List<InstitutionExamPeriodDTO>) examPeriodConverter.convertToDTO(examPeriods.getContent(), InstitutionExamPeriodDTO.class);
	}

	@Override
	public DefaultExamPeriodDTO getByExam(DefaultExamDTO t) {
		Optional<ExamPeriod> examPeriod = examPeriodRepo.findByExamsContaining(examConverter.convertToJPA
				(examService.getOne(t.getId())));

		return examPeriodConverter.convertToDTO(examPeriod.get());
	}

	@Override
	public List<DefaultExamPeriodDTO> filterExamPeriods(DefaultExamPeriodDTO t, Pageable pageable) {
		Page<ExamPeriod> page = examPeriodRepo.filterExamPeriods(t.getInstitution().getId(), t.getName(), t.getStartDate(),
				t.getEndDate(), pageable);

		return examPeriodConverter.convertToDTO(page.getContent());
	}

	@Override
	public List<DefaultExamDTO> getExamPeriodExams(int examPeriodId, Pageable pageable) throws EntityNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
}
