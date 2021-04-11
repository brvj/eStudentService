package com.ftn.tseo2021.sf1513282018.studentService.service.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.CourseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teaching;
import lombok.RequiredArgsConstructor;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher.TeachingRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeachingService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.TeacherTeachingDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultTeachingService implements TeachingService {

	private TeachingRepository teachingRepo;

	private CourseService courseService;

	private DtoConverter<Teaching, TeachingDTO, DefaultTeachingDTO> teachingConverter;
	
	@Override
	public boolean existsById(Integer id) {
		return teachingRepo.existsById(id);
	}

	@Override
	public DefaultTeachingDTO getOne(Integer id) {
		Optional<Teaching> teaching = teachingRepo.findById(id);
		return teachingConverter.convertToDTO(teaching.orElse(null));
	}

	@Override
	public Integer create(DefaultTeachingDTO t) {
		Teaching teaching = teachingConverter.convertToJPA(t);

		teaching = teachingRepo.save(teaching);

		return teaching.getId();
	}

	@Override
	public void update(Integer id, DefaultTeachingDTO t) {
		if(!teachingRepo.existsById(id)) throw new EntityNotFoundException();

		t.setId(id);
		Teaching teaching = teachingConverter.convertToJPA(t);

		teachingRepo.save(teaching);
	}

	@Override
	public boolean delete(Integer id) {
		if(!teachingRepo.existsById(id)) return false;

		teachingRepo.deleteById(id);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TeacherTeachingDTO> filterTeachingsByTeacher(int teacherId, Pageable pageable, TeacherTeachingDTO filterOptions) {
		if (filterOptions == null) {
			Page<Teaching> page = teachingRepo.findByTeacher_Id(teacherId, pageable);
			return (List<TeacherTeachingDTO>) teachingConverter.convertToDTO(page.getContent(), TeacherTeachingDTO.class);
		}
		else {
			Page<Teaching> page = teachingRepo.filterTeachings(teacherId, null, filterOptions.getTeacherRole().getId(), pageable);
			return (List<TeacherTeachingDTO>) teachingConverter.convertToDTO(page.getContent(), TeacherTeachingDTO.class);
		}
	}

	@Override
	public List<DefaultTeachingDTO> getByCourseId(int courseId, Pageable pageable) {
		if(courseService.getOne(courseId) == null) throw new EntityNotFoundException();

		Page<Teaching> page = teachingRepo.filterTeachings(null, courseId, null, pageable);

		return teachingConverter.convertToDTO(page.getContent());
	}
}
