package com.ftn.tseo2021.sf1513282018.studentService.service.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherTitleDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.TeacherTitle;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher.TeacherTitleRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeacherTitleService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherTitleDTO;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class DefaultTeacherTitleService implements TeacherTitleService {
	
	@Autowired
	private TeacherTitleRepository teacherTitleRepo;

	@Autowired
	private DtoConverter<TeacherTitle, TeacherTitleDTO, DefaultTeacherTitleDTO> teacherTitleConverter;

	@Override
	public DefaultTeacherTitleDTO getOne(Integer id) {
		Optional<TeacherTitle> teacherTitle = teacherTitleRepo.findById(id);
		return teacherTitleConverter.convertToDTO(teacherTitle.orElse(null));
	}

	@Override
	public Integer create(DefaultTeacherTitleDTO t) {
		TeacherTitle teacherTitle = teacherTitleConverter.convertToJPA(t);

		teacherTitle = teacherTitleRepo.save(teacherTitle);

		return teacherTitle.getId();
	}

	@Override
	public void update(Integer id, DefaultTeacherTitleDTO t) {
		if(!teacherTitleRepo.existsById(id)) throw new EntityNotFoundException();

		t.setId(id);
		TeacherTitle teacherTitle = teacherTitleConverter.convertToJPA(t);

		teacherTitleRepo.save(teacherTitle);
	}

	@Override
	public boolean delete(Integer id) {
		if(!teacherTitleRepo.existsById(id)) return false;

		teacherTitleRepo.deleteById(id);
		return true;
	}
}
