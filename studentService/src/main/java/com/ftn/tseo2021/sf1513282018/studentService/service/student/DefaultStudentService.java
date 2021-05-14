package com.ftn.tseo2021.sf1513282018.studentService.service.student;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.DocumentService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.EnrollmentService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.FinancialCardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.StudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.StudentRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.StudentService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultFinancialCardDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.InstitutionStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.StudentDocumentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.StudentEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Student;
import com.ftn.tseo2021.sf1513282018.studentService.security.PrincipalHolder;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAdmin;

@Service
public class DefaultStudentService implements StudentService {
	
	@Autowired
	private StudentRepository studentRepo;
	
	@Autowired
	private EnrollmentService enrollmentService;

	@Autowired
	private DocumentService documentService;

	@Autowired
	private FinancialCardService financialCardService;
	
	@Autowired
	private DtoConverter<Student, StudentDTO, DefaultStudentDTO> studentConverter;
	
	@Autowired
	private PrincipalHolder principalHolder;
	
	private void authorize(Integer institutionId) throws PersonalizedAccessDeniedException {
		if (principalHolder.getCurrentPrincipal().getInstitutionId() != institutionId) 
			throw new PersonalizedAccessDeniedException();
	}

	@Override
	public DefaultStudentDTO getOne(Integer id) {
		Optional<Student> s = studentRepo.findById(id);
		return studentConverter.convertToDTO(s.orElse(null));
	}

	@Override
	public Integer create(DefaultStudentDTO dto) throws IllegalArgumentException {
		Student s = studentConverter.convertToJPA(dto);
		
		s = studentRepo.save(s);
		
		return s.getId();
	}

	@Override
	public void update(Integer id, DefaultStudentDTO dto) throws EntityNotFoundException, IllegalArgumentException {
		if (!studentRepo.existsById(id)) throw new EntityNotFoundException();
		
		Student sNew = studentConverter.convertToJPA(dto);
		
		Student s = studentRepo.findById(id).get();
		s.setFirstName(sNew.getFirstName());
		s.setLastName(sNew.getLastName());
		s.setStudentCard(sNew.getStudentCard());
		s.setAddress(sNew.getAddress());
		s.setDateOfBirth(sNew.getDateOfBirth());
		s.setGeneration(sNew.getGeneration());
		studentRepo.save(s);
		
	}

	@Override
	public void delete(Integer id) {
		if (!studentRepo.existsById(id)) {}
		studentRepo.deleteById(id);
	}

	@Override
	public DefaultStudentDTO getByUserId(int userId) {
		Optional<Student> student = studentRepo.findByUser_Id(userId);

		return studentConverter.convertToDTO(student.orElse(null));
	}

	@SuppressWarnings("unchecked")
	@AuthorizeAdmin
	@Override
	public List<InstitutionStudentDTO> filterStudents(int institutionId, Pageable pageable, DefaultStudentDTO filterOptions) 
			throws PersonalizedAccessDeniedException {
		authorize(institutionId);
			
		if (filterOptions == null) {
			Page<Student> page = studentRepo.findByInstitution_Id(institutionId, pageable);
			return (List<InstitutionStudentDTO>) studentConverter.convertToDTO(page.getContent(), InstitutionStudentDTO.class);
		}
		else {
			Page<Student> page = studentRepo.filterStudent(institutionId, filterOptions.getFirstName(), 
					filterOptions.getLastName(), filterOptions.getStudentCard(), filterOptions.getAddress(), 
					null, null, null, null, pageable);
			return (List<InstitutionStudentDTO>) studentConverter.convertToDTO(page.getContent(), InstitutionStudentDTO.class);
		}
	}

	@Override
	public List<StudentEnrollmentDTO> getStudentEnrollments(int studentId, Pageable pageable) throws EntityNotFoundException {
		if(!studentRepo.existsById(studentId)) throw new EntityNotFoundException();

		List<StudentEnrollmentDTO> enrollments = enrollmentService.filterEnrollmentsByStudent(studentId, pageable, null);

		return enrollments;
	}

	@Override
	public List<StudentDocumentDTO> getStudentDocuments(int studentId, Pageable pageable) throws EntityNotFoundException {
		if(!studentRepo.existsById(studentId)) throw new EntityNotFoundException();

		List<StudentDocumentDTO> documents = documentService.filterDocuments(studentId, pageable, null);

		return documents;
	}

	@Override
	public DefaultFinancialCardDTO getStudentFinancialCard(int studentId) throws EntityNotFoundException {
		if(!studentRepo.existsById(studentId)) throw new EntityNotFoundException();

		DefaultFinancialCardDTO financialCard = financialCardService.getByStudentId(studentId);

		return financialCard;
	}

}
