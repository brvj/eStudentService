package com.ftn.tseo2021.sf1513282018.studentService.service.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teaching;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAdmin;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAny;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeTeacher;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher.TeachingRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeachingService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.CourseTeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.TeacherTeachingDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultTeachingService implements TeachingService {

	@Autowired
	private TeachingRepository teachingRepo;

	@Autowired
	private DtoConverter<Teaching, TeachingDTO, DefaultTeachingDTO> teachingConverter;

	@AuthorizeAny
	@Override
	public DefaultTeachingDTO getOne(Integer id) {
		Optional<Teaching> teaching = teachingRepo.findById(id);
		return teachingConverter.convertToDTO(teaching.orElse(null));
	}

	@AuthorizeAdmin
	@Override
	public Integer create(DefaultTeachingDTO dto) throws IllegalArgumentException {
		Teaching teaching = teachingConverter.convertToJPA(dto);

		teaching = teachingRepo.save(teaching);

		return teaching.getId();
	}

	@AuthorizeAdmin
	@Override
	public void update(Integer id, DefaultTeachingDTO dto) throws EntityNotFoundException, IllegalArgumentException {
		if(!teachingRepo.existsById(id)) throw new EntityNotFoundException();

		Teaching tNew = teachingConverter.convertToJPA(dto);
		
		Teaching t = teachingRepo.findById(id).get();
		t.setTeacherRole(tNew.getTeacherRole());
		teachingRepo.save(t);
	}

	@AuthorizeAdmin
	@Override
	public boolean delete(Integer id) {
		if(!teachingRepo.existsById(id)) return false;

		teachingRepo.deleteById(id);
		return true;
	}

	@AuthorizeAdmin
	@AuthorizeTeacher
	@SuppressWarnings("unchecked")
	@Override
	public List<TeacherTeachingDTO> filterTeachingsByTeacher(int teacherId, Pageable pageable, TeacherTeachingDTO filterOptions) {
		Page<Teaching> page;
		if(filterOptions == null) {
			page = teachingRepo.findByTeacher_Id(teacherId, pageable);
		}
		else {
			page = teachingRepo.filterTeachingsByTeacher(teacherId, filterOptions.getTeacherRole().getId(), pageable);
		}
		return (List<TeacherTeachingDTO>) teachingConverter.convertToDTO(page.getContent(), TeacherTeachingDTO.class);
	}

	@AuthorizeAdmin
	@AuthorizeTeacher
	@SuppressWarnings("unchecked")
	@Override
	public List<CourseTeachingDTO> filterTeachingsByCourse(int courseId, Pageable pageable, CourseTeachingDTO filterOptions) {
		Page<Teaching> page;
		if(filterOptions == null) {
			page = teachingRepo.findByCourse_Id(courseId, pageable);
		}
		else {
			page = teachingRepo.filterTeachingsByCourse(courseId, filterOptions.getTeacherRole().getId(), pageable);
		}
		return (List<CourseTeachingDTO>) teachingConverter.convertToDTO(page.getContent(), CourseTeachingDTO.class);
	}
}
