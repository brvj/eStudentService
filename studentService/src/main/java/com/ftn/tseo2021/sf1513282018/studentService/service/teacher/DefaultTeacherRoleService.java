package com.ftn.tseo2021.sf1513282018.studentService.service.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherRoleDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.TeacherRole;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAny;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeSuperadmin;

import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeTeacherOrAdmin;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher.TeacherRoleRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeacherRoleService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherRoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.function.Function;

@Service
public class DefaultTeacherRoleService implements TeacherRoleService {
	
	@Autowired
	private TeacherRoleRepository teacherRoleRepo;

	@Autowired
	private DtoConverter<TeacherRole, TeacherRoleDTO, DefaultTeacherRoleDTO> teacherRoleConverter;

	@AuthorizeAny
	@Override
	public DefaultTeacherRoleDTO getOne(Integer id) {
		Optional<TeacherRole> teacherRole = teacherRoleRepo.findById(id);
		return teacherRoleConverter.convertToDTO(teacherRole.orElse(null));
	}

	@AuthorizeSuperadmin
	@Override
	public Integer create(DefaultTeacherRoleDTO dto) throws IllegalArgumentException {
		TeacherRole teacherRole = teacherRoleConverter.convertToJPA(dto);

		teacherRole = teacherRoleRepo.save(teacherRole);

		return teacherRole.getId();
	}

	@AuthorizeSuperadmin
	@Override
	public void update(Integer id, DefaultTeacherRoleDTO dto) throws EntityNotFoundException, IllegalArgumentException {
		if(!teacherRoleRepo.existsById(id)) throw new EntityNotFoundException();

		TeacherRole tNew = teacherRoleConverter.convertToJPA(dto);
		
		TeacherRole t = teacherRoleRepo.findById(id).get();
		t.setName(tNew.getName());
		teacherRoleRepo.save(t);
	}

	@AuthorizeSuperadmin
	@Override
	public void delete(Integer id) {
		if(!teacherRoleRepo.existsById(id)) {};

		teacherRoleRepo.deleteById(id);
	}

	@AuthorizeTeacherOrAdmin
	@Override
	public Page<DefaultTeacherRoleDTO> getAll(Pageable pageable) {
		Page<TeacherRole> page = teacherRoleRepo.findAll(pageable);
		return page.map(new Function<TeacherRole, DefaultTeacherRoleDTO>() {
			@Override
			public DefaultTeacherRoleDTO apply(TeacherRole teacherRole) {
				return teacherRoleConverter.convertToDTO(teacherRole, DefaultTeacherRoleDTO.class);
			}
		});
	}
}
