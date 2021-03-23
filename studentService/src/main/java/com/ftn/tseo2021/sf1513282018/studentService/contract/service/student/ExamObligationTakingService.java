package com.ftn.tseo2021.sf1513282018.studentService.contract.service.student;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultExamObligationTakingDTO;

public interface ExamObligationTakingService extends BaseService<DefaultExamObligationTakingDTO, Integer> {

	List<DefaultExamObligationTakingDTO> getByExamObligationId(int examObligationId, Pageable pageable);
	
	List<DefaultExamObligationTakingDTO> getByEnrollmentId(int enrollmentId, Pageable pageable);

}
