package com.ftn.tseo2021.sf1513282018.studentService.service.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.institution.InstitutionRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.user.UserRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teacher;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher.TeacherRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeacherService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultTeacherService implements TeacherService {
	
	@Autowired
	private TeacherRepository teacherRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private InstitutionRepository institutionRepo;

	@Autowired
	private DtoConverter<Teacher, TeacherDTO, DefaultTeacherDTO> teacherConverter;

	@Override
	public DefaultTeacherDTO getOne(Integer id) {
		Optional<Teacher> teacher = teacherRepo.findById(id);
		return teacherConverter.convertToDTO(teacher.orElse(null));
	}

	@Override
	public Integer create(DefaultTeacherDTO t) {
		Teacher teacher = teacherConverter.convertToJPA(t);

		teacher = teacherRepo.save(teacher);

		return teacher.getId();
	}

	@Override
	public void update(Integer id, DefaultTeacherDTO t) {
		if(!teacherRepo.existsById(id)) throw new EntityNotFoundException();

		t.setId(id);
		Teacher teacher = teacherConverter.convertToJPA(t);

		teacherRepo.save(teacher);
	}

	@Override
	public boolean delete(Integer id) {
		if(!teacherRepo.existsById(id)) return false;

		teacherRepo.deleteById(id);
		return true;
	}

	@Override
	public DefaultTeacherDTO getByUserId(int userId, Pageable pageable) {
		if(!userRepo.existsById(userId)) throw new EntityNotFoundException();

		Optional<Teacher> teacher = teacherRepo.findByUser_Id(userId);

		return teacherConverter.convertToDTO(teacher.orElse(null));
	}

	@Override
	public List<DefaultTeacherDTO> getByInstitutionId(int institutionId, Pageable pageable) {
		if(!institutionRepo.existsById(institutionId)) throw new EntityNotFoundException();

		Page<Teacher> page = teacherRepo.filterTeachers(institutionId, null, null, null, pageable);

		return teacherConverter.convertToDTO(page.getContent());
	}
}
