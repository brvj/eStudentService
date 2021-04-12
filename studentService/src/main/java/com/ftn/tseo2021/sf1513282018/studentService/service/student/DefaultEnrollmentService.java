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
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.EnrollmentRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.EnrollmentService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.ExamObligationTakingService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.ExamTakingService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultExamObligationTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultExamTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.StudentEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Enrollment;

@Service
public class DefaultEnrollmentService implements EnrollmentService {
	
	@Autowired
	EnrollmentRepository enrollmentRepo;
	
	@Autowired
	DtoConverter<Enrollment, EnrollmentDTO, DefaultEnrollmentDTO> enrollmentConverter;
	
	@Autowired
	ExamObligationTakingService examObligationTakingService;
	
	@Autowired
	ExamTakingService examTakingService;
	
	@Override
	public boolean existsById(Integer id) {
		return enrollmentRepo.existsById(id);
	}

	@Override
	public DefaultEnrollmentDTO getOne(Integer id) {
		Optional<Enrollment> e = enrollmentRepo.findById(id);
		return enrollmentConverter.convertToDTO(e.orElse(null));
	}

	@Override
	public Integer create(DefaultEnrollmentDTO dto) throws IllegalArgumentException {
		Enrollment e = enrollmentConverter.convertToJPA(dto);
		
		e = enrollmentRepo.save(e);
		
		return e.getId();
	}

	@Override
	public void update(Integer id, DefaultEnrollmentDTO dto) throws EntityNotFoundException, IllegalArgumentException {
		if (!enrollmentRepo.existsById(id)) throw new EntityNotFoundException();

		Enrollment eNew = enrollmentConverter.convertToJPA(dto);
		
//		REAL PUT
//		eNew.setId(id);
//		enrollmentRepo.save(eNew);
		
//		SIMULATE PATCH
		Enrollment e = enrollmentRepo.getOne(id);
		e.setStartDate(eNew.getStartDate());
		enrollmentRepo.save(e);
	}

	@Override
	public boolean delete(Integer id) {
		if (!enrollmentRepo.existsById(id)) return false;
		enrollmentRepo.deleteById(id);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudentEnrollmentDTO> filterEnrollmentsByStudent(int studentId, Pageable pageable, StudentEnrollmentDTO filterOptions) {
		if (filterOptions == null) {
			Page<Enrollment> page = enrollmentRepo.findByStudent_Id(studentId, pageable);
			return (List<StudentEnrollmentDTO>) enrollmentConverter.convertToDTO(page.getContent(), StudentEnrollmentDTO.class);
		}
		else {
			Page<Enrollment> page = enrollmentRepo.filterEnrollmentsByStudent(studentId, null, null, null, null, null, null, null, pageable);
			return (List<StudentEnrollmentDTO>) enrollmentConverter.convertToDTO(page.getContent(), StudentEnrollmentDTO.class);
		}
	}

	@Override
	public List<DefaultEnrollmentDTO> getByCourseId(int courseId, Pageable pageable) {
		Page<Enrollment> page = enrollmentRepo.filterEnrollments(null, courseId, null, null, null, null, null, null, null, pageable);
		return enrollmentConverter.convertToDTO(page.getContent());
	}

	@Override
	public List<DefaultExamObligationTakingDTO> getEnrollmentExamObligationTakings(int enrollmentId,
			Pageable pageable) throws EntityNotFoundException {
		if (!enrollmentRepo.existsById(enrollmentId)) throw new EntityNotFoundException();
		return examObligationTakingService.getByEnrollmentId(enrollmentId, pageable);
	}

	@Override
	public List<DefaultExamTakingDTO> getEnrollmentExamTakings(int enrollmentId, Pageable pageable) throws EntityNotFoundException {
		if (!enrollmentRepo.existsById(enrollmentId)) throw new EntityNotFoundException();
		return examTakingService.getByEnrollmentId(enrollmentId, pageable);
	}

}
