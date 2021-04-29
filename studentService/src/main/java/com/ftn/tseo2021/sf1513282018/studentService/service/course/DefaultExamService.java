package com.ftn.tseo2021.sf1513282018.studentService.service.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.ExamTakingService;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Exam;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.CourseExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.ExamExamTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.ExamPeriodExamDTO;

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

	@Override
	public DefaultExamDTO getOne(Integer id) {
		Optional<Exam> exam = examRepo.findById(id);
		return examConverter.convertToDTO(exam.orElse(null));
	}

	@Override
	public Integer create(DefaultExamDTO dto) throws IllegalArgumentException {
		Exam exam = examConverter.convertToJPA(dto);

		exam = examRepo.save(exam);

		return exam.getId();
	}

	@Override
	public void update(Integer id, DefaultExamDTO dto) throws EntityNotFoundException, IllegalArgumentException {
		if(!examRepo.existsById(id)) throw new EntityNotFoundException();

		Exam eNew = examConverter.convertToJPA(dto);

		Exam e = examRepo.findById(id).get();
		e.setDateTime(eNew.getDateTime());
		e.setClassroom(eNew.getClassroom());
		e.setPoints(eNew.getPoints());
		e.setDescription(eNew.getDescription());
		examRepo.save(e);
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
					filterOptions.getClassroom(), null, null, pageable);
			return (List<CourseExamDTO>) examConverter.convertToDTO(page.getContent(), CourseExamDTO.class);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExamPeriodExamDTO> filterExamsByExamPeriod(int examPeriodId, Pageable pageable, ExamPeriodExamDTO filterOptions) {
		if (filterOptions == null) {
			Page<Exam> page = examRepo.findByExamPeriod_Id(examPeriodId, pageable);
			return (List<ExamPeriodExamDTO>) examConverter.convertToDTO(page.getContent(), ExamPeriodExamDTO.class);
		}
		else {
			Page<Exam> page = examRepo.filterExamsByExamPeriod(examPeriodId, filterOptions.getDescription(), 
					filterOptions.getClassroom(), null, null, pageable);
			return (List<ExamPeriodExamDTO>) examConverter.convertToDTO(page.getContent(), ExamPeriodExamDTO.class);
		}
	}

	@Override
	public List<ExamExamTakingDTO> getExamExamTakings(int examId, Pageable pageable) throws EntityNotFoundException {
		if(!examRepo.existsById(examId)) throw new EntityNotFoundException();
		
		return examTakingService.filterTakingsByExam(examId, pageable, null);
	}
}
