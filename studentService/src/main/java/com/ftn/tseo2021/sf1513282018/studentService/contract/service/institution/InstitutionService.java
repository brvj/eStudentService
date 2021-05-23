package com.ftn.tseo2021.sf1513282018.studentService.contract.service.institution;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.InstitutionCourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.InstitutionExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.InstitutionStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.InstitutionTeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.InstitutionUserDTO;

public interface InstitutionService extends BaseService<DefaultInstitutionDTO, Integer> {
	
	List<InstitutionUserDTO> getInstitutionUsers(int institutionId, Pageable pageable) throws EntityNotFoundException, PersonalizedAccessDeniedException;
	
	List<InstitutionTeacherDTO> getInstitutionTeachers(int institutionId, Pageable pageable) throws EntityNotFoundException, PersonalizedAccessDeniedException;
	
	List<InstitutionStudentDTO> getInstitutionStudents(int institutionId, Pageable pageable) throws EntityNotFoundException, PersonalizedAccessDeniedException;
	
	List<InstitutionCourseDTO> getInstitutionCourses(int institutionId, Pageable pageable) throws EntityNotFoundException, PersonalizedAccessDeniedException;
	
	Page<InstitutionExamPeriodDTO> getInstitutionExamPeriods(int institutionId, Pageable pageable) throws EntityNotFoundException, PersonalizedAccessDeniedException;

	Page<InstitutionUserDTO> getInstitutionAdmins(int institutionId, Pageable pageable);
	
}
