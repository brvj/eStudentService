package com.ftn.tseo2021.sf1513282018.studentService.service.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeachingService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teacher;
import com.ftn.tseo2021.sf1513282018.studentService.security.AuthorizeAdmin;
import com.ftn.tseo2021.sf1513282018.studentService.security.AuthorizeAny;
import com.ftn.tseo2021.sf1513282018.studentService.security.AuthorizeSuperadmin;
import com.ftn.tseo2021.sf1513282018.studentService.security.PrincipalHolder;

import lombok.RequiredArgsConstructor;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher.TeacherRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeacherService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.InstitutionTeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.TeacherTeachingDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultTeacherService implements TeacherService {

	@Autowired
	private TeacherRepository teacherRepo;

	@Autowired
	private TeachingService teachingService;

	@Autowired
	private DtoConverter<Teacher, TeacherDTO, DefaultTeacherDTO> teacherConverter;
	
	@Autowired
	private PrincipalHolder principalHolder;
	
	private void authorize(Integer institutionId) throws PersonalizedAccessDeniedException {
		if (principalHolder.getCurrentPrincipal().getInstitutionId() != institutionId) 
			throw new PersonalizedAccessDeniedException();
	}

	@AuthorizeAny
	@Override
	public DefaultTeacherDTO getOne(Integer id) {
		Optional<Teacher> teacher = teacherRepo.findById(id);
		return teacherConverter.convertToDTO(teacher.orElse(null));
	}

	@AuthorizeSuperadmin
	@Override
	public Integer create(DefaultTeacherDTO dto) throws IllegalArgumentException {
		Teacher teacher = teacherConverter.convertToJPA(dto);

		teacher = teacherRepo.save(teacher);

		return teacher.getId();
	}

	@AuthorizeSuperadmin
	@Override
	public void update(Integer id, DefaultTeacherDTO dto) throws EntityNotFoundException, IllegalArgumentException {
		if(!teacherRepo.existsById(id)) throw new EntityNotFoundException();

		Teacher tNew = teacherConverter.convertToJPA(dto);

		Teacher t = teacherRepo.findById(id).get();
		t.setFirstName(tNew.getFirstName());
		t.setLastName(tNew.getLastName());
		t.setAddress(tNew.getAddress());
		t.setDateOfBirth(tNew.getDateOfBirth());
		t.setTeacherTitle(tNew.getTeacherTitle());
		t.getUser().setUsername(tNew.getUser().getUsername());
		t.getUser().setFirstName(tNew.getFirstName());
		t.getUser().setLastName(tNew.getLastName());
		t.getUser().setEmail(tNew.getUser().getEmail());
		t.getUser().setPhoneNumber(tNew.getUser().getPhoneNumber());
		teacherRepo.save(t);

	}

	@AuthorizeSuperadmin
	@Override
	public boolean delete(Integer id) {
		if(!teacherRepo.existsById(id)) return false;

		teacherRepo.deleteById(id);
		return true;
	}

	@Override
	public DefaultTeacherDTO getByUserId(int userId) {
		Optional<Teacher> teacher = teacherRepo.findByUser_Id(userId);

		return teacherConverter.convertToDTO(teacher.orElse(null));
	}

	@SuppressWarnings("unchecked")
	@AuthorizeAdmin
	@Override
	public List<InstitutionTeacherDTO> filterTeachers(int institutionId, Pageable pageable, DefaultTeacherDTO filterOptions)
		throws PersonalizedAccessDeniedException {
		authorize(institutionId);

		Page<Teacher> page;
		if (filterOptions == null) {
			page = teacherRepo.findByInstitution_Id(institutionId, pageable);
		}
		else {
			page = teacherRepo.filterTeachers(institutionId, filterOptions.getTeacherTitle().getId(),
					filterOptions.getFirstName(), filterOptions.getLastName(), pageable);
		}
		return (List<InstitutionTeacherDTO>) teacherConverter.convertToDTO(page.getContent(), InstitutionTeacherDTO.class);
	}

	@Override
	public List<TeacherTeachingDTO> getTeacherTeachings(int teacherId, Pageable pageable)
			throws EntityNotFoundException {
		if(!teacherRepo.existsById(teacherId)) throw new EntityNotFoundException();

		List<TeacherTeachingDTO> teachings = teachingService.filterTeachingsByTeacher(teacherId, pageable, null);

		return teachings;
	}
}
