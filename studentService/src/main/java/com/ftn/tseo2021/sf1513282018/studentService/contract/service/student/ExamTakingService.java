package com.ftn.tseo2021.sf1513282018.studentService.contract.service.student;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.ExamExamTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultExamTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.EnrollmentExamTakingDTO;

public interface ExamTakingService extends BaseService<DefaultExamTakingDTO, Integer> {
	
	List<ExamExamTakingDTO> filterTakingsByExam(int examId, Pageable pageable, ExamExamTakingDTO filterOptions);
	
	List<EnrollmentExamTakingDTO> filterTakingsByEnrollment(int enrollmentId, Pageable pageable, EnrollmentExamTakingDTO filterOptions);

}
