package com.ftn.tseo2021.sf1513282018.studentService.contract.service.course;


import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultCourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamObligationDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeachingDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService extends BaseService<DefaultCourseDTO, Integer> {
	DefaultCourseDTO getByTeaching(DefaultTeachingDTO t);

	DefaultCourseDTO getByEnrollment(DefaultEnrollmentDTO t);

	DefaultCourseDTO getByExamObligation(DefaultExamObligationDTO t);

	DefaultCourseDTO getByExam(DefaultExamDTO t);

	List<DefaultCourseDTO> filterCourses(DefaultCourseDTO filterOptions, Pageable pageable);
}
