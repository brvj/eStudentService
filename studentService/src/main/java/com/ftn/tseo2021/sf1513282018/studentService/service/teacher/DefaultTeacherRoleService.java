package com.ftn.tseo2021.sf1513282018.studentService.service.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherRoleDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.TeacherRole;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher.TeacherRoleRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeacherRoleService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherRoleDTO;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class DefaultTeacherRoleService implements TeacherRoleService {
	
	@Autowired
	private TeacherRoleRepository teacherRoleRepo;

	@Autowired
	private DtoConverter<TeacherRole, TeacherRoleDTO, DefaultTeacherRoleDTO> teacherRoleConverter;
	
	@Override
	public boolean existsById(Integer id) {
		return teacherRoleRepo.existsById(id);
	}

	@Override
	public DefaultTeacherRoleDTO getOne(Integer id) {
		Optional<TeacherRole> teacherRole = teacherRoleRepo.findById(id);
		return teacherRoleConverter.convertToDTO(teacherRole.orElse(null));
	}

	@Override
	public Integer create(DefaultTeacherRoleDTO t) {
		TeacherRole teacherRole = teacherRoleConverter.convertToJPA(t);

		teacherRole = teacherRoleRepo.save(teacherRole);

		return teacherRole.getId();
	}

	@Override
	public void update(Integer id, DefaultTeacherRoleDTO t) {
		if(!teacherRoleRepo.existsById(id)) throw new EntityNotFoundException();

		t.setId(id);
		TeacherRole teacherRole = teacherRoleConverter.convertToJPA(t);

		teacherRoleRepo.save(teacherRole);
	}

	@Override
	public boolean delete(Integer id) {
		if(!teacherRoleRepo.existsById(id)) return false;

		teacherRoleRepo.deleteById(id);
		return true;
	}
}
