package com.ftn.tseo2021.sf1513282018.studentService.contract.service.student;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Pageable;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultFinancialCardDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.InstitutionStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.StudentDocumentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.StudentEnrollmentDTO;

public interface StudentService extends BaseService<DefaultStudentDTO, Integer> {
	
	DefaultStudentDTO getByUserId(int userId);
	
	List<InstitutionStudentDTO> filterStudents(int institutionId, Pageable pageable, DefaultStudentDTO filterOptions);
	
	List<StudentEnrollmentDTO> getStudentEnrollments(int studentId, Pageable pageable) throws EntityNotFoundException;
	
	List<StudentDocumentDTO> getStudentDocuments(int studentId, Pageable pageable) throws EntityNotFoundException;
	
	DefaultFinancialCardDTO getStudentFinancialCard(int studentId) throws EntityNotFoundException;
}
