package com.ftn.tseo2021.sf1513282018.studentService.service.student;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.EntityValidationException;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.ResourceNotFoundException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultFinancialCardDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.InstitutionStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.StudentDocumentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.StudentEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Student;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.User;
import com.ftn.tseo2021.sf1513282018.studentService.security.CustomPrincipal;
import com.ftn.tseo2021.sf1513282018.studentService.security.PersonalizedAuthorizator;
import com.ftn.tseo2021.sf1513282018.studentService.security.PrincipalHolder;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAdmin;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeStudentOrAdmin;

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
	
	@Autowired
	private PersonalizedAuthorizator authorizator;
	
	private CustomPrincipal getPrincipal() { return authorizator.getPrincipal(); }
	
	private void authorize(Integer institutionId) throws PersonalizedAccessDeniedException {
		if (principalHolder.getCurrentPrincipal().getInstitutionId() != institutionId) 
			throw new PersonalizedAccessDeniedException();
	}

	@AuthorizeStudentOrAdmin
	@Override
	public DefaultStudentDTO getOne(Integer id) {
		if (!getPrincipal().isAdmin() && 
				(getPrincipal().isAdmin() || getPrincipal().isStudent()))
			authorizator.assertPrincipalIdIs(id, PersonalizedAccessDeniedException.class);
		
		Student student = studentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		
		if (getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(student.getInstitution().getId(), PersonalizedAccessDeniedException.class);
		
		return studentConverter.convertToDTO(student);
	}

	@AuthorizeAdmin
	@Override
	public Integer create(DefaultStudentDTO dto) {
		authorizator.assertPrincipalIsFromInstitution(dto.getInstitution().getId(), EntityValidationException.class);
		
		Student student = studentConverter.convertToJPA(dto);
		
		student = studentRepo.save(student);

		return student.getId();
	}

	@AuthorizeStudentOrAdmin
	@Override
	public void update(Integer id, DefaultStudentDTO dto) throws EntityNotFoundException {
		
		if (!getPrincipal().isAdmin() && 
				(getPrincipal().isAdmin() || getPrincipal().isStudent()))
			authorizator.assertPrincipalIdIs(id, PersonalizedAccessDeniedException.class);
		
		Student s = studentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		
		if (getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(s.getInstitution().getId(), PersonalizedAccessDeniedException.class);
		
		if (dto.getInstitution().getId() != s.getInstitution().getId()) throw new EntityValidationException();

		Student sNew = studentConverter.convertToJPA(dto);
		
		s.setFirstName(sNew.getFirstName());
		s.setLastName(sNew.getLastName());
		s.setStudentCard(sNew.getStudentCard());
		s.setAddress(sNew.getAddress());
		s.setDateOfBirth(sNew.getDateOfBirth());
		s.setGeneration(sNew.getGeneration());
		studentRepo.save(s);		
	}

	@AuthorizeAdmin
	@Override
	public void delete(Integer id) {
		Student student = studentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		
		authorizator.assertPrincipalIsFromInstitution(student.getInstitution().getId(), PersonalizedAccessDeniedException.class);
		
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
	public Page<InstitutionStudentDTO> filterStudents(int institutionId, Pageable pageable, DefaultStudentDTO filterOptions)  {
		authorizator.assertPrincipalIsFromInstitution(institutionId, PersonalizedAccessDeniedException.class);

		Page<Student> page;

		if (filterOptions == null) {
			page = studentRepo.findByInstitution_Id(institutionId, pageable);
		}
		else {
			page = studentRepo.filterStudent(institutionId, filterOptions.getFirstName(),
					filterOptions.getLastName(), filterOptions.getStudentCard(), filterOptions.getAddress(),
					null, null, null, null, pageable);
		}
		return page.map(new Function<Student, InstitutionStudentDTO>() {
			@Override
			public InstitutionStudentDTO apply(Student student) {
				return studentConverter.convertToDTO(student, InstitutionStudentDTO.class);
			}
		});
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
