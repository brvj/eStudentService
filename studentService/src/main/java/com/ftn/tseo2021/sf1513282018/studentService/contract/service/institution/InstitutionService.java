package com.ftn.tseo2021.sf1513282018.studentService.contract.service.institution;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.course.CourseFilterOptions;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.course.ExamPeriodFilterOptions;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.StudentFilterOptions;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherFilterOptions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.user.UserFilterOptions;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.InstitutionCourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.InstitutionExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.InstitutionStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.InstitutionTeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.InstitutionUserDTO;

public interface InstitutionService extends BaseService<DefaultInstitutionDTO, Integer> {
	
	Page<InstitutionUserDTO> getInstitutionUsers(int institutionId, Pageable pageable);
	
	Page<InstitutionTeacherDTO> getInstitutionTeachers(int institutionId, Pageable pageable, TeacherFilterOptions filterOptions);
	
	Page<InstitutionStudentDTO> getInstitutionStudents(int institutionId, Pageable pageable, StudentFilterOptions filterOptions);
	
	Page<InstitutionCourseDTO> getInstitutionCourses(int institutionId, Pageable pageable, CourseFilterOptions filterOptions);
	
	Page<InstitutionExamPeriodDTO> getInstitutionExamPeriods(int institutionId, Pageable pageable, ExamPeriodFilterOptions filterOptions);

	Page<InstitutionUserDTO> getInstitutionAdmins(int institutionId, Pageable pageable, UserFilterOptions filterOptions);

}
