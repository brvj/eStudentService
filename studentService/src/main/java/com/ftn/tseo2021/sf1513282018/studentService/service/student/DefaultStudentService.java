package com.ftn.tseo2021.sf1513282018.studentService.service.student;

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
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.StudentFilterOptions;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.StudentRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.StudentService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.user.NewUserService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.EntityValidationException;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.ResourceNotFoundException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultFinancialCardDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.InstitutionStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.StudentDocumentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.StudentEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Student;
import com.ftn.tseo2021.sf1513282018.studentService.security.CustomPrincipal;
import com.ftn.tseo2021.sf1513282018.studentService.security.PersonalizedAuthorizator;
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
	private NewUserService userService;
	
	@Autowired
	private DtoConverter<Student, StudentDTO, DefaultStudentDTO> studentConverter;
	
	@Autowired
	private PersonalizedAuthorizator authorizator;
	
	private CustomPrincipal getPrincipal() { return authorizator.getPrincipal(); }

	@AuthorizeStudentOrAdmin
	@Override
	public DefaultStudentDTO getOne(Integer id) {
		if (!getPrincipal().isAdmin() && 
				getPrincipal().isStudent())
			authorizator.assertPrincipalStudentIdIs(id, PersonalizedAccessDeniedException.class);
		
		Student student = studentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		
		if (getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(student.getInstitution().getId(), PersonalizedAccessDeniedException.class);
		
		return studentConverter.convertToDTO(student);
	}

	@AuthorizeAdmin
	@Override
	public Integer create(DefaultStudentDTO dto) {
		if (dto.getInstitution() != null && dto.getInstitution().getId() != null)
			authorizator.assertPrincipalIsFromInstitution(dto.getInstitution().getId(), EntityValidationException.class);
		else
			dto.setInstitution(new DefaultInstitutionDTO(getPrincipal().getInstitutionId(), null, null, null));
		
		if (studentRepo.existsByStudentCard(dto.getStudentCard()))
			throw new EntityValidationException("Student Card already taken");
		
		if (userService.existsByUsername(dto.getUser().getUsername()))
			throw new EntityValidationException("Username already taken");
		
		Student student = studentConverter.convertToJPA(dto);
		
		student = studentRepo.save(student);

		return student.getId();
	}

	@AuthorizeStudentOrAdmin
	@Override
	public void update(Integer id, DefaultStudentDTO dto) throws EntityNotFoundException {
		if (!getPrincipal().isAdmin() && 
				getPrincipal().isStudent())
			authorizator.assertPrincipalStudentIdIs(id, PersonalizedAccessDeniedException.class);
		
		Student s = studentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		
		if (getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(s.getInstitution().getId(), PersonalizedAccessDeniedException.class);
		
		if (dto.getInstitution() != null && dto.getInstitution().getId() != s.getInstitution().getId()) 
			throw new EntityValidationException("Cannot change institution");
		else if (dto.getInstitution() == null)
			dto.setInstitution(new DefaultInstitutionDTO(s.getInstitution().getId(), null, null, null));

		if (!s.getStudentCard().equals(dto.getStudentCard()))
			throw new EntityValidationException("Cannot change Student Card");
		
		if (s.getGeneration() != dto.getGeneration())
			throw new EntityValidationException("Cannot change generation");
		
		if (!s.getUser().getUsername().equals(dto.getUser().getUsername()) && 
				userService.existsByUsername(dto.getUser().getUsername()))
			throw new EntityValidationException("Username already taken");
		
		Student sNew = studentConverter.convertToJPA(dto);
		
		s.setFirstName(sNew.getFirstName());
		s.setLastName(sNew.getLastName());
//		s.setStudentCard(sNew.getStudentCard());
		s.setAddress(sNew.getAddress());
		s.setDateOfBirth(sNew.getDateOfBirth());
//		s.setGeneration(sNew.getGeneration());
		s.getUser().setFirstName(sNew.getFirstName());
		s.getUser().setLastName(sNew.getLastName());
		s.getUser().setUsername(sNew.getUser().getUsername());
		s.getUser().setEmail(sNew.getUser().getEmail());
		s.getUser().setPhoneNumber(sNew.getUser().getPhoneNumber());
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

		return studentConverter.convertToDTO(student.orElseThrow(() -> new ResourceNotFoundException()));
	}

	@AuthorizeAdmin
	@Override
	public Page<InstitutionStudentDTO> filterStudents(int institutionId, Pageable pageable, StudentFilterOptions filterOptions)  {
		authorizator.assertPrincipalIsFromInstitution(institutionId, PersonalizedAccessDeniedException.class);

		Page<Student> page;

		if (filterOptions == null) {
			page = studentRepo.findByInstitution_Id(institutionId, pageable);
		}
		else {
			page = studentRepo.filterStudent(institutionId, filterOptions.firstName, filterOptions.lastName, filterOptions.studentCard, 
					filterOptions.address, filterOptions.dateOfBirthFrom, filterOptions.dateOfBirthTo, filterOptions.generationFrom, 
					filterOptions.generationTo, filterOptions.username, filterOptions.email, filterOptions.phoneNumber, pageable);
		}
		return page.map(new Function<Student, InstitutionStudentDTO>() {
			@Override
			public InstitutionStudentDTO apply(Student student) {
				return studentConverter.convertToDTO(student, InstitutionStudentDTO.class);
			}
		});
	}

	@Override
	public Page<StudentEnrollmentDTO> getStudentEnrollments(int studentId, Pageable pageable) {
		if(!studentRepo.existsById(studentId)) throw new ResourceNotFoundException();

		Page<StudentEnrollmentDTO> enrollments = enrollmentService.filterEnrollmentsByStudent(studentId, pageable, null);

		return enrollments;
	}

	@Override
	public Page<StudentDocumentDTO> getStudentDocuments(int studentId, Pageable pageable) {
		if(!studentRepo.existsById(studentId)) throw new ResourceNotFoundException();

		Page<StudentDocumentDTO> documents = documentService.filterDocuments(studentId, pageable, null);

		return documents;
	}

	@Override
	public DefaultFinancialCardDTO getStudentFinancialCard(int studentId) {
		if(!studentRepo.existsById(studentId)) throw new ResourceNotFoundException();

		DefaultFinancialCardDTO financialCard = financialCardService.getByStudentId(studentId);

		return financialCard;
	}

}
