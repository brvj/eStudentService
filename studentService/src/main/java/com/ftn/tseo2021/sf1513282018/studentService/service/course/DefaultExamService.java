package com.ftn.tseo2021.sf1513282018.studentService.service.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.ExamTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.ExamTakingService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultExamTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Exam;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.ExamTaking;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.CourseExamDTO;
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
	private ExamRepository examRepo;

	@Autowired
	private DtoConverter<Exam, ExamDTO, DefaultExamDTO> examConverter;

	@Autowired
	private ExamTakingService examTakingService;

	@Autowired
	private DtoConverter<ExamTaking, ExamTakingDTO, DefaultExamTakingDTO> examTakingConverter;
	
	@Override
	public boolean existsById(Integer id) {
		return examRepo.existsById(id);
	}

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


	@SuppressWarnings("unchecked")
	@Override
	public List<CourseExamDTO> filterExamsByCourse(int courseId, Pageable pageable, CourseExamDTO filterOptions) {
		if (filterOptions == null) {
			Page<Exam> page = examRepo.findByCourse_Id(courseId, pageable);
			return (List<CourseExamDTO>) examConverter.convertToDTO(page.getContent(), CourseExamDTO.class);
		}
		else {
			Page<Exam> page = examRepo.filterExamsByCourse(courseId, filterOptions.getDescription(),
					filterOptions.getClassroom(), filterOptions.getDateTime(), filterOptions.getDateTime(), pageable);
			return (List<CourseExamDTO>) examConverter.convertToDTO(page.getContent(), CourseExamDTO.class);
		}

	}

	@Override
	public List<DefaultExamDTO> getByExamPeriodId(int examPeriodId, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DefaultExamTakingDTO> getExamExamTakings(int examId, Pageable pageable) throws EntityNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
}
