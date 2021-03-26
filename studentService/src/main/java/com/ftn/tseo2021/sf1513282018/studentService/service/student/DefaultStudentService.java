package com.ftn.tseo2021.sf1513282018.studentService.service.student;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.EnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.StudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.institution.InstitutionRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.StudentRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.user.UserRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.StudentService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Enrollment;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Student;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teacher;

@Service
public class DefaultStudentService implements StudentService {
	
	@Autowired
	StudentRepository studentRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private InstitutionRepository institutionRepo;
	
	@Autowired
	DtoConverter<Student, StudentDTO, DefaultStudentDTO> studentConverter;

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
		if(!userRepo.existsById(userId)) throw new EntityNotFoundException();

		Optional<Student> student = studentRepo.findByUser_Id(userId);

		return studentConverter.convertToDTO(student.orElse(null));
	}

	@Override
	public List<DefaultStudentDTO> getByInstitutionId(int institutionId, Pageable pageable) {
		if(!institutionRepo.existsById(institutionId)) throw new EntityNotFoundException();

		Page<Student> page = studentRepo.filterStudent(institutionId, null, null, null, null, null, null, null, null, pageable);

		return studentConverter.convertToDTO(page.getContent());
	}

}
