package com.ftn.tseo2021.sf1513282018.studentService.contract.service.course;


import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.CourseEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.CourseExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.CourseExamObligationDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.CourseTeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultCourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.InstitutionCourseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

import javax.persistence.EntityNotFoundException;

public interface CourseService extends BaseService<DefaultCourseDTO, Integer> {

	List<InstitutionCourseDTO> filterCourses(int institutionId, Pageable pageable, InstitutionCourseDTO filterOptions);
	
	List<CourseTeachingDTO> getCourseTeachings(int courseId, Pageable pageable) throws EntityNotFoundException;
	
	List<CourseEnrollmentDTO> getCourseEnrollments(int courseId, Pageable pageable) throws EntityNotFoundException;
	
	List<CourseExamObligationDTO> getCourseExamObligations(int courseId, Pageable pageable) throws EntityNotFoundException;
	
	List<CourseExamDTO> getCourseExams(int courseId, Pageable pageable) throws EntityNotFoundException;
	
}
