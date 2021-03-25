package com.ftn.tseo2021.sf1513282018.studentService.contract.service.institution;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Pageable;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultCourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserDTO;

public interface InstitutionService extends BaseService<DefaultInstitutionDTO, Integer> {
	
	List<DefaultUserDTO> getInstitutionUsers(int institutionId, Pageable pageable) throws EntityNotFoundException;
	
	List<DefaultTeacherDTO> getInstitutionTeachers(int institutionId, Pageable pageable) throws EntityNotFoundException;
	
	List<DefaultStudentDTO> getInstitutionStudents(int institutionId, Pageable pageable) throws EntityNotFoundException;
	
	List<DefaultCourseDTO> getInstitutionCourses(int institutionId, Pageable pageable) throws EntityNotFoundException;
	
	List<DefaultExamPeriodDTO> getInstitutionExamPeriods(int institutionId, Pageable pageable) throws EntityNotFoundException;
	
}
