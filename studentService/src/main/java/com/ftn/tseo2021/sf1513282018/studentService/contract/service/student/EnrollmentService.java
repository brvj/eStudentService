package com.ftn.tseo2021.sf1513282018.studentService.contract.service.student;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Pageable;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.CourseEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.EnrollmentExamObligationTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.EnrollmentExamTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.StudentEnrollmentDTO;

public interface EnrollmentService extends BaseService<DefaultEnrollmentDTO, Integer> {
	
	List<StudentEnrollmentDTO> filterEnrollmentsByStudent(int studentId, Pageable pageable, StudentEnrollmentDTO filterOptions);
	
	List<CourseEnrollmentDTO> filterEnrollmentsByCourse(int courseId, Pageable pageable, CourseEnrollmentDTO filterOptions);
	
	List<EnrollmentExamObligationTakingDTO> getEnrollmentExamObligationTakings(int enrollmentId, Pageable pageable) throws EntityNotFoundException;
	
	List<EnrollmentExamTakingDTO> getEnrollmentExamTakings(int enrollmentId, Pageable pageable) throws EntityNotFoundException;

}
