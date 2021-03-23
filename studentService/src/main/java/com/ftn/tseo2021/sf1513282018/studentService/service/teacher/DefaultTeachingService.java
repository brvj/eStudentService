package com.ftn.tseo2021.sf1513282018.studentService.service.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.CourseRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher.TeacherRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teaching;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher.TeachingRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeachingService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeachingDTO;
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
	private TeacherRepository teacherRepo;

	@Autowired
	private CourseRepository courseRepo;

	@Autowired
	private DtoConverter<Teaching, TeachingDTO, DefaultTeachingDTO> teachingConverter;

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

	@Override
	public List<DefaultTeachingDTO> getByTeacherId(int teacherId, Pageable pageable) {
		if(!teacherRepo.existsById(teacherId)) throw new EntityNotFoundException();

		Page<Teaching> page = teachingRepo.filterTeachings(teacherId, null, null, pageable);

		return teachingConverter.convertToDTO(page.getContent());
	}

	@Override
	public List<DefaultTeachingDTO> getByCourseId(int courseId, Pageable pageable) {
		if(!courseRepo.existsById(courseId)) throw new EntityNotFoundException();

		Page<Teaching> page = teachingRepo.filterTeachings(null, courseId, null, pageable);

		return teachingConverter.convertToDTO(page.getContent());
	}
}
