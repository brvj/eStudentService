package com.ftn.tseo2021.sf1513282018.studentService.service.student;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.institution.InstitutionService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.DocumentService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.EnrollmentService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.FinancialCardService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.StudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.StudentRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.StudentService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultDocumentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultFinancialCardDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Student;

@Service
public class DefaultStudentService implements StudentService {
	
	@Autowired
	private StudentRepository studentRepo;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private InstitutionService institutionService;

	@Autowired
	private EnrollmentService enrollmentService;

	@Autowired
	private DocumentService documentService;

	@Autowired
	private FinancialCardService financialCardService;
	
	@Autowired
	private DtoConverter<Student, StudentDTO, DefaultStudentDTO> studentConverter;

	@Override
	public DefaultStudentDTO getOne(Integer id) {
		Optional<Student> s = studentRepo.findById(id);
		return studentConverter.convertToDTO(s.orElse(null));
	}

	@Override
	public Integer create(DefaultStudentDTO t) {
		Student s = studentConverter.convertToJPA(t);
		
		s = studentRepo.save(s);
		
		return s.getId();
	}

	@Override
	public void update(Integer id, DefaultStudentDTO t) {
		if (!studentRepo.existsById(id)) throw new EntityNotFoundException();
		
		t.setId(id);
		Student s = studentConverter.convertToJPA(t);
		
		studentRepo.save(s);
		
	}

	@Override
	public boolean delete(Integer id) {
		if (!studentRepo.existsById(id)) return false;
		studentRepo.deleteById(id);
		return true;
	}

	@Override
	public DefaultStudentDTO getByUserId(int userId) {
		if(userService.getOne(userId) == null) throw new EntityNotFoundException();

		Optional<Student> student = studentRepo.findByUser_Id(userId);

		return studentConverter.convertToDTO(student.orElse(null));
	}

	@Override
	public List<DefaultStudentDTO> getByInstitutionId(int institutionId, Pageable pageable) {
		if(institutionService.getOne(institutionId) == null) throw new EntityNotFoundException();

		Page<Student> page = studentRepo.filterStudent(institutionId, null, null, null, null, null, null, null, null, pageable);

		return studentConverter.convertToDTO(page.getContent());
	}

	@Override
	public List<DefaultEnrollmentDTO> getStudentEnrollments(int studentId, Pageable pageable) throws EntityNotFoundException {
		if(!studentRepo.existsById(studentId)) throw new EntityNotFoundException();

		List<DefaultEnrollmentDTO> enrollments = enrollmentService.getByStudentId(studentId, pageable);

		return enrollments;
	}

	@Override
	public List<DefaultDocumentDTO> getStudentDocuments(int studentId, Pageable pageable) throws EntityNotFoundException {
		if(!studentRepo.existsById(studentId)) throw new EntityNotFoundException();

		List<DefaultDocumentDTO> documents = documentService.getByStudentId(studentId, pageable);

		return documents;
	}

	@Override
	public DefaultFinancialCardDTO getStudentFinancialCard(int studentId) throws EntityNotFoundException {
		if(!studentRepo.existsById(studentId)) throw new EntityNotFoundException();

		DefaultFinancialCardDTO financialCard = financialCardService.getByStudentId(studentId);

		return financialCard;
	}

}
