package com.ftn.tseo2021.sf1513282018.studentService.contract.service.student;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultEnrollmentDTO;

public interface EnrollmentService extends BaseService<DefaultEnrollmentDTO, Integer> {
	
	List<DefaultEnrollmentDTO> getByStudentId(int studentId, Pageable pageable);
	
	List<DefaultEnrollmentDTO> getByCourseId(int courseId, Pageable pageable);

}
