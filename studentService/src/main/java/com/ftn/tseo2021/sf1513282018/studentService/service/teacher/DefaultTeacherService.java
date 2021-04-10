package com.ftn.tseo2021.sf1513282018.studentService.service.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.institution.InstitutionService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeachingService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.user.UserService;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teacher;
import lombok.RequiredArgsConstructor;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher.TeacherRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeacherService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.InstitutionTeacherDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultTeacherService implements TeacherService {

	private TeacherRepository teacherRepo;

	private UserService userService;

	private TeachingService teachingService;

	private InstitutionService institutionService;

	private DtoConverter<Teacher, TeacherDTO, DefaultTeacherDTO> teacherConverter;
	
	@Override
	public boolean existsById(Integer id) {
		return teacherRepo.existsById(id);
	}

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
		if(userService.getOne(userId) == null) throw new EntityNotFoundException();

		Optional<Teacher> teacher = teacherRepo.findByUser_Id(userId);

		return teacherConverter.convertToDTO(teacher.orElse(null));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InstitutionTeacherDTO> getByInstitutionId(int institutionId, Pageable pageable) {
		if(institutionService.getOne(institutionId) == null) throw new EntityNotFoundException();

		Page<Teacher> page = teacherRepo.filterTeachers(institutionId, null, null, null, pageable);

		return (List<InstitutionTeacherDTO>) teacherConverter.convertToDTO(page.getContent(), InstitutionTeacherDTO.class);
	}

	@Override
	public List<DefaultTeachingDTO> getTeacherTeachings(int teacherId, Pageable pageable)
			throws EntityNotFoundException {
		if(!teacherRepo.existsById(teacherId)) throw new EntityNotFoundException();

		List<DefaultTeachingDTO> teachings = teachingService.getByTeacherId(teacherId, pageable);

		return teachings;
	}
}
