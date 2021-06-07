package com.ftn.tseo2021.sf1513282018.studentService.service.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherTitleDTO;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.ResourceNotFoundException;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.TeacherTitle;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAny;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeSuperadmin;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher.TeacherTitleRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeacherTitleService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherTitleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.function.Function;

@Service
public class DefaultTeacherTitleService implements TeacherTitleService {
	
	@Autowired
	private TeacherTitleRepository teacherTitleRepo;

	@Autowired
	private DtoConverter<TeacherTitle, TeacherTitleDTO, DefaultTeacherTitleDTO> teacherTitleConverter;

	@AuthorizeAny
	@Override
	public DefaultTeacherTitleDTO getOne(Integer id) {
		Optional<TeacherTitle> teacherTitle = teacherTitleRepo.findById(id);
		return teacherTitleConverter.convertToDTO(teacherTitle.orElseThrow(() -> new ResourceNotFoundException()));
	}

	@AuthorizeSuperadmin
	@Override
	public Integer create(DefaultTeacherTitleDTO dto) {
		TeacherTitle teacherTitle = teacherTitleConverter.convertToJPA(dto);

		teacherTitle = teacherTitleRepo.save(teacherTitle);

		return teacherTitle.getId();
	}

	@AuthorizeSuperadmin
	@Override
	public void update(Integer id, DefaultTeacherTitleDTO dto) {
		if(!teacherTitleRepo.existsById(id)) throw new ResourceNotFoundException();

		TeacherTitle tNew = teacherTitleConverter.convertToJPA(dto);
		
		TeacherTitle t = teacherTitleRepo.findById(id).get();
		t.setName(tNew.getName());
		teacherTitleRepo.save(t);
	}

	@AuthorizeSuperadmin
	@Override
	public void delete(Integer id) {
		if(!teacherTitleRepo.existsById(id)) {throw new ResourceNotFoundException();}

		teacherTitleRepo.deleteById(id);
	}

	@Override
	public Page<DefaultTeacherTitleDTO> getAll(Pageable pageable) {
		Page<TeacherTitle> page = teacherTitleRepo.findAll(pageable);
		return page.map(new Function<TeacherTitle, DefaultTeacherTitleDTO>() {
			@Override
			public DefaultTeacherTitleDTO apply(TeacherTitle teacherTitle) {
				return teacherTitleConverter.convertToDTO(teacherTitle, DefaultTeacherTitleDTO.class);
			}
		});
	}
}
