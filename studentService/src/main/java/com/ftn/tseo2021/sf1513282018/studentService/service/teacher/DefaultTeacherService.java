package com.ftn.tseo2021.sf1513282018.studentService.service.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeachingService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.user.NewUserService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.EntityValidationException;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.ResourceNotFoundException;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teacher;
import com.ftn.tseo2021.sf1513282018.studentService.security.CustomPrincipal;
import com.ftn.tseo2021.sf1513282018.studentService.security.PersonalizedAuthorizator;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAdmin;

import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeTeacherOrAdmin;
import lombok.RequiredArgsConstructor;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher.TeacherRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeacherService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.InstitutionTeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.TeacherTeachingDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class DefaultTeacherService implements TeacherService {

	@Autowired
	private TeacherRepository teacherRepo;

	@Autowired
	private TeachingService teachingService;
	
	@Autowired
	private NewUserService userService;

	@Autowired
	private DtoConverter<Teacher, TeacherDTO, DefaultTeacherDTO> teacherConverter;

	@Autowired
	private PersonalizedAuthorizator authorizator;

	private CustomPrincipal getPrincipal() { return authorizator.getPrincipal(); }

	@AuthorizeTeacherOrAdmin
	@Override
	public DefaultTeacherDTO getOne(Integer id) {
		if(!getPrincipal().isAdmin() && getPrincipal().isTeacher()){
			authorizator.assertPrincipalTeacherIdIs(id, PersonalizedAccessDeniedException.class);
		}
		Teacher teacher = teacherRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());

		if(getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(teacher.getInstitution().getId(), PersonalizedAccessDeniedException.class);

		return teacherConverter.convertToDTO(teacher);
	}

	@AuthorizeAdmin
	@Override
	public Integer create(DefaultTeacherDTO dto) throws IllegalArgumentException {
		if (dto.getInstitution() != null && dto.getInstitution().getId() != null)
			authorizator.assertPrincipalIsFromInstitution(dto.getInstitution().getId(), EntityValidationException.class);
		else
			dto.setInstitution(new DefaultInstitutionDTO(getPrincipal().getInstitutionId(), null, null, null));
		
		if (userService.existsByUsername(dto.getUser().getUsername()))
			throw new EntityValidationException("Username already taken");
		
		Teacher teacher = teacherConverter.convertToJPA(dto);

		teacher = teacherRepo.save(teacher);

		return teacher.getId();
	}

	@AuthorizeTeacherOrAdmin
	@Override
	public void update(Integer id, DefaultTeacherDTO dto) throws EntityNotFoundException, IllegalArgumentException {
		if(!getPrincipal().isAdmin() && getPrincipal().isTeacher())
			authorizator.assertPrincipalTeacherIdIs(id, PersonalizedAccessDeniedException.class);

		Teacher t = teacherRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());

		if(getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(t.getInstitution().getId(), PersonalizedAccessDeniedException.class);

		if (dto.getInstitution() != null && dto.getInstitution().getId() != t.getInstitution().getId()) 
			throw new EntityValidationException("Cannot change institution");
		else if (dto.getInstitution() == null)
			dto.setInstitution(new DefaultInstitutionDTO(t.getInstitution().getId(), null, null, null));
		
		if (getPrincipal().isTeacher() && dto.getTeacherTitle().getId() != t.getTeacherTitle().getId())
			throw new EntityValidationException("Only admin can change teacher title");
		
		if (!t.getUser().getUsername().equals(dto.getUser().getUsername()) && 
				userService.existsByUsername(dto.getUser().getUsername()))
			throw new EntityValidationException("Username already taken");

		Teacher tNew = teacherConverter.convertToJPA(dto);

		t.setFirstName(tNew.getFirstName());
		t.setLastName(tNew.getLastName());
		t.setAddress(tNew.getAddress());
		t.setDateOfBirth(tNew.getDateOfBirth());
		t.setTeacherTitle(tNew.getTeacherTitle());
		t.getUser().setFirstName(tNew.getFirstName());
		t.getUser().setLastName(tNew.getLastName());
		t.getUser().setUsername(tNew.getUser().getUsername());
		t.getUser().setEmail(tNew.getUser().getEmail());
		t.getUser().setPhoneNumber(tNew.getUser().getPhoneNumber());
		teacherRepo.save(t);
	}

	@AuthorizeAdmin
	@Override
	public void delete(Integer id) {
		Teacher teacher = teacherRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());

		authorizator.assertPrincipalIsFromInstitution(teacher.getInstitution().getId(), PersonalizedAccessDeniedException.class);

		teacherRepo.deleteById(id);
	}

	@Override
	public DefaultTeacherDTO getByUserId(int userId) {
		Optional<Teacher> teacher = teacherRepo.findByUser_Id(userId);

		return teacherConverter.convertToDTO(teacher.orElse(null));
	}

	@SuppressWarnings("unchecked")
	@AuthorizeAdmin
	@Override
	public Page<InstitutionTeacherDTO> filterTeachers(int institutionId, Pageable pageable, DefaultTeacherDTO filterOptions) {
		authorizator.assertPrincipalIsFromInstitution(institutionId, PersonalizedAccessDeniedException.class);

		Page<Teacher> page;

		if (filterOptions == null) {
			page = teacherRepo.findByInstitution_Id(institutionId, pageable);
		}
		else {
			page = teacherRepo.filterTeachers(institutionId, filterOptions.getTeacherTitle().getId(),
					filterOptions.getFirstName(), filterOptions.getLastName(), pageable);
		}

		return page.map(new Function<Teacher, InstitutionTeacherDTO>() {
			@Override
			public InstitutionTeacherDTO apply(Teacher teacher) {
				return teacherConverter.convertToDTO(teacher, InstitutionTeacherDTO.class);
			}
		});
	}

	@Override
	public Page<TeacherTeachingDTO> getTeacherTeachings(int teacherId, Pageable pageable) {
		if(!teacherRepo.existsById(teacherId)) throw new ResourceNotFoundException();

		Page<TeacherTeachingDTO> teachings = teachingService.filterTeachingsByTeacher(teacherId, pageable, null);

		return teachings;
	}
}
