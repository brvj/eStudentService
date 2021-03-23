package com.ftn.tseo2021.sf1513282018.studentService.service.student;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.EnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.EnrollmentRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.EnrollmentService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Enrollment;

public class DefaultEnrollmentService implements EnrollmentService {
	
	@Autowired
	EnrollmentRepository enrollmentRepo;
	
	@Autowired
	DtoConverter<Enrollment, EnrollmentDTO, DefaultEnrollmentDTO> enrollmentConverter;

	@Override
	public DefaultEnrollmentDTO getOne(Integer id) {
		Optional<Enrollment> e = enrollmentRepo.findById(id);
		return enrollmentConverter.convertToDTO(e.orElse(null));
	}

	@Override
	public Integer create(DefaultEnrollmentDTO dto) {
		Enrollment e = enrollmentConverter.convertToJPA(dto);
		
		e = enrollmentRepo.save(e);
		
		return e.getId();
	}

	@Override
	public void update(Integer id, DefaultEnrollmentDTO dto) {
		if (!enrollmentRepo.existsById(id)) throw new EntityNotFoundException();
		
		dto.setId(id);
		Enrollment e = enrollmentConverter.convertToJPA(dto);
		
		enrollmentRepo.save(e);
		
	}

	@Override
	public boolean delete(Integer id) {
		if (!enrollmentRepo.existsById(id)) return false;
		enrollmentRepo.deleteById(id);
		return true;
	}

	@Override
	public List<DefaultEnrollmentDTO> getByStudentId(int studentId, Pageable pageable) {
		Page<Enrollment> page = enrollmentRepo.filterEnrollments(studentId, null, null, null, null, null, null, null, null, pageable);
		return enrollmentConverter.convertToDTO(page.getContent());
	}

	@Override
	public List<DefaultEnrollmentDTO> getByCourseId(int courseId, Pageable pageable) {
		Page<Enrollment> page = enrollmentRepo.filterEnrollments(null, courseId, null, null, null, null, null, null, null, pageable);
		return enrollmentConverter.convertToDTO(page.getContent());
	}

}
