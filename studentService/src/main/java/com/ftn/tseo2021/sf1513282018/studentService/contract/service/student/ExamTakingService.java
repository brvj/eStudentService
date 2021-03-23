package com.ftn.tseo2021.sf1513282018.studentService.contract.service.student;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultExamTakingDTO;

public interface ExamTakingService extends BaseService<DefaultExamTakingDTO, Integer> {
	
	List<DefaultExamTakingDTO> getByExamId(int examId, Pageable pageable);
	
	List<DefaultExamTakingDTO> getByEnrollmentId(int enrollmentId, Pageable pageable);

}
